package ru.iteco.fmhandroid.utils

import io.qameta.allure.kotlin.Allure
import io.qameta.allure.kotlin.Step

object AllureHelper {

    fun setDescription(description: String) {
        Allure.description(description)
    }

    fun addTag(tag: String) {
        Allure.label("tag", tag)
    }

    enum class Severity {
        BLOCKER, CRITICAL, NORMAL, MINOR, TRIVIAL
    }

    fun setSeverity(severity: Severity) {
        Allure.label("severity", severity.name.lowercase())
    }
}