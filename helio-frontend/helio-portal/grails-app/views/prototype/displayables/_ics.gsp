<div id="staticForms">
  <div id="formHeader">
    <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'ics.png')}" />
    <h1 >Instrument Capabilities Service</h1>
    <g:render template="templates/controls" />
    <div style="clear:both">1</div>
  </div>

  <div class="module ">
    <div class="header">
      <h1>Query Form</h1>
    </div>
    <div class="content">

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
</div>
</div>

<div class="module ">
  <div class="header">
    <h1>Aditional Parameters</h1>
  </div>
  <div class="content">
    <div id="icsResponse" class="columnInputs" >
    </div>
  </div>
</div>

<div class="module ">
  <div class="header">
    <h1>Result</h1>
  </div>
  <div class="content">

    <div id="displayableResult" class="displayable" style="display:block">

    </div>
  </div>
</div>



</div>


