package org.parse4j.command;

import java.io.IOException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.parse4j.Parse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Generate parse batch requesy body and construct the url 
 * @author Nikolay Rusev
 *
 */
public class ParseBatchCommand extends ParseCommand{

	private static Logger LOGGER = LoggerFactory.getLogger(ParseBatchCommand.class);

	@Override
	public HttpRequestBase getRequest() throws IOException {
		
		HttpPost httppost = new HttpPost(Parse.getParseAPIUrl("batch"));
		setupHeaders(httppost, addJson);

		if (data.has("requests")) {
			if(LOGGER.isDebugEnabled()) {
				LOGGER.debug("Sending data: {}", data.getJSONArray("requests"));
			}
			httppost.setEntity(new StringEntity(data.toString(), "UTF8"));
		}
		return httppost;
	}
}
