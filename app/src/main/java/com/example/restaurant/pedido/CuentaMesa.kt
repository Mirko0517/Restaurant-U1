package com.example.restaurant.pedido

class CuentaMesa(val mesa: Int) {
    val items = mutableListOf<ItemMesa>()
    var aceptaPropina: Boolean = true

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = ItemMesa(itemMenu, cantidad)
        agregarItem(itemMesa)
    }

    fun agregarItem(itemMesa: ItemMesa) {
        items.add(itemMesa)
    }

    fun calcularTotalSinPropina(): Int {
        return items.sumOf { it.calcularSubtotal() }
    }

    fun calcularPropina(): Int {
        return (calcularTotalSinPropina() * 0.1).toInt()
    }

    fun calcularTotalConPropina(): Int {
        val totalSinPropina = calcularTotalSinPropina()
        val propina = if (aceptaPropina) calcularPropina() else 0
        return totalSinPropina + propina
    }
}