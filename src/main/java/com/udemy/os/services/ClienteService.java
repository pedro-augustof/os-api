package com.udemy.os.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.os.domain.Cliente;
import com.udemy.os.domain.Pessoa;
import com.udemy.os.domain.Tecnico;
import com.udemy.os.dtos.ClienteDTO;
import com.udemy.os.dtos.TecnicoDTO;
import com.udemy.os.repositories.ClienteRepository;
import com.udemy.os.repositories.PessoaRepository;
import com.udemy.os.repositories.TecnicoRepository;
import com.udemy.os.services.exceptions.DataIntegratyViolationException;
import com.udemy.os.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<ClienteDTO> findAll() {
		List<ClienteDTO> listDTO = repository.findAll()
				.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return listDTO;
	}
	
	public Cliente create(ClienteDTO objDTO) {
		if (findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		return repository.save(new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDTO) {
		Cliente oldObj = findById(id);
		
		if (findByCPF(objDTO) != null && findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Cliente obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}
	
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		
		if (obj != null) {
			return obj;
		}
		
		return null;
	}

}
