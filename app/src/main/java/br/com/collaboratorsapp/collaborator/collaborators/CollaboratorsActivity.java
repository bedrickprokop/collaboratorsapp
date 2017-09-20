package br.com.collaboratorsapp.collaborator.collaborators;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import br.com.collaboratorsapp.Constants;
import br.com.collaboratorsapp.Injection;
import br.com.collaboratorsapp.R;
import br.com.collaboratorsapp.collaborator.maintaincollaborator.MaintainCollaboratorActivity;
import br.com.collaboratorsapp.collaborator.collaboratordetail.CollaboratorDetailActivity;
import br.com.collaboratorsapp.commons.ItemListListener;
import br.com.collaboratorsapp.data.entity.Collaborator;

public class CollaboratorsActivity extends AppCompatActivity implements CollaboratorsContract.View {

    private CollaboratorsContract.UserActionListener mActionListener;

    private LinearLayout mContent;
    private SwipeRefreshLayout mSwipeRefresh;

    private CollaboratorsAdapter mAdapter;

    private ItemListListener<Collaborator> mCollaboratorItemListener = new ItemListListener<Collaborator>() {
        @Override
        public void onClickedItem(Collaborator item) {
            mActionListener.openCollaboratorDetail(item);
        }

        @Override
        public void onLongClickedSelectItem(int index) {
        }

        @Override
        public void onLongClickedUnselectItem(int index) {
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(Constants.INTENT_KEY_COLLABORATORLIST, mAdapter.getList());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaborators);

        mActionListener = new CollaboratorsPresenter(Injection.provideCollaboratorServiceApi(), this);

        setupActionBar();
        setupContentView();

        loadData(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.REQUESTCODE_ADDCOLLABORATOR:
                if (resultCode == RESULT_OK) {
                    Collaborator collaborator = (Collaborator) data.getSerializableExtra(
                            Constants.INTENT_KEY_COLLABORATOR);

                    String name = collaborator.getName().split(Constants.BLANK_SPACE)[0];
                    String actionMessage = getString(R.string.createcollaborator_actionmessage);
                    actionMessage = String.format(actionMessage, name);

                    showMessage(actionMessage);

                    mActionListener.loadCollaboratorList(true);
                }
                break;

            case Constants.REQUESTCODE_EDITCOLLABORATOR:
                if (resultCode == RESULT_OK) {
                    boolean reloadCollaborators = data.getBooleanExtra(
                            Constants.INTENT_KEY_RELOAD_COLLABORATORS, true);

                    if (reloadCollaborators) {
                        mActionListener.loadCollaboratorList(true);
                    }
                }
                break;
        }
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        //Define se a view personalizada deve ser exibida
        actionBar.setDisplayShowCustomEnabled(true);
        //Define se o titulo/subtitulo deve ser exibido
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(getString(R.string.activity_title_collaborators));
    }

    private void setupContentView() {
        int numCollaboratorsColumns = getResources().getInteger(R.integer.num_collaborators_columns);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numCollaboratorsColumns);

        mContent = (LinearLayout) findViewById(R.id.collaborators_content);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.collaborators_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new CollaboratorsAdapter(this, new ArrayList<Collaborator>(), mCollaboratorItemListener);

        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.collaborators_swiperefresh);
        mSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(this, R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.colorAccent),
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActionListener.loadCollaboratorList(true);
            }
        });

        FloatingActionButton fabAddCollaborator = (FloatingActionButton) findViewById(R.id.fab_addcollaborator);
        fabAddCollaborator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActionListener.openAddCollaborator();
            }
        });
    }

    private void loadData(Bundle savedInstanceState) {
        if (null != savedInstanceState) {
            mAdapter.addData((CopyOnWriteArrayList<Collaborator>) savedInstanceState
                    .get(Constants.INTENT_KEY_COLLABORATORLIST));
        } else {
            mActionListener.loadCollaboratorList(true);
        }
    }

    @Override
    public void setProgressIndicator(final boolean isActive) {
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(isActive);
            }
        });
    }

    @Override
    public void showCollaboratorsList(List<Collaborator> collaboratorList) {
        mAdapter.addData(collaboratorList);
    }

    @Override
    public void showCollaboratorDetailUi(Integer collaboratorCode) {
        Intent intent = new Intent(this, CollaboratorDetailActivity.class);
        intent.putExtra(Constants.INTENT_KEY_COLLABORATORCODE, collaboratorCode);
        startActivityForResult(intent, Constants.REQUESTCODE_EDITCOLLABORATOR);
    }

    @Override
    public void showAddCollaboratorUi() {
        Intent intent = new Intent(this, MaintainCollaboratorActivity.class);
        startActivityForResult(intent, Constants.REQUESTCODE_ADDCOLLABORATOR);
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mContent, message, Snackbar.LENGTH_SHORT).show();
    }
}
