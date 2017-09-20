package br.com.collaboratorsapp.data.entity;

import java.io.Serializable;

public class Collaborator implements Serializable {

    private Integer code;

    private String name;

    private String login;

    private CollaboratorProfileEnum profile;

    private String password;

    public Collaborator() {
    }

    public Collaborator(String name, String login, CollaboratorProfileEnum profile, String password) {
        this(null, name, login, profile, password);
    }

    public Collaborator(Integer code, String name, String login, CollaboratorProfileEnum profile, String password) {
        this.code = code;
        this.name = name;
        this.login = login;
        this.profile = profile;
        this.password = password;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public CollaboratorProfileEnum getProfile() {
        return profile;
    }

    public void setProfile(CollaboratorProfileEnum profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
