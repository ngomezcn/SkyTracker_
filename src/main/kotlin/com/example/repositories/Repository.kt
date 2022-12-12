package com.example.repositories
/*
import com.example.models.Position
import com.example.models.SpaceTrack.STSatelliteCatalog
import com.example.models.n2yo.N2VisualPasses
import io.ktor.client.call.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*


class Repository {

    private val apiClient = ApiClient

    object N2yo {
        const val baseUrl = "https://api.n2yo.com/rest/v1"
        const val apiKey = "PBXXJB-TLNPHZ-N5MLV7-4YQD"
    }

    object SpaceTrack {
        const val baseUrl = "https://www.space-track.org"
        var cookie: Cookie? = null
    }

    suspend fun getVisualPasses(noradCadID: String, observator: Position) : N2VisualPasses {

        return apiClient.rest.get(
            N2yo.baseUrl+"/satellite/visualpasses/"+noradCadID+"/"+observator.latitude+"/"+observator.longitude+"/"+observator.altitude+"/2/300/&apiKey="+N2yo.apiKey).body()
    }

    suspend fun getAllSatellites(): List<STSatelliteCatalog> {

        val logInResponse = apiClient.rest.submitForm(
            url = SpaceTrack.baseUrl+"/ajaxauth/login",
            formParameters = Parameters.build {
                append("identity", "naim.gomez.7e5@itb.cat")
                append("password", "3zQwwH28B86tG!c")
            }).body<String>()

        if (logInResponse.equals("\"\"")) { // Si la respuesta esta vacía ha sido exitoso
            SpaceTrack.cookie = apiClient.rest.cookies("${SpaceTrack.baseUrl}/ajaxauth/login").first()
            return apiClient.rest.get(SpaceTrack.baseUrl+"/basicspacedata/query/class/gp")
                .body()
        } else {
            throw Exception("Cannot log in Space Track, check credentials")
        }
    }
}*/