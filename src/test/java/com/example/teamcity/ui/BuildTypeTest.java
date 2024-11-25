package com.example.teamcity.ui;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Selenide.sleep;
import com.example.teamcity.api.enums.Endpoint;
import static com.example.teamcity.api.enums.Endpoint.BUILD_TYPES;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseUiTest {


    private static final String REPO_URL = "https://github.com/roguestroke/wtf";
    private static final String WRONG_REPO_URL = "https://github.com/roguestroke/wtf123";
   
    @Test(description = "User should be able to create build type", groups = {"Positive"})
    public void userCreatesBuildType() {
        loginAs(testData.getUser());
        var createdProject = supperUserCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        
        CreateProjectPage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        sleep(5000);

        var createdBuildType = supperUserCheckedRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);
     
        ProjectPage.open(createdProject.getId())
            .openProject()
            .buildTitle.shouldHave(Condition.text(testData.getBuildType().getName()));
    }

    @Test(description = "User should not be able to craete build type with not exist repositiry", groups = {"Negative"})
    public void userCreatesProjectWithEmptyName() {

        loginAs(testData.getUser());
        
        CreateProjectPage.open("_Root")
                .createForm(WRONG_REPO_URL);

        new UncheckedBase(Specifications.superUserSpec(), BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName())
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);

        CreateProjectPage.errorMessage.shouldHave(Condition.text("git -c credential.helper= ls-remote origin command failed.\n" + //
                        "exit code: 128\n" + //
                        "stderr: fatal: could not read Username for 'https://github.com': No such device or address"));
                      
    }
}

