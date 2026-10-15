package com.example.miprimeraapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FormActivity : AppCompatActivity() {
    private val personas = mutableListOf<Persona>()
    private lateinit var etCodigo: EditText
    private lateinit var etNombre: EditText
    private lateinit var etEdad: EditText
    private lateinit var tablaDatos: TableLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        etCodigo = findViewById(R.id.etCodigo)
        etNombre = findViewById(R.id.etNombre)
        etEdad = findViewById(R.id.etEdad)
        tablaDatos = findViewById(R.id.tablaDatos)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        btnAgregar.setOnClickListener { agregar()}
        btnEliminar.setOnClickListener { eliminar() }
    }

    private fun agregar() {
        val codigoTexto = etCodigo.text.toString().trim()
        val nombre = etNombre.text.toString().trim()
        val edadTexto = etEdad.text.toString().trim()
        val codigo = codigoTexto.toIntOrNull()
        val edad = edadTexto.toIntOrNull()
        //validaciones
        if (codigo == null) {
            etCodigo.error = "Ingresa un codigo valido (solo numeros)"
            return
        }
        if (personas.any {it.codigo == codigo}) {
            etCodigo.error = "Ese codigo ya existe"
            return
        }
        if (nombre.isEmpty()){
            etNombre.error = "Ingresa un nombre"
            return
        }
        if (!nombre.all {it.isLetter() || it == ' '}) {
            etNombre.error = "El nombre solo puede tener letras"
            return
        }
        if (edad == null || edad <1 || edad > 120) {
            etEdad.error = "La edad debe estar entre 1 y 120"
            return
        }
        //guardar
        personas.add(Persona(codigo, nombre, edad))
        actualizarTabla()
        limpiarCampos()
        Toast.makeText(this, "Registro agregaddo", Toast.LENGTH_SHORT).show()
    }

    private fun eliminar(){
        val codigo = etCodigo.text.toString().trim().toIntOrNull()
        if (codigo == null) {
            etCodigo.error = "Escribe el código a eliminar"
            return
        }
        val persona = personas.find { it.codigo == codigo }
        if (persona == null) {
            Toast.makeText(this, "No existe ese código", Toast.LENGTH_SHORT).show()
            return
        }
        personas.remove(persona)
        actualizarTabla()
        limpiarCampos()
        Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show()
    }
    private fun actualizarTabla(){
        // Borra todas las filas menos el encabezado (posición 0)
        if (tablaDatos.childCount > 1) {
            tablaDatos.removeViews(1, tablaDatos.childCount - 1)
        }

        // Crea una fila por cada persona
        for (p in personas) {
            val fila = TableRow(this)
            fila.addView(crearCelda(p.codigo.toString()))
            fila.addView(crearCelda(p.nombre))
            fila.addView(crearCelda(p.edad.toString()))
            tablaDatos.addView(fila)
        }
    }
    private fun crearCelda(texto: String): TextView {
        val celda = TextView(this)
        celda.text = texto
        celda.setPadding(16, 8, 16, 8)
        return celda
    }
    private fun limpiarCampos(){
        etCodigo.text.clear()
        etNombre.text.clear()
        etEdad.text.clear()
        etCodigo.text.clear()
    }
}