package pl.dawidgdanski.compass.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class MyLocationsDialogFragment extends DialogFragment {

    public static MyLocationsDialogFragment newInstance() {
        MyLocationsDialogFragment dialogFragment = new MyLocationsDialogFragment();
        Bundle args = new Bundle();
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
