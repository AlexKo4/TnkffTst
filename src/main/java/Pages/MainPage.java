package Pages;

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
}
