# WeatherApp

WeatherApp is a simple Android application built with Jetpack Compose that allows users to manage a list of cities and view detailed weather information for each city. The app fetches weather data from the OpenWeatherMap API and displays it in a user-friendly interface.

---

## Features

- **City List Screen**:
  - Add cities to a list using a Floating Action Button (FAB).
  - Remove cities from the list.
  - Navigate to the weather details screen by selecting a city.
  - Displays a message when no cities are added.

- **Weather Details Screen**:
  - Fetches and displays weather data for the selected city using the OpenWeatherMap API.
  - Shows detailed weather information, including:
    - Current temperature
    - Weather description
    - Humidity
    - Wind speed
    - Sunrise and sunset times
  - Displays a weather icon fetched dynamically using the Coil library.

- **Splash Screen**:
  - A welcoming splash screen with a fade-in animation.

---

## Screenshots

### City List Screen
![image](https://github.com/user-attachments/assets/1b2351c8-3cbd-4459-a5e0-671f82b65fe4)


### Weather Details Screen
![image](https://github.com/user-attachments/assets/577f86a8-cdc3-4334-8d3d-e323cb31538d)


### Splash Screen
![image](https://github.com/user-attachments/assets/8ca5c33b-644d-4695-be9e-4f8819a217de)

---

## Tech Stack

- **Programming Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Dependency Injection**: Hilt
- **Networking**: Retrofit
- **Image Loading**: Coil
- **State Management**: StateFlow
- **Build System**: Gradle

---

## Architecture

The app follows a **MVVM (Model-View-ViewModel)** architecture:

- **View**: Composables like `CityListScreen` and `WeatherDetailsScreen`.
- **ViewModel**: `CityListViewModel` and `WeatherDetailsViewModel` handle UI logic and state.
- **Model**: Data classes like `WeatherRes`, `Main`, `Wind`, etc., represent the API response.

---

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/WeatherApp.git
   cd WeatherApp
   ```

2. Open the project in Android Studio.

3. Add your OpenWeatherMap API key:
   - Open `local.properties` and add:
     ```properties
     WEATHER_API_KEY=your_api_key_here
     ```

4. Build and run the app on an emulator or physical device.

---

## Dependencies

Add the following dependencies to your `build.gradle` file:

```gradle
configurations.all {
    exclude(group = "xmlpull", module = "xmlpull")
}
dependencies {
  ...
  // Navigation
  implementation(libs.androidx.navigation)
  implementation(libs.androidx.navigation.compose)
  
  // Hilt Dependency Injection
  implementation(libs.hilt.android)
  kapt(libs.hilt.compiler)
  implementation(libs.androidx.hilt.navigation.compose)
  androidTestImplementation(libs.hilt.android.testing)
  testImplementation(libs.hilt.android.testing)
  
  // Retrofit
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation(libs.retrofit)
  implementation(libs.kotlinx.serialization)
  implementation(libs.serialization.converter)
  implementation(libs.coil)
}
```

---

## API Integration

The app uses the [OpenWeatherMap API](https://openweathermap.org/api) to fetch weather data. Ensure you have an API key and add it to the project as described in the setup instructions.

---

## Folder Structure

```
app/
├── di/                     # Dependency Injection setup
├── ui/theme/               # UI components and themes
│   ├── screen/city/        # City List Screen and ViewModel
│   ├── screen/detail/      # Weather Details Screen and ViewModel
│   ├── data/               # Data models for API responses
│   ├── network/            # Retrofit API interface
├── MainActivity.kt         # Entry point of the app
├── MainApplication.kt      # Application class with Hilt setup
```

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests to improve the app.

---

## Contact

For any questions or feedback, feel free to reach out:

- **Email**: triandole@gmail.com
- **GitHub**: [TriAnLe171](https://github.com/TriAnLe171)
