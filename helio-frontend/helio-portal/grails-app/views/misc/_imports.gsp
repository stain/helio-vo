
<link rel="stylesheet" href="${resource(dir:'css',file:'jquery-ui-1.8.14.custom.css')}" />

<%-- old style css imports - to be removed --%>
<link rel="stylesheet" href="${resource(dir:'css',file:'navbar.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'prototype.css')}" />

<%-- new style css imports --%>
<link rel="stylesheet" href="${resource(dir:'css',file:'explorer.css')}" />

<g:javascript library="jquery" plugin="jquery"/><%--The main jquery library is managed by grails, this is done to enable the wiring of some functions--%>

<%--plugins--%>
<g:javascript src="/plugins/jquery-ui-1.8.11.custom.min.js"/>
<g:javascript src="/plugins/jquery-ui-timepicker-addon.js"/><%--Addon that expand the ui-datepicker into a time-datepicker --%>
<g:javascript src="/plugins/jquery.tools.min.js"/> <%--Support libraries for jquery with capabilities extended for tabs and tooltips--%>
<g:javascript src="/plugins/cookies.js"/> <%--support for cookies --%>
<g:javascript src="/plugins/jquery.form.js"/><%--used for file uploads--%>
<g:javascript src="/plugins/jquery.dataTables-1.9.1.min.js" /> <%--the data tables use for presentation of votables --%>
<g:javascript src="/plugins/jquery.collapsible.min.js" /> <%-- enable collapsible sections --%>
<g:javascript src="/plugins/jquery.blockUI.js"/> <%--block the full ui to display help texts --%>
<g:javascript src="/plugins/moment-1.4.0.min.js"/> <%-- a date parsing and formatting utility --%>

<%-- HELIO java scripts --%>
<g:javascript src="/helio/helio-main.js"/><%--basic initialization of the HELIO environment--%>
<g:javascript src="/helio/helio-model.js"/><%--the client-side model--%>
<g:javascript src="/helio/helio-dialog.js"/><%--functions related to dialogs--%>
<g:javascript src="/helio/helio-result.js"/><%--classes related to result display--%>
<g:javascript src="/helio/helio-task.js"/><%--functions related to tasks--%>
<g:javascript src="/helio/helio-datacart.js"/><%--the data cart--%>
