package eu.heliovo.hfe.controller

/**
 * Controller for miscellaneous static pages
 * @author MarcoSoldati
 *
 */
class MiscController {

    /**
     * Show the splash screen.
     */
    def splash = {
        render (template: "/misc/splash", model: []);
    }
        
    /**
     * Show the changelog.
     */
    def changelog = {
            render (template: "/misc/changelog", model: []);
    }
}
