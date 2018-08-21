package com.rabbit.samples.manualconsumerservice.configs;

import com.rabbit.samples.manualconsumerservice.feign.clients.ProducerServiceGreetingsClient;
import com.rabbit.samples.manualconsumerservice.feign.clients.ProducerServiceInfoClient;
import com.rabbit.samples.manualconsumerservice.feign.support.CustomGsonDecoder;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.gson.GsonEncoder;
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

		log.debug("ProducerService greetings client hostname {}, port {}", getProducerSrvHostname(), getProducerSrvPort());

		return getFeignClient(
				new Encoder.Default(),
				new Decoder.Default(),
				ProducerServiceGreetingsClient.class,
				buildProducerServiceGreetingsUrl(
						getProducerSrvHostname(),
						getProducerSrvPort()
				));
	}

	@Bean
	public ProducerServiceInfoClient producerServiceInfoClient() {

		log.debug("ProducerService info client hostname {}, port {}", getProducerSrvHostname(), getProducerSrvPort());

		return getFeignClient(
				new GsonEncoder(),
				new CustomGsonDecoder(),
				ProducerServiceInfoClient.class,
				buildProducerServiceInfoUrl(
						getProducerSrvHostname(),
						getProducerSrvPort()
				));
	}

	protected <T> T getFeignClient(final Encoder encoder, final Decoder decoder, final Class<T> clientClass, final String target) {

		return Feign.builder()
				.client(new OkHttpClient())
				.encoder(encoder)
				.decoder(decoder)
				.logger(new Slf4jLogger(clientClass))
				.logLevel(Logger.Level.FULL)
				.target(clientClass,
						target
				);
	}

	protected String getProducerServiceRootUrl(final String hostname, final int port) {

		return "http://" + hostname + ":" + port;
	}

	protected String buildProducerServiceGreetingsUrl(final String hostname, final int port) {

		return getProducerServiceRootUrl(hostname, port) + "/greetings";
	}

	protected String buildProducerServiceInfoUrl(final String hostname, final int port) {

		return getProducerServiceRootUrl(hostname, port) + "/info";
	}

}
