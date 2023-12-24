package com.company.books.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;
import com.company.books.backend.service.ILibroService;

@RestController
@RequestMapping("/v1")
public class LibroRestController {
	
	@Autowired
	private ILibroService service;
	
	@GetMapping("/libros")
	public ResponseEntity<LibroResponseRest> consultarLibros() {
		ResponseEntity<LibroResponseRest> response = service.buscarLibros();
		return response;
	}
	
	@GetMapping("/libros/{id}")
	public ResponseEntity<LibroResponseRest> consultarLibrosPorId(@PathVariable Long id) {
		ResponseEntity<LibroResponseRest> response = service.buscarLibrosPorId(id);
		return response;
	}
	
	@PostMapping("/libros")
	public ResponseEntity<LibroResponseRest> crearLibro(@RequestBody Libro libro) {
		ResponseEntity<LibroResponseRest> response = service.guardarLibro(libro);
		return response;
	}
	
	@PutMapping("/libros/{id}")
	public ResponseEntity<LibroResponseRest> actualizarLibro(@PathVariable Long id, @RequestBody Libro libro) {
		ResponseEntity<LibroResponseRest> response = service.actualizarLibro(id, libro);
		return response;
	}
	
	@DeleteMapping("/libros/{id}")
	public ResponseEntity<LibroResponseRest> eliminarLibro(@PathVariable Long id) {
		ResponseEntity<LibroResponseRest> response = service.eliminarLibro(id);
		return response;
	}

}
