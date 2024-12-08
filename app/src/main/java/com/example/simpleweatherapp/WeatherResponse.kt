package com.example.simpleweatherapp

// Data class to parse the API's JSON response
data class WeatherResponse(
    val main: Main,
    val name: String
)

data class Main(
    val temp: Double
)