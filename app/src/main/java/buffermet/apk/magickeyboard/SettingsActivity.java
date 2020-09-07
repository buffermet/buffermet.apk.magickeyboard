package buffermet.apk.magickeyboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SettingsActivity extends AppCompatActivity {
  private JSONObject app;
  private JSONObject keyboards;

  private String editingConfigID;
  private String editingPackageName;

  private int colorPickerHexColor;
  private int[] colorPickerRgbColor;
  private int currentColoredItem;

  private RelativeLayout relativeLayoutColorDotBackground;
  private RelativeLayout relativeLayoutColorDotToggle;
  private RelativeLayout relativeLayoutColorDotTogglePressed;
  private RelativeLayout relativeLayoutColorDotTogglePressedSymbol;
  private RelativeLayout relativeLayoutColorDotToggleSymbol;
  private RelativeLayout relativeLayoutColorDotKey;
  private RelativeLayout relativeLayoutColorDotKeyPressed;
  private RelativeLayout relativeLayoutColorDotKeyPressedSpecialSymbol;
  private RelativeLayout relativeLayoutColorDotKeyPressedSymbol;
  private RelativeLayout relativeLayoutColorDotKeySpecialSymbol;
  private RelativeLayout relativeLayoutColorDotKeySymbol;
  private RelativeLayout relativeLayoutColorDotModifier;
  private RelativeLayout relativeLayoutColorDotModifierPressed;
  private RelativeLayout relativeLayoutColorDotModifierPressedSymbol;
  private RelativeLayout relativeLayoutColorDotModifierSymbol;
  private String fillBackground;
  private String fillKey;
  private String fillKeyPressed;
  private String fillKeyPressedSpecialSymbol;
  private String fillKeyPressedSymbol;
  private String fillKeySpecialSymbol;
  private String fillKeySymbol;
  private String fillModifier;
  private String fillModifierPressed;
  private String fillModifierPressedSymbol;
  private String fillModifierSymbol;
  private String fillToggle;
  private String fillTogglePressed;
  private String fillTogglePressedSymbol;
  private String fillToggleSymbol;

  private RelativeLayout relativeLayoutPromptContainer;

  private float pixelDensity;

  private int[] hexToRgb(String hexColorString) {
    final int hexColor = Color.parseColor("#" + hexColorString);
    final int r = Color.red(hexColor);
    final int g = Color.green(hexColor);
    final int b = Color.blue(hexColor);
    return new int[]{r, g, b};
  }

  private int rgbToHex(int[] rgbColor) {
    return Color.rgb(
      rgbColor[0],
      rgbColor[1],
      rgbColor[2]);
  }

  private String rgbToHexString(int[] rgbColor) {
    final int hexColor = Color.rgb(
      rgbColor[0],
      rgbColor[1],
      rgbColor[2]);
    return Integer.toHexString(hexColor).substring(2,8);
  }

  private void saveColorPicker() {
    try {
      final JSONObject editingConfig = app
        .optJSONObject("keyboards")
        .optJSONObject(editingConfigID);

      switch (currentColoredItem) {
        case 0: // Background
          fillBackground = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotBackground.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_background", rgbToHexString(colorPickerRgbColor));
          break;
        case 1: // Key
          fillKey = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotKey.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_key", rgbToHexString(colorPickerRgbColor));
          break;
        case 2: // KeyPressed
          fillKeyPressed = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotKeyPressed.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_key_pressed", rgbToHexString(colorPickerRgbColor));
          break;
        case 3: // KeyPressedSpecialSymbol
          fillKeyPressedSpecialSymbol = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotKeyPressedSpecialSymbol.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_key_pressed_special_symbol", rgbToHexString(colorPickerRgbColor));
          break;
        case 4: // KeyPressedSymbol
          fillKeyPressedSymbol = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotKeyPressedSymbol.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_key_pressed_symbol", rgbToHexString(colorPickerRgbColor));
          break;
        case 5: // KeySpecialSymbol
          fillKeySpecialSymbol = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotKeySpecialSymbol.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_key_special_symbol", rgbToHexString(colorPickerRgbColor));
          break;
        case 6: // KeySymbol
          fillKeySymbol = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotKeySymbol.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_key_symbol", rgbToHexString(colorPickerRgbColor));
          break;
        case 7: // Modifier
          fillModifier = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotModifier.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_modifier", rgbToHexString(colorPickerRgbColor));
          break;
        case 8: // ModifierPressed
          fillModifierPressed = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotModifierPressed.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_modifier_pressed", rgbToHexString(colorPickerRgbColor));
          break;
        case 9: // ModifierPressedSymbol
          fillModifierPressedSymbol = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotModifierPressedSymbol.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_modifier_pressed_symbol", rgbToHexString(colorPickerRgbColor));
          break;
        case 10: // ModifierSymbol
          fillModifierSymbol = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotModifierSymbol.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_modifier_symbol", rgbToHexString(colorPickerRgbColor));
          break;
        case 11: // Toggle
          fillToggle = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotToggle.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_toggle", rgbToHexString(colorPickerRgbColor));
          break;
        case 12: // TogglePressed
          fillTogglePressed = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotTogglePressed.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_toggle_pressed", rgbToHexString(colorPickerRgbColor));
          break;
        case 13: // TogglePressedSymbol
          fillTogglePressedSymbol = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotTogglePressedSymbol.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_toggle_pressed_symbol", rgbToHexString(colorPickerRgbColor));
          break;
        case 14: // ToggleSymbol
          fillToggleSymbol = rgbToHexString(colorPickerRgbColor);
          relativeLayoutColorDotToggleSymbol.getBackground().setColorFilter(
            colorPickerHexColor,
            PorterDuff.Mode.SRC_ATOP);
          editingConfig.put("fill_ToggleSymbol", rgbToHexString(colorPickerRgbColor));
          break;
      }

      JSONService.saveJSON(app);

      hideSoftKeyboard();
    } catch (Exception e) {
      DebugService.logStackTrace(e);
    }
  }

  private LinearLayout newColorPicker(String hexColorString) {
    final LinearLayout colorPicker = (LinearLayout) getLayoutInflater().inflate(
      R.layout.color_picker,
      null);

    colorPickerHexColor = Color.parseColor("#" + hexColorString);
    colorPickerRgbColor = hexToRgb(hexColorString);

    final RelativeLayout relativeLayoutColorPickerColorDot = colorPicker.findViewById(
      R.id.relativeLayoutColorPickerColorDot);
    relativeLayoutColorPickerColorDot.getBackground().setColorFilter(
      Color.parseColor("#" + rgbToHexString(colorPickerRgbColor)),
      PorterDuff.Mode.SRC_ATOP);

    final EditText editTextColorPickerHexValue = colorPicker.findViewById(
      R.id.editTextColorPickerHexValue);
    final SeekBar seekBarRedColor = colorPicker.findViewById(R.id.seekBarRedColor);
    final SeekBar seekBarGreenColor = colorPicker.findViewById(R.id.seekBarGreenColor);
    final SeekBar seekBarBlueColor = colorPicker.findViewById(R.id.seekBarBlueColor);
    final TextView textViewRedValue = colorPicker.findViewById(R.id.textViewRedValue);
    final TextView textViewGreenValue = colorPicker.findViewById(R.id.textViewGreenValue);
    final TextView textViewBlueValue = colorPicker.findViewById(R.id.textViewBlueValue);
    final TextWatcher textWatcherColorPickerHexValue = new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        final String hexColorString = s.toString();
        if (Pattern.compile("[a-fA-F0-9]{6}").matcher(hexColorString).matches()) {
          final int[] rgbColor = hexToRgb(hexColorString);
          final int selectionEnd = editTextColorPickerHexValue.getSelectionEnd();
          seekBarRedColor.setProgress(rgbColor[0]);
          seekBarGreenColor.setProgress(rgbColor[1]);
          seekBarBlueColor.setProgress(rgbColor[2]);
          editTextColorPickerHexValue.setSelection(selectionEnd);
          final int hexColor = rgbToHex(rgbColor);
          relativeLayoutColorPickerColorDot.getBackground().setColorFilter(
            hexColor,
            PorterDuff.Mode.SRC_ATOP);
          saveColorPicker();
        }
      }
      @Override
      public void afterTextChanged(Editable s) {}
    };
    editTextColorPickerHexValue.setText(hexColorString);
    editTextColorPickerHexValue.addTextChangedListener(textWatcherColorPickerHexValue);
    seekBarRedColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textViewRedValue.setText(String.valueOf(progress));
        colorPickerRgbColor = new int[]{
          progress,
          colorPickerRgbColor[1],
          colorPickerRgbColor[2]};
        colorPickerHexColor = rgbToHex(colorPickerRgbColor);
        relativeLayoutColorPickerColorDot.getBackground().setColorFilter(
          rgbToHex(colorPickerRgbColor),
          PorterDuff.Mode.SRC_ATOP);
        editTextColorPickerHexValue.setText(rgbToHexString(colorPickerRgbColor));
      }
      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        editTextColorPickerHexValue.removeTextChangedListener(textWatcherColorPickerHexValue);
      }
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        saveColorPicker();
        editTextColorPickerHexValue.addTextChangedListener(textWatcherColorPickerHexValue);
      }
    });
    seekBarGreenColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textViewGreenValue.setText(String.valueOf(progress));
        colorPickerRgbColor = new int[]{
          colorPickerRgbColor[0],
          progress,
          colorPickerRgbColor[2]};
        colorPickerHexColor = rgbToHex(colorPickerRgbColor);
        relativeLayoutColorPickerColorDot.getBackground().setColorFilter(
          rgbToHex(colorPickerRgbColor),
          PorterDuff.Mode.SRC_ATOP);
        editTextColorPickerHexValue.setText(rgbToHexString(colorPickerRgbColor));
      }
      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        editTextColorPickerHexValue.removeTextChangedListener(textWatcherColorPickerHexValue);
      }
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        saveColorPicker();
        editTextColorPickerHexValue.addTextChangedListener(textWatcherColorPickerHexValue);
      }
    });
    seekBarBlueColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        textViewBlueValue.setText(String.valueOf(progress));
        colorPickerRgbColor = new int[]{
          colorPickerRgbColor[0],
          colorPickerRgbColor[1],
          progress};
        colorPickerHexColor = rgbToHex(colorPickerRgbColor);
        relativeLayoutColorPickerColorDot.getBackground().setColorFilter(
          rgbToHex(colorPickerRgbColor),
          PorterDuff.Mode.SRC_ATOP);
        editTextColorPickerHexValue.setText(rgbToHexString(colorPickerRgbColor));
      }
      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        editTextColorPickerHexValue.removeTextChangedListener(textWatcherColorPickerHexValue);
      }
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        saveColorPicker();
        editTextColorPickerHexValue.addTextChangedListener(textWatcherColorPickerHexValue);
      }
    });
    seekBarRedColor.setProgress(colorPickerRgbColor[0]);
    seekBarGreenColor.setProgress(colorPickerRgbColor[1]);
    seekBarBlueColor.setProgress(colorPickerRgbColor[2]);

    return colorPicker;
  }

  private void showPromptColorPicker(String hexColorString) {
    final LinearLayout colorPicker = newColorPicker(hexColorString);

    String promptTitle = "";
    switch (currentColoredItem) {
      case 0:
        promptTitle = "Background";
        break;
      case 1:
        promptTitle = "Key";
        break;
      case 2:
        promptTitle = "Key pressed";
        break;
      case 3:
        promptTitle = "Key pressed special symbol";
        break;
      case 4:
        promptTitle = "Key pressed symbol";
        break;
      case 5:
        promptTitle = "Key special symbol";
        break;
      case 6:
        promptTitle = "Key symbol";
        break;
      case 7:
        promptTitle = "Modifier";
        break;
      case 8:
        promptTitle = "Modifier pressed";
        break;
      case 9:
        promptTitle = "Modifier pressed symbol";
        break;
      case 10:
        promptTitle = "Modifier symbol";
        break;
      case 11:
        promptTitle = "Toggle";
        break;
      case 12:
        promptTitle = "Toggle pressed";
        break;
      case 13:
        promptTitle = "Toggle pressed symbol";
        break;
      case 14:
        promptTitle = "Toggle symbol";
        break;
    }

    final ArrayList<PromptService.PromptButton> buttons = new ArrayList<>();
    final PromptService.PromptButton button = new PromptService.PromptButton();
    button.action = new Runnable() {
      @Override
      public void run() {
        PromptService.hidePrompt(relativeLayoutPromptContainer);
      }
    };
    button.text = "DONE";
    buttons.add(button);

    PromptService.showViewPrompt(
      this,
      promptTitle,
      colorPicker,
      buttons,
      relativeLayoutPromptContainer);
  }

  private void showPromptShareTheme() {
    final String theme =
      fillBackground + "," +
        fillKey + "," +
        fillKeyPressed + "," +
        fillKeyPressedSpecialSymbol + "," +
        fillKeyPressedSymbol + "," +
        fillKeySpecialSymbol + "," +
        fillKeySymbol + "," +
        fillModifier + "," +
        fillModifierPressed + "," +
        fillModifierPressedSymbol + "," +
        fillModifierSymbol + "," +
        fillToggle + "," +
        fillTogglePressed + "," +
        fillTogglePressedSymbol + "," +
        fillToggleSymbol;

    final Pattern regexpWhitespace = Pattern.compile("\\s");
    final Pattern regexpTheme = Pattern.compile(
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}," +
      "\\s{0,16}[a-fA-F0-9]{6}\\s{0,16}");

    final LinearLayout linearLayoutViewMessage = new LinearLayout(SettingsActivity.this);
    final LinearLayout.LayoutParams linearLayoutViewMessageParams = new LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.WRAP_CONTENT,
      LinearLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutViewMessage.setLayoutParams(linearLayoutViewMessageParams);
    linearLayoutViewMessage.setOrientation(LinearLayout.VERTICAL);

    final View viewWidthBar = new View(this);
    final LinearLayout.LayoutParams viewWidthBarParams = new LinearLayout.LayoutParams(
      (int) (320 * pixelDensity),
      0);
    viewWidthBar.setLayoutParams(viewWidthBarParams);

    final TextView textView = new TextView(SettingsActivity.this);
    final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.WRAP_CONTENT,
      LinearLayout.LayoutParams.WRAP_CONTENT);
    textViewParams.setMargins(
      0,
      0,
      0,
      (int) (16 * pixelDensity));
    textView.setLayoutParams(textViewParams);
    textView.setTextSize(18f);
    textView.setTextColor(Color.WHITE);
    textView.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
    textView.setText("Copy your theme or paste one below.");

    final EditText editText = new EditText(SettingsActivity.this);
    final LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
      LinearLayout.LayoutParams.MATCH_PARENT,
      LinearLayout.LayoutParams.WRAP_CONTENT);
    editText.setLayoutParams(editTextParams);
    editText.setBackgroundDrawable(getResources().getDrawable(R.drawable.input_black));
    editText.setSingleLine(true);
    editText.setTextColor(Color.WHITE);
    editText.setText(theme);
    editText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {}
      @Override
      public void afterTextChanged(Editable s) {
        if (regexpTheme.matcher(s).matches()) {
          final String strippedTheme = regexpWhitespace.matcher(s).replaceAll("");
          final String[] themeHexValues = strippedTheme.split(",");

          relativeLayoutColorDotBackground.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[0]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotKey.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[1]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotKeyPressed.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[2]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotKeyPressedSpecialSymbol.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[3]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotKeyPressedSymbol.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[4]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotKeySpecialSymbol.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[5]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotKeySymbol.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[6]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotModifier.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[7]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotModifierPressed.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[8]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotModifierPressedSymbol.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[9]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotModifierSymbol.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[10]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotToggle.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[11]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotTogglePressed.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[12]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotTogglePressedSymbol.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[13]), PorterDuff.Mode.SRC_ATOP);
          relativeLayoutColorDotToggleSymbol.getBackground().setColorFilter(Color.parseColor(
            "#" + themeHexValues[14]), PorterDuff.Mode.SRC_ATOP);
        }
      }
    });

    linearLayoutViewMessage.addView(viewWidthBar);
    linearLayoutViewMessage.addView(textView);
    linearLayoutViewMessage.addView(editText);

    final ArrayList<PromptService.PromptButton> buttons = new ArrayList<>();
    final PromptService.PromptButton button1 = new PromptService.PromptButton();
    button1.action = new Runnable() {
      @Override
      public void run() {
        PromptService.hidePrompt(relativeLayoutPromptContainer);
      }
    };
    button1.text = "DONE";
    buttons.add(button1);

    PromptService.showViewPrompt(
      SettingsActivity.this,
      "Share themes",
      linearLayoutViewMessage,
      buttons,
      relativeLayoutPromptContainer);

    editText.requestFocus();
    editText.selectAll();
    final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
    if (imm != null) {
      imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }
  }

  private void showPromptConfirmDeletion() {
    final ArrayList<PromptService.PromptButton> buttons = new ArrayList<>();

    final PromptService.PromptButton button1 = new PromptService.PromptButton();
    button1.action = new Runnable() {
      @Override
      public void run() {
        PromptService.hidePrompt(relativeLayoutPromptContainer);
        deleteConfig(editingConfigID);
        deleteRule(editingPackageName, editingConfigID);
        startKeyboardListActivity();
      }
    };
    button1.text = "YES";

    final PromptService.PromptButton button2 = new PromptService.PromptButton();
    button2.action = new Runnable() {
      @Override
      public void run() {
        PromptService.hidePrompt(relativeLayoutPromptContainer);
      }
    };
    button2.text = "NO";

    buttons.add(button1);
    buttons.add(button2);

    PromptService.showPrompt(
      this,
      "Warning",
      "Are you sure you want to delete this keyboard?",
      buttons,
      relativeLayoutPromptContainer);
  }

  private void startKeyboardListActivity() {
    final Intent keyboardsListIntent = new Intent(
      SettingsActivity.this,
      KeyboardsListActivity.class);
    startActivity(keyboardsListIntent);
    overridePendingTransition(
      R.anim.translate_right_enter,
      R.anim.translate_left_exit);
    finish();
  }

  private void hideSoftKeyboard() {
    final InputMethodManager imm = (InputMethodManager) getSystemService(
      Activity.INPUT_METHOD_SERVICE);
    if (imm != null) {
      View v = getCurrentFocus();
      if (v == null) {
        v = new View(this);
      }
      imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
      v.clearFocus();
    }
  }

  private void saveConfig(final String keyboardID, final JSONObject config) {
    try {
      keyboards.put(keyboardID, config);
      JSONService.saveJSON(app);
    } catch (final Exception e) {
      DebugService.logStackTrace(e);
    }
  }

  private void deleteConfig(final String keyboardID) {
    try {
      keyboards.remove(keyboardID);
      JSONService.saveJSON(app);
    } catch (final Exception e) {
      DebugService.logStackTrace(e);
    }
  }

  private void deleteRule(final String packageName, final String keyboardID) {
    try {
      final JSONObject rules = app.getJSONObject("rules");
      final JSONArray rulesPackages = rules.names();

      if (rulesPackages != null) {
        for (int i = 0; i < rulesPackages.length(); i++) {
          final String thisRulePackageName = rulesPackages.getString(i);
          final JSONObject thisPackageRules = rules.getJSONObject(thisRulePackageName);
          final JSONArray thisPackageConfigs = thisPackageRules.getJSONArray("configs");

          if (thisPackageConfigs.length() > 0) {
            if (thisRulePackageName.equals(packageName)) {
              JSONArray newKeyboardIDs = new JSONArray();
              for (int ii = 0; ii < thisPackageConfigs.length(); ii++) {
                final String thisKeyboardID = thisPackageConfigs.getString(ii);

                if (!thisKeyboardID.equals(keyboardID)) {
                  newKeyboardIDs.put(thisKeyboardID);
                }
              }

              if (newKeyboardIDs.length() != 0) {
                thisPackageRules.put("configs", newKeyboardIDs);
              } else {
                rules.remove(packageName);
              }

              JSONService.saveJSON(app);
            }
          } else {
            rules.remove(thisRulePackageName);
            JSONService.saveJSON(app);
          }
        }
      }
    } catch (final Exception e) {
      DebugService.logStackTrace(e);
    }
  }

  private void loadValues() {
    try {
      app = JSONService.loadJSON();
      keyboards = app.getJSONObject("keyboards");

      final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
      editingConfigID= prefs.getString("editingConfigID", "");
      editingPackageName = prefs.getString("editingPackageName", "");
      final JSONObject editingConfig = new JSONObject(
        prefs.getString("editingConfig", ""));

      if (
        !editingConfigID.equals("")
          && !editingPackageName.equals("")
      ) {
        fillBackground = editingConfig.getString("fill_background");
        fillKey = editingConfig.getString("fill_key");
        fillKeyPressed = editingConfig.getString("fill_key_pressed");
        fillKeyPressedSpecialSymbol = editingConfig.getString("fill_key_pressed_special_symbol");
        fillKeyPressedSymbol = editingConfig.getString("fill_key_pressed_symbol");
        fillKeySpecialSymbol = editingConfig.getString("fill_key_special_symbol");
        fillKeySymbol = editingConfig.getString("fill_key_symbol");
        fillModifier = editingConfig.getString("fill_modifier");
        fillModifierPressed = editingConfig.getString("fill_modifier_pressed");
        fillModifierPressedSymbol = editingConfig.getString("fill_modifier_pressed_symbol");
        fillModifierSymbol = editingConfig.getString("fill_modifier_symbol");
        fillToggle = editingConfig.getString("fill_toggle");
        fillTogglePressed = editingConfig.getString("fill_toggle_pressed");
        fillTogglePressedSymbol = editingConfig.getString("fill_toggle_pressed_symbol");
        fillToggleSymbol = editingConfig.getString("fill_toggle_symbol");
        final int holdDuration = editingConfig.getInt("hold_duration");
        final int holdDurationConverted = ((holdDuration - 100) / 20);
        final int repeatBackspace = editingConfig.getInt("repeat_backspace");
        final int repeatSpaceBar = editingConfig.getInt("repeat_spacebar");
        final int repeatRate = editingConfig.getInt("repeat_rate");
        final int swipeHorizontal = editingConfig.getInt("swipe_horizontal");
        final int swipeVertical = editingConfig.getInt("swipe_vertical");
        final int selectHorizontal = editingConfig.getInt("select_horizontal");
        final int selectVertical = editingConfig.getInt("select_vertical");
        final int sensitivityHorizontal = editingConfig.getInt("sensitivity_horizontal");
        final int sensitivityVertical = editingConfig.getInt("sensitivity_vertical");
        final int sensitivityHorizontalConverted = (int) (((264 - sensitivityHorizontal) / 2.4) - 1);
        final int sensitivityVerticalConverted = (int) (((264 - sensitivityVertical) / 2.4) - 1);
        final int swipeHorizontalOnlyText = editingConfig.getInt("swipe_horizontal_only_text");
        final int swipeVerticalOnlyText = editingConfig.getInt("swipe_vertical_only_text");
        final String name = editingConfig.getString("name");

        final CheckBox checkBoxRepeatBackspace = findViewById(R.id.checkBoxRepeatBackspace);
        final CheckBox checkBoxRepeatSpacebar = findViewById(R.id.checkBoxRepeatSpacebar);
        final CheckBox checkBoxSwipeHorizontally = findViewById(R.id.checkBoxSwipeHorizontal);
        final CheckBox checkBoxSwipeVertical = findViewById(R.id.checkBoxSwipeVertical);
        final CheckBox checkBoxSelectHorizontal = findViewById(R.id.checkBoxSelectHorizontal);
        final CheckBox checkBoxSelectVertical = findViewById(R.id.checkBoxSelectVertical);
        final CheckBox checkBoxSwipeHorizontalOnlyText = findViewById(R.id.checkBoxSwipeHorizontalOnlyText);
        final CheckBox checkBoxSwipeVerticalOnlyText = findViewById(R.id.checkBoxSwipeVerticalOnlyText);
        relativeLayoutColorDotBackground = findViewById(R.id.relativeLayoutColorDotBackground);
        relativeLayoutColorDotKey = findViewById(R.id.relativeLayoutColorDotKey);
        relativeLayoutColorDotKeyPressed = findViewById(R.id.relativeLayoutColorDotKeyPressed);
        relativeLayoutColorDotKeyPressedSpecialSymbol = findViewById(R.id.relativeLayoutColorDotKeyPressedSpecialSymbol);
        relativeLayoutColorDotKeyPressedSymbol = findViewById(R.id.relativeLayoutColorDotKeyPressedSymbol);
        relativeLayoutColorDotKeySpecialSymbol = findViewById(R.id.relativeLayoutColorDotKeySpecialSymbol);
        relativeLayoutColorDotKeySymbol = findViewById(R.id.relativeLayoutColorDotKeySymbol);
        relativeLayoutColorDotModifier = findViewById(R.id.relativeLayoutColorDotModifier);
        relativeLayoutColorDotModifierPressed = findViewById(R.id.relativeLayoutColorDotModifierPressed);
        relativeLayoutColorDotModifierPressedSymbol = findViewById(R.id.relativeLayoutColorDotModifierPressedSymbol);
        relativeLayoutColorDotModifierSymbol = findViewById(R.id.relativeLayoutColorDotModifierSymbol);
        relativeLayoutColorDotToggle = findViewById(R.id.relativeLayoutColorDotToggle);
        relativeLayoutColorDotTogglePressed = findViewById(R.id.relativeLayoutColorDotTogglePressed);
        relativeLayoutColorDotTogglePressedSymbol = findViewById(R.id.relativeLayoutColorDotTogglePressedSymbol);
        relativeLayoutColorDotToggleSymbol = findViewById(R.id.relativeLayoutColorDotToggleSymbol);
        final EditText editTextRename = findViewById(R.id.editTextRename);
        final ImageView imageViewSelectAppIcon = findViewById(R.id.imageViewSelectAppIcon);
        final LinearLayout linearLayoutDeleteContainer = findViewById(R.id.linearLayoutDeleteContainer);
        final LinearLayout linearLayoutRepeatOptionsContainer = findViewById(R.id.linearLayoutRepeatOptionsContainer);
        final LinearLayout linearLayoutSwipeHorizontalOptionsContainer = findViewById(R.id.linearLayoutSwipeHorizontalOptionsContainer);
        final LinearLayout linearLayoutSwipeVerticalOptionsContainer = findViewById(R.id.linearLayoutSwipeVerticalOptionsContainer);
        final RelativeLayout relativeLayoutDeleteButton = findViewById(R.id.relativeLayoutDeleteButton);
        final RelativeLayout relativeLayoutFinishRenamingButton = findViewById(R.id.relativeLayoutFinishRenamingButton);
        final RelativeLayout relativeLayoutOpenKeyboardSettingsButton = findViewById(R.id.relativeLayoutOpenKeyboardSettingsButton);
        final RelativeLayout relativeLayoutRenameButton = findViewById(R.id.relativeLayoutRenameButton);
        final RelativeLayout relativeLayoutShareThemeButton = findViewById(R.id.relativeLayoutShareThemeButton);
        final RelativeLayout relativeLayoutSetInputMethodButton = findViewById(R.id.relativeLayoutSetInputMethodButton);
        final SeekBar seekBarHoldDuration = findViewById(R.id.seekBarHoldDuration);
        final SeekBar seekBarRepeatRate = findViewById(R.id.seekBarRepeatRate);
        final SeekBar seekBarSensitivityHorizontal = findViewById(R.id.seekBarSensitivityHorizontal);
        final SeekBar seekBarSensitivityVertical = findViewById(R.id.seekBarSensitivityVertical);
        final TextView textViewHoldDuration = findViewById(R.id.textViewHoldDuration);
        final TextView textViewKeyboardName = findViewById(R.id.textViewKeyboardName);
        final TextView textViewRepeatRate = findViewById(R.id.textViewRepeatRate);
        final TextView textViewSelectedAppName = findViewById(R.id.textViewSelectedAppName);
        final TextView textViewSensitivityHorizontal = findViewById(R.id.textViewSensitivityHorizontal);
        final TextView textViewSensitivityVertical = findViewById(R.id.textViewSensitivityVertical);
        final View.OnTouchListener onTouchListenerColorDot = new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
            final RelativeLayout parent = (RelativeLayout) v.getParent();
            switch (event.getAction()) {
              case MotionEvent.ACTION_DOWN:
                parent.animate().scaleX(0.75f).setDuration(90).setListener(null);
                parent.animate().scaleY(0.75f).setDuration(90).setListener(null);
                break;
              case MotionEvent.ACTION_UP:
              case MotionEvent.ACTION_CANCEL:
                parent.animate().scaleX(1f).setDuration(180).setListener(null);
                parent.animate().scaleY(1f).setDuration(180).setListener(null);
                break;
            }
            return false;
          }
        };
        checkBoxRepeatBackspace.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
              if (isChecked) {
                editingConfig.put("repeat_backspace", 1);
              } else {
                editingConfig.put("repeat_backspace", 0);
              }

              hideSoftKeyboard();
              saveConfig(editingConfigID, editingConfig);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        checkBoxRepeatSpacebar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
              if (isChecked) {
                editingConfig.put("repeat_spacebar", 1);
              } else {
                editingConfig.put("repeat_spacebar", 0);
              }

              hideSoftKeyboard();
              saveConfig(editingConfigID, editingConfig);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        checkBoxSwipeHorizontally.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
              if (isChecked) {
                editingConfig.put("swipe_horizontal", 1);
                linearLayoutSwipeHorizontalOptionsContainer.setVisibility(View.VISIBLE);
              } else {
                editingConfig.put("swipe_horizontal", 0);
                linearLayoutSwipeHorizontalOptionsContainer.setVisibility(View.GONE);
              }

              hideSoftKeyboard();
              saveConfig(editingConfigID, editingConfig);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        checkBoxSwipeVertical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
              if (isChecked) {
                editingConfig.put("swipe_vertical", 1);
                linearLayoutSwipeVerticalOptionsContainer.setVisibility(View.VISIBLE);
              } else {
                editingConfig.put("swipe_vertical", 0);
                linearLayoutSwipeVerticalOptionsContainer.setVisibility(View.GONE);
              }

              hideSoftKeyboard();
              saveConfig(editingConfigID, editingConfig);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        checkBoxSelectHorizontal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
              if (isChecked) {
                editingConfig.put("select_horizontal", 1);
              } else {
                editingConfig.put("select_horizontal", 0);
              }

              hideSoftKeyboard();
              saveConfig(editingConfigID, editingConfig);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        checkBoxSelectVertical.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
              if (isChecked) {
                editingConfig.put("select_vertical", 1);
              } else {
                editingConfig.put("select_vertical", 0);
              }

              hideSoftKeyboard();
              saveConfig(editingConfigID, editingConfig);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        checkBoxSwipeHorizontalOnlyText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
              if (isChecked) {
                editingConfig.put("swipe_horizontal_only_text", 1);
              } else {
                editingConfig.put("swipe_horizontal_only_text", 0);
              }

              hideSoftKeyboard();
              saveConfig(editingConfigID, editingConfig);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        checkBoxSwipeVerticalOnlyText.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
              if (isChecked) {
                editingConfig.put("swipe_vertical_only_text", 1);
              } else {
                editingConfig.put("swipe_vertical_only_text", 0);
              }

              hideSoftKeyboard();
              saveConfig(editingConfigID, editingConfig);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        relativeLayoutColorDotBackground.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotKey.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotKeyPressed.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotKeyPressedSpecialSymbol.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotKeyPressedSymbol.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotKeySpecialSymbol.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotKeySymbol.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotModifier.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotModifierPressed.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotModifierPressedSymbol.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotModifierSymbol.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotModifierSymbol.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotToggle.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotTogglePressed.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotTogglePressedSymbol.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotToggleSymbol.setOnTouchListener(onTouchListenerColorDot);
        relativeLayoutColorDotBackground.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 0;
            showPromptColorPicker(fillBackground);
          }
        });
        relativeLayoutColorDotKey.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 1;
            showPromptColorPicker(fillKey);
          }
        });
        relativeLayoutColorDotKeyPressed.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 2;
            showPromptColorPicker(fillKeyPressed);
          }
        });
        relativeLayoutColorDotKeyPressedSpecialSymbol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 3;
            showPromptColorPicker(fillKeyPressedSpecialSymbol);
          }
        });
        relativeLayoutColorDotKeyPressedSymbol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 4;
            showPromptColorPicker(fillKeyPressedSymbol);
          }
        });
        relativeLayoutColorDotKeySpecialSymbol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 5;
            showPromptColorPicker(fillKeySpecialSymbol);
          }
        });
        relativeLayoutColorDotKeySymbol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 6;
            showPromptColorPicker(fillKeySymbol);
          }
        });
        relativeLayoutColorDotModifier.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 7;
            showPromptColorPicker(fillModifier);
          }
        });
        relativeLayoutColorDotModifierPressed.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 8;
            showPromptColorPicker(fillModifierPressed);
          }
        });
        relativeLayoutColorDotModifierPressedSymbol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 9;
            showPromptColorPicker(fillModifierPressedSymbol);
          }
        });
        relativeLayoutColorDotModifierSymbol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 10;
            showPromptColorPicker(fillModifierSymbol);
          }
        });
        relativeLayoutColorDotModifierSymbol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 10;
            showPromptColorPicker(fillModifierSymbol);
          }
        });
        relativeLayoutColorDotToggle.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 11;
            showPromptColorPicker(fillToggle);
          }
        });
        relativeLayoutColorDotTogglePressed.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 12;
            showPromptColorPicker(fillTogglePressed);
          }
        });
        relativeLayoutColorDotTogglePressedSymbol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 13;
            showPromptColorPicker(fillTogglePressedSymbol);
          }
        });
        relativeLayoutColorDotToggleSymbol.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            currentColoredItem = 14;
            showPromptColorPicker(fillToggleSymbol);
          }
        });

