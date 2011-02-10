<div id="staticForms">
  <div id="formHeader">
    <img style ="float:left;position:relative;top:10px;left:10px" height="30px" src="${resource(dir:'images/icons/toolbar',file:'ils.png')}" />
    <h1 style ="float:left;position:relative;left:15px" >Instrument Location Service</h1>
  </div>
  <div style="clear:both;padding:50px 0 0 80px;">
<g:form controller="prototype">
    

<g:render template="dates" />
<table>
      <tr><td style="text-align:right"> Catalogue:</td><td> <g:select class="catalogueSelector"  name="extra"  from="${['trajectories']}" />
  </td></tr>


 <tr><td></td><td>
<g:hiddenField name="serviceName" value="ILS" />
<input id="whereField" name="where" style="display:none" type="text"/>

   <g:submitToRemote style="float:none;margin-right:50"  action="asyncQuery" onLoading="fnOnLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/>
    <g:submitToRemote style="float:none;margin-right:50" action="asyncGetColumns"  onComplete="fnOnCompleteGetColumns();" update="ilsResponse" value="Advanced Search" />
     </td></tr>
    </table>
</g:form>
  <div id="ilsResponse" class="columnInputs" >
  </div>
  </div>


</div>
