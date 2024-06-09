package Modelo

data class listaTickets (

    val uuid: String,
    var numero: Int,
    var title: String,
    var descripcion: String,
    var author: String,
    var email: String,
    var creation_date: Int,
    var status: String,
    var completation_date: Int,

    )