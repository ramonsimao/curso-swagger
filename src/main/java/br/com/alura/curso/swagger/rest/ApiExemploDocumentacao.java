package br.com.alura.curso.swagger.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.curso.swagger.api.involker.AuthAPIInvolker;
import br.com.alura.curso.swagger.mock.JsonSwaggerExemploMockManager;
import br.com.alura.curso.swagger.mock.exc.MockNaoEncontradoException;
import br.com.alura.curso.swagger.rest.json.JsonSwaggerExemplo;

@RestController
@RequestMapping("api/v1/docswagger/exemplo")
public class ApiExemploDocumentacao {
	private static JsonSwaggerExemploMockManager DB_MOCK = new JsonSwaggerExemploMockManager();
	private static AuthAPIInvolker authApi = new AuthAPIInvolker(); 

	@GetMapping(value = "/json-completo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> obterJsonExemploPor(@RequestParam Map<String, String> allParams,
			@RequestHeader(name = "Authorization", required = true) String authorization) {
		if (!authApi.validar(authorization))
			return new ResponseEntity<>("Token inválido ou expirado!", HttpStatus.UNAUTHORIZED);
		
		try {
			
			if (allParams.containsKey("id")) {
				JsonSwaggerExemplo jsonExemplo = DB_MOCK.consultarPor(Long.parseLong(allParams.get("id")));
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonExemplo);
			} else {
				final List<JsonSwaggerExemplo> LISTA = DB_MOCK.listar();
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(LISTA);
			}
		} catch (MockNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PostMapping(value = "/json-completo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<?> criarJsonExemplo(@RequestBody JsonSwaggerExemplo body,
			@RequestHeader(name = "Authorization", required = true) String authorization) {
		if (!authApi.validar(authorization))
			return new ResponseEntity<>("Token inválido ou expirado!", HttpStatus.UNAUTHORIZED);
		
		try {
			JsonSwaggerExemplo jsonExemplo;
			jsonExemplo = DB_MOCK.criar(body.getData(), body.getString(), body.getNumero(), body.isBooleano());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonExemplo);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@PutMapping(value = "/json-completo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<?> alterarJsonExemplo(@RequestBody JsonSwaggerExemplo body,
			@RequestHeader(name = "Authorization", required = true) String authorization) {
		if (!authApi.validar(authorization))
			return new ResponseEntity<>("Token inválido ou expirado!", HttpStatus.UNAUTHORIZED);
		
		try {
			JsonSwaggerExemplo jsonExemplo;
			jsonExemplo = DB_MOCK.alterar(body.getId(), body.getData(), body.getString(), body.getNumero(),
					body.isBooleano());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonExemplo);
		} catch (MockNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}

	@DeleteMapping(value = "/json-completo")
	public ResponseEntity<?> removerJsonExemplo(@RequestParam Map<String, String> allParams,
			@RequestHeader(name = "Authorization", required = true) String authorization) {
		if (!authApi.validar(authorization))
			return new ResponseEntity<>("Token inválido ou expirado!", HttpStatus.UNAUTHORIZED);
		
		try {
			if (allParams.containsKey("id")) {
				DB_MOCK.remover(Long.parseLong(allParams.get("id")));
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().build();
			}
		} catch (MockNaoEncontradoException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}