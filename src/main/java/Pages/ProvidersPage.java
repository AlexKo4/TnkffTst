package Pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.util.ArrayList;
import static com.codeborne.selenide.Selenide.*;

/**
 * Created by Александр on 07.09.2017.
 */
public class ProvidersPage extends MainPage {

    private ArrayList<SelenideElement> providers = new ArrayList<>();

    //требуется рефакторинг
    public void selectRegion(String region) {
        String pattern = region.substring(0, region.length()-1);
        SelenideElement regionElement = $("span[class='ui-link payment-page__title_inner']");
        if (!(regionElement.getText().contains(pattern))) {
            switch (region) {
                case "Москва" : changeRegion(regionElement, "г. Москва");
                    break;
                case "Санкт-Петербург" : changeRegion(regionElement, "г. Санкт-Петербург");
                    break;
                default: changeRegion(regionElement, region);
            }
        }
    }

    private void changeRegion (SelenideElement regionElement, String toRegion) {
        regionElement.click();
        System.out.println("ckick on region Element");
        $x("//span[text()='" + toRegion + "']").click();
        System.out.println("click on toRegion");
    }


    public ElementsCollection getProviderList() {
        return $$("span[class*='ui-menu__link_icons_active'] span");
    }

    public PaymentProviderPage toPaymentProviderPage(String provider) {
        $x("//span[text()='" + provider + "']").click();
        return page(PaymentProviderPage.class);

    }
}

