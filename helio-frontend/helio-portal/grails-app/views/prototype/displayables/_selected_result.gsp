<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td width="60">
          <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'circle_obs.png')}" alt="image missing"/>
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px"> Result Viewer </h1>
          Label: <input style="margin-top:5px;" id="label" type="text"/>
        </td>
        <td>
          <div style="float:right;" class="controls" id="delete">X</div>
        </td>
      </tr>
    </table>

  </div>

  <div class="module ">
    <div class="header queryHeader viewerHeader">
      <h1>Parameter Extraction</h1>
    </div>
    <div class="content">
      <table width="100%">
        <col width="*">
        <col width="250">
        <tbody>
          <tr>
            <td>

      <table style="clear:both;margin-top:30px;margin-bottom: 30px;width:500px;margin-left:170px">
        <tr>
          <td class="row-borders" id="time-row" style="display:block">

            <div style="margin-left:220px;height:40px;padding-bottom:20px "><!--input id="time-row-check" type="checkbox"/--><img  src="${resource(dir:'images/icons/toolbar',file:'circle_time.png')}"/></div>
            <center> <h2>Time Selection</h2></center>
            <table id="times-table" style="width:100%;text-align:left;">
              <tr >
                <td>
                  Time Start:
                </td>
                <td></td>
                <td>
                  Time End:
                </td>
              </tr>
            </table>

          </td>
        </tr>
        <tr style="border-top:1px solid black">
          <td class="row-borders" id="instrument-row" style="display:none">
            <div style="margin-left:200px;height:40px;padding-bottom:20px "><!--input id="inst-row-check" type="checkbox"/--><img  src="${resource(dir:'images/icons/toolbar',file:'circle_inst.png')}"/></div>
            <center><h2>Instrument Selection</h2></center>

          </td>
          <!--td class="row-borders" id="observatory-row" style="border-right:1px solid black">
            <img style="margin-left:100px;height:40px;padding-bottom:20px" src="${resource(dir:'images/icons/toolbar',file:'circle_obs.png')}" />
            <center><h2>Observatory Selection</h2></center>
          </td-->
        </tr>
      </table>
            </td>
            <td valign="top">
              <div class="message"><b>Step 1</b><br/>
                Select the data products you want to use as input of another service. Your selection will be stored in the left sidebar.<br/>
                If this section is empty you cannot reuse any data product from the table below, but you still can download the results.
              </div>
              <div class="message"><b>Step 2</b><br/>
                Select a service from the top bar and drag and drop the data product from the left side to this service.
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
  <div class="module ">
    <div class="header queryHeader viewerHeader">
      <h1>Result Selection</h1>
    </div>
    <div class="content">
      <div id="voTables" style="clear: both;">
        <g:form target="_blank" controller="prototype">
          <g:actionSubmit style="padding:3px;float:left"  action="downloadPartialVOTable" value="Save as VOTable" name="download"/>
          <input id="indexes" type="hidden" value="" name="indexes" />
        </g:form>
      </div>


      <div id="displayableResult" class="displayable" style="display:block">

      </div>




    </div>
  </div>
</div>

