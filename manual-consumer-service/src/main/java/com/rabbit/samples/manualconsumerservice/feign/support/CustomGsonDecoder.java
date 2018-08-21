package com.rabbit.samples.manualconsumerservice.feign.support;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import feign.gson.GsonDecoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CustomGsonDecoder extends GsonDecoder {

	public CustomGsonDecoder() {

		super(
				new GsonBuilder()
						.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
								LocalDateTime.parse(
										json.getAsJsonPrimitive().getAsString(),
										DateTimeFormatter.ISO_LOCAL_DATE_TIME
								))
						.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->
								new JsonPrimitive(
										DateTimeFormatter.ISO_LOCAL_DATE_TIME
												.format(localDateTime)
								))
						.create()
		);
	}

}
