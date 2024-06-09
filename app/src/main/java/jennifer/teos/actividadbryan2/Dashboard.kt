package jennifer.teos.actividadbryan2



import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import Modelo.listaTickets
import Modelo.ClaseConexion
import RecyclerViewHelpers.Adaptador
import androidx.core.view.WindowInsetsCompat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Dashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNumTicket = findViewById<EditText>(R.id.txtNumTicket)
        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescTicket = findViewById<EditText>(R.id.txtDescTicket)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtFechaCreacion = findViewById<EditText>(R.id.txtFechaCreacion)
        val txtEstatus = findViewById<EditText>(R.id.txtEstatus)
        val txtFechaFinalizacion = findViewById<EditText>(R.id.txtFechaFinalizacion)
        val btnCrearTicket = findViewById<Button>(R.id.btnCrearTicket)
        val rcvDatos = findViewById<RecyclerView>(R.id.rcvDatos)


        rcvDatos.layoutManager = LinearLayoutManager(this)

        fun obtenerTickets(): List<listaTickets>{

            val objConexion = ClaseConexion().cadenaConexion()
            
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("select * from tickets")!!

            val listadoTickets = mutableListOf<listaTickets>()

            while (resultSet.next()){
                val numero = resultSet.getInt("numero")
                val title = resultSet.getString("title")
                val descripcion = resultSet.getString("descripcion")
                val author = resultSet.getString("author")
                val email = resultSet.getString("email")
                val creation_date = resultSet.getInt("creation_date")
                val status = resultSet.getString("status")
                val completion_date = resultSet.getInt("completion_date")
                val tickets = listaTickets(numero, title, descripcion, author, email, creation_date, status, completion_date)
                listadoTickets.add(tickets)

            }
            return listadoTickets
        }

        CoroutineScope(Dispatchers.IO).launch {
            val ejecutarFuncion = obtenerTickets()


            withContext(Dispatchers.Main){
                val miAdaptador = Adaptador(ejecutarFuncion)
                rcvDatos.adapter = miAdaptador
            }
        }

        btnCrearTicket.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {

                val objConexion = ClaseConexion().cadenaConexion()
                val addTicket = objConexion?.prepareStatement("insert into tickets (numero, title, descripcion, author, email, creation_date, status, completion_date) values (?, ?, ?, ?, ?, ?, ?, ?)")!!

                addTicket.setInt(1, txtNumTicket.text.toString().toInt())
                addTicket.setString(2, txtTitulo.text.toString())
                addTicket.setString(3, txtDescTicket.text.toString())
                addTicket.setString(4, txtAutor.text.toString())
                addTicket.setString(5, txtEmail.text.toString())
                addTicket.setInt(6, txtFechaCreacion.text.toString().toInt())
                addTicket.setString(7, txtEstatus.text.toString())
                addTicket.setInt(8, txtFechaFinalizacion.text.toString().toInt())
                addTicket.executeUpdate()
            }
        }

    }






}