package br.com.collaboratorsapp.collaborator.collaboratordetail;

import android.support.annotation.NonNull;

import br.com.collaboratorsapp.data.entity.Collaborator;
import br.com.collaboratorsapp.data.service.CollaboratorServiceApi;

class CollaboratorDetailPresenter implements CollaboratorDetailContract.UserActionListener {

    private CollaboratorServiceApi mServiceApi;
    private CollaboratorDetailContract.View mView;

    private Collaborator loadedCollaborator;

    CollaboratorDetailPresenter(@NonNull CollaboratorServiceApi collaboratorServiceApi,
                                @NonNull CollaboratorDetailContract.View collaboradorDetailView) {
        this.mServiceApi = collaboratorServiceApi;
        this.mView = collaboradorDetailView;
    }

    @Override
    public void loadCollaborator(Integer collaboratorCode) {

        mView.setProgressIndicator(true);

        mServiceApi.findOne(collaboratorCode, new CollaboratorServiceApi.CollaboratorCallback<Collaborator>() {
            @Override
            public void onLoaded(Collaborator data) {
                loadedCollaborator = data;

                mView.setProgressIndicator(false);
                mView.showCollaborator(data);
            }
        });
    }

    @Override
    public void openCollaboratorList() {
        mView.showCollaboratorsUi();
    }

    @Override
    public void openAddCollaborator() {
        if (null != loadedCollaborator) {
            mView.showAddCollaboratorUi(loadedCollaborator);
        }
    }
}
