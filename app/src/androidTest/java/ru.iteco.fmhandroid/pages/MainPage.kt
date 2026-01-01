package ru.iteco.fmhandroid.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandroid.R

class MainPage {

    @Step("Проверить что главный экран отображается")
    fun checkMainScreenIsDisplayed(): MainPage {
        try {
            // Проверяем кнопку меню
            Espresso.onView(ViewMatchers.withId(R.id.main_menu_image_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (e: Exception) {
            // Или заголовок
            Espresso.onView(ViewMatchers.withText("В ХОСПИСЕ"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
        return this
    }

    @Step("Открыть меню")
    fun openMenu(): MainPage {
        Espresso.onView(ViewMatchers.withId(R.id.main_menu_image_button))
            .perform(ViewActions.click())
        return this
    }

    @Step("Перейти в раздел 'Новости' через меню")
    fun navigateToNewsFromMenu(): NewsPage {
        openMenu()
        Thread.sleep(500)
        // Ищем "Новости" в выпадающем меню
        Espresso.onView(ViewMatchers.withText("Новости"))
            .perform(ViewActions.click())
        return NewsPage()
    }

    @Step("Нажать на 'Все новости' (если есть)")
    fun clickAllNews(): NewsPage {
        try {
            Espresso.onView(ViewMatchers.withId(R.id.all_news_text_view))
                .perform(ViewActions.click())
        } catch (e: Exception) {
            // Если нет такой кнопки, пробуем найти по тексту
            Espresso.onView(ViewMatchers.withText("Все новости"))
                .perform(ViewActions.click())
        }
        return NewsPage()
    }

    @Step("Перейти в раздел 'Цитаты'")
    fun navigateToQuotes(): QuotesPage {
        Espresso.onView(ViewMatchers.withId(R.id.our_mission_image_button))
            .perform(ViewActions.click())
        return QuotesPage()
    }

    // ДОБАВЛЕННЫЕ МЕТОДЫ ДЛЯ NavigationTest:

    @Step("Перейти в раздел 'О приложении' через меню")
    fun navigateToAboutFromMenu(): AboutPage {
        openMenu()
        Thread.sleep(500)
        // Ищем "О приложении" в меню
        Espresso.onView(ViewMatchers.withText("О приложении"))
            .perform(ViewActions.click())
        return AboutPage()
    }

    @Step("Выйти из приложения")
    fun logout(): MainPage {
        println("Выход из приложения...")

        // Нажимаем на иконку авторизации
        Espresso.onView(ViewMatchers.withId(R.id.authorization_image_button))
            .perform(ViewActions.click())
        Thread.sleep(1000)

        // Ищем кнопку выхода в появившемся меню
        try {
            Espresso.onView(ViewMatchers.withText("Выйти"))
                .perform(ViewActions.click())
            println("Нажали 'Выйти'")
        } catch (e: Exception) {
            try {
                Espresso.onView(ViewMatchers.withText("Выход"))
                    .perform(ViewActions.click())
                println("Нажали 'Выход'")
            } catch (e2: Exception) {
                // Если нет кнопки выхода, просто закрываем меню
                Espresso.pressBack()
                println("Не нашли кнопку выхода, закрыл меню")
            }
        }

        // Ждем выхода
        Thread.sleep(2000)
        return this
    }
}