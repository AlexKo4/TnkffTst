/**
 * Created by Александр on 07.09.2017.
 */

import Pages.MainPage;
import Pages.PaymentsPage;
import Pages.PaymentProviderPage;
import Pages.ProviderPage;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Text;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;


public class TestPayment {


    @Before
    public void beforeTest() {
        Configuration.browser = "chrome";
    }


    @Test
    public void testPaymenZKU() throws InterruptedException {
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
        ProviderPage providerPage = paymentsPage.toSection("Коммунальные платежи");

        //4 иногда не находит Москву в виду того что не открыл список с регионами
        providerPage.selectRegion("Москва");

        //5 иногда записывает "Мобильная связь со страницы paymentsPage"
        String firstProviderText = providerPage.getProviderList().first().getText();

        //6
        PaymentProviderPage paymentProviderPage = providerPage.toPaymentProviderPage("ЖКУ-Москва").toPayForm();
        String urlZKUmoskva = paymentProviderPage.getURL();

        //7
        paymentProviderPage.validateField("Код плательщика за ЖКУ в Москве", validatorCodePayer);
        paymentProviderPage.validateField("За какой период оплачиваете коммунальные услуги", validatorPeriod);
        paymentProviderPage.validateField("Сумма платежа, ", validatorAmount);

        //8
        paymentsPage = paymentProviderPage.toPaymentPage();

        //9
        SelenideElement firstSeachResult = paymentsPage.fastSeachResult(firstProviderText).first();

        //10
        firstSeachResult.shouldHave(text(firstProviderText));

        //11
        paymentProviderPage = paymentsPage.seach(firstSeachResult);
        Assert.assertTrue(urlZKUmoskva.equals(paymentProviderPage.getURL()));

        //12
        paymentsPage = paymentProviderPage.toPaymentPage();
        providerPage = paymentsPage.toSection("Коммунальные платежи");

        //13
        providerPage.selectRegion("Санкт-Петербург");

        //14
        for (SelenideElement provider: providerPage.getProviderList()) {
            Assert.assertFalse(provider.getText().equals(firstProviderText));
        }

    }
}
