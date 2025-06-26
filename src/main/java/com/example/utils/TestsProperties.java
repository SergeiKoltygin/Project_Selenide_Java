package com.example.utils;

import org.aeonbits.owner.Config;

/**
 * Интерфейс конфигурации, содержащий ключи и методы доступа к настройкам тестов.
 * Настройки загружаются из системных свойств, переменных окружения и файла tests.properties.
 *
 * @author Колтыгин Сергей
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties",
        "system:env",
        "file:src/main/resources/tests.properties"
})
public interface TestsProperties extends Config {

    /**
     * URL страницы Яндекс Маркета, загружаемый из конфигурации.
     *
     * @return строка с адресом страницы
     */
    @Config.Key("marketYandex.url")
    String marketYandexUrl();

}

