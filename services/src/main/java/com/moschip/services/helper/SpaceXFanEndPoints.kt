package com.moschip.services.helper

import com.moschip.services.model.Rocket
import com.moschip.services.model.UpcomingLaunch
import io.reactivex.Observable
import retrofit2.http.GET

interface SpaceXFanEndPoints {

    @GET("rockets")
    fun rockets(): Observable<List<Rocket>>

    @GET("launches/upcoming")
    fun upcomingLaunches(): Observable<List<UpcomingLaunch>>

}