package com.magicpanda.game.jelly.domain;

/**
 * Created by 利彬 on 2015/3/29.
 */
public class User {
    private long id;
    private String name;
    private String description;
    private long installTime;
    private String sessionId;
    private int currentLevel;
    private String currentLayout;
    private int currentStep;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getInstallTime() {
        return installTime;
    }

    public void setInstallTime(long installTime) {
        this.installTime = installTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getCurrentLayout() {
        return currentLayout;
    }

    public void setCurrentLayout(String currentLayout) {
        this.currentLayout = currentLayout;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep = currentStep;
    }
}
