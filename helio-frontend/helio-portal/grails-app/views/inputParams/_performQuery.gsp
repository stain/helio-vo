<%-- Template for the perform query area
Expected params:
 * Integer step: step number for help text.
 --%><tr>
  <td style="border-top: solid 1px gray; vertical-align: top; padding-top: 10px;">
    <table style="margin-bottom: 10px;">
      <tbody>
        <tr>
          <td valign="center" align="center" width="90"><div id="result_summary_select" class="custom_button">Submit</div></td>
          <td id="perform_query_text" class="candybox" height="45">
          </td>
        </tr>
      </tbody>
    </table>
  </td>

  <td style="border-top: solid 1px gray; vertical-align: top;">
    <div class="message">
      <b>Step ${step}</b><br /> Click 'Send query' to send the query to the server and retrieve the result. Depending on the query this may take a while
    </div>
  </td>
</tr>
