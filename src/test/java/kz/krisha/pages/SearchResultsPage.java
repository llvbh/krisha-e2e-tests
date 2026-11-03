package kz.krisha.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.cssClass;
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
        resultsCount.shouldBe(visible).shouldHave(text("Найдено"));
        cards.shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("Проверяем, что все объявления содержат {value}")
    public SearchResultsPage checkAllCards(String value) {
        simpleCards.shouldHave(sizeGreaterThan(0));
        for (SelenideElement card : simpleCards) {
            card.shouldHave(text(value));
        }
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
