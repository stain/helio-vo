<g:if test="${responseObject?.result != null}" >
  <div id="tables">
    <div class="module">
      <div class="header queryHeader viewerHeader">
        <h1>Result</h1>
      </div>
      <div class="content">
        <div>
          <table width="100%">
            
            
            <tr>
              <td>
              
                <div class="message" style=" margin: 0; clear: both; padding: 0px pt 20pt 5px;"><b>Select result</b><br/>To save your results you can click on 'Save as VoTable', you can also transform them into parameters to use in another query by selecting the rows of interest and then clicking on "Save selection to Data-Cart" or download the data by clicking on "Download Selected files/all". These options will only be avaliable where applicable.</div>
              </td>
            </tr> 
          </table>
          <div id="voTables" style="clear: both; padding: 0px pt 20pt 5px;">
            <g:set var="result" value="${responseObject?.result}" />
            <g:set var="result" scope="session" value="${responseObject?.result}" />
            
            
            <g:form target="_blank" controller="prototype">

              <input id="resultId" name="resultId" type="hidden" value="${responseObject?.resultId}"/>

              <g:actionSubmit class="custom_button" style="padding:3px;float:left" action="downloadVOTable" value="Save as VOTable" name="download"/>
            </g:form>
            <!--div id="resultSelectionCounter" class="custom_button" style="margin-right:10px;float:right;">0</div-->
            
            <div id="response_save_selection" class="custom_button" style="margin-right:10px;float:right;">Save selection to Data Cart</div>
            <div id="response_download_selection" class="custom_button" style="display: none;margin-right:10px;float:right;">Download selected files/all</div>


            <div style="clear:both;width:100%"></div>

            <g:if test="${result?.queryInfo?.contains('QUERY_ERROR')}" >
              "The server reported an unspecified error. Some context information may be found in the returned VOTable. We are sorry for the inconvenience."
            </g:if>
            <g:each in="${result?.getTables()}"  status="x" var="tables">
              <div style="margin-top:20px;margin-bottom:5px;border-bottom:3px solid black"> <h3>${tables.getName()}</h3></div>
              <!--div reference="resultTable${x}" id="resultSelectionSelectAll" class="custom_button" style="margin-right:10px;float:left;">Select All</div-->
              <g:if test="${tables?.getData().size() == 0}" >
              "The selected catalogue returned no data."
            </g:if>
              <g:else>
              <table cellpadding="0" cellspacing="0" border="0" class='resultTable' id="resultTable${x}">

                <thead>
                  <tr>
                <g:each in="${tables?.getHeaders()}" status="i" var="testInstance">

                  <g:if test="${testInstance == 'url' || testInstance == 'event_detail'}"><g:set var="urls" value="${i}" /></g:if>
                  <th>${testInstance} </th>

                </g:each>

                </tr>
                </thead>
                <tbody>
                <g:each in="${tables?.getData()}" status="i" var="testInstance">
                  <tr class="${(i % 2) == 0 ? 'gradeB' : 'gradeB'}">
                  <g:each in="${testInstance}" status="j" var="row">
                    <g:if test="${urls == j}"><td><a target="_blank" href="${row}">${row.substring(row.lastIndexOf('/')+1,row.length())}</a></td></g:if>
                    <g:elseif test="${row == '-2147483648'}"><td></td></g:elseif>
                    <g:else><td>${row}</td></g:else>
                  </g:each>
                  </tr>
                </g:each>


                </tbody>
              </table>
                </g:else>
            </g:each>

          </div>
        </div>
      </div>
    </div>
<%-- show the user log if available --%>
    <g:if test="${responseObject?.result && responseObject.result?.logRecords?.length > 0}">
      <div class="module">
        <div class="header queryHeader viewerHeader collapsed">
          <h1>Log</h1>
        </div>
        <div class="content">
          <table cellpadding="0" cellspacing="0" border="1" >
            <g:each in="${responseObject?.result.logRecords}" var="record">
              <tr>
                <td valign="top" align="left"><%=record.level %></td>
                <td valign="top" align="left"><%=record.message %></td>
              </tr>
            </g:each>
          </table>
        </div>
      </div>
    </g:if>
  </div>

</g:if>

<g:if test="${responseObject?.error != null}" >
  <div id="errorResponse" style="display:none">
${responseObject?.error}
  </div>

</g:if>

<g:if test="${responseObject?.uploadId != null}" >
  <div id="uploadId" style="display:none">
${responseObject?.uploadId}
  </div>

</g:if>
