package Modelo

data class listaTickets (

    val uuid: String,
    val numero: Int,
    val title: String,
    val descripcion: String,
    val author: String,
    val email: String,
    val creation_date: Int,
    val status: String,
    val completation_date: Int,

    )