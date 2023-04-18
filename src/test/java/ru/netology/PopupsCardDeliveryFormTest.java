package ru.netology;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class PopupsCardDeliveryFormTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldSendFormDirectInput() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("ив");
        $x("//span[contains(text(), 'Иваново')]").click();
        String deliveryDate = generateDate(17, "dd.MM.yyyy");
        String day = generateDate(17, "dd");
        String month = generateDate(17, "MM");
        String currentMonth = generateDate(0, "MM");
        $(".icon_name_calendar").click();
        if(!month.equals(currentMonth)) {
            $("[data-step='1']").click();
        }
        String day1;
        if (day.startsWith(String.valueOf(0))) {
            day1 = day.substring(1);
        }
        else {
            day1 = day;
        }
        $$(".calendar__day").findBy(Condition.text(day1)).click();
        $("[data-test-id='name'] input").setValue("Михаил Салтыков-Щедрин");
        $("[data-test-id='phone'] input").setValue("+71234567890");
        $("[data-test-id='agreement'] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $(".notification__content")
                .should(Condition.appear, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + deliveryDate));


    }
}
