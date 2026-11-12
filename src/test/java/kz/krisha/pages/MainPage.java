package kz.krisha.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import kz.krisha.pages.components.HeaderComponent;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.title;

public class MainPage {

    public HeaderComponent header = new HeaderComponent();

    private final SelenideElement searchForm = $("#search-form");
    private final SelenideElement dealTypeSelect = $("#search-form select.category-type");
    private final SelenideElement roomsSelect = $("#search-form select[name='das[live.rooms]']:not([disabled])");
    private final SelenideElement searchButton = $("#search-form .search-btn-main:not(.map-search)");

    @Step("Открываем главную страницу")
    public MainPage openPage() {
        open("/");
        return this;
    }

    @Step("Проверяем заголовок страницы {value}")
    public MainPage checkPageTitle(String value) {
        webdriver().shouldHave(title(value));
        return this;
    }

    @Step("Проверяем, что форма поиска отображается")
    public MainPage checkSearchForm() {
        searchForm.shouldBe(visible);
        return this;
    }

    @Step("Выбираем тип сделки {dealType}")
    public MainPage selectDealType(String dealType) {
        dealTypeSelect.selectOption(dealType);
        return this;
    }

    @Step("Выбираем количество комнат {rooms}")
    public MainPage selectRooms(String rooms) {
        roomsSelect.selectOption(rooms);
        return this;
    }

    @Step("Нажимаем кнопку Найти")
    public void clickSearch() {
        searchButton.click();
    }
}
