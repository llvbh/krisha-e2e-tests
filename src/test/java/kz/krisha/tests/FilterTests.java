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
@DisplayName("Фильтры и сортировка")
public class FilterTests extends TestBase {

    MainPage mainPage = new MainPage();
    SearchResultsPage searchResultsPage = new SearchResultsPage();

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Фильтр по максимальной цене")
    void maxPriceFilterTest() {
        searchResultsPage.openPage("/prodazha/kvartiry/almaty/")
                .setMaxPrice("30000000")
                .clickShowResults()
                .checkUrl("das[price][to]=30000000")
                .checkResults()
                .checkAllPricesNotMore(30000000);
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Фильтр по минимальной площади")
    void minSquareFilterTest() {
        searchResultsPage.openPage("/prodazha/kvartiry/almaty/")
                .setMinSquare("100")
                .clickShowResults()
                .checkUrl("das[live.square][from]=100")
                .checkResults();
    }

    @ParameterizedTest(name = "Сортировка {0}")
    @CsvSource({
            "Дешевые, sort_by=price-asc",
            "Дорогие, sort_by=price-desc"
    })
    @Severity(SeverityLevel.NORMAL)
    void sortTest(String sort, String url) {
        searchResultsPage.openPage("/prodazha/kvartiry/almaty/")
                .clickSort(sort)
                .checkUrl(url)
                .checkActiveSort(sort)
                .checkResults();
    }

    @Test
    @Severity(SeverityLevel.MINOR)
    @DisplayName("Переход на главную страницу по хлебным крошкам")
    void breadcrumbsTest() {
        searchResultsPage.openPage("/prodazha/kvartiry/almaty/")
                .checkBreadcrumb("Продажа квартир")
                .clickBreadcrumb("Крыша");

        mainPage.checkSearchForm();
    }
}
