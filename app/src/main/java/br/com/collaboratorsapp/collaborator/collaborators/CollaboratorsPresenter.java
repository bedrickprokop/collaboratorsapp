package br.com.collaboratorsapp.collaborator.collaborators;

import android.support.annotation.NonNull;

import java.util.List;

import br.com.collaboratorsapp.data.entity.Collaborator;
import br.com.collaboratorsapp.data.service.CollaboratorServiceApi;

class CollaboratorsPresenter implements CollaboratorsContract.UserActionListener {

    private CollaboratorServiceApi mServiceApi;
    private CollaboratorsContract.View mView;

    CollaboratorsPresenter(@NonNull CollaboratorServiceApi collaboratorServiceApi,
                           @NonNull CollaboratorsContract.View collaboratorsView) {
        this.mServiceApi = collaboratorServiceApi;
        this.mView = collaboratorsView;
    }

    @Override
    public void loadCollaboratorList(boolean doRefresh) {

        mView.setProgressIndicator(true);
        mServiceApi.findAll(new CollaboratorServiceApi.CollaboratorCallback<List<Collaborator>>() {
            @Override
            public void onLoaded(List<Collaborator> data) {
                mView.setProgressIndicator(false);
                mView.showCollaboratorsList(data);
            }
        });
    }

    @Override
    public void openCollaboratorDetail(Collaborator collaborator) {
        mView.showCollaboratorDetailUi(collaborator.getCode());
    }

    @Override
    public void openAddCollaborator() {
        mView.showAddCollaboratorUi();
    }
}
