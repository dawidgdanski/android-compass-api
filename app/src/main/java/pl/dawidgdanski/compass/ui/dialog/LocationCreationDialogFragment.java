package pl.dawidgdanski.compass.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.ui.validation.LatitudeTextValidator;
import pl.dawidgdanski.compass.ui.validation.LongitudeTextValidator;
import pl.dawidgdanski.compass.ui.view.CoordinateEntry;

public class LocationCreationDialogFragment extends DialogFragment implements DialogInterface.OnClickListener,
        DialogInterface.OnShowListener{

    public static LocationCreationDialogFragment newInstance() {
        LocationCreationDialogFragment dialogFragment = new LocationCreationDialogFragment();
        Bundle args = new Bundle();
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Bind(R.id.dialog_latitude_entry)
    CoordinateEntry latitudeEntry;

    @Bind(R.id.dialog_longitude_entry)
    CoordinateEntry longitudeEntry;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity context = getActivity();

        View view = LayoutInflater.from(context).inflate(R.layout.location_creation_dialog_fragment, null);
        ButterKnife.bind(this, view);
        initializeEntries();

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton(getString(R.string.save), this)
                .setNegativeButton(getString(R.string.cancel), this)
                .create();

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setOnShowListener(this);

        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which == DialogInterface.BUTTON_POSITIVE) {
            onSaveButtonClicked();
        } else {
            dismiss();
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        final AlertDialog alertDialog = (AlertDialog) dialog;

        final Button saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
        final Button cancelButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

        final Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        saveButton.setTypeface(boldTypeface);
        cancelButton.setTypeface(boldTypeface);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void initializeEntries() {
        latitudeEntry.setDecimalPartTextValidator(new LatitudeTextValidator());
        longitudeEntry.setDecimalPartTextValidator(new LongitudeTextValidator());
    }

    private void onSaveButtonClicked() {
        final boolean result = latitudeEntry.validateSelf() && longitudeEntry.validateSelf();

        if(result) {
            final double latitude = latitudeEntry.getValue();
            final double longitude = longitudeEntry.getValue();
        }
    }

    public interface OnLocationSavedListener {
        void onLocationSaved();
    }
}