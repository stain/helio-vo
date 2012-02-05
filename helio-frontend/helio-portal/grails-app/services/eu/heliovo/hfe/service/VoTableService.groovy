package eu.heliovo.hfe.service

import org.springframework.web.multipart.MultipartFile
import org.w3c.dom.NodeList

import uk.ac.starlink.votable.FieldElement
import uk.ac.starlink.votable.LinkElement
import uk.ac.starlink.votable.ParamElement
import uk.ac.starlink.votable.TableElement
import uk.ac.starlink.votable.VOElement
import uk.ac.starlink.votable.VOStarTable
import eu.heliovo.clientapi.utils.STILUtils
import eu.heliovo.hfe.model.result.HelioResult
import eu.heliovo.hfe.model.result.LocalVOTableResult
import eu.heliovo.hfe.model.result.RemoteVOTableResult
import eu.heliovo.hfe.model.security.User

/**
 * Services to load, save and parse voTables.
 * @author MarcoSoldati
 *
 */
class VoTableService {
    /**
     * Use transactions for db access.
     */
    static transactional = true

    /**
     * Auto-wire the spring security service.
     */
    def springSecurityService;

    /**
     * Service method to parse and store the VOTable to a database 
     * @param file the handle to the uploaded file.
     * @return a HelioResult pointing to the loaded data.
     */
    def Map<String, Object> parseAndSaveVoTable(MultipartFile file) {

        // get the currently logged in user or create a new one.
        def user = User.get(springSecurityService.principal.id)
        def votableContent = file.inputStream.text;
        HelioResult helioResult = new LocalVOTableResult(originalFileName : file.originalFilename,
                voTableContent : votableContent , user: user);


        // parse file into a starTable. this will throw an exception in case of problems.
        def voTableModel = createVOTableModel(helioResult);

        // otherwise we can save the object in the database.
        if (!helioResult.save(true)) {
            throw new RuntimeException("Internal Error: unable to store helioResult. Cause: " + helioResult.errors)
        }
        // and set the newly created id of the votable
        voTableModel["id"] = helioResult.id;

        return voTableModel;
    }

    /**
     * Create a voTable model structure from a given HelioResult
     * @param helioResult the helioResult to load into a structure
     * @return the votableModel consisting of a map of headers and tables.
     */
    def Map<String, Object> createVOTableModel(HelioResult helioResult) {
        VOElement votableModel;
        if (helioResult instanceof LocalVOTableResult) {
            votableModel = STILUtils.readVOElement(new ByteArrayInputStream(helioResult.voTableContent.getBytes()), helioResult.originalFileName);
            //votableModel = STILUtils.readVOElement(new URL("http://hec.ts.astro.it/hec/hec_gui_fetch.php?cmd=http%3A%2F%2Fhec.ts.astro.it%3A8081%2Fstilts%2Ftask%2Fsqlclient%3Fdb%3Djdbc%3Apostgresql%3A%2F%2Fhec.ts.astro.it%2Fhec%26user%3Dapache%26sql%3Dselect+%2A+from+goes_sxr_flare+where+time_start%3E%3D%272011-02-09+00%3A00%3A00%27+AND+time_start%3C%3D%272011-03-09+23%3A59%3A59%27%26ofmt%3Dvotable&type=votable"));
        } else if (helioResult instanceof RemoteVOTableResult) {
            votableModel = STILUtils.readVOElement(new URL(helioResult.url));
        } else {
            throw new IllegalArgumentException("Unknown type of helioResult: " + helioResult)
        }

        // create a table model to hold the extracted information
        def tableModel = [:]

        // get some metadata
        tableModel["id"] = helioResult.id
        
        tableModel["fileName"] = getFilename(helioResult)
        
        tableModel["actions"] = [] as Set

        // get header info
        // Find DESCRIPTION element
        tableModel["description"] =""
        VOElement[] descriptions = votableModel.getChildrenByName("DESCRIPTION")
        for (VOElement descElement : descriptions) {
            tableModel["description"] += descElement.getTextContent()
        }

        // Find INFO elements.
        tableModel["infos"] = []
        VOElement[] infoElements = votableModel.getChildrenByName("INFO")
        for (VOElement infoElement : infoElements) {
            def info = handleInfoElement(infoElement, [:])
            tableModel["infos"].add(info)
        }
        if (infoElements.length > 0) {
            tableModel['actions'].add('info')
        }

        // todo: handle PARAM, GROUP elements, probably not needed for HELIO

        // get tables
        def tableList = []
        NodeList resources = votableModel.getElementsByTagName("RESOURCE");
        for (int i = 0; i < resources.length; i++) {
            VOElement resource = resources.item(i);
            // Find resource INFO elements.
            infoElements = resource.getChildrenByName("INFO");
            def infos = [];
            for (VOElement infoElement : infoElements) {
                def info = handleInfoElement(infoElement, [:])
                infos.add(info)
            }
            TableElement[] tables = resource.getChildrenByName( "TABLE" );
            if (tables.size() == 0) {
                // a resource without any tables in it.
                def table = [:];
                table["type"] = "empty_resource" // just a resource
                table["infos"] = infos // the info of the parent resource.
                tableList.add(table);
            } else {
                for (TableElement tableElement : tables) {
                    def table = handleVOElement(tableElement, [:])
    
                    table["type"] = "table" // a real table
                    table["actions"] = [] as Set // actions that are globally available 
                    tableModel["actions"].add('extract') 
                    table["rowactions"] = new LinkedHashSet() // actions per row
                    
                    // the info of the parent resource.
                    // if a resource contains two tables the info will be duplicated.
                    table["infos"] = infos
                    if (infos.size() > 0) {
                        table['actions'].add('info')
                    }
                    
                    // extract fields
                    table["fields"] = []
                    
                    // add synthetic id column
                    //table["fields"].add(createIdField([:]))
                    
                    FieldElement[] fieldElements = tableElement.getFields()
                    for (FieldElement fieldElement : fieldElements) {
                        def field = handleFieldElement(fieldElement, [:])
                        table["fields"].add(field)
                        if (field['rendering_hint'] =='url') {
                            tableModel['actions'].add('download');
                        }
                    }
    
                    // extract links
                    table["links"] = []
                    LinkElement[] linkElements = tableElement.getLinks()
                    for (LinkElement linkElement : linkElements) {
                        def link = handleLinkElement(linkElement, [:])
                        table["links"].add(link)
                    }
    
                    // extract params
                    table["params"] = []
                    ParamElement[] paramElements = tableElement.getParams()
                    for (ParamElement paramElement : paramElements) {
                        def param = handleParamElement(paramElement, [:])
                        table["params"].add(param)
                    }
    
                    // extrakt data
                    def starTable = new VOStarTable(tableElement);
                    table["data"] = starTable
                    tableList.add(table);
                }
            }
        }
        tableModel["tables"] = tableList
        return tableModel;
    }

