package com.cgi.laps.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootPaymentsAppTests {

//	@LocalServerPort
//	static int randomServerPort;
//	private static final String baseUrl = new StringBuffer().append("http://localhost:").append(randomServerPort).append("/accounts").toString();
//	private static final String baseUrlAdmin = new StringBuffer(baseUrl).append("/173627").toString();
//
//	@Test
//	public void testGetCustomerAccountsListSuccess() throws URISyntaxException {
//		RestTemplate restTemplate = new RestTemplate();
//		URI uri = new URI(baseUrl);
//		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
//		// Verify request succeed
//		assertThat(result.getStatusCodeValue()).isEqualTo(200);
//	}
//
//	@Test
//	public void testGetCustomerAccountSuccess() throws URISyntaxException {
//		RestTemplate restTemplate = new RestTemplate();
//		URI uri = new URI(baseUrlAdmin);
//		ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
//		// Verify request succeed
//		assertThat(result.getStatusCodeValue()).isEqualTo(200);
//		assertThat(result.getBody().contains("173627")).isEqualTo(true);
//	}
}
