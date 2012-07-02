<div id="menubar">
  <ul id="menu" class="sf-menu">
    <li>
    	<a href="">Home</a>
    	<ul>
    		<li><a href="">Home Page</a></li>
    		<li><a href="">About</a></li>
    		<li><a href="">Changelog</a></li>
    		<li class="separated first"><a href="">Help</a></li>
    	</ul>
    </li>
    <li>
    	<a href="">Search</a>
    	<ul>
    		<li><a href="">Events</a></li>
    		<li><a href="">Features</a></li>
    		<li><a href="">Data</a></li>
    		<li class="separated first"><a href="">Instruments by Capability</a></li>
    		<li class="separated"><a href="">Instruments / Plantes by time</a></li>
    	</ul>
    </li>
    <li>
    	<a href="">Plot Data</a>
    	<ul>
    		<li><a href="">Goes timeline</a></li>
    		<li><a href="">Flare Plot</a></li>
    		<li><a href="">Simple parker spiral</a></li>
    		<li><a href="">Feature plot</a></li>
    		<li class="separated first"><a href="">ACE timeline</a></li>
    		<li class="separated"><a href="">STEREO-A timeline</a></li>
    		<li class="separated"><a href="">STEREO-B timeline</a></li>
    		<li class="separated"><a href="">Ulysses timeline</a></li>
    		<li class="separated"><a href="">WIND timeline</a></li>
    	</ul>
    </li>
    <li>
    	<a href="">Tools mit Megasubelement</a>
    	<ul>
    		<li><a href="">Upload VOTable</a></li>
    		<li class="separated first mega"><a href="">Event Lists</a></li>
    		<li class="separated mega"><a href="">Combine lists</a></li>
    		<li class="separated mega"><a href="">...</a></li>
    		<li class="separated first mega"><a href="">Propagation Model</a></li>
    		<li class="separated mega"><a href="">Coronal Mass Ejections (CME) Forward PM</a></li>
    		<li class="separated mega"><a href="">Coronal Mass Ejections (CME) Backward PM</a></li>
    		<li class="separated mega"><a href="">Co-rotating Interaction Region (CIR) Forward PM</a></li>
    		<li class="separated mega"><a href="">Co-rotating Interaction Region (CIR) Backward PM</a></li>
    		<li class="separated mega"><a href="">Solar Energetic Particles (SEP) Forward PM</a></li>
    		<li class="separated mega"><a href="">Solar Energetic Particles (SEP) Backward PM</a></li>
    		<li class="separated"><a href="">Connect to external tools (SAMP)</a></li>
    	</ul>
    </li>
    <li>
    	<a href="">Tools mit mehreren Subelementen</a>
    	<ul>
    		<li><a href="">Upload VOTable</a></li>
    		<li>
	    		<a href="">Propagation Model</a>
	    		<ul>
	    			<li class=""><a href="">...</a></li>
		    		<li class=""><a href="">...</a></li>
		    		<li class=""><a href="">...</a></li>
		    		<li class=""><a href="">...</a></li>
		    		<li class=""><a href="">...</a></li>
		    		<li class=""><a href="">...</a></li>
	    		</ul>
    		</li>
    		<li class="separated first"><a href="">Connect to external tools (SAMP)</a></li>
    	</ul>
    </li>
    <li>
    	<a href="">Beta</a>
    	<ul>
    		<li><a href="">...</a></li>
    	</ul>
    </li>
    <li>
    	<a href="">Links</a>
    	<ul>
    		<li><a href="http://helio-vo.eu" target="_blank">Helio Main page</a></li>
    		<li><a href="http://hec.ts.astro.it/hec/hec_gui.php" target="_blank">Helio Event Catalogue</a></li>
    		<li><a href="http://voparis-helio.obspm.fr/hfc-gui/" target="_blank">Helio Feature Catalogue</a></li>
    	</ul>
    </li>
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