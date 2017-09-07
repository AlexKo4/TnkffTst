package Pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;

/**
 * Created by Александр on 07.09.2017.
 */
public class PaymentPage {


    public ProviderPage toSection(String section) {
        $x("//span[text()='" + section + "']").click();
        return page(ProviderPage.class);
    }

}
