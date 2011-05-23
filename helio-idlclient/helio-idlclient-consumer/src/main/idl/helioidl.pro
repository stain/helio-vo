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
;Parameters:
;     starttime: Komma seperated starttime for the query in the form yyyy:mm:ddThh:mm:ss (ISO-8601).
;     endtime: Komma seperated endtime for the query in the form yyyy:mm:ddThh:mm:ss (ISO-8601).
;     from: Komma seperated resource for the query
;     service: HELIO service typ.
;
;Keywords:
;     /post - use http-post instead of http-get. Not working yet!
;
;Last Modified: 
;      16 Mai 2011 - Matthias Meyer 
  

function helioidl, starttime = starttime, $
              endtime = endtime, $
              from = from, $
              service = service, $
              where = where
   
   ;Default Parameters
   checkvar, starttime, '2003-02-01T00:00:00'   
   checkvar, endtime, '2003-02-02T00:00:00'
   checkvar, from, 'trajectories'
   checkvar, where, ''
   checkvar, service, 'ILS'      
              
   ; create a new IDLnetURL object   
   oUrl = OBJ_NEW('IDLnetUrl') 
   
   if keyword_set(post) then begin 
   
   ; Make a post request to an HTTP server.
   oUrl->SetProperty, URL_HOST = 'localhost'
   oUrl->SetProperty, URL_PORT = '8080'
   oUrl->SetProperty, URL_PATH = 'helio-idlclient-provider/AsyncQuersServiceServlet'
   oUrl->SetProperty, URL_SCHEME = 'http'
   data = ['starttime='+starttime,'endtime='+endtime,'from='+from]
   resultpost = oUrl->put(data, /BUFFER, /POST)
   
   endif else begin
   
   ; Make a get request to an HTTP server.
   oUrl->SetProperty, URL_HOST = 'localhost'
   oUrl->SetProperty, URL_PORT = '8085'
   ;oUrl->SetProperty, URL_PATH = 'helio-idlclient-provider/AsyncQueryServiceServlet'
   oUrl->SetProperty, URL_PATH = 'AsyncQueryServiceServlet'
   oUrl->SetProperty, URL_QUERY = 'service='+service+'&starttime='+starttime+'&endtime='+endtime+'&from='+from+'&where='+where
   ;oUrl->SetProperty, URL_QUERY = 'starttime=2003-02-01T00:00:00&endtime=2003-02-02T00:00:00&from=trajectories'
   result = oUrl->Get(/STRING_ARRAY)
   
   endelse
   
   ;Execute returncode
   res = EXECUTE(result[0])

   ;Check for Java exception and print message.
   status = tag_exist(str,'stacktrace')
   if status eq 1b then $
   print, 'Exception: ', str.message

   return, str
   
END