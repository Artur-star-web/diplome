package ru.iteco.fmhandroid.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.WaitAction;

public class MainPage {

    public static void logout() {
        onView(withId(R.id.authorization_image_button)).perform(click());
        // ИЗМЕНИТЬ: "Log out" на русский текст
        onView(withText("Выйти")).perform(click());  // Было: "Log out"
    }

    public void verifyScreenIsDisplayed() {
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.container_list_news_include_on_fragment_main, WaitAction.TIMEOUT));
    }

    public void checkNavigationMenu() {
        onView(withId(R.id.main_menu_image_button)).perform(click());
        // ИЗМЕНИТЬ: "About" на русский текст
        onView(withText("О приложении")).check(matches(isDisplayed()));  // Было: "About"
    }

    public NewsPage goToNewsPage() {
        onView(withId(R.id.main_menu_image_button)).perform(click());
        // ИЗМЕНИТЬ: "News" на русский текст
        onView(withText("Новости")).perform(click());  // Было: "News"

        return new NewsPage();
    }

    public AboutPage goToAboutPage() {
        onView(withId(R.id.main_menu_image_button)).perform(click());
        // ИЗМЕНИТЬ: "About" на русский текст
        onView(withText("О приложении")).perform(click());  // Было: "About"

        return new AboutPage();
    }

    public void scrollPage() {
        onView(isRoot()).perform(swipeDown());
    }

    public void refreshPage() {
        onView(isRoot()).perform(swipeDown());
    }

    public QuotesPage goToQuotesPage() {
        onView(withId(R.id.our_mission_image_button)).perform(click());
        return new QuotesPage();
    }

    public void collapseExpandNews() {
        onView(withId(R.id.expand_material_button)).perform(click());
    }

    public void goToAllNews() {
        onView(withId(R.id.all_news_text_view)).perform(click());
    }

    // ДОБАВИТЬ: Метод для безопасного логаута (с обработкой ошибок)
    public static void safeLogout() {
        try {
            // Сначала проверяем, видна ли кнопка авторизации
            onView(withId(R.id.authorization_image_button)).check(matches(isDisplayed()));
            logout();
        } catch (Exception e) {
            System.out.println("Не удалось выполнить logout: " + e.getMessage());
            // Если не удалось, возможно мы уже на экране авторизации
        }
    }

    // ДОБАВИТЬ: Метод проверки, что мы на главном экране
    public boolean isMainScreenDisplayed() {
        try {
            onView(withId(R.id.container_list_news_include_on_fragment_main)).check(matches(isDisplayed()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ДОБАВИТЬ: Метод для ожидания главного экрана
    public void waitForMainScreen() {
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.container_list_news_include_on_fragment_main, WaitAction.TIMEOUT));
    }
}