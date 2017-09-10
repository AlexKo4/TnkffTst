/**
 * Created by Александр on 07.09.2017.
 */

import Pages.MainPage;
import Pages.PaymentsPage;
import Pages.PaymentProviderPage;
import Pages.ProvidersPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;


public class TestPayment {

    @Before
    public void beforeTest() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.home")+"\\webdrivers\\chromedriver.exe");
        Configuration.browser = "chrome";
    }

    @Test
    public void testPaymenZKU() throws InterruptedException {
        //initialize test-data for testing validation of fields
        final HashMap<String, String> validatorCodePayer = new HashMap<>();
        validatorCodePayer.put("", "Поле обязательное");
        validatorCodePayer.put("123456789", "Поле неправильно заполнено");
        validatorCodePayer.put("qW!@#$%^ &*()_+-={}[]/?<>", "Поле обязательное");
        validatorCodePayer.put("12345678.9", "Поле неправильно заполнено");

        final HashMap<String, String> validatorPeriod = new HashMap<>();
        validatorPeriod.put("", "Поле обязательное");
        validatorPeriod.put("13.2012", "Поле заполнено некорректно");
        validatorPeriod.put("qW!@#$%^ &*()_+-={}[]/?<>", "Поле обязательное");
        validatorPeriod.put("10.203", "Поле заполнено некорректно");
        validatorPeriod.put("00.2017", "Поле заполнено некорректно");
        //validatorPeriod.put("10.9999", "Поле заполнено некорректно"); // this bug, may be?

        final HashMap<String, String> validatorAmount = new HashMap<>();
        validatorAmount.put("", "Поле обязательное");
        validatorAmount.put("9.99", "Минимальная сумма перевода - 10 \u20BD");
        validatorAmount.put("qW!@#$%^ &*()_+-={}[]/?<>", "Поле обязательное");
        validatorAmount.put("15000.01", "Максимальная сумма перевода - 15 000 \u20BD");

        //1
        MainPage mainPage = open("https://www.tinkoff.ru", MainPage.class);

        //2
        PaymentsPage paymentsPage = mainPage.toPaymentPage();

        //3
        ProvidersPage providersPage = paymentsPage.toProvidersPage("Коммунальные платежи");

        //4
        providersPage.selectRegion("Москва");

        //5
        String firstProviderText = providersPage.getProviderList().first().getText();

        //6
        PaymentProviderPage paymentProviderPage = providersPage.toPaymentProviderPage("ЖКУ-Москва").toPayForm();
        String urlZKUmoskva = paymentProviderPage.getURL();

        //7
        paymentProviderPage.validateField("Код плательщика за ЖКУ в Москве", validatorCodePayer);
        paymentProviderPage.validateField("За какой период оплачиваете коммунальные услуги", validatorPeriod);
        paymentProviderPage.validateField("Сумма платежа, ", validatorAmount);

        //8
        paymentsPage = paymentProviderPage.toPaymentPage();

        //9-11
        paymentProviderPage = paymentsPage.firstSeachResult(firstProviderText);
        Assert.assertEquals(urlZKUmoskva, paymentProviderPage.getURL());

        //12
        paymentsPage = paymentProviderPage.toPaymentPage();
        providersPage = paymentsPage.toProvidersPage("Коммунальные платежи");

        //13
        providersPage.selectRegion("Санкт-Петербург");

        //14
        for (SelenideElement provider: providersPage.getProviderList()) {
            Assert.assertNotEquals(provider.getText(), firstProviderText);
        }
        sleep(5000);
    }
}