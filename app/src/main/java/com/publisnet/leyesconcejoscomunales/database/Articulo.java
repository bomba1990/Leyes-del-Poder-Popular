package com.publisnet.leyesconcejoscomunales.database;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mariano on 10/08/16.
 */
public class Articulo extends RealmObject {
    @PrimaryKey
    public int id;
    @Index
    public String titulo= "";
    public String titulocorto= "";
    public String descripcion = "";
    public String contenido= "";


    public int categoria;
    public int pos;
    public int ley;
    public int posicion; // posicion rescpecto a la ley

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTitulocorto() {
        return titulocorto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getContenido() {
        return contenido;
    }

    public int getCategoria() {
        return categoria;
    }

    public int getPos() {
        return pos;
    }

    public int getPosicion() {
        return posicion;
    }
}
