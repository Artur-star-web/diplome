package ru.iteco.fmhandroid.tests.auth

import androidx.test.filters.LargeTest
import io.qameta.allure.kotlin.Allure
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Severity
import io.qameta.allure.kotlin.SeverityLevel
import io.qameta.allure.kotlin.Story
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import ru.iteco.fmhandroid.pages.MainPage
import ru.iteco.fmhandroid.tests.BaseTest

@LargeTest
@Epic("Авторизация")
@Feature("Вход в приложение")
@Story("Успешная авторизация с валидными данными")
class AuthTest : BaseTest() {

    private val mainPage = MainPage()

    @Test
    @DisplayName("Успешная авторизация с данными login2/password2")
    @Description("Тест проверяет возможность входа в приложение с корректными учетными данными")
    @Severity(SeverityLevel.BLOCKER)
    fun testSuccessfulAuth() {

        Allure.label("owner", "QA Team")
        Allure.label("layer", "authentication")

        Allure.step("Проверка экрана авторизации") {
            println("=== ТЕСТ АВТОРИЗАЦИИ ===")
            println("1. Проверка экрана авторизации...")
            authPage.checkAuthScreenIsDisplayed()
            println("✅ Экран авторизации отображается")
        }

        Allure.step("Ввод логина") {
            println("2. Ввод логина...")
            authPage.enterLogin(TEST_LOGIN)
            println("✅ Введен логин: $TEST_LOGIN")
        }

        Allure.step("Ввод пароля") {
            println("3. Ввод пароля...")
            authPage.enterPassword(TEST_PASSWORD)
            println("✅ Введен пароль")
        }

        Allure.step("Нажатие кнопки 'Войти'") {
            println("4. Нажатие кнопки 'Войти'...")
            authPage.closeKeyboard()
            Thread.sleep(500)
            authPage.clickSignInButton()
            println("✅ Нажата кнопка 'Войти'")
        }

        Allure.step("Проверка главного экрана") {
            println("5. Проверка главного экрана...")
            Thread.sleep(3000)
            mainPage.checkMainScreenIsDisplayed()
            println("✅ Авторизация успешна! Главный экран отображен")
        }

        println("\n🎉 ТЕСТ АВТОРИЗАЦИИ ЗАВЕРШЕН УСПЕШНО!")
    }
}