package pl.dawidgdanski.compass.util;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

public final class Intents {

    private Intents() {

    }

    public static Intent newActivityIntent(final Context context,
                                           final Class<? extends Activity> activityClass) {
        return new Intent(context, activityClass);
    }

    public static Intent newServiceIntent(final Context context,
                                          final Class<? extends Service> serviceClass) {
        return new Intent(context, serviceClass);
    }

    public static Intent newBroadcastIntent(final String action) {
        return new Intent(action);
    }

}
