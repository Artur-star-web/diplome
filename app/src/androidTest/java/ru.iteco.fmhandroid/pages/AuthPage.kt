package ru.iteco.fmhandroid.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandroid.R

class AuthPage {

    @Step("Проверить что экран авторизации отображается")
    fun checkAuthScreenIsDisplayed(): AuthPage {
        Espresso.onView(ViewMatchers.withHint("Логин"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        return this
    }

    @Step("Ввести логин: {login}")
    fun enterLogin(login: String): AuthPage {
        Espresso.onView(ViewMatchers.withHint("Логин"))
            .perform(
                ViewActions.click(),
                ViewActions.clearText(),
                ViewActions.typeText(login)
            )
        return this
    }

    @Step("Ввести пароль: {password}")
    fun enterPassword(password: String): AuthPage {
        Espresso.onView(ViewMatchers.withHint("Пароль"))
            .perform(
                ViewActions.click(),
                ViewActions.clearText(),
                ViewActions.typeText(password)
            )
        return this
    }

    @Step("Скрыть клавиатуру")
    fun closeKeyboard(): AuthPage {
        Espresso.closeSoftKeyboard()
        Thread.sleep(500) // ждем скрытия клавиатуры
        return this
    }

    @Step("Нажать кнопку 'Войти'")
    fun clickSignInButton(): AuthPage {
        // 1. Сначала скрываем клавиатуру
        closeKeyboard()

        // 2. Ждем немного
        Thread.sleep(1000)

        // 3. Нажимаем кнопку
        Espresso.onView(ViewMatchers.withText("ВОЙТИ"))
            .perform(ViewActions.click())

        return this
    }

    @Step("Очистить поле логина")
    fun clearLogin(): AuthPage {
        Espresso.onView(ViewMatchers.withHint("Логин"))
            .perform(ViewActions.clearText())
        return this
    }

    @Step("Очистить поле пароля")
    fun clearPassword(): AuthPage {
        Espresso.onView(ViewMatchers.withHint("Пароль"))
            .perform(ViewActions.clearText())
        return this
    }
}