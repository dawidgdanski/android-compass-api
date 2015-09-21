package pl.dawidgdanski.compass.compassapi.broadcast;

import java.util.Collection;

public interface OnLocationProviderStateChangeListener {
    void onLocationProvidersStateChange(Collection<String> availableProviders);
}
