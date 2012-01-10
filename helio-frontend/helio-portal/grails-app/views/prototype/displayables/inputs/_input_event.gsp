<table>
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
        	<td colspan="2"><input id="checkAll" checked="" name="everything" type="checkbox"/> Show all</td>
        </tr>
        <tr>
          <td></td>
          <td>Event type:</td>
          <td><input class="checkFilter event" name="CME" title="Coronal Mass Ejection" column="7" type="checkbox" value="y"/> CME</td>
          <td><input class="checkFilter event" name="Flare" title="Flare" title="Flare" column="6" type="checkbox" value="y"/> Flare</td>
          <td><input class="checkFilter event" name="Solar Wind" title="Solar Wind" column="8" type="checkbox" value="y"/> Solar Wind </td>
          <td><input class="checkFilter event" name="Particle" title="Particle" column="9" type="checkbox" value="y"/> Particle</td>
        </tr>
        <tr>
          <td></td>
          <td>Location:</td>
          <td><input class="checkFilter location" name="Solar" title="Solar" name="solar" column="11" type="checkbox" value="y"/> Solar</td>
          <td><input class="checkFilter location" name="IPS" title="Interplanetary Space" column="12" type="checkbox" value="y"/> IPS</td>
          <td><input class="checkFilter location" name="Geo" title="Geo" column="13" type="checkbox" value="y"/> Geo</td>
          <td><input class="checkFilter location" name="Planet "title="Planet" column="14" type="checkbox" value="y"/> Planet</td>
        </tr>
        <tr>
          <td></td>
          <td>Obs. type:</td>
          <td><input class="checkFilter observation" title="In situ" name="obsType" column="10" type="radio" value="I"/> In situ</td>
          <td><input class="checkFilter observation" title="Remote" name="obsType" column="10" type="radio" value="r"/> Remote</td>
          <td><input class="checkFilter observation" title="Both" name="obsType" id="obsBoth" checked="" column="10" type="radio" value="I|r"/> Both</td>
          <td></td>
          <td></td>
        </tr>
      </table>
    </td>
    <td style="padding:10px;">
      <div><b>Search</b> <input id="input_filter" type="text"></div>
    </td>
  </tr>
  <tr>
  	<td colspan="3" id="filterText">All flare lists are shown.</td>
  </tr>
  <tr>
    <td colspan="3">
      <table style="width:100%">
        <tr>
          <td></td>
          <td>
            <table id="input_table" class="resultTable">
              <thead>
                <tr>
                  <g:if test="${hecCatalogs?.getTables() != null && hecCatalogs?.getTables().size() > 0} ">
                    <g:each in="${hecCatalogs?.getTables()?.get(0)?.getHeaders()}" status="i" var="header">
                      <th>${header}</th>
                    </g:each>
                    <th>Invisible Filter Column</th>
                  </g:if>
                </tr>
              </thead>
              <tbody>
                <g:if test="${hecCatalogs?.getTables() != null && hecCatalogs?.getTables().size() > 0} ">
                  <g:each in="${hecCatalogs?.getTables()?.get(0)?.getData()}" status="i" var="rows">
                    <tr id="row_${rows[0]}">
                      <g:each in="${rows}" status="j" var="tdelement">
                        <td internal="${tdelement}">${tdelement}</td>
                      </g:each>
                      <td>f</td>
                    </tr>
                  </g:each>
                </g:if>
              </tbody>
            </table>
          </td>
          <td class="candybox"><ul id="extra_list_form"></ul></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<table id="input_label_table"  style="display:none;width:100%;margin: 30px 0px">
  <tbody>
    <tr>
      <td>
        <h4 style="border-bottom:2px solid grey">Label Management</h4>
      </td>
    </tr>
    <tr>
      <td style="padding:20px">
        Label: <input type="text" id="input_form_label" tabindex="1"/>
      </td>
    </tr>
  </tbody>
</table>