package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ProjectPage extends BasePage {
    private static final String PROJECT_URL = "/project/%s";

    public SelenideElement title = $("span[class*='ProjectPageHeader']");
    public SelenideElement buildTitle = $("h2[class*='BuildTypeLine__buildTypeName--lY'] span[class*='MiddleEllipsis__searchable--uZ']");
    public SelenideElement project = $("a[class*='Subproject__link--PS']");

    public static ProjectPage open(String projectId) {
        return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
    }

    public ProjectPage openProject() {
        project.click();
        return this;
    }
}