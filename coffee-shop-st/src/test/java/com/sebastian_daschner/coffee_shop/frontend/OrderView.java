package com.sebastian_daschner.coffee_shop.frontend;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.actions;

public class OrderView {

    private final SelenideElement typeSelect = $("select[name=type]");
    private final SelenideElement originSelect = $("select[name=origin]");
    private final SelenideElement submitButton = $("button[type=submit]");

    public String getPageHeader() {
        return $("body > h1").text();
    }

    public IndexView orderCoffee(String type, String origin) {
        typeSelect.selectOptionContainingText(type);
        originSelect.shouldBe(enabled);
        originSelect.selectOptionContainingText(origin);
        submitButton.click();
        return new IndexView();
    }

    public IndexView orderCoffeeSelectWithKeyboard(String type, String origin) {
        selectWithKeyboard(type, typeSelect);
        originSelect.shouldBe(enabled);
        selectWithKeyboard(origin, originSelect);

        actions()
            .sendKeys(Keys.TAB)
            .sendKeys(Keys.ENTER)
            .perform();

        return new IndexView();
    }

    public void selectWithKeyboard(String type, SelenideElement select) {
        while (!type.equals(select.getSelectedText()))
            select.getWrappedElement().sendKeys(Keys.ARROW_DOWN);
    }
}
