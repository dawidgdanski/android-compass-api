package pl.dawidgdanski.compass.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.google.common.collect.Lists;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.database.model.MyLocation;
import pl.dawidgdanski.compass.ui.adapter.MyLocationsAdapter;
import pl.dawidgdanski.compass.ui.loader.CursorLoader;

public class MyLocationsDialogFragment extends DialogFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 0;

    public static final String TAG = MyLocationsDialogFragment.class.getSimpleName();

    public static MyLocationsDialogFragment newInstance() {
        MyLocationsDialogFragment dialogFragment = new MyLocationsDialogFragment();
        Bundle args = new Bundle();
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Bind(R.id.my_locations_list)
    ListView locationsList;

    private MyLocationsAdapter myLocationsAdapter;

    private OnLocationPickedListener onLocationPickedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myLocationsAdapter = new MyLocationsAdapter(getActivity(), Lists.<MyLocation>newArrayList());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity context = getActivity();
        View view = LayoutInflater.from(context).inflate(R.layout.my_locations_dialog, null);
        ButterKnife.bind(this, view);

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        locationsList.setAdapter(myLocationsAdapter);
        getLoaderManager().initLoader(LOADER_ID, Bundle.EMPTY, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader.Builder(getContext())
                .setProjection(MyLocationsAdapter.getProjection())
                .setUri(MyLocationsAdapter.getContentUri())
                .build();
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        myLocationsAdapter.replaceAll(MyLocationsAdapter.transform(data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnItemClick(R.id.my_locations_list)
    public void onItemClick(int position) {
        MyLocation myLocation = myLocationsAdapter.getItem(position);

        if (onLocationPickedListener != null) {
            onLocationPickedListener.onLocationPicked(myLocation);
            dismiss();
        }

    }

    public void setLocationPickedListener(OnLocationPickedListener onLocationPickedListener) {
        this.onLocationPickedListener = onLocationPickedListener;
    }

    public interface OnLocationPickedListener {
        void onLocationPicked(MyLocation location);
    }
}
