package buffermet.apk.magickeyboard;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PackageService {
    static List<ResolveInfo> installedApps;
    static List<ResolveInfo> allApps;

    static void loadInstalledApps(Context ctxt) {
        final PackageManager pm = ctxt.getPackageManager();

        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        installedApps = pm.queryIntentActivities(mainIntent, 0);

        final PackageItemInfo.DisplayNameComparator comparator = new
          PackageItemInfo.DisplayNameComparator(pm);
        Collections.sort(installedApps, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo lhs, ResolveInfo rhs) {
                return comparator.compare(lhs.activityInfo, rhs.activityInfo);
            }
        });
    }

    protected static void loadAllApps(Context ctxt) {
        ctxt.getPackageManager();
    }
}
