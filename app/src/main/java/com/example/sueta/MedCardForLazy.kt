package com.example.sueta


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun MedCardForLazy(cardName : String, onClick: () -> Unit) {

    Card(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() }) {
        Row(Modifier.fillMaxWidth()) {
            Text(modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 25.dp) ,text = cardName)
            IconButton(modifier = Modifier, onClick = {} ) {
                Icon(Icons.Outlined.FavoriteBorder,contentDescription = null)
            }
        }
    }
}