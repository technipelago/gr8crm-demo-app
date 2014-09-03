import grails.plugins.crm.core.TenantUtils
import grails.plugins.crm.task.CrmTask
import grails.util.BuildSettingsHolder
import groovy.io.FileType
import org.apache.commons.lang.StringUtils
import org.codehaus.groovy.grails.commons.ApplicationAttributes

class BootStrap {

    def grailsApplication
    def crmAccountService
    def crmPluginService
    def crmCoreService
    def crmSecurityService
    def navigationService
    def crmContentService
    def crmContactService
    def crmTaskService

    def init = { servletContext ->

        // Add some items to the main horizontal menu
        navigationService.registerItem('main', [controller: 'crmTask', action: 'index', title: 'crmTask.index.label', order: 20])
        navigationService.registerItem('main', [controller: 'crmCalendar', action: 'index', title: 'crmCalendar.index.label', order: 30])
        navigationService.registerItem('main', [controller: 'crmFolder', action: 'list', title: 'crmContent.index.label', order: 80])
        navigationService.updated()

        // Add a tab in the contact screen to list all tasks (pending and completed) for the contact.
        crmPluginService.registerView('crmContact', 'show', 'tabs',
                [id: "tasks", index: 300, permission: "crmTask:show", label: "Tasks", template: '/crmTask/list', plugin: "crm-task-ui", model: {
                    def result = CrmTask.createCriteria().list([sort: 'startTime', order: 'asc']) {
                        eq('ref', crmCoreService.getReferenceIdentifier(crmContact))
                    }
                    return [bean  : crmContact, reference: crmCoreService.getReferenceIdentifier(crmContact),
                            result: result, totalCount: result.totalCount]
                }]
        )

        // Load some demo data first time we run.
        if (!crmSecurityService.getUser("admin")) {
            loadData(servletContext.getAttribute(ApplicationAttributes.APPLICATION_CONTEXT))
        }
    }

    def destroy = {
    }

    private void loadData(ctx) {
        // Create a user.
        def admin = crmSecurityService.createUser([username: "admin", password: "admin", email: "info@gr8crm.com", name: "Administrator", postalCode: "12345", enabled: true])
        def tenant

        // The admin user can do anything.
        crmSecurityService.addPermissionAlias("permission.all", ["*:*"])

        // Create admin's account and one tenant.
        crmSecurityService.runAs(admin.username) {
            // Create an account
            def account = crmAccountService.createAccount([status: "active"],
                    [crmAdmin: 5, crmUser: 5, crmContact: 1, crmContent: 5, crmTask: 1, crmTenant: 2])
            // Create a tenant to hold the website data.
            tenant = crmSecurityService.createTenant(account, "Wiki") // tenant #1
            // Initialize the first tenant.
            TenantUtils.withTenant(tenant.id) {
                crmSecurityService.addPermissionToRole("permission.all", "admin")
                // Add some common content folders.
                def web = crmContentService.createFolder(null, "web", "Web", "", "")
                crmContentService.createFolder(web, "pages", "Web pages", "", "")
                crmContentService.createFolder(web, "parts", "Web page fragments", "", "")
                loadTextTemplates() // Load website pages.
            }
            // Create a tenant to hold the demo data.
            tenant = crmSecurityService.createTenant(account, "CRM") // tenant #2
            // Initialize the second tenant.
            TenantUtils.withTenant(tenant.id) {
                crmSecurityService.addPermissionToRole("permission.all", "admin")
                // Add some common content folders.
                crmContentService.createFolder(null, "email", "Email templates")
                loadTasks() // Load example tasks
            }
        }
    }

    private void loadTextTemplates() {
        List<File> templates
        if (grailsApplication.warDeployed) {
            templates = grailsApplication.mainContext.getResources("**/WEB-INF/templates/crm/**".toString())?.toList().collect {
                it.file
            }
        } else {
            // Scan all plugins src/templates for text templates.
            def settings = BuildSettingsHolder.settings
            def dirs = settings.getPluginDirectories()
            // Finally scan the application's src/templates
            dirs << settings.getBaseDir()
            templates = []
            for (dir in dirs) {
                // Look for FreeMarker templates.
                def templatePath = new File(dir, "src/templates/crm")
                if (templatePath.exists()) {
                    templatePath.eachFileRecurse(FileType.FILES) { file ->
                        templates << file
                    }
                }
            }
        }

        if (templates) {
            String separator = File.separator
            for (file in templates.findAll { it.file && !it.hidden }) {
                def path = StringUtils.substringAfter(file.parentFile.toString(), "${separator}templates${separator}crm")
                def folder = crmContentService.getFolder(path)
                if (!folder) {
                    folder = crmContentService.createFolders(path)
                }
                crmContentService.createResource(file, null, folder, [status: "shared", username: 'admin', overwrite: true])
                log.debug "Loaded template $file into folder /${folder.path.join('/')}"
            }
        }
    }

    private void loadTasks() {
        def type = crmTaskService.createTaskType(name: "Outbound telephone call", param: "outbound").save(failOnError: true, flush: true)
        def company = crmContactService.createRelationType(name: "Company", param: "company").save(failOnError: true)
        def companies = []
        companies << crmContactService.createCompany(name: "BMW", true)
        companies << crmContactService.createCompany(name: "ACME Inc.", true)
        companies << crmContactService.createCompany(name: "IKEA", true)
        companies << crmContactService.createCompany(name: "Volvo", true)
        companies << crmContactService.createCompany(name: "Google", true)
        def firstNames = ['Larry', 'Joe', 'Anna', 'Denise', 'Peter']
        def lastNames = ['Ericsson', 'Stephenson', 'Freeman', 'Young']
        def random = new Random()
        def cal = Calendar.getInstance()
        cal.clearTime()
        cal.set(Calendar.HOUR_OF_DAY, 9)
        for (firstName in firstNames) {
            for (lastName in lastNames) {
                def contact = crmContactService.createPerson(related: [companies[random.nextInt(companies.size())], company], firstName: firstName, lastName: lastName, telephone: "08-${System.currentTimeMillis().toString().substring(6)}", true)
                if(contact.hasErrors()) {
                    throw new RuntimeException("Failed to create contact: ${contact.errors.allErrors}")
                } else {
                    def task = crmTaskService.createTask(name: type.name, type: type,
                            startTime: cal.getTime(), duration: 15,
                            reference: contact, hidden: true, username: 'admin')
                    task.save(failOnError: true, flush: true)
                    cal.add(Calendar.MINUTE, 15)
                }
            }
        }
    }
}
