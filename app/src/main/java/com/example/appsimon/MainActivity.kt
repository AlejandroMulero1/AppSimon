package com.example.appsimon

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextClock
import android.widget.TextView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import java.lang.reflect.Array
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), PulsarBoton {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Metodo que ajusta los valores de las variables para que cumplan lo necesitado en funcion al
     * estado actual de la partida
     * @param estado=variable String que almacena el estado de la partida
     */
    fun ajustarVariables(estado: String){
        val btnIniciar=findViewById<Button>(R.id.btnJugar)
        val txtEstado=findViewById<TextView>(R.id.txtEstadoPartida)
        val btnAzul=findViewById<Button>(R.id.btnFragmentoAzul)
        val btnAmarillo=findViewById<Button>(R.id.btnFragmentoAmarillo)
        val btnVerde=findViewById<Button>(R.id.btnFragmentoVerde)
        val btnRojo=findViewById<Button>(R.id.btnFragmentoRojo)

        //Al inicio de la partida
        if (estado=="inicio") {
            btnIniciar.visibility = View.GONE
            txtEstado.visibility = View.VISIBLE
            txtEstado.setText("Observa")
            btnAzul.isClickable = false;
            btnRojo.isClickable = false;
            btnAmarillo.isClickable = false;
            btnVerde.isClickable = false;

        }
        //Cuando empieza el turno del jugador
        else if (estado=="click") {
            btnAzul.isClickable = true;
            btnRojo.isClickable = true;
            btnAmarillo.isClickable = true;
            btnVerde.isClickable = true;
            txtEstado.setText("Juega")
        }
        //Al terminar la partida
        else{
            btnIniciar.visibility = View.VISIBLE
            txtEstado.visibility = View.GONE
        }
    }

    var secuencia=ArrayList<String>()
    var turno=1

    /**
     * Metodo que empieza la partida y la gestiona hasta que el usuario falle, contiene las
     * llamadas a los otros metodos que hacen funcionar a la partida
     */
    override fun empezarPartida(){
        var valido=true
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                while (valido) {
                    generarSecuencia(secuencia)
                    delay(TimeUnit.SECONDS.toMillis(2))
                    ajustarVariables("inicio")
                    // mostrarSecuencia()
                    delay(TimeUnit.SECONDS.toMillis(2))
                    ajustarVariables("click")
                    //while (valido){
                    //     valido=recogerEleccionJugador()
                    //                  }
                    delay(TimeUnit.SECONDS.toMillis(2))
                    ajustarVariables("final")
                    turno++
                    if (turno==5)
                    {
                        valido=false
                        secuencia.clear()
                        turno=0
                    }
                }
            }
        }
    }

    fun mostrarSecuencia(secuencia: ArrayList<String>){
        val btnAzul=findViewById<Button>(R.id.btnFragmentoAzul)
        val btnAmarillo=findViewById<Button>(R.id.btnFragmentoAmarillo)
        val btnVerde=findViewById<Button>(R.id.btnFragmentoVerde)
        val btnRojo=findViewById<Button>(R.id.btnFragmentoRojo)
        for (color in secuencia){
            when(color){
                "Rojo" -> encenderBoton(btnRojo, color)
                "Verde" -> encenderBoton(btnVerde, color)
                "Azul" -> encenderBoton(btnAzul, color)
                "Amarillo" -> encenderBoton(btnAmarillo, color)
            }
        }
    }

    fun encenderBoton(boton: Button, color: String){
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                if (color=="Rojo"){
                    delay(TimeUnit.SECONDS.toMillis(2))
                    boton.setBackgroundColor(Color.RED)
                } else if (color=="Verde"){
                    delay(TimeUnit.SECONDS.toMillis(2))
                    boton.setBackgroundColor(Color.GREEN)
                } else if (color=="Azul"){
                    delay(TimeUnit.SECONDS.toMillis(2))
                    boton.setBackgroundColor(Color.BLUE)
                }else if(color=="Amarillo"){
                    delay(TimeUnit.SECONDS.toMillis(2))
                    boton.setBackgroundColor(Color.YELLOW)                 }

            }
        }
    }

    /**
     *Metodo que añade un color mas a la secuencia pasada por parametro para que se le añada un color mas
     * @param secuencia=parametro que contiene la secuencia de colores integra
     */
    fun  generarSecuencia(secuencia:ArrayList<String>){
        val colores= arrayListOf<String>("Rojo", "Verde", "Azul", "Amarillo")
        secuencia.add(colores.random())
    }
    }


