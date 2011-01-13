

<div class="block" id="columnBlock" style="display:block">
  <h2>Columns:</h2>
  <g:each var="tableNamesIterator" in="${hecHash.list}">
    <div id="${tableNamesIterator}" class="columnReset" style="display:none">
          ${tableNamesIterator}
      <ul >
        <g:each var="column" in="${hecHash[tableNamesIterator]}">
          <li>${column} <input class="columnSelection" name="columnSelection" type="text"/></li>
        </g:each>
      </ul>
    </div>
  </g:each>
  <g:each var="tableNamesIterator" in="${icsHash.list}">
    <div id="${tableNamesIterator}" class="columnReset" style="display:none">
${tableNamesIterator}
      <ul >
        <g:each var="column" in="${icsHash[tableNamesIterator]}">
          <li>${column} <input class="columnSelection" name="columnSelection" type="text"/></li>
        </g:each>
      </ul>
    </div>
  </g:each>
  <g:each var="tableNamesIterator" in="${ilsHash.list}">
    <div id="${tableNamesIterator}" class="columnReset" style="display:none">
${tableNamesIterator}
      <ul>
        <g:each var="column" in="${ilsHash[tableNamesIterator]}">
          <li>${column} <input class="columnSelection" name="columnSelection" type="text"/></li>
        </g:each>
      </ul>
    </div>
  </g:each>
  <input id="whereField" name="where" style="display:none" type="text"/>


</div><!--endblock columnBlock-->