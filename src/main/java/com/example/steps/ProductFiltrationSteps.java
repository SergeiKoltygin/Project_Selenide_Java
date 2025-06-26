package com.example.steps;

import io.qameta.allure.Step;
import com.example.page.ProductFiltrationPage;
import com.example.utils.CustomAssertions;

import java.util.List;

/**
 * Класс шагов для взаимодействия с фильтрацией товаров на странице каталога.
 * Позволяет установить фильтр по бренду и проверить соответствие всех найденных товаров.
 * Использует Page Object {@link ProductFiltrationPage} и кастомные ассерты {@link CustomAssertions}.
 *
 * @author Колтыгин Сергей
 */
public class ProductFiltrationSteps {


    private final ProductFiltrationPage filtrationPage = new ProductFiltrationPage();

    /**
     * Применяет фильтр по бренду на странице каталога.
     *
     * @param brand название бренда, по которому нужно фильтровать (например, "Apple").
     */
    @Step("Фильтруем смартфоны по бренду: {brand}")
    public void filterByBrand(String brand) {
        filtrationPage
                .setBrand(brand)
                .waitForResultsToLoad()
                .loadAllProducts();
    }

    /**
     * Проверяет, что все найденные товары содержат указанное слово в названии.
     *
     * @param expected ожидаемое слово, которое должно присутствовать в названии каждого товара (например, "iPhone").
     */
    @Step("Проверяем, что все товары содержат в названии: {expected}")
    public void verifyOnlyProductsFromBrand(String expected) {
        List<String> titles = filtrationPage.getAllTitles();
        CustomAssertions.assertAllTitlesContainKeyword(titles, expected);
    }
}


