package com.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import com.example.steps.MainPageSteps;
import com.example.steps.ProductFiltrationSteps;
import com.example.utils.Properties;


import static com.codeborne.selenide.Selenide.open;

/**
 * Тест для фильтрации смартфонов по бренду на сайте Яндекс Маркет.
 * Тест проверяет, что все товары на странице соответствуют выбранному бренду.
 * <p>
 * Используется параметризация для проверки различных брендов.
 * Каждый бренд проверяется путем фильтрации товаров и проверки, что все товары принадлежат выбранному бренду.
 * <p>
 * Параметризация теста осуществляется с помощью {@link MethodSource}.
 *
 * @author Колтыгин Сергей
 */
public class ProductFiltrationTest extends BaseTest {

    /**
     * Тест для фильтрации смартфонов по бренду.
     * Фильтрует смартфоны по бренду и проверяет, что все товары соответствуют выбранному бренду.
     *
     * @param brand название бренда для фильтрации
     */
    @ParameterizedTest
    @MethodSource("com.example.utils.DataProvider#productFilterDataProvider")
    public void testSmartphonesFilterByBrand(String brand,String expectedInTitle) {
        open(Properties.testsProperties.marketYandexUrl());

        MainPageSteps mainPageSteps = new MainPageSteps();
        mainPageSteps.openCatalog();
        mainPageSteps.hoverOverElectronics();
        mainPageSteps.goToSmartphones();
        mainPageSteps.verifySmartphonesPageOpened();

        ProductFiltrationSteps filtrationSteps = new ProductFiltrationSteps();
        filtrationSteps.filterByBrand(brand);
        filtrationSteps.verifyOnlyProductsFromBrand(expectedInTitle);
    }
}


