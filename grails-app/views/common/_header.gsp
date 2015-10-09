<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            %{--
  - Copyright (c) 2013 Rene Puchinger.
  - http://renepuchinger.com
  --}%

<g:link controller="group" class="brand"><g:img dir="images" file="logo.png" alt="commshare"/></g:link>
            <div class="nav-collapse collapse">
                <ul class="nav nav-pills">
                    <li <g:if test="${params.controller == 'group'}">class="active"</g:if>><g:link controller="group">Communities</g:link></li>
                    <li <g:if test="${params.controller == 'user'}">class="active"</g:if>><g:link controller="user">Users</g:link></li>
                    <li <g:if test="${params.controller == 'about'}">class="active"</g:if>><g:link controller="about"><i class="icon-question-sign"></i> About</g:link></li>
                    <sec:ifAnyGranted roles="ROLE_ADMIN"><li><g:link controller="securityInfo" action="config"><i class="icon-wrench"></i> Administration</g:link></li></sec:ifAnyGranted>
                </ul>
                <div class="pull-right" style="line-height: 38px;">
                    <sec:ifLoggedIn>
                        <i class='icon-user'></i>
                        Welcome, <g:link controller="user" action="show" id="${sec.loggedInUserInfo(field: 'id')}">
                        <sec:username/>
                    </g:link>
                        | <i class='icon-off'></i> <g:link controller="logout">Log out</g:link>
                    </sec:ifLoggedIn>
                    <sec:ifNotLoggedIn>
                        <i class='icon-user'></i> <g:link controller="login">Log in</g:link>&nbsp;&nbsp;&nbsp;
                        <i class='icon-plus'></i> <g:link controller="register">Register</g:link>
                    </sec:ifNotLoggedIn>
                </div>
            </div>
        </div>
    </div>
</div>
