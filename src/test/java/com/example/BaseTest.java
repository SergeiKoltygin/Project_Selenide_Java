package com.example;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.example.utils.CustomAllureSelenide;

/**
 * Базовый класс для всех тестов.
 * Настроен для использования Selenide с браузером Chrome.
 * Добавляет Allure отчетность для тестов.
 *
 * @author Колтыгин Сергей
 */
public class BaseTest {

    @BeforeAll
    public static void setup(){
        SelenideLogger.addListener("AllureSelenide",new CustomAllureSelenide().screenshots(true).savePageSource(true));
    }

    /**
     * Метод настройки конфигурации перед каждым тестом.
     * Устанавливает браузер, таймауты и добавляет нужные аргументы для запуска Chrome.
     * Также настраивает Allure для скриншотов и сохранения исходников страниц.
     */
    @BeforeEach
    public void setUp() {

        Configuration.browser = "chrome";

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, options);

    }
}

