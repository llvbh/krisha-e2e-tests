package kz.krisha.pages.components;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class HeaderComponent {

    private final SelenideElement logo = $(".secondary-navbar .logo");
    private final ElementsCollection mainMenu = $$(".main-menu a");
    private final ElementsCollection topMenu = $$(".navbar-menu a");

    @Step("Проверяем, что логотип отображается")
    public void checkLogo() {
        logo.shouldBe(visible);
    }

    @Step("Проверяем, что в главном меню есть пункт {item}")
    public void checkMainMenuItem(String item) {
        mainMenu.findBy(text(item)).shouldBe(visible);
    }

    @Step("Проверяем, что в верхнем меню есть пункт {item}")
    public void checkTopMenuItem(String item) {
        topMenu.findBy(text(item)).shouldBe(visible);
    }

    @Step("Нажимаем на пункт меню {item}")
    public void clickMainMenuItem(String item) {
        mainMenu.findBy(text(item)).click();
    }
}
