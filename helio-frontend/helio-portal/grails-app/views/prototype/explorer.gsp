<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

<%@ page import="ch.i4ds.helio.frontend.model.*" %>
<%@ page import="ch.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
    <META HTTP-EQUIV="EXPIRES" CONTENT="0">
    <title>HELIO Front End</title>
    <g:render template="imports" />
    <link rel="shortcut icon" href="${resource(dir:'images/helio',file:'sun.ico')}" type="image/x-icon" />
  </head>

  <body>
    <input type="hidden" value="${HUID}" id="HUID"/>
    <!-- Background Elements -->
    <div id="page-background"></div>

    <!-- Logo Elements -->
    <div id="logo">
      <table style="width: 100%;position:relative;top:-18px;">
        <tr>
          <td><img style="float:left;z-index:100;" src="${resource(dir:'images/background',file:'header_logo.png')}"   /></td>
          <td><center><img style="z-index:100;" src="${resource(dir:'images/background',file:'header_text.png')}"   /></center></td>
        <td><img style="position:relative;top:-1px;float:right;height: 120px" src="${resource(dir:'images/background',file:'glowlogo.png')}"  /></td>
        </tr>
      </table>
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
      <div id="tabs">
        <ul>
          <li><a href="#tabs-2">Services</a></li>
          <li><a href="#tabs-3">Advanced</a></li>
          <li><a href="#tabs-4">Beta</a></li>
          <li><a href="#tabs-5">Administration</a></li>
        </ul>
        <div id="tabs-2">
          <table>
            <tr>
              <td>
                <div style="display:block" class="menu_item custom_button"  id="task_searchEvents">Search Events</div>
              </td>
              <td>
                <div style="display:block" class="menu_item custom_button"  id="task_searchInstCap">Find instruments by capability</div>
              </td>
              <td>
                <div style="display:block" class="menu_item custom_button"  id="task_searchInstLoc">Locate planets/instruments by time</div>
              </td>
              <td>
                <div style="display:block" class="menu_item custom_button"  id="task_searchData">Search Data</div>
              </td>
            </tr>
          </table>
        </div>
        <div id="tabs-3">
          <table>
            <tr>
              <td>
                <div style="display:block" class="menu_item custom_button"  id="task_upload">VOtable Upload</div>
              </td>
              <td>
                <div>
                  <div id="task_plotservice" title="Select a Plot">Plot Data</div><div
                  id="task_plotservice_select">Select a Plot</div>
                </div>
                <ul id="task_plotservice_menu" class="menu" style="display: none;">
                  <li id="task_plotservice_goes">GOES timeline plot</li>
                  <li id="task_plotservice_flare">Flare plot</li>
                  <li id="task_plotservice_parker">Simple Parker Spiral</li>
                  <li id="task_plotservice_ace">ACE timeline</li>
                  <li id="task_plotservice_sta">STEREO-A timeline</li>
                  <li id="task_plotservice_stb">STEREO-B timeline</li>
                  <li id="task_plotservice_ulysses">Ulysses timeline</li>
                  <li id="task_plotservice_wind">WIND timeline</li>
                </ul>
              </td>
              <g:if env="development">              
                <td>
                  <div>
                    <div id="task_propagationmodel" title="Select a Propagation Model">Propagation Model</div><div
                    id="task_propagationmodel_select">Select a Propagation Model</div>
                  </div>
                  <ul id="task_propagationmodel_menu" class="menu" style="display: none;">
                      <li id="task_propagationmodel_pm_cme_fw" >Coronal Mass Ejections (CME) Forward PM</li>
                      <li id="task_propagationmodel_pm_cme_back" >Coronal Mass Ejections (CME) Backward PM</li>
                      <li id="task_propagationmodel_pm_cir_fw" >Co-rotate Interaction Region (CIR) Forward PM</li>
                      <li id="task_propagationmodel_pm_cir_back" >Co-rotate Interaction Region (CIR) Backward PM</li>
                      <li id="task_propagationmodel_pm_sep_fw" >Solar Energetic Particles (SEP) Forward PM</li>
                      <li id="task_propagationmodel_pm_sep_back" >Solar Energetic Particles (SEP) Backward PM</li>
                  </ul>
                </td>
                <td>
                  <div style="display:block" class="custom_button"  id="task_upload2">Upload VOTable (beta)</div>
                </td>
              </g:if>
              <g:if env="development" test="${false}">
                <td>
                  <div>
                    <div id="task_taverna" title="Select a Taverna Workflow">Workflows</div><div
                    id="task_propagationmodel_select">Select a Taverna Workflow</div>
                  </div>
                  <ul id="task_taverna_menu" class="menu" style="display: none;">
                      <li id="task_tav_2283">Combine two event lists</li>
                  </ul>
                </td>
              </g:if>
            </tr>
          </table>
        </div>
        <div id="tabs-4">
          <table>
            <tr>
              <td>
                <div style="display:block" class="menu_item custom_button"  id="task_datamining">In-situ Data Mining (beta)</div>
              </td>            
            </tr>
          </table>
        </div>
        <div id="tabs-5">
          <table>
            <tr>
              <td>
                <div style="display:block" class="menu3_item custom_button reset_session" >Reset Session</div>
              </td>
            </tr>
          </table>
        </div>
      </div>
    
      <div id="content-container">
        <div class="candybox" id="history">
          <div>Data Cart</div>
          <div id="historyScrollWidth">
            <div id="historyContent"></div>
          </div>
          <div id="content-slider"></div>
        </div> <!-- History -->
        
        <%-- the data cart %>
        <div class="candybox" id="datacart_container">
          <div>Data Cart</div>
          <div id="datacart_scrollarea">
            <div id="datacart_content"></div>
          </div>
          <div id="datacart_slider"></div>
        </div --%>
    
        <!-- Content -->
        <div id="content" >
          <!-- 1st level droppable -->
          <div id="droppable-inner" class="candybox">
            <g:render template="templates/displayable_content" />
          </div>
        </div>
        <div id="responseDivision" ></div>
      </div>
    </div>
    
    <div style="display:none">
      <div id="loading_form" style="display:none">
        <div><h3 style="margin:30px;">Please wait </h3>
          <img style="margin:30px;" src="${resource(dir:'images/helio/',file:'load.gif')}" alt="Loading..." /></div>
        <div style="margin:30px;" id="loading_form_cancel_button" class="custom_button">Cancel</div>
      </div>
      <div style="padding:30px;text-align: left" id="help_overlay" style="display:none">
        <H3 style="text-align: left;margin:10px"></H3>
        <p></p>
      </div>
    </div>
    <!-- placeholders to attach temporary ui widgets -->
    <div id="dialog_placeholder" style="display:none"></div>
  </body>
</html>