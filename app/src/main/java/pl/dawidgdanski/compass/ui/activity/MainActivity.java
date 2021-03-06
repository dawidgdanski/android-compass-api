package pl.dawidgdanski.compass.ui.activity;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.compassapi.ActivityBoundCompass;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.database.model.MyLocation;
import pl.dawidgdanski.compass.inject.DependencyInjector;
import pl.dawidgdanski.compass.inject.Qualifiers;
import pl.dawidgdanski.compass.persistence.LocationPreferenceManager;
import pl.dawidgdanski.compass.ui.dialog.LocationCreationDialogFragment;
import pl.dawidgdanski.compass.ui.dialog.MyLocationsDialogFragment;
import pl.dawidgdanski.compass.ui.view.CompassView;
import pl.dawidgdanski.compass.ui.view.LocationLayout;
import pl.dawidgdanski.compass.util.ApplicationUtils;

public class MainActivity extends BaseActivity implements LocationSupplier.OnLocationChangedListener,
        LocationCreationDialogFragment.OnLocationSavedListener,
        MyLocationsDialogFragment.OnLocationPickedListener {

    @Inject
    @Named(Qualifiers.PLAY_SERVICES_COMPASS)
    ActivityBoundCompass compass;

    @Inject
    LocationPreferenceManager locationPreferenceManager;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.compass_view)
    CompassView compassView;

    @Bind(R.id.destination_layout)
    LocationLayout destinationLayout;

    @Bind(R.id.my_location_layout)
    LocationLayout myLocationLayout;

    @Bind(R.id.fab)
    FloatingActionsMenu floatingActionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ApplicationUtils.setOrientationChangeEnabled(false, this);
        ButterKnife.bind(this);
        DependencyInjector.getGraph().inject(this);
        setUpActionBar(toolbar);
        setUpActionBarTitle(getString(R.string.app_name));
        setUpFloatingActionMenu();

        compass.setView(compassView.getArrowView());
        compass.setOnLocationChangedListener(this);
        compass.onActivityCreated(this, savedInstanceState);

        initializeLastDestinationIfExists();

        restoreFragmentsState(savedInstanceState);
    }

    private void initializeLastDestinationIfExists() {
        MyLocation destination = locationPreferenceManager.getLastDestination();

        if(destination != null) {
            compass.navigateTo(destination.getLatitude(), destination.getLongitude());
            destinationLayout.setLocation(destination);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        compass.onActivityStarted(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.onActivityResumed(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        compass.onActivitySaveInstanceState(this, outState);
        saveFragmentsInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compass.onActivityStopped(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationUtils.setOrientationChangeEnabled(true, this);
        compass.onActivityDestroyed(this);
        compass = null;
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocationLayout.setLocation(location);
    }

    private void setUpFloatingActionMenu() {
        final Context context = getBaseContext();
        Resources resources = getResources();
        FloatingActionButton showMyLocationsButton = new FloatingActionButton(context);
        showMyLocationsButton.setTitle(resources.getString(R.string.my_locations));
        showMyLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLocationsDialogFragment dialogFragment = MyLocationsDialogFragment.newInstance();
                dialogFragment.setLocationPickedListener(MainActivity.this);
                dialogFragment.show(getSupportFragmentManager(), MyLocationsDialogFragment.TAG);
                floatingActionMenu.collapse();
            }
        });

        FloatingActionButton addNewLocationButton = new FloatingActionButton(context);
        addNewLocationButton.setTitle(resources.getString(R.string.add_location));
        addNewLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationCreationDialogFragment locationCreationDialogFragment = LocationCreationDialogFragment.newInstance();
                locationCreationDialogFragment.setOnLocationSavedListener(MainActivity.this);
                locationCreationDialogFragment.show(getSupportFragmentManager(), LocationCreationDialogFragment.TAG);
                floatingActionMenu.collapse();
            }
        });

        floatingActionMenu.addButton(addNewLocationButton);
        floatingActionMenu.addButton(showMyLocationsButton);
    }

    @Override
    public void onLocationSaved(MyLocation myLocation) {
        onDestinationChange(myLocation);
    }

    @Override
    public void onLocationPicked(MyLocation location) {
        onDestinationChange(location);
    }

    private void onDestinationChange(MyLocation location) {
        locationPreferenceManager.saveDestination(location);
        destinationLayout.setLocation(location);
        compass.navigateTo(location.getLatitude(), location.getLongitude());
    }

    private void restoreFragmentsState(Bundle savedInstanceState) {

        if(savedInstanceState == null) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        LocationCreationDialogFragment dialogFragment =
                (LocationCreationDialogFragment) fragmentManager.getFragment(savedInstanceState, LocationCreationDialogFragment.TAG);
        if(dialogFragment != null) {
            dialogFragment.setOnLocationSavedListener(this);
        }

        MyLocationsDialogFragment myLocationsDialogFragment =
                (MyLocationsDialogFragment) fragmentManager.getFragment(savedInstanceState, MyLocationsDialogFragment.TAG);
        if(myLocationsDialogFragment != null) {
            myLocationsDialogFragment.setLocationPickedListener(this);
        }
    }

    private void saveFragmentsInstanceState(Bundle outState) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        LocationCreationDialogFragment creationDialogFragment =
                (LocationCreationDialogFragment) fragmentManager.findFragmentByTag(LocationCreationDialogFragment.TAG);

        if(creationDialogFragment != null) {
            fragmentManager.putFragment(outState, LocationCreationDialogFragment.TAG, creationDialogFragment);
        }

        MyLocationsDialogFragment myLocationsDialogFragment =
                (MyLocationsDialogFragment) fragmentManager.findFragmentByTag(MyLocationsDialogFragment.TAG);

        if(myLocationsDialogFragment != null) {
            fragmentManager.putFragment(outState, MyLocationsDialogFragment.TAG, myLocationsDialogFragment);
        }
    }
}
