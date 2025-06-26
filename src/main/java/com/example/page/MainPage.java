package com.example.page;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.SelenideElement;


/**
 * Страница главной страницы с методами для взаимодействия с элементами на странице.
 * Использует chain-style вызовы методов.
 *
 * @author Колтыгин Сергей
 */

public class MainPage {
    private final SelenideElement catalogMenu = $x("//span[text() = 'Каталог']");
    private final SelenideElement electronics = $x("//div[@data-zone-name='catalog-content']//li[contains(., 'Электроника')]");
    private final SelenideElement smartphones = $x("//a[text()='Смартфоны']");

    /**
     * Нажимает на кнопку "Каталог".
     *
     * @return текущий объект MainPage для цепочки вызовов
     */
    public MainPage clickCatalogButton() {
        catalogMenu.shouldBe(visible).click();
        return this;
    }

    /**
     * Наводит курсор на раздел "Электроника".
     *
     * @return текущий объект MainPage для цепочки вызовов
     */
    public MainPage hoverOnElectronics() {
        electronics.shouldBe(visible).hover();
        return this;
    }

    /**
     * Кликает по ссылке "Смартфоны".
     *
     * @return текущий объект MainPage для цепочки вызовов
     */
    public MainPage clickSmartphones() {
        smartphones.shouldBe(visible).click();
        return this;
    }

    /**
     * Проверяет, что открыта страница "Смартфоны".
     *
     * @return текущий объект MainPage для цепочки вызовов
     */
    public MainPage shouldBeOnSmartphonesPage() {
        $x("//h1[contains(text(), 'Смартфоны')]").shouldBe(visible).shouldHave(text("Смартфоны"));
        return this;
    }
}
