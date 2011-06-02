;+
;Project: 
;     HELIO
;
;Name: 
;     helioidl
;
;Purpose: 
;     IDL HELIO client. Access to HELIO querys from IDL.
;
;Parameters for helioidlquery:
;     starttime: Komma seperated starttime for the query in the form yyyy:mm:ddThh:mm:ss (ISO-8601).
;     endtime: Komma seperated endtime for the query in the form yyyy:mm:ddThh:mm:ss (ISO-8601).
;     from: Komma seperated resource for the query.
;     service: HELIO service typ.
;     where: where statement for the query.
;     
;Parameters for helioidlshowcatalog
;     no parameters.
;
;Keywords:
;     /post - use http-post instead of http-get. Not working yet!
;
;Last Modified: 
;      2. June 2011 - Matthias Meyer 
  

function helioidlquery, starttime = starttime, $
              endtime = endtime, $
              from = from, $
              service = service, $
              where = where
   
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
   
   
   ;Default Parameters
   checkvar, starttime, '2003-02-01T00:00:00'   
   checkvar, endtime, '2003-02-02T00:00:00'
   checkvar, from, 'trajectories'
   checkvar, where, ''
   checkvar, service, 'ILS'       
              
   ; create a new IDLnetURL object   
   oUrl = OBJ_NEW('IDLnetUrl') 
   
   if keyword_set(post) then begin 
   
   ; Make a post request to a HTTP server.
   oUrl->SetProperty, URL_HOST = 'localhost'
   oUrl->SetProperty, URL_PORT = '8080'
   oUrl->SetProperty, URL_PATH = 'helio-idlclient-provider/AsyncQuersServiceServlet'
   oUrl->SetProperty, URL_SCHEME = 'http'
   data = ['starttime='+starttime,'endtime='+endtime,'from='+from]
   resultpost = oUrl->put(data, RESPONSE_FILENAME='helioidlapi.pro', /BUFFER, /POST)
   
   endif else begin
   
   ; Make a get request to a HTTP server.
   oUrl->SetProperty, URL_HOST = 'localhost'
   ;oUrl->SetProperty, URL_PORT = '8080'
   oUrl->SetProperty, URL_PORT = '8085'
   ;oUrl->SetProperty, URL_PATH = 'helio-idlclient-provider/AsyncQueryServiceServlet'
   oUrl->SetProperty, URL_PATH = 'AsyncQueryServiceServlet'
   oUrl->SetProperty, URL_QUERY = 'service='+service+'&starttime='+starttime+'&endtime='+endtime+'&from='+from+'&where='+where
   ;oUrl->SetProperty, URL_QUERY = 'starttime=2003-02-01T00:00:00&endtime=2003-02-02T00:00:00&from=trajectories'
   ;result = oUrl->Get(/STRING_ARRAY)
   result = oUrl->Get(FILENAME='helioidlapi.pro')
      
   endelse
   
   ;Execute returncode
   ;res = EXECUTE(result[0])
   RESOLVE_ROUTINE, 'helioidlapi', /IS_FUNCTION
   var = call_function('helioidlapi')

   ;Check for Java exception and print message.
   result = size(var)
   if result[2] eq 8 THEN BEGIN
   print, 'struct'
   status = tag_exist(var,'stacktrace')
   if status eq 1b then $
   print, 'Exception: ', str.message
   ENDIF
   
   ; we are done so we release the url object  
   OBJ_DESTROY, oUrl 

   return, var
   
END

function helioidlshowcatalog, catalog = catalog

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

   checkvar, catalog, ''
   
   ; create a new IDLnetURL object   
   oUrl = OBJ_NEW('IDLnetUrl')

   ; Make a get request to a HTTP server.
   oUrl->SetProperty, URL_HOST = 'localhost'
   ;oUrl->SetProperty, URL_PORT = '8080'
   oUrl->SetProperty, URL_PORT = '8085'
   ;oUrl->SetProperty, URL_PATH = 'helio-idlclient-provider/HecStaticCatalogRegistryServlet'
   oUrl->SetProperty, URL_PATH = 'HecStaticCatalogRegistryServlet'
   oUrl->SetProperty, URL_QUERY = 'catalog='+catalog
   ;result = oUrl->Get(/STRING_ARRAY)
   result = oUrl->Get(FILENAME='helioidlapi.pro')
   
   ;Execute returncode
   ;res = EXECUTE(result[0])
   RESOLVE_ROUTINE, 'helioidlapi', /IS_FUNCTION
   var = call_function('helioidlapi')

   ;Check for Java exception and print message.
   result = size(var)
   if result[2] eq 8 THEN BEGIN
   print, 'struct'
   status = tag_exist(var,'stacktrace')
   if status eq 1b then $
   print, 'Exception: ', str.message
   ENDIF
   
   ; we are done so we release the url object  
   OBJ_DESTROY, oUrl 

   return, var
END