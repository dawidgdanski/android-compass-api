package pl.dawidgdanski.compass.ui.activity;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
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
import pl.dawidgdanski.compass.inject.DependencyInjector;
import pl.dawidgdanski.compass.inject.Qualifiers;
import pl.dawidgdanski.compass.ui.dialog.LocationCreationDialogFragment;
import pl.dawidgdanski.compass.ui.view.CompassView;
import pl.dawidgdanski.compass.ui.view.LocationLayout;
import pl.dawidgdanski.compass.util.Constants;

public class MainActivity extends BaseActivity implements LocationSupplier.OnLocationChangedListener {

    @Inject
    @Named(Qualifiers.PLAY_SERVICES_COMPASS)
    ActivityBoundCompass compass;

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
        ButterKnife.bind(this);
        DependencyInjector.getGraph().inject(this);
        setUpActionBar(toolbar);
        setUpActionBarTitle(getString(R.string.app_name));
        setUpFloatingActionMenu();

        compass.setView(compassView.getArrowView());
        compass.setOnLocationChangedListener(this);
        compass.onActivityCreated(this, savedInstanceState);
    }

    private void setUpFloatingActionMenu() {
        final Context context = getBaseContext();
        Resources resources = getResources();
        FloatingActionButton showMyLocationsButton = new FloatingActionButton(context);
        showMyLocationsButton.setTitle(resources.getString(R.string.my_locations));
        showMyLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FloatingActionButton addNewLocationButton = new FloatingActionButton(context);
        addNewLocationButton.setTitle(resources.getString(R.string.add_location));
        addNewLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationCreationDialogFragment.newInstance().show(getSupportFragmentManager(), Constants.DIALOG);
                floatingActionMenu.collapse();
            }
        });

        floatingActionMenu.addButton(addNewLocationButton);
        floatingActionMenu.addButton(showMyLocationsButton);
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
    }

    @Override
    protected void onStop() {
        super.onStop();
        compass.onActivityStopped(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compass.onActivityDestroyed(this);
        compass = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        myLocationLayout.setLocation(location);
    }
}
