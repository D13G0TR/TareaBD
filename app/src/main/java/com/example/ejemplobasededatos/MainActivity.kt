package com.example.ejemplobasededatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.ejemplobasededatos.data.NombreDataBase
import com.example.ejemplobasededatos.data.NombreEntity
import com.example.ejemplobasededatos.ui.theme.EjemploBaseDeDatosTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val db = NombreDataBase.getDatabase(applicationContext)
            val nombres = db.nombreDao().getUltimosNombres()

            val nombreEntity = NombreEntity(name = "Carlitos")
            db.nombreDao().insertNombre(nombreEntity)

            setContent {
                EjemploBaseDeDatosTheme {
                    List(nombres)
                }
            }
        }
    }
}


@Composable
fun List(nombres : List<NombreEntity>){
    Column{
        Spacer(modifier = Modifier.height(40.dp))
        Text("Prueba de base de datos ")
        nombres.forEach {
            Text(it.name)
        }
    }
}