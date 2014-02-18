package org.parse4j.command;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.parse4j.Parse;
import org.parse4j.ParseConstants;
import org.parse4j.ParseException;

public abstract class ParseCommand {

	private static RequestConfig config;
	protected JSONObject data;

	static {
		config = RequestConfig.custom().build();
	}	
	
	abstract HttpRequestBase getRequest() throws IOException;

	public ParseResponse perform() throws ParseException {

		try {
			HttpClient httpclient = createSingleClient();
			HttpResponse httpResponse = httpclient.execute(getRequest());
			ParseResponse response = new ParseResponse(httpResponse);
			
			return response;
		}
		catch (ClientProtocolException e) {
			throw ParseResponse.getConnectionFailedException(e.getMessage());
		} 
		catch (IOException e) {
			throw ParseResponse.getConnectionFailedException(e.getMessage());
		}
		
	}
	
	protected HttpClient createSingleClient() {
		HttpClientBuilder client = HttpClients.custom().setDefaultRequestConfig(config);
		
		return client.build();
	}
	
	protected void setupHeaders(HttpRequestBase requestBase, boolean addJson) {
		requestBase.addHeader(ParseConstants.HEADER_APPLICATION_ID, Parse.getApplicationId());
		requestBase.addHeader(ParseConstants.HEADER_REST_API_KEY, Parse.getRestAPIKey());
		if(addJson) {
			requestBase.addHeader(ParseConstants.HEADER_CONTENT_TYPE, ParseConstants.CONTENT_TYPE_JSON);
		}
	}

	public void setData(JSONObject data) {
		this.data = data;
	}
	
}