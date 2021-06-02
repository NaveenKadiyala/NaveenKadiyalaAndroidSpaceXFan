package com.moschip.services.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class UpcomingLaunch(
    val links: Links,
    val tbd: Boolean?,
    val net: Boolean?,
    val window: Long?,
    val rocket: String,
    val details: String?,
    val crew: List<String?>?,
    val capsules: List<String?>?,
    val payloads: List<String?>?,
    val launchpad: String?,

    @SerializedName("auto_update")
    val autoUpdate: Boolean,

    @SerializedName("launch_library_id")
    val launchLibraryID: String?,

    @SerializedName("flight_number")
    val flightNumber: Long,

    val name: String,

    @SerializedName("date_utc")
    val dateUTC: String,

    @SerializedName("date_unix")
    val dateUnix: Long,

    @SerializedName("date_local")
    val dateLocal: String,

    @SerializedName("date_precision")
    val datePrecision: String,

    val upcoming: Boolean,
    val cores: List<Core>,
    val id: String
) : Parcelable {

    fun getConvertedDate(): String {
        return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date(dateUnix * 1000))
            .toString()
    }
}

@Parcelize
data class Core(
    val core: String?,
    val flight: Int?,
    val gridfins: Boolean?,
    val legs: Boolean?,
    val reused: Boolean?,

    @SerializedName("landing_attempt")
    val landingAttempt: Boolean?,

    @SerializedName("landing_success")
    val landingSuccess: Boolean?,

    @SerializedName("landing_type")
    val landingType: String?

) : Parcelable

@Parcelize
data class Links(
    val patch: Patch,
    val reddit: Reddit,
    val wikipedia: String?
) : Parcelable

@Parcelize
data class Patch(
    val small: String?,
    val large: String?
) : Parcelable

@Parcelize
data class Reddit(
    val campaign: String?,
    val recovery: String?
) : Parcelable