/**
 editTextHexColourBackground.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourBackground.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillBackground = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillBackground), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_background", fillBackground);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_background", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourKey.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourKey.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillKey = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillKey), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key", fillKey);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourKeyPressed.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourKeyPressed.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillKeyPressed = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillKeyPressed), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_pressed", fillKeyPressed);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_pressed", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourKeyPressedSpecialSymbol.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourKeyPressedSpecialSymbol.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillKeyPressedSpecialSymbol = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillKeyPressedSpecialSymbol), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_pressed_special_symbol", fillKeyPressedSpecialSymbol);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_pressed_special_symbol", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourKeyPressedSymbol.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourKeyPressedSymbol.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillKeyPressedSymbol = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillKeyPressedSymbol), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_pressed_symbol", fillKeyPressedSymbol);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_pressed_symbol", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourKeySpecialSymbol.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourKeySpecialSymbol.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillKeySpecialSymbol = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillKeySpecialSymbol), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_special_symbol", fillKeySpecialSymbol);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_special_symbol", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourKeySymbol.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourKeySymbol.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillKeySymbol = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillKeySymbol), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_symbol", fillKeySymbol);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_key_symbol", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourModifier.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourModifier.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillModifier = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillModifier), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_modifier", fillModifier);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_modifier", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourModifierPressed.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourModifierPressed.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillModifierPressed = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillModifierPressed), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_modifier_pressed", fillModifierPressed);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_modifier_pressed", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourModifierPressedSymbol.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourModifierPressedSymbol.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillModifierPressedSymbol = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillModifierPressedSymbol), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_modifier_pressed_symbol", fillModifierPressedSymbol);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_modifier_pressed_symbol", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourModifierSymbol.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourModifierSymbol.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillModifierSymbol = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillModifierSymbol), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_modifier_symbol", fillModifierSymbol);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_modifier_symbol", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourToggle.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourToggle.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillToggle = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillToggle), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_toggle", fillToggle);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_toggle", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourTogglePressed.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourTogglePressed.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillTogglePressed = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillTogglePressed), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_toggle_pressed", fillTogglePressed);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_toggle_pressed", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourTogglePressedSymbol.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourTogglePressedSymbol.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillTogglePressedSymbol = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillTogglePressedSymbol), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_toggle_pressed_symbol", fillTogglePressedSymbol);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_toggle_pressed_symbol", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 editTextHexColourToggleSymbol.addTextChangedListener(new TextWatcher() {
@Override
public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
try {
if (s.length() == 6) {
final LinearLayout p = (LinearLayout) editTextHexColourToggleSymbol.getParent();
final RelativeLayout pp = (RelativeLayout) p.getParent();
final LinearLayout colourPreview = (LinearLayout) pp.getParent();
if (Pattern.compile("^[a-fA-F0-9]{6}$").matcher(s).matches()) {
fillToggleSymbol = s.toString();
colourPreview.getBackground().setColorFilter(Color.parseColor("#" + fillToggleSymbol), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_toggle_symbol", fillToggleSymbol);
} else {
colourPreview.getBackground().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
editingConfig.put("fill_toggle_symbol", "000000");
}
JSONService.saveJSON(app);
hideSoftKeyboard();
}
} catch (final Exception e) {
DebugService.logStackTrace(e);
}
}
@Override
public void afterTextChanged(Editable s) {}
});
 **/
        relativeLayoutFinishRenamingButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            try {
              hideSoftKeyboard();

              // add textwatcher as well
              String newName;
              if (editTextRename.length() > 255) {
                newName = editTextRename.getText().subSequence(0, 254).toString();
                editTextRename.setText(newName);
              } else {
                newName = editTextRename.getText().toString();
              }

              textViewKeyboardName.setText(newName);
              editTextRename.setVisibility(View.GONE);
              textViewKeyboardName.setVisibility(View.VISIBLE);

              editingConfig.put("name", newName);
              saveConfig(editingConfigID, editingConfig);

              relativeLayoutFinishRenamingButton.setVisibility(View.GONE);
              relativeLayoutRenameButton.setVisibility(View.VISIBLE);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        relativeLayoutDeleteButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            hideSoftKeyboard();
            PromptService.hideAllPrompts(relativeLayoutPromptContainer);
            showPromptConfirmDeletion();
          }
        });
        relativeLayoutOpenKeyboardSettingsButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            final Intent manageKeyboardsIntent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
            manageKeyboardsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(manageKeyboardsIntent);
          }
        });
        relativeLayoutRenameButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            try {
              final String name = editingConfig.getString("name");

              editTextRename.setText(name);
              textViewKeyboardName.setVisibility(View.GONE);
              editTextRename.setVisibility(View.VISIBLE);
              editTextRename.requestFocus();
              editTextRename.selectAll();
              final InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
              if (imm != null) {
                imm.showSoftInput(editTextRename, InputMethodManager.SHOW_IMPLICIT);
              }

              relativeLayoutRenameButton.setVisibility(View.GONE);
              relativeLayoutFinishRenamingButton.setVisibility(View.VISIBLE);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
        });
        relativeLayoutShareThemeButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            showPromptShareTheme();
          }
        });
        relativeLayoutSetInputMethodButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            final InputMethodManager mgr = (InputMethodManager) getSystemService(
              Context.INPUT_METHOD_SERVICE);
            if (mgr != null) {
              mgr.showInputMethodPicker();
            }
          }
        });
        seekBarHoldDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            try {
              final int convertedProgress = (progress * 20) + 100;
              textViewHoldDuration.setText(String.valueOf(convertedProgress));
              editingConfig.put("hold_duration", convertedProgress);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}
          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {
            hideSoftKeyboard();
            saveConfig(editingConfigID, editingConfig);
          }
        });
        seekBarRepeatRate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            try {
              textViewRepeatRate.setText(String.valueOf(progress));
              editingConfig.put("repeat_rate", progress);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}
          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {
            hideSoftKeyboard();
            saveConfig(editingConfigID, editingConfig);
          }
        });
        seekBarSensitivityHorizontal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            try {
              final int convertedProgress = (int) (264 - (2.4 * (progress + 1)));
              textViewSensitivityHorizontal.setText(String.valueOf(progress + 1));
              editingConfig.put("sensitivity_horizontal", convertedProgress);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}
          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {
            hideSoftKeyboard();
            saveConfig(editingConfigID, editingConfig);
          }
        });
        seekBarSensitivityVertical.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
          @Override
          public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            try {
              final int convertedProgress = (int) (264 - (2.4 * (progress + 1)));
              textViewSensitivityVertical.setText(String.valueOf(progress + 1));
              editingConfig.put("sensitivity_vertical", convertedProgress);
            } catch (final Exception e) {
              DebugService.logStackTrace(e);
            }
          }
          @Override
          public void onStartTrackingTouch(SeekBar seekBar) {}
          @Override
          public void onStopTrackingTouch(SeekBar seekBar) {
            hideSoftKeyboard();
            saveConfig(editingConfigID, editingConfig);
          }
        });

        relativeLayoutColorDotBackground.getBackground().setColorFilter(Color.parseColor(
          "#" + fillBackground), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotKey.getBackground().setColorFilter(Color.parseColor(
          "#" + fillKey), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotKeyPressed.getBackground().setColorFilter(Color.parseColor(
          "#" + fillKeyPressed), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotKeyPressedSpecialSymbol.getBackground().setColorFilter(Color.parseColor(
          "#" + fillKeyPressedSpecialSymbol), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotKeyPressedSymbol.getBackground().setColorFilter(Color.parseColor(
          "#" + fillKeyPressedSymbol), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotKeySpecialSymbol.getBackground().setColorFilter(Color.parseColor(
          "#" + fillKeySpecialSymbol), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotKeySymbol.getBackground().setColorFilter(Color.parseColor(
          "#" + fillKeySymbol), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotModifier.getBackground().setColorFilter(Color.parseColor(
          "#" + fillModifier), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotModifierPressed.getBackground().setColorFilter(Color.parseColor(
          "#" + fillModifierPressed), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotModifierPressedSymbol.getBackground().setColorFilter(Color.parseColor(
          "#" + fillModifierPressedSymbol), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotModifierSymbol.getBackground().setColorFilter(Color.parseColor(
          "#" + fillModifierSymbol), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotToggle.getBackground().setColorFilter(Color.parseColor(
          "#" + fillToggle), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotTogglePressed.getBackground().setColorFilter(Color.parseColor(
          "#" + fillTogglePressed), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotTogglePressedSymbol.getBackground().setColorFilter(Color.parseColor(
          "#" + fillTogglePressedSymbol), PorterDuff.Mode.SRC_ATOP);
        relativeLayoutColorDotToggleSymbol.getBackground().setColorFilter(Color.parseColor(
          "#" + fillToggleSymbol), PorterDuff.Mode.SRC_ATOP);
        editTextRename.setVisibility(View.GONE);
        seekBarHoldDuration.setProgress(holdDurationConverted);
        seekBarRepeatRate.setProgress(repeatRate);
        seekBarSensitivityHorizontal.setProgress(sensitivityHorizontalConverted);
        seekBarSensitivityVertical.setProgress(sensitivityVerticalConverted);
        textViewHoldDuration.setText(String.valueOf(holdDuration));
        textViewKeyboardName.setText(name);
        textViewKeyboardName.setVisibility(View.VISIBLE);
        textViewRepeatRate.setText(String.valueOf(repeatRate));
        textViewSensitivityHorizontal.setText(String.valueOf(sensitivityHorizontalConverted + 1));
        textViewSensitivityVertical.setText(String.valueOf(sensitivityVerticalConverted + 1));
        if (repeatBackspace == 1) {
          checkBoxRepeatBackspace.setChecked(true);
        } else {
          checkBoxRepeatBackspace.setChecked(false);
        }
        if (repeatSpaceBar == 1) {
          checkBoxRepeatSpacebar.setChecked(true);
        } else {
          checkBoxRepeatSpacebar.setChecked(false);
        }
        if (editingPackageName.equals("*")) {
          imageViewSelectAppIcon.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher_round));
          textViewSelectedAppName.setText("All apps");
          final JSONArray editingConfigNames = editingConfig.names();
          if (JSONService.containsString(editingConfigNames, "fallback")) {
            linearLayoutDeleteContainer.setVisibility(View.GONE);
          } else {
            linearLayoutDeleteContainer.setVisibility(View.VISIBLE);
          }
        } else {
          linearLayoutDeleteContainer.setVisibility(View.VISIBLE);
          imageViewSelectAppIcon.setImageDrawable(getPackageManager().getApplicationIcon(editingPackageName));
          textViewSelectedAppName.setText(editingPackageName);
        }
        if (swipeHorizontal == 1) {
          checkBoxSwipeHorizontally.setChecked(true);
          linearLayoutSwipeHorizontalOptionsContainer.setVisibility(View.VISIBLE);
        } else {
          checkBoxSwipeHorizontally.setChecked(false);
          linearLayoutSwipeHorizontalOptionsContainer.setVisibility(View.GONE);
        }
        if (swipeVertical == 1) {
          checkBoxSwipeVertical.setChecked(true);
          linearLayoutSwipeVerticalOptionsContainer.setVisibility(View.VISIBLE);
        } else {
          checkBoxSwipeVertical.setChecked(false);
          linearLayoutSwipeVerticalOptionsContainer.setVisibility(View.GONE);
        }
        if (selectHorizontal == 1) {
          checkBoxSelectHorizontal.setChecked(true);
        } else {
          checkBoxSelectHorizontal.setChecked(false);
        }
        if (selectVertical == 1) {
          checkBoxSelectVertical.setChecked(true);
        } else {
          checkBoxSelectVertical.setChecked(false);
        }
        if (swipeHorizontalOnlyText == 1) {
          checkBoxSwipeHorizontalOnlyText.setChecked(true);
        } else {
          checkBoxSwipeHorizontalOnlyText.setChecked(false);
        }
        if (swipeVerticalOnlyText == 1) {
          checkBoxSwipeVerticalOnlyText.setChecked(true);
        } else {
          checkBoxSwipeVerticalOnlyText.setChecked(false);
        }

        saveConfig(editingConfigID, editingConfig);
      }
    } catch (final Exception e) {
      DebugService.logStackTrace(e);
    }
  }

  private void initUI() {
    findViewById(R.id.relativeLayoutBackButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startKeyboardListActivity();
      }
    });

    relativeLayoutPromptContainer = findViewById(R.id.relativeLayoutPromptContainer);
  }

  @Override
  public void onBackPressed() {
    if (PromptService.waitGroup != 0) {
      PromptService.hidePrompt(relativeLayoutPromptContainer);
    } else {
      startKeyboardListActivity();
    }
  }

  @Override
  protected void onNewIntent(final Intent intent) {
    super.onNewIntent(intent);
    loadValues();
  }

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    pixelDensity = getResources().getDisplayMetrics().density;

    initUI();
    loadValues();
  }
}
