package com.example.notas

//Creamos un objeto constants, com.example.notas boton derecho poner nombre y elegir tipo object
object Constants {
    // BASES DE DATOS
    const val DATABASE_NAME="Notas"
    const val DATABASE_VERSION=1

    // TABLAS
    const val ENTITY_NOTE = "entity_note"

    // PROPIEDADES
    const val PROPERTY_ID ="id"
    const val PROPERTY_DESCRIPTION ="description"
    const val PROPERTY_IS_FINISHED="is_finished"


    const val TRUE = 1
    const val ID_ERROR = -1L
}