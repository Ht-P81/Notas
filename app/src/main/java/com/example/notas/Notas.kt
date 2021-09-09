package com.example.notas

import android.content.IntentSender

data class Notas (var id: Long=0,
                  var description:String = "",
                  var isFinished: Boolean = false) {
}