package com.udemy.os.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.udemy.os.domain.Cliente;
import com.udemy.os.dtos.ClienteDTO;
import com.udemy.os.services.ClienteService;

import static org.springframework.http.HttpStatus.*;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;

	@GetMapping("/{id}")
	@ResponseStatus(OK)
	public ClienteDTO findById(@PathVariable Integer id) {
		return new ClienteDTO(service.findById(id));
	}
	
	@GetMapping
	@ResponseStatus(OK)
	public List<ClienteDTO> findAll(){
		return service.findAll();
	}
	
	@PostMapping
	public ResponseEntity<ClienteDTO> create(@RequestBody @Valid ClienteDTO objDTO) {
		Cliente obj = service.create(objDTO);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(OK)
	public ClienteDTO update(@PathVariable Integer id, @RequestBody @Valid ClienteDTO objDTO) {
		return new ClienteDTO(service.update(id, objDTO));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}
}
