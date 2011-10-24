<table>
  <tr><td></td>

  </tr>
  <tr>
    <td style="vertical-align:middle;" >
      <div  class="resultDroppableEvent" style="width: 70px; height: 70px; padding: 0; float: left; margin: 10px;">
        <img style="margin:11px" src="${resource(dir:'images/helio',file:'circle_event.png')}" />
      </div>
    </td>
    <td>
      <h5 style="margin-bottom: 7px">Specify event characteristics to restrict search:</h5>
      <table  style="text-align: left"cellpadding="0" cellspacing="0">
        <col width="10" />
        <col width="100"/>
        <col width="100"/>
        <col width="100"/>
        <col width="100"/>
        <col width="100"/>
        <tr>
          <td></td>
          <td>Event type:</td>
          <td><input name="y" column="7" type="checkbox" value=""/>CME</td>
          <td><input name="y" column="6" type="checkbox" value=""/>Flare</td>
          <td><input name="y" column="8" type="checkbox" value=""/>Solar Wind </td>
          <td><input name="y" column="9" type="checkbox" value=""/>Particle</td>
        </tr>
        <tr>
          <td></td>
          <td>Location:</td>
          <td><input name="y" column="11" type="checkbox" value=""/>Solar</td>
          <td><input name="y" column="12" type="checkbox" value=""/>IPS</td>
          <td><input name="y" column="13" type="checkbox" value=""/>Geo</td>
          <td><input name="y" column="14" type="checkbox" value=""/>Planet</td>
        </tr>
        <tr>
          <td></td>
          <td>Obs. type:</td>
          <td><input name="I" column="10" type="checkbox" value=""/>In situ</td>
          <td><input name="r" column="10" type="checkbox" value=""/>Remote</td>
          <td></td>
          <td></td>
        </tr>
      </table>
    </td>
    <td style="padding:10px;"><div><b>Search</b> <input id="input_filter" type="text"></div></td>

  </tr>
  <tr>
  <table style="margin-top: 10px;width:100%"><tr>
      <td></td>
      <td>
        <table  id="input_table" class="resultTable">
          <thead>
            <tr>
          <g:if test="${hecCatalogs?.getTables() != null && hecCatalogs?.getTables().size() > 0} ">
          <g:each in="${hecCatalogs?.getTables()?.get(0)?.getHeaders()}" status="i" var="header">
            <th>${header}</th>
          </g:each>
          </g:if>
    </tr>
    </thead>
    <tbody>
    <g:if test="${hecCatalogs?.getTables() != null && hecCatalogs?.getTables().size() > 0} ">
    <g:each in="${hecCatalogs?.getTables()?.get(0)?.getData()}" status="i" var="rows">

      <tr >
      <g:each in="${rows}" status="j" var="tdelement">
        <td internal="${tdelement}">${tdelement}</td>
      </g:each>
      </tr>
    </g:each>
      </g:if>
    </tbody>
  </table>
</td>

<td class="candybox"><ul id="extra_list_form"></ul></td>

</tr></table>
</tr>

</table>

<table id="input_label_table"  style="display:none;width:100%;margin: 30px 0px">
  <tbody>
    <tr >
      <td>
        <h4 style="border-bottom:2px solid grey">Label Management</h4>
      </td>
    </tr>
    <tr>
      <td style="padding:20px">Label: <input type="text" id="input_form_label" tabindex="1"/>
        <%--Group:<input type="text" disabled="true" id="input_form_group"/> --%></td>
    </tr>


  </tbody>
</table>