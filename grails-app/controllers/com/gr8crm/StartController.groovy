package com.gr8crm

import grails.plugins.crm.core.TenantUtils

import javax.servlet.http.HttpServletResponse

/**
 * Welcome screen after login.
 */
class StartController {

    static navigation = [
            [group: 'main',
                    order: 1,
                    title: 'Start',
                    action: 'index'
            ]
    ]

    def crmSecurityService

    def index() {
    }

    def tenant(Long id) {
        if (crmSecurityService.isValidTenant(id)) {
            switchTenant(id)
            if (params.referer) {
                redirect(uri: params.referer - request.contextPath)
            } else {
                redirect(mapping: 'start')
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN)
        }
    }

    private boolean switchTenant(Long id) {
        def oldTenant = TenantUtils.getTenant()
        if (id != oldTenant) {
            TenantUtils.setTenant(id)
            request.session.tenant = id
            def username = crmSecurityService.currentUser?.username
            event(for: "crm", topic: "tenantChanged",
                    data: [tenant: id, newTenant: id, oldTenant: oldTenant, user: username, request: request])
            return true
        }
        return false
    }
}
