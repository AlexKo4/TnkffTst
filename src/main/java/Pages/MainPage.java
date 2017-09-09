package Pages;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.remote.DriverCommand;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by Александр on 07.09.2017.
 */
public class MainPage {

    public PaymentsPage toPaymentPage() {
        $("span>a[href='/payments/']").click();
        //$("li[data-menu-item='2']").click();
        return page(PaymentsPage.class);
    }

    public String getURL() {
        return WebDriverRunner.url();
    }
}
