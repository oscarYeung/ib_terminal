package test.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/application-test.xml")

public abstract class BaseTest {
	protected String host = "http://localhost:8080/ib_terminal_server/";
	
	protected Gson gson = new GsonBuilder().setDateFormat(getGsonDateFormatter()).create();
	
	
	protected <T extends Object> T getContentFromHttpResponse(Class<T> clazz, HttpResponse response) throws Exception{
		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
		StringBuilder sb = new StringBuilder();
		String line = null;
		
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    System.out.println("<<< Response");
		System.out.println(sb.toString());
	   
	    return gson.fromJson(sb.toString(), clazz);

	}
	
	protected String getGsonDateFormatter(){
		return "yyyy-MM-dd HH:mm:ss";
	}
	
	protected <T extends Object> T postJson(Class<T> clazz, String link, String jsonString){
		HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

	    try {
	    	System.out.println("");
	    	System.out.println("Link :" + link);
	    	System.out.println("Request >>>");
	    	System.out.println(jsonString);
	    	link = host + link;
	        HttpPost request = new HttpPost(link);
	        StringEntity params =new StringEntity(jsonString);
	        request.addHeader("content-type", "application/json");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);
	        return getContentFromHttpResponse(clazz, response);
	        // handle response here...
	    }catch (Exception ex) {
	        // handle exception here
	    } finally {

	    }
	    return null;
	}
}

