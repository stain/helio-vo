package ch;

/*
 * $Header: 
 * $Revision$
 * $Date$
 * ====================================================================
 *
 *  Copyright 2002-2004 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 * [Additional notices, if required by prior licensing conditions]
 *
 */

import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;

public class PostXML {
    
    /**
     * 	Usage:
     *       java PostXML http://mywebserver:80/ c:\foo.xml
     *
     * 	@param args command line arguments
     *         Argument 0 is a URL to a web server
     *         Argument 1 is a local filename
    **/
    public static String service() throws Exception {
        
         String domain = "http://msslxv.mssl.ucl.ac.uk";
        // Get target URL

         File newFile = new File("sabe.sabe");

         System.out.println(newFile.getAbsolutePath()+"este es mi path");
       
        String strURL = domain + "/helio-cea/uws/jobs";

//
       // 

         String strXMLFilename = "tool.xml";
        
        System.out.println(" : URL : "+strURL+" : File Name : "+strXMLFilename);
        System.out.println("-------> getting location ------->");
        String location=getDetails(strXMLFilename,strURL,"");
        System.out.println("------->Location ------->"+location);
        System.out.println("-------> Executing the job ------->");
        executeJob(location,"phase");   
        System.out.println("-------> Done ------->");
        Thread.sleep(1000);
        System.out.println("-------> Getting phase ------->");
        String sPhase=null;
        int count=0;
        do{
        	//getting phase
 		System.out.println(" Checking if completed ");
        	sPhase=getPhase(location,"phase");
        	count++;
        	if(count==40){
        	   System.out.println("-------> Aborting the job, Exection occurred ------->");
        	   abortJob(location,"phase");
        	}
        	Thread.sleep(1000);
        }while(!sPhase.contains("COMPLETED"));
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("-------> Phase of executing ------->"+sPhase);
        System.out.println("");
        System.out.println("");
        String sResult=null;
        //getting result
        if(sPhase.contains("COMPLETED")){
        	 sResult=getResults(location,"results");
        }
        System.out.println("-------> Result of the executing ------->"+sResult);
        System.out.println("Resulting URL "+ domain + sResult);

        return domain+sResult;
    }
    /**
     * 
     * @param strURL
     * @param append
     * @return
     * @throws Exception
    */
    private static String getResults(String strURL,String append) throws Exception{
    	if(append!=null && !append.trim().equals("")){
			strURL=strURL+"/"+append;
        }
    	 System.out.print(" --------> Location --------> "+strURL);
    	 //PostMethod post = new PostMethod(strURL);
    	 GetMethod get = new GetMethod(strURL);
    	 get.setFollowRedirects(false); 
    	 //post.setParameter("phase", "RUN");
    	 //NameValuePair action   = new NameValuePair("PHASE", "RUN");
         //post.setRequestBody( new NameValuePair[] {action});
         // Get HTTP client
         HttpClient httpclient = new HttpClient();
         int result = httpclient.executeMethod(get);
         int statuscode = get.getStatusCode();
         String results=get.getResponseBodyAsString();
         System.out.println(get.getResponseBodyAsString());
         if(results!=null && !results.equals("")){
        	 results=results.substring(results.indexOf("xlink:href")+12, results.length()-23);
         }
         return results;
    }
    /**
     * 
     * @param strURL
     * @param append
     * @return
     * @throws Exception
    */
    private static String getPhase(String strURL,String append) throws Exception{
    	if(append!=null && !append.trim().equals("")){
			strURL=strURL+"/"+append;
        }
    	 System.out.print(" ----------> Location ----------> "+strURL);
    	 //PostMethod post = new PostMethod(strURL);
    	 GetMethod get = new GetMethod(strURL);
    	 get.setFollowRedirects(false); 
    	 //post.setParameter("phase", "RUN");
         // Get HTTP client
         HttpClient httpclient = new HttpClient();
         int result = httpclient.executeMethod(get);
         return get.getResponseBodyAsString();
    }
    /**
     * @param strURL
     * @param append
     * @throws Exception
    */
    private static void abortJob(String strURL,String append) throws Exception{
    	 if(append!=null && !append.trim().equals("")){
			strURL=strURL+"/"+append;
         }
    	 //Post 
    	 PostMethod post = new PostMethod(strURL);
    	 post.setFollowRedirects(false); 
    	 post.setParameter("phase", "ABORT");
         // Get HTTP client
         HttpClient httpclient = new HttpClient();
         int result = httpclient.executeMethod(post);
         int statuscode = post.getStatusCode();
         System.out.println(post.getResponseBodyAsString());
    }
    /**
     * 
     * @param strURL
     * @param append
     * @throws Exception
    */
    private static void executeJob(String strURL,String append) throws Exception{
    	if(append!=null && !append.trim().equals("")){
			strURL=strURL+"/"+append;
        }
    	 PostMethod post = new PostMethod(strURL);
    	 post.setFollowRedirects(false); 
    	 post.setParameter("phase", "RUN");
    	 //
    	 HttpClient httpclient = new HttpClient();
         int result = httpclient.executeMethod(post);
         int statuscode = post.getStatusCode();
         System.out.println(post.getResponseBodyAsString());
    }
    /**
     * @param filename
     * @param strURL
     * @param append
     * @return
     * @throws Exception
     */
	private static String getDetails(String filename ,String strURL,String append) throws Exception{
		// Prepare HTTP post
        PostMethod post = new PostMethod(strURL);
        // Request content will be retrieved directly
        // from the input stream
        // Per default, the request content needs to be buffered
        // in order to determine its length.
        // Request body buffering can be avoided when
        // content length is explicitly specified
        if(filename!=null){
        	 File input = new File(filename);
        post.setRequestEntity(new InputStreamRequestEntity(
                new FileInputStream(input), input.length()));
        post.setRequestHeader(
                "Content-type", "text/xml; charset=ISO-8859-1");
        }     
        post.setFollowRedirects(false); 
        // Get HTTP client
        HttpClient httpclient = new HttpClient();
        
        try {
            //httpclient.
            int result = httpclient.executeMethod(post);
            int statuscode = post.getStatusCode();
       
            if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
                    (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
                    (statuscode == HttpStatus.SC_SEE_OTHER) ||
                    (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT))
                {
            	
                    Header header = post.getResponseHeader("location");
                    System.out.println("   :  Header  :  "+header);
                    if (header != null)
                    {
                        String newuri = header.getValue();
                        if ((newuri == null) || (newuri.equals("")))
                            newuri = "/";
                       return newuri;
                       }
                    else{
                    	return null;
                    }
                }else{
                	System.out.println(post.getResponseBodyAsString());
                }
        } finally {
            // Release current connection to the connection pool 
            // once you are done
            post.releaseConnection();
        }
        return null;
	}
}

