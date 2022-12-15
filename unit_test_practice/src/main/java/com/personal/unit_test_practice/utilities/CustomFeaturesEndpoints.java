package com.personal.unit_test_practice.utilities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "customFeatures")
public class CustomFeaturesEndpoints {
	
	private Map<String, Object> features = new ConcurrentHashMap<>();
	
	@ReadOperation
	public Map<String, Object> CustomFeature(){
		features.put("customfeature", "example - employee bought the product using premium account.");
		return features;
	}

}
