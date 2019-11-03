package javabrains.io.movieCatalogue.interceptor;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class MovieCatalogueInterceptor implements ClientHttpRequestInterceptor
{
	Logger log=LoggerFactory.getLogger(getClass());

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		log.info(request.getURI().toString());
		log.info(request.getMethod().toString());
		log.info("Request Headers"+request.getHeaders().toString());
		String requestBody=new String(body);
		log.info(requestBody);
		ClientHttpResponse response=execution.execute(request, body);
		log.info(response.getHeaders().toString());
		// TODO Auto-generated method stub
		return response;
	}

}
