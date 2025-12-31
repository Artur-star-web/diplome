package ru.iteco.fmhandroid.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandroid.R

class QuotesPage {

    @Step("Проверить что экран цитат отображается")
    fun checkQuotesScreenIsDisplayed(): QuotesPage {
        // Пробуем разные способы найти экран цитат
        try {
            // 1. По заголовку "Главное – жить любя"
            Espresso.onView(ViewMatchers.withText("Главное – жить любя"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (e: Exception) {
            try {
                // 2. По ID элемента
                Espresso.onView(ViewMatchers.withId(R.id.our_mission_item_list_recycler_view))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            } catch (e2: Exception) {
                try {
                    // 3. По любому тексту на экране цитат
                    Espresso.onView(ViewMatchers.withText("Главное"))
                        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                } catch (e3: Exception) {
                    // 4. По кнопке возврата
                    Espresso.onView(ViewMatchers.withId(R.id.our_mission_image_button))
                        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                }
            }
        }
        return this
    }

    @Step("Вернуться на главный экран")
    fun navigateBack(): MainPage {
        Espresso.pressBack()
        return MainPage()
    }
}