<div id='ajaxLogin'>
  <div class='inner'>
    <div class='fheader'>Please Login</div>
    <form action='${request.contextPath}/j_spring_security_check'
      method='POST' id='ajaxLoginForm' name='ajaxLoginForm'
      class='cssform'>
      <p>
        <label for='username'>Mail Address</label> <input type='text'
          class='text_' name='j_username' id='username' />
      </p>
      <p>
        <label for='password'>Password</label> <input type='password'
          class='text_' name='j_password' id='password' />
      </p>
      <p>
        <label for='remember_me'>Remember me</label> <input
          type='checkbox' class='chk' id='remember_me'
          name='_spring_security_remember_me' />
      </p>
      <p>
        <a href='javascript:void(0)' onclick='authAjax(); return false;'>Login</a>
        <a href='javascript:void(0)'
          onclick='cancelLogin(); return false;'>Cancel</a>
      </p>
    </form>
    <div style='display: none; text-align: left;' id='loginMessage'></div>
  </div>
</div>
<div id='ajaxRegister'>
  <div class='inner'>
    <div class='fheader'>Please Register</div>
    <g:form controller="user" action="ajaxRegister"
        method='POST' id='ajaxRegisterForm' name='ajaxRegisterForm' class='cssform'>
      <p>
        <label for='r_username'>Mail Address</label> 
        <input type='text' class='text_' name='username' id='r_username' />
      </p>
      <p>
        <label for='r_password'>Password</label> 
        <input type='password' class='text_' name='password' id='r_password' />
      </p>
      <p>
        <label for='r_passwordconfirm'>Confirm password</label> 
        <input type='password' class='text_' name='password2' id='r_passwordconfirm' />
      </p>
      <p>
        <a href='javascript:void(0)' onclick='registerAjax(); return false;'>Register</a>
        <a href='javascript:void(0)' onclick='cancelRegister(); return false;'>Cancel</a>
      </p>
    </g:form>
    <div style='display: none; text-align: left;' id='registerMessage'></div>
  </div>
</div>

<script type='text/javascript'>
// center the form 
$(document).ready(function() {
    $('#ajaxLogin').dialog({ autoOpen: false });
    $('#ajaxRegister').dialog({ autoOpen: false });
  <sec:ifLoggedIn>
    $('.notLoggedIn').hide();
    $('.loggedIn').show();
  </sec:ifLoggedIn>
  <sec:ifNotLoggedIn>
    $('.loggedIn').hide();
    $('.notLoggedIn').show();
  </sec:ifNotLoggedIn>
});

function showLogin() {
    $('#ajaxLogin').dialog('open'); 
}

function cancelLogin() { 
    $("#ajaxLoginForm input").attr("disabled");
    $('#ajaxLogin').dialog('close');
}

function authAjax() { 
    $("#ajaxLoginForm input").removeAttr("disabled");
    
    $('#loginMessage').html('Sending request ...'); 
    $('#loginMessage').show();
    
    $("#ajaxLoginForm input").attr("disabled");
    
    $.ajax({
        url: "${request.contextPath}/j_spring_security_check",
        type: "POST",
        data: $("#ajaxLoginForm").serialize(),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("X-Ajax-call", "true");
        },
        success: function(result) {
            $("#ajaxLoginForm input").removeAttr("disabled");
            if (result.success) { 
                $('#ajaxLogin').dialog('close');
                $('.notLoggedIn').hide();
                $('.loggedIn').show();
                $('#loginLink').html('Logged in as ' + result.username);
            } else if (result.error) { 
                $('#loginMessage').html("<span class='errorMessage'>" + result.error + '</span>'); 
            } else { 
                $('#loginMessage').html(result); 
            }
        }
    });
}

function showRegister() {
    $('#ajaxRegister').dialog('open'); 
}

function cancelRegister() { 
    $('#registerMessage').html(''); 
   
    $("#ajaxRegisterForm input").attr("disabled");
    $('#ajaxRegister').dialog('close');
}

function registerAjax() { 
    $("#ajaxRegisterForm input").removeAttr("disabled");
    
    $('#registerMessage').html('Sending request ...'); 
    $('#registerMessage').show();

    
    $("#ajaxRegisterForm input").attr("disabled");
    
    $.ajax({
        url: "${request.contextPath}/user/ajaxRegister",
        type: "POST",
        data: $("#ajaxRegisterForm").serialize(),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("X-Ajax-call", "true");
        },
        success: function(result) {
            $("#ajaxRegisterForm input").removeAttr("disabled");
            if (result.success) { 
                $('#ajaxRegister').dialog('close');
                $('#loginLink').html('Logged in as ' + result.username);
                $('.notLoggedIn').hide();
                $('.loggedIn').show();                
                $('<div>Your account has been created and you have been logged into HELIO.</div>').dialog({ buttons: { "Ok": function() { $(this).dialog("close"); }}});
            } else if (result.errors) {
                var msg = '';
                $(result.errors).each(function(index) {
                    msg += '<div class="errorMessage">' + this.message + '</div>';
                });
                $('#registerMessage').html('<span class="errorMessage">' + msg + '</span>');
            } else { 
                $('#registerMessage').html('<span class="errorMessage">Unknown problem occurred: ' + result + '</span'); 
            }
        }
    });
}

function logout() {         
    $.ajax({
        url: "${request.contextPath}/logout",
        type: "POST",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("X-Ajax-call", "true");
        },
        success: function(result) {
            // update nav bar.
            $('.loggedIn').hide();
            $('.notLoggedIn').show();
            $('#loginLink').html('');
        }
    });
}
</script>