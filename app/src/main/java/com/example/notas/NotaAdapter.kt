package com.example.notas

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.notas.databinding.ItemNotasBinding

//En el constructor ponemos una variable MutableList de tipo Notas, otra listener OnClickListener
//da algunos errores pq necesita que implementemos algunos métodos
class NotaAdapter (var noteList: MutableList<Notas>, private val listener: OnClickListener):
    RecyclerView.Adapter<NotaAdapter.ViewHolder>() {

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

        holder.setListener(nota)
        holder.binding.tvDescription.text = nota.description
        holder.binding.cbFinished.isChecked = nota.isFinished

        // Donde podemos modificar nuestra vista en tiempo real
        if(nota.isFinished){
            // Con esto cambiamos el tamaño de los textos si está seleccionada la nota
            holder.binding.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f) //nos pide un float por eso ponemos la f al final
        }else{
            holder.binding.tvDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        }

    }

    //Este método nos va indicar cuantos elementos queremos ver en este listado
    // Pasamos la variabale noteList que hemos pasado por parámetro en el constructor de la clase NoteAdapter
    override fun getItemCount(): Int= noteList.size


    fun add(nota: Notas) {
           noteList.add(nota)
        // Es necesario notificar para que lo escrito en el EditText se refleje en la lista
        notifyDataSetChanged()
    }

    fun remove(nota: Notas) {
        noteList.remove(nota)
        // Es necesario notificar para que lo escrito en el EditText se refleje en la lista
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemNotasBinding.bind(view)

        fun setListener(nota: Notas){
            binding.cbFinished.setOnClickListener {
                nota.isFinished = (it as CheckBox).isChecked
                notifyDataSetChanged()
            }
            binding.root.setOnLongClickListener {
                listener.onLongClick(nota)
                true
            }
        }
    }
}