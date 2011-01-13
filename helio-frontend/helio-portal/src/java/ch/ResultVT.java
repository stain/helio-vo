/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch;

import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import net.ivoa.xml.votable.v1.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 *
 * @author David
 */
public class ResultVT {

    private VOTABLE res;

    private Stack<Stack<String>> stack;
    private Stack<String> headers;
    public int startTime;
    public int endTime;
    public LinkedList<TableVT> tables;
    public String queryInfo;


    public class TableVT{

        private String tableName;
        private LinkedList<String> headers;
        private LinkedList<LinkedList<String>> data;

        public TableVT(){
            tableName = "";
            headers = new LinkedList<String>();
            data = new LinkedList<LinkedList<String>>();

        }

        private void setName(String name) {
            this.tableName= name;
        }

        private void setHeaders(LinkedList<String> headersVT) {
            this.headers = headersVT;
        }

        private void setData(LinkedList<LinkedList<String>> dataVT) {
            this.data = dataVT;
        }

        public String getName(){
            return tableName;
        }

        public LinkedList<String> getHeaders(){
            return headers;
        }
        public LinkedList<LinkedList<String>> getData(){
            return data;
        }

        
    }


    
    public ResultVT(){
        
    }

    public ResultVT(VOTABLE res) {

        tables = new LinkedList<TableVT>();
        this.res = res;
        print(res);
    }

    public LinkedList<TableVT> getTables(){
        return tables;

    }

    public String getStringTable(){
        try {
            StringWriter retBuf = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(res.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FRAGMENT, true);
            m.marshal(res, retBuf);
            String retVal = retBuf.toString();
            
            return retVal;
        } catch (JAXBException ex) {
            Logger.getLogger(ResultVT.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }
    
    public Stack<Stack<String>> getStack() {
        return stack;
    }
    public void addTable(VOTABLE res){

        // this.res = res;
        //print(res);
    }
    public Stack<String> getHeaders() {
        return headers;
    }
    public void setStack(Stack<Stack<String>> stack) {
        this.stack= stack;
    }

    public void print(VOTABLE res) {
       //System.out.println("printing VOTABLE");
        headers= new Stack<String>();

        stack = new Stack<Stack<String>>();
        for (RESOURCE resource : res.getRESOURCE()) {
        //System.out.println("resource " +resource.getDESCRIPTION());
            queryInfo="";
            for(Object o :resource.getINFOOrCOOSYSOrPARAM()){
                
                INFO info =(INFO)o;
                queryInfo += info.getName()+" "+info.getValue();
            }


            for (TABLE table : resource.getTABLE()) {
               //System.out.println("TABLE " +table.getName());
               TableVT tableVT = new TableVT();
               tableVT.setName(table.getName());
               LinkedList<String> headersVT = new LinkedList<String>();
                for(Object o: table.getFIELDOrPARAMOrGROUP()){
                   FIELD f =(FIELD)o;
                   headersVT.add(f.getName());
                   if(f.getName().equals("time_start"))startTime = headers.size();
                   if(f.getName().equals("time_end"))endTime = headers.size();
                   if(!headers.contains(f.getName()))headers.add(f.getName());



                }
                tableVT.setHeaders(headersVT);
                LinkedList<LinkedList<String>> dataVT = new LinkedList<LinkedList<String>>();
                for (TR tr : table.getDATA().getTABLEDATA().getTR()) {
                  //  System.out.println("TR");
                    Stack<String> row = new Stack<String>();
                    LinkedList<String> rowVT = new LinkedList<String>();
                    for (TD td : tr.getTD()) {
                      //  System.out.println("Td" +td.getValue());
                        row.add(td.getValue());
                      //  System.out.println("value son " +td.getValue());
                        rowVT.add(td.getValue());
                    }
                    stack.add(row);
                    dataVT.add(rowVT);
                }
                tableVT.setData(dataVT);
                tables.add(tableVT);
            }
            
        }

//System.out.println("end of print");

    }

    public void printAdd(VOTABLE res) {
         // System.out.println("printing VOTABLE ADD");
        if(headers==null)headers= new Stack<String>();

        if(stack==null)stack = new Stack<Stack<String>>();
        for (RESOURCE resource : res.getRESOURCE()) {
        //System.out.println("resource ADD" +resource.getDESCRIPTION());

            for (TABLE table : resource.getTABLE()) {
              //  System.out.println("TABLE ADD" +table.getName());
                for(Object o: table.getFIELDOrPARAMOrGROUP()){
                   FIELD f =(FIELD)o;
                   
                  if(!headers.contains(f.getName())){
                      if(f.getName().equals("time_start"))startTime = headers.size();
                      if(f.getName().equals("end_start"))endTime = headers.size();
                      headers.add(f.getName());
                  }


                }
                for (TR tr : table.getDATA().getTABLEDATA().getTR()) {
                    //System.out.println("TR ADD");
                    Stack<String> row = new Stack<String>();
                    for (TD td : tr.getTD()) {
                      //  System.out.println("Td" +td.getValue());
                        row.add(td.getValue());
                    }
                    stack.add(row);
                }
            }
        }

//System.out.println("end of print ADD");
    }
}

