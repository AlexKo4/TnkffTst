/**
 * Created by Александр on 07.09.2017.
 */

import Pages.MainPage;
import Pages.PaymentsPage;
import Pages.PaymentProviderPage;
import Pages.ProviderPage;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;


public class TestPayment {


    @Before
    public void beforeTest() {
        Configuration.browser = "chrome";
    }


    @Test
    public void testPaymenZKU() throws InterruptedException {
        HashMap<String, String> validatorCodePayer = new HashMap<>();
        validatorCodePayer.put("", "Поле обязательное");
        validatorCodePayer.put("123456789", "Поле неправильно заполнено");
        validatorCodePayer.put("qW!@#$%^ &*()_+-={}[]/?<>", "Поле обязательное");
        validatorCodePayer.put("12345678.9", "Поле неправильно заполнено");

        HashMap<String, String> validatorPeriod = new HashMap<>();
        validatorPeriod.put("", "Поле обязательное");
        validatorPeriod.put("13.2012", "Поле заполнено некорректно");
        validatorPeriod.put("qW!@#$%^ &*()_+-={}[]/?<>", "Поле обязательное");
        validatorPeriod.put("10.203", "Поле заполнено некорректно");
        //validatorPeriod.put("10.9999", "Поле заполнено некорректно"); // this bug, may be?

        HashMap<String, String> validatorAmount = new HashMap<>();
        validatorAmount.put("", "Поле обязательное");
        validatorAmount.put("9.99", "Минимальная сумма перевода - 10 \u20BD");
        validatorAmount.put("qW!@#$%^ &*()_+-={}[]/?<>", "Поле обязательное");
        validatorAmount.put("15000.01", "Максимальная сумма перевода - 15 000 \u20BD");

        MainPage mainPage = open("https://www.tinkoff.ru", MainPage.class);
        PaymentsPage paymentsPage = mainPage.toPaymentPage();
        ProviderPage providerPage = paymentsPage.toSection("Коммунальные платежи");
        providerPage.selectRegion("Москве");
        SelenideElement firstProvider = providerPage.getProviderList().first();
        PaymentProviderPage paymentProviderPage = providerPage.toPaymentProviderPage("ЖКУ-Москва").toPayForm();

        paymentProviderPage.validateField("Код плательщика за ЖКУ в Москве", validatorCodePayer);
        paymentProviderPage.validateField("За какой период оплачиваете коммунальные услуги", validatorPeriod);
        paymentProviderPage.validateField("Сумма платежа, ", validatorAmount);
        sleep(10000);

    }
}
