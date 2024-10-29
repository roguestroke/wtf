package com.example.teamcity.ui;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseUiTest {


    private static final String REPO_URL = "https://github.com/roguestroke/wtf";
    private static final String WRONG_REPO_URL = "https://github.com/roguestroke/wtf123";
   
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

    @Test(description = "User should not be able to craete build type with not exist repositiry", groups = {"Negative"})
    public void userCreatesProjectWithEmptyName() {

        loginAs(testData.getUser());
        
        CreateProjectPage.open("_Root")
                .createForm(WRONG_REPO_URL);


        CreateProjectPage.errorMessage.shouldHave(Condition.text("git -c credential.helper= ls-remote origin command failed.\n" + //
                        "exit code: 128\n" + //
                        "stderr: fatal: could not read Username for 'https://github.com': No such device or address"));   
    }
}

