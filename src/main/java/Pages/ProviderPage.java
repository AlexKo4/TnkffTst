package Pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Created by Александр on 07.09.2017.
 */
public class ProviderPage {

    //требуется рефакторинг
    public void selectRegion(String region) {
        SelenideElement regionElement = $("span[class='ui-link payment-page__title_inner']");
        if (!region.equals(regionElement.getText())) {
            switch (region) {
                case "Москве" : changeRegion(regionElement, "г. Москва");
                    break;
                case "Санкт-Петербурге" : changeRegion(regionElement, "г. Санкт-Петербург");
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
}
