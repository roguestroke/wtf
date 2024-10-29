package com.example.teamcity.ui;
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
        // подготовка окружения
        loginAs(testData.getUser());
        var newProject = supperUserCheckedRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        
        // взаимодействие с UI
        CreateProjectPage.open(newProject.getId())
                .createForm(REPO_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        var createdBuildType = supperUserCheckedRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);

        // проверка состояния UI
        // (корректность считывания данных и отображение данных на UI)
        ProjectPage.open(testData.getProject().getId())
            .openProject()
            .buildTitle.shouldHave(Condition.text(testData.getBuildType().getName()));
    }

    @Test(description = "User should not be able to craete build type without name", groups = {"Negative"})
    public void userCreatesProjectWithEmptyName() {

    }
}

