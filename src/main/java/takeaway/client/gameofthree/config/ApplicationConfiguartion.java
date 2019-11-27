package takeaway.client.gameofthree.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContextListener;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationConfiguartion {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplate restTemplate = builder.build();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		return restTemplate;
	}
	
	@Bean
	ServletListenerRegistrationBean<ServletContextListener> servletListener() {
	    ServletListenerRegistrationBean<ServletContextListener> srb
	      = new ServletListenerRegistrationBean<>();
	    srb.setListener(new UnregistrationServletContextListener());
	    return srb;
	}
}
