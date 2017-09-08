package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import java.util.HashMap;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Александр on 08.09.2017.
 */
public class PaymentProviderPage extends MainPage {

    public PaymentProviderPage toPayForm() {
        $$x("//span[@class='ui-menu-second__title']").get(1).click();
        return page(PaymentProviderPage.class);
    }

    public void validateField(String fieldName, HashMap<String, String> validator) {
        if (fieldName.toLowerCase().contains("сумма")) validateAmountField(fieldName, validator);
        else validateUniqField(fieldName, validator);
    }

    private void validateUniqField(String fieldName, HashMap<String, String> validator) {
        SelenideElement field = $x("//span[text()='" + fieldName + "']/preceding-sibling::input");
        validator.forEach((key, value) -> {
            field.setValue(key).pressEnter();
            $x("//span[text()='" + fieldName + "']/following::div" +
                    "[contains(@class, 'ui-form-field-error-message')][1]").shouldBe(Condition.text(value));
        });
    }

    private void validateAmountField(String fieldName, HashMap<String, String> validator) {
        SelenideElement field = $x("//b[contains(text(), '" + fieldName + "')]/preceding-sibling::input");
        validator.forEach((key, value) -> {
            field.setValue(key).pressEnter();
            $x("//b[contains(text(), '" + fieldName + "')]/following::div" +
                    "[contains(@class, 'ui-form-field-error-message')][1]").shouldBe(Condition.text(value));
        });
    }
}
