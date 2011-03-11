<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page import="ch.i4ds.helio.frontend.model.*" %>
<%@ page import="ch.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Heliophysics Integrated Observatory</title>


    <link rel="stylesheet" href="${resource(dir:'css',file:'jquery-ui-1.8.2.custom.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'navbar.css')}" />
    
    <link rel="stylesheet" href="${resource(dir:'css',file:'prototype.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />

  <g:javascript library="jquery" plugin="jquery"/>
  <g:javascript src="jquery-ui-1.8.5.custom.min.js"/>
  <g:javascript src="jquery.tools.min.js"/>
  <g:javascript src="/helio/helio-prototype.js"/>
  <g:javascript src="/helio/ActionViewer.js"/>
  <g:javascript src="/helio/UploadViewer.js"/>
  <g:javascript src="/helio/ResultViewer.js"/>
  <g:javascript src="/helio/History.js"/>
  <g:javascript src="/helio/Workspace.js"/>
  <g:javascript src="/helio/HelioElement.js"/>
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
    <!--img src="${resource(dir:'images/background',file:'helio_transp.png')}"  width="350px" height="120px" />
    <img id="line" src="${resource(dir:'images/background',file:'line_transp.png')}" height="120px"  /-->
    <!--img src="${resource(dir:'images/helio',file:'helio_logo.jpg')}" width="200px" height="100px"  /-->
    <img style="float:left" src="${resource(dir:'images/background',file:'logo_helio.png')}"   />
    <img style="position:relative;top:-10px;float:right" src="${resource(dir:'images/background',file:'logo7.png')}"   />
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
        
          <div class="draggable"><img title="Query heliophysics event catalogues" src="${resource(dir:'images/icons/toolbar',file:'hec.png')}" alt="HEC" /></div>
          <div class="draggable"><img title="Query capabilities of instruments" src="${resource(dir:'images/icons/toolbar',file:'ics.png')}" alt="ICS" /></div>
          <div class="draggable"><img title="Query instrument location" src="${resource(dir:'images/icons/toolbar',file:'ils.png')}" alt="ILS" /></div>
          <div class="draggable"><img title="Query for instrument data" src="${resource(dir:'images/icons/toolbar',file:'dpas.png')}" alt="DPAS" /></div>
          <div class="draggable"><img title="Upload VoTable" src="${resource(dir:'images/icons/toolbar',file:'upload_vot.png')}" alt="upload" /></div>
          
        
      </div>
      <div>Helio</div>

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
        <div style="border:none" id="droppable-inner" class="ui-widget-header">
           <g:render template="templates/displayable-content" />
        </div>
      </div>

      <div id="responseDivision" style="width:858px;"></div>
    </div>
  </div>



</body>
</html>
