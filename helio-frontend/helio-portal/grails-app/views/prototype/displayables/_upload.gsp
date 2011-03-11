
<div id="staticForms">
  <div id="formHeader">
    <img style ="float:left;position:relative;top:10px;left:10px" height="30px" src="${resource(dir:'images/icons/toolbar',file:'upload_vot.png')}" />
    <h1 style ="float:left;position:relative;left:15px" >Upload VOTable</h1>
    
  </div>
  <div style="clear:both;padding:50px 0 0 80px;">

<form id="myForm" action="asyncUpload" method="post">

    File to upload: <input type="file" name="fileInput"/>

    <input type="submit" value="Submit" />
</form>
    
  

  </div>

<div id="displayableResult" class="displayable" style="display:block">

</div>

</div>
