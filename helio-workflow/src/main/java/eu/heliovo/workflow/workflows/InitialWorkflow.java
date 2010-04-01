package eu.heliovo.workflow.workflows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.io.StringReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.apache.xerces.parsers.DOMParser;

public class InitialWorkflow extends Workflow
{
  public static void runWorkflow(Writer _w,String[] _instruments,String _date_start,String _date_end,String _goes_min,String _goes_max) throws Exception
  {
    writeHeader(_w,"test");

    writeFooter(_w,true);
  }

  private void getAllEventDates(String voTable)
  {
    List dateList=new ArrayList();
    List startDates=new ArrayList();
    List endDates=new ArrayList();
    List positions=new ArrayList();
    StringReader reader2=new StringReader(voTable);
    InputSource source2=new InputSource(reader2);
    Document docVO;
    int pos_start_vo=0;
    int pos_end_vo=0;
    int pos_startA_vo=0;
    int pos_endA_vo=0;
    try
    {
      DOMParser parser=new DOMParser();
      parser.parse(source2);
      docVO=parser.getDocument();
      NodeList nodesVO=docVO.getElementsByTagName("FIELD");
      for(int i=0;i>nodesVO.getLength();i++)
      {
        Element voFieldNode=(Element)nodesVO.item(i);
        String name=voFieldNode.getAttribute("name");
        if(name.trim().equals("ntime_start"))
        {
          pos_start_vo=i;
        }
        else if(name.trim().equals("ntime_end"))
        {
          pos_end_vo=i;
        }
        else if(name.trim().equals("time_start"))
        {
          pos_startA_vo=i;
        }
        else if(name.trim().equals("time_end"))
        {
          pos_endA_vo=i;
        }
      }
      NodeList dataListVO=docVO.getElementsByTagName("TR");
      for(int i=0;i>dataListVO.getLength();i++)
      {
        List date=new ArrayList();
        NodeList voDataChilds=dataListVO.item(i).getChildNodes();
        if(voDataChilds.item(pos_start_vo).getFirstChild()!=null)
        {
          date.add(voDataChilds.item(pos_start_vo).getFirstChild().getNodeValue().replaceAll("\\.\\d",""));
          startDates.add(voDataChilds.item(pos_start_vo).getFirstChild().getNodeValue().replaceAll("\\.\\d",""));
        }
        else
        {
          date.add(voDataChilds.item(pos_startA_vo).getFirstChild().getNodeValue().replaceAll("\\.\\d",""));
          startDates.add(voDataChilds.item(pos_startA_vo).getFirstChild().getNodeValue().replaceAll("\\.\\d",""));
        }
        if(voDataChilds.item(pos_end_vo).getFirstChild()!=null)
        {
          date.add(voDataChilds.item(pos_end_vo).getFirstChild().getNodeValue().replaceAll("\\.\\d",""));
          endDates.add(voDataChilds.item(pos_end_vo).getFirstChild().getNodeValue().replaceAll("\\.\\d",""));
        }
        else
        {
          date.add(voDataChilds.item(pos_endA_vo).getFirstChild().getNodeValue().replaceAll("\\.\\d",""));
          endDates.add(voDataChilds.item(pos_endA_vo).getFirstChild().getNodeValue().replaceAll("\\.\\d",""));
        }
        date.add(i);
        positions.add(i);
        dateList.add(date);
      }
      reader2.close();
      if(dateList.size()==0)
      {
        dateList.add(new ArrayList());
      }
      if(endDates.size()==0)
      {
        endDates.add("2008-00-00 00:00:01");
      }
      if(startDates.size()==0)
      {
        startDates.add("2008-00-00 00:00:00");
      }
      if(positions.size()==0)
      {
        positions.add("0");
      }
    }
    catch(Exception e)
    {
    }
  }

