class UrlMappings {

	static mappings = {
        name 'start': "/start" {
            controller = 'start'
            action = 'index'
        }
        name 'crm-tenant-activate': "/activate/$id" {
            controller = 'start'
            action = 'tenant'
            constraints {
                id(matches: /\d+/)
            }
        }
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

        name 'home': "/"(view: "/index.html")

        "404"(controller: "crmPageNotFound", action: "index")
        "500"(view:'/error')
	}
}
