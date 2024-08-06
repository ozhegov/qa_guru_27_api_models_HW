package qa.ozhegov.tests;

import qa.ozhegov.models.CreateUsersBodyModel;
import qa.ozhegov.models.CreateUsersResponseModel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import qa.ozhegov.utils.TestData;


import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static qa.ozhegov.specs.CreateUsersSpec.createUserResponseSpec;
import static qa.ozhegov.specs.CreateUsersSpec.createUsersRequestSpec;

@DisplayName("API тесты на POST /api/users")
public class CreateUsersTests extends TestBase {

    TestData testData = new TestData();
    CreateUsersBodyModel createUserData = new CreateUsersBodyModel();

    @DisplayName("При создании пользователя с именем и работой")
    @ParameterizedTest(name = "корректное имя {0} и работа {1} отображаются в ответе")
    @CsvSource({
            "Max Ozhegov, QA Engineer",
            "Sergey Brin,  CEO",
            "Vasya Pupkin, Plumber",
            "Filipp Kirkorov, Singer"}
    )
    void correctNameShouldBeVisibleAfterSuccessCreation(String name, String job) {

        createUserData.setName(name);
        createUserData.setJob(job);

        CreateUsersResponseModel response = step("Отправляем запрос", () -> {
            return given(createUsersRequestSpec)
                    .body(createUserData)

                    .when()
                    .post()

                    .then()
                    .spec(createUserResponseSpec)
                    .extract().as(CreateUsersResponseModel.class);

        });

        step("Проверяем тело ответа", () -> {
            assertThat(response.getName()).isEqualTo(name);
            assertThat(response.getJob()).isEqualTo(job);
        });

    }

    @Test
    @DisplayName("Тело ответа на успешный запрос создания пользователя не должно быть пустым")
    void responseBodyShouldHaveFourParams() {

        createUserData.setName(testData.name);
        createUserData.setJob(testData.job);

        CreateUsersResponseModel response = step("Отправляем запрос", () -> {
            return given(createUsersRequestSpec)

                    .body(createUserData)

                    .when()
                    .post()

                    .then()
                    .spec(createUserResponseSpec)
                    .extract().as(CreateUsersResponseModel.class);
        });

        step("Проверяем тело ответа", () -> {
            assertThat(response).isNotNull();
        });

    }

    @Test
    @DisplayName("В теле ответа на успешный запрос создания пользователя содержится текущая дата")
    void responseBodyShouldContainCurrentDay() {

        createUserData.setName(testData.name);
        createUserData.setJob(testData.job);


        CreateUsersResponseModel response = step("Отправляем запрос", () -> {

            return given(createUsersRequestSpec)

                    .body(createUserData)

                    .when()
                    .post()

                    .then()
                    .spec(createUserResponseSpec)
                    .extract().as(CreateUsersResponseModel.class);

        });

        step("Проверяем тело ответа", () -> {
            assertThat(response.getCreatedAt()).contains(testData.currentDate);
        });

    }

    @Test
    @DisplayName("При попытке созать пользователя без указания имени в ответе отображается имя null")
    void responseBodyShouldContainsNullName() {

        createUserData.setJob(testData.job);

        CreateUsersResponseModel response = step("Отправляем запрос", () -> {

            return given(createUsersRequestSpec)
                    .body(createUserData)

                    .when()
                    .post()

                    .then()
                    .spec(createUserResponseSpec)
                    .extract().as(CreateUsersResponseModel.class);

        });

        step("Проверяем тело ответа", () -> {
            assertThat(response.getName()).isEqualTo(null);
        });

    }

    @Test
    @DisplayName("При попытке созать пользователя без указания работы в ответе в ответе отображается работа null")
    void responseBodyShouldContainsNullJob() {

        createUserData.setName(testData.name);

        CreateUsersResponseModel response = step("Отправляем запрос", () -> {
            return given(createUsersRequestSpec)
                    .body(createUserData)

                    .when()
                    .post()

                    .then()
                    .spec(createUserResponseSpec)
                    .extract().as(CreateUsersResponseModel.class);
        });

        step("Проверяем тело ответа", () -> {
            assertThat(response.getJob()).isEqualTo(null);
        });


    }

}
