<h1 style="position:relative;right:35px;margin-bottom:20px;float:none;">Catalogue Parameters</h1>

<table>
  

<g:each in="${resultMap.keySet()}" var="itr">
<tr>
  <td>
    <h3>Catalogue: <span style="color:highlight">${itr}</span></h3>
    <ul style="list-style-type:none;">
      <g:each in="${resultMap[itr]}" var="column">
        <li><label style=" display: block;float: left;width: 150px;">${column} </label> <input class="columnSelection" name="${itr}" type="text"/></li>
      </g:each>
    </ul>
  </td>
  <td>
    <div style="" class="message"><h4>Usage:</h4><ul style="padding:0;margin:0"><li>(<= 3) use /3,</li><li> (>=3)use 3/,</li><li>(between)use 3/6</li></ul></div>
  </td>
  </tr>
</g:each>


</table>