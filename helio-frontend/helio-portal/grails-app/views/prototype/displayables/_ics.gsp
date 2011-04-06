<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td>
          <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'ics.png')}" />
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Instrument Capabilities Service</h1>
          Label: <input style="margin-top:5px;" id="label" type="text"/>
        </td>
        <td>
     <div style="float:right;" class="controls custom_button" id="delete">X</div>
          <div style="float:right;display:none" class="controls custom_button" id="forward">Next</div>
          <div style="float:right;display:none" class="controls custom_button" id="counter" ></div>
          <div style="float:right;display:none" class="controls custom_button" id="backward" >Prev</div>
        </td>
      </tr>
    </table>

  </div>

  <div class="module ">
    <div class="header queryHeader viewerHeader">
      <h1>Query Form</h1>
    </div>
    <div class="content">

      <form id="actionViewerForm"  action="asyncQuery" method="POST">

        <g:render template="templates/dates" />
        <table>
          <tr><td style="text-align:right"> Catalogue:</td><td> <g:select class="catalogueSelector" name="extra" size="2" MULTIPLE="yes" value="observatory" from="${['observatory','instrument']}" />
        </td><td><!--img class="tooltipme" title="Use Ctrl/Cmd-Click to select multiple entries" style ="position:relative;right:110px;" height="20px" src="${resource(dir:'images/icons',file:'info.png')}" /-->
                 </td></tr>


      <tr><td></td><td>
      <g:hiddenField name="serviceName" value="ICS" />
      <input id="whereField" name="where" style="display:none" type="text"/>

      <input type="submit" class="custom_button" style="float:none;margin-right:50"/>
      <!--g:submitToRemote class="custom_button" before="fnBeforeQuery();" style="float:none;margin-right:50"  action="asyncQuery" onLoading="window.workspace.onLoading();" update="responseDivision" value="Search" onComplete="fnOnComplete();"/-->
      <g:submitToRemote class="custom_button" style="float:none;margin-right:50" action="asyncGetColumns"  onComplete="fnOnCompleteGetColumns();" update="icsResponse" value="Advanced Search" />
      </td></tr>
    </table>
        <div id="icsResponse" class="columnInputs" >
    </div>
  </form>
    </div>
  </div>

  <div id="displayableResult" class="displayable" style="display:block">
  </div>
</div>


