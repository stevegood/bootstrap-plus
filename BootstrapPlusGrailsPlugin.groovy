class BootstrapPlusGrailsPlugin {
    // the plugin version
    def version = "1.0"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.4 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp",
        "grails-app/views/index.gsp"
    ]

    // TODO Fill in these fields
    def title = "Bootstrap Plus Plugin" // Headline display name of the plugin
    def author = "Steve Good"
    def authorEmail = "steve@stevegood.org"
    def description = 'Adds a number of helpful tags to make building bootstrap applications less verbose.'

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/bootstrap-plus"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
    def issueManagement = [ system: "Github", url: "https://github.com/stevegood/bootstrap-plus/issues" ]

    // Online location of the plugin's browseable source code.
    def scm = [ url: "https://github.com/stevegood/bootstrap-plus" ]

    def doWithWebDescriptor = { xml ->
        // TODO Implement additions to web.xml (optional), this event occurs before
    }

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

    def doWithApplicationContext = { ctx ->
        // TODO Implement post initialization spring config (optional)
        Map.metaClass.prepend = {k,v ->
            if (delegate.hasProperty(k) && delegate[k]) {
                delegate[k] = "$v ${delegate[k]}"
            } else {
                delegate[k] = v
            }
        }
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    def onShutdown = { event ->
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
