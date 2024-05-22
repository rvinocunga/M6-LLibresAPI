package com.roger_vinocunga.gestionlibros.backend.business.model;

import java.io.Serializable;

public class Libro implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String titulo;
    private String autor;
    private Double precio;
    private CategoriaLibro categoria;
    private int añoPublicacion;
    private String isbn;

    public Libro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public CategoriaLibro getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaLibro categoria) {
        this.categoria = categoria;
    }

    public int getAñoPublicacion() {
        return añoPublicacion;
    }

    public void setAñoPublicacion(int añoPublicacion) {
        this.añoPublicacion = añoPublicacion;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", precio=" + precio +
                ", categoria=" + categoria +
                ", añoPublicacion=" + añoPublicacion +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
