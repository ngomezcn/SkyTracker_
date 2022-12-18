package com.example.routes.web

enum class WebRoutesEnum(val route: String, val title: String) {
    home("home", "SkyTracker"),
    satellites("satellites", "Satellites"),
    view_satellite("view_satellite", ""),
    create_account("create_account", "Create Account"),
    sign_in("sign_in", "Sign In"),
}