package com.example.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notas.databinding.ActivityMainBinding

//Implementamos nuestra interfaz con comas y el nombre, en esta caso OnClickListener,
//necesitamos implementar el método de la interfaz, en este caso, onLongClick
class MainActivity : AppCompatActivity(), OnClickListener{

    private lateinit var binding: ActivityMainBinding
    // Vinculamos nuestra vista con el recyclerview a el adaptador desde el main activity
    //Variable
    private lateinit var notaAdapter: NotaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Todas las vistas se inflan, se construyen en base a lo que hemos construido
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //ArrayList mutableListOf de tipo objeto Notas (pasamos id1, descripcion)
        val data = mutableListOf(Notas(1, "Estudiar"),
        Notas(2, "Enviar mail"),
        Notas(3, "Revisar el correo", true))
        // Lo inicializamos dentro del onCreate, dentro le pasaremos esa lista
        notaAdapter = NotaAdapter(data, this)

        //Vinculamos nuestro adaptador con el recyclerview
        binding.reyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter=notaAdapter
        }

        //Funcionalidad del botón agregar
        binding.btnAdd.setOnClickListener {
            // En caso de que no esté vacío
            if(binding.etDescription.text.toString().isNotBlank()){
                // Creamos la variable de objeto tipo Notas
                    // Primer parámetro el número, el segundo el texto y por ultimo el booleano
                val nota = Notas((notaAdapter.itemCount +1).toLong(),
                    binding.etDescription.text.toString().trim())
                //Agregamos la nota la adaptador desde un metodo nuevo
                addNotesAuto(nota)
                //Limpiamos el campo etDescription, si no está vacío (text?) que lo limpie
                binding.etDescription.text?.clear()
                //binding.etDescription.error = null
            }else{
                binding.etDescription.error = getString(R.string.validation_fieldrequired)
            }
        }
    }

    private fun addNotesAuto(nota: Notas) {
        notaAdapter.add(nota)
    }

    private fun deleteNoteAuto(nota: Notas) {
        val builder= AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_ok),{ dialogInterface, i->
                notaAdapter.remove(nota)
            })
            .setNegativeButton(getString(R.string.dialog_cancel), null)

        builder.create().show()

    }

    override fun onChecked(nota: Notas) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(nota: Notas) {
        deleteNoteAuto(nota)
    }


}