import grails.plugins.crm.core.TenantUtils
import grails.plugins.crm.task.CrmTask
import org.codehaus.groovy.grails.commons.ApplicationAttributes

class BootStrap {

    def crmAccountService
    def crmPluginService
    def crmCoreService
    def crmSecurityService
    def crmContentService
    def crmContentImportService
    def crmContactService
    def crmTaskService

    def init = { servletContext ->

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
            tenant = crmSecurityService.createTenant(account, "Website") // tenant #1
            // Initialize the first tenant.
            TenantUtils.withTenant(tenant.id) {
                crmSecurityService.addPermissionToRole("permission.all", "admin")
                // Add some common content folders.
                def web = crmContentService.createFolder(null, "web", "Web", "", "")
                crmContentService.createFolder(web, "pages", "Web pages", "", "")
                crmContentService.createFolder(web, "parts", "Web page fragments", "", "")
                crmContentImportService.importFiles("templates/crm", "admin") // Load website pages.
            }
            // Create a tenant to hold the demo data.
            tenant = crmSecurityService.createTenant(account, "CRM") // tenant #2
            // Initialize the second tenant.
            TenantUtils.withTenant(tenant.id) {
                crmSecurityService.addPermissionToRole("permission.all", "admin")
                // Add some common content folders.
                crmContentService.createFolder(null, "docs", "Documents")
                crmContentService.createFolder(null, "email", "Email templates")
                loadTasks() // Load example tasks
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
