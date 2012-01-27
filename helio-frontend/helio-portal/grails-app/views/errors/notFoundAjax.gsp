<div class="error_dialog">
  <h4>Error processing request.</h4>
  <div class="message">
    Error Trace:<br /> 
    <strong>Error ${request.'javax.servlet.error.status_code'}:</strong>${request.'javax.servlet.error.message'?.encodeAsHTML()}<br /> 
    <strong>Servlet:</strong>${request.'javax.servlet.error.servlet_name'}<br /> 
    <strong>URI:</strong>${request.'javax.servlet.error.request_uri'}<br />
    <g:if test="${exception}">
      <strong>Exception Message:</strong>
      ${exception.message?.encodeAsHTML()}
      <br />
      <strong>Caused by:</strong>
      ${exception.cause?.message?.encodeAsHTML()}
      <br />
      <strong>Class:</strong>
      ${exception.className}
      <br />
      <strong>At Line:</strong> [${exception.lineNumber}] <br />
      <strong>Code Snippet:</strong>
      <br />
      <div class="snippet">
        <g:each var="cs" in="${exception.codeSnippet}">
          ${cs?.encodeAsHTML()}<br />
        </g:each>
      </div>
    </g:if>
  </div>
</div>