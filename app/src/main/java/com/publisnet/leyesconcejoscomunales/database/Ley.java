package com.publisnet.leyesconcejoscomunales.database;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mariano on 10/08/16.
 */
public class Ley extends RealmObject {
    @PrimaryKey
    public int id;
    public String titulo;
    public String titulocorto;
    public String descripcion;
    public int pos;

    //@Expose(serialize = false, deserialize = false)
    public RealmList<Categoria> categorias;
}
