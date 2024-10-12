package com.example.teamcity.api;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static com.example.teamcity.api.enums.Endpoint.USERS;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {

    @Test(description = "User successfully creates project", groups = {"Positive", "CRUD"})
    public void userCreateProjectTest() {

        supperUserCheckedRequests.<User>getRequest(USERS).create(testData.getUser());

        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        softy.assertEquals(testData.getProject().getId(), createdProject.getId(), "Project Id is not correct");
    }

    @Test(description = "User successfully read project name", groups = {"Positive", "CRUD"})
    public void userGetsProjectTest() {

        supperUserCheckedRequests.<User>getRequest(USERS).create(testData.getUser());

        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var createdProjectName = userCheckedRequests.<Project>getRequest(PROJECTS).read(createdProject.getId()).getName();

        softy.assertEquals(createdProjectName, testData.getProject().getName(), "Project name is not correct");
    }

    @Test(description = "User successfully updates project name and description", groups = {"Positive", "CRUD"}, enabled = false)
    public void userUpdatesProjectNameAndDescriptionTest() {

        supperUserCheckedRequests.<User>getRequest(USERS).create(testData.getUser());

        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
    }

    @Test(description = "User successfully deletes project", groups = {"Positive", "CRUD"})
    public void userDeletesProjectTest() {
        supperUserCheckedRequests.<User>getRequest(USERS).create(testData.getUser());

        var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var createdProject = userCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        userCheckedRequests.<Project>getRequest(PROJECTS).delete(createdProject.getId());

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .read(createdProject.getId())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
    }
}
