

<html>
  <head>
    <meta name="layout" content="singleService" />



    <title>Heliophysics Integrated Observatory index</title>




  </head>
  <body>
    <div class="navigation">
      <g:render template="/indexbar" />
    </div>
    <div id="content">
      <div id="header">
        <h1>Single Service</h1>
        <h2>TBD.</h2>
      </div>
      <!--div class="demo">
        <button id="login">Login</button>
        <input type="text" name="loginid" value=" " id="loginid" />

      </div-->
      
      <form controller="singleService">

        <g:render template="blocks" />

        <g:actionSubmit onclick="mysubmit()" action="querySubmit" value="Search" />
</form>
    </div>








    
  </body>
</html>