package br.com.alura.curso.swagger.mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import br.com.alura.curso.swagger.mock.exc.MockNaoEncontradoException;
import br.com.alura.curso.swagger.rest.json.JsonSwaggerExemplo;

public class JsonSwaggerExemploMockManager {
	ConcurrentMap<Long, JsonSwaggerExemplo> BASE_DADOS = new ConcurrentHashMap<Long, JsonSwaggerExemplo>();
	private long idSequence = 1;
	
	public List<JsonSwaggerExemplo> listar() {		
		return new ArrayList<JsonSwaggerExemplo>(BASE_DADOS.values());
	}
	
	public JsonSwaggerExemplo consultarPor(long id) throws MockNaoEncontradoException {
		if (!BASE_DADOS.containsKey(id))
			throw new MockNaoEncontradoException();
		return BASE_DADOS.get(id);
	}
	
	public JsonSwaggerExemplo criar(LocalDateTime data, String string, int numero, boolean booleano) {
		while (BASE_DADOS.containsKey(idSequence)) {
			idSequence++;
		}
		
		final JsonSwaggerExemplo MOCK = new JsonSwaggerExemplo(idSequence, data, string, numero, booleano);
		BASE_DADOS.put(idSequence, MOCK);
		return MOCK;
	}
	
	public JsonSwaggerExemplo alterar(long id, LocalDateTime data, String string, int numero, boolean booleano) throws MockNaoEncontradoException {
		if (!BASE_DADOS.containsKey(id))
			throw new MockNaoEncontradoException();
		
		final JsonSwaggerExemplo MOCK = BASE_DADOS.get(id);
		MOCK.setData(data);
		MOCK.setString(string);
		MOCK.setNumero(numero);
		MOCK.setBooleano(booleano);
		return MOCK;
	}
	
	public boolean remover(long id) throws MockNaoEncontradoException {
		if (!BASE_DADOS.containsKey(id))
			throw new MockNaoEncontradoException();
		return BASE_DADOS.remove(id) != null;
	}
}
