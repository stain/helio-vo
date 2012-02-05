
<link rel="stylesheet" href="${resource(dir:'css',file:'jquery-ui-1.8.14.custom.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'jquery.selectBox.css')}" />

<%-- old style css imports - to be removed --%>
<link rel="stylesheet" href="${resource(dir:'css',file:'demo.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'navbar.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'demo_table.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'demo_page.css')}" />
<link rel="stylesheet" href="${resource(dir:'css',file:'prototype.css')}" />

<%-- new style css imports --%>
<link rel="stylesheet" href="${resource(dir:'css',file:'explorer.css')}" />


<g:javascript library="jquery" plugin="jquery"/><%--The main jquery library is managed by grails, this is done to enable the wiring of some functions--%>
<g:javascript src="jquery-ui-1.8.11.custom.min.js"/>

<g:javascript src="jquery-ui-timepicker-addon.js"/><%--Addon that expand the ui-datepicker into a time-datepicker --%>
<g:javascript src="jquery.tools.min.js"/> <%--Support libraries for jquery with capabilities extended for tabs and tooltips--%>

<g:javascript src="/helio/helio-prototype.js"/><%--Onload initialization--%>

<g:javascript src="/helio/helio-main.js"/><%--basic initialization of the HELIO environment--%>
<g:javascript src="/helio/helio-model.js"/><%--the client-side model--%>
<g:javascript src="/helio/helio-dialog.js"/><%--functions related to dialogs--%>
<g:javascript src="/helio/helio-result.js"/><%--classes related to result display--%>
<g:javascript src="/helio/helio-task.js"/><%--functions related to tasks--%>
<g:javascript src="/helio/helio-datacart.js"/><%--the data cart--%>

<g:javascript src="/helio/ActionViewer.js"/>
<g:javascript src="/helio/HelioAjax.js"/>
<g:javascript src="/helio/History.js"/>
<g:javascript src="/helio/Workspace.js"/>
<g:javascript src="/helio/StaticUtilities.js"/>

<%--plugins--%>
<g:javascript src="/plugins/cookies.js"/>
<g:javascript src="jquery.form.js"/><%--used for file uploads--%>
<g:javascript src="highcharts.js"/>
<g:javascript src="themes/grid.js"/><%--highcharts theme--%>
<g:javascript src="jquery.dataTables-1.9.0.min.js" />
<g:javascript src="/plugins/jquery.collapsible.js" />
<g:javascript src="/plugins/jquery.selectBox.min.js" />
<g:javascript src="/plugins/jquery.blockUI.js"/>