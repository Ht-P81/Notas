package com.example.notas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.databinding.ItemNotasBinding

//En el constructor ponemos una variable MutableList de tipo Notas, otra listener OnClickListener
//da algunos errores pq necesita que implementemos algunos métodos
class NotaAdapter (var noteList: MutableList<Notas>, private val listener: OnClickListener):
    RecyclerView.Adapter<NotaAdapter.ViewHolder>() {

    //Creamos una clase viewholder de tipo view que herede de RecyclerView
    //Esta clase nos va a permitir inflar la vista, que tomemos item_notas y se puede inflar la vista
    //dentro de activity_main, lo mismo que hacer binding dentro del setConentView en MainActivity.kt
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val binding = ItemNotasBinding.bind(view)
    }

    //METODOS OBLIGATORIOS DE IMPLEMENTAR DE LA RECYCLERVIEW.ADAPTER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Aquí inflamos nuestra vista...
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notas, parent, false)
        // Retornamos la variable que nos exige el método
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Le vamos a dar un valor a la descripción del TextView que forma cada uno de los elementos de
        //nuestra vista, esto está en item_notas-> textView -> tvDescription
        //nuestra clase Notas, el parámetro description
        val nota = noteList.get(position)

        holder.binding.tvDescription.text = nota.description

    }

    //Este método nos va indicar cuantos elementos queremos ver en este listado
    // Pasamos la variabale noteList que hemos pasado por parámetro en el constructor de la clase NoteAdapter
    override fun getItemCount(): Int= noteList.size
}