// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
        all: '*/*',
        atom: 'application/atom+xml',
        css: 'text/css',
        csv: 'text/csv',
        form: 'application/x-www-form-urlencoded',
        html: ['text/html', 'application/xhtml+xml'],
        js: 'text/javascript',
        json: ['application/json', 'text/json'],
        multipartForm: 'multipart/form-data',
        rss: 'application/rss+xml',
        text: 'text/plain',
        xml: ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart = false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

crm.security.controllers.public = ['home', 'crmFileAccess', 'crmPageNotFound']

crm.feature.crmContact.required = true
crm.feature.crmSales.required = true
crm.feature.crmProduct.required = true
crm.feature.crmTelemarketing.required = true
crm.feature.crmContent.required = true

crm.theme.logo.default = "/images/gr8crm-logo-small.png"
crm.theme.logo.small = "/images/gr8crm-logo-small.png"
crm.theme.logo.large = "/images/gr8crm-logo.png"

crm.currency.default = 'SEK'

crm.tag.favorite = "favorite"
crm.favorite.icon = "icon-star"

selection.uri.parameter = "q"
selection.uri.encoding = "base64"

crm.content.include.tenant = 1L

crm.task.attenders.enabled = false

recentDomain.autoscan.actions = ['crmContact:show', 'crmSalesProject:show', 'crmCall:show']

grails.plugin.databasemigration.changelogLocation = "grails-app/migrations"
grails.plugin.databasemigration.changelogFileName = "changelog.groovy"
grails.plugin.databasemigration.updateOnStart = false
grails.plugin.databasemigration.updateOnStartFileNames = ["changelog.groovy"]

environments {
    development {
        grails {
            //serverURL = "http://localhost:8080/gr8crm"
            mail {
                host = "smtprelay1.telia.com"
                port = 25
                //host = "localhost"
                //port = com.icegreen.greenmail.util.ServerSetupTest.SMTP.port
            }
            mail.default.from = "goran@technipelago.se"
            logging.jul.usebridge = true
        }
        crm {
            content {
                file {
                    path = "./content-repository"
                }
            }
        }
    }
    production {
        grails.logging.jul.usebridge = false
        app.context = "/crm"
        grails.serverURL = "http://www.technipelago.se/crm"
        // Resources moved out of Tomcat install and served by Apache HTTPD.
        grails.resources.work.dir = "/home/www/crm/static-resources"
        crm {
            content {
                file {
                    path = "/home/www/crm/content-repository"
                }
            }
        }
    }
}

// log4j configuration
log4j = {
    // Example of changing the log pattern for the default console appender:
    //
    //appenders {
    //    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
    //}

    error 'org.codehaus.groovy.grails.web.servlet',        // controllers
            'org.codehaus.groovy.grails.web.pages',          // GSP
            'org.codehaus.groovy.grails.web.sitemesh',       // layouts
            'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
            'org.codehaus.groovy.grails.web.mapping',        // URL mapping
            'org.codehaus.groovy.grails.commons',            // core / classloading
            'org.codehaus.groovy.grails.plugins',            // plugins
            'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
            'org.springframework',
            'org.hibernate',
            'net.sf.ehcache.hibernate'
}