    /**
     * Get the HELIO Filename either from the upload file name or the URL.
     * @param helioResult the helioResult to load
     * @return the fileName (without any path)
     */
    public getFilename(HelioResult helioResult) {
        String filename;
        if (helioResult instanceof LocalVOTableResult) {
            filename = helioResult.originalFileName;
        } else if (helioResult instanceof RemoteVOTableResult) {
            String url = helioResult.url
            filename = url.substring(url.lastIndexOf('/') + 1)
        } else {
            throw new IllegalArgumentException("Unknown type of helioResult: " + helioResult)
        }
        return filename;
    }

    /**
     * Read the content of a VOTable from the database
     * @param helioResult the result to parse. Must not be null.
     * @return
     */
    public getContent(HelioResult helioResult) {
        if (helioResult instanceof LocalVOTableResult) {
            return helioResult.voTableContent
        } else if (helioResult instanceof RemoteVOTableResult) {
            return new URL(helioResult.url).text
        } else {
            throw new IllegalArgumentException("Unknown type of helioResult: " + helioResult)
        }
    }
    
    /**
     * Fill the attributes of a field element into a map
     * @param fieldElement the field element to analyze
     * @param field the map to fill in
     * @return the field map
     */
    private handleFieldElement(FieldElement fieldElement, field) {
        handleVOElement(fieldElement, field)
        field["nullValue"]= fieldElement.getNull()
        field["ucd"] = fieldElement.ucd
        field["unit"] = fieldElement.unit
        field["utype"] = fieldElement.utype
        field["xtype"] = fieldElement.xtype
        // todo: use UCD
        
        if (fieldElement.name == 'hec_id') {
            field["rendering_hint"] = 'hidden'
        } else if (fieldElement.name == 'url') {
            field["rendering_hint"] = 'url';
        } else {
            field["rendering_hint"] = 'text'
        }
        
        if (fieldElement.ucd == 'meta.ref.url') {
            field["rendering_hint"] = 'url';
        }
        return field
    }

    /**
     * Fill the attributes of a link element into a map
     * @param linkElement the link element to analyze
     * @param link the map to fill in
     * @return the link map
     */
    private handleLinkElement(LinkElement linkElement, link) {
        handleVOElement(linkElement, link)
        link["handle"]= linkElement.handle // title
        link["href"]= linkElement.href // the actual link
        return link
    }

    /**
     * Fill the attributes of a param element into a map
     * @param paramElement the param element to analyze
     * @param param the map to fill in
     * @return the param map
     */
    private handleParamElement(ParamElement paramElement, param) {
        handleFieldElement(paramElement, param)
        def value = paramElement.value ? paramElement.value : paramElement.textContent;
        param["value"]= value
        param["object"]= paramElement.object  // decoded value
        return param
    }

    /**
     * Fill the attributes of a info element into a map
     * @param infoElement the info element to analyze
     * @param info the map to fill in
     * @return the info map
     */
    private handleInfoElement(VOElement infoElement, info) {
        handleVOElement(infoElement, info)
        info["value"] = infoElement.getAttribute("value") ? infoElement.getAttribute("value") : infoElement.getTextContent()
        return info
    }

    /**
     * Fill the attributes of a field element into a map
     * @param voElement the element to analyze
     * @param voMap the map to fill in
     * @return the map
     */
    private handleVOElement(VOElement voElement, voMap) {
        voMap["id"]= voElement.ID
        voMap["name"]= voElement.name
        voMap["description"]= voElement.description
        // do we need to deal with getHandle()???
        return voMap
    }
}