package com.udemy.os.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.udemy.os.domain.Cliente;
import com.udemy.os.domain.OS;
import com.udemy.os.domain.Tecnico;
import com.udemy.os.domain.enuns.Prioridade;
import com.udemy.os.domain.enuns.Status;
import com.udemy.os.repositories.ClienteRepository;
import com.udemy.os.repositories.OSRepository;
import com.udemy.os.repositories.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private OSRepository osRepository;
	
	@Bean
	public void instaciaDB() {
		Tecnico t1 = new Tecnico(null, "Valdir Cezar", "340.857.120-12", "(88) 98888-8888");
		Tecnico t2 = new Tecnico(null, "Linus Torvalds", "641.760.040-88", "(88) 94545-4545");
		Cliente c1 = new Cliente(null, "Betina Campos", "597.534.530-86", "(88) 98888-7777");
		
		OS os1 = new OS(null, Prioridade.ALTA, "Teste create OS", Status.ANDAMENTO, t1, c1);
		
		t1.getList().add(os1);
		c1.getList().add(os1);
		
		tecnicoRepository.saveAll(Arrays.asList(t1,t2));
		clienteRepository.saveAll(Arrays.asList(c1));
		osRepository.saveAll(Arrays.asList(os1));
	}

}
