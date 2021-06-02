package com.moschip.services.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Rocket(
    val height: Diameter,
    val diameter: Diameter,
    val mass: Mass,

    @SerializedName("first_stage")
    val firstStage: FirstStage,

    @SerializedName("second_stage")
    val secondStage: SecondStage,

    val engines: Engines,

    @SerializedName("landing_legs")
    val landingLegs: LandingLegs,

    @SerializedName("payload_weights")
    val payloadWeights: List<PayloadWeight>,

    @SerializedName("flickr_images")
    val flickrImages: List<String>,

    val name: String,
    val type: String,
    val active: Boolean,
    val stages: String,
    val boosters: Long,

    @SerializedName("cost_per_launch")
    val costPerLaunch: Long,

    @SerializedName("success_rate_pct")
    val successRatePct: Long,

    @SerializedName("first_flight")
    val firstFlight: String,

    val country: String,
    val company: String,
    val wikipedia: String,
    val description: String,
    val id: String,
    var isFavorite: Boolean = false
) : Parcelable {

    fun getDateInRequiredFormat(): String {
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormatDate = SimpleDateFormat("MMM d", Locale.getDefault())
        return outputFormatDate.format(inputFormat.parse(firstFlight)).toString().replace(" ", "\n")
    }

    fun getDate(): String {
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormatDate = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        return outputFormatDate.format(inputFormat.parse(firstFlight)).toString()
    }
}

@Parcelize
data class Diameter(
    val meters: Double,
    val feet: Double
) : Parcelable {
    fun getMetersInString(): String {
        return meters.toString()
    }
}

@Parcelize
data class Engines(
    val isp: ISP,

    @SerializedName("thrust_sea_level")
    val thrustSeaLevel: Thrust,

    @SerializedName("thrust_vacuum")
    val thrustVacuum: Thrust,

    val number: Long,
    val type: String,
    val version: String,
    val layout: String?,

    @SerializedName("engine_loss_max")
    val engineLossMax: Long,

    @SerializedName("propellant_1")
    val propellant1: String,

    @SerializedName("propellant_2")
    val propellant2: String,

    @SerializedName("thrust_to_weight")
    val thrustToWeight: Double
) : Parcelable {
    fun getThrustToWeightInString(): String {
        return thrustToWeight.toString()
    }
}

@Parcelize
data class ISP(
    @SerializedName("sea_level")
    val seaLevel: Long,
    val vacuum: Long
) : Parcelable {
    fun getSeaLevelInString(): String {
        return seaLevel.toString()
    }

    fun getVacuumInString(): String {
        return vacuum.toString()
    }
}

@Parcelize
data class Thrust(
    val kN: Long,
    val lbf: Long
) : Parcelable {
    fun getKnInString(): String {
        return kN.toString()
    }
}

@Parcelize
data class FirstStage(
    @SerializedName("thrust_sea_level")
    val thrustSeaLevel: Thrust,

    @SerializedName("thrust_vacuum")
    val thrustVacuum: Thrust,

    val reusable: Boolean,
    val engines: Long,

    @SerializedName("fuel_amount_tons")
    val fuelAmountTons: Double,

    @SerializedName("burn_time_sec")
    val burnTimeSEC: Long
) : Parcelable

@Parcelize
data class LandingLegs(
    val number: Long,
    val material: String?
) : Parcelable

@Parcelize
data class Mass(
    val kg: Long,
    val lb: Long
) : Parcelable {
    fun getKgs(): String {
        return kg.toString()
    }
}

@Parcelize
data class PayloadWeight(
    val id: String,
    val name: String,
    val kg: Long,
    val lb: Long
) : Parcelable

@Parcelize
data class SecondStage(
    val thrust: Thrust,
    val payloads: Payloads,
    val reusable: Boolean,
    val engines: Long,

    @SerializedName("fuel_amount_tons")
    val fuelAmountTons: Double,

    @SerializedName("burn_time_sec")
    val burnTimeSEC: Long
) : Parcelable

@Parcelize
data class Payloads(
    @SerializedName("composite_fairing")
    val compositeFairing: CompositeFairing,

    @SerializedName("option_1")
    val option1: String
) : Parcelable

@Parcelize
data class CompositeFairing(
    val height: Diameter,
    val diameter: Diameter
) : Parcelable
