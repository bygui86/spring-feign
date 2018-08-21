package com.rabbit.samples.manualconsumerservice.feign.clients;

import feign.RequestLine;


public interface ProducerServiceGreetingsClient {

	@RequestLine("GET /")
	String greet();

}
