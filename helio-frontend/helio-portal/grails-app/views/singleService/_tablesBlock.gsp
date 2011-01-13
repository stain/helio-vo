<div class="block" id="tableBlock"  style="display:block">
  <h3>Select a table to query:</h3>

  <div id="hecTableDiv" class="tableReset" style="display:none">
    <ul>
      <g:each in="${hecHash.list}">
        <li><input class="selectableTableBlock" type="checkbox" name="tableSelection" value="${it}" /> ${it}</li>
      </g:each>
    </ul>

  </div><!--enddiv hectablediv-->
  <div id="icsTableDiv" class="tableReset" style="display:none">
    <ul>
      <g:each in="${icsHash.list}">
        <li><input class="selectableTableBlock" type="checkbox" name="tableSelection" value="${it}" /> ${it}</li>
      </g:each>
    </ul>
  </div><!--enddiv icsTableDiv-->
  <div id="ilsTableDiv" class="tableReset" style="display:none">
    <ul>
      <g:each in="${ilsHash.list}">
        <li><input class="selectableTableBlock" type="checkbox" name="tableSelection" value="${it}" /> ${it}</li>
      </g:each>
    </ul>
  </div><!--enddiv ilsTableDiv-->
  <div id="dpasTableDiv" class="tableReset" style="display:none">
    <ul>
      <g:each in="${dpasHash.list}">
        <li><input class="selectableTableBlock" type="checkbox" name="tableSelection" value="${it}" /> ${it}</li>
      </g:each>
    </ul>

  </div><!--enddiv dpasTableDiv-->

  
  <ul id="selectedListTable">

  </ul>
</div><!--endblock tableBlock-->