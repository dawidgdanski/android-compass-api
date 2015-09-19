package pl.dawidgdanski.compass.ui.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

abstract class AbstractLoader<D> extends AsyncTaskLoader<D> {

    private D data;

    public AbstractLoader(Context context) {
        super(context);
    }

    @Override
    public void deliverResult(D data) {
        if(isReset()) {
            releaseResources(data);
            return;
        }

        D oldResult = this.data;
        this.data = data;

        if(isStarted()) {
            if(oldResult != data) {
                onNewDataDelivered(data);
            }
            super.deliverResult(data);
        }

        if(oldResult != data && oldResult != null) {
            releaseResources(oldResult);
        }
    }

    @Override
    public void onCanceled(D data) {
        super.onCanceled(data);
        releaseResources(data);
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();
        releaseResources(data);
        data = null;
    }

    @Override
    protected void onStartLoading() {
        if(data != null) {
            deliverResult(data);
        }

        if(takeContentChanged() || data == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    protected void releaseResources(D data) {

    }

    protected void onNewDataDelivered(D data) {
    }
}
