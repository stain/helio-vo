<div id="tables">
  <div class="module">
    <div class="header votableResultHeader viewerHeader">
      <h1>Error in Result</h1>
    </div>
    <div class="content">
      <div>
        <table width="100%">
          <tr>
            <td>
              <div id="errorResponse">
                <p>An error has occurred while processing your request.</p>
                <div class="error">Message: ${responseObject?.message}</div>
                <div class="stacktrace"><pre>${responseObject?.stackTrace}</pre></div>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</div>