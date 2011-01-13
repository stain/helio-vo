<html>
  <head>
  <gui:resources components="['tooltip','datePicker']"/>
  <link rel="stylesheet" href="${resource(dir:'css',file:'singleService.css')}" />
  <link rel="stylesheet" href="${resource(dir:'css',file:'menupro.css')}" />

       <link rel="stylesheet" href="${resource(dir:'css',file:'jquery-ui-1.8.2.custom.css"')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'anytime.css"')}" />

        <link rel="shortcut icon" href="${resource(dir:'images/helio',file:'sun.ico')}" type="image/x-icon" />

        <g:javascript src="jquery-1.4.2.min.js"/>
        <g:javascript src="jquery-ui-1.8.2.custom.min.js"/>
        <g:javascript src="anytime.js"/>
        <g:javascript src="singleService/index.js"/>

  <title><g:layoutTitle default="Helio" /></title>
  <g:layoutHead />




</head>
<body  style="margin: 0px;">
  <div id="logo">
    <div id="logo2">
      <div class="title">
        HELIO
        <br>
        <i>Heliophysics <span class="blue">Integrated</span> Observatory</i>
      </div>
    </div>
  </div>
  <g:layoutBody />

</body>
</html>

