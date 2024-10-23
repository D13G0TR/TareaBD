package com.example.ejemplobasededatos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import com.example.ejemplobasededatos.data.NombreDataBase
import com.example.ejemplobasededatos.data.NombreEntity
import com.example.ejemplobasededatos.data.NombreViewModel
import com.example.ejemplobasededatos.ui.theme.EjemploBaseDeDatosTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel : NombreViewModel by viewModels()
            setContent {
                EjemploBaseDeDatosTheme {
                    Column {
                        AgregarNombre(viewModel = viewModel)
                        List(viewModel = viewModel)
                    }

                }
            }
    }
}


@Composable
fun List(viewModel: NombreViewModel) {
    val nombres by viewModel.getUltimosNombres().observeAsState(emptyList())
    val selectedName by viewModel.selectedName.observeAsState("")

    ListContent(
        nombres = nombres,
        selectedName = selectedName,
        onRandomize = { viewModel.seleccionarNombreAleatorio() }
    )
}


@Composable
fun ListContent(
    nombres: List<NombreEntity>,
    selectedName: String,
    onRandomize: () -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Prueba de base de datos")
        nombres.forEach {
            Text(it.name)
        }

        if (nombres.isNotEmpty()) {
            Button(onClick = onRandomize) {
                Text("Seleccionar nombre al azar")
            }
        }

        if (selectedName.isNotEmpty()) {
            Text("El nombre seleccionado es: $selectedName")
        }
    }
}


@Composable
fun AgregarNombre(viewModel: NombreViewModel){
    var texto by remember { mutableStateOf("") }
    Column {
        TextField(value = texto, onValueChange = {texto = it}, label={Text("Ingrese nombre :")} )
        Button(onClick = {
            viewModel.insertName(NombreEntity(name = texto))
            texto = ""
        }) {
            Text("Agregar nombre")
        }
    }
}