package ru.iteco.fmhandroid.tests

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import ru.iteco.fmhandroid.pages.AuthPage
import ru.iteco.fmhandroid.ui.AppActivity
import androidx.test.ext.junit.runners.AndroidJUnit4

@LargeTest
@RunWith(AndroidJUnit4::class)
abstract class BaseTest {

    protected val authPage = AuthPage()

    @get:Rule
    val activityRule = ActivityScenarioRule(AppActivity::class.java)

    companion object {
        const val TEST_LOGIN = "login2"
        const val TEST_PASSWORD = "password2"
    }

    @Before
    fun setUp() {
        // Ждем сплеш-скрин
        Thread.sleep(5000)

        // Проверяем где находимся
        checkIfOnAuthScreen()
    }

    private fun checkIfOnAuthScreen() {
        // Ждем 2 секунды
        Thread.sleep(2000)

        // Пробуем найти поле логина
        try {
            Espresso.onView(ViewMatchers.withHint("Логин"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            println("✅ На экране авторизации")
        } catch (e: Exception) {
            println("❌ Не на экране авторизации. Возможно уже авторизованы.")
            println("Нужно сначала выйти из приложения!")

            // Пробуем выйти если уже авторизованы
            tryToLogout()
        }
    }

    private fun tryToLogout() {
        println("Пытаюсь выйти из приложения...")

        // 1. Проверяем что на главном экране
        try {
            Espresso.onView(ViewMatchers.withId(ru.iteco.fmhandroid.R.id.main_menu_image_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            println("✅ На главном экране, пытаюсь выйти...")

            // 2. Нажимаем на иконку авторизации
            Espresso.onView(ViewMatchers.withId(ru.iteco.fmhandroid.R.id.authorization_image_button))
                .perform(ViewActions.click())
            Thread.sleep(1000)

            // 3. Ищем кнопку выхода
            try {
                Espresso.onView(ViewMatchers.withText("Выйти"))
                    .perform(ViewActions.click())
                println("✅ Нажал 'Выйти'")
            } catch (e: Exception) {
                try {
                    Espresso.onView(ViewMatchers.withText("Выход"))
                        .perform(ViewActions.click())
                    println("✅ Нажал 'Выход'")
                } catch (e2: Exception) {
                    println("❌ Не нашел кнопку выхода")
                    // Нажимаем Back и пробуем снова
                    Espresso.pressBack()
                }
            }

            // 4. Ждем выхода
            Thread.sleep(3000)

        } catch (e: Exception) {
            println("❌ Не на главном экране: ${e.message}")
        }
    }

    protected fun login() {
        println("=== АВТОРИЗАЦИЯ ===")

        // Ваша логика авторизации
        authPage.enterLogin(TEST_LOGIN)
        authPage.enterPassword(TEST_PASSWORD)
        authPage.clickSignInButton()
        Thread.sleep(5000)
        println("✅ Авторизация выполнена")
    }
}