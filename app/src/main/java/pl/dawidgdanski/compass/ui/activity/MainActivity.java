package pl.dawidgdanski.compass.ui.activity;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.compassapi.ActivityBoundCompass;
import pl.dawidgdanski.compass.compassapi.Compass;
import pl.dawidgdanski.compass.compassapi.location.LocationSupplier;
import pl.dawidgdanski.compass.inject.DependencyInjector;
import pl.dawidgdanski.compass.inject.Qualifiers;

public class MainActivity extends AppCompatActivity implements LocationSupplier.OnLocationChangedListener {

    @Inject
    @Named(Qualifiers.PLAY_SERVICES_COMPASS)
    ActivityBoundCompass compass;

    @Bind(R.id.arrow)
    View arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        DependencyInjector.getGraph().inject(this);

        compass.setView(arrow);
        compass.setOnLocationChangedListener(this);
        compass.onActivityCreated(this, savedInstanceState);
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

    }
}
