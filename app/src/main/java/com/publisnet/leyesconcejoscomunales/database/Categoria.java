package com.publisnet.leyesconcejoscomunales.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mariano on 10/08/16.
 */
public class Categoria extends RealmObject {
    @PrimaryKey
    public int id;
    public String name= "";
    public String descripcion = "";
    public String contenido= "";
    public int parent;
    public int ley;
    public int pos;

    public RealmList<Articulo> articulos;
    public RealmList<Categoria> categorias;
}
