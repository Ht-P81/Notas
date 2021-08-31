package com.example.notas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        Notas(2, "Enviar mail"))
        // Lo inicializamos dentro del onCreate, dentro le pasaremos esa lista
        notaAdapter = NotaAdapter(data, this)

        //Vinculamos nuestro adaptador con el recyclerview
        binding.reyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter=notaAdapter
        }

    }

    override fun onLongClick(nota: Notas) {
        TODO("Not yet implemented")
    }
}