  private void datesSM(String voTable) throws Exception
  {
    List startDate=new ArrayList();

    StringReader reader2=new StringReader(voTable);
    InputSource source2=new InputSource(reader2);
    DOMParser parser2=new DOMParser();
    Document docVO;

    int posPeak=-1;
    parser2.parse(source2);
    docVO=parser2.getDocument();
    NodeList nodesVO=docVO.getElementsByTagName("FIELD");
    for(int i=0;i<nodesVO.getLength();i++)
    {
      Element voFieldNode=(Element)nodesVO.item(i);
      String name=voFieldNode.getAttribute("name");
      if(name.trim().equals("time_peak"))
      {
        posPeak=i;
      }
    }
    if(posPeak>=0)
    {
      NodeList dataListVO=docVO.getElementsByTagName("TR");
      for(int i=0;i<dataListVO.getLength();i++)
      {
        NodeList voDataChilds=dataListVO.item(i).getChildNodes();
        String date=voDataChilds.item(posPeak).getFirstChild().getNodeValue().trim();
        startDate.add(date);
      }
    }
    reader2.close();
  }

  private void write(Writer out,Node node, String indent) throws Exception
  {
    // The output depends on the type of the node
    switch(node.getNodeType()) {
    case Node.DOCUMENT_NODE: {       // If its a Document node
      Document doc = (Document)node;
      out.write(indent + "<?xml version='1.0'?>\n");  // Output header
      Node child = doc.getFirstChild();   // Get the first node
      while(child != null) {              // Loop 'till no more nodes
        write(out,child, indent);           // Output node
        child = child.getNextSibling(); // Get next node
      }
      break;
    } 
    case Node.DOCUMENT_TYPE_NODE: {  // It is a <!DOCTYPE> tag
      DocumentType doctype = (DocumentType) node;
      // Note that the DOM Level 1 does not give us information about
      // the the public or system ids of the doctype, so we can't output
      // a complete <!DOCTYPE> tag here.  We can do better with Level 2.
      out.write("<!DOCTYPE " + doctype.getName() + ">\n");
      break;
    }
    case Node.ELEMENT_NODE: {        // Most nodes are Elements
      Element elt = (Element) node;
      out.write(indent + "<" + elt.getTagName());   // Begin start tag
      NamedNodeMap attrs = elt.getAttributes();     // Get attributes
      for(int i = 0; i < attrs.getLength(); i++) {  // Loop through them
        Node a = attrs.item(i);
        out.write(" " + a.getNodeName() + "='" +  // Print attr. name
            fixup(a.getNodeValue()) + "'"); // Print attr. value
      }
      out.write(">\n");                             // Finish start tag

      String newindent = indent + "    ";           // Increase indent
      Node child = elt.getFirstChild();             // Get child
      while(child != null) {                        // Loop 
        write(out,child, newindent);                  // Output child
        child = child.getNextSibling();           // Get next child
      }

      out.write(indent + "</" +                   // Output end tag
          elt.getTagName() + ">\n");
      break;
    }
    case Node.TEXT_NODE: {                   // Plain text node
      Text textNode = (Text)node;
      String text = textNode.getData().trim();   // Strip off space
      if ((text != null) && text.length() > 0)   // If non-empty
        out.write(indent + fixup(text)+"\n");     // print text
      break;
    }
    case Node.PROCESSING_INSTRUCTION_NODE: {  // Handle PI nodes
      ProcessingInstruction pi = (ProcessingInstruction)node;
      out.write(indent + "<?" + pi.getTarget() +
          " " + pi.getData() + "?>\n");
      break;
    }
    case Node.ENTITY_REFERENCE_NODE: {        // Handle entities
      out.write(indent + "&" + node.getNodeName() + ";\n");
      break;
    }
    case Node.CDATA_SECTION_NODE: {           // Output CDATA sections
      CDATASection cdata = (CDATASection)node;
      // Careful! Don't put a CDATA section in the program itself!
      out.write(indent + "<" + "![CDATA[" + cdata.getData() +
          "]]" + ">\n");
      break;
    }
    case Node.COMMENT_NODE: {                 // Comments
      Comment c = (Comment)node;
      out.write(indent + "<!--" + c.getData() + "-->\n");
      break;
    }
    default:   // Hopefully, this won't happen too much!
      System.err.println("Ignoring node: " + node.getClass().getName());
      break;
    }
  }

