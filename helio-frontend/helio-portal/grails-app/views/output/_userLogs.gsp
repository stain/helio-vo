<%-- template to render the user logs
Excpected model:
 * LogRecord[] userLogs: array of java.log.LogRecords
 * Map taskDescriptor : the task descriptor 
--%><div class="header userLogsHeader viewerHeader collapsed">
  <h1>Log for task '${taskDescriptor.label}'</h1>
</div>
<div class="content task_body">
  <table cellpadding="0" cellspacing="0" border="1">
    <g:each var="record" in="${userLogs}" >
      <tr>
        <td valign="top" align="left">${record.level}</td>
        <td valign="top" align="left"><pre>${record.message}</pre></td>
      </tr>
    </g:each>
  </table>
</div>