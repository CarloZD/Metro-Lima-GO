package com.example.metrolima.utils

object StringsManager {

    private val translations = mapOf(
        "configuration" to mapOf(
            "es" to "Configuración",
            "en" to "Configuration"
        ),
        "quick_access" to mapOf(
            "es" to "ACCESO RÁPIDO",
            "en" to "QUICK ACCESS"
        ),
        "central_station" to mapOf(
            "es" to "Estación Central",
            "en" to "Central Station"
        ),
        "your_routes" to mapOf(
            "es" to "Tus rutas",
            "en" to "Your routes"
        ),
        "saved_routes" to mapOf(
            "es" to "5 guardadas",
            "en" to "5 saved"
        ),
        "stations" to mapOf(
            "es" to "ESTACIONES",
            "en" to "STATIONS"
        ),
        "home" to mapOf(
            "es" to "Inicio",
            "en" to "Home"
        ),
        "stations_nav" to mapOf(
            "es" to "Estaciones",
            "en" to "Stations"
        ),
        "routes" to mapOf(
            "es" to "Rutas",
            "en" to "Routes"
        ),
        "configuration_nav" to mapOf(
            "es" to "Configuración",
            "en" to "Settings"
        ),
        "theme" to mapOf(
            "es" to "Tema",
            "en" to "Theme"
        ),
        "change_theme_description" to mapOf(
            "es" to "Cambia entre modo claro y oscuro",
            "en" to "Switch between light and dark mode"
        ),
        "light_mode" to mapOf(
            "es" to "Modo Claro",
            "en" to "Light Mode"
        ),
        "dark_mode" to mapOf(
            "es" to "Modo Oscuro",
            "en" to "Dark Mode"
        ),
        "use_system_theme" to mapOf(
            "es" to "Usar tema del sistema",
            "en" to "Use system theme"
        ),
        "language" to mapOf(
            "es" to "Idioma",
            "en" to "Language"
        ),
        "select_language" to mapOf(
            "es" to "Selecciona tu idioma preferido",
            "en" to "Select your preferred language"
        ),
        "select_language_description" to mapOf(
            "es" to "Selecciona el idioma de la aplicación",
            "en" to "Select the application language"
        ),
        "back" to mapOf(
            "es" to "Volver",
            "en" to "Back"
        ),
        "open_metro_map" to mapOf(
            "es" to "Abrir mapa del Metro de Lima",
            "en" to "Open Metro Lima map"
        ),
        "open_google_maps" to mapOf(
            "es" to "Abrir en Google Maps",
            "en" to "Open in Google Maps"
        ),
        "filter_by_line" to mapOf(
            "es" to "Filtrar por línea",
            "en" to "Filter by line"
        ),
        "all_lines" to mapOf(
            "es" to "Todas las líneas",
            "en" to "All lines"
        ),
        "line_1" to mapOf(
            "es" to "Línea 1",
            "en" to "Line 1"
        ),
        "line_2" to mapOf(
            "es" to "Línea 2",
            "en" to "Line 2"
        ),
        "filtering" to mapOf(
            "es" to "Filtrando",
            "en" to "Filtering"
        ),
        "remove_filter" to mapOf(
            "es" to "Quitar filtro",
            "en" to "Remove filter"
        ),
        "search_station_district" to mapOf(
            "es" to "Buscar estación o distrito",
            "en" to "Search station or district"
        ),
        "clear" to mapOf(
            "es" to "Limpiar",
            "en" to "Clear"
        ),
        "stations_found" to mapOf(
            "es" to "estaciones encontradas",
            "en" to "stations found"
        ),
        "no_stations_found" to mapOf(
            "es" to "No se encontraron estaciones",
            "en" to "No stations found"
        ),
        "clear_filters" to mapOf(
            "es" to "Limpiar filtros",
            "en" to "Clear filters"
        ),
        "about_metro_lima" to mapOf(
            "es" to "Acerca de MetroLima GO",
            "en" to "About MetroLima GO"
        ),
        "development_team" to mapOf(
            "es" to "Equipo de Desarrollo",
            "en" to "Development Team"
        ),
        "developer_1" to mapOf(
            "es" to "Desarrollador 1",
            "en" to "Developer 1"
        ),
        "developer_2" to mapOf(
            "es" to "Desarrolladora 2",
            "en" to "Developer 2"
        ),
        "developer_3" to mapOf(
            "es" to "Desarrollador 3",
            "en" to "Developer 3"
        ),
        "backend" to mapOf(
            "es" to "Backend",
            "en" to "Backend"
        ),
        "frontend" to mapOf(
            "es" to "Frontend",
            "en" to "Frontend"
        ),
        "ui_ux" to mapOf(
            "es" to "UI/UX",
            "en" to "UI/UX"
        ),
        "app_information" to mapOf(
            "es" to "Información de la Aplicación",
            "en" to "Application Information"
        ),
        "version" to mapOf(
            "es" to "Versión",
            "en" to "Version"
        ),
        "release_date" to mapOf(
            "es" to "Fecha de Lanzamiento",
            "en" to "Release Date"
        ),
        "release_date_value" to mapOf(
            "es" to "24 de octubre 2025",
            "en" to "October 24, 2025"
        ),
        "current_language" to mapOf(
            "es" to "Español",
            "en" to "English"
        ),
        "acknowledgments" to mapOf(
            "es" to "Agradecimientos",
            "en" to "Thanks"
        ),
        "acknowledgments_text" to mapOf(
            "es" to "Agradecemos a todos los usuarios por su apoyo y retroalimentación para hacer de MetroLima GO una mejor aplicación.",
            "en" to "We thank all users for their support and feedback to make MetroLima GO a better application."
        ),

        // FavoritesScreen
        "favorites" to mapOf(
            "es" to "Favoritos",
            "en" to "Favorites"
        ),
        "no_favorite_routes" to mapOf(
            "es" to "No tienes rutas favoritas",
            "en" to "You have no favorite routes"
        ),
        "save_frequent_routes" to mapOf(
            "es" to "Guarda tus rutas frecuentes aquí",
            "en" to "Save your frequent routes here"
        ),
        "no_favorite_stations" to mapOf(
            "es" to "No tienes estaciones favoritas",
            "en" to "You have no favorite stations"
        ),
        "save_frequent_stations" to mapOf(
            "es" to "Guarda tus estaciones frecuentes aquí",
            "en" to "Save your frequent stations here"
        ),
        "delete" to mapOf(
            "es" to "Eliminar",
            "en" to "Delete"
        ),
        "spanish" to mapOf(
            "es" to "Español",
            "en" to "Español"
        ),
        "english" to mapOf(
            "es" to "English",
            "en" to "English"
        ),
        "about" to mapOf(
            "es" to "Acerca de",
            "en" to "About"
        ),
        "version" to mapOf(
            "es" to "Versión",
            "en" to "Version"
        ),
        "metro_lima" to mapOf(
            "es" to "Metro Lima",
            "en" to "Metro Lima"
        ),
        "where_to_go" to mapOf(
            "es" to "¿Hacia dónde vas?",
            "en" to "Where are you going?"
        ),
        "your_location" to mapOf(
            "es" to "Tu ubicación",
            "en" to "Your location"
        ),
        "route" to mapOf(
            "es" to "Ruta",
            "en" to "Route"
        ),
        "trip_details" to mapOf(
            "es" to "Detalles del viaje",
            "en" to "Trip details"
        ),
        "origin" to mapOf(
            "es" to "Origen",
            "en" to "Origin"
        ),
        "destination" to mapOf(
            "es" to "Destino",
            "en" to "Destination"
        ),
        "select_station" to mapOf(
            "es" to "Selecciona una estación",
            "en" to "Select a station"
        ),
        "select_origin" to mapOf(
            "es" to "Seleccionar origen",
            "en" to "Select origin"
        ),
        "select_destination" to mapOf(
            "es" to "Seleccionar destino",
            "en" to "Select destination"
        ),
        "calculate_route" to mapOf(
            "es" to "Calcular ruta",
            "en" to "Calculate route"
        ),
        "plaza_mayor" to mapOf(
            "es" to "Plaza Mayor",
            "en" to "Plaza Mayor"
        ),
        "route_map" to mapOf(
            "es" to "Mapa de la ruta",
            "en" to "Route map"
        ),
        "tap_to_open" to mapOf(
            "es" to "(toca para abrir)",
            "en" to "(tap to open)"
        ),
        "not_specified" to mapOf(
            "es" to "No especificado",
            "en" to "Not specified"
        ),
        "estimated_time" to mapOf(
            "es" to "Tiempo estimado",
            "en" to "Estimated time"
        ),
        "intermediate_stations" to mapOf(
            "es" to "Estaciones intermedias",
            "en" to "Intermediate stations"
        ),
        "save_route" to mapOf(
            "es" to "Guardar ruta",
            "en" to "Save route"
        ),
        "45_minutes" to mapOf(
            "es" to "45 minutos",
            "en" to "45 minutes"
        ),
        "15_stations" to mapOf(
            "es" to "15 estaciones",
            "en" to "15 stations"
        ),
        "station" to mapOf(
            "es" to "Estación",
            "en" to "Station"
        ),
        "add_to_favorites" to mapOf(
            "es" to "Agregar a favoritos",
            "en" to "Add to favorites"
        ),
        "station_not_found" to mapOf(
            "es" to "Estación no encontrada",
            "en" to "Station not found"
        ),
        "back" to mapOf(
            "es" to "Volver",
            "en" to "Back"
        ),
        "lines" to mapOf(
            "es" to "Líneas",
            "en" to "Lines"
        ),
        "line_detail" to mapOf(
            "es" to "Detalle de Línea",
            "en" to "Line Detail"
        ),
        "operational" to mapOf(
            "es" to "Operativa",
            "en" to "Operational"
        ),
        "under_construction" to mapOf(
            "es" to "En construcción",
            "en" to "Under construction"
        ),
        "planned" to mapOf(
            "es" to "Planificada",
            "en" to "Planned"
        ),
        "show_map" to mapOf(
            "es" to "Ver mapa",
            "en" to "Show map"
        ),
        "view_stations" to mapOf(
            "es" to "Ver estaciones",
            "en" to "View stations"
        )
    )


    fun getString(key: String, isEnglish: Boolean): String {
        val languageCode = if (isEnglish) "en" else "es"
        return translations[key]?.get(languageCode) ?: key
    }

    fun getAllStrings(isEnglish: Boolean): Map<String, String> {
        val languageCode = if (isEnglish) "en" else "es"
        return translations.mapValues { (_, translations) ->
            translations[languageCode] ?: ""
        }
    }
}