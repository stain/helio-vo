<div id="menubar">
  <ul id="menu">
    <li><a href="">Home</a></li>
    <li><a href="javascript:showHelp('./help.gsp', 'Helio:Help','500','600','100','100')">Help</a></li>
    <li><a href="">Changelog</a></li>
  </ul>
  <g:if env="development">
    <ul id="login">
      <li class="notLoggedIn">
        <a href="" onclick="showLogin(); return false;">Login</a>
      </li>
      <li class="notLoggedIn">
        <a href="" onclick="showRegister(); return false;">Register</a>
      </li>
      <li class="loggedIn">
        <a href="" onclick="logout(); return false;">Logout</a>
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
  </g:if>
</div>