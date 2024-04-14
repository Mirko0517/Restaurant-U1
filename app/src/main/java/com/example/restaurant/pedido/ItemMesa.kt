package com.example.restaurant.pedido

class ItemMesa (
    val itemMenu: ItemMenu,
    val cantidad: Int
) {
    fun calcularSubtotal(): Int {
        return cantidad * itemMenu.precio
    }
}