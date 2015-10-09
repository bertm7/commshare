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

<g:if test="${groups}">
    <div class="row">
        <div class="accordion" id="grAccordion">
            <g:each in="${groups}" status="i" var="it">
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <div class="pull-right" style="padding: 8px 15px;">
                            ${it.creator.username},
                            <prettytime:display date="${it.dateCreated}" />
                        </div>
                        <a class="accordion-toggle" data-toggle="collapse" data-parent="#grAccordion" href="#collapse${i}">
                            ${it.name}
                        </a>
                    </div>
                    <div id="collapse${i}" class="accordion-body collapse <g:if test="${i == 0}">in</g:if>">
                        <div class="accordion-inner">
                            ${it.description}<br />
                            <g:link class="btn btn-mini" action="show" id="${it.id}"> <i class='icon-circle-arrow-right'></i> Visit</g:link>
                            <sec:ifAnyGranted roles="ROLE_ADMIN"><g:link class="btn btn-mini" action="edit" id="${it.id}"> <i class='icon-edit'></i> Edit</g:link></sec:ifAnyGranted>
                        </div>
                    </div>
                </div>
            </g:each>
        </div>
    </div>
</g:if>

<g:if test="${groupCount > 10}">
    <div class="row">
        <g:paginate controller="group" action="list" total="${groupCount}" next="&raquo;" prev="&laquo;" max="10" />
    </div>
</g:if>

<div class="row">
    <g:form class="well collapse-group" action="save">
        <h4>Cannot find any community suitable for you?
        <sec:ifLoggedIn>
            <button class="btn btn-primary" id="createCommBtn" data-toggle="collapse">Create your own community!</button></h4>
            <div class="collapse" id="createCommForm">
                <g:textField name="name" placeholder="Enter community name..." class="input-block-level" /><br />
                <g:textArea id="groupTextArea" name="description" placeholder="Enter community description..." class="input-block-level" rows="22"/><br />
                <recaptcha:ifEnabled>
                    <recaptcha:recaptcha theme="clean"/>
                </recaptcha:ifEnabled>
                <br/>
                <div class="controls">
                    <g:submitButton name="Create" class="btn btn-primary" />
                </div>
            </div>
        </sec:ifLoggedIn>
        <sec:ifNotLoggedIn>
            <g:link controller="login" action="auth">Log in</g:link> to create your own community!</h4>
        </sec:ifNotLoggedIn>
    </g:form>
</div>

<g:javascript>
    $('#groupTextArea').wysihtml5();
    $(function(){
        $('#createCommBtn').click(function(e) {
            $("#createCommForm").collapse({toggle: true});
            $('#createCommBtn').hide(200);
        });
    });
</g:javascript>

</body>
</html>