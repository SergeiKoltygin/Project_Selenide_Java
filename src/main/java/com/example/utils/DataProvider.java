package com.example.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

/**
 * Класс-поставщик тестовых данных для параметризованных тестов.
 * Содержит методы для фильтрации товаров и поиска.
 *
 * @author Колтыгин Сергей
 */
public class DataProvider {

    /**
     * Метод для предоставления тестовых данных для фильтрации товаров.
     * Возвращает поток аргументов для теста.
     *
     * @return поток аргументов: производители.
     */
    public static Stream<Arguments> productFilterDataProvider() {
        return Stream.of(
                Arguments.of("Apple", "iPhone")
        );
    }

}

