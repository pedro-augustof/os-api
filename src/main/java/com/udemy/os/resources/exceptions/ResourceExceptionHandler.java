package com.udemy.os.resources.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.udemy.os.services.exceptions.DataIntegratyViolationException;
import com.udemy.os.services.exceptions.ObjectNotFoundException;

@RestControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException e){
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.NOT_FOUND.value(), e.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegratyViolationException.class)
	public ResponseEntity<StandardError> dataIntegratyViolationException(DataIntegratyViolationException e){
		StandardError error = new StandardError(System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), e.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValidException(MethodArgumentNotValidException e){
		ValidationError error = new ValidationError(System.currentTimeMillis(), 
				HttpStatus.BAD_REQUEST.value(), "Erro na validação dos campos!");
		
		e.getBindingResult().getFieldErrors().forEach(x -> error.addError(x.getField(), x.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
