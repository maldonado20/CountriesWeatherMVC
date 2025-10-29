# CountriesWeatherMVC

App Android (Kotlin + XML, MVC) que consume **RestCountries** y **WeatherAPI**:
- Pantalla 1: Lista de regiones
- Pantalla 2: Países por región (nombre, bandera, capital)
- Pantalla 3: Detalle del país (datos + clima de la capital)
- Manejo de errores y loaders, icono personalizado, diseño azul/negro con degradado rojo

## Configuración
1) Abre el proyecto en Android Studio (2025 Narwhal o superior).
2) Coloca tu clave en `local.properties` o como env var:
   ```properties
   WEATHER_API_KEY=TU_API_KEY
   ```
3) Ejecuta el proyecto en un dispositivo físico o emulador.

## Tecnologías
- Retrofit 2 + Gson Converter
- OkHttp Logging Interceptor
- Coil (carga de imágenes)
- Navigation + Fragments (sin SafeArgs para evitar plugin extra)
- Arquitectura **MVC**: `models/` (datos), `controllers/` (coordinación), `ui/` (vistas/fragments/adapters)

## APK
Genera el APK desde **Build > Build Bundle(s) / APK(s) > Build APK(s)**. 
Sube el APK y deja el enlace en el README si es requerido.