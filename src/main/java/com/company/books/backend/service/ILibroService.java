package com.company.books.backend.service;

import org.springframework.http.ResponseEntity;

import com.company.books.backend.model.Libro;
import com.company.books.backend.response.LibroResponseRest;

public interface ILibroService {
	
	public ResponseEntity<LibroResponseRest> buscarLibros();
	public ResponseEntity<LibroResponseRest> buscarLibrosPorId(Long id);
	public ResponseEntity<LibroResponseRest> guardarLibro(Libro libro);
	public ResponseEntity<LibroResponseRest> actualizarLibro(Long id, Libro libro);
	public ResponseEntity<LibroResponseRest> eliminarLibro(Long id);

}
