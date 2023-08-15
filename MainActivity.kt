//Universidad del Valle de Guatemala
//Programación de Plataformas Móviles
//Laboratorio 4
//Sección 20
//Mónica Salvatierra -22249

package com.example.lab4ms

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.text.font.FontFamily


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme{
                MainScreen()
            }

        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(){

        val recipeList = remember { mutableStateListOf<Recipe>() } //Lista que contiene las recetas
        val inputName = remember { mutableStateOf(TextFieldValue()) } // input para el nombre de la receta
        val inputImageURL= remember { mutableStateOf(TextFieldValue()) } // input para el url
        val showError = remember { mutableStateOf(false) } // Validación para mostrar error
        val urlError= remember {mutableStateOf(false)}

        Column(
            modifier= Modifier
                .padding(16.dp)
                .fillMaxSize()
                .background(color = Color(android.graphics.Color.parseColor("#463C52")))
        ){
            Text( //Añade un título
                text = "     Recetario  ",
                style = TextStyle(
                    fontSize = 52.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily= FontFamily.Serif,
                    color=Color.White

                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 16.dp)

            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField( //Espacio para colocar el nombre de la receta
                value = inputName.value,
                onValueChange = { inputName.value = it },
                label = { Text("Nombre de la receta ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),

            )
            TextField( //Espacio para colocar la URL de la imagen
                value = inputImageURL.value,
                onValueChange = { inputImageURL.value = it },
                label = { Text("URL de la imagen ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            if (showError.value) { // showError cuando se duplique el nombre de la receta o el usuario no haya puesto nada.
                Text(
                    text = "Error: Nombre de receta duplicado o campos vacíos.",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            if (urlError.value) { // showError cuando se duplique el nombre de la receta o el usuario no haya puesto nada.
                Text(
                    text = "Error: Formato de URL incorrecto.",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
            }

            Button( //Botón para agregar receta
                onClick = {
                    val name = inputName.value.text
                    val imageUrl = inputImageURL.value.text

                    if (name.isNotBlank() && imageUrl.isNotBlank() && !recipeList.any { it.name == name }) { //Validación para verificar que no se duplique el nombre ni hayan campos vacíos.
                        val uri = try { //Validación para que en el campo de URL solo se puedanponer URLS
                            Uri.parse(imageUrl)
                        } catch (e: Exception) { //Tira un error de tipo Exception indicando que no es válido
                            null
                        }
                        if (uri != null && "https" == uri.scheme) { //Si el campo de url no es null y tiene el formato http
                            recipeList.add(Recipe(name, imageUrl)) //Se agrega el nombre de la receta y la imagen a la lista de recetas
                            urlError.value=false //Si la validación es correcta, se e añade el nombre y el url a la lista de las recetas
                            showError.value=false // Si la validación es correcta, se añade el nombre y el url a la lista de las recetas
                        }else{
                            urlError.value=true
                        }
                    }else{
                        showError.value=true // De lo contrario mostrará error en pantalla
                        urlError.value=true //De lo contrario mostrará error en pantalla
                    }
                    inputName.value = TextFieldValue("")
                    inputImageURL.value = TextFieldValue("")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                content={
                    Text("Agregar nueva receta")
                }
            )

            LazyColumn {
                items(recipeList.toMutableList() ){ recipe ->
                    RecipeCard(recipe){
                        recipeList.remove(recipe) //Formato para mostrar las recetas de manera vertical
                    }
                }
            }
        }
    }

    data class Recipe(val name: String, val imageUrl: String)

    @Composable
    fun RecipeCard(recipe: Recipe, onDelete: () -> Unit) { //Card para mostrar las recetas individualmente
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    ){
                val imagePainter= rememberAsyncImagePainter(recipe.imageUrl)
                Image(
                    painter= imagePainter,
                    contentDescription="Recipe Image",
                    modifier= Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(bottom = 8.dp),
                        contentScale= ContentScale.Crop
                )

                Text(
                    text = recipe.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color= Color(android.graphics.Color.parseColor("#1C2135"))
                    )
                )

                Button(
                    onClick = onDelete,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    content = {
                        Text("Eliminar receta")
                    }
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview(){
        MainScreen()
    }
}