package Pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Condition.text;

/**
 * Created by Александр on 07.09.2017.
 */
public class PaymentsPage extends MainPage {


    public ProvidersPage toProvidersPage(String providerType) {
        $x("//span[text()='" + providerType + "']").click();
        return page(ProvidersPage.class);
    }


    //searching by argument and checking first result with redirect to result page
    public PaymentProviderPage firstSeachResult(String textSeach) {
        $x("//span[text()='Что оплатить или куда перевести?']/../input").setValue(textSeach);
        SelenideElement result = $$x("//div[contains(@class, 'ui-logo_size_42')]/ancestor::div[2]/div[1]/div[1]").first();
        result.shouldNotHave(text(textSeach)).click();
        return page(PaymentProviderPage.class);
    }
}