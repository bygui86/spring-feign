package com.rabbit.samples.consumerservice.feign.clients;

import feign.RequestLine;


public interface ProducerServiceGreetingsClient {

	@RequestLine("GET /")
	String greet();

}
