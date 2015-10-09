<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    %{--
  - Copyright (c) 2013 Rene Puchinger.
  - http://renepuchinger.com
  --}%

<r:require modules="application"/>
    <r:require modules="bootstrap"/>
    <r:require modules="bootstrap-responsive-css"/>
    <r:require modules="wysiwyg"/>
</head>

<body>

<div class="row">

    <div class="well">

        <h2>Welcome to commshare!</h2>

        <p>The purpose of this demo application is to evaluate the web application framework
        Grails. This results mainly from my personal interests and from my needs to be more effective.
        The current version is by no means intended as a fully featured social networking site.</p>

        <p>There are several different technologies for the Java platform I have focused on to some extend,
        for example Spring Roo or Vaadin. It would be rather longer discussion to explain why Grails is better
        choice for me and of course, it mainly depends on personal opinions and taste.</p>

        <p>
        For more information about this great framework, you can visit <a href="http://www.grails.org" target="_blank">http://www.grails.org</a></p>

        <p>Here are some features of this demo:</p>

        <ul class="unstyled">
            <li><i class="icon-ok"> </i> Simple community/social web aplication developed in Grails 2.0.4.</li>
            <li><i class="icon-ok"> </i> Responsive layout using Bootstrap, HTML5 and CSS3 enabling user experience on different view devices (try to resize the browser window width!).</li>
            <li><i class="icon-ok"> </i> Login, Logout, Registration with email confirmation, Lost password, Reset password by means of Spring Security.</li>
            <li><i class="icon-ok"> </i> Distinguished roles of anonymous, regular and administrator users.</li>
            <li><i class="icon-ok"> </i> Approximate displaying of time using Pretty Time.</li>
            <li><i class="icon-ok"> </i> Administration of users, roles and other security info.</li>
            <li><i class="icon-ok"> </i> Possibility to "follow" other users by means of AJAX.</li>
            <li><i class="icon-ok"> </i> Modal dialog to edit user profile.</li>
            <li><i class="icon-ok"> </i> Uploading user image in AJAX way.</li>
            <li><i class="icon-ok"> </i> Recaptcha integration to help avoid spam.</li>
            <li><i class="icon-ok"> </i> Scaffolded, but customized administration of underlying entities.</li>
        </ul>

        <p><strong>Source codes available via Git</strong>, see <a href="https://github.com/renp/commshare">github.com/renp/commshare</a> for instructions.</p>

        <p>Created by <a href="http://renepuchinger.com" target="_blank">Rene Puchinger</a></p>

        <br />

    </div>

</div>

</body>
</html>