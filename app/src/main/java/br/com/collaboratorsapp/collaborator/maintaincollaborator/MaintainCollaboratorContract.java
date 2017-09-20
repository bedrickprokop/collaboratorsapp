package br.com.collaboratorsapp.collaborator.maintaincollaborator;

import br.com.collaboratorsapp.data.entity.Collaborator;

interface MaintainCollaboratorContract {

    interface View {

        void showCollaboratorsUi();

        void showPreviousUi(Collaborator collaborator);

        void setProgressIndicator(boolean isActive);
    }

    interface UserActionListener {

        void openCollaboratorsList();

        void createNewCollaborator(Collaborator collaborator);

        void updateCollaborator(Collaborator collaborator);
    }
}
