package com.example.simpleweatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {
    val weatherData: MutableLiveData<String> = MutableLiveData()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(WeatherApiService::class.java)

    fun fetchWeather(city: String) {
        val call = api.getWeather(city, "74a34691623587401b7c655e751a0a0c")
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    val temperature = weatherResponse?.main?.temp
                    val cityName = weatherResponse?.name
                    weatherData.postValue("Weather in $cityName: $temperature°C")
                } else {
                    weatherData.postValue("Failed to fetch weather: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                weatherData.postValue("Error: ${t.message}")
            }
        })
    }
}