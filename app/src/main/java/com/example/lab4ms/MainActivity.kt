package com.example.lab4ms

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.TextFieldValue
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp


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
        val recipeList = remember { mutableStateListOf<String>() }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Button(
                onClick = {
                    val name = inputName.value.text
                    val imageUrl = inputImageURL.value.text

                    if (name.isNotBlank() && imageUrl.isNotBlank()) {
                        recipeList.add(name)
                        inputName.value = TextFieldValue("")
                        inputImageURL.value = TextFieldValue("")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text("Agregar nueva receta")
            }
        }




    }

    @Preview(showBackground = true)
    @Composable
    fun RecipeCard(){
    }
}