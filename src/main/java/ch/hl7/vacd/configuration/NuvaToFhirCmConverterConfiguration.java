/**
 * 
 */
package ch.hl7.vacd.configuration;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.api.IRestfulClientFactory;
import ca.uhn.fhir.rest.client.api.ServerValidationModeEnum;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;

/**
 * 
 */
@Configuration
public class NuvaToFhirCmConverterConfiguration {

	private FhirContext fhirContext;

	@Value("${application.terminology.server}")
	private String baseFhirUrl;

	@Bean
	FhirContext fhirContext() {
		if (fhirContext == null) {
			fhirContext = FhirContext.forR4();
		}
		return fhirContext;
	}

	@Bean
	CloseableHttpClient createHttpClient() {
		return HttpClients.custom().build();
	}

	@Bean
	IGenericClient createClientBase() {

		IRestfulClientFactory clientFactory = fhirContext().getRestfulClientFactory();
		clientFactory.setServerValidationMode(ServerValidationModeEnum.NEVER);//
		clientFactory.setSocketTimeout(600 * 1000);
		clientFactory.setHttpClient(createHttpClient());
		IGenericClient client = clientFactory.newGenericClient(baseFhirUrl);
//		client.registerInterceptor(loggingInterceptor);

		return client;
	}

}
