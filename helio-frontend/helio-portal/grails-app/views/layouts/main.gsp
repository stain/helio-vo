<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
    <head>
        <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
        <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
        <META HTTP-EQUIV="EXPIRES" CONTENT="0">
        <title><g:layoutTitle default="HELIO Front End" /></title>
        <g:layoutHead />
        <link rel="shortcut icon" href="${resource(dir:'images/helio',file:'sun.ico')}" type="image/x-icon" />
    </head>
    <body>
        <!-- Logo Elements -->
        <div id="headerbar">
          <div style="float:left; display: block; height: 89px;vertical-align: middle; "><a href="http://helio-vo.eu"><img style="top:-2px;vertical-align: middle; " src="${resource(dir:'images/background',file:'header_logo.png')}" /></a></div>
          <div style="float:left; display: block; height: 89px;"><img src="${resource(dir:'images/background',file:'header_text.png')}" /></div>
          <div style="float:right; display: block; height: 89px;vertical-align: middle;"><img style="height: 89px; top:-2px;vertical-align: middle; " src="${resource(dir:'images/background',file:'glowlogo.png')}" /></div>
        </div><%-- 
        
        Navigation Bar 
        --%><g:render template="/misc/navbar" />        
        <g:layoutBody />
    </body>
</html>