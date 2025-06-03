package service;

import model.ProjectSettings;

public class ProjectSettingsService
{
    private static final ProjectSettingsService INSTANCE = new ProjectSettingsService();

    private ProjectSettings currentSettings;

    private ProjectSettingsService() {}

    public static ProjectSettingsService getInstance() {
        return INSTANCE;
    }

    public ProjectSettings getSettings() {
        return currentSettings;
    }

    public void setSettings(ProjectSettings settings) {
        this.currentSettings = settings;
    }
}
