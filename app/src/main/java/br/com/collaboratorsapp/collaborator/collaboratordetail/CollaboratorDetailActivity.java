package br.com.collaboratorsapp.collaborator.collaboratordetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.collaboratorsapp.Constants;
import br.com.collaboratorsapp.Injection;
import br.com.collaboratorsapp.R;
import br.com.collaboratorsapp.collaborator.maintaincollaborator.MaintainCollaboratorActivity;
import br.com.collaboratorsapp.data.entity.Collaborator;
import br.com.collaboratorsapp.view.DialogLoader;

public class CollaboratorDetailActivity extends AppCompatActivity implements CollaboratorDetailContract.View {

    private CollaboratorDetailContract.UserActionListener mActionListener;
    private Integer collaboratorCode;

    private LinearLayout mContent;

    private TextView tvCollaboratorDetailName;
    private TextView tvCollaboratorDetailLogin;
    private TextView tvCollaboratorDetailProfile;

    private DialogLoader mDialogLoader;

    private Boolean reloadCollaborators;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaboratordetail);

        mActionListener = new CollaboratorDetailPresenter(Injection.provideCollaboratorServiceApi(), this);

        setupActionBar();
        setupContentView();

        loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                mActionListener.openCollaboratorList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUESTCODE_EDITCOLLABORATOR) {
            if (resultCode == RESULT_OK) {

                Collaborator collaborator = (Collaborator) data.getSerializableExtra(
                        Constants.INTENT_KEY_COLLABORATOR);

                String name = collaborator.getName().split(Constants.BLANK_SPACE)[0];
                String actionMessage = getString(R.string.editcollaborator_actionmessage);
                actionMessage = String.format(actionMessage, name);

                showMessage(actionMessage);
                showCollaborator(collaborator);

                this.reloadCollaborators = true;
            }
        }
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Exibe ou oculta o botão padrão home
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Define se a view personalizada deve ser exibida
        actionBar.setDisplayShowCustomEnabled(true);
        //Define se o título/subtitulo deve ser exibido
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.activity_title_collaboratordetail));
    }

    private void setupContentView() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            collaboratorCode = (Integer) extras.get(Constants.INTENT_KEY_COLLABORATORCODE);
        }

        mContent = (LinearLayout) findViewById(R.id.collaboratordetail_content);

        tvCollaboratorDetailName = (TextView) findViewById(R.id.tv_collaboratordetail_name);
        tvCollaboratorDetailLogin = (TextView) findViewById(R.id.tv_collaboratordetail_login);
        tvCollaboratorDetailProfile = (TextView) findViewById(R.id.tv_collaboratordetail_profile);

        FloatingActionButton fabEditCollaborator = (FloatingActionButton) findViewById(R.id.fab_editcollaborator);
        fabEditCollaborator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.openAddCollaborator();
            }
        });
    }

    private void loadData() {
        reloadCollaborators = false;
        mActionListener.loadCollaborator(this.collaboratorCode);
    }

    @Override
    public void setProgressIndicator(final boolean isActive) {
        if (isActive) {
            mDialogLoader = new DialogLoader(this);
            mDialogLoader.setIndeterminate(true);
            mDialogLoader.setCancelable(false);
            mDialogLoader.show();
        } else {
            mDialogLoader.dismiss();
            mDialogLoader = null;
        }
    }

    @Override
    public void showCollaborator(Collaborator collaborator) {
        tvCollaboratorDetailName.setText(null != collaborator.getName() ?
                collaborator.getName() : getString(R.string.notprovided_message));

        tvCollaboratorDetailLogin.setText(null != collaborator.getLogin() ?
                collaborator.getLogin() : getString(R.string.notprovided_message));

        tvCollaboratorDetailProfile.setText(null != collaborator.getProfile() ?
                collaborator.getProfile().toString() : getString(R.string.notprovided_message));
    }

    @Override
    public void showCollaboratorsUi() {
        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_KEY_RELOAD_COLLABORATORS, reloadCollaborators);
        setResult(RESULT_OK, intent);

        this.finish();
    }

    @Override
    public void showAddCollaboratorUi(Collaborator collaborator) {
        Intent intent = new Intent(CollaboratorDetailActivity.this, MaintainCollaboratorActivity.class);
        intent.putExtra(Constants.INTENT_KEY_COLLABORATOR, collaborator);
        startActivityForResult(intent, Constants.REQUESTCODE_EDITCOLLABORATOR);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mContent, message, Snackbar.LENGTH_SHORT).show();
    }
}
