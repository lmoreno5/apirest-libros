package com.company.books.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDao;
import com.company.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceImp implements ILibroService{
	
	private static final Logger log = LoggerFactory.getLogger(LibroServiceImp.class);
	
	@Autowired
	private ILibroDao libroDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarLibros() {
		
		log.info("Iniciado el servicio de busqueda de libros");
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			
			List<Libro> libro = (List<Libro>) libroDao.findAll();
			response.getLibroResponse().setLibro(libro);
			response.setMetadata("Respuesta ok", "200", "Respuesta exitosa");
			
		}catch (Exception e) {
			
			response.setMetadata("Respuesta ko", "-1", "Respuesta erronea");
			log.error("error en la consulta", e.getMessage());
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);

	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarLibrosPorId(Long id) {
		
		log.info("Iniciado el servicio de busqueda de libros por id");
		LibroResponseRest response = new LibroResponseRest();
		List <Libro> list = new ArrayList<>();
		
		try {
			
			Optional<Libro> libro = libroDao.findById(id);
			
			if (libro.isPresent()) {
				
				list.add(libro.get());
				response.getLibroResponse().setLibro(list);
				
			} else {
				
				log.error("libro no encontrado");
				response.setMetadata("Respuesta ko", "-1", "Libro no encontrado");				
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND);
				
			}
				
		} catch (Exception e) {
			
			response.setMetadata("Respuesta ko", "-1", "Respuesta erronea");
			log.error("error en la consulta", e.getMessage());
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.setMetadata("Respuesta ok", "200", "Consulta exitosa");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
		
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> guardarLibro(Libro libro) {
		
		log.info("Iniciado el servicio de crear libros");
		LibroResponseRest response = new LibroResponseRest();
		List <Libro> list = new ArrayList<>();		
		
		try {
			
			Libro libroGuardado = libroDao.save(libro);
			if (libroGuardado != null) {
				
				list.add(libro);
				response.getLibroResponse().setLibro(list);
				
			} else {
				
				response.setMetadata("Respuesta ko", "-1", "Respuesta erronea");
				log.error("error en el guardado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
				
			}

			
		} catch (Exception e) {
			
			response.setMetadata("Respuesta ko", "-1", "Respuesta erronea");
			log.error("error en la consulta", e.getMessage());
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.setMetadata("Respuesta ok", "200", "Consulta exitosa");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
		
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> actualizarLibro(Long id, Libro libro) {
		
		log.info("Iniciado el servicio de actualizar libros");		
		LibroResponseRest response = new LibroResponseRest();
		List <Libro> list = new ArrayList<>();	

		
		try {
			
			Optional<Libro> libroBuscado = libroDao.findById(id);
			
			if (libroBuscado.isPresent()) {
				
				libroBuscado.get().setNombre(libro.getNombre());
				libroBuscado.get().setDescripcion(libro.getDescripcion());
				libroBuscado.get().setCategoria(libro.getCategoria());
				Libro libroActualizar = libroDao.save(libroBuscado.get());
				
				if (libroActualizar != null) {
					
					list.add(libroActualizar);
					response.getLibroResponse().setLibro(list);
					
				} else {
					
					response.setMetadata("Respuesta ko", "-1", "Libro no encontrado");
					log.error("error al actualizar libro");
					return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
					
				}
				
			} else {
				
				response.setMetadata("Respuesta ko", "-1", "Libro no encontrado");
				log.error("libro no encontrado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST);
				
			}
			
			
		} catch (Exception e) {
			
			response.setMetadata("Respuesta ko", "-1", "Respuesta erronea");
			log.error("error en la consulta", e.getMessage());
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.setMetadata("Respuesta ok", "200", "Actualizacion exitosa");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
		
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> eliminarLibro(Long id) {
		
		
		log.info("Servicio de eliminar libro iniciado");	
		LibroResponseRest response = new LibroResponseRest();		
		
		try {
			
			libroDao.deleteById(id);			
			
		} catch (Exception e) {
			
			log.error("Error en el servidor", e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta ko", "500", "Error en el servidor");
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.setMetadata("Respuesta ok", "200", "Borrado correctamente");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);	
	
	}

}
