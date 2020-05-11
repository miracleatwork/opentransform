package com.miracle.labs.transform.generated.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.net.ssl.HttpsURLConnection;



public class RESTClient {

	 String targetURI;
	 String targetApplicationPath;
	 String targetPath;
	 String targetPathVariables;
	 final String GET="GET";
	 final String POST="POST";
	 final static String REST_SERVER_URL="http://localhost:8080/";
	 
	 private final static Jsonb jsonb = JsonbBuilder.create();
	 
	    
	   
	
	 private static Executor executor = Executors.newFixedThreadPool(10);
	 /**
	  * Get will have String pathParams where as post will have requestParams
	  * @param applicationpath
	  * @param path
	  * @param returnType
	  * @param params
	  * @return
	  * @throws InterruptedException
	  * @throws ExecutionException
	 * @throws JsonbException 
	 * @throws UnsupportedEncodingException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	  */
	 public <T> T executeRemote(String applicationpath,String path,  Class<T> returnType,Object pathParams, Map<String,Object> requestParams) throws InterruptedException, ExecutionException, UnsupportedEncodingException, JsonbException, InstantiationException, IllegalAccessException{
		 if(path.startsWith("get"))
		 {
			 return executeGET(applicationpath,path,returnType,pathParams.toString());
		 }
		 else
		 {
			 return executePOST(applicationpath,path,returnType,requestParams);
		 }
	 }
	 
	 
	 public <T> T executeGET(String applicationpath,String path,  Class<T> returnType,String params) throws InterruptedException, ExecutionException, UnsupportedEncodingException, JsonbException, InstantiationException, IllegalAccessException{
		 
		 // call target/applicationpath/path/params
		 Client client = new Client(applicationpath+path+returnType+params,null,GET);
		 FutureTask<String> task = new FutureTask<String>(client);
		 executor.execute(task);
		 if(returnType!=void.class)
		 {	 
			 return returnType.cast(task.get());
		 }
		 else
		 {
			 return returnType.newInstance();
		 }
	 } 
	 
	 public <T> T executePOST(String applicationpath,String path,  Class<T> returnType,Map<String,Object> requestParams) throws InterruptedException, ExecutionException, UnsupportedEncodingException, JsonbException, InstantiationException, IllegalAccessException{
		 
		 // call target/applicationpath/path/params
		 Client client = new Client(applicationpath+"/"+path.substring(3),requestParams,POST);
		 FutureTask<String> task = new FutureTask<String>(client);
		 executor.execute(task);
		 System.out.println(task.get());
//		 if(returnType!=void.class)
//		 {	 
//			 return returnType.cast(task.get());
//		 }
//		 else
//		 {
//			 return returnType.newInstance();
//		 }
		 return null;
	 } 
	 
	 
	 
	 private class Client implements Callable<String>
	 {
		 String urlS;
		 String method;
		 String payload;
		 
		 private Client(String url,Map<String,Object> requestParams, String type) throws UnsupportedEncodingException, JsonbException
		 {
			 urlS = REST_SERVER_URL+url;
			 method = type;
			 if(type.equals(POST))
			 {
				 payload=generatePayload(requestParams);
			 }
			 else
			 {
				 payload="";
			 }
		 }
		 
		 @Override
		 public String call() {
			URL url;
			StringBuilder res = new StringBuilder();
			try {
				
				url = new URL(urlS);
				 HttpURLConnection con = (HttpURLConnection) url.openConnection();
				 if(method.equals(GET))
				 {
					 con.setRequestMethod("GET");	 
				 }
				 else
				 {
					 con.setDoInput(true);
				     con.setDoOutput(true);
					 con.setRequestMethod("POST");
					 PrintStream ps = new PrintStream(con.getOutputStream());
				     ps.println(payload);
				     ps.close();
				 }
				 
			     if (con.getResponseCode() == HttpsURLConnection.HTTP_OK) {
			            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			            String line;
			            while ((line = br.readLine()) != null) {
			                res.append(line);
			           }
			            br.close();
			     }

			     con.disconnect();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res.toString();
			

		 }
		
			 
	 }



	public String generatePayload(Map<String, Object> params) throws UnsupportedEncodingException, JsonbException {
		StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, Object> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(jsonb.toJson(entry.getValue()), "UTF-8"));
        }

        return result.toString();
	}
	 
	

	 
	
}
