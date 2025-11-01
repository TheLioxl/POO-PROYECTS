    package org.poo.api;

import java.util.List;

public interface ApiOperacionBD<T, ID> {

    public int getSerial();

    public T insertInto(T objeto, String ruta);

    public List<T> selectFrom();
    
    public List<T> selectFromWhereActivos();

    public int numRows();

    public Boolean deleteFrom(ID codigo);

    public T updateSet(ID codigo, T objeto, String ruta);

    public T getOne(ID codigo);
}