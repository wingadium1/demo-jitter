package com.winga.demo;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class APICaller {

	private static Logger LOGGER = LoggerFactory.getLogger(APICaller.class);

	public Integer callApi() {
		OkHttpClient client = new OkHttpClient();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url("http://localhost:8080/employees").method("POST", body).build();
		try {
			Response response = client.newCall(request).execute();
			LOGGER.info(response.body().string());
			if (!response.isSuccessful()) {
				throw new RuntimeException("API CALL ERROR");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e.getLocalizedMessage());
			return 1;
		}
		return 0;
	}
}
