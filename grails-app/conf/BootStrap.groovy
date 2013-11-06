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
    def crmFeatureService
    def navigationService
    def crmContentService
    def crmContactService
    def crmTaskService

    def init = { servletContext ->

        crmFeatureService.removeApplicationFeature("register")

        // Main horizontal menu
        navigationService.registerItem('main', [controller: 'crmTask', action: 'index', title: 'crmTask.index.label', order: 20])
        navigationService.registerItem('main', [controller: 'crmCalendar', action: 'index', title: 'crmCalendar.index.label', order: 30])
        navigationService.registerItem('main', [controller: 'crmFolder', action: 'list', title: 'crmContent.index.label', order: 80])

        navigationService.updated()

        crmPluginService.registerView('crmContact', 'show', 'tabs',
                [id: "tasks", index: 300, permission: "crmTask:show", label: "Händelser", template: '/crmTask/list', plugin: "crm-task-ui", model: {
                    def result = CrmTask.createCriteria().list([sort: 'startTime', order: 'asc']) {
                        eq('ref', crmCoreService.getReferenceIdentifier(crmContact))
                    }
                    return [bean: crmContact, reference: crmCoreService.getReferenceIdentifier(crmContact),
                            result: result, totalCount: result.totalCount]
                }]
        )
        if (!crmSecurityService.getUser("admin")) {
            loadData(servletContext.getAttribute(ApplicationAttributes.APPLICATION_CONTEXT))
        }
    }

    def destroy = {
    }

    private void loadData(ctx) {
        // Create a user.
        def admin = crmSecurityService.createUser([username: "admin", password: "admin", email: "info@gr8crm.com", name: "Systemadministratör", postalCode: "12345", enabled: true])
        def tenant

        crmSecurityService.addPermissionAlias("permission.all", ["*:*"])

        // Create admin's tenant.
        crmSecurityService.runAs(admin.username) {
            def account = crmAccountService.createAccount([status: "active"],
                    [crmAdmin:5, crmUser: 5, crmContact:1, crmContent:5, crmTask: 1, crmTenant:2])
            tenant = crmSecurityService.createTenant(account, "Demo") // tenant #1
            TenantUtils.withTenant(tenant.id) {
                crmSecurityService.addPermissionToRole("permission.all", "admin")
                crmContentService.createFolder(null, "email", "E-postmallar")
                def web = crmContentService.createFolder(null, "web", "Hemsidans uppbyggnad", "", "")
                crmContentService.createFolder(web, "pages", "Hela webbsidor", "", "")
                crmContentService.createFolder(web, "parts", "Texter som visas på webbsidor", "", "")
                loadTextTemplates()
                loadTasks()
            }
        }
    }

    private void loadTextTemplates() {
        List<File> templates
        if (grailsApplication.warDeployed) {
            templates = grailsApplication.mainContext.getResources("**/WEB-INF/templates/freemarker/**".toString())?.toList().collect { it.file }
        } else {
            // Scan all plugins src/templates for text templates.
            def settings = BuildSettingsHolder.settings
            def dirs = settings.getPluginDirectories()
            // Finally scan the application's src/templates
            dirs << settings.getBaseDir()
            templates = []
            for (dir in dirs) {
                // Look for FreeMarker templates.
                def templatePath = new File(dir, "src/templates/freemarker")
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
                def path = StringUtils.substringAfter(file.parentFile.toString(), "${separator}templates${separator}freemarker")
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
        def type = crmTaskService.createTaskType(name: "Utgående samtal", param: "outbound").save(failOnError: true, flush: true)
        def companies = []
        companies << crmContactService.createCompany(name: "BMW", true)
        companies << crmContactService.createCompany(name: "Nacka kommun", true)
        companies << crmContactService.createCompany(name: "Bilia", true)
        companies << crmContactService.createCompany(name: "Haninge kommun", true)
        companies << crmContactService.createCompany(name: "Bosses Bygg", true)
        def firstNames = ['Lars', 'Göran', 'Anna', 'Ulrika', 'Peter']
        def lastNames = ['Bengtsson', 'Åkesson', 'Dahlman', 'Rosenkvist']
        def random = new Random()
        def cal = Calendar.getInstance()
        cal.clearTime()
        cal.set(Calendar.HOUR_OF_DAY, 9)
        for (firstName in firstNames) {
            for (lastName in lastNames) {
                def contact = crmContactService.createPerson(parent: companies[random.nextInt(companies.size())], firstName: firstName, lastName: lastName, telephone: "08-${System.currentTimeMillis().toString().substring(6)}").save(failOnError: true, flush: true)
                def task = crmTaskService.createTask(name: type.name, type: type,
                        startTime: cal.getTime(), duration: 15,
                        reference: contact, hidden: true, username: 'lars')
                task.save(failOnError: true, flush: true)
                cal.add(Calendar.MINUTE, 15)
            }
        }
    }
}
