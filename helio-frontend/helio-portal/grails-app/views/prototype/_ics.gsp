<div id="staticForms">
  <div id="formHeader">
    <img style ="float:left;position:relative;top:10px;left:10px" height="30px" src="${resource(dir:'images/icons/toolbar',file:'ics.png')}" />
    <h1 style ="float:left;position:relative;left:15px" > Instrument Capabilities</h1>
  </div>
  <div style="clear:both;padding:50px 0 0 80px;">
<g:form controller="prototype">
    <table>

    <tr><td>Start Date: <input type="text" id="minDate" name="minDate" value="2003-01-01"/> </td></tr>
    <tr><td>Start Time: <input type="text" name="minTime" id="minTime" value="00:00" /></td></tr>
    <tr><td>End Date: <input type="text" id="maxDate" name="maxDate" value="2003-01-02"/> </td></tr>
    <tr><td>End Time: <input type="text" name="maxTime" id="maxTime" value="00:00" /> </td></tr>


   <tr><td> Catalogue:
   <g:select name="extra" from="${['observatory','instrument']}" />
  </td></tr>


 <tr><td>
<g:hiddenField name="serviceName" value="ICS" />
    <g:actionSubmit style="float:none;margin-right:50" action="explorer" value="Search" />

     </td></tr>
    </table>
  </g:form>
  </div>


</div>
