package jennifer.teos.actividadbryan2

import Modelo.ClaseConexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import android.util.Log

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtCrearUsuario = findViewById<EditText>(R.id.txtCrearUsuario)
        val txtCrearContrasena = findViewById<EditText>(R.id.txtCrearContrasena)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val btnIrLogin = findViewById<Button>(R.id.btnIrLogin)

        btnCrearCuenta.setOnClickListener {
            val nombreUsuario = txtCrearUsuario.text.toString()
            val contrasena = txtCrearContrasena.text.toString()

            if (nombreUsuario.isBlank() || contrasena.isBlank()) {
                Toast.makeText(this@Register, "Nombre de usuario y contraseña no pueden estar vacíos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val objConexion = ClaseConexion().cadenaConexion()

                    if (objConexion != null) {
                        val crearUsuario = objConexion.prepareStatement("INSERT INTO usuarios (UUID_Usuario, nombreUsuario, clave) VALUES (?, ?, ?)")
                        crearUsuario.setString(1, UUID.randomUUID().toString())
                        crearUsuario.setString(2, nombreUsuario)
                        crearUsuario.setString(3, contrasena)
                        crearUsuario.executeUpdate()

                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Register, "Usuario Creado", Toast.LENGTH_SHORT).show()
                            txtCrearUsuario.setText("")
                            txtCrearContrasena.setText("")
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@Register, "Error en la conexión a la base de datos", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Register, "Error al crear usuario: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                    Log.e("Register", "Error al crear usuario", e)
                }
            }
        }

        btnIrLogin.setOnClickListener {
            val pantallaLogin = Intent(this, Login::class.java)
            startActivity(pantallaLogin)
        }
    }
}