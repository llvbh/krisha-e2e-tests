package kz.krisha.tests;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import kz.krisha.pages.MainPage;
import kz.krisha.pages.SearchResultsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Selenide.title;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Owner("llvbh")
@Tag("smoke")
@DisplayName("Главная страница")
public class MainPageTests extends TestBase {

    MainPage mainPage = new MainPage();
    SearchResultsPage searchResultsPage = new SearchResultsPage();

    @Test
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Главная страница открывается")
    void mainPageTest() {
        mainPage.openPage();
        assertTrue(title().contains("Крыша"));
        mainPage.header.checkLogo();
        mainPage.checkSearchForm();
    }

    @ParameterizedTest(name = "В главном меню есть пункт {0}")
    @ValueSource(strings = {"Продажа", "Аренда", "Новостройки", "Оценка", "Новости"})
    @Severity(SeverityLevel.NORMAL)
    void mainMenuTest(String item) {
        mainPage.openPage();
        mainPage.header.checkMainMenuItem(item);
    }

    @ParameterizedTest(name = "В верхнем меню есть пункт {0}")
    @ValueSource(strings = {"Избранное", "Регистрация", "Личный кабинет"})
    @Severity(SeverityLevel.MINOR)
    void topMenuTest(String item) {
        mainPage.openPage();
        mainPage.header.checkTopMenuItem(item);
    }

    @ParameterizedTest(name = "Пункт меню {0} открывает страницу {1}")
    @CsvSource({
            "Продажа, /prodazha/",
            "Аренда, /arenda/"
    })
    @Severity(SeverityLevel.CRITICAL)
    void menuNavigationTest(String item, String url) {
        mainPage.openPage();
        mainPage.header.clickMainMenuItem(item);
        searchResultsPage.checkUrl(url);
    }
}
