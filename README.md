# GR8 CRM - Demo Application

This Grails application uses GR8 CRM plugins to create a simple
Contact Management and To-Do application.

The application handles
* Companies
* Contacts
* Tasks

The GR8 CRM "Ecosystem" currently contains over 40 Grails plugins. Some of them are listed below.

Main contributors to GR8 CRM plugins are https://github.com/technipelago and https://github.com/goeh

The company [Technipelago AB](http://www.technipelago.se/ "Technipelago AB") based in Stockholm, Sweden has several GR8 CRM applications in production.

| Plugin Name                  | Description
| ---------------------------- | ------------------------------------------------
| crm-blog+crm-blog-ui         | Blog presentation and authoring (-ui)
| crm-campaign+crm-campaign-ui | Marketing campaign execution and management (-ui)
| crm-contact+crm-contact-lite | Contact Management (companies and individuals)
| crm-content+crm-content-ui   | Content Management and authoring (-ui)
| crm-core                     | Core features like multi tenancy (used by most other plugins)
| crm-email                    | Send emails using Grails events
| crm-feature                  | Turn application features on/off for specific users or roles
| crm-i18n                     | Store localized messages in database to support runtime changes
| crm-invitation               | Invite external users to your application
| crm-notes                    | Add short notes (subject+body) to any domain instance
| crm-notification             | Notify users about stuff with a central notification center
| crm-order+crm-order-ui       | Order Management
| crm-product+crm-product-ui   | Product and inventory management including administration (-ui)
| crm-security                 | Core security services
| crm-security-shiro           | Apache Shiro security implementation
| crm-security-ui              | Back Office features for user and role administration
| crm-syslog                   | Log application events to database
| crm-tags                     | Tag any domain instance
| crm-task+crm-task-ui         | Task/Calendar Management
| crm-ui-bootstrap             | Twitter Bootstrap user interface (used by most -ui plugins)

## Running the demo application

To test this demo application, you basically just have to clone the repository and start the application with `grails run-app`.

. Make sure you have Grails 2.2.4 installed
. Clone this repository
. cd gr8crm-demo-app
. grails compile
. grails run-app

## Using GR8CRM plugins in your own Grails application

The GR8CRM plugins are not yet available in Grails Central repository. The reason is we don't feel they are polished enough
to be officially released (yet). We plan to release most of the plugins during spring/summer 2014 but we can't promise anything.
Until all plugins are available in Grails Central you have to include two custom *technipelago* repositories in your BuildConfig.groovy.

    repositories {
        inherits true

        grailsHome()
        grailsCentral()

        mavenRepo "http://labs.technipelago.se/repo/crm-releases-local/"
        mavenRepo "http://labs.technipelago.se/repo/plugins-releases-local/"

        mavenCentral()
    }