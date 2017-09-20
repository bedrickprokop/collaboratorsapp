package br.com.collaboratorsapp.collaborator.maintaincollaborator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import br.com.collaboratorsapp.Constants;
import br.com.collaboratorsapp.Injection;
import br.com.collaboratorsapp.R;
import br.com.collaboratorsapp.data.entity.Collaborator;
import br.com.collaboratorsapp.data.entity.CollaboratorProfileEnum;
import br.com.collaboratorsapp.view.DialogLoader;

public class MaintainCollaboratorActivity extends AppCompatActivity implements MaintainCollaboratorContract.View {

    private MaintainCollaboratorContract.UserActionListener mActionListener;
    private EditText etMaintainCollaboratorName;
    private EditText etMaintainCollaboratorLogin;
    private Spinner spMaintainCollaboratorProfile;

    private EditText etMaintainCollaboratorPassword;

    private Collaborator collaborator;
    private Boolean isInsertMode;

    private DialogLoader mDialogLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintaincollaborator);

        mActionListener = new MaintainCollaboratorPresenter(Injection.provideCollaboratorServiceApi(), this);

        setupExtras();
        setupActionBar();
        setupContentView();
    }

    private void setupExtras() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            isInsertMode = false;
            collaborator = (Collaborator) extras.getSerializable(Constants.INTENT_KEY_COLLABORATOR);

        } else {
            isInsertMode = true;
            collaborator = new Collaborator(Constants.EMPTY_SPACE, Constants.EMPTY_SPACE,
                    CollaboratorProfileEnum.ADMINISTRATOR, Constants.EMPTY_SPACE);
        }
    }

    public void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Exibe ou oculta o botão padrão home
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Define se a view personalizada deve ser exibida
        actionBar.setDisplayShowCustomEnabled(true);

        //Define se o título/subtítulo deve ser exibido
        actionBar.setDisplayShowTitleEnabled(true);

        if (isInsertMode) {
            actionBar.setTitle(getString(R.string.activity_title_addcollaborator));
        } else {
            actionBar.setTitle(getString(R.string.activity_title_editcollaborator));
        }
    }

    public void setupContentView() {
        //Name
        etMaintainCollaboratorName = (EditText) findViewById(R.id.et_maintaincollaborator_name);
        etMaintainCollaboratorName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        etMaintainCollaboratorName.setOnFocusChangeListener(
                new FocusListener(R.id.et_maintaincollaborator_name));
        etMaintainCollaboratorName.setText(collaborator.getName());

        //Login
        etMaintainCollaboratorLogin = (EditText) findViewById(R.id.et_maintaincollaborator_login);
        etMaintainCollaboratorLogin.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        etMaintainCollaboratorLogin.setOnFocusChangeListener(
                new FocusListener(R.id.et_maintaincollaborator_login));
        etMaintainCollaboratorLogin.setText(collaborator.getLogin());

        //Profile
        spMaintainCollaboratorProfile = (Spinner) findViewById(R.id.sp_maintaincollaborator_profile);
        CollaboratorProfileEnum[] values = CollaboratorProfileEnum.values();
        ArrayAdapter<CollaboratorProfileEnum> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, values);
        spMaintainCollaboratorProfile.setAdapter(adapter);
        spMaintainCollaboratorProfile.setSelection(collaborator.getProfile().ordinal());

        //Password
        etMaintainCollaboratorPassword = (EditText) findViewById(R.id.et_maintaincollaborator_password);
        etMaintainCollaboratorPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        etMaintainCollaboratorPassword.setOnFocusChangeListener(
                new FocusListener(R.id.et_maintaincollaborator_password));

        TextView tvMaintaincollaboratorPassword = (TextView) findViewById(R.id.tv_maintaincollaborator_password);
        if (!isInsertMode) {
            etMaintainCollaboratorPassword.setVisibility(View.GONE);
            tvMaintaincollaboratorPassword.setVisibility(View.GONE);
        }

        FloatingActionButton fabSubmitCollaborator = (FloatingActionButton) findViewById(
                R.id.fab_submitcollaborator);
        fabSubmitCollaborator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collaborator.setName(etMaintainCollaboratorName.getText().toString());
                collaborator.setLogin(etMaintainCollaboratorLogin.getText().toString());
                collaborator.setProfile((CollaboratorProfileEnum) spMaintainCollaboratorProfile.getSelectedItem());

                if (isInsertMode) {
                    collaborator.setPassword(etMaintainCollaboratorPassword.getText().toString());
                }

                if (isCollaboratorValid(collaborator)) {
                    if (isInsertMode) {
                        mActionListener.createNewCollaborator(collaborator);
                    } else {
                        mActionListener.updateCollaborator(collaborator);
                    }

                }
            }
        });
    }

    private boolean isCollaboratorValid(Collaborator collaborator) {
        if (null == collaborator.getName() || collaborator.getName().isEmpty()) {
            etMaintainCollaboratorName.setError(getString(R.string.et_maintaincollaborator_name_required));
            etMaintainCollaboratorName.requestFocus();
            return false;
        }
        if (null == collaborator.getLogin() || collaborator.getLogin().isEmpty()) {
            etMaintainCollaboratorLogin.setError(getString(R.string.et_maintaincollaborator_login_required));
            etMaintainCollaboratorLogin.requestFocus();
            return false;
        }

        if (isInsertMode) {
            if (null == collaborator.getPassword() || collaborator.getPassword().isEmpty()) {
                etMaintainCollaboratorPassword.setError(getString(R.string.et_maintaincollaborator_password_required));
                etMaintainCollaboratorPassword.requestFocus();
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                mActionListener.openCollaboratorsList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showCollaboratorsUi() {
        this.finish();
    }

    @Override
    public void showPreviousUi(Collaborator collaborator) {
        Intent intent = new Intent();
        intent.putExtra(Constants.INTENT_KEY_COLLABORATOR, collaborator);
        setResult(RESULT_OK, intent);

        this.finish();
    }

    @Override
    public void setProgressIndicator(boolean isActive) {
        if (isActive) {
            mDialogLoader = new DialogLoader(MaintainCollaboratorActivity.this);
            mDialogLoader.setIndeterminate(true);
            mDialogLoader.setCancelable(false);
            mDialogLoader.show();
        } else {
            mDialogLoader.dismiss();
            mDialogLoader = null;
        }
    }

    private void showSoftKeyboard(View view, int id, boolean hasFocus) {
        if (view.getId() == id && !hasFocus) {
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private class FocusListener implements View.OnFocusChangeListener {
        private int mViewId;

        private FocusListener(int viewId) {
            this.mViewId = viewId;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            showSoftKeyboard(view, mViewId, hasFocus);
        }
    }
}