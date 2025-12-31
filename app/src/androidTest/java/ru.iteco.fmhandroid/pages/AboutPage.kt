package ru.iteco.fmhandroid.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandroid.R

class AboutPage {

    @Step("Проверить что экран 'О приложении' отображается")
    fun checkAboutScreenIsDisplayed(): AboutPage {
        Espresso.onView(ViewMatchers.withId(R.id.about_version_value_text_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    @Step("Проверить версию приложения")
    fun checkAppVersion(): AboutPage {
        Espresso.onView(ViewMatchers.withId(R.id.about_version_value_text_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    @Step("Нажать на ссылку 'Политика конфиденциальности'")
    fun clickPrivacyPolicy(): AboutPage {
        Espresso.onView(ViewMatchers.withId(R.id.about_privacy_policy_value_text_view))
            .perform(ViewActions.click())
        return this
    }

    @Step("Нажать на ссылку 'Пользовательское соглашение'")
    fun clickTermsOfUse(): AboutPage {
        Espresso.onView(ViewMatchers.withId(R.id.about_terms_of_use_value_text_view))
            .perform(ViewActions.click())
        return this
    }

    @Step("Вернуться назад")
    fun navigateBack(): MainPage {
        Espresso.onView(ViewMatchers.withId(R.id.about_back_image_button))
            .perform(ViewActions.click())
        return MainPage()
    }
}