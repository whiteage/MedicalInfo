package com.example.sueta

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import com.google.gson.Gson
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Base64
import java.util.Base64.Decoder

class MainVM(application: Application) : AndroidViewModel(application){

    private val db = DataBaseDriver(application)
    private val notification = Notification()


    private val _posts = MutableLiveData<List<CardData>>(listOf())
    val posts : LiveData<List<CardData>> = _posts

    private val _allcardsInfo = MutableLiveData<List<CreateMenuData>>(emptyList())
    val allcardsInfo : LiveData<List<CreateMenuData>> = _allcardsInfo

    private val _cardInfo = MutableLiveData<CreateMenuData>()
    val cardInfo : LiveData<CreateMenuData> = _cardInfo

    fun base64ToBitmap(base64Str: String): Bitmap? {
        val decoder = Base64.getDecoder()
        val decodedBytes = decoder.decode(base64Str)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }


    fun  Database.toCreateMenuData(): CreateMenuData  {
        val bitmap = qrCodeBase64?.let { base64ToBitmap(it) }
        return CreateMenuData(
            cardName,
            userName ?: "",
            blood ?: "",
            allergy ?: "",
            diagnoses ?: "",
            medicines ?: "",
            contacts ?: "",
            ps ?: "",
            bitmap
        )

    }

    fun sendNotification(){
        notification.createNotification(context = application)
        notification.showNotification(context =  application, title = "SOSAL?", message = "NET")
    }


    fun getCardByName(cardName: String): CreateMenuData? {
        _cardInfo.postValue(_allcardsInfo.value?.firstOrNull { it.cardName == cardName })
        return _allcardsInfo.value?.firstOrNull { it.cardName == cardName }
    }

    fun loadMenuItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val dbItems: List<Database> = db.database.databaseQueries.getFromDataBase().executeAsList()
            val uiItems: List<CreateMenuData> = dbItems.map { it.toCreateMenuData() }
            _allcardsInfo.postValue(uiItems)
        }
    }

    fun clearCardInfo(){
        _cardInfo.postValue(CreateMenuData("", "", "", "", "", "", "", "", null))
    }


     fun save(item : CreateMenuData) {
        _cardInfo.postValue(item)

         val curlist = _allcardsInfo.value
         val existe =  curlist.any {it.cardName == item.cardName}

         if(item.cardName == ""){
             item.cardName = "empty"
         }

         if(!existe){
            _allcardsInfo.postValue(_allcardsInfo.value.plus(item) ?: listOf(item))}

         else {
             val updatedList = curlist.map {
                 if (it.cardName == item.cardName) item else it
             }
             _allcardsInfo.postValue(updatedList)

         }
    }

    fun deleteItem(item: CreateMenuData){
        _allcardsInfo.value = _allcardsInfo.value.filter { it.cardName != item.cardName }
        db.database.databaseQueries.deleteFromDataBase(item.cardName)

    }


    fun createQr(content : String, size : Int = 512 ) : Bitmap{
        val bitMatrix = try {
            MultiFormatWriter().encode(
                content,
                BarcodeFormat.QR_CODE,
                size,
                size,
                null
            )
        } catch (e: Exception) {
            throw RuntimeException("Ошибка генерации QR-кода: ${e.message}")
        }

        val width = bitMatrix.width
        val height = bitMatrix.height
        val pixels = IntArray(width * height)

        for (y in 0 until height) {
            for (x in 0 until width) {
                pixels[y * width + x] = if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt()
            }
        }

        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888).apply {
            setPixels(pixels, 0, width, 0, 0, width, height)
        }
    }


    fun saveAndCreateQr(item : CreateMenuData){
        Log.d("sosal", "${item}")
        val qrContent = Gson().toJson(item)
        val qrBitmap = createQr(qrContent)
        item.qrCode = qrBitmap
        viewModelScope.launch { insertIntoOrReplaceDataBase(item) }
        save(item)
    }

    fun insertIntoOrReplaceDataBase(item: CreateMenuData) {
        val outputStream = ByteArrayOutputStream()
        item.qrCode!!.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()

        // Кодируем в Base64
        val base64String = Base64.getEncoder()
        val qeq =  base64String.encodeToString(byteArray)

        val queries = db.database.databaseQueries.insertOrReplaceIntoDatabase(
            item.cardName,
            item.userName,
            item.blood,
            item.allergy,
            item.diagnoses,
            item.medicines,
            item.contacts,
            item.ps,
            qeq
        )
    }



}