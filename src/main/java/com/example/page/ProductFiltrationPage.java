package com.example.page;

import com.codeborne.selenide.*;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * Страница для фильтрации товаров.
 * Использует chain-style вызовы методов.
 *
 * @author Колтыгин Сергей
 */
public class ProductFiltrationPage {
    private final SelenideElement showAllManufacturers = $x("//span[text()='Показать всё']");
    private final SelenideElement manufacturerSearchInput = $x("//input[@placeholder='Найти']");
    private final ElementsCollection titles = $$x("//div[@data-auto-themename='listDetailed']//div[@data-baobab-name='title']");

    /**
     * Устанавливает фильтр по производителю.
     *
     * @param brand название бренда (например, "Apple")
     * @return текущий объект ProductFiltrationPage для цепочки вызовов
     */
    public ProductFiltrationPage setBrand(String brand) {
        showAllManufacturers.shouldBe(visible).click();
        manufacturerSearchInput.setValue(brand);
        $x("//span[text()='" + brand + "']/preceding-sibling::*").shouldBe(visible).click();
        return this;
    }

    /**
     * Ожидает загрузку результатов после применения фильтра.
     *
     * @return текущий объект ProductFiltrationPage для цепочки вызовов
     */
    public ProductFiltrationPage waitForResultsToLoad() {
        titles.shouldBe(CollectionCondition.sizeGreaterThan(0));
        SelenideElement loader = $x("//div[contains(@data-auto,'preloader')]");
        loader.shouldNot(Condition.visible);
        return this;
    }

    /**
     * Прокручивает страницу, загружая все доступные товары.
     *
     * @return текущий объект ProductFiltrationPage для цепочки вызовов
     */
    public ProductFiltrationPage loadAllProducts() {
        int lastCount = 0;
        int sameCount = 0;

        while (sameCount < 3) {
            if (titles.isEmpty()) {
                sleep(2000);
                continue;
            }

            titles.last().scrollIntoView(true);
            sleep(2000);

            int currentCount = titles.size();
            if (currentCount == lastCount) {
                sameCount++;
            } else {
                sameCount = 0;
                lastCount = currentCount;
            }
        }
        return this;
    }

    /**
     * Получает список названий всех отображаемых товаров.
     *
     * @return список названий товаров
     */
    public List<String> getAllTitles() {
        return titles.texts();
    }

}


