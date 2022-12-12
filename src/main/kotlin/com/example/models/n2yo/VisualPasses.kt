package com.example.models.n2yo

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class N2VisualPasses (
    @SerialName("info"   ) var info   : Info?             = Info(),
    @SerialName("passes" ) var passes : ArrayList<Passes> = arrayListOf()
 )

@kotlinx.serialization.Serializable
data class Info (
    @SerialName("satid"             ) var satid             : Int?    = null,
    @SerialName("satname"           ) var satname           : String? = null,
    @SerialName("transactionscount" ) var transactionscount : Int?    = null,
    @SerialName("passescount"       ) var passescount       : Int?    = null
)

@kotlinx.serialization.Serializable
data class Passes (
    @SerialName("startAz"         ) var startAz         : Double? = null,
    @SerialName("startAzCompass"  ) var startAzCompass  : String? = null,
    @SerialName("startEl"         ) var startEl         : Double? = null,
    @SerialName("startUTC"        ) var startUTC        : Int?    = null,
    @SerialName("maxAz"           ) var maxAz           : Double? = null,
    @SerialName("maxAzCompass"    ) var maxAzCompass    : String? = null,
    @SerialName("maxEl"           ) var maxEl           : Double? = null,
    @SerialName("maxUTC"          ) var maxUTC          : Int?    = null,
    @SerialName("endAz"           ) var endAz           : Double? = null,
    @SerialName("endAzCompass"    ) var endAzCompass    : String? = null,
    @SerialName("endEl"           ) var endEl           : Int?    = null,
    @SerialName("endUTC"          ) var endUTC          : Int?    = null,
    @SerialName("mag"             ) var mag             : Double? = null,
    @SerialName("duration"        ) var duration        : Int?    = null,
    @SerialName("startVisibility" ) var startVisibility : Int?    = null
)