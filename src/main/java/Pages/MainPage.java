package Pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.commands.FindAll;
import com.sun.javaws.Main;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by Александр on 07.09.2017.
 */
public class MainPage {

    public PaymentPage toPaymentPage() {
        $$("a[href='/payments/']").get(1).click(); // три элемента только второй видимый
        //$("li[data-menu-item='2']").click();
        return page(PaymentPage.class);
    }
}
