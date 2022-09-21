package com.udemy.os.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.os.domain.Pessoa;
import com.udemy.os.dtos.TecnicoDTO;
import com.udemy.os.repositories.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		
		if (obj != null) {
			return obj;
		}
		
		return null;
	}
}
