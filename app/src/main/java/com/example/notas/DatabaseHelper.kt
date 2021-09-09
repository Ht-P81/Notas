package com.example.notas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Creamos esta clase para empezar con las bases de datos
// Parámetros de SQLiteOpenHelper (context:, name:, factory:, version:)
// primero el contexto (pasado por parametro en el nombre de la clase DatabaseHelper(context: Context)
// segundo el name de la base de datos que lo creamos en una variable dentro de
// la clase de constantes que hemos creado anteriormente: object Constants {
// después de completar los campos pedidos por parámetros nuestra clase nos marcará un error pq
// hemos heredado de SQLiteOpenHelper y necesitamos implementar algunos métodos.
// Dentro de la clase le damos a ctrl + i y aparacerá todos los métodos que debemos implementar
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, Constants.DATABASE_NAME,
    null, Constants.DATABASE_VERSION ) {

    //Renombramos los argumentos de los métodos para que todo sea más entendible
    override fun onCreate(db: SQLiteDatabase?) {
        // Aquí vamos a definir como queremos que se estructure nuestra base de datos
        // Aquí vamos a definir las tablas que van a estar interactuando
        // Nuestra tabla tendrá los mismos campos en las tablas que parámetros en nuestra
        // clase Notas (ID, DESCRIPTION, ISFINISHED)
        val createTable ="CREATE TABLE ${Constants.ENTITY_NOTE}(" +
                "${Constants.PROPERTY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${Constants.PROPERTY_DESCRIPTION} VARCHAR(60), " +
                "${Constants.PROPERTY_IS_FINISHED} BOOLEAN)"

        //Una vez creada la tabla la ejecutamos
        db?.execSQL(createTable)

        //Por último habría que instanciar desde la MainActivity (creamos variable y luego
        //instanciamos dentro del onCreate
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    // Creamos un método que insertará nuestra nota, una vez creado lo utilizamos dentro del MainActivity
    fun insertNote(notas: Notas): Long{
        // Con esta variable escribimos en la base de datos
        val database = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(Constants.PROPERTY_DESCRIPTION, notas.description)
            put(Constants.PROPERTY_IS_FINISHED, notas.isFinished)
        }

        // Esto va a devolver un long que va a ser reasignado dentro de la variable result
        val resultId = database.insert(Constants.ENTITY_NOTE, null, contentValues)
        return resultId
    }
}