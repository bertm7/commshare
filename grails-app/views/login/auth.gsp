<html>
<head>
	<meta name='layout' content='main'/>
	<title>%{--
  - Copyright (c) 2013 Rene Puchinger.
  - http://renepuchinger.com
  --}%

<g:message code="springSecurity.login.title"/></title>
    <r:require modules="application"/>
    <r:require modules="bootstrap"/>
    <r:require modules="bootstrap-responsive-css"/>
</head>

<body>
<div class="row">

		<form action='${postUrl}' method='POST' id='loginForm' class="span4">
            <h2><g:message code="springSecurity.login.header"/></h2>

			<p>
				<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
				<input type='text' class='text_ input-block-level' name='j_username' id='username'/>
			</p>

			<p>
				<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
				<input type='password' class='text_ input-block-level' name='j_password' id='password'/>
			</p>

			<p id="remember_me_holder">
				<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
				<label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>
			</p>

			<p>
				<input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}' class="btn btn-primary" />
                <g:link controller="register" action="forgotPassword" class="btn">Forgot Password?</g:link>
			</p>
		</form>
</div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>
