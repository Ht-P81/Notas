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
    private lateinit var notaTerminadaAdapter: NotaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Todas las vistas se inflan, se construyen en base a lo que hemos construido
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lo inicializamos dentro del onCreate, dentro le pasaremos esa lista
        notaAdapter = NotaAdapter(mutableListOf(), this)
        //Vinculamos nuestro adaptador con el recyclerview
        binding.rvNotas.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter=notaAdapter
        }

        notaTerminadaAdapter = NotaAdapter(mutableListOf(), this)
        //Vinculamos nuestro adaptador con el recyclerview
        binding.rvNotasTerminadas.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter=notaTerminadaAdapter
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

    // Hacemos mandar a llamar la función getData
    // un método de los del ciclo de vida, para cuando ejecute la app cargue el método que
    // alimente los listados
    override fun onStart() {
        super.onStart()
        getData()
    }

    // Metodo que reparte la información entre terminadas y pendientes
    private fun getData(){
        //ArrayList mutableListOf de tipo objeto Notas (pasamos id1, descripcion)
        val data = mutableListOf(Notas(1, "Estudiar"),
            Notas(2, "Enviar mail"),
            Notas(3, "Revisar el correo", true))
        data.forEach{ nota->
            addNotesAuto(nota)
        }
    }

    private fun addNotesAuto(nota: Notas) {
        if(nota.isFinished){
            notaTerminadaAdapter.add(nota)
        }else notaAdapter.add(nota)
    }

    private fun deleteNoteAuto(nota: Notas) {
        if(nota.isFinished){
            notaAdapter.remove(nota)
        }else{
            notaTerminadaAdapter.remove(nota)
        }
    }

    override fun onChecked(nota: Notas) {
        deleteNoteAuto(nota)
        addNotesAuto(nota)
    }

    override fun onLongClick(nota: Notas, currentAdapter: NotaAdapter) {
        val builder= AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_ok),{ dialogInterface, i->
                currentAdapter.remove(nota)
            })
            .setNegativeButton(getString(R.string.dialog_cancel), null)
        builder.create().show()

    }


}