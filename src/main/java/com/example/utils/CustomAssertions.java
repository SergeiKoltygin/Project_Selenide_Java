package com.example.utils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomAssertions {

    /**
     * Проверяет, что каждый заголовок содержит указанное ключевое слово (без учёта регистра).
     *
     * @param titles список заголовков товаров
     * @param keyword ключевое слово, которое должно присутствовать в каждом заголовке
     */
    public static void assertAllTitlesContainKeyword(List<String> titles, String keyword) {
        for (String title : titles) {
            assertTrue(title.toLowerCase().contains(keyword.toLowerCase()),
                    "Название товара не содержит ожидаемое слово: '" + keyword + "' в '" + title + "'");
        }
    }

}

