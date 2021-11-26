package com.rawnak.weatherappmvvm.features.weather_info_show.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.rawnak.weatherappmvvm.common.RequestCompleteListener
import com.rawnak.weatherappmvvm.features.weather_info_show.model.data_class.City
import com.rawnak.weatherappmvvm.features.weather_info_show.model.data_class.WeatherInfoResponse
import com.rawnak.weatherappmvvm.network.ApiInterface
import com.rawnak.weatherappmvvm.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WeatherInfoShowModelImpl(private val context: Context) : WeatherInfoShowModel {

    override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
//            val stream = context.assets.open("city_list.json")
//
//            val size =
//                stream.available()   // it returns the number of bytes in corresponding json file
//            val buffer = ByteArray(size)
//            stream.read(buffer)     // it reads the whole json file and sets it to the byte array
//            stream.close()
//            val tContents = String(buffer)      // converting byte array to a string
            // or doing the above thing with one statement
            val tContents = context.assets.open("city_list.json").bufferedReader()
                .use { it.readText() }
            println("tContents ${tContents.replace("\n", "")}")
            Log.d("tContents", tContents)

            // groupListType will hold the Type of the json file
//            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val groupListType = object : TypeToken<MutableList<City>>() {}.type
//            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = Gson().fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList) //let presenter know the city list

            //////////////////////////////////////////////////////////////////////////////

            // test of Gson

            //-----------------------------------------------------
            // object to json
//            val student = Student("Alex", "Rome")
//            val jsonString = Gson().toJson(student)
//            println("student $jsonString")
            //-----------------------------------------------------

            //-----------------------------------------------------
            // json to object
            //  raw string
//            val json1 = """{
//                |"key_name": "Mark",
//                |"key_address": {
//                |               "key_city": "London",
//                |               "post": "1000"
//                |               }
//                |}""".trimMargin()
//
////            val jsonObjectType = object : TypeToken<Student>() {}.type
//            val student: Student = Gson().fromJson(json1, Student::class.java)
//            println("student ${student.name} ${student.address} ${student.address?.city}")
            //-----------------------------------------------------

            //////////////////////////////////////////////////////////////////////////////

        } catch (e: IOException) {
            e.printStackTrace()
            callback.onRequestFailed(e.localizedMessage!!) //let presenter know about failure
        }
    }

    override fun getWeatherInfo(
        cityId: Int,
        callback: RequestCompleteListener<WeatherInfoResponse>
    ) {

        // retrofit is giving the implementation of the interface
        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        // interface implementation's method is returning a response object
        val call: Call<WeatherInfoResponse> = apiInterface.callApiForWeatherInfo(cityId)

        call.enqueue(object : Callback<WeatherInfoResponse> {

            // if retrofit network call is successful, this method will be triggered
            override fun onResponse(
                call: Call<WeatherInfoResponse>,
                response: Response<WeatherInfoResponse>
            ) {
                if (response.body() != null)
                    callback.onRequestSuccess(response.body()!!) //let presenter know the weather information data
                else
                    callback.onRequestFailed(response.message()) //let presenter know about failure
            }

            // this method will be triggered if network call failed
            override fun onFailure(call: Call<WeatherInfoResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!) //let presenter know about failure
            }
        })
    }
}

//data class Student(
//    @SerializedName("key_name")
//    var name: String? = null,
//    @SerializedName("key_address")
//    var address: Address? = null
//)
//
//data class Address(
//    @SerializedName("key_city")
//    var city: String? = null,
//    var post: String? = null
//)