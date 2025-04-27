package com.example.weatherapp.ui.theme.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.data.WeatherRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailsScreen(
    cityName: String,
    viewModel: WeatherDetailsViewModel = hiltViewModel()
) {
    val weather by viewModel.weather.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    LaunchedEffect(cityName) {
        viewModel.fetchWeather(cityName)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.detail)) })
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                isLoading -> LoadingIndicator()
                errorMessage != null -> ErrorMessage(errorMessage = errorMessage ?: "Unknown error")
                weather != null -> weather?.let { WeatherDetailsCard(it) }
            }
        }
    }
}

@Composable
fun WeatherDetailsCard(weather: WeatherRes) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.city, weather.name ?: "Unknown"),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(
                    R.string.temperature,
                    weather.main?.temp?.toCelsius()?.let { "$it°C" } ?: "N/A"),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            WeatherIcon(iconCode = weather.weather?.firstOrNull()?.icon)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(
                R.string.description,
                weather.weather?.firstOrNull()?.description ?: "N/A"
            ))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = stringResource(
                R.string.humidity,
                weather.main?.humidity?.let { "$it%" } ?: "N/A"))
            Text(text = stringResource(
                R.string.pressure,
                weather.main?.pressure?.let { "$it hPa" } ?: "N/A"))
            Text(text = stringResource(
                R.string.wind_speed,
                weather.wind?.speed?.let { "$it m/s" } ?: "N/A"))
            Text(text = stringResource(
                R.string.sunrise,
                weather.sys?.sunrise?.let { formatUnixTime(it) } ?: "N/A"))
            Text(text = stringResource(
                R.string.sunset,
                weather.sys?.sunset?.let { formatUnixTime(it) } ?: "N/A"))
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(errorMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.error, errorMessage),
            color = Color.Red,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun WeatherIcon(iconCode: String?) {
    if (iconCode != null) {
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://openweathermap.org/img/w/$iconCode.png"
            ),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(64.dp)
        )
    }
}

fun Double.toCelsius(): Int {
    return (this - 273.15).toInt()
}

fun formatUnixTime(unixTime: Int): String {
    val date = java.util.Date(unixTime.toLong() * 1000)
    val format = java.text.SimpleDateFormat("hh:mm a", java.util.Locale.getDefault())
    return format.format(date)
}

