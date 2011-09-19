;+
;Project: 
;     HELIO
;
;Name: 
;     wget
;
;Purpose: 
;     wget for idl. Simple programm to download a remote file to a local variable or file.
;
;Last Modified: 
;      29 Aug 2011 - Matthias Meyer 
  
;------------------------------------------------------------------------------------------------------------------------
; wget for idl.  Downloads a file from a http server to a variable or save it to a local file.

function wget, url = url, filename = filename

  ; If the IDLnetURL object throws an error it will be caught here  
  CATCH, errorStatus  
  IF (errorStatus NE 0) THEN BEGIN  
    CATCH, /CANCEL
    
    ; Display the error msg in a dialog and in the IDL Output log  
    r = DIALOG_MESSAGE("Can't connect to server '"+url+"'. Please check with your system administrator." $
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

  checkvar, url, ''
  
  ;check if url is emtpy
  if url eq '' then print, 'Error, no url specified'
   
  ;find the start and end of the hostname within the url
  hoststart = 0
  if STRPOS(url, 'http://') eq 0 then hoststart = 7
  hostend = STRPOS(url, '/', hoststart)
   
  ;split the url into host and path
  if hostend ne -1 then begin
    host = STRMID(url, hoststart, hostend-hoststart)
    urlpath = STRMID(url, hostend)
  endif else begin
    host = STRMID(url, hoststart)
    urlpath = ''
  endelse
   
  ;split host if a port is specified
  if STRPOS(host, ':') ne -1 then begin
    host = STRSPLIT(host, ':', /EXTRACT)
    hostname = host[0]
    hostport = host[1]
  endif else begin
    hostname = host
    hostport = '80'
  endelse
   
  ;split path
  if STRPOS(urlpath, '?') ne -1 then begin
    urlpath = STRSPLIT(urlpath, '?', /EXTRACT)
    path = urlpath[0]
    query = urlpath[1]
  endif else begin
    path = urlpath
    query = ''
  endelse
   
  print, 'starting download...'
   
  ; create a new IDLnetURL object   
  oUrl = OBJ_NEW('IDLnetUrl')

  ; Make a get request to a HTTP server.
  oUrl->SetProperty, URL_HOST = hostname
  oUrl->SetProperty, URL_PORT = hostport
  oUrl->SetProperty, URL_PATH = path
  oUrl->SetProperty, URL_QUERY = query
  if(keyword_set(filename)) then begin
    result = oUrl->Get(FILENAME=filename)
  endif else begin
    result = oUrl->Get(/STRING_ARRAY)
  endelse
  print, 'download done!'
   
   
  ; we are done so we release the url object  
  OBJ_DESTROY, oUrl 

  return, result
END


;------------------------------------------------------------------------------------------------------------------------
;-- wget for idl.  Downloads a file from a http server to a local file (default: idldownload.file). 

pro wget, url = url, filename = filename
  checkvar, filename, 'idldownload.file'
  x = wget(url=url, filename=filename)
end
