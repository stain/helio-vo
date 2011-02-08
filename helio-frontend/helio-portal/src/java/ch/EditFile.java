/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author David
 */
public class EditFile {

    public void edit(String startDate, String endDate){
         File f=new File("tool.xml");

        FileInputStream fs = null;
        InputStreamReader in = null;
        BufferedReader br = null;

        StringBuffer sb = new StringBuffer();

        String textinLine;

        try {
             fs = new FileInputStream(f);
             in = new InputStreamReader(fs);
             br = new BufferedReader(in);

            while(true)
            {
                textinLine=br.readLine()+"\n";


                if(textinLine==null)
                    break;
                if(textinLine.contains("START_DATE")){
                    textinLine=br.readLine();
                    if(textinLine.contains("<value>")){
                        textinLine = "<value>2002-01-01T09:00:00</value>"+"\n";;
                    }
                }
                if(textinLine.contains("END_DATE")){
                    textinLine=br.readLine();
                    if(textinLine.contains("<value>")){
                        textinLine = "<value>2002-01-05T20:00:00</value>"+"\n";;
                    }
                }
                sb.append(textinLine);
            }
              

              fs.close();
              in.close();
              br.close();

            } catch (FileNotFoundException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }

            try{
                FileWriter fstream = new FileWriter(f);
                BufferedWriter outobj = new BufferedWriter(fstream);
                outobj.write(sb.toString());
                outobj.close();

            }catch (Exception e){
              System.err.println("Error: " + e.getMessage());
            }

    }
}
