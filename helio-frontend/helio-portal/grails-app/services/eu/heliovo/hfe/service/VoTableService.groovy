package eu.heliovo.hfe.service

import org.springframework.web.multipart.MultipartFile

import eu.heliovo.clientapi.utils.STILUtils
import eu.heliovo.hfe.model.result.HelioResult;
import eu.heliovo.hfe.model.result.LocalHelioResult;
import eu.heliovo.hfe.model.security.User;

/**
 * Services to load, save and parse voTables.
 * @author MarcoSoldati
 *
 */
class VoTableService {

    static transactional = true

    /**
     * Service method to parse and store the VOTable to a database 
     * @param file the handle to the uploaded file.
     * @return a HelioResult pointing to the loaded data.
     */
    def parseAndSaveVoTable(MultipartFile file) {
        // parse file into a starTable. this will throw an exception in case of problems.
        starTable = STILUtils.load(file.inputStream);

        User user = User.get(springSecurityService.principal.id);
        HelioResult helioResult = new LocalHelioResult(originalFileName : file.originalFilename, voTableContent : file.inputStream.text, user: user);
        helioResult.save();
        return helioResult;
    }
}