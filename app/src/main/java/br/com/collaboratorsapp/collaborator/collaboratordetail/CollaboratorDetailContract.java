package br.com.collaboratorsapp.collaborator.collaboratordetail;

import br.com.collaboratorsapp.data.entity.Collaborator;

interface CollaboratorDetailContract {

    interface View {

        void setProgressIndicator(boolean isActive);

        void showCollaborator(Collaborator collaborator);

        void showCollaboratorsUi();

        void showAddCollaboratorUi(Collaborator collaborator);

        void showMessage(String message);
    }

    interface UserActionListener {

        void loadCollaborator(Integer collaboratorCode);

        void openCollaboratorList();

        void openAddCollaborator();
    }
}
