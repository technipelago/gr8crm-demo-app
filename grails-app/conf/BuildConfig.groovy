grails.servlet.version = "3.0"
grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6
grails.project.source.level = 1.6
//grails.project.war.file = "target/${appName}-${appVersion}.war"

grails.project.fork = [
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    test: false,
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {
    inherits("global") {}
    log "warn"
    checksums true
    legacyResolve false

    repositories {
        inherits true
        grailsCentral()
        mavenLocal()
        mavenRepo "http://repo.grails.org/grails/repo/"
        mavenCentral()
    }

    dependencies {
    }

    plugins {
        build ":tomcat:8.0.22"

        runtime (":hibernate4:4.3.10") {
            excludes "net.sf.ehcache:ehcache-core"  // remove this when http://jira.grails.org/browse/GPHIB-18 is resolved
            export = false
        }

        compile ":database-migration:1.4.1"

        compile ":greenmail:1.3.4"

        compile ":mail:1.0.7"
        compile ":decorator:1.1"
        compile ":decorator-markdown:0.4"

        compile ":crm-core:2.4.3"
        compile ":crm-i18n:2.4.0"
        compile ":crm-ui-bootstrap:2.4.3"
        compile ":crm-security:2.4.3"
        compile ":crm-security-shiro:2.4.2"
        compile ":crm-content:2.4.5"
        compile ":crm-content-ui:2.4.4"
        compile ":crm-contact-ui:2.4.3"
        compile ":crm-task-ui:2.4.3"
    }
}
