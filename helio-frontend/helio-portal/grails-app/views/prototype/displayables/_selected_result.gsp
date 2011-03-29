<div id="staticForms">
  <div id="formHeader" style="float:none;">
    <img style ="float:left;position:relative;top:10px;left:10px" height="30px" src="${resource(dir:'images/icons/toolbar',file:'circle_obs.png')}" />
    <h1 style ="float:left;position:relative;left:15px" > Result Selection </h1>
    <g:render template="templates/controls_reduced" />
    
  </div>
  
  <table style="clear:both;width:850px;margin-top:30px;margin-bottom: 30px">
    
    <tr>
      <td class="row-borders" id="time-row">
        <img style="margin-left:100px;height:40px;padding-bottom:20px " src="${resource(dir:'images/icons/toolbar',file:'circle_time.png')}" />
       <center> <h2>Time Selection</h2></center>
      <table id="times-table" style="width:100%;text-align:center">
          <tr >
            <td>
              Time Start:
            </td>
            <td>.</td>
            <td>
              Time End:
            </td>
          </tr>
        </table>
      </td>
      <td class="row-borders" id="instrument-row">
        <img style="margin-left:100px;height:40px;padding-bottom:20px" src="${resource(dir:'images/icons/toolbar',file:'circle_inst.png')}" />
        <center><h2>Instrument Selection</h2></center>
       
      </td>
      <!--td class="row-borders" id="observatory-row" style="border-right:1px solid black">
        <img style="margin-left:100px;height:40px;padding-bottom:20px" src="${resource(dir:'images/icons/toolbar',file:'circle_obs.png')}" />
        <center><h2>Observatory Selection</h2></center>
      </td-->
    </tr>
  </table>
  <div id="voTables" style="clear: both; padding: 20px 5pt 20pt 5px;">
  <g:form target="_blank" controller="prototype">
    <g:actionSubmit style="padding:3px;float:left"  action="downloadPartialVOTable" value="Save as VOTable" name="download"/>
          <input id="indexes" type="hidden" value="" name="indexes" />
  </g:form>
  </div>

  <div id="displayableResult" class="displayable" style="display:block">

</div>
 


</div>