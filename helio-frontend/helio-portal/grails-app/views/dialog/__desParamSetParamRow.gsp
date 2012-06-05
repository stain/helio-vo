<%-- Render the param row of the DES.
* String paramName: name of the param
* Object param: the stored param and it actual value
* Map paramDescriptor: map that describes the param to render
* Object value: the currently stored value or the default value.
* Map paramSet : the complete paramset
 --%><tr>
  <td valign="top">
    <label for="parameter_param"><b>Parameter</b></label>
  </td>
  <td valign="top">
    <%-- param selection --%>
    <select id="parameter_param" name="parameter_param" class="paramSetEntry" title="parameter_param" disabled="disabled">
      <g:each var="opt" in="${paramSet.config['parameter_param'].valueDomain}">
          <option value="${opt.id}" title="${opt.fullName}" >${opt.name} (${opt.fullName})</option>
      </g:each>
    </select>
    <%-- operator --%>
    <select id="parameter_operator" name="parameter_operator" class="paramSetEntry" title="Operator" style="width:42px;">
      <g:each var="type" in="${paramSet.config['parameter_operator'].type.values()}">
        <option class="paramSetEntry" name="parameter_operator" value="${type.toString()}" title="${paramSet.config['parameter_operator'].label}">
          <g:message code="${type.label}" />
        </option>
      </g:each>
    
    </select>
    <%-- value --%>
    <input size="7" type="text" class="paramSetEntry" name="parameter_value" title="Value" value="1" disabled="disabled" />
  </td>
  <td valign="top">
    <div class="message" style="display:inline; width:250px; float:right; margin:2px 0px; padding:3px">
      ${paramSet.config['parameter_param'].description}
    </div>
    <g:javascript>
      this.helio.des = this.helio.des ||
        { toString: function() { return 'package: helio.des'; } };
      
      this.helio.des.createArgumentSection = function(functionDescriptor) {
        var argDiv = $('#desFunctionArgs'); 
        argDiv.empty();
        argDiv.append(functionDescriptor.id + ' (');
        
        var topRow = $('<tr/>', argDiv);
        var bottomRow = $('<tr/>', argDiv);
        
        for (var i = 0; i < functionDescriptor.args.length; i++) {
          var arg = functionDescriptor.args[i];
          var topTd = $('<td/>', topRow);
          topTd.append(arg.name);
          
          var bottomTd = $('<td/>', bottomRow);
          if (arg.operators && arg.operators.length > 1) {
            var select = $('<select id="parameter_arg_operator" name="parameter_arg_operator" class="paramSetEntry" title="parameter_arg_operator"></select>', bottomTd);
            for (var j = 0; j < arg.operators.length; j++) {
                $('<option value="arg.operators[j]">')
                 value="${opt.id}" title="${opt.fullName}" >${opt.name} (${opt.fullName})</option>
            }
            select.append()
            bottomTd.append(select);
                        
          }
          bottomRow.append('td');
        }
      };
    
      setTimeout(function() { 
        $('#mission, #function').change(function( evt ) {
          $.ajax({
            url : '<g:createLink controller="des" action="paramFieldConfig" />',
            data: {missionId : $("#mission").val(), functionId : $("#function").val()},
            type : 'GET'
          }).done(
            function(data) {
              console.log(data);
              // populate the params options
              $.each(data.params, function(key, value) {   
                 $('#parameter_param')
                    .append($('<option>', { value : value.id })
                    .text(value.name + ' (' + value.fullName + ', ' + value.unit + ')')
                 ); 
              });
              // populate the operator
              
            }
          );
        });
        $('#mission').change();
      }, 1);
    </g:javascript>
  </td>
</tr>