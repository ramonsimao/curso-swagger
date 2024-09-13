package br.com.alura.curso.swagger.api.involker;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;

import br.com.alura.curso.swagger.api.involker.model.AccessToken;
import br.com.alura.curso.swagger.rest.json.util.JsonUtils;

public class AuthAPIInvolker {
	
	/**
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @return
	 */
	public AccessToken gerar(final String clientId, final String clientSecret) {
		try {
			Map<String, String> parameters = new HashMap<>();
			parameters.put("a", "client_credentials");
			parameters.put("client_id", clientId);
			parameters.put("client_secret", clientSecret);

			String form = parameters.entrySet().stream()
					.map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
					.collect(Collectors.joining("&"));
			
			
			HttpClient client = HttpClient.newHttpClient();
			
			Builder requestBuilder = HttpRequest.newBuilder();
			requestBuilder.uri(URI.create("http://localhost:8081/api/v1/auth/access-token"));
			requestBuilder.header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
			requestBuilder.POST(HttpRequest.BodyPublishers.ofString(form));
			
			// Criar uma requisição GET
			HttpRequest request = requestBuilder.build();
			
			// Enviar a requisição e obter a resposta
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			return JsonUtils.jsonToObject(response.body(), AccessToken.class);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	public boolean validar(final String token) {
		if (token == null)
			return false;
		
		try {			
			HttpClient client = HttpClient.newHttpClient();
			
			Builder requestBuilder = HttpRequest.newBuilder();
			requestBuilder.uri(URI.create("http://localhost:8081/api/v1/auth/validate"));
			requestBuilder.header("Authorization", token);
			requestBuilder.POST(HttpRequest.BodyPublishers.noBody());
			
			// Criar uma requisição GET
			HttpRequest request = requestBuilder.build();
			
			// Enviar a requisição e obter a resposta
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			
			if (response.statusCode() == 200)
				return true;
		} catch (IOException | InterruptedException e) {
			return false;
		}
		
		return false;
	}
}