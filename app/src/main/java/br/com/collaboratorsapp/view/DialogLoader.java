package br.com.collaboratorsapp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import br.com.collaboratorsapp.R;

public class DialogLoader extends ProgressDialog {

    public DialogLoader(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loader);
    }
}
