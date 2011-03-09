<div id="staticForms">
  <div id="formHeader">
    <img style ="float:left;position:relative;top:10px;left:10px" height="30px" src="${resource(dir:'images/icons/toolbar',file:'ics.png')}" />
    <h1 style ="float:left;position:relative;left:15px" >Instrument Capabilities Service</h1>
  </div>
  <div style="clear:both;padding:50px 0 0 80px;">
    
    <g:render template="templates/controls" />
<g:form  controller="prototype">
    
      <g:render template="templates/dates" />
      <table>
   <tr><td style="text-align:right"> Catalogue:</td><td> <g:select class="catalogueSelector" name="extra" size="2" MULTIPLE="yes" value="observatory" from="${['observatory','instrument']}" />
  </td><td><img class="tooltipme" title="Use Ctrl/Cmd-Click to select multiple entries" style ="position:relative;right:110px;" height="20px" src="${resource(dir:'images/icons',file:'info.png')}" /></td></tr>


 <tr><td></td><td>
<g:hiddenField name="serviceName" value="ICS" />
<input id="whereField" name="where" style="display:none" type="text"/>

<g:submitToRemote before="fnBeforeQuery();" style="float:none;margin-right:50"  action="asyncQuery" onLoading="window.workspace.onLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/>
    <g:submitToRemote style="float:none;margin-right:50" action="asyncGetColumns"  onComplete="fnOnCompleteGetColumns();" update="icsResponse" value="Advanced Search" />
     </td></tr>
    </table>
</g:form>
  <div id="icsResponse" class="columnInputs" >
  </div>
  </div>





<div id="displayableResult" class="displayable" style="display:block">

</div>



</div>
