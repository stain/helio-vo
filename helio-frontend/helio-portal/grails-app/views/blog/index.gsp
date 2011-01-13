<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>HELIOOO title</title>

    <link rel="stylesheet" href="${ resource(dir:'css/humanity',file:'jquery-ui-1.8.5.custom.css"')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'menupro.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'anytime.css"')}" />
    
   
  
  <g:javascript src="jquery-1.4.2.min.js"/>
  <g:javascript src="jquery-ui-1.8.5.custom.min.js"/>
  <g:javascript src="jquery.tools.min.js"/>
  <g:javascript src="test.js"/>
  <g:javascript src="anytime.js"/>
 



  
  <link rel="shortcut icon" href="${resource(dir:'images/helio',file:'sun.ico')}" type="image/x-icon" />

  <style>
    #page-background {position:fixed; top:0; left:0; width:100%; height:100%;z-index:-1;}
    #container
    {
      position:relative;
      margin: 0 auto;
      width: 1000px;


    }

    #header
    {


      padding:0;
    }

    #header h1 { margin: 0; }


    #droppable-inner{
      width: auto;
      height: 468px;

      margin: 10px;
    }
    #history {
      float: left;
      display: inline;
      width: 882px;
      height:60px;
      background-color:#DDDDE9;


      margin: 0px 0 0px 30px;

      border:1px solid #464693;



    }
    /*#navigation ul
    {
      margin: 0;
      padding: 0;
    }

    #navigation ul li
    {
      list-style-type: none;
      display: inline;
    }

    #navigation li a
    {
      display: block;
      float: left;
      padding: 5px 10px;
      color: #fff;
      text-decoration: none;
      border-right: 1px solid #fff;
    }

    #navigation li a:hover { background: #383; }*/

    #content-container
    {
      float: left;
      width: 1000px;

    }

    #section-navigation
    {
      float: left;
      width: 80px;
      padding:0;
      margin: 0px;
      display: inline;




      background-color:#DDDDE9;



      border:1px solid #464693;
    }

    #section-navigation ul
    {
      margin: 0;
      padding: 0;
    }

    #section-navigation ul li
    {
      margin: 0 0 0 0;
      padding: 0;
      list-style-type: none;
    }
    .draggable { cursor:move; float: left; margin: 10px 10px 10px 15px }
    .draggable img{ width:50px;height:50px; }

    #content
    {

      background-color:white;



      border:1px solid #464693;
      float: left;
      width: 882px;
      height:490px;

      margin: 15px 0 5px 30px;
    }

    #content h2 { margin: 0; }

    #aside
    {
      float: right;
      width: 200px;
      padding: 20px 0;
      margin: 0 20px 0 0;
      display: inline;
    }

    #aside h3 { margin: 0; }

    #footer
    {
      clear: left;
      background: #ccc;
      text-align: right;
      padding: 20px;
      height: 1%;
    }

    #staticForms{

    }
    #staticForms h1{

      float:left;
      margin:0;
      padding:0;
      border-bottom:1px solid black;
      width:750px;
      font-size:2em;
      font-weight:normal;


    }
    #staticForms img{
      float:right;

    }
    #navigation{
      /* width:1000px;
       height:40px;
       margin:0;
      */
      position:relative;
      top:10px;
      /*background-color:#DDDDE9;*/
    }
    .tooltip {
      display:none;
      background:transparent url(./images/helio/black_arrow.png);
      font-size:12px;
      height:70px;
      width:160px;
      padding:25px;
      color:#fff;
    }

  </style>

</head>
<body>
  <div id="page-background"><img src="./images/icons/wallpaper-515385.jpg"  width="100%" height="100%" alt="Smile"></div>
  <div id="container">
    <div id="header">
      <img src="./images/icons/logoline.png"  height="70px" style="padding:0;margin:0" alt="Angry face" />
    </div>
    <div id="navigation">
      <!-- elements with tooltips -->
<g:render template="/webService/indexbar" />


    </div>

    <div id="content-container">

      <div id="section-navigation">
        <ul>
          <li style="border-bottom:1px solid black">Add Criteria</li>
          <li><div><img width="80px" src="./images/icons/toolbar/scroller_u.png" alt="Angry face" /></div></li>
          <li><div class="draggable"><img title="The tooltip text #4" src="./images/icons/toolbar/catalogue50.png" alt="Angry face" /></div></li>
          <li><div class="draggable"><img title="The tooltip text #3" src="./images/icons/toolbar/date50.png" alt="Angry face" /></div></li>
          <li><div class="draggable"><img title="The tooltip text #2" src="./images/icons/toolbar/timerange50.png" alt="Angry face" /></div></li>
          <li><div class="draggable"><img title="The tooltip text #1" src="./images/icons/toolbar/event50.png" alt="Angry face" /></div></li>
          
          <li><div class="draggable"><img src="./images/icons/toolbar/instrument50.png" alt="Angry face" /></div></li>
          <li><div class="draggable"><img src="./images/icons/toolbar/observableentity50.png" alt="Angry face" /></div></li>

          <li><div><img width="80px" src="./images/icons/toolbar/scroller_d.png" alt="Angry face" /></div></li>


        </ul>
      </div>
      <div id="history">
        <div><img style="float:left;display:inline;"height="60px" src="./images/icons/toolbar/scroller_l.png" alt="Angry face" /></div>
        <!--<div><img height="20px" src="./images/icons/toolbar/scroller_r.png" alt="Angry face" /></div>-->
      </div><!--history-->
      <div id="content">

        <div id="droppable-inner" class="ui-widget-header">
          <img class="displayable" width="250px" height="200px" src="./images/icons/drophere2.png" alt="Smile">
          <div id="displayableDate" class="displayable" style="display:none">
            <g:render template="/blog/date" />
          </div>
          <div id="displayableTime" class="displayable" style="display:none">
            <g:render template="/blog/time" />
          </div>
          <div id="displayableCatalogue" class="displayable" style="display:none">
            <g:render template="/blog/catalogue" />
          </div>
          <div id="displayableEvent" class="displayable" style="display:none">
            <g:render template="/blog/event" />
          </div>
          <div id="displayableInstrument" class="displayable" style="display:none">
            <g:render template="/blog/instrument" />
          </div>
          <div id="displayableObservable" class="displayable" style="display:none">
            <g:render template="/blog/observable" />
          </div>


        </div><!--droppable-inner-->
      </div>

      <div id="footer">
			Copyright © Site name, 20XX



      </div>
    </div>
  </div>


</body>
</html>
