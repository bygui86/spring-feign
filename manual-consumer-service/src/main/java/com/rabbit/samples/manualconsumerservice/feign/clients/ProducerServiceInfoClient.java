package com.rabbit.samples.manualconsumerservice.feign.clients;

import com.rabbit.samples.manualconsumerservice.feign.dtos.InfoDto;
import feign.Headers;
import feign.RequestLine;


public interface ProducerServiceInfoClient {

	@RequestLine("GET /")
	@Headers("Accept: application/json")
	InfoDto getInfo();

}
