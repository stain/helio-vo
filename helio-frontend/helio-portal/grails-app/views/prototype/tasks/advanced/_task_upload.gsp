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
          <div style="float:right;" class="controls custom_button" id="delete">X</div>
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

        File to upload: <input type="file" name="fileInput"/>

        <input type="submit" value="Submit" />
      </form>

    </div>
  </div>

  <div id="displayableResult" class="displayable" style="display:block">
  </div>
</div>

