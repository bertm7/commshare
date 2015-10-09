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
<r:require module="fileuploader" />

<g:javascript>
    function followLoading() {
        $('.followBtn').attr("disabled", "true");
    }

    function followComplete() {
        $('.followBtn').removeAttr("disabled");
        if ($('.followBtn').text().indexOf("Stop") != -1) {
            $('.followBtn').text("Follow");
        } else {
            $('.followBtn').text("Stop following");
        }
    }

    $(function() {
        $('#profileEdit').bind('hidden', function () {
            $('.qq-upload-button').css("display", "block");
        });
    });

</g:javascript>
</head>

<body>

<div class="row">
    <h2>${user.username}
        <sec:ifLoggedIn>
            <g:if test="${user.id != loggedUserId}">
                <g:remoteLink action="followToggle" id="${user.id}" update="ajaxMessage" onLoading="followLoading();"
                      onComplete="followComplete();" class="btn btn-primary followBtn">
                    <g:followIndicator user="${user}" followText="Follow" stopFollowText="Stop following"/>
                </g:remoteLink>
            </g:if>
        </sec:ifLoggedIn>
    </h2>
</div>

<g:if test="${user.id == loggedUserId}">
    <div id="profileEdit" class="modal hide fade in" style="display: none; ">
        <g:form action="update" id="${user.id}" style="margin:0; padding:0">
            <div class="modal-header">
                <a class="close" data-dismiss="modal">×</a>

                <h3>Edit your profile</h3>
            </div>

            <div class="modal-body">
                <div class="pull-right">
                    <img id="tmpAvatar" src="${g.createLink(action:'viewAvatar', id: user.id)}" alt="${user.username}" class="avatar"/>
                    <uploader:uploader id="${user.id}" params="${[userId: user.id]}" sizeLimit="10000" multiple="false" allowedExtensions="${"['jpeg', 'jpg']"}"  url="${[action:'uploadAvatar']}">
                        <uploader:onComplete>
                            d = new Date();
                            $('#tmpAvatar').attr("src", "${g.createLink(action:'viewAvatar', id: user.id, params: [temporary: true])}&d=" + d.getTime() + Math.random());
                            $('.qq-upload-button').css("display", "none");
                        </uploader:onComplete>
                    </uploader:uploader>
                </div>
                <table>
                    <tr><td>Gender:</td><td><g:select from="${['MALE', 'FEMALE']}" name="gender" value="${user.gender?.id ?: 'MALE'}" class="input-small"/></td><td>Born:</td><td><g:select from="${(1900..2010).reverse()}" name="yearBorn" value="${user.yearBorn ?: 1980}" class="input-small"/></td></tr>
                    <tr><td>Country:</td><td colspan="3"><g:textField name="country" value="${user.country}"/></td></tr>
                    <tr><td>Town:</td><td colspan="3"><g:textField name="town" value="${user.town}"/></td></tr>
                    <tr><td>Info:</td><td colspan="3"><g:textArea name="info" value="${user.info}"/></td></tr>
                </table>
            </div>

            <div class="modal-footer">
                <g:submitButton class="btn btn-primary" name="Save"/>
                <a href="#" class="btn" data-dismiss="modal">Close</a>
            </div>
        </g:form>
    </div>
</g:if>

%{--User description--}%
<div class="row">
    <div class="well">
        <p class="pull-right">
            <img src="${g.createLink(action:'viewAvatar', id: user.id)}" alt="${user.username}" class="avatar"/>
        </p>

        <p>Gender: <strong>${user.gender ?: "?"}</strong></p>
        <p>Born: <strong>${user.yearBorn  ?: "?"}</strong></p>
        <p>Country: <strong>${user.country  ?: "?"}</strong></p>
        <p>Town: <strong>${user.town ?: "?"}</strong></p>
        <g:if test="${user.info}">
            <br/>

            <p>More about ${user.username}: <strong>${user.info}</strong></p>
        </g:if>
        <g:if test="${user.id == loggedUserId}">
            <a data-toggle="modal" href="#profileEdit" class="btn"><i class="icon-align-justify"></i> Edit Profile</a>
        </g:if>
    </div>
</div>

<g:if test="${user.following?.size() > 0}">
    <div class="row">
        <div class="well">
            <g:if test="${user.following?.size() == 1}">
                <p>
                    One user follows ${user.username}: <g:link action="show"
                                                           id="${user.following?.min()?.id}">${user.following?.min()?.username}</g:link>
                </p>
            </g:if>
            <g:elseif test="${user.following?.size() > 1}">
                <p>${user.following?.size()} users follow ${user.username}:
                <g:each in="${user.following}">
                    <g:link action="show" id="${it.id}">${it.username}</g:link>
                </g:each>
                </p>
            </g:elseif>
        </div>
    </div>
</g:if>

<g:if test="${user.groupsOwning}">
    <div class="row">
        <div class="well">
            ${user.username} created communities
            <g:each in="${user.groupsOwning}">
                <g:link controller="group" action="show" id="${it.id}">${it.name}</g:link>
            </g:each>
        </div>
    </div>
</g:if>

<g:if test="${posts}">
    <h3>${user.username} recently wrote:</h3>

    <div class="row">
        <div class="well">
            <g:each in="${posts}">
                <div class="well well-light">
                    <div class="pull-right"><prettytime:display date="${it.dateCreated}" /></div>

                    <p>${it.text}</p>

                    <sec:ifAnyGranted roles="ROLE_ADMIN"><g:link class="btn btn-mini" action="edit" controller="post" id="${it.id}"> <i class='icon-edit'></i> Edit</g:link></sec:ifAnyGranted>

                    <p>In group <g:link controller="group" action="show"
                                        id="${it.group.id}">${it.group.name}</g:link></p>
                </div>
            </g:each>
        </div>
    </div>
</g:if>

<g:if test="${user.posts.count {'*'} > 10}">
    <div class="row">
        <g:paginate controller="user" action="show" params="[id: user.id]" total="${user.posts.count {'*'} }" next="&raquo;" prev="&laquo;" max="10" />
    </div>
</g:if>
