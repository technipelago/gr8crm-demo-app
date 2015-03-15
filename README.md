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

Documentation for each plugin is available on http://gr8crm.github.io

Note that all GR8 CRM plugins are not available in Grails Central Repository yet, but most of them are.
If you want to use a plugin from the list above that is not yet available, create an issue and I will
prioritize the polishing and documentation for that plugin and release it as soon as I can.

## Running the demo application

To test this demo application, you basically just have to clone the repository and start the application with `grails run-app`.

1. Make sure you have Grails 2.4.4 installed (we recommend [GVM](http://gvmtool.net) to handle your Grails installations)
2. Clone this repository
3. cd gr8crm-demo-app
4. grails compile
5. grails run-app
