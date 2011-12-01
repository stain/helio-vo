package eu.heliovo.hfe.service

import org.springframework.web.multipart.MultipartFile

import eu.heliovo.clientapi.utils.STILUtils
import eu.heliovo.hfe.model.result.HelioResult;
import eu.heliovo.hfe.model.result.LocalHelioResult;

/**
 * Services to load, save and parse voTables.
 * @author MarcoSoldati
 *
 */
class VoTableService {

    static transactional = true

    /**
     * Service method to upload a VOTable. 
     * @param file the handle to the uploaded file.
     * @return a HelioResult pointing to the loaded data.
     */
    def parseVoTable(MultipartFile file) {
        // parse file into a starTable. this will throw an exception in case of problems.
        starTable = STILUtils.load(file.inputStream);
        
        HelioResult helioResult = new LocalHelioResult(voTableContent : file.string);
        helioResult.save();
        return helioResult;
    }
}