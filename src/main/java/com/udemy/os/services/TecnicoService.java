package com.udemy.os.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.os.domain.Pessoa;
import com.udemy.os.domain.Tecnico;
import com.udemy.os.dtos.TecnicoDTO;
import com.udemy.os.repositories.PessoaRepository;
import com.udemy.os.repositories.TecnicoRepository;
import com.udemy.os.services.exceptions.DataIntegratyViolationException;
import com.udemy.os.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	
	@Autowired
	private PessoaService pessoaService;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<TecnicoDTO> findAll() {
		List<TecnicoDTO> listDTO = repository.findAll()
				.stream().map(obj -> new TecnicoDTO(obj)).collect(Collectors.toList());
		
		return listDTO;
	}
	
	public Tecnico create(TecnicoDTO objDTO) {
		if (pessoaService.findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		return repository.save(new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone()));
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDTO) {
		Tecnico oldObj = findById(id);
		
		if (pessoaService.findByCPF(objDTO) != null && pessoaService.findByCPF(objDTO).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setNome(objDTO.getNome());
		oldObj.setCpf(objDTO.getCpf());
		oldObj.setTelefone(objDTO.getTelefone());
		return repository.save(oldObj);
	}
	
	public void delete(Integer id) {
		Tecnico obj = findById(id);
		if (obj.getList().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui Ordens de Serviço, não pode ser deletado!");
		}
		repository.deleteById(id);
	}
	
	

}
