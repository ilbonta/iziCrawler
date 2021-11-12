package ch.bnt.izicrawler;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import ch.bnt.izicrawler.utils.Globals;

@SpringBootApplication
public class IziCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IziCrawlerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder.build();

		restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
			@Override
			public org.springframework.http.client.ClientHttpResponse intercept(
					org.springframework.http.HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				request.getHeaders().set("X-IZI-API-KEY", Globals.API_KEY);
				return execution.execute(request, body);
			}
		});
		
		return restTemplate;
	}

}
