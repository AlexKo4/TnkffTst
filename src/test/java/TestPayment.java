/**
 * Created by Александр on 07.09.2017.
 */

import Pages.MainPage;
import Pages.PaymentPage;
import Pages.ProviderPage;
import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.junit.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;


public class TestPayment {


    @Before
    public void beforeTest() {
        Configuration.browser = "chrome";
    }


    @Test
    public void testPaymenZKU() throws InterruptedException {
        MainPage mainPage = open("https://www.tinkoff.ru", MainPage.class);
        PaymentPage paymentPage = mainPage.toPaymentPage();
        ProviderPage providerPage = paymentPage.toSection("Коммунальные платежи");
        providerPage.selectRegion("Москве");
        sleep(10000);

    }
}
