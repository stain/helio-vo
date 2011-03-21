FUNCTION Url_Callback, status, progress, data  
  
   ; print the info msgs from the url object  
   PRINT, status  
  
   ; return 1 to continue, return 0 to cancel  
   RETURN, 1  
END  


PRO neturl
  ; If the url object throws an error it will be caught here  
  CATCH, errorStatus   
  IF (errorStatus NE 0) THEN BEGIN  
    CATCH, /CANCEL  

    ; Display the error msg in a dialog and in the IDL output log  
    r = DIALOG_MESSAGE(!ERROR_STATE.msg, TITLE='URL Error', $  
       /ERROR)  
    PRINT, !ERROR_STATE.msg  

    ; Get the properties that will tell us more about the error.  
    oUrl->GetProperty, RESPONSE_CODE=rspCode, $  
       RESPONSE_HEADER=rspHdr, RESPONSE_FILENAME=rspFn  
    PRINT, 'rspCode = ', rspCode  
    PRINT, 'rspHdr= ', rspHdr  
    PRINT, 'rspFn= ', rspFn  

    ; Destroy the url object  
    OBJ_DESTROY, oUrl  
    RETURN  
   ENDIF 
   
   ; create a new IDLnetURL object   
   oUrl = OBJ_NEW('IDLnetUrl')  
  
   ; Specify the callback function  
   oUrl->SetProperty, CALLBACK_FUNCTION ='Url_Callback'  
  
   ; Set verbose to 1 to see more info on the transacton  
   oUrl->SetProperty, VERBOSE = 1  
  
   ; Make a request to an HTTP server.
   oUrl->SetProperty, URL_HOST = 'www.google.com'  
   fn = oUrl->Get(FILENAME='google.html')  
  
   ; Print the path to the file retrieved from the remote server  
   PRINT, 'filename returned = ', fn  
  
  
   ; Make the same requrest again, but save the retrieved data  
   ; into a string array.  
   strings = oUrl->Get(FILENAME='google.html', /STRING_ARRAY)  
  
   ; Print the returned array of strings  
   PRINT, 'array of strings returned:'  
   for i=1, n_elements(strings)-1 do print, strings[i]  
  
   ; Destroy the url object  
   OBJ_DESTROY, oUrl  

end