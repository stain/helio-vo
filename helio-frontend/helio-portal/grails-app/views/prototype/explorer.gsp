<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->
<%@ page import="ch.i4ds.helio.frontend.model.*" %>
<%@ page import="ch.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Heliophysics Integrated Observatory</title>

    <link rel="stylesheet" href="${ resource(dir:'css/humanity',file:'jquery-ui-1.8.5.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'navbar.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'anytime.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'prototype.css')}" />
    
   
      <link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />
    
  <g:javascript src="jquery-1.4.2.min.js"/>
  <g:javascript src="jquery-ui-1.8.5.custom.min.js"/>
  <g:javascript src="jquery.tools.min.js"/>
  <g:javascript src="test.js"/>
  <g:javascript src="anytime.js"/>
  <g:javascript src="jquery.dataTables.js" />
 



  
  <link rel="shortcut icon" href="${resource(dir:'images/helio',file:'sun.ico')}" type="image/x-icon" />


</head>
<body>
  <div id="page-background">
    <img src="${resource(dir:'images/background',file:'bgpicture.png')}"  width="100%" height="170px" alt="background">
    <!--div style="width:100%;height:150px;background-color:orange"></div-->
  </div>
  <div id="logo">
    <img src="${resource(dir:'images/background',file:'helio_transp.png')}"  width="350px" height="120px" />
    <img id="line" src="${resource(dir:'images/background',file:'line_transp.png')}"   height="120px" width="50%" />
  </div>
   <div >
      <!-- elements with tooltips -->
		<g:render template="navbar" />


    </div>
  <div id="container" >
  
   

    <div id="content-container">

      <div style="display:block;" id="section-navigation">
        <ul>
          <li style="border-bottom:1px solid black">Add Criteria</li>
          <li><div><img width="80px" src="${resource(dir:'images/icons/toolbar',file:'scroller_u.png')}" alt="Angry face" /></div></li>
          <li><div class="draggable"><img title="Event Search" src="${resource(dir:'images/icons/toolbar',file:'event.png')}" alt="HEC" /></div></li>
          <li><div class="draggable"><img title="Instrument Capabilities" src="${resource(dir:'images/icons/toolbar',file:'ics.png')}" alt="ICS" /></div></li>
          <li><div class="draggable"><img title="Instrument Locator" src="${resource(dir:'images/icons/toolbar',file:'ils.png')}" alt="ILS" /></div></li>
          <li><div class="draggable"><img title="DPAS" src="${resource(dir:'images/icons/toolbar',file:'dpas.png')}" alt="DPAS" /></div></li>
          <li><div class="draggable"><img title="Filter" src="${resource(dir:'images/icons/toolbar',file:'filter.png')}" alt="filter" /></div></li>
          <li><div class="draggable"><img title="User Space" src="${resource(dir:'images/icons/toolbar',file:'userspace.png')}" alt="userspace" /></div></li>
          
          
          

          <li><div><img width="80px" src="${resource(dir:'images/icons/toolbar',file:'scroller_d.png')}" alt="Angry face" /></div></li>


        </ul>
      </div>
      <div style="display:block;" id="history">
        <div><img style="float:left;display:inline;"height="60px" src="${resource(dir:'images/icons/toolbar',file:'scroller_l.png')}" alt="Angry face" /></div>
        <!--<div><img height="20px" src="./images/icons/toolbar/scroller_r.png" alt="Angry face" /></div>-->
      </div><!--history-->
      <div style="display:block;" id="content">
        <div id="droppable-inner" class="ui-widget-header">
          
          <g:if test="${result}" >
            <div id="displayableResult" class="displayable" style="display:block">
            <g:render template="result" />
          </div>
          </g:if>
          <g:else>
            <img class="displayable" width="250px" height="200px" src="../images/icons/drophere2.png" alt="Smile">
            </g:else>
       
          <div id="displayableDate" class="displayable" style="display:none">
            <g:render template="date" />
          </div>
          <div id="displayableTime" class="displayable" style="display:none">
            <g:render template="time" />
          </div>
          <div id="displayableCatalogue" class="displayable" style="display:none">
            <g:render template="catalogue" />
          </div>
          <div id="displayableICS" class="displayable" style="display:none">
            <g:render template="ics" />
          </div>
          <div id="displayableILS" class="displayable" style="display:none">
            <g:render template="ics" />
          </div>
          <div id="displayableDPAS" class="displayable" style="display:none">
            <g:render template="dpas" />
          </div>


        </div><!--droppable-inner-->
      </div>

      <div id="footer">
			
<g:remoteFunction action="action1"/>


      </div>
    </div>
  </div>


</body>
</html>
