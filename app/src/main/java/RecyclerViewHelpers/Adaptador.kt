package RecyclerViewHelpers

import Modelo.ClaseConexion
import Modelo.listaTickets
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jennifer.teos.actividadbryan2.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Adaptador(private var Datos: List<listaTickets>): RecyclerView.Adapter<ViewHolder>() {


    fun actualizarRecyclerView (nuevaLista: List<listaTickets>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun eliminarRegistro(title: String, posicion: Int ){
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){


            val objConexion = ClaseConexion().cadenaConexion()

            val deleteTicket = objConexion?.prepareStatement("delete tickets where title = ?")!!
            deleteTicket.setString(1,title)
            deleteTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()
        }

        Datos = listaDatos.toList()

        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return  ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ticket = Datos[position]
        holder.textView.text = ticket.title
    }


}