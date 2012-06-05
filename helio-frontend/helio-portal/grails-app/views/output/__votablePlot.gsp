<%-- Render a single table of a full votable.
Expected params: 
* String tableId: a unique identifier of the table for a prefix of the table rows.
* String plotTitle: the title for the plot.
 --%><input type="hidden" name="plotTitle" value="${plotTitle}"></input>
<table style="width:100%">
  <tr>
    <td colspan="2"><b>WARNING: The plotting area is in prototype state.</b></td>
  </tr>
  <tr>
    <td valign="top" style="width:250px">
      y-Axis
      <div id="${tableId}_plot_options"></div>
    </td>
    <td valign="top">
      <div id="${tableId}_plot" style="width:100%"></div>
    </td>
  </tr> 
</table>