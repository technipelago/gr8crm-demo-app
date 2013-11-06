package com.gr8crm

import grails.events.Listener
import grails.plugins.crm.security.CrmUserRole

/**
 * Generic application services.
 */
class CrmApplicationService {

    def grailsApplication
    def crmSecurityService

    @Listener(namespace = "crmTenant", topic = "getUsers")
    def getTenantUsers(data) {
        CrmUserRole.createCriteria().list() {
            projections {
                user {
                    property('id')
                    property('guid')
                    property('username')
                    property('name')
                    property('email')
                }
            }
            role {
                eq('tenantId', data.tenant)
                inList('name', ['admin', 'user', 'guest', 'partner'])
            }
            cache true
        }.collect {
            [id: it[0], guid: it[1], username: it[2], name: it[3], email: it[4]]
        }.sort { it.name }
    }

    @Listener(namespace = "crmCall", topic = "sendEmail")
    def sendInCallEmail(data) {
        def user = crmSecurityService.getUserInfo(data.user)
        def from
        if (user.email) {
            from = "${user.name} <${user.email}>"
        } else {
            from = grailsApplication.config.grails.mail.default.from
        }
        println ">>> Send email from $from to ${data.to} regarding ${data.subject}"
        sendMail {
            delegate.from from
            to data.to
            subject data.subject
            body data.body
        }
    }
}