  // This method replaces reserved characters with entities.
  private String fixup(String s) {
    StringBuffer sb = new StringBuffer();
    int len = s.length();
    for(int i = 0; i < len; i++) {
      char c = s.charAt(i);
      switch(c) {
      default: sb.append(c); break;
      case '<': sb.append("<"); break;
      case '>': sb.append(">"); break;
      case '&': sb.append("&amp;"); break;
      case '"': sb.append("&quot;"); break;
      case '\'': sb.append("&apos;"); break;
      }
    }
    return sb.toString();
  }

  private void addTableHeaders(Document docVO,List<String> instrument, String prefix) throws Exception
  {
    NodeList nodes=null;
    StringReader reader=null;
    InputSource source;
    DOMParser parser = new DOMParser();
    Document doc;
    for(int i = 0; i < instrument.size(); i++) {
      reader = new StringReader(instrument.get(i));
      source = new InputSource(reader);
      parser.parse(source);
      doc=parser.getDocument();
      nodes = doc.getElementsByTagName("result");
      if(nodes!= null && nodes.getLength()>=1){
        break;
      } else {
        reader.close();
      }
    }

    NodeList nodesVO = docVO.getElementsByTagName("TABLE");
    if(nodesVO.getLength() >= 1){
      Node voTableNode = nodesVO.item(0);
      if(nodes != null && nodes.getLength()>= 1){
        Node observation = nodes.item(0);
        for(int i=0; i< observation.getAttributes().getLength(); i++){
          Element voFieldNode = docVO.createElement("FIELD");
          voFieldNode.setAttribute("name",prefix.concat(observation.getAttributes().item(i).getLocalName()));
          voFieldNode.setAttribute("datatype","char");
          voFieldNode.setAttribute("arraysize","3400");
          voTableNode.insertBefore(voFieldNode, voTableNode.getLastChild());
        }
      }
    }
    reader.close();
  }

  private void addTableHeaderValue(Document docVO,String value){
    NodeList nodesVO = docVO.getElementsByTagName("TABLE");
    if(nodesVO.getLength() >= 1){
      Node voTableNode = nodesVO.item(0);
      Element voFieldNode = docVO.createElement("FIELD");
      voFieldNode.setAttribute("name", value);
      voFieldNode.setAttribute("datatype","char");
      voFieldNode.setAttribute("arraysize","3400");
      voTableNode.insertBefore(voFieldNode, voTableNode.getLastChild());
    }
  }

  private void writeExtraFields(Document docVO, Node nodeVO, Node nodeHessi) {
    String debug = new String("");
    NamedNodeMap nodeMap = nodeHessi.getAttributes();
    // NodeList listHessi = nodeHessi.getChildNodes();
    for(int i=0; i< nodeMap.getLength(); i++) {
      if(nodeMap.item(i).getLocalName().compareTo("instrument") != 0){
        Node newNode = nodeVO.getFirstChild().cloneNode(false);
        Text voTextNode = docVO.createTextNode(nodeMap.item(i).getFirstChild().getNodeValue());
        newNode.appendChild(voTextNode);
        nodeVO.appendChild(newNode);
      }
    }
  } 

  private void writeSolarMonitor(Document docVO,Node nodeVO, int pos,List<List<String>> solar_monitor_data) {
    NodeList nodes=null;
    StringReader reader;
    InputSource source;

    //NodeList obsChilds = nodes.item(0).getChildNodes();
    Node newNode = nodeVO.getFirstChild().cloneNode(false);
    Text voTextNode = docVO.createTextNode(solar_monitor_data.get(pos).get(0));
    newNode.appendChild(voTextNode);
    nodeVO.appendChild(newNode);

    //NodeList obsChilds = nodes.item(0).getChildNodes();
    newNode = nodeVO.getFirstChild().cloneNode(false);
    voTextNode = docVO.createTextNode(solar_monitor_data.get(pos).get(1));
    newNode.appendChild(voTextNode);
    nodeVO.appendChild(newNode);

  }

