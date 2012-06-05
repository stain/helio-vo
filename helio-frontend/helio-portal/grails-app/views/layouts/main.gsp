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
        <div id="logo">
          <table style="width: 100%;position:relative;top:-18px;">
            <tr>
              <td><img style="float:left;z-index:100;" src="${resource(dir:'images/background',file:'header_logo.png')}"   /></td>
              <td align="center"><img style="z-index:100;" src="${resource(dir:'images/background',file:'header_text.png')}"   /></td>
            <td><img style="position:relative;top:-1px;float:right;height: 120px" src="${resource(dir:'images/background',file:'glowlogo.png')}"  /></td>
            </tr>
          </table>
        </div>
        <g:layoutBody />
    </body>
</html>