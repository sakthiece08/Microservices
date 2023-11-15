package com.teqmonic.microservices.mortgagecalculationservice.service.util;

import static com.teqmonic.microservices.mortgagecalculationservice.service.util.Constants.MOCK_RESPONSE_PATH;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static <T> T convertJsonToJava(String fileName, Class<T> classType) {
		logger.info("In convertJsonToJava, fileName {}, classType {} ", fileName, classType);
		T type = null;
		InputStream input;
		try {
			input = new ClassPathResource(MOCK_RESPONSE_PATH + fileName).getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			type = mapper.readValue(input, classType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return type;
	}

}
