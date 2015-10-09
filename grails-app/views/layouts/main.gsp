%{--
  - Copyright (c) 2013 Rene Puchinger.
  - http://renepuchinger.com
  --}%

<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><g:layoutTitle default="commshare"/></title>
    <r:layoutResources/>
    <link rel="stylesheet" href="<g:createLinkTo dir="css" file="custom.css" />" />

    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

</head>

<body>

<g:render template="/common/header"/>

<div class="main">
<div class="container">

    <div class="span8">
        <g:render template="/common/messages" model="[messages: messages]"/>
        <g:layoutBody/>
    </div>

    <div class="span3">
        <g:render template="/common/sidebar"/>
    </div>

</div>

</div>

<g:render template="/common/footer"/>

<r:layoutResources/>
</body>
</html>