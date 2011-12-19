<div id="task" class="task">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr>
        <td>
        </td>
        <td>
          <h1 >Upload a VOTable</h1>
        </td>
        <td>
        </td>
      </tr>
    </table>
  </div>
  <div id="task_area">
    <div class="header queryHeader viewerHeader">
      <h1>Upload</h1>
    </div>
    <div class="task_request">
      <g:form controller="voTable" action="asyncUpload" method="post" name="upload2Form" enctype="multipart/form-data"> 
          <input id="service_name" name="serviceName" type="hidden" value="upload2"/>
          <input id="task_name" name="taskName" type="hidden" value="task_upload2"/>
          <input id="task_label" type="hidden" value="Upload2"/>
          <!--  File to upload: <input type="file" name="fileInput"/>-->
          
          <input type="text" id="fileName" class="file_input_textbox" readonly="readonly">
 
		  <div class="file_input_div">
		  	<input type="button" value="Browse" class="file_input_button menu_item custom_button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" />
		  	<input type="file" name="fileInput" class="file_input_hidden" onchange="javascript: document.getElementById('fileName').value = this.value" />
		  </div>
          
          <div id="btn_upload" >Upload</div>
          
          <div id="msg_upload"></div>   
      </g:form>
    </div>
  </div>
  <div id="task_result_area">
  </div>
</div>

