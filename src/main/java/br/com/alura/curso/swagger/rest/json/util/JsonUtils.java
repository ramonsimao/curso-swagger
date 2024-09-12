package br.com.alura.curso.swagger.rest.json.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static <T> T jsonToObject(String json, Class<T> clazz) {
		try {
			// Converter o JSON em um objeto Java (Pessoa)
			return MAPPER.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}