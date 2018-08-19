package com.rabbit.samples.consumerservice.configs;

import com.rabbit.samples.consumerservice.feign.clients.ProducerServiceGreetingsClient;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Configuration("feignConfig")
public class FeignConfig {

	@Value("${producer.service.hostname}")
	String producerSrvHostname;

	@Value("${producer.service.port}")
	int producerSrvPort;

	@Bean
	public ProducerServiceGreetingsClient producerServiceGreetingsClient() {

		log.debug("ProducerService hostname {}, port {}", getProducerSrvHostname(), getProducerSrvPort());

		return Feign.builder()
				.client(new OkHttpClient())
				.encoder(new Encoder.Default())
				.decoder(new Decoder.Default())
				// .encoder(new GsonEncoder())
				// .decoder(new GsonDecoder())
				.logger(new Slf4jLogger(ProducerServiceGreetingsClient.class))
				.logLevel(Logger.Level.FULL)
				.target(ProducerServiceGreetingsClient.class,
						buildProducerServiceGreetingsUrl(
								getProducerSrvHostname(),
								getProducerSrvPort()
						)
				);
	}

	protected String buildProducerServiceGreetingsUrl(final String hostname, final int port) {

		return "http://" + hostname + ":" + port + "/greetings";
	}

}