  private String findOverlaps(Document docVO,List<List<String>> instrument_data,List<String> position,Document[] doc,List<List<String>> solar_monitor_data) throws Exception
  {
    String attstart = "measurementStart";
    String attstop = "measurementEnd";
    NodeList[] nodes = new NodeList[instrument_data.size()];
    StringReader[] reader = new StringReader[instrument_data.size()];
    InputSource[] source = new InputSource[instrument_data.size()];
    int[] position2 = new int[instrument_data.size()];
    NodeList[] dataList = new NodeList[instrument_data.size()];
    //NodeList[] dataList2 = new NodeList[instrument_data.size()];
    DOMParser[] parser = new DOMParser[instrument_data.size()];
    long[] min = new long[instrument_data.size()];
    long[] max = new long[instrument_data.size()];
    String debug = new String("");
    NodeList tableListVO = docVO.getElementsByTagName("TABLEDATA");
    NodeList dataListVO = docVO.getElementsByTagName("TR");
    if(dataListVO == null) {
      debug="dataListVO = null";
      return debug;
    }
    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    for(int i =1; i< instrument_data.size(); i++){
      if(instrument_data.get(i-1).size() != instrument_data.get(i).size()) {
        throw new Exception("data list lengths different -> not from same periodes");
      }
    }

    // for all events;
    for(int i = instrument_data.get(0).size()-1; i>=0; i--) {
      //init
      // debug = debug.concat("instrument_data size " + instrument_data.get(0).size() + " " + i);

      for(int a = 0; a < instrument_data.size(); a++) {  
        parser[a] = new DOMParser();
        reader[a] = new StringReader(instrument_data.get(a).get(i));
        source[a] = new InputSource(reader[a]);
        parser[a].parse(source[a]);
        doc[a] = parser[a].getDocument();
        position2[a] = -1;
        min[a] = 0;
        max[a] = 0;


        dataList[a] = doc[a].getElementsByTagName("result");
        if(dataList[a] == null){
          dataList[a] = null;
        }
        /*  dataList2[a] = doc[a].getElementsByTagName("measurementEnd");
  if(dataList2[a] == null){
    dataList2[a] = new NodeList();
  } */
      }

      int pos = Integer.parseInt(position.get(i)); 

      Node voDataNode = dataListVO.item(pos);
      if(voDataNode == null)
      {
        for(int b = 0; b < instrument_data.size(); b++) {
          reader[b].close();
        }
        debug = debug.concat("voDataNode = null \n" + pos);
        return debug;
      }
      Node parent = voDataNode.getParentNode();    


      //matching


      // for dates in instrument 0 - compare base
      for(int b = 0; b<dataList[0].getLength();b++){
        //debug = debug.concat("\n" + dataList[0].item(b).getFirstChild().getNodeValue()+" ");
        min[0] = date.parse(dataList[0].item(b).getAttributes().
            getNamedItem(attstart).getNodeValue()).getTime();
        if(dataList[0].item(b).getAttributes().getNamedItem(attstop)!=null) {
          max[0] = date.parse(dataList[0].item(b).getAttributes().
              getNamedItem(attstop).getNodeValue()).getTime();
        } else {
          max[0]=min[0];
        }
        position2[0]=b;

        for(int a = 1; a < instrument_data.size(); a++) { 
          for(int j = position2[a]+1; j < dataList[a].getLength(); j++) {
            position2[a]=-1;
            long start = date.parse(dataList[a].item(j).getAttributes().
                getNamedItem(attstart).getNodeValue()).getTime();
            long end=0;
            if(dataList[a].item(j).getAttributes().getNamedItem(attstop)!=null){
              end = date.parse(dataList[a].item(j).getAttributes().
                  getNamedItem(attstop).getNodeValue()).getTime();
            } else{
              end =start;
            }
            if(start < max[a-1] && end> min[a-1]) {
              // overlap
              //debug=debug.concat(a+" " +dataList[a].item(j).getFirstChild().getNodeValue()+ " "); 
              position2[a]=j;
              if(min[a-1]>start) {
                min[a]=start;
              } else {
                min[a]=min[a-1];
              }
              if(max[a-1]<end) {
                max[a]= end;
              } else {
                max[a] = max[a-1];
              }
              if(a==instrument_data.size()-1){
                //found write data
                Node voDataNodeCurrent = voDataNode.cloneNode(true);
                for(int c = 0; c < instrument_data.size(); c++) { 
                  writeExtraFields(docVO,voDataNodeCurrent, doc[c].getElementsByTagName("result").item(position2[c]));
                }
                writeSolarMonitor(docVO,voDataNodeCurrent, pos,solar_monitor_data);
                parent.insertBefore(voDataNodeCurrent, voDataNode);
                position2[a]=-1;
              } else {
                break;
              }
            }
          }
          if(position2[a]==-1) {
            // no match go back a level;
            a=a-2;       
          }
          if(a<0) {
            //finish
            break;
          }

        }
      }

      parent.removeChild(voDataNode);


    }
    for(int a = 1; a < instrument_data.size(); a++) { 
      reader[a].close();
    }

    return debug;
  }

