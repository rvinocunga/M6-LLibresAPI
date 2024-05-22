package com.roger_vinocunga.gestionlibros.backend.presentation.restcontrollers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.roger_vinocunga.gestionlibros.backend.business.model.Libro;
import com.roger_vinocunga.gestionlibros.backend.business.services.LibroServices;
import com.roger_vinocunga.gestionlibros.backend.presentation.config.RespuestaError;

@RestController
public class LibroController {

    @Autowired
    private LibroServices libroServices;

    @GetMapping("/libros")
    public List<Libro> getAll() {
        return libroServices.getAll();
    }

    @GetMapping("/libros/{id}")
    public ResponseEntity<?> read(@PathVariable Long id) {

        Optional<Libro> optional = libroServices.read(id);

        if (optional.isEmpty()) {
            RespuestaError respuestaError = new RespuestaError("No se encuentra el libro con ID " + id);
            return new ResponseEntity<>(respuestaError, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optional.get());
    }
    
    @PostMapping("/libros")
	public ResponseEntity<?> create(@RequestBody Libro libro, UriComponentsBuilder ucb) {
		
		Long codigo = null;
		
		try {
			codigo = libroServices.create(libro);
		} catch(IllegalStateException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		URI uri = ucb.path("/libros/{codigo}").build(codigo);
		
		return ResponseEntity.created(uri).build();
	}
    
    @DeleteMapping("/libros/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		
		try {
			libroServices.delete(id);
		} catch(IllegalStateException e) {
			throw new RuntimeException("No se encuentra el libro con id [" + id + "]. No se ha podido eliminar.");
		}
		
	}
	
    @PutMapping("/libros/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @RequestBody Libro libro) {
        if (libro == null || libro.getId() == null) {
            throw new IllegalArgumentException("El libro o su ID no pueden ser nulos para actualizar.");
        }
        
        if (!id.equals(libro.getId())) {
            throw new IllegalArgumentException("El ID del libro en el cuerpo de la solicitud no coincide con el ID proporcionado en la ruta.");
        }

        try {
            libroServices.update(libro);
        } catch (IllegalStateException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    // ****************************************************
    //
    // Gestión de excepciones
    //
    // ****************************************************

    @ExceptionHandler({ IllegalArgumentException.class, ClassCastException.class })
    public ResponseEntity<?> gestor1(Exception e) {
        return ResponseEntity.badRequest().body(new RespuestaError(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> gestor2(Exception e) {
        return ResponseEntity.badRequest().body(new RespuestaError(e.getMessage()));
    }
}
