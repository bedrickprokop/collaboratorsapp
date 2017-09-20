package br.com.collaboratorsapp;

public class Constants {

    //extra intents
    public static final String INTENT_KEY_MESSAGE = "message";
    public static final String INTENT_KEY_COLLABORATOR = "collaborator";
    public static final String INTENT_KEY_COLLABORATORCODE = "collaboratorCode";
    public static final String INTENT_KEY_COLLABORATORLIST = "collaboratorList";
    public static final String INTENT_KEY_RELOAD_COLLABORATORS = "reloadCollaborators";

    //texts
    public static final String COMMA = ",";
    public static final String BLANK_SPACE = " ";
    public static final String EMPTY_SPACE = "";

    //request codes
    public static final int REQUESTCODE_ADDCOLLABORATOR = 100;
    public static final int REQUESTCODE_EDITCOLLABORATOR = 200;

    //endpoint constants
    public static final String BASE_SERVICE_URL = "http://192.168.0.15:8080/api/";

}
