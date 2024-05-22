package com.maga.myapplication;

public class Nota {
    String titulo;
    String descripcion;
    String fecha;
    Timestamp timestamp;
    /**
      * Constructor por defecto.
      */
    public Nota() {
    }
    /**
      * Obtener el título de la nota.
      * @return El título de la nota.
      */
    public String getTitulo() {
        return titulo;
    }
    /**
      * Establecer el título de la nota.
      * @param titulo El título de la nota.
      */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    /**
      * Obtener la descripción de la nota.
      * @return La descripción de la nota.
      */
    public String getDescripcion() {
        return descripcion;
    }
    /**
      * Establecer la descripción de la nota.
      * @param descripcion La descripción de la nota.
      */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
      * Obtener la marca de tiempo de la nota.
      * @return La marca de tiempo de la nota.
      */
    public Timestamp getTimestamp() {
        return timestamp;
    }
    /**
      * Establecer la marca de tiempo de la nota.
      * @param timestamp La marca de tiempo de la nota.
      */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    /**
      * Obtener la fecha de la nota.
      * @return La fecha de la nota.
      */
    public String getFecha() {
        return fecha;
    }
    /**
      * Establecer la fecha de la nota.
      * @param fecha La fecha de la nota.
      */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}