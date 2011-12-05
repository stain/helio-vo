<div id="menuContainer">
  <ul id="menu">
    <li class="first"><a onclick="return false" href=""><b>Explorer</b></a></li>
    <li><a href="javascript:showHelp('./help.gsp', 'Helio:Help','500','600','100','100')"><b>Help</b></a></li>
    <%-- li><a target="_blank" href="http://helio-vo.blogspot.com/"><b>Helio-Blog</b></a></li --%>
  </ul>
  <ul id="login">
      <li class="notLoggedIn">
        <a href="" onclick="showLogin(); return false;"><b>Login</b></a>
      </li>
      <li class="notLoggedIn">
        <a href="" onclick="showRegister(); return false;"><b>Register</b></a>
      </li>
      <li class="loggedIn">
        <a href="" onclick="logout(); return false;"><b>Logout</b></a>
      </li>
      <li class="loggedIn">
         <sec:ifLoggedIn>
            <span id="loginLink">Logged in as <sec:username/></span>
          </sec:ifLoggedIn>
          <sec:ifNotLoggedIn>
            <span id="loginLink"></span>
          </sec:ifNotLoggedIn>
      </li>
      
  </ul>
  <g:render template="/login/ajaxLogin"/>
</div>