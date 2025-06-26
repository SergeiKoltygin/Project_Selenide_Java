package com.example.utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.SelenideLog;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StatusDetails;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import io.qameta.allure.util.ResultsUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Кастомный класс-расширение {@link AllureSelenide}, обеспечивающий автоматическое
 * добавление скриншотов, HTML страницы и логов браузера после каждого действия Selenide.
 * <p>
 * Предназначен для улучшенной интеграции с Allure отчетами и полной трассировкой шагов.
 * Используется для автоматического снятия скриншотов, выгрузки исходного кода страницы
 * и логов браузера при любом взаимодействии с веб-страницей.
 * <p>
 *
 * @author Колтыгин Сергей
 */
public class CustomAllureSelenide extends AllureSelenide {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllureSelenide.class);
    private boolean saveScreenshots;
    private boolean savePageHtml;
    private boolean includeSelenideLocatorsSteps;
    private final Map<LogType, Level> logTypesToSave;
    private final AllureLifecycle lifecycle;

    /**
     * Конструктор по умолчанию. Использует текущий {@link AllureLifecycle}.
     */
    public CustomAllureSelenide() {
        this(Allure.getLifecycle());
    }

    /**
     * Конструктор с передачей экземпляра {@link AllureLifecycle}.
     *
     * @param lifecycle экземпляр AllureLifecycle
     */
    public CustomAllureSelenide(AllureLifecycle lifecycle) {
        this.saveScreenshots = true;
        this.savePageHtml = true;
        this.includeSelenideLocatorsSteps = true;
        this.logTypesToSave = new HashMap();
        this.lifecycle = lifecycle;
    }

    /**
     * Получение байтов скриншота текущего экрана.
     *
     * @return Optional массив байт PNG-скриншота
     */
    private static Optional<byte[]> getScreenshotBytes() {
        try {
            return WebDriverRunner.hasWebDriverStarted() ? Optional.of(((TakesScreenshot)WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES)) : Optional.empty();
        } catch (WebDriverException var1) {
            LOGGER.warn("Could not get screen shot", var1);
            return Optional.empty();
        }
    }

    /**
     * Получение байтов исходного HTML-кода текущей страницы.
     *
     * @return Optional массив байт HTML-кода
     */
    private static Optional<byte[]> getPageSourceBytes() {
        try {
            return WebDriverRunner.hasWebDriverStarted() ? Optional.of(WebDriverRunner.getWebDriver().getPageSource().getBytes(StandardCharsets.UTF_8)) : Optional.empty();
        } catch (WebDriverException var1) {
            LOGGER.warn("Could not get page source", var1);
            return Optional.empty();
        }
    }

    /**
     * Получение логов браузера по указанному типу и уровню логирования.
     *
     * @param logType тип логов браузера (например, BROWSER)
     * @param level уровень логирования (например, INFO)
     * @return строковое представление логов
     */
    private static String getBrowserLogs(LogType logType, Level level) {
        return String.join("\n\n", Selenide.getWebDriverLogs(logType.toString(), level));
    }

    /**
     * Обработка события после действия Selenide.
     * <ul>
     *     <li>Добавляет скриншоты</li>
     *     <li>Добавляет исходный HTML</li>
     *     <li>Добавляет логи браузера</li>
     *     <li>Завершает шаг в Allure со статусом</li>
     * </ul>
     *
     * @param event объект события логирования
     */
    @Override
    public void afterEvent(LogEvent event) {
        this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
            if (this.saveScreenshots) {
                getScreenshotBytes().ifPresent((bytes) -> {
                    this.lifecycle.addAttachment("Screenshot", "image/png", "png", bytes);
                });
            }

            if (this.savePageHtml) {
                getPageSourceBytes().ifPresent((bytes) -> {
                    this.lifecycle.addAttachment("Page source", "text/html", "html", bytes);
                });
            }

            if (!this.logTypesToSave.isEmpty()) {
                this.logTypesToSave.forEach((logType, level) -> {
                    byte[] content = getBrowserLogs(logType, level).getBytes(StandardCharsets.UTF_8);
                    this.lifecycle.addAttachment("Logs from: " + logType, "application/json", ".txt", content);
                });
            }

        });


        if (this.stepsShouldBeLogged(event)) {
            this.lifecycle.getCurrentTestCaseOrStep().ifPresent((parentUuid) -> {
                switch(event.getStatus()) {
                    case PASS:
                        this.lifecycle.updateStep((step) -> {
                            step.setStatus(Status.PASSED);
                        });
                        break;
                    case FAIL:
                        this.lifecycle.updateStep((stepResult) -> {
                            stepResult.setStatus((Status) ResultsUtils.getStatus(event.getError()).orElse(Status.BROKEN));
                            stepResult.setStatusDetails((StatusDetails)ResultsUtils.getStatusDetails(event.getError()).orElse(new StatusDetails()));
                        });
                        break;
                    default:
                        LOGGER.warn("Step finished with unsupported status {}", event.getStatus());
                }

                this.lifecycle.stopStep();
            });
        }

    }

    /**
     * Проверяет, следует ли логировать шаги.
     *
     * @param event событие Selenide
     * @return true, если шаги нужно логировать
     */
    private boolean stepsShouldBeLogged(LogEvent event) {
        return this.includeSelenideLocatorsSteps || !(event instanceof SelenideLog);
    }
}

