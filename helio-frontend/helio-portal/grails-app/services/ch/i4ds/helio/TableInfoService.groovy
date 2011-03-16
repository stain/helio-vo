package ch.i4ds.helio

import org.springframework.core.io.ClassPathResource

/**
 *Temporary service while the files with the relevant data are placed in the registry or API
 */
class TableInfoService {

    boolean transactional = true


    def getDpasHash(){
        log.info("getDpasHash => " );
        Hashtable<String,List> hash = new Hashtable<String,List>();
        File f = new File("files/patlist.xml");
        List list = f.readLines();
        LinkedList<String> tableNameList = new LinkedList<String>();

        for(int i = 0;i<list.size();i++){
            String line = list.get(i);
       /*
            if(line.contains("enumeration value=")){
                line =line.replace("<xs:enumeration value=","");
                line =line.replace("\"","");
                line =line.replace(">","");
                //println line.trim();
                tableNameList.add(line.trim());
            }
            */
             tableNameList.add(line.trim());
        }
        tableNameList = tableNameList.sort();
        hash.put("list",tableNameList);
        return hash;
    }
    def serviceMethod(String filePath) {
        log.info("serviceMethod => "+ filePath );
        Hashtable<String,List> hash = new Hashtable<String,List>();
        //InputStream s = getClass().getResourceAsStream(filePath);
		
		File f = new ClassPathResource("/" + filePath).getFile();
        println "file path: " + f;
        //File f = new File("files/tablesils.xml");
        List list = f.readLines();
        LinkedList<String> tableNameList = new LinkedList<String>();
        for(int i = 0;i<list.size();i++){
            String line = list.get(i);
            if(line.contains("<table>")){
                String tableName;
                LinkedList columnNames = new LinkedList();
                while(!line.contains("</table>")){
                    if(line.contains("<name>")){
                        line =line.replace("<name>","");
                        line =line.replace("</name>","");
                        tableName= line.trim();
                        //println line.trim();
                        //println line;
                    }//if name
                    if(line.contains("<column>")){
                        while(!line.contains("</column>")){
                            if(line.contains("<name>")){
                                line =line.replace("<name>","");
                                line =line.replace("</name>","");
                                columnNames.add(line.trim());
                                
                            }//if name
                            i++;
                            line = list.get(i);
                        }//while column

                    }//if column



                    i++;
                    line = list.get(i);
                    
                }// while table
                hash.put(tableName,columnNames);
                tableNameList.add(tableName);

            }//if table
            tableNameList = tableNameList.sort();
            hash.put("list",tableNameList);
        }// for i

        return hash;
        
        
    }


}
