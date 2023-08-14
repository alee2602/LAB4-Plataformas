package com.example.lab4ms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen(){
        val recipeList = remember { mutableStateListOf<Recipe>() }
        val inputName = remember { mutableStateOf(TextFieldValue()) }
        val inputImageURL= remember { mutableStateOf(TextFieldValue()) }

        Column(
            modifier= Modifier
                .padding(16.dp)
                .fillMaxSize()
        ){
            TextField(
                value = inputName.value,
                onValueChange = { inputName.value = it },
                label = { Text("Nombre de la receta ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            TextField(
                value = inputImageURL.value,
                onValueChange = { inputImageURL.value = it },
                label = { Text("URL de la imagen ") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done, // Puedes cambiarlo según tus necesidades
                    keyboardType = KeyboardType.Uri
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = {
                    val name = inputName.value.text
                    val imageUrl = inputImageURL.value.text

                    if (name.isNotBlank() && imageUrl.isNotBlank()) {
                        recipeList.add(Recipe(name, imageUrl))
                        inputName.value = TextFieldValue("")
                        inputImageURL.value = TextFieldValue("")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                content={
                    Text("Agregar nueva receta")
                }
            )

            LazyColumn {
                items(recipeList) { recipe ->
                    RecipeCard(recipe)
                }
            }
        }
    }

    @Composable
    fun RecipeCard(recipe: Recipe) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            elevation = CardElevation(4.sp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    ){

                Image(
                    painter= rememberAsyncImagePainter(recipe.imageUrl),
                    contentDescription="Recipe Image",
                    modifier= Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(bottom=8.dp),
                        contentScale= ContentScale.Crop
                )
                Text(
                    text = recipe.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

    data class Recipe(val name: String, val imageUrl: String)

    private fun CardElevation(defaultElevation: TextUnit): CardElevation {
        return CardElevation(defaultElevation)
    }


    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview(){
        MainScreen()

    }
}