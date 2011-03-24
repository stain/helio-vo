<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page import="ch.i4ds.helio.frontend.model.*" %>
<%@ page import="ch.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Helio: FrondEnd</title>


    <link rel="stylesheet" href="${resource(dir:'css',file:'jquery-ui-1.8.11.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'navbar.css')}" />
    
    <link rel="stylesheet" href="${resource(dir:'css',file:'prototype.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />




  <g:javascript library="jquery" plugin="jquery"/>
  <g:javascript src="jquery-ui-1.8.11.custom.min.js"/>
  
  <g:javascript src="jquery.tools.min.js"/>
  <g:javascript src="/helio/helio-prototype.js"/>
  <g:javascript src="/helio/ActionViewer.js"/>
  <g:javascript src="/helio/UploadViewer.js"/>
  <g:javascript src="/helio/ResultViewer.js"/>
  <g:javascript src="/helio/History.js"/>
  <g:javascript src="/helio/Workspace.js"/>
  <g:javascript src="/helio/HelioElement.js"/>
  <g:javascript src="/helio/cookies.js"/>
  <g:javascript src="jquery.form.js"/>

  <g:javascript src="jquery.dataTables.js" />



  <link rel="shortcut icon" href="${resource(dir:'images/helio',file:'sun.ico')}" type="image/x-icon" />


</head>

<body>
  <!-- Background Elements -->
  <div id="page-background">
    <!--img src="${resource(dir:'images/background',file:'bg_flat.jpg')}"   style="width:100%;height:150px"  alt="background"-->
    <!--img src="${resource(dir:'images/background',file:'iconbg.png')}"   style="width:100%;height:125px"  alt="background"-->


    <!--div style="width:100%;height:150px;background-color:orange"></div-->
  </div>

  <!-- Logo Elements -->
  <div id="logo">
    <!--img src="${resource(dir:'images/background',file:'header_lines.png')}"  width="350px" height="120px" />
    <img id="line" src="${resource(dir:'images/background',file:'line_transp.png')}" height="120px"  /-->
    <!--img src="${resource(dir:'images/helio',file:'helio_logo.jpg')}" width="200px" height="100px"  /-->
    <img style="float:left;z-index:100;" src="${resource(dir:'images/background',file:'header_logo.png')}"   />
    <img style="float:left;z-index:100;" src="${resource(dir:'images/background',file:'header_text.png')}"   />
    <img style="position:relative;top:-10px;float:right;height: 90px" src="${resource(dir:'images/background',file:'logo7.png')}"  />
  </div>

  <!-- Navigation Bar -->
  <div >
    <!-- elements with tooltips -->
    <g:render template="navbar" />

  </div>

  <!-- Body Container -->
  <div id="container" >

    <!-- Hidden division holding selection results :: need to rework -->
    <div id="testdiv" class="displayable" style="display:none">
      Selection
      <div style="margin-top:4px;margin-bottom:4px;cursor:pointer;padding:4px;background-color:black;color:white;border:1px solid #464693;" id="saveButton">Save Results</div>
    </div>

    <!-- Content container -->
    <div id="content-container">


      <!-- ToolBar -->
      
      <div style="display:block;clear:both" id="section-navigation">
        
          <div class="draggable"><img title="<b>HELIO Event Catalog</b><br/>Search for events in several lists." src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" id="hec_draggable" alt="HELIO Event Catalog" /></div>
          <div class="draggable"><img title="<b>Instrument Capability Serivce</b><br/>Search for instruments with certain capabilities." src="${resource(dir:'images/icons/toolbar',file:'ics.png')}" alt="Instrument Capabilties Service" /></div>
          <div class="draggable"><img title="<b>Instrument Location Service</b><br/>Search if an instrument has been in the<br/> right place for a certain observation." src="${resource(dir:'images/icons/toolbar',file:'ils.png')}" alt="Instrument Location Service" /></div>
          <div class="draggable"><img title="<b>Data Provider Access Service</b><br/>Get access to the actual observations" src="${resource(dir:'images/icons/toolbar',file:'dpas.png')}" alt="Data Provide Access Service" /></div>
          <div class="draggable"><img title="<b>Upload external data as VOTable</b>" src="${resource(dir:'images/icons/toolbar',file:'upload_vot.png')}" alt="Upload a VOTable" /></div>
      </div>
      
      <div style="color:white">Helio</div>

       <!-- History  -->
      <div style="display:block;" id="history">
        <!--div><img id="scroller_left" style="float:left;display:inline;" height="60px" src="${resource(dir:'images/icons/toolbar',file:'scroller_l.png')}" alt="Angry face" /></div-->
        <div id="historyContent"></div>

        <!--select onchange="fnOnChangeHistoryFilterSelect(this);" style="margin-top:15px;float:right"><option selected="yes" >all</option><option>results</option><option>selections</option><option>actions</option></select-->
        <!--div><img id="scroller_right" style="float:right;display:inline;"height="60px" src="${resource(dir:'images/icons/toolbar',file:'scroller_r.png')}" alt="Angry face" /></div-->
        <!--div><img style="float:right;display:inline;margin-top:15px"height="30px" id="clearButton" src="${resource(dir:'images/icons/toolbar',file:'delet40.png')}" alt="Angry face" /></div-->


      </div> <!-- History -->
      

      <!-- Content -->
      <div style="display:block;" id="content">
        <!-- 1st level droppable -->
        <div style="border:none" id="droppable-inner" >
           <g:render template="templates/displayable-content" />
        </div>
      </div>

      <div id="responseDivision" style="width:858px;"></div>

      <div id="collapsable" style="border:1px solid black;width: 100%;clear:both;">
        <h3><a class="header" href="#">First header</a></h3>
    <div>First content</div>
    <h3><a href="#">Second header</a></h3>
    <div>Second content</div>




      </div>

   

    </div>
  </div>



</body>
</html>
