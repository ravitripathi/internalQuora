package com.coviam.notifications.service;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaSender {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	

	public void send(String topic, JsonObject object) {
	    kafkaTemplate.send(topic, object.toString());
	}
}