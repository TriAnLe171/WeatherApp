package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.theme.network.WeatherAPI
import com.example.weatherapp.ui.theme.screen.city.CityListScreen
import com.example.weatherapp.ui.theme.screen.detail.WeatherDetailsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var weatherAPI: WeatherAPI

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                var showSplashScreen by remember { mutableStateOf(true) }
                val navController = rememberNavController()

                if (showSplashScreen) {
                    SplashScreen { showSplashScreen = false }
                } else {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = { Text(stringResource(R.string.weather_app)) },
                                actions = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_cloud),
                                        contentDescription = "Weather Icon",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    titleContentColor = Color.White
                                )
                            )
                        },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        MainNavigation(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            weatherAPI = weatherAPI
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
        delay(3000) // 3 seconds delay
        onTimeout()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_cloud),
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = stringResource(R.string.welcome_to_weather_app),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    weatherAPI: WeatherAPI
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "cityList"
    ) {
        composable("cityList") {
            CityListScreen(
                onCitySelected = { city ->
                    navController.navigate("detail/$city")
                }
            )
        }
        composable("detail/{cityName}") { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName") ?: ""
            WeatherDetailsScreen(cityName = cityName)
        }
    }
}

