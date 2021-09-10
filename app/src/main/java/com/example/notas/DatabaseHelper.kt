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

    // Método de léctura
    fun getAllNotes(): MutableList<Notas>{
        // Creamos la variable notas de tipo Notas en un MutableList
        val notas: MutableList<Notas> = mutableListOf()

        // Esta variable es de lectura
        val database = this.readableDatabase

        //Lo siguiente será una consulta, la básica, seleccionar todo desde la unica tabla creada:
        val query = "SELECT * FROM ${Constants.ENTITY_NOTE}"

        //Ejecutamos esa sentencia que recibe la consulta, los argumentos los pone a null
        val result= database.rawQuery(query, null)

        //Con esto vamos a evaluar el resultado y en base a eso vamos a construir nuestros objetos de tipo nota
        // Si hay al menos un registro quiere decir que podemos movernos hacia delante, que hay al menos un registro
        if(result.moveToFirst()){
            // Empezamos con el do while, nos aseguramos de que tenemos al menos un registro
            do{
                //Creamos una variable de tipo nota y le vamos a ir agregando cada uno de sus
                    //campos, id, description, y isFinished
                val nota = Notas()
                // Agregamos el primer campo, agregamos el result de la consulta y le decimos el index
                // pasándole como parámetro la constante.
                nota.id = result.getLong(result.getColumnIndex(Constants.PROPERTY_ID))
                nota.description= result.getString(result.getColumnIndex(Constants.PROPERTY_DESCRIPTION))
                //El último parámetro del objeto Notas es isFinished de tipo Boolean pero no existe el método
                // getBoolean, cuando vemos en databaseInspector la columna vemos que da valores 0 y 1
                // por eso podemos utilizar el metodo Int y luego lo comparamos con 1 que es true,
                // si fuera falso lo comparamos con 0
                nota.isFinished=result.getInt(result.getColumnIndex
                    (Constants.PROPERTY_IS_FINISHED)) == Constants.TRUE

                //Finalmente agregamos a nuestra variable notas de tipo Notas en un mutableList
                notas.add(nota)
            } while (result.moveToNext())
        }
        return notas
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
        val resultId = database.insert(Constants.ENTITY_NOTE,
            null,
            contentValues)
        return resultId
    }

    // Método de actualizar la base de datos
    fun updateNota(notas: Notas): Boolean{
        // Con esta variable escribimos en la base de datos
        val database = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(Constants.PROPERTY_DESCRIPTION, notas.description)
            put(Constants.PROPERTY_IS_FINISHED, notas.isFinished)
        }

        // Guardamos el resultado de la actualización, whereClause se pone la PrimaryKey
        val result = database.update(Constants.ENTITY_NOTE, contentValues,
            "${Constants.PROPERTY_ID} = ${notas.id}",
            null)

        // Si fue todo bien nos devuelve un 1 y si da algún error devolverá un 0
        return result == Constants.TRUE
    }

    // Método de borrar un registro de la base de datos
    fun deleteNote(notas: Notas): Boolean{
        // leemos la base de datos
        val database = this.readableDatabase
        // El resultado del borrado tenemos que decir donde lo borramos (la tabla), donde
        val result= database.delete(Constants.ENTITY_NOTE,
            "${Constants.PROPERTY_ID} = ${notas.id}", null)

        return result == Constants.TRUE
    }
}