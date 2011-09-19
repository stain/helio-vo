;+
;Project: 
;     HELIOVO
;
;Name: 
;     heliovo__define
;
;Purpose: 
;     Define a heliovo object.
;     
;Syntax:
;     o=heliovo()
;     o->help()
;
;Last Modified: 
;      19 Sep 2011 - Matthias Meyer 

;
;-
;-- HELIOVO init.

function heliovo::init
  DEFSYSV, '!heliovo_host', 'helio.i4ds.technik.fhnw.ch'
  DEFSYSV, '!heliovo_port', '8080'
  DEFSYSV, '!heliovo_context', 'helio-idl/'
  ;DEFSYSV, '!heliovo_host', 'localhost'
  ;DEFSYSV, '!heliovo_port', '8085'
  ;DEFSYSV, '!heliovo_context', ''
  self.url = 'HelioServiceNameServlet'
  x = self->get_data()
  return, 1
end


;------------------------------------------------------------------------------------------------------------------------
; Get the Helio Services.

function heliovo::get_data

  ; If the IDLnetURL object throws an error it will be caught here  
  CATCH, errorStatus  
  IF (errorStatus NE 0) THEN BEGIN  
    CATCH, /CANCEL  
 
    ; Display the error msg in a dialog and in the IDL Output log  
    r = DIALOG_MESSAGE("Can't connect to server '"+!heliovo_host+"'. Please check with your system administrator." $
        + "                                                                                                      " $
        + "Detail: " + !ERROR_STATE.msg, TITLE='URL Error', $  
      /ERROR)  
    PRINT, !ERROR_STATE.msg  
  
    ; Get the properties that will tell about the error.  
    oUrl->GetProperty, RESPONSE_CODE=rspCode, $  
      RESPONSE_HEADER=rspHdr, RESPONSE_FILENAME=rspFn  
    PRINT, 'rspCode = ', rspCode  
    PRINT, 'rspHdr= ', rspHdr  
    PRINT, 'rspFn= ', rspFn  
  
    ; Since we are done we can destroy the url object  
    OBJ_DESTROY, oUrl  
    RETURN, ptr_new()
  ENDIF 
  
  if(ptr_valid(self.data)) then return, *self.data
  
  ;print, 'Starting query...'
  
  ; create a new IDLnetURL object   
  oUrl = OBJ_NEW('IDLnetUrl')

  ; Make a get request to a HTTP server.
  oUrl->SetProperty, URL_HOST = !heliovo_host
  oUrl->SetProperty, URL_PORT = !heliovo_port
  oUrl->SetProperty, URL_PATH = !heliovo_context+self.url
  oUrl->SetProperty, URL_QUERY = ''
  result = oUrl->Get(FILENAME='helioidlapi.pro')
   
  ;Execute returncode
  RESOLVE_ROUTINE, 'helioidlapi', /IS_FUNCTION
  var = call_function('helioidlapi')
  ;print, 'Query done!'
   
  ; we are done so we release the url object  
  OBJ_DESTROY, oUrl
   
  ;Check for Java exception and print message.
  if size(var, /d) eq 0 then begin
   
    self_tag_names = obj_props( var )
    index = where_arr( self_tag_names, 'STACKTRACE' )

    if index  ne -1 then begin
      print, 'Exception: ', var->get(/message)
      return, var
    endif
  endif

  self.data = ptr_new(var)
  return, var
END


;------------------------------------------------------------------------------------------------------------------------
;-- Return helio_services object.

function heliovo::get_service, service

  if(ptr_valid(self.data) eq 0) then x = self->get_data()
  if(ptr_valid(self.data) eq 0) then return, -1

  if keyword_set(service) then begin
    if(size(service, /type) eq 2) then begin
      if(service gt (size(*self.data,/d))[0] or service lt 1) then begin
        print, 'Error: index ' + STRING(service) + ' is out of range'
        return, -1
      endif
      service = (*self.data)[service-1]
    endif else begin
      if(where_arr(STRUPCASE(*self.data),STRUPCASE(service)) eq -1) then begin
      print, 'Error: no service found with name ' + (service)
      return, -1
      endif
    endelse
    x = obj_new('heliovo_service', service=(service))
    print, 'Created new helio_service object for service ' + (service)
    return, x
  endif
  print, 'error, no service specified!'
