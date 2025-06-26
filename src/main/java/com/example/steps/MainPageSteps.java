package com.example.steps;

import io.qameta.allure.Step;
import com.example.page.MainPage;

/**
 * Класс шагов для взаимодействия с главной страницей Яндекс Маркета.
 * Реализует действия пользователя: открытие каталога, переход в раздел "Электроника" и "Смартфоны".
 * Использует Page Object {@link MainPage}.
 *
 * @author Колтыгин Сергей
 */
public class MainPageSteps {
    private final MainPage mainPage = new MainPage();

    /**
     * Нажимает на кнопку "Каталог".
     */
    @Step("Нажимаем на кнопку 'Каталог'")
    public void openCatalog() {
        mainPage.clickCatalogButton();
    }

    /**
     * Наводит курсор на раздел "Электроника".
     */
    @Step("Наводим курсор на раздел 'Электроника'")
    public void hoverOverElectronics() {
        mainPage.hoverOnElectronics();
    }

    /**
     * Переходит в подраздел "Смартфоны".
     */
    @Step("Переходим в раздел 'Смартфоны'")
    public void goToSmartphones() {
        mainPage.clickSmartphones();
    }

    /**
     * Проверяет, что открыта страница "Смартфоны".
     */
    @Step("Проверяем, что открыта страница 'Смартфоны'")
    public void verifySmartphonesPageOpened() {
        mainPage.shouldBeOnSmartphonesPage();
    }
}

