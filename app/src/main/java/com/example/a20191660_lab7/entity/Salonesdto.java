package com.example.a20191660_lab7.entity;

public class Salonesdto {
    private String urlImagen;

    public Salonesdto() {
    }

    // Constructor con argumentos


    public Salonesdto(String id, String nombre, long horaCreacion, String contenido, String urlImagen, double latitud, double longitud, String userName, String userApellido, String userFotoPerfil,String nombreUbicacion, String idActividad) {
        this.urlImagen = urlImagen;
    }


    // Getters y setters para cada campo


    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

}
