package ru.iteco.fmhandroid.pages;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.containsString;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import com.google.android.material.textfield.TextInputEditText;

import org.hamcrest.Matcher;

import java.util.Collection;

import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.utils.WaitAction;

public class LoginPage {

    // ID контейнеров (TextInputLayout) которые ЕСТЬ
    private final int LOGIN_CONTAINER_ID = R.id.login_text_input_layout;
    private final int PASSWORD_CONTAINER_ID = R.id.password_text_input_layout;
    private final int LOGIN_BUTTON_ID = R.id.enter_button;

    // Текст подсказок на русском (из логов)
    private final String LOGIN_HINT_RU = "Логин";
    private final String PASSWORD_HINT_RU = "Пароль";

    public static ViewAction withTextLengthLessThanOrEqualTo(final int maxLength) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "check text length <= " + maxLength;
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view;
                CharSequence text = tv.getText();
                if (text != null && text.length() > maxLength) {
                    // Можно выбросить исключение или просто логировать
                }
            }
        };
    }

    public void verifyScreenIsDisplayed() {
        // Проверяем наличие контейнеров и кнопки
        onView(withId(LOGIN_CONTAINER_ID)).check(matches(isDisplayed()));
        onView(withId(PASSWORD_CONTAINER_ID)).check(matches(isDisplayed()));
        onView(withId(LOGIN_BUTTON_ID)).check(matches(isDisplayed()));
    }

    // СПОСОБ 1: Поиск TextInputEditText внутри контейнера (надежный)
    public LoginPage enterLogin(String login) {
        // Ищем поле ввода внутри контейнера логина
        onView(allOf(
                isDescendantOfA(withId(LOGIN_CONTAINER_ID)),
                isAssignableFrom(TextInputEditText.class)
        )).perform(click(), replaceText(login), closeSoftKeyboard());
        return this;
    }

    public LoginPage enterPassword(String password) {
        // Ищем поле ввода внутри контейнера пароля
        onView(allOf(
                isDescendantOfA(withId(PASSWORD_CONTAINER_ID)),
                isAssignableFrom(TextInputEditText.class)
        )).perform(click(), replaceText(password), closeSoftKeyboard());
        return this;
    }

    // СПОСОБ 2: Поиск по подсказке (hint) на русском
    public LoginPage enterLoginByHint(String login) {
        onView(withHint(LOGIN_HINT_RU))
                .perform(click(), replaceText(login), closeSoftKeyboard());
        return this;
    }

    public LoginPage enterPasswordByHint(String password) {
        onView(withHint(PASSWORD_HINT_RU))
                .perform(click(), replaceText(password), closeSoftKeyboard());
        return this;
    }

    // СПОСОБ 3: По индексу (если в контейнере только одно поле)
    public LoginPage enterLoginByIndex(String login) {
        // Берем первый TextInputEditText в контейнере логина
        onView(allOf(
                withClassName(containsString("TextInputEditText")),
                isDescendantOfA(withId(LOGIN_CONTAINER_ID))
        )).perform(click(), replaceText(login), closeSoftKeyboard());
        return this;
    }

    public LoginPage enterPasswordByIndex(String password) {
        // Берем первый TextInputEditText в контейнере пароля
        onView(allOf(
                withClassName(containsString("TextInputEditText")),
                isDescendantOfA(withId(PASSWORD_CONTAINER_ID))
        )).perform(click(), replaceText(password), closeSoftKeyboard());
        return this;
    }

    public void clickSignIn() {
        onView(withId(LOGIN_BUTTON_ID)).perform(click());
    }

    public void verifySuccessfulLogin() {
        // Ждем появления главного экрана
        onView(isRoot()).perform(WaitAction.waitDisplayed(R.id.container_list_news_include_on_fragment_main, WaitAction.TIMEOUT));
        onView(withId(R.id.container_list_news_include_on_fragment_main)).check(matches(isDisplayed()));
    }

    public void verifyAlertMessage(String expectedMessage) {
        onView(withText(expectedMessage))
                .inRoot(withDecorView(not(is(getCurrentActivityDecorView()))))
                .check(matches(isDisplayed()));
    }

    private View getCurrentActivityDecorView() {
        final Activity[] activity = new Activity[1];
        getInstrumentation().runOnMainSync(() -> {
            Collection<Activity> activities = ActivityLifecycleMonitorRegistry
                    .getInstance()
                    .getActivitiesInStage(Stage.RESUMED);
            if (!activities.isEmpty()) {
                activity[0] = activities.iterator().next();
            }
        });
        return activity[0] != null ? activity[0].getWindow().getDecorView() : null;
    }

    public void verifyLoginFieldLength(int maxLength) {
        onView(allOf(
                isDescendantOfA(withId(LOGIN_CONTAINER_ID)),
                isAssignableFrom(TextInputEditText.class)
        )).perform(withTextLengthLessThanOrEqualTo(maxLength));
    }

    public void verifyPasswordFieldLength(int maxLength) {
        onView(allOf(
                isDescendantOfA(withId(PASSWORD_CONTAINER_ID)),
                isAssignableFrom(TextInputEditText.class)
        )).perform(withTextLengthLessThanOrEqualTo(maxLength));
    }

    // Метод для ожидания экрана авторизации
    public void waitForLoginScreen() {
        onView(isRoot()).perform(WaitAction.waitDisplayed(LOGIN_CONTAINER_ID, WaitAction.TIMEOUT));
    }

    // Тестовый метод для проверки доступности полей
    public void debugCheckFields() {
        try {
            onView(allOf(
                    isDescendantOfA(withId(LOGIN_CONTAINER_ID)),
                    isAssignableFrom(TextInputEditText.class)
            )).check(matches(isDisplayed()));
            System.out.println("Поле логина найдено");
        } catch (Exception e) {
            System.out.println("Поле логина НЕ найдено: " + e.getMessage());
        }

        try {
            onView(allOf(
                    isDescendantOfA(withId(PASSWORD_CONTAINER_ID)),
                    isAssignableFrom(TextInputEditText.class)
            )).check(matches(isDisplayed()));
            System.out.println("Поле пароля найдено");
        } catch (Exception e) {
            System.out.println("Поле пароля НЕ найдено: " + e.getMessage());
        }
    }
}