package com.teqmonic.microservices.mortgagerateservice.service.util;

import static com.teqmonic.microservices.mortgagerateservice.service.util.Constants.MOCK_RESPONSE_PATH;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static <T> T convertJsonToJava(String fileName, Class<T> classType) {
		logger.info("In convertJsonToJava, fileName {}, classType {} ", fileName, classType);
		T type = null;
		File file = new File(MOCK_RESPONSE_PATH + fileName);
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			type = mapper.readValue(file, classType);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return type;
	}
}
