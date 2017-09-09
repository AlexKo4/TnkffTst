package Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Александр on 07.09.2017.
 */
public class PaymentsPage extends MainPage {


    public ProvidersPage toProvidersPage(String providerType) {
        $x("//span[text()='" + providerType + "']").click();
        return page(ProvidersPage.class);
    }

    //выбор провайдера из автокомплита (костыль для возврщения PaymentProviderPage)
    public PaymentProviderPage seach(SelenideElement seachResult) {
        seachResult.click();
        return page(PaymentProviderPage.class);
    }

    //Возвращает коллекцию вариантов автокомплита в зависимости от text
    public ElementsCollection fastSeachResult(String text) {
        $x("//span[text()='Что оплатить или куда перевести?']/../input").setValue(text);
        return $$x("//div[contains(@class, 'ui-logo_size_42')]/ancestor::div[2]/div[1]/div[1]");
    }
}