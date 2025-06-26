package com.example.utils;

import org.aeonbits.owner.ConfigFactory;

/**
 * Класс-обертка для доступа к конфигурационным свойствам тестов.
 * Использует библиотеку OWNER для загрузки настроек из файла, системных свойств и переменных окружения.
 *
 * @author Колтыгин Сергей
 */
public class Properties {

    /**
     * Экземпляр интерфейса {@link TestsProperties}, содержащий настройки тестов.
     */
    public static TestsProperties testsProperties = ConfigFactory.create(TestsProperties.class);
}