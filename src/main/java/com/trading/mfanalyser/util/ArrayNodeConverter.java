package com.trading.mfanalyser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jakarta.persistence.AttributeConverter;

public class ArrayNodeConverter implements AttributeConverter<Object, String> {

	private final static Logger LOGGER = LoggerFactory.getLogger(ArrayNodeConverter.class);

	@Autowired
	ObjectMapper mapper;

	@Override
	public String convertToDatabaseColumn(Object attribute) {
		if (attribute == null) {
			return mapper.createArrayNode().toString();
		}
		try {
			return mapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			LOGGER.error("Exception while converting to database column", e);
			return null;
		}
	}

	@Override
	public Object convertToEntityAttribute(String dbData) {
		try {
			if (!StringUtils.hasText(dbData)) {
				return mapper.createArrayNode();
			}
			return (ArrayNode) mapper.readTree(dbData);
		} catch (Exception e) {
			LOGGER.error("Exception while converting to entity attribute", e);
			return null;
		}
	}
}
