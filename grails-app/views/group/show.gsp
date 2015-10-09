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
</head>

<body>

<div class="row">
    <h2>${group.name}</h2>
</div>

<div class="row">
    <div class="well">
        ${group.description}
    </div>
</div>

<div class="row">
    <g:form class="well" action="save" controller="post">
        <sec:ifLoggedIn>
            <h4>What's on your mind?</h4>
            <g:hiddenField name="group.id" value="${group.id}"/>
            <g:textArea name="text" placeholder="Enter text..." class="input-block-level"/><br />
            <div class="controls">
                <g:submitButton name="Save" class="btn btn-primary" />
            </div>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
            <h4>Please <g:link controller="login" action="auth">Log in</g:link> to contribute to this community!</h4>
        </sec:ifNotLoggedIn>
    </g:form>
</div>

<g:if test="${posts}">
    <div class="row">
        <div class="well">
            <g:each in="${posts}">
                <div class="well well-light">
                    <div class="pull-right"><prettytime:display date="${it.dateCreated}" /></div>
                    <div class="pull-left" style="width:60px; padding-right: 15px;">
                        <img src="${g.createLink(controller: 'user', action:'viewAvatar', id: it.user.id)}" alt="${it.user.username}" class="smallAvatar"/>
                    </div>
                    <p><i class="icon-comment"></i> <g:link controller="user" action="show" id="${it.user.id}">${it.user.username}</g:link> wrote:</p>
                    <p>${it.text}</p>
                    <sec:ifAnyGranted roles="ROLE_ADMIN"><g:link class="btn btn-mini" action="edit" controller="post" id="${it.id}"> <i class='icon-edit'></i> Edit</g:link></sec:ifAnyGranted>
                </div>
            </g:each>
        </div>
    </div>
</g:if>

<g:if test="${group.posts.count {'*'} > 10}">
    <div class="row">
        <g:paginate controller="group" action="show" params="[id: group.id]" total="${group.posts.count {'*'} }" next="&raquo;" prev="&laquo;" max="10" />
    </div>
</g:if>
