package com.example.teamcity.ui;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseUiTest {


    private static final String REPO_URL = "https://github.com/roguestroke/wtf";
   
    @Test(description = "User should be able to create build type", groups = {"Positive"})
    public void userCreatesBuildType() {
        loginAs(testData.getUser());
        
        CreateProjectPage.open("_Root")
                .createForm(REPO_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());


        var createdBuildType = supperUserCheckedRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);

        var createdProject = supperUserCheckedRequests.<Project>getRequest(Endpoint.PROJECTS).read("name:" + testData.getProject().getName());
     
        ProjectPage.open(createdProject.getId())
            .buildTitle.shouldHave(Condition.text(testData.getBuildType().getName()));
    }

    @Test(description = "User should not be able to craete build type without name", groups = {"Negative"})
    public void userCreatesProjectWithEmptyName() {

    }
}

