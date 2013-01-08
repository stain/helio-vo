package eu.heliovo.hfe.controller.help

/**
 * Controller for the help pages
 * @author MarcoSoldati
 *
 */
class HelpController {
	static layout = 'help'

    /**
     * Check if there is a param called page and render this view, otherwise render the index.gsp (default action)
     */
    def index() {
        if (params.page) {
            if (params.page.matches("\\w+")) {
                render (view : '/help/' + params.page)
            } else {
                response.sendError 504, "Error: page name must be an alphanumeric string."
            }
        } 
    }
}
