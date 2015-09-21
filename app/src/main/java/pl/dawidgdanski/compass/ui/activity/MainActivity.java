package pl.dawidgdanski.compass.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.dawidgdanski.compass.R;
import pl.dawidgdanski.compass.compassapi.Compass;
import pl.dawidgdanski.compass.inject.DependencyInjector;

public class MainActivity extends AppCompatActivity {

    @Inject
    Compass compass;

    @Bind(R.id.arrow)
    View arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        DependencyInjector.getGraph().inject(this);
        compass.setView(arrow);
    }

    @Override
    protected void onStart() {
        super.onStart();
        compass.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        compass.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compass.destroy();
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
}
