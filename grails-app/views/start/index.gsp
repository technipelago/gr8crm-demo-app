<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Welcome to GR8 CRM</title>
</head>

<body>

<crm:user>
    <crm:header title="Welcome {0}" args="${[name]}"
            subtitle="${g.formatDate(format: 'EEEE d MMMM yyyy', date: new Date())} - week ${crm.weekNumber(date: new Date())}"/>
</crm:user>

<div class="row-fluid">
    <div class="span4">

        <div id="urgent-important" class="alert alert-error" style="min-height: 120px;">
            <h3>Urgent and important</h3>
            <crm:pluginViews location="urgent-important" var="view">
                <g:render template="${view.template}" model="${view.model}" plugin="${view.plugin}"/>
            </crm:pluginViews>
        </div>

        <div id="important" class="alert alert-success" style="min-height: 120px;">
            <h3>Important</h3>
            <crm:pluginViews location="important" var="view">
                <g:render template="${view.template}" model="${view.model}" plugin="${view.plugin}"/>
            </crm:pluginViews>
        </div>

    </div>

    <div class="span4">

        <div id="urgent" class="alert" style="min-height: 120px;">
            <h3>Urgent</h3>
            <crm:pluginViews location="urgent" var="view">
                <g:render template="${view.template}" model="${view.model}" plugin="${view.plugin}"/>
            </crm:pluginViews>
        </div>

        <div id="later" class="alert alert-info" style="min-height: 120px;">
            <h3>Later</h3>
            <crm:pluginViews location="later" var="view">
                <g:render template="${view.template}" model="${view.model}" plugin="${view.plugin}"/>
            </crm:pluginViews>
        </div>

    </div>

    <div class="span4">

    </div>
</div>

</body>
</html>
