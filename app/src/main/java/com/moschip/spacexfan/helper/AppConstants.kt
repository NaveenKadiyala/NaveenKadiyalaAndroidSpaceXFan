package com.moschip.spacexfan.helper

interface AppConstants {

    object Defaults {
        const val ACTIVE = "Status : Active"
        const val IN_ACTIVE = "Status : InActive"
        const val FROM = "from"
        const val POSITION = "position"
        const val IS_FAV = "isFav"
        const val IS_CLICKED = "isClicked"
        const val SPLASH_ANIM_TIME : Long = 3000
    }

    object FragmentConstants {
        const val ROCKETS = "rockets"
        const val FAVORITES = "favorites"
        const val LAUNCHES = "launches"
        const val ROCKET_DETAILS = "rocketDetails"
        const val LAUNCH_DETAILS = "Upcoming Launch Details"
    }

    object ModelConstants {
        const val FAVORITE = "favorite"
        const val ROCKET = "rocket"
        const val UPCOMING_LAUNCH = "launch"
    }

    enum class ObserverEvents {
        OPEN_MAIN_SCREEN
    }

    object FirebaseEventConstants{
        const val ROCKET_DETAILS_EVENT = "rocketDetailsOpened"
        const val UPCOMING_DETAILS_EVENT = "upcomingLaunchDetailsOpened"
        const val ROCKET_NAME = "rocketName"
    }
}