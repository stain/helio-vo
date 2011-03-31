<div class="module" style="width:99%;margin-top: 20px">
  <div class="header advancedParameters viewerHeader">
    <h1>Catalogue Parameters</h1>
  </div>
  <div class="content">

<h1 style="margin-bottom:10px;float:none;"></h1>
<input class="column-reset" type="button" value="Reset the form">
<table>


<g:each in="${resultMap.keySet()}" var="itr">
<tr>
  <td>
    <h3 style=";margin: 10px 0 20px 0">Catalogue: <span style="color:highlight">${itr}</span></h3>
    <ul style="list-style-type:none;">
      <g:each in="${resultMap[itr]}" var="column">
        <li><label style=" display: block;float: left;width: 150px;">${column} </label> <input class="columnSelection" name="${itr}" type="text"/></li>
      </g:each>
    </ul>
  </td>
  <td>
  <div style="" class="message pqlmessage">
<b>Usage:</b>
<p>The current version of HELIO supports
<a href="http://www.ivoa.net/internal/IVOA/TableAccess/PQL-0.2-20090520.pdf" target="blank" title="Parameterized Query Language">PQL</a> only.
</p>
<table border="0" cellpadding="0" cellspacing="0">
<tbody>
	<tr><td><code>'/25.2'</code></td><td> for&nbsp;</td><td><code>&lt;=25.2</code></td><td>(less than equal)</td></tr>
	<tr><td><code>'25.2/'</code></td><td> for </td><td><code>&gt;=25.2</code></td><td>(greater than equal)</td></tr>
	<tr><td><code>'3/6'</code></td><td> for </td><td><code>between(3, 6)</code></td><td>(between)</td></tr>
	<tr><td><code>'*text*'</code></td><td> for </td><td><code>like %text%</code></td><td> (like)</td></tr>
	<tr><td><code>'3'</code></td><td> for </td><td><code>=3</code></td><td>(number equals)</td></tr>
	<tr><td><code>'text'</code></td><td> for </td><td><code>='text'</code></td><td>(string equals)</td></tr>
	<tr><td><code>'/2,7/'</code></td><td> for </td><td><code>x&lt;=2 OR x&gt;=7</code></td><td>(or constraint)</td></tr>
	<tr><td><code>'2/;/7'</code></td><td> for </td><td><code>x&gt;=2 AND x&lt;=7</code></td><td>(and constraint)</td></tr>
</tbody>
</table>
</div>
  </td>
  </tr>
</g:each>


</table>
  </div>
</div>
