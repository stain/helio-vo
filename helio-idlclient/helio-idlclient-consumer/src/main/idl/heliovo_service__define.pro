;+
;Project: 
;     HELIOVO
;
;Name: 
;     heliovo_service__define
;
;Purpose: 
;     Define a heliovo_service object.
;
;Last Modified: 
;      6 Sep 2011 - Matthias Meyer 

;
;-
;-- heliovo_service init.

function heliovo_service::init, service=service
  checkvar, service, ''

  self.host = 'localhost'
  ;self.url = 'helio-idlclient-provider/StaticCatalogRegistryServlet'
  self.url = 'StaticCatalogRegistryServlet'
  ;self.port = '8080'
  self.port = '8085'
  self.service = service
  x = self->get_data()
  return, 1
end


;------------------------------------------------------------------------------------------------------------------------
;-- Get the helio catalogs of this helio service.

function heliovo_service::get_data

  ; If the IDLnetURL object throws an error it will be caught here  
  CATCH, errorStatus  
  IF (errorStatus NE 0) THEN BEGIN  
    CATCH, /CANCEL  
  
    ; Display the error msg in a dialog and in the IDL Output log  
    r = DIALOG_MESSAGE(!ERROR_STATE.msg, TITLE='URL Error', $  
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
  
  if self.service eq '' then begin
    print, 'Error, object created without service!'
    return, -1
  endif
   
  ;print, 'Starting query...'
   
  ; create a new IDLnetURL object   
  oUrl = OBJ_NEW('IDLnetUrl')

  ; Make a get request to a HTTP server.
  oUrl->SetProperty, URL_HOST = self.host
  oUrl->SetProperty, URL_PORT = self.port
  oUrl->SetProperty, URL_PATH = self.url
  oUrl->SetProperty, URL_QUERY = 'service='+self.service
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
;-- Return helio_catalog object.

function heliovo_service::get_catalog, catalog=catalog
  if(ptr_valid(self.data) eq 0) then x = self->get_data()
  if(ptr_valid(self.data) eq 0) then return, -1
  
  if keyword_set(catalog) then begin
    if(size(catalog, /type) eq 2) then begin
      if(catalog ge (size(*self.data,/d))[0]) then begin
        print, 'Error: index ' + STRING(catalog) + ' is out of range'
        return, -1
      endif
      catalog = ((*self.data)[catalog])->get(/catalogname)
    endif else begin
      x=0
      for i=0,(size((*self.data), /d))[0]-1 do begin
        if((*self.data)[i]->get(/catalogname) eq catalog) then x=1
      endfor
      if x ne 1 then begin
        print, 'Error: no catalog found with name ' + catalog
        return, -1
      endif
    endelse
    x=obj_new('heliovo_catalog', service=self.service, catalog=catalog)
    print, 'Created new helio_catalog object for catalog ' + catalog
    return, x
  endif
  print, 'error, no catalog specified!'
end


;------------------------------------------------------------------------------------------------------------------------
; Get function.

function heliovo_service::get, data=data, catalog=catalog
  if keyword_set(data) then return, self->get_data()
  if keyword_set(catalog) then return, self->get_catalog(catalog=catalog)
end


;------------------------------------------------------------------------------------------------------------------------
;-- Search for helio catalogs.

pro heliovo_service::find_catalog, catalog=catalog
  if(ptr_valid(self.data) eq 0) then x = self->get_data()
  if(ptr_valid(self.data) eq 0) then return
  
  for i=1,(size(*self.data, /d))[0] do begin
    if stregex((*self.data)[i-1]->get(/catalogname), catalog, /boolean, /FOLD_CASE) eq 1 then print, STRING(i) + ': ' + (*self.data)[i-1]->get(/catalogname)
  endfor
end


;------------------------------------------------------------------------------------------------------------------------
;-- Search function.

pro heliovo_service::find, catalog=catalog
  if keyword_set(catalog) then self->find_catalog, catalog=catalog
end


;------------------------------------------------------------------------------------------------------------------------
; Print the helio catalogs.

pro heliovo_service::print_catalog, service=service
  if(ptr_valid(self.data) eq 0) then x = self->get_data()
  if(ptr_valid(self.data) eq 0) then return
  
  for i=1,(size((*self.data), /d))[0] do print, STRING(i) + ': ' + (*self.data)[i-1]->get(/catalogname)
end


;------------------------------------------------------------------------------------------------------------------------
; Print function.

pro heliovo_service::print, catalog=catalog
  if keyword_set(catalog) then self->print_catalog
end


;------------------------------------------------------------------------------------------------------------------------
; heliovo help

pro heliovo_service::help
  print, ''
  print, 'IDL> heliovo_service help'
  print, '-----------------------------------------------------------------------------------------------------------------------'
  print, 'IDL> service->print, /catalog                                   ; print available catalogs for this helio_service'
  print, "IDL> service->find, catalog='CATALOGNAME'                       ; search for catalog with name CATALOGNAME (RegExp)"
  print, 'IDL> catalog = service->get(catalog=catalog)                    ; gets catalog object of CATALOG (Name or ID)'
end


;------------------------------------------------------------------------------------------------------------------------
;-- heliovo_service_SERVICE data structure

pro heliovo_service__define
  self = {heliovo_service, host:'', url:'', port:'', data:ptr_new(), service: ''}
end