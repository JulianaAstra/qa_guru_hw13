package tests.reqres;

import com.github.javafaker.Faker;

public class TestData {
    private static final Faker faker = new Faker();

    public String userName = faker.name().fullName();
    public String job = "qa_manual";
}
