<div id="menubar">
  <ul id="menu" class="sf-menu">
    <li>
    	<a>Home</a>
    	<ul style="width: 13em;">
    		<li><a id="misc_splash">Entry Page</a></li>
    		<li><a id="misc_changelog">Changelog</a></li>
    	</ul>
    </li>
    <li>
    	<a>Search</a>
    	<ul>
    		<li><a id="task_eventlist">Events</a></li>
    		<li class="disabled"><a id="task_feature">Features</a></li>
    		<li><a id="task_dataaccess">Data</a></li>
    		<li class="separated"><a id="task_ics">Instruments by Capability</a></li>
    		<li><a id="task_ils">Instruments/Planets by location/time</a></li>
    	</ul>
    </li>
    <li>
    	<a>Plot Data</a>
    	<ul style="width: 13em;">
            <li><a id="task_goesplot">GOES timeline plot</a></li>
            <li><a id="task_flareplot">Flare plot</a></li>
            <li><a id="task_parkerplot">Simple Parker Spiral</a></li>
            <li class="separated"><a id="task_aceplot">ACE timeline</a></li>
            <li><a id="task_staplot">STEREO-A timeline</a></li>
            <li><a id="task_stbplot">STEREO-B timeline</a></li>
            <li><a id="task_ulyssesplot">Ulysses timeline</a></li>
            <li><a id="task_windplot">WIND timeline</a></li>
    	</ul>
    </li>
    <li>
    	<a>Tools</a>
    	<ul style="width: 30em;">
    		<li><a id="task_votableupload">Upload VOTable</a></li>
    		<li class="separated mega category disabled"><a>Propagation Model</a></li>
            <li class="mega"><a id="task_pm_cme_fw" >Coronal Mass Ejections (CME) Forward PM</a></li>
            <li class="mega"><a id="task_pm_cme_back" >Coronal Mass Ejections (CME) Backward PM</a></li>
            <li class="mega"><a id="task_pm_cir_fw" >Co-rotating Interaction Region (CIR) Forward PM</a></li>
            <li class="mega"><a id="task_pm_cir_back" >Co-rotating Interaction Region (CIR) Backward PM</a></li>
            <li class="mega"><a id="task_pm_sep_fw" >Solar Energetic Particles (SEP) Forward PM</a></li>
            <li class="mega"><a id="task_pm_sep_back" >Solar Energetic Particles (SEP) Backward PM</a></li>
            <li class="separated mega category disabled"><a>Event Lists</a></li>
            <li class="mega"><a id="task_tav_2283">Combine lists</a></li>
    		<li class="separated disabled"><a>Connect to external tools (SAMP)</a></li>
    	</ul>
    </li>
    <li>
    	<a>Links</a>
    	<ul>
    		<li><a href="http://helio-vo.eu" target="_blank"><span class="external">Helio Main page</span></a></li>
    		<li><a href="http://hec.ts.astro.it/hec/hec_gui.php" target="_blank"><span class="external">Helio Event Catalogue</span></a></li>
    		<li><a href="http://voparis-helio.obspm.fr/hfc-gui/" target="_blank"><span class="external">Helio Feature Catalogue</span></a></li>
    	</ul>
    </li>
    <li><a>Help</a>
      <ul style="width: 6em;">
        <li><a id="misc_help">Help</a></li>
      </ul>
    </li>
  </ul>
  <g:if env="development">
    <ul id="login">
      <li class="notLoggedIn">
        <a onclick="showLogin(); return false;">Login</a>
      </li>
      <li class="notLoggedIn">
        <a onclick="showRegister(); return false;">Register</a>
      </li>
      <li class="loggedIn">
        <a onclick="logout(); return false;">Logout</a>
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