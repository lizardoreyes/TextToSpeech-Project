package com.lizardoreyes.texttospeechproject.models

data class Language(
    val name: String,
    val code: String
) {
    override fun toString(): String {
        return name
    }
}
