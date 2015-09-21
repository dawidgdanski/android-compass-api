package pl.dawidgdanski.compass.compassapi.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import pl.dawidgdanski.compass.compassapi.util.CompassPreconditions;

public class CompassPermissions {

    public static void check(final Context context) {

        final PackageManager packageManager = context.getPackageManager();

        final String packageName = context.getPackageName();

        try {
            checkPermission(packageManager,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    packageName);
            return;
        } catch (IllegalStateException e) {
        }

        checkPermission(packageManager,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                packageName);
    }

    private static void checkPermission(final PackageManager packageManager,
                                        final String permission,
                                        final String packageName) {
        final boolean isPermissionPresent =
                packageManager.checkPermission(permission, packageName) == PackageManager.PERMISSION_GRANTED;

        CompassPreconditions.checkState(isPermissionPresent,
                String.format("%s is not set in AndroidManifest.xml", permission));
    }

}
