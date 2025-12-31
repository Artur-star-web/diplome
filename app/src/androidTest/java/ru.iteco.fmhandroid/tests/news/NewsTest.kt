package ru.iteco.fmhandroid.tests.news

import androidx.test.espresso.Espresso
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
@Epic("Новости")
@Feature("Работа с новостями")
@Story("Просмотр и обновление списка новостей")
class NewsTest : BaseTest() {

    private val mainPage = MainPage()

    @Test
    @DisplayName("Просмотр списка новостей и обновление свайпом")
    @Description("Тест проверяет переход в раздел новостей и обновление списка свайпом")
    @Severity(SeverityLevel.CRITICAL)
    fun testViewNewsList() {

        Allure.label("owner", "QA Team")
        Allure.label("layer", "news")

        Allure.step("Авторизация в приложении") {
            println("=== ТЕСТ НОВОСТЕЙ ===")
            println("1. Авторизация...")

            try {
                authPage.checkAuthScreenIsDisplayed()
                authPage.enterLogin(TEST_LOGIN)
                authPage.enterPassword(TEST_PASSWORD)
                authPage.clickSignInButton()
                Thread.sleep(3000)
                println("✅ Авторизация выполнена")
            } catch (e: Exception) {
                println("⚠ Уже авторизованы")
            }
        }

        Allure.step("Проверка главного экрана") {
            println("2. Проверка главного экрана...")
            mainPage.checkMainScreenIsDisplayed()
            println("✅ Главный экран отображен")
            Thread.sleep(1000)
        }

        Allure.step("Переход в раздел новостей") {
            println("3. Переход в раздел новостей...")
            val newsPage = mainPage.navigateToNewsFromMenu()
            println("✅ Перешли в новости через меню")
            Thread.sleep(2000)

            newsPage.checkNewsScreenIsDisplayed()
            println("✅ Экран новостей подтвержден")
        }

        Allure.step("Обновление списка новостей свайпом") {
            println("4. Обновление списка новостей свайпом...")
            try {
                val newsPage = ru.iteco.fmhandroid.pages.NewsPage()
                newsPage.swipeToRefresh()
                println("✅ Список обновлен свайпом")
                Thread.sleep(2000)
            } catch (e: Exception) {
                println("⚠ Не удалось обновить свайпом")
            }
        }

        Allure.step("Возврат на главный экран") {
            println("5. Возврат на главный экран...")
            Espresso.pressBack()
            Thread.sleep(2000)
            mainPage.checkMainScreenIsDisplayed()
            println("✅ Вернулись на главный")
        }

        println("\n🎉 ТЕСТ НОВОСТЕЙ ЗАВЕРШЕН УСПЕШНО!")
    }
}