end


;------------------------------------------------------------------------------------------------------------------------
; Get function.

function heliovo::get, data=data, service=service
  if keyword_set(data) then return, self->get_data()
  if keyword_set(service) then return, self->get_service(service)
end


;------------------------------------------------------------------------------------------------------------------------
;-- Search for helio services.

pro heliovo::find_service, service
  if keyword_set(service)  then begin
    if(ptr_valid(self.data) eq 0) then x = self->get_data()
    if(ptr_valid(self.data) eq 0) then return
  
    for i=1,(size(*self.data, /d))[0] do begin
      if stregex((*self.data)[i-1], service, /boolean, /FOLD_CASE) eq 1 then print, STRING(i) + ': ' + (*self.data)[i-1]
    endfor
  endif
end


;------------------------------------------------------------------------------------------------------------------------
;-- Search function.

pro heliovo::find, service=service
  if keyword_set(service) then self->find_service, service
end


;------------------------------------------------------------------------------------------------------------------------
; Print the helio services.

pro heliovo::print_service

  if(ptr_valid(self.data) eq 0) then x =self->get_data()
  if(ptr_valid(self.data) eq 0) then return
  
  for i=1,(size(*self.data, /d))[0] do print, STRING(i) + ': ' + (*self.data)[i-1]
end


;------------------------------------------------------------------------------------------------------------------------
; Print function.

pro heliovo::print, service=service
  if keyword_set(service) then self->print_service
end


;------------------------------------------------------------------------------------------------------------------------
; heliovo help

pro heliovo::help
  print, ''
  print, 'IDL> heliovo help'
  print, '-----------------------------------------------------------------------------------------------------------------------'
  print, 'IDL> helio = heliovo()                                          ; create heliovo object'
  print, 'IDL> '
  print, 'IDL> helio->print, /service                                     ; print available services'
  print, "IDL> helio->find, service='SERVICENAME'                         ; search for service with name SERVICENAME (RegExp)"
  print, 'IDL> service = helio->get(service=SERVICE)                          ; gets service object of SERVICE (Name or ID)'
  print, 'IDL> '
  print, 'IDL> service->print, /catalog                                   ; print available catalogs for this helio_service'
  print, "IDL> service->find, catalog='CATALOGNAME'                       ; search for catalog with name CATALOGNAME (RegExp)"
  print, 'IDL> catalog = service->get(catalog=catalog)                    ; gets catalog object of CATALOG (Name or ID)'
  print, 'IDL> '
  print, "IDL> catalog->set, time_interval=['1-may-2005','2-may-2005']    ; set time_interval OR"
  print, "IDL> catalog->set, where='WHERE_STATEMENT'                      ; set where statement"
  print, 'IDL>'
  print, 'IDL> data = catalog->get(/struct)                               ; run the query and directly parse the data OR'
  print, 'IDL>'
  print, 'IDL> result = catalog->get(/query)                              ; run the query'
  print, 'IDL>'
  print, "IDL> x = wget(url=result->get(/url), file='my.xml')             ; download result and save it to my.xml"
  print, 'IDL> myxml = wget(url=result->get(/url))                        ; download result to variable myxml'
  print, 'IDL>'
  print, "IDL> parser = obj_new('votable2struct                           ; create votable parser object"
  print, "IDL> data = parser->getdata('my.xml')                           ; download result to variable myxml"
end


;------------------------------------------------------------------------------------------------------------------------
;-- HELIOVO data structure

pro heliovo__define
  self = {heliovo, url:'', data:ptr_new()}
end