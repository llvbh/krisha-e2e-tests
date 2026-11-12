package kz.krisha.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.allMatch;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class SearchResultsPage {

    private final SelenideElement title = $("h1");
    private final SelenideElement resultsCount = $(".search-results-nb");
    private final ElementsCollection cards = $$(".a-card .a-card__title");
    private final ElementsCollection simpleCards = $$(".a-card:not([data-is-layout='1']) .a-card__title");
    private final ElementsCollection pages = $$(".paginator .paginator__btn");
    private final ElementsCollection prices = $$(".a-card:not([data-is-layout='1']) .a-card__price");
    private final SelenideElement maxPriceInput = $("[name='das[price][to]']");
    private final SelenideElement minSquareInput = $("[name='das[live.square][from]']");
    private final SelenideElement showResultsButton = $(".search-btn-main");
    private final ElementsCollection sortButtons = $$(".sort-option-selector-unverified");
    private final ElementsCollection breadcrumbs = $$(".breadcrumbs-top .breadcrumbs__link");

    @Step("Вводим максимальную цену {price}")
    public SearchResultsPage setMaxPrice(String price) {
        maxPriceInput.setValue(price);
        return this;
    }

    @Step("Вводим минимальную площадь {square}")
    public SearchResultsPage setMinSquare(String square) {
        minSquareInput.setValue(square);
        return this;
    }

    @Step("Нажимаем кнопку Показать результаты")
    public SearchResultsPage clickShowResults() {
        showResultsButton.click();
        return this;
    }

    @Step("Получаем цены объявлений")
    public List<Long> getPrices() {
        prices.shouldHave(sizeGreaterThan(0));
        List<Long> result = new ArrayList<>();
        for (String price : prices.texts()) {
            String number = price.replaceAll("[^0-9]", "");
            if (!number.isEmpty()) {
                result.add(Long.parseLong(number));
            }
        }
        return result;
    }

    @Step("Выбираем сортировку {sort}")
    public SearchResultsPage clickSort(String sort) {
        sortButtons.findBy(text(sort)).click();
        return this;
    }

    @Step("Проверяем, что выбрана сортировка {sort}")
    public SearchResultsPage checkActiveSort(String sort) {
        sortButtons.findBy(cssClass("search-tab-active")).shouldHave(text(sort));
        return this;
    }

    @Step("Проверяем, что в хлебных крошках есть {item}")
    public SearchResultsPage checkBreadcrumb(String item) {
        breadcrumbs.findBy(text(item)).shouldBe(visible);
        return this;
    }

    @Step("Нажимаем на {item} в хлебных крошках")
    public void clickBreadcrumb(String item) {
        breadcrumbs.findBy(text(item)).click();
    }

    @Step("Открываем страницу {url}")
    public SearchResultsPage openPage(String url) {
        open(url);
        return this;
    }

    @Step("Проверяем заголовок {value}")
    public SearchResultsPage checkTitle(String value) {
        title.shouldHave(text(value));
        return this;
    }

    @Step("Проверяем, что адрес страницы содержит {value}")
    public SearchResultsPage checkUrl(String value) {
        webdriver().shouldHave(urlContaining(value));
        return this;
    }

    @Step("Проверяем, что объявления найдены")
    public SearchResultsPage checkResults() {
        resultsCount.shouldBe(visible).shouldNotBe(empty);
        cards.shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("Проверяем, что все объявления содержат {value}")
    public SearchResultsPage checkAllCards(String value) {
        simpleCards.shouldHave(sizeGreaterThan(0));
        simpleCards.shouldHave(allMatch("Все объявления содержат " + value, card -> card.getText().contains(value)));
        return this;
    }

    @Step("Переходим на страницу {number}")
    public SearchResultsPage goToPage(String number) {
        pages.findBy(text(number)).click();
        return this;
    }

    @Step("Проверяем, что открыта страница {number}")
    public SearchResultsPage checkActivePage(String number) {
        pages.findBy(cssClass("paginator__btn--active")).shouldHave(text(number));
        return this;
    }
}
