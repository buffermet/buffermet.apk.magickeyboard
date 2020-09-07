package buffermet.apk.magickeyboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Typeface;
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

public class AppsListActivity extends AppCompatActivity {
    private JSONObject app;

    private float pixelDensity;

    final private String newConfig =
        "{" +
            "\"fill_background\":"                 + "\"000000\"," +
            "\"fill_key\":"                        + "\"000000\"," +
            "\"fill_key_pressed\":"                + "\"131313\"," +
            "\"fill_key_pressed_special_symbol\":" + "\"444444\"," +
            "\"fill_key_pressed_symbol\":"         + "\"FFFFFF\"," +
            "\"fill_key_special_symbol\":"         + "\"444444\"," +
            "\"fill_key_symbol\":"                 + "\"FFFFFF\"," +
            "\"fill_modifier\":"                   + "\"000000\"," +
            "\"fill_modifier_pressed\":"           + "\"131313\"," +
            "\"fill_modifier_pressed_symbol\":"    + "\"444444\"," +
            "\"fill_modifier_symbol\":"            + "\"444444\"," +
            "\"fill_toggle\":"                     + "\"60BF00\"," +
            "\"fill_toggle_pressed\":"             + "\"60BF00\"," +
            "\"fill_toggle_pressed_symbol\":"      + "\"000000\"," +
            "\"fill_toggle_symbol\":"              + "\"000000\"," +
            "\"hold_duration\":"                   + "300," +
            "\"key_radius\":"                      + "4," +
            "\"layout\":"                          + "0," +
            "\"name\":"                            + "\"Untitled\"," +
            "\"repeat_backspace\":"                + "1," +
            "\"repeat_spacebar\":"                 + "1," +
            "\"repeat_rate\":"                     + "30," +
            "\"select_horizontal\":"               + "1," +
            "\"select_vertical\":"                 + "1," +
            "\"sensitivity_horizontal\":"          + "40," +
            "\"sensitivity_vertical\":"            + "198," +
            "\"swipe_horizontal\":"                + "1," +
            "\"swipe_horizontal_only_text\":"      + "1," +
            "\"swipe_vertical\":"                  + "1," +
            "\"swipe_vertical_only_text\":"        + "1" +
        "}";

    private void startKeyboardsListActivity() {
        final Intent keyboardsListIntent = new Intent(
            AppsListActivity.this,
            KeyboardsListActivity.class);

        startActivity(keyboardsListIntent);
        overridePendingTransition(
            R.anim.translate_right_enter,
            R.anim.translate_left_exit);
    }

    private void addRule(final String packageName, final String configID) {
        try {
            final JSONObject rules = app.getJSONObject("rules");
            final JSONArray rulePackageNames = rules.names();

            JSONObject newRules = new JSONObject();
            JSONArray configs = new JSONArray();
            if (JSONService.containsString(rulePackageNames, packageName)) {
                newRules = rules.getJSONObject(packageName);
                configs = newRules.getJSONArray("configs");
            } else {
                newRules.put("current", configID);
            }
            configs.put(configID);

            newRules.put("configs", configs);

            rules.put(packageName, newRules);

            JSONService.saveJSON(app);
        } catch (Exception e) {
            DebugService.logStackTrace(e);
        }
    }

    private void loadApps() {
        try {
            app = JSONService.loadJSON();

            final LinearLayout linearLayoutAppsList = findViewById(R.id.linearLayoutAppsList);

            final JSONObject keyboards = app.getJSONObject("keyboards");
            final JSONArray keyboardIDs = keyboards.names();
            final String magicKeyboardPackageName = "buffermet.apk.magickeyboard";
            String newKeyboardID = "0";
            if (keyboardIDs != null) {
                for (int i = 0; i < 9999; i++) {
                    final String thisID = String.valueOf(i);
                    if (!JSONService.containsString(keyboardIDs, thisID)) {
                        newKeyboardID = thisID;
                    }
                }
            }

            final PackageManager pm = getPackageManager();
            for (final ResolveInfo resolveInfo : PackageService.installedApps) {
                final String editingPackageName = resolveInfo.activityInfo.packageName;

                final LinearLayout linearLayout = new LinearLayout(this);
                final LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
                linearLayout.setLayoutParams(linearLayoutParams);
                linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_black));
                final String finalNewKeyboardID = newKeyboardID;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                            final SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("editingConfigID", finalNewKeyboardID);
                            editor.putString("editingConfig", newConfig);
                            if (editingPackageName.equals(magicKeyboardPackageName)) {
                                editor.putString("editingPackageName", "*");
                                addRule("*", finalNewKeyboardID);
                            } else {
                                editor.putString("editingPackageName", editingPackageName);
                                addRule(editingPackageName, finalNewKeyboardID);
                            }
                            editor.commit();

                            final Intent settingsIntent = new Intent(AppsListActivity.this, SettingsActivity.class);
                            startActivity(settingsIntent);
                            overridePendingTransition(R.anim.translate_left_enter, R.anim.translate_right_exit);

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
                imageView.setImageDrawable(resolveInfo.loadIcon(pm));

                final TextView textView = new TextView(this);
                final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
                textViewParams.weight = 1;
                textViewParams.setMargins(
                    0,
                    0,
                    (int) (16 * pixelDensity),
                    0);
                textView.setLayoutParams(textViewParams);
                textView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
                if (editingPackageName.equals(magicKeyboardPackageName)) {
                    textView.setText("All apps");
                } else {
                    textView.setText(resolveInfo.loadLabel(pm));
                }
                textView.setTextSize(20);
                textView.setTextColor(Color.parseColor("#ffffff"));
                textView.setSingleLine(true);
                textView.setEllipsize(TextUtils.TruncateAt.END);

                linearLayout.addView(imageView);
                linearLayout.addView(textView);

                if (editingPackageName.equals(magicKeyboardPackageName)) {
                    linearLayoutAppsList.addView(linearLayout, 0);
                } else {
                    linearLayoutAppsList.addView(linearLayout);
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
                startKeyboardsListActivity();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        startKeyboardsListActivity();
        finish();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);

        pixelDensity = getResources().getDisplayMetrics().density;

        initUI();
        loadApps();
    }
}
