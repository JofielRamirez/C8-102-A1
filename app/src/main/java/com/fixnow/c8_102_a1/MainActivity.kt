package com.fixnow.c8_102_a1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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

fun getAgeGroup(age: Int): String {
    return when {
        age in 0..12 -> "Child"
        age in 13..17 -> "Teenager"
        age in 18..59 -> "Adult"
        else -> "Senior"
    }
}

//Challenge 3
fun addFriend(friends: MutableList<String>, newFriend: String) {
    if (friends.contains(newFriend)) {
        println("$newFriend is already in the list of friends.")
    }
    else {
        friends.add(newFriend)
    }
}

//Challenge 4
fun removeFriend(friends: MutableList<String>, friendToRemove: String) {
        friends.remove(friendToRemove)
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
    // Create a new variable ageGroup, and assign it by calling the getAgeGroup function.
    val ageGroup = getAgeGroup(age)



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
            onChangeUsername = { username = "@newUser123"},
            ageGroup,
            friends = friends,
            onAddFriend = { addFriend(friends = friends, newFriend = "Jose")},
            onRemoveFriend = { removeFriend(friends = friends, friendToRemove = "Jose")}
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
                   onChangeUsername: () -> Unit,
                   ageGroup: String,
                   friends: List<String>,
                   onAddFriend: () -> Unit,
                   onRemoveFriend: () -> Unit,) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "User Profile",
            modifier = Modifier.padding(30.dp)
        )
        Text(
            text = "Name: $name",
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "Age: $age",
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "Birthday: $birthday",
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "Address: $address",
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "Username: $username",
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "Username: $username",
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "Likes: $likesCount",
            modifier = Modifier.padding(5.dp)
        )
        Text(
            text = "Age Group: $ageGroup",
            modifier = Modifier.padding(5.dp)
        )

        Text(text = "Verified: ${if (isVerified) "Yes" else "No"}",
                modifier = Modifier.padding(5.dp))

        Text(text = "Friends (${friends.size}): ",
                modifier = Modifier.padding(5.dp))

        friends.forEach {
            Text(
                it,
                modifier = Modifier.padding(5.dp)
            )
        }
        //CHALLENGE 1 SESSION 2
        Row() {
            Button(onChangeUsername) {
                Text(
                    "Change Username",
                    modifier = Modifier.padding(2.dp)
                )
            }

            Button(onLike) {
                Text(
                    "Like",
                    modifier = Modifier.padding(2.dp)
                )
            }

        Row(){
            Button(onClick = onRemoveFriend) {
                Text(
                    "Remove Friend",
                    modifier = Modifier.padding(2.dp)
                )
            }
            Button(onClick = onAddFriend) {
                Text(
                    "Add Friend",
                    modifier = Modifier.padding(2.dp)
            }
        }




        }




    }
}

@Preview(showBackground = true)
    @Composable
    fun userProfileScreenPreview() {
        userProfileScreen()
    }
