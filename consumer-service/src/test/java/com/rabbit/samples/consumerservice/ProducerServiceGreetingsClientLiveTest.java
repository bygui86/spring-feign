package com.rabbit.samples.consumerservice;

import com.rabbit.samples.consumerservice.feign.clients.ProducerServiceGreetingsClient;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * Consumes http://localhost:8082/greetings from producer-service
 */
@RunWith(JUnit4.class)
public class ProducerServiceGreetingsClientLiveTest {

	ProducerServiceGreetingsClient producerServiceGreetingsClient;

	@Before
	public void setup() {

		producerServiceGreetingsClient = Feign.builder()
				.client(new OkHttpClient())
				.encoder(new Encoder.Default())
				.decoder(new Decoder.Default())
				// .encoder(new GsonEncoder())
				// .decoder(new GsonDecoder())
				.logger(new Slf4jLogger(ProducerServiceGreetingsClient.class))
				.logLevel(Logger.Level.FULL)
				.target(ProducerServiceGreetingsClient.class, "http://localhost:8082/greetings");
	}

	@Test
	public void greetingsTest() {

		final String greetings = producerServiceGreetingsClient.greet();

		Assert.assertNotNull(greetings);
	}

}
