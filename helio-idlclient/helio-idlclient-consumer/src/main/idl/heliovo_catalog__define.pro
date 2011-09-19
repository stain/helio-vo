;+
;Project: 
;     HELIOVO
;
;Name: 
;     heliovo_catalog__define
;
;Purpose: 
;     Define a heliovo_catalog object.
;
;Last Modified: 
;      5 Sep 2011 - Matthias Meyer 

;
;-
;-- heliovo_catalog init.

function heliovo_catalog::init, service=service, catalog=catalog
  checkvar, service, ''
  checkvar, catalog, ''
  
  self.url[0] = 'StaticCatalogRegistryServlet'
  self.url[1] = 'AsyncQueryServiceServlet'
  
  self.service = service
  self.catalog = catalog
  x = self->get_data()
  return, 1
end


;------------------------------------------------------------------------------------------------------------------------
;-- set method for all helio_vo option.

pro heliovo_catalog::set, time_interval = time_interval, $
              where = where;, $
              ;starttime = starttime, $
              ;endtime = endtime
              
  
  if keyword_set(time_interval) then begin
    ;interval size 1/2 of array size (1d array)
    n_int=(size(time_interval, /dim))[0]/2
    
    ;interval size equal arraysize (2d array)
    if size(time_interval,/n_dim) eq 2 then begin
      n_int = (size(time_interval,/d))[1]
    endif
    
    starttime_arr = dblarr(n_int)
    endtime_arr = dblarr(n_int)
    for i=0, n_int-1 do begin
      starttime_arr[i] = anytim(time_interval[i*2])
      endtime_arr[i] = anytim(time_interval[i*2+1])
    endfor
    
    heap_free, self.time_int[0]
    heap_free, self.time_int[1]
    
    self.time_int[0] = ptr_new(starttime_arr)
    self.time_int[1] = ptr_new(endtime_arr)
    
    for i=0, n_int-1 do begin
      print, 'interval ' + String(i) + ': from: ' + anytim((*self.time_int[0])[i], /CCSDS) + ' to: ' + anytim((*self.time_int[1])[i], /CCSDS)
    endfor
    
  endif
  
  if keyword_set(starttime) then begin
    if(size(starttime,/n_dim) eq 0) then starttime = STRSPLIT(starttime,',',/ext)
    
    n_int = (size(starttime, /d))[0]
    
    starttime_arr = dblarr(n_int)
    
    for i=0, n_int-1 do begin
      starttime_arr[i] = anytim(starttime[i])
    endfor
    
    heap_free, self.time_int[0]
    self.time_int[0] = ptr_new(starttime_arr)
    
    for i=0, n_int-1 do begin
      print, 'interval ' + String(i) + ': starttime set to: ' + anytim((*self.time_int[0])[i], /CCSDS)
    endfor
  endif
  
  if keyword_set(endtime) then begin
    if(size(endtime,/n_dim) eq 0) then endtime = STRSPLIT(endtime,',',/ext)
    
    n_int = (size(endtime, /d))[0]
    
    endtime_arr = dblarr(n_int)
    
    for i=0, n_int-1 do begin
      endtime_arr[i] = anytim(endtime[i])
    endfor
    
    heap_free, self.time_int[0]
    self.time_int[0] = ptr_new(endtime_arr)
    
    for i=0, n_int-1 do begin
      print, 'interval ' + String(i) + ': endtime set to: ' + anytim((*self.time_int[1])[i], /CCSDS)
    endfor
  endif
  
  if keyword_set(where) then begin
    self.where = where
    print, 'where set to: ' + self.where
  endif
end


;------------------------------------------------------------------------------------------------------------------------
;-- Get the info of this helio catalog.

