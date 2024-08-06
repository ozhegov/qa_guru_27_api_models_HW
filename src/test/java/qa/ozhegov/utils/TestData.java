package qa.ozhegov.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.github.javafaker.Faker;

public class TestData {

    private static final Faker faker = new Faker(new Locale("en"));

    public String name = getRandomName(),
            job = getRandomJob(),
            currentDate = getCurrentDate();

    public static String getRandomName() {

        return faker.name().firstName();

    }

    public static String getRandomJob() {

        return faker.job().field();

    }

    public static String getCurrentDate() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.now();

        return dtf.format(localDate);

    }

}
