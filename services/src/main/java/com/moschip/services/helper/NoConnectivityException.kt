package com.moschip.services.helper

import java.io.IOException

class NoConnectivityException : IOException() {

    override fun getLocalizedMessage(): String {
        return SConstants.NO_INTERNET
    }

    override val message: String
        get() = SConstants.NO_INTERNET
}