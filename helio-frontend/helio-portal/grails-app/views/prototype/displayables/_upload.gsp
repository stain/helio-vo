<div id="actionViewer">
  <div class="viewerHeader">
    <table  style="height:30px;width: 100%;">
      <tr >
        <td width="60">
          <img height="30px" src="${resource(dir:'images/icons/toolbar',file:'upload_vot.png')}" />
        </td>
        <td>
          <h1 style="font-size:2em;font-weight: normal;margin-top: 10px">Upload VOTable</h1>
          Label: <input style="margin-top:5px;" id="label" type="text"/>
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

        File to upload: <input type="file" name="fileInput"/>

        <input type="submit" value="Submit" />
      </form>

    </div>
  </div>

  <div id="displayableResult" class="displayable" style="display:block">
  </div>
</div>

