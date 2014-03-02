<%@ page import="org.apache.commons.lang.StringUtils; grails.plugins.crm.core.TenantUtils; grails.util.GrailsNameUtils;" %><!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><g:layoutTitle default="${message(code: 'app.title', default: 'GR8 CRM')}"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <r:require modules="application, crm"/>

    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">

    <link rel="apple-touch-icon" sizes="144x144" href="${resource(dir: 'images', file: 'apple-touch-icon-144.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-114.png')}">
    <link rel="apple-touch-icon" sizes="72x72" href="${resource(dir: 'images', file: 'apple-touch-icon-72.png')}">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">

    <r:script>

        $(document).ajaxError(function(e, xhr, settings, exception) {
            if (xhr.status == 403) {
                window.location.href = "${createLink(mapping: 'home', absolute: true)}";
            } else if (xhr.status == 404) {
                alert('Requested URL not found.');
            } else if (xhr.status == 500) {
                alert('Error.\nInternal server error.');
            } else if (errStatus == 'parsererror') {
                alert('Error.\nParsing JSON Request failed.');
            } else if (errStatus == 'timeout') {
                alert('Request timed out.\nPlease try later');
            } else {
                alert('Unknown Error.');
            }
        });
    </r:script>
    <r:layoutResources/>
    <g:layoutHead/>
    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
          <script src="${resource(plugin: 'crm-ui-bootstrap', dir: 'js', file: 'html5.js')}"></script>
    <![endif]-->
</head>

<body class="${controllerName ?: 'home'}-body">

<div id="global-message" class="hide">
    <g:if test="${flash.info || flash.message}">
        <div class="alert-info">
            ${flash.info ?: flash.message}
        </div>
    </g:if>
    <g:if test="${flash.success}">
        <div class="alert-success">
            ${flash.success}
        </div>
    </g:if>
    <g:if test="${flash.warning}">
        <div class="alert-warning">
            ${flash.warning}
        </div>
    </g:if>
    <g:if test="${flash.error}">
        <div class="alert-error">
            ${flash.error}
        </div>
    </g:if>
</div>

<div id="header-wrapper" class="row-fluid hidden-phone">
    <div class="span3">
        <g:link mapping="home">
            <g:img dir="images" file="gr8crm-logo-small.png" id="logo"/>
        </g:link>
    </div>
</div>

<div class="navbar">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <g:link mapping="home" class="brand visible-phone">GR8 CRM</g:link>

            <div class="nav-collapse collapse">
                <g:render template="/webmenu"/>
            </div>
        </div>
    </div>
</div>

<div id="content-wrapper" class="controller-${controllerName ?: 'home'} action-${actionName ?: 'index'}" role="main">
    <g:layoutBody/>
</div>

<div id="footer-wrapper">
    <g:render template="/webfooter"/>
</div>

<r:layoutResources/>

</body>
</html>
