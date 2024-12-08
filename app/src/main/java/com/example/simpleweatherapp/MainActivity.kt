package com.example.simpleweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simpleweatherapp.ui.theme.SimpleWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleWeatherAppTheme {
                WeatherScreen()
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    var city by remember { mutableStateOf("") }
    val weatherInfo by viewModel.weatherData.observeAsState("Enter a city and press 'Get Weather'")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Text Input for City
        BasicTextField(
            value = city,
            onValueChange = { city = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            decorationBox = { innerTextField ->
                if (city.isEmpty()) {
                    Text(text = "Enter city name", style = MaterialTheme.typography.bodyLarge)
                }
                innerTextField()
            }
        )

        // Button to Fetch Weather
        Button(
            onClick = {
                if (city.isNotEmpty()) {
                    viewModel.fetchWeather(city)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Get Weather")
        }

        // Display Weather Info
        Text(
            text = weatherInfo,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherScreenPreview() {
    SimpleWeatherAppTheme {
        WeatherScreen()
    }
}