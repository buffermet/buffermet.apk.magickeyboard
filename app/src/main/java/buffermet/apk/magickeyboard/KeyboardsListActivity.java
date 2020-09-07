package buffermet.apk.magickeyboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class KeyboardsListActivity extends AppCompatActivity {
  private float pixelDensity;

  private Drawable getAppIcon(final String packageName) {
    try {
      return getPackageManager().getApplicationIcon(packageName);
    } catch (Exception ignore) {}
    return null;
  }

  private void loadKeyboards() {
    try {
      final LinearLayout linearLayoutKeyboardsList = findViewById(R.id.linearLayoutKeyboardsList);
      linearLayoutKeyboardsList.removeAllViews();

      final JSONObject app = JSONService.loadJSON();
      final JSONObject keyboards = app.getJSONObject("keyboards");
      final JSONObject rules = app.getJSONObject("rules");
      final JSONArray rulesPackageNames = rules.names();
      if (rulesPackageNames != null) {
        for (int i = 0; i < rulesPackageNames.length(); i++) {
          final String thisPackageName = rulesPackageNames.getString(i);

          final JSONArray thisPackageConfigs = rules.getJSONObject(
            thisPackageName).getJSONArray("configs");

          for (int ii = 0; ii < thisPackageConfigs.length(); ii++) {
            final String thisConfigID = thisPackageConfigs.getString(ii);

            if (!thisConfigID.equals("-1")) {
              final JSONObject thisKeyboardConfig = keyboards.getJSONObject(thisConfigID);

              Drawable drawableIcon;
              if (thisPackageName.equals("*")) {
                drawableIcon = getResources().getDrawable(R.mipmap.ic_launcher);
              } else {
                drawableIcon = getAppIcon(thisPackageName);
              }

              if (drawableIcon != null) {
                final LinearLayout linearLayout = new LinearLayout(this);
                final LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.MATCH_PARENT,
                  LinearLayout.LayoutParams.MATCH_PARENT);
                linearLayout.setLayoutParams(linearLayoutParams);
                linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_black));
                linearLayout.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    try {
                      final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                      final SharedPreferences.Editor editor = prefs.edit();
                      editor.putString(
                        "editingConfigID",
                        thisConfigID);
                      editor.putString(
                        "editingConfig",
                        keyboards.getJSONObject(thisConfigID).toString());
                      editor.putString(
                        "editingPackageName",
                        thisPackageName);
                      editor.apply();

                      final Intent settingsIntent = new Intent(
                        KeyboardsListActivity.this,
                        SettingsActivity.class);

                      startActivity(settingsIntent);
                      overridePendingTransition(
                        R.anim.translate_left_enter,
                        R.anim.translate_right_exit);

                      finish();
                    } catch (Exception e) {
                      DebugService.logStackTrace(e);
                    }
                  }
                });

                final ImageView imageView = new ImageView(this);
                final LinearLayout.LayoutParams imageViewParams = new LinearLayout.LayoutParams(
                  (int) (32 * pixelDensity),
                  (int) (32 * pixelDensity));
                imageViewParams.setMargins(
                  (int) (16 * pixelDensity),
                  (int) (16 * pixelDensity),
                  (int) (16 * pixelDensity),
                  (int) (16 * pixelDensity));
                imageView.setLayoutParams(imageViewParams);
                imageView.setImageDrawable(drawableIcon);

                final LinearLayout linearLayoutInner = new LinearLayout(this);
                final LinearLayout.LayoutParams linearLayoutInnerParams = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT,
                  LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayoutInner.setLayoutParams(linearLayoutInnerParams);
                linearLayoutInner.setOrientation(LinearLayout.VERTICAL);

                final String keyboardName = thisKeyboardConfig.getString("name");

                final TextView textViewName = new TextView(this);
                final LinearLayout.LayoutParams textViewNameParams = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT,
                  LinearLayout.LayoutParams.WRAP_CONTENT);
                textViewNameParams.weight = 1;
                textViewNameParams.setMargins(
                  0,
                  0,
                  (int) (16 * pixelDensity),
                  0);
                textViewName.setLayoutParams(textViewNameParams);
                textViewName.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
                textViewName.setText(keyboardName);
                textViewName.setTextSize(20);
                textViewName.setTextColor(Color.parseColor("#ffffff"));
                textViewName.setSingleLine(true);
                textViewName.setEllipsize(TextUtils.TruncateAt.END);

                final TextView textViewPackage = new TextView(this);
                final LinearLayout.LayoutParams textViewPackageParams = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT,
                  LinearLayout.LayoutParams.WRAP_CONTENT);
                textViewPackageParams.setMargins(
                  0,
                  0,
                  (int) (16 * pixelDensity),
                  0);
                textViewPackage.setLayoutParams(textViewPackageParams);
                textViewPackage.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
                if (thisPackageName.equals("*")) {
                  textViewPackage.setText("All apps");
                } else {
                  textViewPackage.setText(thisPackageName);
                }
                textViewPackage.setTextSize(14);
                textViewPackage.setTextColor(Color.parseColor("#999999"));

                linearLayoutInner.addView(textViewName);
                linearLayoutInner.addView(textViewPackage);

                linearLayout.addView(imageView);
                linearLayout.addView(linearLayoutInner);

                linearLayoutKeyboardsList.addView(linearLayout);
              }
            }
          }
        }
      }
    } catch (Exception e) {
      DebugService.logStackTrace(e);
    }
  }

  private void initUI() {
    final RelativeLayout relativeLayoutBackButton = findViewById(R.id.relativeLayoutBackButton);
    relativeLayoutBackButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        KeyboardsListActivity.super.onBackPressed();
      }
    });

    final RelativeLayout relativeLayoutAddKeyboardButton = findViewById(R.id.relativeLayoutAddKeyboardButton);
    relativeLayoutAddKeyboardButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        final Intent appsListIntent = new Intent(KeyboardsListActivity.this, AppsListActivity.class);
        startActivity(appsListIntent);
        overridePendingTransition(
          R.anim.translate_left_enter,
          R.anim.translate_right_exit);

        finish();
      }
    });
  }

  @Override
  public void onBackPressed() {
    finish();
  }

  @Override
  protected void onNewIntent(final Intent intent) {
    super.onNewIntent(intent);
    loadKeyboards();
  }

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_keyboards_list);

    pixelDensity = getResources().getDisplayMetrics().density;

    initUI();
    loadKeyboards();

    new Thread(new Runnable() {
      @Override
      public void run() {
        PackageService.loadInstalledApps(AppContext.getContext());
      }
    }).start();
  }
}
