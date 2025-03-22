package com.digitalinovation.lab_padroes_projeto_spring.service;

import com.digitalinovation.lab_padroes_projeto_spring.model.Cliente;

public interface ClienteService {
	Iterable<Cliente> bucarTodos();
	
	Cliente buscarPorId(Long id);
	
	void inserir(Cliente cliente);
	
	void atualizar(Long id, Cliente cliente);
	
	void deletar(Long id);

}
