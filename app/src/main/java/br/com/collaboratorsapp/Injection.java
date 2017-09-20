package br.com.collaboratorsapp;

import br.com.collaboratorsapp.data.service.CollaboratorServiceApi;
import br.com.collaboratorsapp.data.service.impl.CollaboratorServiceApiImplRetrofit;

public class Injection {

    public static CollaboratorServiceApi provideCollaboratorServiceApi() {
        return new CollaboratorServiceApiImplRetrofit();
    }
}
