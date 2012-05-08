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
    <g:render template="/misc/imports" />
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
      <g:render template="/misc/navbar" />
    </div>

    <!-- Body Container -->
    <div id="container" >
      <g:render template="/misc/taskSelectionArea" />
    
      <div id="content-container">
        <%-- the data cart --%>
        <div class="candybox" id="datacart_container">
          <div>Data Cart</div>
          <div id="datacart_scrollarea">
            <div id="datacart_content"></div>
          </div>
          <div id="datacart_slider"></div>
        </div>
    
        <!-- Content -->
        <div id="content" >
            <g:render template="/misc/splash" />
        </div>
      </div>
    </div>
    
    <div style="display:none">
      <div id="loading_form" style="display:none">
        <div><h3 style="margin:30px;">Please wait </h3>
          <img style="margin:30px;" src="${resource(dir:'images/helio/',file:'load.gif')}" alt="Loading..." /></div>
        <div style="margin:30px;" id="loading_form_cancel_button">Cancel</div>
      </div>
      <div id="help_overlay" style="padding:30px;text-align:left;display:none">
        <h3 style="text-align: left;margin:10px"></h3>
        <p></p>
      </div>
    </div>
    <!-- placeholder to attach temporary dialog widgets to the dom -->
    <div id="dialog_placeholder" style="display:none"></div>
  </body>
</html>