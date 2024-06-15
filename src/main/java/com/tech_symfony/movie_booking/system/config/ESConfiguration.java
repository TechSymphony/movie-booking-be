package com.tech_symfony.movie_booking.system.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

@Configuration
public class ESConfiguration extends ElasticsearchConfiguration {

	@Value("${spring.elasticsearch.username}")
	private String username;

	@Value("${spring.elasticsearch.password}")
	private String password;

	@Override
	public ClientConfiguration clientConfiguration() {

		return ClientConfiguration.builder()
			.connectedTo("localhost:9200")
			.usingSsl(getSslContext())
			.withBasicAuth(username, password)
			.build();
	}

	@SneakyThrows
	public SSLContext getSslContext() {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");

		Certificate ca;
		try (InputStream certificateInputStream = new FileInputStream("ca.crt")) {
			ca = cf.generateCertificate(certificateInputStream);
		}

		// Create a KeyStore containing our trusted CAs
		String keyStoreType = KeyStore.getDefaultType();
		KeyStore keyStore = KeyStore.getInstance(keyStoreType);
		keyStore.load(null, null);
		keyStore.setCertificateEntry("ca", ca);

		// Create a TrustManager that trusts the CAs in our KeyStore
		String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
		tmf.init(keyStore);

		// Create an SSLContext that uses our TrustManager
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, tmf.getTrustManagers(), null);
		return context;
	}
}
