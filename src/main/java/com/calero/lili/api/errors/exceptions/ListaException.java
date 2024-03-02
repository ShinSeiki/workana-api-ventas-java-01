package com.calero.lili.api.errors.exceptions;

import java.util.List;

public class ListaException extends RuntimeException {

    public ListaException(String message) {
        super(message);
    }

    public static List<ListaException> convertirALista(List<ListaException> lista, ListaException excepcion) {
        lista.add(excepcion);
        return lista;
    }
}
