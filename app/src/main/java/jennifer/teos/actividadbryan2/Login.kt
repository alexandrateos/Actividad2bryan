package jennifer.teos.actividadbryan2

import Modelo.ClaseConexion
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.actividad_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val txtUsuarioLogin = findViewById<EditText>(R.id.txtUsuarioLogin)
        val txtContrasenaLogin = findViewById<EditText>(R.id.txtContrasenaLogin)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)
        val btnRegistrate = findViewById<Button>(R.id.btnRegistrate)

        btnEntrar.setOnClickListener{

            val pantallaDashboard = Intent(this, Dashboard::class.java)
            val pantallaDashboardAdmin = Intent(this, DashboardAdmin::class.java) // Nueva pantalla para el administrador

            GlobalScope.launch(Dispatchers.IO){

                val objConexion = ClaseConexion().cadenaConexion()

                val comprobarUsuario = objConexion?.prepareStatement("select * from usuarios WHERE nombreUsuario = ? AND contrasena = ?")!!
                comprobarUsuario.setString(1, txtUsuarioLogin.text.toString())
                comprobarUsuario.setString(2, txtContrasenaLogin.text.toString())

                val resultado = comprobarUsuario.executeQuery()

                if (resultado.next()) {
                    startActivity(pantallaDashboard)
                }
                else {
                    println("Usuario no encontrado :(((, verifica tus credenciales nuevamente")
                }

            }
        }

        btnRegistrate.setOnClickListener{
            //cambiar a pantalla Register
            val pantallaRegistrarme = Intent(this, Register::class.java)
            startActivity(pantallaRegistrarme)
        }


    }
}