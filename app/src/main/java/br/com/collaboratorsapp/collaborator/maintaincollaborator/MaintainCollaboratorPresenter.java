package br.com.collaboratorsapp.collaborator.maintaincollaborator;

import android.support.annotation.NonNull;

import br.com.collaboratorsapp.data.entity.Collaborator;
import br.com.collaboratorsapp.data.service.CollaboratorServiceApi;

class MaintainCollaboratorPresenter implements MaintainCollaboratorContract.UserActionListener {

    private CollaboratorServiceApi mServiceApi;
    private MaintainCollaboratorContract.View mView;

    MaintainCollaboratorPresenter(@NonNull CollaboratorServiceApi collaboratorServiceApi,
                                  @NonNull MaintainCollaboratorContract.View addCollaboratorsView) {

        mServiceApi = collaboratorServiceApi;
        mView = addCollaboratorsView;
    }

    @Override
    public void openCollaboratorsList() {
        mView.showCollaboratorsUi();
    }

    @Override
    public void createNewCollaborator(final Collaborator collaborator) {

        mView.setProgressIndicator(true);

        mServiceApi.create(collaborator, new CollaboratorServiceApi.CollaboratorCallback<Collaborator>() {
            @Override
            public void onLoaded(Collaborator data) {

                mView.setProgressIndicator(false);

                mView.showPreviousUi(collaborator);
            }
        });
    }

    @Override
    public void updateCollaborator(final Collaborator collaborator) {
        mView.setProgressIndicator(true);

        mServiceApi.update(collaborator, new CollaboratorServiceApi.CollaboratorCallback<Collaborator>() {
            @Override
            public void onLoaded(Collaborator data) {

                mView.setProgressIndicator(false);

                mView.showPreviousUi(collaborator);
            }
        });
    }
}
