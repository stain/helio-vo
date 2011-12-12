<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td>
        </td>
        <td>
          <h1 >Upload VOTable</h1>
        </td>
        <td>
        </td>
      </tr>
    </table>
  </div>
  <div class="module ">
    <div class="header queryHeader viewerHeader">
      <h1>Query Form</h1>
    </div>
    <div class="content">
      <form id="uploadForm" action="asyncUpload" method="post">
          <input id="service_name" name="serviceName" type="hidden" value="upload"/>
          <input id="task_name" name="taskName" type="hidden" value="task_upload"/>
          <input id="task_label" type="hidden" value="Upload"/>

          

		<input type="text" id="fileName" class="file_input_textbox" readonly="readonly">
 
		<div class="file_input_div">
		  <input type="button" id="browseButton" value="Browse" class="file_input_button menu_item custom_button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" />
		  <input type="file" id="fakeBrowseButton" class="file_input_hidden" onchange="javascript: document.getElementById('fileName').value = this.value" />
		</div>

          <input type="submit" value="Submit" class="menu_item custom_button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" />
      </form>
    </div>
  </div>
  <div id="displayableResult" class="displayable" style="display:block">
  </div>
</div>

