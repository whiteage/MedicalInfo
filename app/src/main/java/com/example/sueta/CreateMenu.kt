package com.example.sueta

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.sueta.navigation.Screen


@Composable
fun CreateMenu (viewModel: MainVM,navHostController : NavHostController, cardId : String ) {



    val card = remember { mutableStateOf(CreateMenuData("", "", "", "", "", "", "", "", null)) }
    val cardInfo = viewModel.cardInfo.observeAsState(CreateMenuData("", "", "", "", "", "", "", "", null))


    LaunchedEffect(cardId) {

        if (cardId.isNotEmpty()) {
            card.value = viewModel.getCardByName(cardId)!!
            viewModel.sendNotification()

        }

    }


    Box(modifier = Modifier.padding(start = 15.dp, top = 20.dp).fillMaxWidth()){
        IconButton(onClick = { viewModel.clearCardInfo()
            navHostController.navigate(Screen.MainScreen.route)}, modifier = Modifier.align(Alignment.BottomStart)) {Icon(Icons.Filled.ArrowBack, contentDescription = null)}
        IconButton(onClick = { viewModel.saveAndCreateQr(card.value) }, modifier = Modifier.align(Alignment.BottomEnd).padding(end = 15.dp)){ Icon(Icons.Filled.Check, contentDescription = null)}
    }
    Column() {

        Image(
            painter = cardInfo.value.qrCode?.let { BitmapPainter(it.asImageBitmap()) } ?: run { ColorPainter(Color.LightGray) } ,
            contentDescription = "qr",
            contentScale = ContentScale.Fit,
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 50.dp).size(150.dp).clip(RoundedCornerShape(25.dp))
        )

        Box(modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(25.dp)).align(alignment = Alignment.CenterHorizontally)) {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp), horizontalAlignment = Alignment.Start) {
                item {OutlinedTextField(modifier = Modifier.fillMaxWidth() ,value = card.value.cardName, onValueChange = { card.value = card.value.copy(cardName = it)}, label = { Text(text = "Название:")}) }
                item {OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(top = 8.dp) ,value = card.value.userName, onValueChange = { card.value = card.value.copy(userName = it)}, label = { Text(text = "ФИО:")}) }
                item {OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(top = 8.dp) ,value = card.value.blood, onValueChange = { card.value = card.value.copy(blood = it)}, label = { Text(text = "Группа крови:")})  }
                item {OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(top = 8.dp) ,value = card.value.allergy, onValueChange = { card.value = card.value.copy(allergy = it)}, label = { Text(text = "Аллергии:")})  }
                item {OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(top = 8.dp) ,value = card.value.diagnoses, onValueChange = {card.value = card.value.copy(diagnoses = it) }, label = { Text(text = "Диагнозы:")})  }
                item {OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(top = 8.dp) ,value = card.value.medicines, onValueChange = {card.value = card.value.copy(medicines = it) }, label = { Text(text = "Лекарства:")})  }
                item {OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(top = 8.dp) ,value = card.value.contacts, onValueChange = { card.value = card.value.copy(contacts = it)}, label = { Text(text = "Контакты:")})  }
                item {OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(top = 8.dp) ,value = card.value.ps, onValueChange = {card.value = card.value.copy(ps = it) }, label = { Text(text = "Примечание:")})  }
            }
        }
    }



}