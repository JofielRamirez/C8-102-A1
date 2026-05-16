package com.fixnow.c8_102_a1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.fixnow.c8_102_a1.ui.theme.User


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserProfileScreen()
        }
    }
}

fun addFriend(friends: MutableList<String>, newFriend: String) {
    if (friends.contains(newFriend)) {
        println("$newFriend is already in the list of friends.")
    } else {
        friends.add(newFriend)
    }
}

fun removeFriend(friends: MutableList<String>, friendToRemove: String) {
    friends.remove(friendToRemove)
}

@Composable
fun UserProfileScreen() {
    var user1 by remember {
        mutableStateOf(
            User(
                fullName = "John Lennon",
                age = 85,
                birthday = "1940-10-09",
                address = "Liverpool, UK",
                userName = "@jlennon",
                isVerified = true,
                likesCount = 0
            )
        )
    }
// Challenge 3
    val friends = remember {
        mutableStateListOf("Paul", "George", "Ringo", "Elvis", "Jim")
    }
    // Challenge 4
    var newUsername by remember { mutableStateOf(user1.userName)}
    var newAge by remember { mutableStateOf(user1.age.toString())}
    var newFriendName by remember { mutableStateOf("")}

    val backgroundColor = when(user1.getAgeGroup(user1.age)){
        "Child" -> Color(0xFFD2D2D2)
        "Teenager" -> Color(0xFFCCFFCE)
        "Adult" -> Color(0xFFFFF3C0)
        "Senior" -> Color(0xFFFFDCB3)
        else -> Color.White
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        ProfileContent(
            // Variables
            user = user1,
            friends = friends,
            newUsername = newUsername,
            newAge = newAge,
            newFriendName = newFriendName,
            // Actions
            onUsernameChange = { newUsername = it},
            onAgeChange = { newAge = it },
            onFriendNameChange = { newFriendName = it},
            onLike = {
                user1 = user1.copy(likesCount = user1.likesCount + 1)
            },
            onUpdateProfile = {
                val ageInt = newAge.toIntOrNull()
                if (ageInt != null) {
                    user1 = user1.updateProfile(newUsername, ageInt)
                }
            },
            onAddFriend = {
                user1.addFriend(friends, newFriendName)
                newFriendName = ""
            },
            onRemoveFriend = { friendName ->
                user1.removeFriend(friends, friendName)
            },
            onResetLikes = {
                user1 = user1.copy(likesCount = 0)
            }
        )

    }
}

@Composable
fun ProfileContent(
    user: User,
    friends: List<String>,
    newUsername: String,
    newAge: String,
    newFriendName: String,
    onUsernameChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onFriendNameChange: (String) -> Unit,
    onLike: () -> Unit,
    onUpdateProfile: () -> Unit,
    onAddFriend: () -> Unit,
    onRemoveFriend: (String) -> Unit,
    onResetLikes: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
        .padding(16.dp)
    ) {
        UserProfileCard(user)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = newUsername,
            onValueChange = onUsernameChange,
            label = { Text("Edit username") },
            modifier = Modifier.fillMaxWidth()
        )

        // CHALLENGE 6
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = newAge,
            onValueChange = onAgeChange,
            label = { Text("Edit age") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onUpdateProfile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Profile")
        }


        //CHALLENGE 7
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = newFriendName,
            onValueChange = onFriendNameChange,
            label = { Text("Enter friend's name") },
            modifier = Modifier.fillMaxWidth()
        )


        //CHALLENGE 8
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onAddFriend,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Friend")
        }

        Spacer(modifier = Modifier.height(12.dp))
        FriendList(
            friends = friends,
            onRemoveFriend = onRemoveFriend
        )

        //CHALLENGE 9
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onResetLikes,
                modifier = Modifier.weight(1f)
            ) {
                Text("Reset Likes")
            }
            Button(
                onClick = onLike,
                modifier = Modifier.weight(1f)
            ) {
                Text("Like")
            }
        }


        // Text(text = "User Profile", modifier = Modifier.padding(30.dp))

        //CHALLENGE 5
        /*
        Text(text = "Age: ${user1.age}", modifier = Modifier.padding(5.dp))
        Text(text = "Birthday: ${user1.birthday}", modifier = Modifier.padding(5.dp))
        Text(text = "Address: ${user1.address}", modifier = Modifier.padding(5.dp))
        Text(text = "Verified: ${if (user1.isVerified) "Yes" else "No"}", modifier = Modifier.padding(5.dp))
        Text(text = "Likes: $likesCount", modifier = Modifier.padding(5.dp))
        Text(text = "Age Group: ${user1.getAgeGroup(user1.age)}", modifier = Modifier.padding(5.dp))


        Text(text = "Friends (${friends.size}):", modifier = Modifier.padding(5.dp))
        friends.forEach {
            Text(it, modifier = Modifier.padding(5.dp))
        }
*/


    }



    }



@Composable
fun UserProfileCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${user.fullName}")
            Text(text = "${user.userName}")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Age: ${user.age}")
            Text(text = "Birthday: ${user.birthday}")
            Text(text = "Address: ${user.address}")
            Text(text = "Verified: ${if (user.isVerified) "Yes" else "No"}")
            Text(text = "Likes: ${user.likesCount}")
            Text(text = "Age Group: ${user.getAgeGroup(user.age)}")
        }
    }
}

@Composable
fun FriendItem(friendName: String, onRemove: () -> Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Text(
                text = friendName,
                modifier = Modifier.weight(1f)
            )
            Button(onClick = onRemove){
                Text("Remove")
            }
        }
    }
}

@Composable
fun FriendList(
    friends: List<String>,
    onRemoveFriend: (String) -> Unit
){
    Text(
        text = "Friends (${friends.size})",
        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
    )

    if(friends.isEmpty()) {
        Text("No friends added yet.")
    } else{
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ){
            items(friends) { friend -> FriendItem(
                friendName = friend,
                onRemove = { onRemoveFriend(friend)}
            )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun userProfileScreenPreview() {
    UserProfileScreen()
}