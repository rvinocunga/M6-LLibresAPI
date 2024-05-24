package com.roger_vinocunga.gestionlibros.backend.presentation.restcontrollers;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roger_vinocunga.gestionlibros.backend.business.model.CategoriaLibro;
import com.roger_vinocunga.gestionlibros.backend.business.model.Libro;
import com.roger_vinocunga.gestionlibros.backend.business.services.LibroServices;

import com.roger_vinocunga.gestionlibros.backend.presentation.config.RespuestaError;

@WebMvcTest(controllers=LibroController.class)
public class LibroControllerTest {
	
	@Autowired
	private MockMvc miniPostman;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private LibroServices librosServices;
	
	private Libro libro1;
	private Libro libro2;
	
	@BeforeEach
	void init() {
		initObjects();
	}
	
	@Test
	void pedimos_todos_los_libros() throws Exception {
		
		// Arrange
		
		List<Libro> libros = Arrays.asList(libro1, libro2);
		when(librosServices.getAll()).thenReturn(libros);
		
		// Act
		
		MvcResult respuesta 
		= miniPostman.perform(get("/libros").contentType("application/json"))
											
		.andExpect(status().isOk())
		
		.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(libros);
		
		// Assert
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
		
	}
	
	
	@Test
	void obtenemos_libro_a_partir_de_su_id() throws Exception{
		
		when(librosServices.read(100L)).thenReturn(Optional.of(libro1));
		
		MvcResult respuesta = miniPostman.perform(get("/libros/100").contentType("application/json"))
									.andExpect(status().isOk())
									.andReturn();
		
		String responseBody = respuesta.getResponse().getContentAsString();
		String respuestaEsperada = objectMapper.writeValueAsString(libro1);
		
		assertThat(responseBody).isEqualToIgnoringWhitespace(respuestaEsperada);
	
	}
	
	
	@Test
	void eliminamos_libro_ok() throws Exception{
		
		miniPostman.perform(delete("/libros/789")).andExpect(status().isNoContent());
		
		verify(librosServices, times(1)).delete(789L);
	}
	
	
	// **************************************************************
	//
	// Private Methods
	//
	// **************************************************************
	
	private void initObjects() {
		
        libro1 = new Libro();
        libro1.setId(100L);
        libro1.setTitulo("El nombre del viento");
        libro1.setAutor("Patrick Rothfuss");
        libro1.setPrecio(20.50);
        libro1.setCategoria(CategoriaLibro.CIENCIA_FICCION);
        libro1.setAnyoPublicacion(2007);
        libro1.setIsbn("9788401339790");

        libro2 = new Libro();
        libro2.setId(101L);
        libro2.setTitulo("Cien anos de soledad");
        libro2.setAutor("Gabriel Garcia Marquez");
        libro2.setPrecio(18.99);
        libro2.setCategoria(CategoriaLibro.NOVELA);
        libro2.setAnyoPublicacion(1967);
        libro2.setIsbn("9788437616555");
		
	}
	
}
