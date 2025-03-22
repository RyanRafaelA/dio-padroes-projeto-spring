package com.digitalinovation.lab_padroes_projeto_spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalinovation.lab_padroes_projeto_spring.model.Cliente;
import com.digitalinovation.lab_padroes_projeto_spring.model.Endereco;
import com.digitalinovation.lab_padroes_projeto_spring.repository.ClienteRepository;
import com.digitalinovation.lab_padroes_projeto_spring.repository.EnderecoRepository;
import com.digitalinovation.lab_padroes_projeto_spring.service.ClienteService;
import com.digitalinovation.lab_padroes_projeto_spring.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService{
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ViaCepService viaCep;
	
	@Override
	public Iterable<Cliente> buscarTodos() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id) {
		Optional<Cliente> cliente; 
		
		cliente = clienteRepository.findById(id);
		
		return cliente.get();
	}

	@Override
	public void inserir(Cliente cliente) {
		salvarClienteComCeo(cliente);
	}

	@Override
	public void atualizar(Long id, Cliente cliente) {
		Optional<Cliente> clienteBD;
		
		clienteBD = clienteRepository.findById(id);
		if(clienteBD.isPresent()) {
			salvarClienteComCeo(cliente);
		}
	}

	@Override
	public void deletar(Long id) {
		clienteRepository.deleteById(id);
		
	}
	
	private void salvarClienteComCeo(Cliente cliente) {
		String cep;
		Endereco endereco;
		
		cep = cliente.getEndereco().getCep();
		endereco = enderecoRepository.findById(cep).orElseGet(() ->{
			Endereco novoEndereco;
			
			novoEndereco = viaCep.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		clienteRepository.save(cliente);
	}

}
