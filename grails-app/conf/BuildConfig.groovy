grails.servlet.version = "3.0"
grails.project.work.dir = "target"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

// uncomment (and adjust settings) to fork the JVM to isolate classpaths
//grails.project.fork = [
//   run: [maxMemory:1024, minMemory:64, debug:false, maxPerm:256]
//]

grails.project.dependency.resolution = {
    inherits("global") {}
    log "warn"
    checksums true
    legacyResolve false

    repositories {
        inherits true

        grailsHome()
        grailsCentral()

        mavenRepo "http://labs.technipelago.se/repo/crm-releases-local/"
        mavenRepo "http://labs.technipelago.se/repo/plugins-releases-local/"

        mavenCentral()
    }

    dependencies {
        compile 'org.apache.poi:poi:3.8'
    }

    plugins {
        build ":tomcat:$grailsVersion"
        runtime ":hibernate:$grailsVersion"

        runtime ":jquery:1.10.0"
        runtime ":resources:1.2"
        runtime ":twitter-bootstrap:2.3.2"

        runtime ":less-resources:1.3.3.1"
        runtime ":zipped-resources:1.0.1"

        runtime ":database-migration:1.3.6"

        compile ':cache:1.1.1'

        runtime "grails.crm:crm-security-shiro:latest.integration"
        runtime "grails.crm:crm-i18n:latest.integration"
        runtime "grails.crm:crm-contact-lite:latest.integration"
        runtime "grails.crm:crm-task-ui:latest.integration"
        runtime "grails.crm:crm-content-ui:latest.integration"

        runtime ":decorator-markdown:0.2"
        runtime ":mail:1.0.1"
    }
}
