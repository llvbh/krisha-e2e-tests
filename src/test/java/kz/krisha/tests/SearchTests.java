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

@Owner("llvbh")
@Tag("search")
@DisplayName("Поиск объявлений")
public class SearchTests extends TestBase {

    MainPage mainPage = new MainPage();
    SearchResultsPage searchResultsPage = new SearchResultsPage();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Поиск двухкомнатных квартир на продажу")
    void searchFlatsForSaleTest() {
        mainPage.openPage()
                .selectDealType("Купить")
                .selectRooms("2 - комн.")
                .clickSearch();

        searchResultsPage.checkUrl("das[live.rooms]=2")
                .checkTitle("двухкомнатных квартир")
                .checkResults()
                .checkAllCards("2-комнатная");
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Поиск однокомнатных квартир в аренду")
    void searchFlatsForRentTest() {
        mainPage.openPage()
                .selectDealType("Арендовать")
                .selectRooms("1 - комн.")
                .clickSearch();

        searchResultsPage.checkUrl("/arenda/kvartiry/")
                .checkTitle("Аренда")
                .checkResults()
                .checkAllCards("1-комнатная");
    }

    @ParameterizedTest(name = "Страница {0} открывается и на ней есть объявления")
    @CsvSource({
            "/prodazha/kvartiry/almaty/, Продажа квартир в Алматы",
            "/arenda/kvartiry/astana/, Аренда квартиры помесячно в Астане",
            "/prodazha/doma-dachi/shymkent/, Шымкент"
    })
    @Severity(SeverityLevel.CRITICAL)
    void searchResultsTest(String url, String title) {
        searchResultsPage.openPage(url)
                .checkTitle(title)
                .checkResults();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Переход на вторую страницу с объявлениями")
    void paginationTest() {
        searchResultsPage.openPage("/prodazha/kvartiry/almaty/")
                .checkActivePage("1")
                .goToPage("2")
                .checkActivePage("2")
                .checkResults();
    }
}
