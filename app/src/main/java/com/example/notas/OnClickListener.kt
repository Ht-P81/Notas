package com.example.notas

interface OnClickListener {
    fun onChecked(nota: Notas)
    fun onLongClick(nota: Notas, currentAdapter: NotaAdapter)
}