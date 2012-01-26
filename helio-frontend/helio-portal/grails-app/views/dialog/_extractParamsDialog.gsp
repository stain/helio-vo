<%--Dialog to extract parameters
The parameters are read from a VoTable model and the number of the selected table.  
Expected parameters:
 * Tree votableModel as created by VoTableService. 
 * int tableNr: number of the table to extract params from.
 --%><div class="input-dialog" id="extractDialog" style="display:none">
  <table class="dialog_table" >
    <tr valign="top" align="left" height="">
      <td>
        <div class="message" style="display:inline; float:right; margin:2px 0px; padding:3px">
          Choose the type of parameter you are interested in.
        </div>
      </td>
    </tr>
    <tr valign="top" align="left" height="*">
      <td valign="top" width="*">
        <div id="tabs">
          <ul>
            <li><a href="#extract_date"><img style="margin:5px" src="${resource(dir:'images/helio',file:'circle_time.png')}" /></a></li>
            <li><a href="#extract_inst"><img style="margin:5px" src="${resource(dir:'images/helio',file:'circle_inst.png')}" /></a></li>
            <li><a href="#extract_obs" ><img style="margin:5px" src="${resource(dir:'images/helio',file:'circle_obs.png')}" /></a></li>
            <li><a href="#extract_paramset" ><img style="margin:5px" src="${resource(dir:'images/helio',file:'circle_block.png')}" /></a></li>
          </ul>
          <g:each var="paramType" in="${votableModel.paramTypes}" status="i">
            <div id="${paramType.id}">
              <%-- label management --%>
              <table style="width:100%">
                <tr valign="top" align="left" height="20" >
                  <td colspan="2" style="padding:5px; border-top:2px solid grey" title="Assign a name to the parameter.">Name: <input tabindex="1" name="param_name" type="text" tabindex="1" value="${paramType.shortName}"/>
                  <div class="message" style="display:inline; float:right; margin:0px; padding:3px">Name this parameter.</div>
                </tr>
              </table>
              <table class="dialog_content_table" style="border-spacing: 0">
                <thead>
                  <tr>
                    <th align="left">
                      Column
                    </th>
                    <th align="left" >
                      Use as
                    </th>
                    <th></th>
                  </tr>
                </thead>
                <tbody>
                  <%-- loop over all fields that can be used for the current parameter --%>
                  <g:each var="field" in="${paramType.fields}" status="i">
                    <tr>
                      <td>
                        ${field.label}
                      </td>
                      <td>
                        <g:select class="paramSetEntry" id="${paramType.id}_${field.id}" name="${field.id}" value="${field.mappedto}" from="${field.mappedto_range}" title="Use column ${field.label} as field in ${paramType.label}"/>
                      </td>
                    </tr>
                  </g:each>
                </tbody>
              </table>
            </div>
          </g:each>
        </div> 
      </td>
    </tr>
  </table>
</div>