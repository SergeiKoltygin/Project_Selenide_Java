package com.example;

import org.junit.jupiter.api.Test;
import com.example.steps.MainPageSteps;
import com.example.utils.Properties;

import static com.codeborne.selenide.Selenide.open;

/**
 * Тест для проверки навигации по главной странице.
 * Проверяет, что переход на страницу 'Смартфоны' работает корректно.
 *
 * @author Колтыгин Сергей
 */
public class MainPageTest extends BaseTest {

    /**
     * Тестовый метод для проверки навигации на страницу 'Смартфоны'.
     * Ожидает, что при переходе по шагам, страница 'Смартфоны' откроется корректно.
     */
    @Test
    public void testNavigationToSmartphones() {
        open(Properties.testsProperties.marketYandexUrl());
        MainPageSteps mainPageSteps = new MainPageSteps();
        mainPageSteps.openCatalog();
        mainPageSteps.hoverOverElectronics();
        mainPageSteps.goToSmartphones();
        mainPageSteps.verifySmartphonesPageOpened();
    }

}