function heliovo_catalog::get_data

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
  
  if self.service eq '' then begin
    print, 'Error, object created without service!'
    return, -1
  endif
  
  if self.catalog eq '' then begin
    print, 'Error, object created without catalog!'
    return, -1
  endif
   
  ;print, 'Starting query...'
   
  ; create a new IDLnetURL object   
  oUrl = OBJ_NEW('IDLnetUrl')

  ; Make a get request to a HTTP server.
  oUrl->SetProperty, URL_HOST = !heliovo_host
  oUrl->SetProperty, URL_PORT = !heliovo_port
  oUrl->SetProperty, URL_PATH = !heliovo_context+self.url[0]
  oUrl->SetProperty, URL_QUERY = 'service='+self.service+'&catalog='+self.catalog
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
;-- helio query function.  Sends a query with all options to the helio backend and returns the result.

function heliovo_catalog::get_query, $
              where = where, $
              time_interval = time_interval;, $
              ;starttime = starttime, $
              ;endtime = endtime
   
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
   
  self->set, time_interval=time_interval, where=where

  n_int = (size(*(self.time_int[0]),/d))[0] 
  n_int1 = (size(*(self.time_int[0]),/d))[0]
  if n_int gt n_int1 then n_int = n_int1
  
  starttime = anytim((*self.time_int[0])[0], /CCSDS)
  endtime = anytim((*self.time_int[1])[0], /CCSDS)
  
  for i=1, n_int-1 do begin
    starttime = starttime + ',' + anytim((*self.time_int[0])[i], /CCSDS)
    endtime = endtime + ',' + anytim((*self.time_int[1])[i], /CCSDS)
  endfor

  print, 'Stating query...'
             
  ; create a new IDLnetURL object   
  oUrl = OBJ_NEW('IDLnetUrl') 
   
  ; Make a get request to a HTTP server.
  oUrl->SetProperty, URL_HOST = !heliovo_host
  oUrl->SetProperty, URL_PORT = !heliovo_port
  oUrl->SetProperty, URL_PATH = !heliovo_context+self.url[1]
  oUrl->SetProperty, URL_QUERY = 'service='+self.service+'&starttime='+starttime+'&endtime='+endtime+'&from='+self.catalog+'&where='+self.where
  ;result = oUrl->Get(/STRING_ARRAY)
  result = oUrl->Get(FILENAME='helioidlapi.pro')
   
  ;Execute returncode
  ;res = EXECUTE(result[0])
  RESOLVE_ROUTINE, 'helioidlapi', /IS_FUNCTION
  var = call_function('helioidlapi')
  print, 'Query done!'

  ;Check for Java exception and print message.
  if size(var, /d) eq 0 then begin
   
    self_tag_names = obj_props( var )
    index = where_arr( self_tag_names, 'STACKTRACE' )

    if index  ne -1 then begin
      print, 'Exception: ', var->get(/message)
    endif
  endif
   
  ; we are done so we release the url object  
  OBJ_DESTROY, oUrl 

  return, var
   
end


;------------------------------------------------------------------------------------------------------------------------
; Get function.

function heliovo_catalog::get, data=data, query=query
  if keyword_set(data) then return, self->get_data()
  if keyword_set(query) then return, self->get_query()
end


;------------------------------------------------------------------------------------------------------------------------
; heliovo help

pro heliovo_catalog::help
  print, ''
  print, 'IDL> heliovo_catalog help'
  print, '-----------------------------------------------------------------------------------------------------------------------'
  print, "IDL> catalog->set, time_interval=['1-may-2005','2-may-2005']    ; set time_interval OR"
  print, "IDL> catalog->set, starttime='1-may-2005'                       ; set starttime of time_interval AND"
  print, "IDL> catalog->set, endtime='2-may-2005'                         ; set endtime of time_interval"
  print, 'IDL>'
  print, 'IDL> result = catalog->get(/query)                              ; run the query'
end

;------------------------------------------------------------------------------------------------------------------------
;-- heliovo_catalog_SERVICE data structure

pro heliovo_catalog__define
  self = {heliovo_catalog, url:strarr(2), data:ptr_new(), service:'', catalog:'', where:'', time_int:ptrarr(2)}
end