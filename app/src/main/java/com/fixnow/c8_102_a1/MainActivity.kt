package com.fixnow.c8_102_a1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.fixnow.c8_102_a1.ui.theme.C8102A1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            userProfileScreen()
        }
    }
}

@Composable
fun userProfileScreen(){
    var name by remember { mutableStateOf("Jofiel") }
    var age by remember { mutableIntStateOf(21) }
    val birthday: String = "February 14, 2005"
    var address by remember { mutableStateOf("Callejon Reforma 90, Tecate") }
    var username by remember { mutableStateOf("@jofiel222") }
    val isVerified: Boolean = true
    var like by remember { mutableIntStateOf(0) }
    val friends = remember { mutableStateListOf<String>("Juan", "Pablo", "Marcos", "Sofia", "Andrea") }

    Surface{
        ProfileContent(
            name,
            age,
            birthday,
            address,
            username,
            isVerified,
            likesCount = like,
            //Challenge 4
            onLike = { like ++},
            onChangeUsername = { username = "@newUser123"}
        )
    }
}

@Composable
fun ProfileContent(name: String,
                   age: Int,
                   birthday: String,
                   address: String,
                   username: String,
                   isVerified: Boolean,
                   likesCount: Int,
                   onLike: () -> Unit,
                   onChangeUsername: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Name: $name\n"+
            "Age: $age\n"+
            "Birthday: $birthday\n"+
            "Address: $address\n"+
            "Likes: $likesCount\n"+
            "Username: $username\n",
            modifier = Modifier.padding(5.dp))
        Button(onChangeUsername) {
            Text("Change Username")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun userProfileScreenPreview() {
    userProfileScreen()
    }
