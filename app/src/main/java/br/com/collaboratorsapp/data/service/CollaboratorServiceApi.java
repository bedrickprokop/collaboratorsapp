package br.com.collaboratorsapp.data.service;

import java.util.List;

import br.com.collaboratorsapp.data.entity.Collaborator;

public interface CollaboratorServiceApi {

    interface CollaboratorCallback<T> {
        void onLoaded(T data);
    }

    void create(Collaborator collaborator, CollaboratorCallback<Collaborator> callback);

    void findOne(Integer code, CollaboratorCallback<Collaborator> callback);

    void findAll(CollaboratorCallback<List<Collaborator>> callback);

    void update(Collaborator collaborator, CollaboratorCallback<Collaborator> callback);

    void delete(Collaborator collaborator, CollaboratorCallback<Collaborator> callback);

}
