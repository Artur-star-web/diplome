package ru.iteco.fmhandroid.pages

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import io.qameta.allure.kotlin.Step
import ru.iteco.fmhandroid.R

class NewsPage {

    @Step("Проверить что экран новостей отображается")
    fun checkNewsScreenIsDisplayed(): NewsPage {
        // Проверяем разные элементы экрана новостей
        try {
            // Заголовок "НОВОСТИ"
            Espresso.onView(ViewMatchers.withText("НОВОСТИ"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } catch (e: Exception) {
            // Или кнопку фильтра/сортировки
            Espresso.onView(ViewMatchers.withId(R.id.filter_news_material_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
        return this
    }

    @Step("Обновить список новостей свайпом")
    fun swipeToRefresh(): NewsPage {
        // Ищем RecyclerView или любой контейнер для свайпа
        Espresso.onView(ViewMatchers.withId(R.id.news_list_recycler_view))
            .perform(ViewActions.swipeDown())
        return this
    }

    @Step("Вернуться на главный экран")
    fun navigateBack(): MainPage {
        Espresso.pressBack()
        return MainPage()
    }
}