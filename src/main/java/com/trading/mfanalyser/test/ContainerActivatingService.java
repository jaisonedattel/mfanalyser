package com.trading.mfanalyser.test;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ContainerActivatingService {
	Logger logger = LoggerFactory.getLogger(ContainerActivatingService.class);

	public static final String GO_REST_API = "https://gorest.co.in/public/v2/users";
	public static final String RENDER_REST_API = "https://mfanalyser.onrender.com/test/hello";

	@Scheduled(fixedRate = 300000, initialDelay = 120000) //every 5 minutes with initial 1 min delay
	public void refreshJobToKeepContainerActive() {
		logger.info("refreshJobToKeepContainerActive : "+LocalDateTime.now(ZoneId.of("GMT+05:30")));
		try {
			getRestApiResponse(GO_REST_API);
		} catch (Exception e) {
			logger.error("Error processing the API call :"+GO_REST_API);
		}
	}
	
	public String getRestApiResponse(String url) throws Exception {
		
		WebClient client = WebClient.builder().baseUrl(url)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
		String jsonStr = client.get().retrieve().bodyToMono(String.class).block();
		return jsonStr;

	}
	//https://mfanalyser.onrender.com/test/hello
	//@Scheduled(fixedRate = 300000, initialDelay = 60000) //every 5 minutes with initial 1 min delay
	public void callRenderApiToKeepContainerActive() {
		logger.info("callRenderApiToKeepContainerActive : "+LocalDateTime.now(ZoneId.of("GMT+05:30")));
		try {
			System.out.println("Render API Resonse :"+getRestApiResponse(RENDER_REST_API));
		} catch (Exception e) {
			logger.error("Error processing the API call :"+RENDER_REST_API);
		}
	}
}
