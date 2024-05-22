package com.roger_vinocunga.gestionlibros.backend.business.services;

import java.util.List;
import java.util.Optional;

import com.roger_vinocunga.gestionlibros.backend.business.model.Libro;

public interface LibroServices {

	Long create(Libro libro);	        // C
	Optional<Libro> read(Long id);      // R
	
	/**
	 * 
	 * Lanza una IllegalStateException si el código del producto es null o no existe en el sistema
	 * 
	 */
	void update(Libro libro);
	
	/**
	 * Lanza una IllegalStateException si no existe la id en el sistema
	 * 
	 */
	void delete(Long id);
	
	List<Libro> getBetweenPriceRange(double min, double max);
	
	List<Libro> getAll();
	
}
