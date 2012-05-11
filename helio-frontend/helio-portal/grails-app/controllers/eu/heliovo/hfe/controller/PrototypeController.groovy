package eu.heliovo.hfe.controller

/**
 * Temporary controller to redirect to the main page.
 * This can be removed some time.
 * @author MarcoSoldati
 *
 */
class PrototypeController {

    def explorer() {
        redirect( permanent: true, uri: "../../")
    }
}
