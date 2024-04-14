package com.example.restaurant

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurant.pedido.CuentaMesa
import com.example.restaurant.pedido.ItemMenu

class MainActivity : AppCompatActivity() {
    private lateinit var cuentaMesa: CuentaMesa

    private lateinit var editTextCatidadPastel: EditText
    private lateinit var editTextCantidadCazuela: EditText
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchAgregarPropina: Switch

    private lateinit var textViewSubtotalPastel: TextView
    private lateinit var textViewSubtotalCazuela: TextView
    private lateinit var textViewTotalSinPropina: TextView
    private lateinit var textViewPropina: TextView
    private lateinit var textViewTotalConPropina: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicialización de vistas
        editTextCatidadPastel = findViewById(R.id.editTextCantidadPastel)
        editTextCantidadCazuela = findViewById(R.id.editTextCantidadCazuela)
        switchAgregarPropina = findViewById(R.id.switchAgregarPropina)
        textViewSubtotalPastel = findViewById(R.id.textViewSubtotalPastel)
        textViewSubtotalCazuela = findViewById(R.id.textViewSubtotalCazuela)
        textViewTotalSinPropina = findViewById(R.id.textViewTotalSinPropina)
        textViewPropina = findViewById(R.id.textViewPropina)
        textViewTotalConPropina = findViewById(R.id.textViewTotalConPropina)


        // Crear la cuenta de la mesa
        cuentaMesa = CuentaMesa(1)

        // Definir los items del menú
        val pastelChoclo = ItemMenu("Pastel de Choclo", 12000)
        val cazuela = ItemMenu("Cazuela", 10000)

        // Manejar cambios en la cantidad de Pastel de Choclo
        editTextCatidadPastel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(pastelChoclo, cantidad)
                actualizarTotales()
            }
        })

        // Manejar cambios en la cantidad de Cazuela
        editTextCantidadCazuela.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val cantidad = s.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(cazuela, cantidad)
                actualizarTotales()
            }
        })

        // Manejar cambios en la opción de propina
        switchAgregarPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarTotales()
        }
    }

    private fun actualizarTotales() {
        // Actualizar subtotales
        val subtotalPastel = cuentaMesa.items.firstOrNull { it.itemMenu.nombre == "Pastel de Choclo" }?.calcularSubtotal() ?: 0
        val subtotalCazuela = cuentaMesa.items.firstOrNull { it.itemMenu.nombre == "Cazuela" }?.calcularSubtotal() ?: 0
        textViewSubtotalPastel.text = formatoMoneda(subtotalPastel)
        textViewSubtotalCazuela.text = formatoMoneda(subtotalCazuela)

        // Actualizar total sin propina
        val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
        textViewTotalSinPropina.text = formatoMoneda(totalSinPropina)

        // Actualizar propina
        val propina = cuentaMesa.calcularPropina()
        textViewPropina.text = formatoMoneda(propina)

        //Actualizar total con propina si el switch esta activado, de lo contrario, mostrar el total sin propina
        val totalConPropina = if (switchAgregarPropina.isChecked) {
            cuentaMesa.calcularTotalConPropina()
        } else {
            totalSinPropina
        }
        textViewTotalConPropina.text = formatoMoneda(totalConPropina)

    }

    private fun formatoMoneda(valor: Int): String {
        val formato = java.text.NumberFormat.getCurrencyInstance(java.util.Locale("es", "CL"))
        return formato.format(valor)
    }
}