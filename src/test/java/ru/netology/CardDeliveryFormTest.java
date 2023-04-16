package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryFormTest {

    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldSendFormDirectInput() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Иваново");
        String currentDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(currentDate);
        $("[data-test-id='name'] input").setValue("Михаил Салтыков-Щедрин");
        $("[data-test-id='phone'] input").setValue("+71234567890");
        $("[data-test-id='agreement'] span").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $(".notification__content").should(Condition.appear, Duration.ofSeconds(15)).shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));


    }
}
