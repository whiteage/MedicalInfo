package com.example.sueta

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sueta.navigation.Navig
import com.example.sueta.navigation.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun LazycolumnSueta(viewModel: MainVM, navHostController: NavHostController  ) {


    LaunchedEffect(Unit){
        viewModel.loadMenuItems()
    }

    val cards = viewModel.allcardsInfo.observeAsState(emptyList())

    LazyColumn(modifier = Modifier.fillMaxHeight()) {
        itemsIndexed(
            items = cards.value,
            key = {_, item -> item.cardName}
        ) {_, item ->
            val swipeState = rememberSwipeToDismissBoxState(
                confirmValueChange = { swipeValue ->
                    if (swipeValue == SwipeToDismissBoxValue.EndToStart) {
                        viewModel.deleteItem(item)
                        true
                    } else {
                        false
                    }
                }
            )

            SwipeToDismiss(state = swipeState,
                directions = setOf(SwipeToDismissBoxValue.EndToStart),
                background = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterEnd){
                        Text(modifier = Modifier.align(Alignment.CenterEnd).padding(end = 20.dp) ,text = "Удалить")
                    }
                },
                dismissContent ={
                    MedCardForLazy(item.cardName, onClick = { navHostController.navigate( "creation/${item.cardName}") })
                })

        }

    }
}