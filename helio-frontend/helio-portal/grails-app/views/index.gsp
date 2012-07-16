<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>HELIO Front End</title>
    <meta name="layout" content="main" />
    <g:render template="/misc/imports" />
  </head>

  <body>
    <!-- Body Container -->
    <div id="container" >
      <!--  >g:render template="/misc/taskSelectionArea" /-->
    
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