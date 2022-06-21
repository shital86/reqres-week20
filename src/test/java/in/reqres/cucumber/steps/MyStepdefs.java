package in.reqres.cucumber.steps;
/* 
 Created by Kalpesh Patel
 */

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import in.reqres.reqresinfo.UsersSteps;
import in.reqres.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

public class MyStepdefs {
    static String name = "Bob" + TestUtils.getRandomValue();
    static String job = "API Tester" + TestUtils.getRandomValue();
    static String email = "eve.holt@reqres.in";
    static String password = "cityslicka";
    static String userID;
    static String token;
    static ValidatableResponse response;

    @Steps
    UsersSteps usersSteps;

    @Given("^I am on homepage of the given url$")
    public void iAmOnHomepageOfTheGivenUrl() {
       usersSteps.loginUser(email, password);
    }

    @When("^I send POST request to the the application using a valid payload to create a User$")
    public void iSendPOSTRequestToTheTheApplicationUsingAValidPayloadToCreateAUser() {
        response = usersSteps.createUser(name, job);
    }

    @Then("^I get status code (\\d+)$")
    public void iGetStatusCode(int code) {
        response.assertThat().statusCode(code);
    }

    @And("^I fetch the ID of newly created user$")
    public void iFetchTheIDOfNewlyCreatedUser() {
        userID = response.log().all().extract().path("id");
        System.out.println(userID);
    }

    @When("^I send GET request to the application to read newly created user$")
    public void iSendGETRequestToTheApplicationToReadNewlyCreatedUser() {
        HashMap<String, ?> userMap = usersSteps.getUserByID(userID);
        Assert.assertThat(userMap, hasValue(name));
    }

    @And("^I verify in the response if it has newly created user name$")
    public void iVerifyInTheResponseIfItHasNewlyCreatedUserName() {
        HashMap<String, ?> userMap = usersSteps.getUserByID(userID);
        Assert.assertThat(userMap, hasValue(name));
        System.out.println(userID);
    }

    @When("^I send PUT request to the application to update newly created user$")
    public void iSendPUTRequestToTheApplicationToUpdateNewlyCreatedUser() {
        name = name + "_updatedbyPut";
        response = usersSteps.updateUserByPut(userID, name, job);
        response.log().all().statusCode(200);
    }

    @And("^I verify in the response if it has newly created user is updated$")
    public void iVerifyInTheResponseIfItHasNewlyCreatedUserIsUpdated() {
        HashMap<String, ?> userMap = usersSteps.getUserByID(userID);
        Assert.assertThat(userMap, hasValue(name));
    }

    @When("^I send PATCH request to the application to update newly created user$")
    public void iSendPATCHRequestToTheApplicationToUpdateNewlyCreatedUser() {
        name = name + "_updatedbyPatch";
        response = usersSteps.updateUserByPatch(userID, name, job);
        response.log().all().statusCode(200);
    }

    @When("^I send DELETE request to the application to delete newly created user$")
    public void iSendDELETERequestToTheApplicationToDeleteNewlyCreatedUser() {
        response = usersSteps.deleteProduct(userID);
        response.log().all().statusCode(204);
    }

    @And("^I verify if newly created user is deleted$")
    public void iVerifyIfNewlyCreatedUserIsDeleted() {
        response = usersSteps.deleteProduct(userID);
        response.log().all().statusCode(404);
    }

    @When("^I send GET request to the the application to read all users$")
    public void iSendGETRequestToTheTheApplicationToReadAllUsers() {
        response = usersSteps.getAllUserFromPageTwo();
    }

    @And("^I verify if page is (\\d+)$")
    public void iVerifyIfPageIs(int page) {
        response = usersSteps.getAllUserFromPageTwo();
        int actual_page = response.log().all().extract().path("page");
        Assert.assertEquals(page, actual_page);
    }

    @And("^I verify if per_page is (\\d+)$")
    public void iVerifyIfPer_pageIs(int per_page) {
        response = usersSteps.getAllUserFromPageTwo();
        int actual_per_page = response.log().all().extract().path("per_page");
        Assert.assertEquals(per_page, actual_per_page);
    }


    @And("^I verify if second data's id is (\\d+)$")
    public void iVerifyIfSecondDataSIdIs(int expected) {
        response = usersSteps.getAllUserFromPageTwo();
        int data = response.log().all().extract().path("data[1].id");
        Assert.assertEquals(expected, data);
    }

    @And("^I verify if forth data's first_name is Byron$")
    public void iVerifyIfForthDataSFirst_nameIsByron() {
        response = usersSteps.getAllUserFromPageTwo();
        String firstname = response.log().all().extract().path("data[3].first_name");
        Assert.assertEquals("Byron", firstname);
    }

    @And("^I verify if list of data is (\\d+)$")
    public void iVerifyIfListOfDataIs(int expected) {
        response = usersSteps.getAllUserFromPageTwo();
        List<?> listOfData= response.log().all().extract().path("data");
        Assert.assertEquals(expected, listOfData.size());

    }

    @And("^I verify if sixth data's avatar is \"([^\"]*)\"$")
    public void iVerifyIfSixthDataSAvatarIs(String expected) {
        response = usersSteps.getAllUserFromPageTwo();
        String imageUrl = response.log().all().extract().path("data[5].avatar");
        Assert.assertEquals(expected, imageUrl);
    }

    @And("^I verify if support\\.url is \"([^\"]*)\"$")
    public void iVerifyIfSupportUrlIs(String expected) {
        response = usersSteps.getAllUserFromPageTwo();
        String supportHeading = response.log().all().extract().path("support.url");
        Assert.assertEquals(expected, supportHeading);
    }

    @And("^I verify if support\\.text is \"([^\"]*)\"$")
    public void iVerifyIfSupportTextIs(String expected)  {
        response = usersSteps.getAllUserFromPageTwo();
        String supportText = response.log().all().extract().path("support.text");
        Assert.assertEquals(expected, supportText);

    }
}
