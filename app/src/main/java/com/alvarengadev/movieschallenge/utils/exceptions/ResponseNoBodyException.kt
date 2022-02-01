package com.alvarengadev.movieschallenge.utils.exceptions

class ResponseNoBodyException : Exception() {
    override val message: String
        get() = "response no body"
}