package com.roger_vinocunga.gestionlibros.backend.business.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.roger_vinocunga.gestionlibros.backend.business.model.CategoriaLibro;
import com.roger_vinocunga.gestionlibros.backend.business.model.Libro;
import com.roger_vinocunga.gestionlibros.backend.business.services.LibroServices;

@Service
public class LibroServicesImpl implements LibroServices {

    private final TreeMap<Long, Libro> LIBROS = new TreeMap<>();

    public LibroServicesImpl() {
        init();
    }

    @Override
    public Long create(Libro libro) {
        Long id = LIBROS.isEmpty() ? 1L : LIBROS.lastKey() + 1;
        libro.setId(id);
        LIBROS.put(id, libro);
        return id;
    }

    @Override
    public Optional<Libro> read(Long id) {
        return Optional.ofNullable(LIBROS.get(id));
    }

    @Override
    public List<Libro> getAll() {
        return new ArrayList<>(LIBROS.values());
    }

    // ***************************************************************
    //
    // Métodos Privados
    //
    // ***************************************************************

    private void init() {
        Libro libro1 = new Libro();
        libro1.setId(1L);
        libro1.setTitulo("Don Quijote de la Mancha");
        libro1.setAutor("Miguel de Cervantes");
        libro1.setPrecio(33.0);
        libro1.setCategoria(CategoriaLibro.NOVELA);
        libro1.setAnyoPublicacion(1605);
        libro1.setIsbn("9780142437230");

        Libro libro2 = new Libro();
        libro2.setId(2L);
        libro2.setTitulo("1984");
        libro2.setAutor("George Orwell");
        libro2.setPrecio(20.0);
        libro2.setCategoria(CategoriaLibro.CIENCIA_FICCION);
        libro2.setAnyoPublicacion(1949);
        libro2.setIsbn("9780451524935");

        Libro libro3 = new Libro();
        libro3.setId(3L);
        libro3.setTitulo("Cien años de soledad");
        libro3.setAutor("Gabriel García Márquez");
        libro3.setPrecio(30.0);
        libro3.setCategoria(CategoriaLibro.NOVELA);
        libro3.setAnyoPublicacion(1967);;
        libro3.setIsbn("9780804171462");

        LIBROS.put(libro1.getId(), libro1);
        LIBROS.put(libro2.getId(), libro2);
        LIBROS.put(libro3.getId(), libro3);
    }

    @Override
    public void update(Libro libro) {
        if (libro == null || libro.getId() == null) {
            throw new IllegalArgumentException("El libro o su ID no pueden ser nulos para actualizar.");
        }

        Long id = libro.getId();
        if (!LIBROS.containsKey(id)) {
            throw new IllegalArgumentException("El libro con ID " + id + " no existe. No se puede actualizar.");
        }

        LIBROS.put(id, libro);
    }


	@Override
	public void delete(Long id) {
		if(id == null) {
			throw new IllegalStateException("No se puede actualizar un producto con código not null");
		}		

		if(!LIBROS.containsKey(id)) {
			throw new IllegalStateException("El producto con código " + id + " no existe. No se puede actualizar.");
		}
		
		LIBROS.remove(id);
	}

	@Override
	public List<Libro> getBetweenPriceRange(double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
