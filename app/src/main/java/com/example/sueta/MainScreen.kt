package com.example.sueta

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sueta.navigation.Navig
import com.example.sueta.navigation.Screen

@Composable
fun MainScreen(viewModel: MainVM, navHostController: NavHostController) {

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp).padding(top = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Icon(
                Icons.Filled.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
            )
            Text(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                text = "пу-пу-пу"
            )
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically).size(32.dp),
                onClick = { navHostController.navigate(Screen.CreateScreen.route) }
            ) {
                Icon(Icons.Filled.Build, contentDescription = null)
            }
        }

        LazycolumnSueta(viewModel, navHostController)
    }
}
