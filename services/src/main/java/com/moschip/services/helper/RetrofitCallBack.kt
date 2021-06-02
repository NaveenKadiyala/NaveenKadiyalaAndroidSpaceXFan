package com.moschip.services.helper

/**
 * Purpose : Create for handling the data from services module to app module.
 * Created by : Naveen kadiyala
 */
interface RetrofitCallBack {
    /**
     * @param response : It could be any type of response.
     * @param error    : If there is any error occurred while calling the apis.
     */
    fun responseListener(response: Any?, error: String?)
}