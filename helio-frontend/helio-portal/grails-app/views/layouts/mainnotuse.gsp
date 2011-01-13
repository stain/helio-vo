<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        
          <link rel="stylesheet" href="${resource(dir:'css',file:'1.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'menupro.css')}" />


        <link rel="stylesheet" href="${resource(dir:'css',file:'jquery-ui-1.8.2.custom.css"')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'anytime.css"')}" />

        <link rel="shortcut icon" href="${resource(dir:'images/helio',file:'sun.ico')}" type="image/x-icon" />

        <g:javascript src="jquery-1.4.2.min.js"/>
        <g:javascript src="jquery-ui-1.8.2.custom.min.js"/>
        <g:javascript src="anytime.js"/>
        <g:layoutHead />
        
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        <div id="grailsLogo" class="logo"><a href="http://grails.org"><img src="${resource(dir:'images',file:'grails_logo.png')}" alt="Grails" border="0" /></a></div>
        <g:layoutBody />
    </body>
</html>