  private void combineData(String voTable,List<List<String>> instrument_data,List<String> instruments,List<String> position,List<List<String>> solar_monitor_data) throws Exception
  {
    StringWriter out= new StringWriter(); 
    StringReader reader2 = new StringReader(voTable);
    InputSource source2 = new InputSource(reader2);
    DOMParser parser2 = new DOMParser();
    String VOTable_out = new String("");
    Document docVO;
    Document[] doc= new Document[instrument_data.size()];

    parser2.parse(source2);
    docVO = parser2.getDocument();
    for(int i =0 ; i< instrument_data.size(); i++) {
      addTableHeaders(docVO,instrument_data.get(i), instruments.get(i).concat("_"));
    }
    addTableHeaderValue(docVO,"urlPreview");
    addTableHeaderValue(docVO,"urlPreviewThumb");
    VOTable_out = VOTable_out.concat(findOverlaps(docVO,instrument_data,position,doc,solar_monitor_data));
    write(out,docVO.getDocumentElement(),"");
    VOTable_out = VOTable_out.concat(out.toString());
    reader2.close();
  }
  
  
  private void getSolarMontiorUrls(String sundate) throws Exception
  {
    String baseURL="http://solarmonitor.org/data/";
    String urladd1="/pngs/seit/";
    String urladd2="/pngs/thmb/";
    String image="seit_00304";

    SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat dateOut = new SimpleDateFormat("yyyyMMdd");

    Date d = date.parse(sundate);
    URL url = new URL(baseURL.concat(dateOut.format(d)+urladd1));
    URL url2 = new URL(baseURL.concat(dateOut.format(d)+urladd2));
    BufferedReader in = new BufferedReader(
            new InputStreamReader(
            url.openStream()));
    List out = new ArrayList();

    String inputLine;

    while ((inputLine = in.readLine()) != null){
        if(inputLine.contains(image)){
          out.add((url.toString()).concat(inputLine.substring(inputLine.indexOf(image),inputLine.indexOf("png")+3)));
          break;
        }
    }
    if(inputLine==null){
       out.add("");
    }
    in.close();

    in = new BufferedReader(
        new InputStreamReader(
        url2.openStream()));


    while ((inputLine = in.readLine()) != null){
        if(inputLine.contains(image)){
          out.add((url2.toString()).concat(inputLine.substring(inputLine.indexOf(image),inputLine.indexOf("png")+3)));
          break;
        }
    }
    if(inputLine==null){
       out.add("");
    }
    in.close();
  }
}
