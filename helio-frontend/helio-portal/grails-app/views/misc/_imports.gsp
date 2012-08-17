
<link rel="stylesheet" href="${resource(dir:'css',file:'jquery-ui-1.8.14.custom.css')}" />

<%-- old style css imports - to be removed --%>
<link rel="stylesheet" href="${resource(dir:'css',file:'navbar.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'prototype.css')}" />

<%-- new style css imports --%>
<link rel="stylesheet" href="${resource(dir:'css',file:'explorer.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'superfish.css')}" />

<g:javascript library="jquery" plugin="jquery"/><%--The main jquery library is managed by grails, this is done to enable the wiring of some extra functions--%>

<%--plugins--%>
<g:if env="prod">
  <g:javascript src="/plugins/jquery-ui-1.8.21.custom.min.js"/>
  <g:javascript src="/plugins/jquery.dataTables-1.9.3.min.js" /> <%--the data tables use for presentation of votables --%>
</g:if>
<g:else>
  <g:javascript src="/plugins/jquery-ui-1.8.21.custom.js"/>
  <g:javascript src="/plugins/jquery.dataTables-1.9.3.js" /> <%--the data tables use for presentation of votables --%>
</g:else>

<g:javascript src="/plugins/jquery-ui-timepicker-addon.js"/><%--Addon that expand the ui-datepicker into a time-datepicker --%>
<g:javascript src="/plugins/jquery.tools.min.js"/> <%--Support libraries for jquery with capabilities extended for tabs and tooltips--%>
<g:javascript src="/plugins/cookies.js"/> <%--support for cookies --%>
<g:javascript src="/plugins/jquery.form.js"/><%--used for file uploads--%>
<g:javascript src="/plugins/jquery.collapsible.min.js" /> <%-- enable collapsible sections --%>
<g:javascript src="/plugins/jquery.blockUI.js"/> <%--block the full ui to display help texts --%>
<g:javascript src="/plugins/moment-1.4.0.min.js"/> <%-- a date parsing and formatting utility --%>
<g:javascript src="/plugins/highcharts-2.2.3.min.js"/> <%-- a charting library --%>
<g:javascript src="/plugins/highcharts-exporting-2.2.3.min.js"/> <%-- an extension for highcharts --%>
<g:javascript src="/plugins/superfish-1.4.8.js"/> <%-- plugin to create a dropdown menu --%>

<%-- HELIO java scripts --%>
<g:javascript src="/helio/helio-main.js"/><%--basic initialization of the HELIO environment--%>
<g:javascript src="/helio/helio-model.js"/><%--the client-side model--%>
<g:javascript src="/helio/helio-dialog.js"/><%--functions related to dialogs--%>
<g:javascript src="/helio/helio-result.js"/><%--classes related to result display--%>
<g:javascript src="/helio/helio-task.js"/><%--functions related to tasks--%>
<g:javascript src="/helio/helio-datacart.js"/><%--the data cart--%>
<g:javascript src="/helio/helio-charts.js"/><%--the HEC charting functions--%>
<g:javascript src="/helio/helio-charts-config.js"/>
