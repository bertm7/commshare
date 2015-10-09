%{--
  - Copyright (c) 2013 Rene Puchinger.
  - http://renepuchinger.com
  --}%

<%@ page import="org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><g:layoutTitle default="${meta(name: 'app.name')}"/></title>
    <link rel="stylesheet" href="<g:createLinkTo dir="css" file="custom.css" />" />
    <meta name="description" content="">
    <meta name="author" content="">

    <meta name="viewport" content="initial-scale = 1.0">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->

    <r:require modules="scaffolding"/>

    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">

    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body>

<g:render template="/common/header"/>

<div class="container">
    <g:layoutBody/>
</div>

<g:render template="/common/footer"/>

<r:layoutResources/>

</body>
</html>