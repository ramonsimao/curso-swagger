package br.com.alura.curso.swagger.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	private static boolean segurancaOn = false;

	@GetMapping(value = "/ativar-seguranca")
	public ResponseEntity<?> ativarSeguranca() {
		ApiExemploDocumentacao.segurancaOn = true;
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/desativar-seguranca")
	public ResponseEntity<?> desativarSeguranca() {
		ApiExemploDocumentacao.segurancaOn = false;
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/json-completo", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<?> obterJsonExemploPor(@RequestParam Map<String, String> allParams,
			@RequestHeader(name = "Authorization", required = false) String authorization) {

		if (ApiExemploDocumentacao.segurancaOn)
			if (!authApi.validar(authorization))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.TEXT_PLAIN)
						.body("Token inválido ou expirado!");				

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
			return ResponseEntity.internalServerError().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping(value = "/json-completo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<?> criarJsonExemplo(@RequestBody JsonSwaggerExemplo body,
			@RequestHeader(name = "Authorization", required = false) String authorization) {
		if (ApiExemploDocumentacao.segurancaOn)
			if (!authApi.validar(authorization))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.TEXT_PLAIN)
						.body("Token inválido ou expirado!");

		try {
			JsonSwaggerExemplo jsonExemplo;
			jsonExemplo = DB_MOCK.criar(body.getData(), body.getString(), body.getNumero(), body.isBooleano());
			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonExemplo);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().contentType(MediaType.TEXT_PLAIN).body(e.getMessage());
		}
	}

	@CrossOrigin(origins = "*")
	@PutMapping(value = "/json-completo/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {
			MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<?> alterarJsonExemplo(@PathVariable Long id, @RequestBody JsonSwaggerExemplo body,
			@RequestHeader(name = "Authorization", required = false) String authorization) {
		if (ApiExemploDocumentacao.segurancaOn)
			if (!authApi.validar(authorization))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.TEXT_PLAIN)
						.body("Token inválido ou expirado!");

		try {
			body.setId(id);
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

	@CrossOrigin(origins = "*")
	@DeleteMapping(value = "/json-completo/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<?> removerJsonExemplo(@PathVariable Long id,
			@RequestHeader(name = "Authorization", required = false) String authorization) {
		if (ApiExemploDocumentacao.segurancaOn)
			if (!authApi.validar(authorization))
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.TEXT_PLAIN)
						.body("Token inválido ou expirado!");

		try {
			if (id != 0) {
				JsonSwaggerExemplo jsonExemplo = DB_MOCK.remover(id);
				return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonExemplo);
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