package buffermet.apk.magickeyboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@SuppressLint("ClickableViewAccessibility")
public class MagicKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
  private int[][] emojisRecent = new int[64][0];

  final private int[][] emojisAnimals = new int[][]{};
  final private int[][] emojisFlags = new int[][]{};
  final private int[][] emojisFood = new int[][]{};
  final private int[][] emojisObjects = new int[][]{};
  final private int[][] emojisPeople = new int[][]{};
  final private int[][] emojisSports = new int[][]{};
  final private int[][] emojisSymbols = new int[][]{};
  final private int[][] emojisTravel = new int[][]{};

  private JSONObject app = null;

  private int deviceWidth;
  private int cellDimension;

  private ConstraintLayout rootView = null;
  private ImageView imageViewEmojiRecentButton = null;
  private ImageView imageViewEmojiPeopleButton = null;
  private ImageView imageViewEmojiAnimalsButton = null;
  private ImageView imageViewEmojiFoodButton = null;
  private ImageView imageViewEmojiSportButton = null;
  private ImageView imageViewEmojiTravelButton = null;
  private ImageView imageViewEmojiObjectsButton = null;
  private ImageView imageViewEmojiSymbolsButton = null;
  private ImageView imageViewEmojiFlagsButton = null;
  private Keyboard keyboard = null;
  private LinearLayout linearLayoutRecentEmojis = null;
  private LinearLayout linearLayoutPeopleEmojis = null;
  private LinearLayout linearLayoutAnimalEmojis = null;
  private LinearLayout linearLayoutFoodEmojis = null;
  private LinearLayout linearLayoutSportEmojis = null;
  private LinearLayout linearLayoutTravelEmojis = null;
  private LinearLayout linearLayoutObjectEmojis = null;
  private LinearLayout linearLayoutSymbolEmojis = null;
  private LinearLayout linearLayoutFlagEmojis = null;
  private MagicKeyboardView keyboardView = null;
  private RelativeLayout relativeLayoutEmojisContainer = null;
  private RelativeLayout relativeLayoutNoRecentEmojisContainer = null;
  private RelativeLayout relativeLayoutEmojiKeyboardView = null;
  private ScrollView scrollViewEmojisContainer = null;

  private boolean caps = false;
  private boolean shift = false;
  private boolean ctrl = false;
  private boolean alt = false;

  private int holdDuration = 300; // configurable
  private int repeatBackspace = 1; // configurable
  private int repeatSpacebar = 1; // configurable
  private int repeatRate = 30; // configurable
  private int scrollDistanceX = 40; // configurable
  private int scrollDistanceY = 196; // configurable
  private int selectHorizontal = 1; // configurable
  private int selectVertical = 1; // configurable
  private int swipeHorizontal = 1; // configurable
  private int swipeVertical = 1; // configurable
  private int swipeHorizontalOnlyText = 0; // configurable
  private int swipeVerticalOnlyText = 0; // configurable

  private long touchDownTime = 0;
  private boolean hasTouchedFromModifier = false;
  private float lastScrollCoordX;
  private float lastScrollCoordY;
  private boolean hasSwiped = false;
  private int swipeStartDelay = 150;
  private boolean vibrateEnabled = true; // configurable
  private boolean soundsEnabled = true; // configurable
  private int vibrateDuration = 10; // configurable
  private Vibrator vibrator;
  private AudioManager audioManager;

  private boolean keyPressed = false;
  private int lastPressedKeyCode;
  private boolean longPressed = false;
  private int currentRepeatRate;
  private Handler handlerLongPressWatcher = new Handler();
  final private Runnable runnableLongPressWatcher = new Runnable() {
    @Override
    public void run() {
      if (keyPressed) MagicKeyboardService.this.onKeyLongPress(lastPressedKeyCode);
    }
  };

  private String foregroundPackageName;

  private int currentEmojiCategory; // 0=recent 1=people 2=animals 3=food 4=sport 5=travel 6=objects 7=symbols 8=flags

  protected static String fillBackground = "000000";
  protected static String fillKey = "000000";
  protected static String fillKeyPressed = "000000";
  protected static String fillKeyPressedSpecialSymbol = "000000";
  protected static String fillKeyPressedSymbol = "000000";
  protected static String fillKeySpecialSymbol = "000000";
  protected static String fillKeySymbol = "000000";
  protected static String fillModifier = "000000";
  protected static String fillModifierPressed = "000000";
  protected static String fillModifierPressedSymbol = "000000";
  protected static String fillModifierSymbol = "000000";
  protected static String fillToggle = "000000";
  protected static String fillTogglePressed = "000000";
  protected static String fillTogglePressedSymbol = "000000";
  protected static String fillToggleSymbol = "000000";

  private void toggleKeyboardConfig() {
    if (!foregroundPackageName.equals(getPackageName())) {
      try {
        final JSONObject rules = app.getJSONObject("rules");
        final JSONArray rulesPackageNames = rules.names();

        String packageName;
        if (JSONService.containsString(rulesPackageNames, foregroundPackageName)) {
          packageName = foregroundPackageName;
        } else {
          packageName = "*";
        }

        final JSONObject thisPackageRules = rules.getJSONObject(packageName);
        final JSONArray thisPackageConfigs = thisPackageRules.getJSONArray("configs");
        final String oldCurrentConfigID = thisPackageRules.getString("current");

        String newCurrentConfigID = oldCurrentConfigID;
        for (int i = 0; i < thisPackageConfigs.length(); i++) {
          final String thisConfigID = thisPackageConfigs.getString(i);

          if (thisConfigID.equals(oldCurrentConfigID)) {
            if (i != (thisPackageConfigs.length() - 1)) {
              newCurrentConfigID = thisPackageConfigs.getString(i + 1);
            } else {
              newCurrentConfigID = thisPackageConfigs.getString(0);
            }
          }
        }

        thisPackageRules.put("current", newCurrentConfigID);

        if (newCurrentConfigID.equals("-1")) {
          if (emojisRecent[0].length != 0) {
            loadEmojiKeyboardCategory(0);
          } else {
            loadEmojiKeyboardCategory(1);
          }
        } else {
          loadKeyboardConfig(newCurrentConfigID);

          final boolean showToast = true; // debug
          if (showToast) {
            Toast.makeText(
              this,
              app.getJSONObject("keyboards")
                .getJSONObject(newCurrentConfigID)
                .getString("name"),
              Toast.LENGTH_SHORT).show();
          }
        }

        resetAllModifierKeys();
      } catch (Exception e) {
        DebugService.logStackTrace(e);
      }
    } else {
      Toast.makeText(
        this,
        "You can't toggle keyboards while you're editing one.",
        Toast.LENGTH_SHORT).show();
    }
  }

  private void sendKeyDown(final InputConnection ic, final int keyCode) {
    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode));
  }

  private void sendKeyUp(final InputConnection ic, final int keyCode) {
    ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyCode));
  }

  private void pressKey(final List<Keyboard.Key> keys, final int keyCode) {
    for (Keyboard.Key key : keys) {
      if (key.codes[0] == keyCode) {
        key.pressed = true;
      }
    }
  }

  private void resetAltKey() {
    alt = false;
    final List<Keyboard.Key> keys = keyboard.getKeys();
    for (Keyboard.Key key : keys) {
      if (key.codes[0] == -9996) { // alt
        key.on = false;
        break;
      }
    }
    keyboardView.invalidateAllKeys();
  }

  private void resetCtrlKey() {
    ctrl = false;
    final List<Keyboard.Key> keys = keyboard.getKeys();
    for (Keyboard.Key key : keys) {
      if (key.codes[0] == -9997) { // ctrl
        key.on = false;
        break;
      }
    }
    keyboardView.invalidateAllKeys();
  }

  private void resetShiftKey() {
    shift = false;
    if (!caps) keyboard.setShifted(false);
    final List<Keyboard.Key> keys = keyboard.getKeys();
    for (Keyboard.Key key : keys) {
      if (key.codes[0] == -1) { // shift
        key.on = false;
        break;
      }
    }
    keyboardView.invalidateAllKeys();
  }

  private void resetAllModifierKeys() {
    alt = false;
    caps = false;
    ctrl = false;
    shift = false;
    final List<Keyboard.Key> keys = keyboard.getKeys();
    for (Keyboard.Key key : keys) {
      switch (key.codes[0]) {
        case -9996: // alt
          key.on = false;
          break;
        case -9995: // caps
          key.codes[0] = -1; // shift
          key.on = false;
          break;
        case -9997: // ctrl
          key.on = false;
          break;
        case -1: // shift
          key.on = false;
          break;
      }
    }
    keyboard.setShifted(false);
    keyboardView.invalidateAllKeys();
  }

  @Override
  public void onPress(final int keyCode) {
    keyPressed = true;
    lastPressedKeyCode = keyCode;

    handlerLongPressWatcher = new Handler();
    handlerLongPressWatcher.postDelayed(runnableLongPressWatcher, holdDuration);

    if (vibrateEnabled) {
      if (vibrator != null) {
        vibrator.vibrate(vibrateDuration);
      } else {
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
      }
    }
    if (soundsEnabled) {
      if (audioManager != null) {
        switch (keyCode) {
          case 32: // spacebar
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
            break;
          case 10:
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
            break;
          case Keyboard.KEYCODE_DELETE:
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
            break;
          default:
            audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
      } else {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
      }
    }
  }

  private void onKeyLongPress(final int keyCode) {
//        Toast.makeText(this, "Service:onKeyLongPress(keyCode:"+keyCode+")", Toast.LENGTH_SHORT).show();
    if (!hasSwiped) {
      longPressed = true;

      final InputConnection ic = getCurrentInputConnection();
      boolean hasSubLabel = false;
      switch (keyCode) {
        case -9998: // toggle
          final InputMethodManager imm = (InputMethodManager) getSystemService(
            Context.INPUT_METHOD_SERVICE);
          if (imm != null) {
            imm.showInputMethodPicker();
          }
          hasSubLabel = true;
          break;
        case -9997: // ctrl
          break;
        case -9996: // alt
          break;
        case -9995: // caps
          break;
        case -5: // backspace
          if (repeatBackspace == 1) {
            currentRepeatRate = repeatRate;
            final Handler handler = new Handler(Looper.getMainLooper());
            final Runnable runnable = new Runnable() {
              @Override
              public void run() {
                if (longPressed) {
                  if (!hasSwiped) {
                    final CharSequence selectedText = ic.getSelectedText(0);
                    if (TextUtils.isEmpty(selectedText)) {
                      ic.deleteSurroundingText(1, 0);
                    } else {
                      ic.commitText("", 1);
                    }
                    handler.postDelayed(this, currentRepeatRate);
                  } else {
                    handler.removeCallbacks(this);
                  }
                }
              }
            };
            handler.post(runnable);
          }
          break;
        case -1: // shift
          break;
        case 32: // spacebar
          if (repeatSpacebar == 1) {
            currentRepeatRate = repeatRate;
            final Handler handler2 = new Handler(Looper.getMainLooper());
            final Runnable runnable2 = new Runnable() {
              @Override
              public void run() {
                if (longPressed) {
                  if (!hasSwiped) {
                    ic.commitText(String.valueOf((char) 32), 1);
                    handler2.postDelayed(this, currentRepeatRate);
                  } else {
                    handler2.removeCallbacks(this);
                  }
                }
              }
            };
            handler2.post(runnable2);
          }
          break;
        case 39: // '
          ic.commitText(String.valueOf((char) 34), 1);
          hasSubLabel = true;
          break;
        case 47: // /
          ic.commitText(String.valueOf((char) 63), 1);
          hasSubLabel = true;
          break;
        case 48: // 0
          ic.commitText(String.valueOf((char) 41), 1);
          hasSubLabel = true;
          break;
        case 49: // 1
          ic.commitText(String.valueOf((char) 33), 1);
          hasSubLabel = true;
          break;
        case 50: // 2
          ic.commitText(String.valueOf((char) 64), 1);
          hasSubLabel = true;
          break;
        case 51: // 3
          ic.commitText(String.valueOf((char) 35), 1);
          hasSubLabel = true;
          break;
        case 52: // 4
          ic.commitText(String.valueOf((char) 36), 1);
          hasSubLabel = true;
          break;
        case 53: // 5
          ic.commitText(String.valueOf((char) 37), 1);
          hasSubLabel = true;
          break;
        case 54: // 6
          ic.commitText(String.valueOf((char) 94), 1);
          hasSubLabel = true;
          break;
        case 55: // 7
          ic.commitText(String.valueOf((char) 38), 1);
          hasSubLabel = true;
          break;
        case 56: // 8
          ic.commitText(String.valueOf((char) 42), 1);
          hasSubLabel = true;
          break;
        case 57: // 9
          ic.commitText(String.valueOf((char) 40), 1);
          hasSubLabel = true;
          break;
        case 59: // ;
          ic.commitText(String.valueOf((char) 58), 1);
          hasSubLabel = true;
          break;
        case 96: // `
          ic.commitText(String.valueOf((char) 126), 1);
          hasSubLabel = true;
          break;
        case 98: // b
          ic.commitText(String.valueOf((char) 60), 1);
          hasSubLabel = true;
          break;
//                case 99: // c
//                    ic.commitText(String.valueOf((char) 60), 1);
//                    hasSubLabel = true;
//                    break;
//                case 101: // e
//                    ic.commitText(String.valueOf((char) 45), 1);
//                    hasSubLabel = true;
//                    break;
        case 102: // f
          ic.commitText(String.valueOf((char) 124), 1);
          hasSubLabel = true;
          break;
        case 103: // g
          ic.commitText(String.valueOf((char) 92), 1);
          hasSubLabel = true;
          break;
        case 104: // h
          ic.commitText(String.valueOf((char) 91), 1);
          hasSubLabel = true;
          break;
        case 105: // i
          ic.commitText(String.valueOf((char) 45), 1);
          hasSubLabel = true;
          break;
        case 106: // j
          ic.commitText(String.valueOf((char) 93), 1);
          hasSubLabel = true;
          break;
        case 107: // k
          ic.commitText(String.valueOf((char) 123), 1);
          hasSubLabel = true;
          break;
        case 108: // l
          ic.commitText(String.valueOf((char) 125), 1);
          hasSubLabel = true;
          break;
        case 109: // m
          ic.commitText(String.valueOf((char) 44), 1);
          hasSubLabel = true;
          break;
        case 110: // n
          ic.commitText(String.valueOf((char) 62), 1);
          hasSubLabel = true;
          break;
        case 111: // o
          ic.commitText(String.valueOf((char) 43), 1);
          hasSubLabel = true;
          break;
        case 112: // p
          ic.commitText(String.valueOf((char) 61), 1);
          hasSubLabel = true;
          break;
//                case 113: // q
//                    ic.commitText(String.valueOf((char) 61), 1);
//                    hasSubLabel = true;
//                    break;
//                case 114: // r
//                    ic.commitText(String.valueOf((char) 43), 1);
//                    hasSubLabel = true;
//                    break;
//                case 116: // t
//                    ic.commitText(String.valueOf((char) 124), 1);
//                    hasSubLabel = true;
//                    break;
        case 117: // u
          ic.commitText(String.valueOf((char) 95), 1);
          hasSubLabel = true;
          break;
//                case 118: // v
//                    ic.commitText(String.valueOf((char) 62), 1);
//                    hasSubLabel = true;
//                    break;
//                case 119: // w
//                    ic.commitText(String.valueOf((char) 95), 1);
//                    hasSubLabel = true;
//                    break;
//                case 121: // y
//                    ic.commitText(String.valueOf((char) 92), 1);
//                    hasSubLabel = true;
//                    break;
      }

      if (hasSubLabel) {
        if (vibrateEnabled) {
          if (vibrator != null) {
            vibrator.vibrate(5);
          } else {
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
          }
        }

        if (soundsEnabled) {
          if (audioManager != null) {
            switch (keyCode) {
              case 32: // spacebar
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
              case 10:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
              case Keyboard.KEYCODE_DELETE:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
              default:
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
            }
          } else {
            audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
          }
        }
      }
    }
  }

// this enables popup
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        Toast.makeText(this, "Service:onKeyDown(keyCode:"+keyCode+", event:"+event.getAction()+")", Toast.LENGTH_SHORT).show();
//        final int primaryCode = event.getKeyCode();
//
////        keyboardView.setPreviewEnabled(true);
//
//        return super.onKeyDown(primaryCode, event);
//    }

  @Override
  public void onKey(final int primaryCode, final int[] keyCodes) {
    if (!longPressed && !hasSwiped) {
      final InputConnection ic = getCurrentInputConnection();
      List<Keyboard.Key> keys;
      if (ic != null) {
        keys = keyboard.getKeys();

        keyboardView.invalidateAllKeys();
        switch (primaryCode) {
          case -9998: // toggle
            toggleKeyboardConfig();
            break;
          case -9997: // ctrl
            if (ctrl) {
              ctrl = false;
              for (Keyboard.Key key : keys) {
                if (key.codes[0] == primaryCode) {
                  key.on = false;
                  break;
                }
              }
            } else {
              ctrl = true;
              for (Keyboard.Key key : keys) {
                if (key.codes[0] == primaryCode) {
                  key.on = true;
                  break;
                }
              }
            }
            keyboardView.invalidateAllKeys();
            break;
          case -9996: // alt
            if (alt) {
              alt = false;
              for (Keyboard.Key key : keys) {
                if (key.codes[0] == primaryCode) {
                  key.on = false;
                  break;
                }
              }
            } else {
              alt = true;
              for (Keyboard.Key key : keys) {
                if (key.codes[0] == primaryCode) {
                  key.on = true;
                  break;
                }
              }
            }
            keyboardView.invalidateAllKeys();
            break;
          case -9995: // caps
            caps = false;
            keyboard.setShifted(false);
            for (Keyboard.Key key : keys) {
              if (key.codes[0] == primaryCode) {
                key.codes[0] = -1;
                break;
              }
            }
            keyboardView.invalidateAllKeys();
            break;
//          case -9994: // search
//            if (shift) {
//              shift = false;
//              ic.sendKeyEvent(new KeyEvent(
//                  0,
//                  0,
//                  KeyEvent.ACTION_DOWN,
//                  KeyEvent.KEYCODE_SEARCH,
//                  0,
//                  KeyEvent.META_SHIFT_ON));
//              sendKeyDown(ic, KeyEvent.KEYCODE_SEARCH);
//            } else {
//            }
//            keyboardView.invalidateAllKeys();
//            break;
          case -5: // delete
            final CharSequence selectedText = ic.getSelectedText(0);
            if (TextUtils.isEmpty(selectedText)) {
              ic.deleteSurroundingText(1, 0);
            } else {
              ic.commitText("", 1);
            }
            resetShiftKey();
            break;
          case -4: // return
            final int actionId = getCurrentInputEditorInfo().imeOptions
              & 0x000000ff; // EditorInfo.IME_MASK_ACTION
            switch (actionId) {
              case 0x00000006: // EditorInfo.IME_ACTION_DONE
              case 0x00000002: // EditorInfo.IME_ACTION_GO
              case 0x00000003: // EditorInfo.IME_ACTION_SEARCH
                sendDefaultEditorAction(true);
                break;
            }
            if (shift) {
              shift = false;
              ic.sendKeyEvent(new KeyEvent(
                0,
                0,
                KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_ENTER,
                0,
                KeyEvent.META_SHIFT_ON));
            } else {
              sendKeyDown(ic, KeyEvent.KEYCODE_ENTER);
            }
            keyboardView.invalidateAllKeys();
            break;
          case -1: // shift
            if (shift) {
              shift = false;
              caps = true;
              keyboard.setShifted(true);
              for (Keyboard.Key key : keys) {
                if (key.codes[0] == primaryCode) {
                  key.on = false;
                  key.codes[0] = -9995; // caps
                  break;
                }
              }
            } else {
              shift = true;
              caps = false;
              keyboard.setShifted(true);
              for (Keyboard.Key key : keys) {
                if (key.codes[0] == primaryCode) {
                  key.on = true;
                  break;
                }
              }
            }
            keyboardView.invalidateAllKeys();
            break;
          case 9: // tab
            if (shift) {
              ic.sendKeyEvent(new KeyEvent(
                0,
                0,
                KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_TAB,
                0,
                KeyEvent.META_SHIFT_ON));
              resetShiftKey();
            } else {
              sendKeyDown(ic, KeyEvent.KEYCODE_TAB);
            }
            break;
          case 9999: // escape
            sendKeyDown(ic, KeyEvent.KEYCODE_ESCAPE);
            break;
          default:
            char code = (char) primaryCode;
            if (Character.isLetter(code) && (shift|caps)) {
              code = Character.toUpperCase(code);
            }

            if (ctrl) {
              if (alt) {
                onKeyCtrlAlt(primaryCode, ic);
              } else {
                onKeyCtrl(primaryCode, ic);
              }
            } else if (alt) {
              onKeyAlt(primaryCode, ic);
            } else {
              ic.commitText(String.valueOf(code), 1);
            }

            resetAltKey();
            resetCtrlKey();
            resetShiftKey();
            break;
        }

        pressKey(keys, primaryCode);
      }
    }
  }

  private void onKeyAlt(final int code, final InputConnection ic) {
    switch (code) {
      case 'a':
      case 'A':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_A,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_A,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'b':
      case 'B':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_B,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_B,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'c':
      case 'C':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_C,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_C,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'd':
      case 'D':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_D,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_D,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'e':
      case 'E':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_E,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_E,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'f':
      case 'F':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_F,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_F,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'g':
      case 'G':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_G,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_G,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'h':
      case 'H':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_H,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_H,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'i':
      case 'I':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_I,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_I,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'j':
      case 'J':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_J,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_J,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'k':
      case 'K':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_K,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_K,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'l':
      case 'L':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_L,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_L,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'm':
      case 'M':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_M,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_M,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'n':
      case 'N':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_N,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_N,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'o':
      case 'O':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_O,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_O,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'p':
      case 'P':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_P,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_P,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'q':
      case 'Q':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Q,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Q,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'r':
      case 'R':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_R,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_R,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 's':
      case 'S':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_S,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_S,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 't':
      case 'T':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_T,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_T,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'u':
      case 'U':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_U,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_U,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'v':
      case 'V':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_V,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_V,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'w':
      case 'W':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_W,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_W,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'x':
      case 'X':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_X,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_X,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'y':
      case 'Y':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Y,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Y,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      case 'z':
      case 'Z':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Z,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Z,
              0,
              KeyEvent.META_ALT_ON));
        }
        shift = false;
        break;
      default:
        if (Character.isLetter(code) && shift) {
          final int codeUppercase = Character.toUpperCase(code);
          ic.commitText(String.valueOf(codeUppercase), 1);
          if (!caps) {
            shift = false;
            ic.sendKeyEvent(
              new KeyEvent(
                0,
                0,
                KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_SHIFT_LEFT,
                0,
                KeyEvent.META_SHIFT_ON));
          }
        }
        break;
    }
  }

  private void onKeyCtrl(final int code, final InputConnection ic) {
    switch (code) {
      case 'a':
      case 'A':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_A,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_A,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'b':
      case 'B':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_B,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_B,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'c':
      case 'C':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_C,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_C,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'd':
      case 'D':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_D,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_D,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'e':
      case 'E':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_E,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_E,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'f':
      case 'F':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_F,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_F,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'g':
      case 'G':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_G,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_G,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'h':
      case 'H':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_H,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_H,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'i':
      case 'I':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_I,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_I,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'j':
      case 'J':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_J,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_J,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'k':
      case 'K':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_K,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_K,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'l':
      case 'L':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_L,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_L,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'm':
      case 'M':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_M,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_M,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'n':
      case 'N':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_N,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_N,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'o':
      case 'O':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_O,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_O,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'p':
      case 'P':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_P,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_P,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'q':
      case 'Q':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Q,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Q,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'r':
      case 'R':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_R,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_R,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 's':
      case 'S':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_S,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_S,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 't':
      case 'T':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_T,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_T,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'u':
      case 'U':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_U,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_U,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'v':
      case 'V':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_V,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_V,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'w':
      case 'W':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_W,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_W,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'x':
      case 'X':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_X,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_X,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'y':
      case 'Y':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Y,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Y,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'z':
      case 'Z':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Z,
              0,
              KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Z,
              0,
              KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      default:
        if (Character.isLetter(code) && shift) {
          final int codeUppercase = Character.toUpperCase(code);
          ic.commitText(String.valueOf(codeUppercase), 1);
          if (!caps) {
            shift = false;
            ic.sendKeyEvent(
              new KeyEvent(
                0,
                0,
                KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_SHIFT_LEFT,
                0,
                KeyEvent.META_SHIFT_ON));
          }
        }
        break;
    }
  }

  private void onKeyCtrlAlt(final int code, final InputConnection ic) {
    switch (code) {
      case 'a':
      case 'A':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_A,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_A,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'b':
      case 'B':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_B,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_B,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'c':
      case 'C':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_C,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_C,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'd':
      case 'D':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_D,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_D,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'e':
      case 'E':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_E,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_E,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'f':
      case 'F':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_F,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_F,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'g':
      case 'G':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_G,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_G,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'h':
      case 'H':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_H,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_H,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'i':
      case 'I':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_I,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_I,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'j':
      case 'J':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_J,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_J,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'k':
      case 'K':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_K,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_K,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'l':
      case 'L':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_L,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_L,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'm':
      case 'M':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_M,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_M,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'n':
      case 'N':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_N,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_N,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'o':
      case 'O':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_O,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_O,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'p':
      case 'P':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_P,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_P,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'q':
      case 'Q':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Q,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Q,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'r':
      case 'R':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_R,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_R,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 's':
      case 'S':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_S,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_S,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 't':
      case 'T':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_T,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_T,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'u':
      case 'U':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_U,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_U,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'v':
      case 'V':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_V,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_V,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'w':
      case 'W':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_W,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_W,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'x':
      case 'X':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_X,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_X,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'y':
      case 'Y':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Y,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Y,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      case 'z':
      case 'Z':
        if (shift) {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Z,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON
                | KeyEvent.META_SHIFT_ON));
        } else {
          ic.sendKeyEvent(
            new KeyEvent(
              0,
              0,
              KeyEvent.ACTION_DOWN,
              KeyEvent.KEYCODE_Z,
              0,
              KeyEvent.META_ALT_ON
                | KeyEvent.META_CTRL_ON));
        }
        shift = false;
        break;
      default:
        if (Character.isLetter(code) && shift) {
          final int codeUppercase = Character.toUpperCase(code);
          ic.commitText(String.valueOf(codeUppercase), 1);
          if (!caps) {
//                        Toast.makeText(this, "SHIFT OFF", Toast.LENGTH_SHORT).show();
            shift = false;
            ic.sendKeyEvent(
              new KeyEvent(
                0,
                0,
                KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_SHIFT_LEFT,
                0,
                KeyEvent.META_SHIFT_ON));

            //Log.e("CodeboardIME", "Unshifted b/c no lock");
          }
//                    shiftKeyUpdateView();
        }
        break;
    }
  }

  @Override
  public void onRelease(final int keyCode) {
//        Toast.makeText(this, "Service:onRelease(keyCode:"+keyCode+")", Toast.LENGTH_SHORT).show();
    keyPressed = false;
    longPressed = false;
    handlerLongPressWatcher.removeCallbacks(runnableLongPressWatcher);

    final List<Keyboard.Key> keys = keyboard.getKeys();
    for (Keyboard.Key key : keys) {
      if (key.codes[0] == keyCode) {
        key.pressed = false;
      }
    }

    switch (keyCode) {
//      case -9994: // search
//        break;
      case -4:    // return
        sendKeyUp(getCurrentInputConnection(), KeyEvent.KEYCODE_ENTER);
        break;
    }

//        keyboardView.setPreviewEnabled(false);

    keyboardView.invalidateAllKeys();
  }

  @Override
  public void onText(final CharSequence charSequence) {}

  @Override
  public void swipeLeft() {}

  @Override
  public void swipeRight() {}

  @Override
  public void swipeDown() {}

  @Override
  public void swipeUp() {}

  private void saveRecentEmoji(final int[] emojiBytes) {
    int existingRecentEmojiIndex = 64;
    for (int i = 0; i < emojisRecent.length; i++) {
      if (emojisRecent[i] == emojiBytes) {
        existingRecentEmojiIndex = i;
      }
    }
    int[][] newEmojisRecent = new int[64][0];
    newEmojisRecent[0] = emojiBytes;
    for (int i = 0; i < 63; i++) {
      if (i != existingRecentEmojiIndex) {
        if (i > existingRecentEmojiIndex) {
          newEmojisRecent[i] = emojisRecent[i];
        } else {
          newEmojisRecent[i + 1] = emojisRecent[i];
        }
      }
    }
    newEmojisRecent[0] = emojiBytes;
    emojisRecent = newEmojisRecent;

    try {
      JSONObject newRecentEmojis = new JSONObject();
      for (int i = 0; i < emojisRecent.length; i++) {
        JSONArray newByteCodes = new JSONArray();
        for (int ii = 0; ii < emojisRecent[i].length; ii++) {
          newByteCodes.put(emojisRecent[i][ii]);
        }
        newRecentEmojis.put(String.valueOf(i), newByteCodes);
      }
      app.put("recent_emojis", newRecentEmojis);
    } catch (Exception e) {
      DebugService.logStackTrace(e);
    }
  }

  private void loadEmojisRecent() {
    linearLayoutRecentEmojis.removeAllViews();

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };

    for (int i = 0; i < (Math.floor(emojisRecent.length / 8) + 1); i++) {
      final LinearLayout row = new LinearLayout(this);
      final LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(rowParams);
      row.setOrientation(LinearLayout.HORIZONTAL);

      for (int ii = (i * 8); ii < emojisRecent.length && ii < ((i * 8) + 8); ii++) {
        final StringBuilder emoji = new StringBuilder();
        if (emojisRecent[ii].length > 0) {
          for (int iii = 0; iii < emojisRecent[ii].length; iii++) {
            emoji.append(new String(Character.toChars(emojisRecent[ii][iii])));
          }

          final int finalii = ii;
          final TextView textView = new TextView(MagicKeyboardService.this);
          final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
            cellDimension,
            cellDimension);
          textView.setLayoutParams(textViewParams);
          textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
          textView.setTextColor(Color.BLACK);
          textView.setGravity(Gravity.CENTER);
          textView.setText(emoji);
          textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getCurrentInputConnection().commitText(emoji, 1);
              saveRecentEmoji(emojisRecent[finalii]);
              onPress(-999999999);
            }
          });
          textView.setOnTouchListener(onTouchListenerKey);

          row.addView(textView);
        }
      }

      linearLayoutRecentEmojis.addView(row);
    }
  }

  private void loadEmojisPeople() {
    linearLayoutPeopleEmojis = new LinearLayout(this);
    final RelativeLayout.LayoutParams linearLayoutPeopleEmojisParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutPeopleEmojis.setLayoutParams(linearLayoutPeopleEmojisParams);
    linearLayoutPeopleEmojis.setOrientation(LinearLayout.VERTICAL);
    linearLayoutPeopleEmojis.setVisibility(View.INVISIBLE);
    linearLayoutPeopleEmojis.setEnabled(false);

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };

    for (int i = 0; i < (Math.floor(emojisPeople.length / 8) + 1); i++) {
      final LinearLayout row = new LinearLayout(this);
      final LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(rowParams);
      row.setOrientation(LinearLayout.HORIZONTAL);

      for (int ii = (i * 8); ii < emojisPeople.length && ii < ((i * 8) + 8); ii++) {
        final StringBuilder emoji = new StringBuilder();
        for (int iii = 0; iii < emojisPeople[ii].length; iii++) {
          emoji.append(new String(Character.toChars(emojisPeople[ii][iii])));
        }

        final int finalii = ii;
        final TextView textView = new TextView(MagicKeyboardService.this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
          cellDimension,
          cellDimension);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setText(emoji);
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getCurrentInputConnection().commitText(emoji, 1);
            saveRecentEmoji(emojisPeople[finalii]);
            onPress(-999999999);
          }
        });
        textView.setOnTouchListener(onTouchListenerKey);

        row.addView(textView);
      }

      linearLayoutPeopleEmojis.addView(row);
    }

    linearLayoutPeopleEmojis.setVisibility(View.GONE);

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (relativeLayoutEmojisContainer.indexOfChild(linearLayoutPeopleEmojis) != -1) {
          relativeLayoutEmojisContainer.removeView(linearLayoutPeopleEmojis);
        }
        relativeLayoutEmojisContainer.addView(linearLayoutPeopleEmojis);
      }
    });
  }

  private void loadEmojisAnimal() {
    linearLayoutAnimalEmojis = new LinearLayout(this);
    final RelativeLayout.LayoutParams linearLayoutAnimalEmojisParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutAnimalEmojis.setLayoutParams(linearLayoutAnimalEmojisParams);
    linearLayoutAnimalEmojis.setOrientation(LinearLayout.VERTICAL);
    linearLayoutAnimalEmojis.setVisibility(View.INVISIBLE);
    linearLayoutAnimalEmojis.setEnabled(false);

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };

    for (int i = 0; i < (Math.floor(emojisAnimals.length / 8) + 1); i++) {
      final LinearLayout row = new LinearLayout(this);
      final LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(rowParams);
      row.setOrientation(LinearLayout.HORIZONTAL);

      for (int ii = (i * 8); ii < emojisAnimals.length && ii < ((i * 8) + 8); ii++) {
        final StringBuilder emoji = new StringBuilder();
        for (int iii = 0; iii < emojisAnimals[ii].length; iii++) {
          emoji.append(new String(Character.toChars(emojisAnimals[ii][iii])));
        }

        final int finalii = ii;
        final TextView textView = new TextView(MagicKeyboardService.this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
          cellDimension,
          cellDimension);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setText(emoji);
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getCurrentInputConnection().commitText(emoji, 1);
            saveRecentEmoji(emojisAnimals[finalii]);
            onPress(-999999999);
          }
        });
        textView.setOnTouchListener(onTouchListenerKey);

        row.addView(textView);
      }

      linearLayoutAnimalEmojis.addView(row);
    }

    linearLayoutAnimalEmojis.setVisibility(View.GONE);

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (relativeLayoutEmojisContainer.indexOfChild(linearLayoutAnimalEmojis) != -1) {
          relativeLayoutEmojisContainer.removeView(linearLayoutAnimalEmojis);
        }
        relativeLayoutEmojisContainer.addView(linearLayoutAnimalEmojis);
      }
    });
  }

  private void loadEmojisFood() {
    linearLayoutFoodEmojis = new LinearLayout(this);
    final RelativeLayout.LayoutParams linearLayoutAnimalEmojisParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutFoodEmojis.setLayoutParams(linearLayoutAnimalEmojisParams);
    linearLayoutFoodEmojis.setOrientation(LinearLayout.VERTICAL);
    linearLayoutFoodEmojis.setVisibility(View.INVISIBLE);
    linearLayoutFoodEmojis.setEnabled(false);

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };

    for (int i = 0; i < (Math.floor(emojisFood.length / 8) + 1); i++) {
      final LinearLayout row = new LinearLayout(this);
      final LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(rowParams);
      row.setOrientation(LinearLayout.HORIZONTAL);

      for (int ii = (i * 8); ii < emojisFood.length && ii < ((i * 8) + 8); ii++) {
        final StringBuilder emoji = new StringBuilder();
        for (int iii = 0; iii < emojisFood[ii].length; iii++) {
          emoji.append(new String(Character.toChars(emojisFood[ii][iii])));
        }

        final int finalii = ii;
        final TextView textView = new TextView(MagicKeyboardService.this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
          cellDimension,
          cellDimension);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setText(emoji);
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getCurrentInputConnection().commitText(emoji, 1);
            saveRecentEmoji(emojisFood[finalii]);
            onPress(-999999999);
          }
        });
        textView.setOnTouchListener(onTouchListenerKey);

        row.addView(textView);
      }

      linearLayoutFoodEmojis.addView(row);
    }

    linearLayoutFoodEmojis.setVisibility(View.GONE);

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (relativeLayoutEmojisContainer.indexOfChild(linearLayoutFoodEmojis) != -1) {
          relativeLayoutEmojisContainer.removeView(linearLayoutFoodEmojis);
        }
        relativeLayoutEmojisContainer.addView(linearLayoutFoodEmojis);
      }
    });
  }

  private void loadEmojisSport() {
    linearLayoutSportEmojis = new LinearLayout(this);
    final RelativeLayout.LayoutParams linearLayoutAnimalEmojisParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutSportEmojis.setLayoutParams(linearLayoutAnimalEmojisParams);
    linearLayoutSportEmojis.setOrientation(LinearLayout.VERTICAL);
    linearLayoutSportEmojis.setVisibility(View.INVISIBLE);
    linearLayoutSportEmojis.setEnabled(false);

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };

    for (int i = 0; i < (Math.floor(emojisSports.length / 8) + 1); i++) {
      final LinearLayout row = new LinearLayout(this);
      final LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(rowParams);
      row.setOrientation(LinearLayout.HORIZONTAL);

      for (int ii = (i * 8); ii < emojisSports.length && ii < ((i * 8) + 8); ii++) {
        final StringBuilder emoji = new StringBuilder();
        for (int iii = 0; iii < emojisSports[ii].length; iii++) {
          emoji.append(new String(Character.toChars(emojisSports[ii][iii])));
        }

        final int finalii = ii;
        final TextView textView = new TextView(MagicKeyboardService.this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
          cellDimension,
          cellDimension);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setText(emoji);
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getCurrentInputConnection().commitText(emoji, 1);
            saveRecentEmoji(emojisSports[finalii]);
            onPress(-999999999);
          }
        });
        textView.setOnTouchListener(onTouchListenerKey);

        row.addView(textView);
      }

      linearLayoutSportEmojis.addView(row);
    }

    linearLayoutSportEmojis.setVisibility(View.GONE);

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (relativeLayoutEmojisContainer.indexOfChild(linearLayoutSportEmojis) != -1) {
          relativeLayoutEmojisContainer.removeView(linearLayoutSportEmojis);
        }
        relativeLayoutEmojisContainer.addView(linearLayoutSportEmojis);
      }
    });
  }

  private void loadEmojisTravel() {
    linearLayoutTravelEmojis = new LinearLayout(this);
    final RelativeLayout.LayoutParams linearLayoutAnimalEmojisParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutTravelEmojis.setLayoutParams(linearLayoutAnimalEmojisParams);
    linearLayoutTravelEmojis.setOrientation(LinearLayout.VERTICAL);
    linearLayoutTravelEmojis.setVisibility(View.INVISIBLE);
    linearLayoutTravelEmojis.setEnabled(false);

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };

    for (int i = 0; i < (Math.floor(emojisTravel.length / 8) + 1); i++) {
      final LinearLayout row = new LinearLayout(this);
      final LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(rowParams);
      row.setOrientation(LinearLayout.HORIZONTAL);

      for (int ii = (i * 8); ii < emojisTravel.length && ii < ((i * 8) + 8); ii++) {
        final StringBuilder emoji = new StringBuilder();
        for (int iii = 0; iii < emojisTravel[ii].length; iii++) {
          emoji.append(new String(Character.toChars(emojisTravel[ii][iii])));
        }

        final int finalii = ii;
        final TextView textView = new TextView(MagicKeyboardService.this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
          cellDimension,
          cellDimension);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setText(emoji);
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getCurrentInputConnection().commitText(emoji, 1);
            saveRecentEmoji(emojisTravel[finalii]);
            onPress(-999999999);
          }
        });
        textView.setOnTouchListener(onTouchListenerKey);

        row.addView(textView);
      }

      linearLayoutTravelEmojis.addView(row);
    }

    linearLayoutTravelEmojis.setVisibility(View.GONE);

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (relativeLayoutEmojisContainer.indexOfChild(linearLayoutTravelEmojis) != -1) {
          relativeLayoutEmojisContainer.removeView(linearLayoutTravelEmojis);
        }
        relativeLayoutEmojisContainer.addView(linearLayoutTravelEmojis);
      }
    });
  }

  private void loadEmojisObject() {
    linearLayoutObjectEmojis = new LinearLayout(this);
    final RelativeLayout.LayoutParams linearLayoutAnimalEmojisParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutObjectEmojis.setLayoutParams(linearLayoutAnimalEmojisParams);
    linearLayoutObjectEmojis.setOrientation(LinearLayout.VERTICAL);
    linearLayoutObjectEmojis.setVisibility(View.INVISIBLE);
    linearLayoutObjectEmojis.setEnabled(false);

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };

    for (int i = 0; i < (Math.floor(emojisObjects.length / 8) + 1); i++) {
      final LinearLayout row = new LinearLayout(this);
      final LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(rowParams);
      row.setOrientation(LinearLayout.HORIZONTAL);

      for (int ii = (i * 8); ii < emojisObjects.length && ii < ((i * 8) + 8); ii++) {
        final StringBuilder emoji = new StringBuilder();
        for (int iii = 0; iii < emojisObjects[ii].length; iii++) {
          emoji.append(new String(Character.toChars(emojisObjects[ii][iii])));
        }

        final int finalii = ii;
        final TextView textView = new TextView(MagicKeyboardService.this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
          cellDimension,
          cellDimension);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setText(emoji);
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getCurrentInputConnection().commitText(emoji, 1);
            saveRecentEmoji(emojisObjects[finalii]);
            onPress(-999999999);
          }
        });
        textView.setOnTouchListener(onTouchListenerKey);

        row.addView(textView);
      }

      linearLayoutObjectEmojis.addView(row);
    }

    linearLayoutObjectEmojis.setVisibility(View.GONE);

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (relativeLayoutEmojisContainer.indexOfChild(linearLayoutObjectEmojis) != -1) {
          relativeLayoutEmojisContainer.removeView(linearLayoutObjectEmojis);
        }
        relativeLayoutEmojisContainer.addView(linearLayoutObjectEmojis);
      }
    });
  }

  private void loadEmojisSymbol() {
    linearLayoutSymbolEmojis = new LinearLayout(this);
    final RelativeLayout.LayoutParams linearLayoutAnimalEmojisParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutSymbolEmojis.setLayoutParams(linearLayoutAnimalEmojisParams);
    linearLayoutSymbolEmojis.setOrientation(LinearLayout.VERTICAL);
    linearLayoutSymbolEmojis.setVisibility(View.INVISIBLE);
    linearLayoutSymbolEmojis.setEnabled(false);

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };

    for (int i = 0; i < (Math.floor(emojisSymbols.length / 8) + 1); i++) {
      final LinearLayout row = new LinearLayout(this);
      final LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(rowParams);
      row.setOrientation(LinearLayout.HORIZONTAL);

      for (int ii = (i * 8); ii < emojisSymbols.length && ii < ((i * 8) + 8); ii++) {
        final StringBuilder emoji = new StringBuilder();
        for (int iii = 0; iii < emojisSymbols[ii].length; iii++) {
          emoji.append(new String(Character.toChars(emojisSymbols[ii][iii])));
        }

        final int finalii = ii;
        final TextView textView = new TextView(MagicKeyboardService.this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
          cellDimension,
          cellDimension);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setText(emoji);
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getCurrentInputConnection().commitText(emoji, 1);
            saveRecentEmoji(emojisSymbols[finalii]);
            onPress(-999999999);
          }
        });
        textView.setOnTouchListener(onTouchListenerKey);

        row.addView(textView);
      }

      linearLayoutSymbolEmojis.addView(row);
    }

    linearLayoutSymbolEmojis.setVisibility(View.GONE);

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (relativeLayoutEmojisContainer.indexOfChild(linearLayoutSymbolEmojis) != -1) {
          relativeLayoutEmojisContainer.removeView(linearLayoutSymbolEmojis);
        }
        relativeLayoutEmojisContainer.addView(linearLayoutSymbolEmojis);
      }
    });
  }

  private void loadEmojisFlag() {
    linearLayoutFlagEmojis = new LinearLayout(this);
    final RelativeLayout.LayoutParams linearLayoutAnimalEmojisParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutFlagEmojis.setLayoutParams(linearLayoutAnimalEmojisParams);
    linearLayoutFlagEmojis.setOrientation(LinearLayout.VERTICAL);
    linearLayoutFlagEmojis.setVisibility(View.INVISIBLE);
    linearLayoutFlagEmojis.setEnabled(false);

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };

    for (int i = 0; i < (Math.floor(emojisFlags.length / 8) + 1); i++) {
      final LinearLayout row = new LinearLayout(this);
      final LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
      row.setLayoutParams(rowParams);
      row.setOrientation(LinearLayout.HORIZONTAL);

      for (int ii = (i * 8); ii < emojisFlags.length && ii < ((i * 8) + 8); ii++) {
        final StringBuilder emoji = new StringBuilder();
        for (int iii = 0; iii < emojisFlags[ii].length; iii++) {
          emoji.append(new String(Character.toChars(emojisFlags[ii][iii])));
        }

        final int finalii = ii;
        final TextView textView = new TextView(MagicKeyboardService.this);
        final LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
          cellDimension,
          cellDimension);
        textView.setLayoutParams(textViewParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PT, 11);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER);
        textView.setText(emoji);
        textView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            getCurrentInputConnection().commitText(emoji, 1);
            saveRecentEmoji(emojisFlags[finalii]);
            onPress(-999999999);
          }
        });
        textView.setOnTouchListener(onTouchListenerKey);

        row.addView(textView);
      }

      linearLayoutFlagEmojis.addView(row);
    }

    linearLayoutFlagEmojis.setVisibility(View.GONE);

    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (relativeLayoutEmojisContainer.indexOfChild(linearLayoutFlagEmojis) != -1) {
          relativeLayoutEmojisContainer.removeView(linearLayoutFlagEmojis);
        }
        relativeLayoutEmojisContainer.addView(linearLayoutFlagEmojis);
      }
    });
  }

  private void loadEmojiKeyboardCategory(final int category) {
    currentEmojiCategory = category;

    relativeLayoutNoRecentEmojisContainer.setVisibility(View.GONE);

    linearLayoutRecentEmojis.setVisibility(View.INVISIBLE);
    linearLayoutPeopleEmojis.setVisibility(View.INVISIBLE);
    linearLayoutAnimalEmojis.setVisibility(View.INVISIBLE);
    linearLayoutFoodEmojis.setVisibility(View.INVISIBLE);
    linearLayoutSportEmojis.setVisibility(View.INVISIBLE);
    linearLayoutTravelEmojis.setVisibility(View.INVISIBLE);
    linearLayoutObjectEmojis.setVisibility(View.INVISIBLE);
    linearLayoutSymbolEmojis.setVisibility(View.INVISIBLE);
    linearLayoutFlagEmojis.setVisibility(View.INVISIBLE);

    linearLayoutRecentEmojis.setEnabled(false);
    linearLayoutPeopleEmojis.setEnabled(false);
    linearLayoutAnimalEmojis.setEnabled(false);
    linearLayoutFoodEmojis.setEnabled(false);
    linearLayoutSportEmojis.setEnabled(false);
    linearLayoutTravelEmojis.setEnabled(false);
    linearLayoutObjectEmojis.setEnabled(false);
    linearLayoutSymbolEmojis.setEnabled(false);
    linearLayoutFlagEmojis.setEnabled(false);

    final int backgroundColorKeySymbol = Color.parseColor("#" + fillKeySymbol);
    imageViewEmojiRecentButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiPeopleButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiAnimalsButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiFoodButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiSportButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiTravelButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiObjectsButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiSymbolsButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiFlagsButton.setColorFilter(backgroundColorKeySymbol);

    final int backgroundColorToggle = Color.parseColor("#" + fillToggle);
    switch (category) {
      case 0:
        loadEmojisRecent();
        linearLayoutRecentEmojis.setVisibility(View.VISIBLE);
        linearLayoutRecentEmojis.setEnabled(true);
        imageViewEmojiRecentButton.setColorFilter(backgroundColorToggle);
        if (emojisRecent[0].length == 0) {
          final TextView noRecentEmojisText = (TextView)
            relativeLayoutNoRecentEmojisContainer.getChildAt(0);
          noRecentEmojisText.setTextColor(backgroundColorKeySymbol);
          relativeLayoutNoRecentEmojisContainer.setVisibility(View.VISIBLE);
        }
        break;
      case 1:
        linearLayoutPeopleEmojis.setVisibility(View.VISIBLE);
        linearLayoutPeopleEmojis.setEnabled(true);
        imageViewEmojiPeopleButton.setColorFilter(backgroundColorToggle);
        break;
      case 2:
        linearLayoutAnimalEmojis.setVisibility(View.VISIBLE);
        linearLayoutAnimalEmojis.setEnabled(true);
        imageViewEmojiAnimalsButton.setColorFilter(backgroundColorToggle);
        break;
      case 3:
        linearLayoutFoodEmojis.setVisibility(View.VISIBLE);
        linearLayoutFoodEmojis.setEnabled(true);
        imageViewEmojiFoodButton.setColorFilter(backgroundColorToggle);
        break;
      case 4:
        linearLayoutSportEmojis.setVisibility(View.VISIBLE);
        linearLayoutSportEmojis.setEnabled(true);
        imageViewEmojiSportButton.setColorFilter(backgroundColorToggle);
        break;
      case 5:
        linearLayoutTravelEmojis.setVisibility(View.VISIBLE);
        linearLayoutTravelEmojis.setEnabled(true);
        imageViewEmojiTravelButton.setColorFilter(backgroundColorToggle);
        break;
      case 6:
        linearLayoutObjectEmojis.setVisibility(View.VISIBLE);
        linearLayoutObjectEmojis.setEnabled(true);
        imageViewEmojiObjectsButton.setColorFilter(backgroundColorToggle);
        break;
      case 7:
        linearLayoutSymbolEmojis.setVisibility(View.VISIBLE);
        linearLayoutSymbolEmojis.setEnabled(true);
        imageViewEmojiSymbolsButton.setColorFilter(backgroundColorToggle);
        break;
      case 8:
        linearLayoutFlagEmojis.setVisibility(View.VISIBLE);
        linearLayoutFlagEmojis.setEnabled(true);
        imageViewEmojiFlagsButton.setColorFilter(backgroundColorToggle);
        break;
    }

//    scrollViewEmojisContainer.scrollTo(0, 0);
    relativeLayoutEmojiKeyboardView.setVisibility(View.VISIBLE);
  }

  private void loadKeyboardConfig(final String activeConfigID) {
    try {
      final JSONObject keyboards = app.getJSONObject("keyboards");
      final JSONObject activeKeyboardConfig = keyboards.getJSONObject(activeConfigID);

      fillBackground = activeKeyboardConfig.getString("fill_background");
      fillKey = activeKeyboardConfig.getString("fill_key");
      fillKeyPressed = activeKeyboardConfig.getString("fill_key_pressed");
      fillKeyPressedSpecialSymbol = activeKeyboardConfig.getString("fill_key_pressed_special_symbol");
      fillKeyPressedSymbol = activeKeyboardConfig.getString("fill_key_pressed_symbol");
      fillKeySpecialSymbol = activeKeyboardConfig.getString("fill_key_special_symbol");
      fillKeySymbol = activeKeyboardConfig.getString("fill_key_symbol");
      fillModifier = activeKeyboardConfig.getString("fill_modifier");
      fillModifierPressed = activeKeyboardConfig.getString("fill_modifier_pressed");
      fillModifierPressedSymbol = activeKeyboardConfig.getString("fill_modifier_pressed_symbol");
      fillModifierSymbol = activeKeyboardConfig.getString("fill_modifier_symbol");
      fillToggle = activeKeyboardConfig.getString("fill_toggle");
      fillTogglePressed = activeKeyboardConfig.getString("fill_toggle_pressed");
      fillTogglePressedSymbol = activeKeyboardConfig.getString("fill_toggle_pressed_symbol");
      fillToggleSymbol = activeKeyboardConfig.getString("fill_toggle_symbol");

      holdDuration = activeKeyboardConfig.getInt("hold_duration");
      repeatBackspace = activeKeyboardConfig.getInt("repeat_backspace");
      repeatSpacebar = activeKeyboardConfig.getInt("repeat_spacebar");
      repeatRate = activeKeyboardConfig.getInt("repeat_rate");
      scrollDistanceX = activeKeyboardConfig.getInt("sensitivity_horizontal");
      scrollDistanceY = activeKeyboardConfig.getInt("sensitivity_vertical");
      selectVertical = activeKeyboardConfig.getInt("select_vertical");
      selectHorizontal = activeKeyboardConfig.getInt("select_horizontal");
      swipeHorizontal = activeKeyboardConfig.getInt("swipe_horizontal");
      swipeVertical = activeKeyboardConfig.getInt("swipe_vertical");
      swipeHorizontalOnlyText = activeKeyboardConfig.getInt("swipe_horizontal_only_text");
      swipeVerticalOnlyText = activeKeyboardConfig.getInt("swipe_vertical_only_text");

      if (swipeHorizontal == 1 && swipeVertical == 1) {
        if (swipeHorizontalOnlyText == 1 && swipeVerticalOnlyText == 1) {
          if (selectHorizontal == 1 && selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                          break;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (!hasTouchedFromModifier) {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_LEFT,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_LEFT);
                            }
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_RIGHT,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_RIGHT);
                            }
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_UP,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_UP);
                            }
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_DOWN,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_DOWN);
                            }
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        }
                      } else {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else if (selectHorizontal == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeHorizontalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_LEFT);
                              lastScrollCoordX = x;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeHorizontalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_RIGHT);
                              lastScrollCoordX = x;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else if (selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeVerticalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_UP);
                              lastScrollCoordY = y;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeVerticalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_DOWN);
                              lastScrollCoordY = y;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                }
                return false;
              }
            });
          }
        } else if (swipeHorizontalOnlyText == 1) {
          if (selectHorizontal == 1 && selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (!hasTouchedFromModifier) {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_LEFT,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_LEFT);
                            }
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0).length() != 0
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_RIGHT,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_RIGHT);
                            }
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_UP,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                          }
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_DOWN,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                          }
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else if (selectHorizontal == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        sendKeyDown(
                          inputConnection,
                          KeyEvent.KEYCODE_DPAD_UP);
                        lastScrollCoordY = y;
                        hasSwiped = true;
                        return true;
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        sendKeyDown(
                          inputConnection,
                          KeyEvent.KEYCODE_DPAD_DOWN);
                        lastScrollCoordY = y;
                        hasSwiped = true;
                        return true;
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else if (selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (!hasTouchedFromModifier) {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_LEFT,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_LEFT);
                            }
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0).length() != 0
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_RIGHT,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_RIGHT);
                            }
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_UP,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                          }
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_DOWN,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                          }
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (!hasTouchedFromModifier) {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_LEFT,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_LEFT);
                            }
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0).length() != 0
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_RIGHT,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_RIGHT);
                            }
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_UP,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                          }
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_DOWN,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                          }
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          }
        } else if (swipeVerticalOnlyText == 1) {
          if (selectHorizontal == 1 && selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (!hasTouchedFromModifier) {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_LEFT,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                          }
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_RIGHT,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                          }
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_UP,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_UP);
                            }
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (
                               (chars != null && chars.length() == 1)
                            || inputConnection.getSelectedText(0) != null
                          ) {
                            if (ctrl) {
                              inputConnection.sendKeyEvent(
                                new KeyEvent(
                                  0,
                                  0,
                                  KeyEvent.ACTION_DOWN,
                                  KeyEvent.KEYCODE_DPAD_DOWN,
                                  0,
                                  KeyEvent.META_CTRL_ON));
                            } else {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_DOWN);
                            }
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        }
                      } else {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else if (selectHorizontal == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeHorizontalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_LEFT);
                              lastScrollCoordX = x;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeHorizontalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_RIGHT);
                              lastScrollCoordX = x;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else if (selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeVerticalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_UP);
                              lastScrollCoordY = y;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeVerticalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_DOWN);
                              lastScrollCoordY = y;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                }
                return false;
              }
            });
          }
        } else {
          if (selectHorizontal == 1 && selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (!hasTouchedFromModifier) {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_LEFT,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                          }
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_RIGHT,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                          }
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_UP,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                          }
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          if (ctrl) {
                            inputConnection.sendKeyEvent(
                              new KeyEvent(
                                0,
                                0,
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_DPAD_DOWN,
                                0,
                                KeyEvent.META_CTRL_ON));
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                          }
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else if (selectHorizontal == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeHorizontalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_LEFT);
                              lastScrollCoordX = x;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeHorizontalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_RIGHT);
                              lastScrollCoordX = x;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else if (selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeVerticalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_UP);
                              lastScrollCoordY = y;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (!hasTouchedFromModifier) {
                          if (swipeVerticalOnlyText == 1) {
                            final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                            if (chars != null && chars.length() == 1) {
                              sendKeyDown(
                                inputConnection,
                                KeyEvent.KEYCODE_DPAD_DOWN);
                              lastScrollCoordY = y;
                              hasSwiped = true;
                              return true;
                            }
                          } else {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float y = e.getY();
                      final float differenceX = x - lastScrollCoordX;
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeVerticalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                }
                return false;
              }
            });
          }
        }
      } else if (swipeHorizontal == 1) {
        if (swipeHorizontalOnlyText == 1) {
          if (selectHorizontal == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float differenceX = x - lastScrollCoordX;
                      if (!hasTouchedFromModifier) {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        }
                      } else { // selecting text
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float differenceX = x - lastScrollCoordX;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                }
                return false;
              }
            });
          }
        } else {
          if (selectHorizontal == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float differenceX = x - lastScrollCoordX;
                      if (!hasTouchedFromModifier) {
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else { // selecting text
                        if (differenceX <= -scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        } else if (differenceX > scrollDistanceX) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordX = e.getX();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float x = e.getX();
                      final float differenceX = x - lastScrollCoordX;
                      if (differenceX <= -scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_LEFT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_LEFT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceX > scrollDistanceX) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        if (swipeHorizontalOnlyText == 1) {
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_RIGHT);
                            lastScrollCoordX = x;
                            hasSwiped = true;
                            return true;
                          }
                        } else {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_RIGHT);
                          lastScrollCoordX = x;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                }
                return false;
              }
            });
          }
        }
      } else if (swipeVertical == 1) {
        if (swipeVerticalOnlyText == 1) {
          if (selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float y = e.getY();
                      final float differenceY = y - lastScrollCoordY;
                      if (!hasTouchedFromModifier) {
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_UP);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                          if (chars != null && chars.length() == 1) {
                            sendKeyDown(
                              inputConnection,
                              KeyEvent.KEYCODE_DPAD_DOWN);
                            lastScrollCoordY = y;
                            hasSwiped = true;
                            return true;
                          }
                        }
                      } else {
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float y = e.getY();
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        final CharSequence chars = inputConnection.getTextBeforeCursor(1, 0);
                        if (chars != null && chars.length() == 1) {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        final CharSequence chars = inputConnection.getTextAfterCursor(1, 0);
                        if (chars != null && chars.length() == 1) {
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                }
                return false;
              }
            });
          }
        } else {
          if (selectVertical == 1) {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    if (!hasTouchedFromModifier) {
                      switch (lastPressedKeyCode) {
                        case 9999:  // escape
                        case -1:    // shift
                        case -4:    // return
                        case -5:    // backspace
                        case -9995: // caps
                        case -9996: // alt
                        case -9997: // ctrl
                        case -9998: // toggle
                          hasTouchedFromModifier = true;
                      }
                    }
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float y = e.getY();
                      final float differenceY = y - lastScrollCoordY;
                      if (!hasTouchedFromModifier) {
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      } else {
                        if (differenceY <= -scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_UP);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        } else if (differenceY > scrollDistanceY) {
                          final InputConnection inputConnection = getCurrentInputConnection();
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_SHIFT_LEFT);
                          sendKeyDown(
                            inputConnection,
                            KeyEvent.KEYCODE_DPAD_DOWN);
                          lastScrollCoordY = y;
                          hasSwiped = true;
                          return true;
                        }
                      }
                    }
                    return false;
                  case MotionEvent.ACTION_UP:
                    if (hasSwiped && hasTouchedFromModifier) {
                      sendKeyUp(
                        getCurrentInputConnection(),
                        KeyEvent.KEYCODE_SHIFT_LEFT);
                    }
                    hasTouchedFromModifier = false;
                    return false;
                }
                return false;
              }
            });
          } else {
            keyboardView.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent e) {
                switch (e.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    hasSwiped = false;
                    lastScrollCoordY = e.getY();
                    touchDownTime = e.getEventTime();
                    return false;
                  case MotionEvent.ACTION_MOVE:
                    final int timeSinceTouchDown = (int) (e.getEventTime() - touchDownTime);
                    if (timeSinceTouchDown > swipeStartDelay) {
                      final float y = e.getY();
                      final float differenceY = y - lastScrollCoordY;
                      if (differenceY <= -scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        sendKeyDown(
                          inputConnection,
                          KeyEvent.KEYCODE_DPAD_UP);
                        lastScrollCoordY = y;
                        hasSwiped = true;
                        return true;
                      } else if (differenceY > scrollDistanceY) {
                        final InputConnection inputConnection = getCurrentInputConnection();
                        sendKeyDown(
                          inputConnection,
                          KeyEvent.KEYCODE_DPAD_DOWN);
                        lastScrollCoordY = y;
                        hasSwiped = true;
                        return true;
                      }
                    }
                    return false;
                }
                return false;
              }
            });
          }
        }
      } else {
        keyboardView.setOnTouchListener(null);
      }

      final int backgroundColor = Color.parseColor("#" + fillBackground);
      keyboardView.getBackground().setColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP);
      relativeLayoutEmojiKeyboardView.setBackgroundColor(backgroundColor);

      vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
      audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    } catch (Exception e) {
      DebugService.logStackTrace(e);
    }
  }

  private void initEmojiKeyboard() {
    linearLayoutRecentEmojis = new LinearLayout(this);
    final RelativeLayout.LayoutParams linearLayoutRecentEmojisParams = new RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.MATCH_PARENT,
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    linearLayoutRecentEmojis.setLayoutParams(linearLayoutRecentEmojisParams);
    linearLayoutRecentEmojis.setOrientation(LinearLayout.VERTICAL);
    linearLayoutRecentEmojis.setVisibility(View.INVISIBLE);
    linearLayoutRecentEmojis.setEnabled(false);
    new Handler(Looper.getMainLooper()).post(new Runnable() {
      @Override
      public void run() {
        if (relativeLayoutEmojisContainer.indexOfChild(linearLayoutRecentEmojis) != -1) {
          relativeLayoutEmojisContainer.removeView(linearLayoutRecentEmojis);
        }
        relativeLayoutEmojisContainer.addView(linearLayoutRecentEmojis);
      }
    });

    final LinearLayout linearLayoutEmojiKeyboardBottomBar = relativeLayoutEmojiKeyboardView.findViewById(R.id.linearLayoutEmojiKeyboardBottom);
    scrollViewEmojisContainer = relativeLayoutEmojiKeyboardView.findViewById(R.id.scrollViewEmojisContainer);

    final FrameLayout frameLayoutToggleKeyboardButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutToggleKeyboardButton);
    final FrameLayout frameLayoutBackspaceButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutBackspaceButton);
    frameLayoutToggleKeyboardButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        toggleKeyboardConfig();
        relativeLayoutEmojiKeyboardView.setVisibility(View.GONE);
      }
    });
    frameLayoutToggleKeyboardButton.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            onPress(-9998); // toggle
            v.setBackgroundColor(Color.parseColor("#" + fillModifierPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            onRelease(-9998); // toggle
            v.setBackgroundColor(Color.parseColor("#" + fillModifier));
            return false;
        }
        return false;
      }
    });
    frameLayoutBackspaceButton.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            onKey(-5, new int[]{});
            onPress(-5); // delete
            v.setBackgroundColor(Color.parseColor("#" + fillModifierPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            onRelease(-5); // delete
            v.setBackgroundColor(Color.parseColor("#" + fillModifier));
            return false;
        }
        return false;
      }
    });

    final FrameLayout frameLayoutEmojiRecentButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutEmojiRecentButton);
    final FrameLayout frameLayoutEmojiPeopleButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutEmojiPeopleButton);
    final FrameLayout frameLayoutEmojiAnimalsButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutEmojiAnimalsButton);
    final FrameLayout frameLayoutEmojiFoodButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutEmojiFoodButton);
    final FrameLayout frameLayoutEmojiSportButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutEmojiSportButton);
    final FrameLayout frameLayoutEmojiTravelButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutEmojiTravelButton);
    final FrameLayout frameLayoutEmojiObjectsButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutEmojiObjectsButton);
    final FrameLayout frameLayoutEmojiSymbolsButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutEmojiSymbolsButton);
    final FrameLayout frameLayoutEmojiFlagsButton = linearLayoutEmojiKeyboardBottomBar.findViewById(R.id.frameLayoutEmojiFlagsButton);

    frameLayoutEmojiRecentButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentEmojiCategory != 0) {
          loadEmojiKeyboardCategory(0);
        }
      }
    });
    frameLayoutEmojiPeopleButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentEmojiCategory != 1) {
          loadEmojiKeyboardCategory(1);
        }
      }
    });
    frameLayoutEmojiAnimalsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentEmojiCategory != 2) {
          loadEmojiKeyboardCategory(2);
        }
      }
    });
    frameLayoutEmojiFoodButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentEmojiCategory != 3) {
          loadEmojiKeyboardCategory(3);
        }
      }
    });
    frameLayoutEmojiSportButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentEmojiCategory != 4) {
          loadEmojiKeyboardCategory(4);
        }
      }
    });
    frameLayoutEmojiTravelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentEmojiCategory != 5) {
          loadEmojiKeyboardCategory(5);
        }
      }
    });
    frameLayoutEmojiObjectsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentEmojiCategory != 6) {
          loadEmojiKeyboardCategory(6);
        }
      }
    });
    frameLayoutEmojiSymbolsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentEmojiCategory != 7) {
          loadEmojiKeyboardCategory(7);
        }
      }
    });
    frameLayoutEmojiFlagsButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentEmojiCategory != 8) {
          loadEmojiKeyboardCategory(8);
        }
      }
    });

    final int backgroundColorModifier = Color.parseColor("#" + fillModifier);
    frameLayoutToggleKeyboardButton.setBackgroundColor(backgroundColorModifier);
    frameLayoutBackspaceButton.setBackgroundColor(backgroundColorModifier);

    final int backgroundColorKey = Color.parseColor("#" + fillKey);
    frameLayoutEmojiRecentButton.setBackgroundColor(backgroundColorKey);
    frameLayoutEmojiPeopleButton.setBackgroundColor(backgroundColorKey);
    frameLayoutEmojiAnimalsButton.setBackgroundColor(backgroundColorKey);
    frameLayoutEmojiFoodButton.setBackgroundColor(backgroundColorKey);
    frameLayoutEmojiSportButton.setBackgroundColor(backgroundColorKey);
    frameLayoutEmojiTravelButton.setBackgroundColor(backgroundColorKey);
    frameLayoutEmojiObjectsButton.setBackgroundColor(backgroundColorKey);
    frameLayoutEmojiSymbolsButton.setBackgroundColor(backgroundColorKey);
    frameLayoutEmojiFlagsButton.setBackgroundColor(backgroundColorKey);

    final View.OnTouchListener onTouchListenerKey = new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            v.setBackgroundColor(Color.parseColor("#" + fillKeyPressed));
            return false;
          case MotionEvent.ACTION_UP:
          case MotionEvent.ACTION_CANCEL:
            v.setBackgroundColor(Color.parseColor("#" + fillKey));
            return false;
        }
        return false;
      }
    };
    frameLayoutEmojiRecentButton.setOnTouchListener(onTouchListenerKey);
    frameLayoutEmojiPeopleButton.setOnTouchListener(onTouchListenerKey);
    frameLayoutEmojiAnimalsButton.setOnTouchListener(onTouchListenerKey);
    frameLayoutEmojiFoodButton.setOnTouchListener(onTouchListenerKey);
    frameLayoutEmojiSportButton.setOnTouchListener(onTouchListenerKey);
    frameLayoutEmojiTravelButton.setOnTouchListener(onTouchListenerKey);
    frameLayoutEmojiObjectsButton.setOnTouchListener(onTouchListenerKey);
    frameLayoutEmojiSymbolsButton.setOnTouchListener(onTouchListenerKey);
    frameLayoutEmojiFlagsButton.setOnTouchListener(onTouchListenerKey);

    final int backgroundColorModifierSymbol = Color.parseColor("#" + fillModifierSymbol);
    final ImageView imageViewToggleKeyboardButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewToggleKeyboardButton);
    final ImageView imageViewBackspaceButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewBackspaceButton);
    imageViewToggleKeyboardButton.setColorFilter(backgroundColorModifierSymbol);
    imageViewBackspaceButton.setColorFilter(backgroundColorModifierSymbol);

    final int backgroundColorKeySymbol = Color.parseColor("#" + fillKeySymbol);
    imageViewEmojiRecentButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewEmojiRecentButton);
    imageViewEmojiPeopleButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewEmojiPeopleButton);
    imageViewEmojiAnimalsButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewEmojiAnimalsButton);
    imageViewEmojiFoodButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewEmojiFoodButton);
    imageViewEmojiSportButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewEmojiSportButton);
    imageViewEmojiTravelButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewEmojiTravelButton);
    imageViewEmojiObjectsButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewEmojiObjectsButton);
    imageViewEmojiSymbolsButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewEmojiSymbolsButton);
    imageViewEmojiFlagsButton = relativeLayoutEmojiKeyboardView.findViewById(R.id.imageViewEmojiFlagsButton);
    imageViewEmojiRecentButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiPeopleButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiAnimalsButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiFoodButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiSportButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiTravelButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiObjectsButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiSymbolsButton.setColorFilter(backgroundColorKeySymbol);
    imageViewEmojiFlagsButton.setColorFilter(backgroundColorKeySymbol);
  }

  private void initKeyboard() {
    app = JSONService.loadJSON();
    final JSONObject rules = app.optJSONObject("rules");

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        try {
          foregroundPackageName = getCurrentInputEditorInfo().packageName;

          JSONObject thisPackageRules = new JSONObject();
          String activeConfigID;
          if (foregroundPackageName.equals(getPackageName())) {
            final SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            activeConfigID = prefs.getString("editingConfigID", "");
          } else {
            if (JSONService.containsString(rules.names(), foregroundPackageName)) {
              thisPackageRules = rules.getJSONObject(foregroundPackageName);
              activeConfigID = thisPackageRules.getString("current");
            } else {
              thisPackageRules = rules.getJSONObject("*");
              activeConfigID = thisPackageRules.getString("current");
            }
          }

          if (activeConfigID.equals("-1")) {
            final JSONArray configIDs = thisPackageRules.getJSONArray("config");
            for (int i = 0; i < configIDs.length(); i++) {
              final String thisConfigID = configIDs.getString(i);
              if (!thisConfigID.equals("-1")) {
                activeConfigID = thisConfigID;
                break;
              }
            }
          }

          loadKeyboardConfig(activeConfigID);
          resetAllModifierKeys();

          initEmojiKeyboard();
        } catch (Exception e) {
          DebugService.logStackTrace(e);
        }
      }
    }, 1);
  }

  @Override
  public void onWindowShown() {
    super.onWindowShown();
    if (keyboardView != null) {
      initKeyboard();
      relativeLayoutEmojiKeyboardView.setVisibility(View.GONE);
    } else {
      new CountDownTimer(4000, 10) {
        @Override
        public void onTick(long millisUntilFinished) {
          if (keyboardView != null) {
            initKeyboard();
            relativeLayoutEmojiKeyboardView.setVisibility(View.GONE);
            this.cancel();
          }
        }
        @Override
        public void onFinish() {}
      };
    }
  }

  @Override
  @SuppressLint("ClickableViewAccessibility")
  public View onCreateInputView() {
    deviceWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    cellDimension = (deviceWidth / 8);

    rootView = (ConstraintLayout) getLayoutInflater().inflate(
      R.layout.keyboard,
      null);

    final ConstraintLayout emojiKeyboardView = (ConstraintLayout) getLayoutInflater().inflate(
      R.layout.keyboard_emoji,
      rootView,
      false);
    relativeLayoutEmojiKeyboardView = rootView.findViewById(R.id.relativeLayoutEmojiKeyboardView);
    relativeLayoutEmojiKeyboardView.addView(emojiKeyboardView);
    relativeLayoutEmojisContainer = relativeLayoutEmojiKeyboardView.findViewById(R.id.relativeLayoutEmojisContainer);
    relativeLayoutNoRecentEmojisContainer = relativeLayoutEmojiKeyboardView.findViewById(R.id.relativeLayoutNoRecentEmojisContainer);
    app = JSONService.loadJSON();
    final JSONObject jsonObjectRecentEmojis = app.optJSONObject("recent_emojis");
    if (jsonObjectRecentEmojis != null) {
      final JSONArray recentEmojiIndexes = jsonObjectRecentEmojis.names();
      if (recentEmojiIndexes != null) {
        try {
          emojisRecent = new int[recentEmojiIndexes.length()][];
          for (int i = 0; i < recentEmojiIndexes.length(); i++) {
            final String index = recentEmojiIndexes.getString(i);
            final JSONArray emojiBytes = jsonObjectRecentEmojis.optJSONArray(index);
            if (emojiBytes != null) {
              int[] emojiBytecodes = new int[emojiBytes.length()];
              for (int ii = 0; ii < emojiBytes.length(); ii++) {
                emojiBytecodes[ii] = emojiBytes.getInt(ii);
              }
              emojisRecent[i] = emojiBytecodes;
            }
          }
        } catch (Exception e) {
          // do nothing
        }
      }
    }
    new Thread(new Runnable() {
      @Override
      public void run() {
        loadEmojisPeople();
      }
    }).start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        loadEmojisAnimal();
      }
    }).start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        loadEmojisFood();
      }
    }).start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        loadEmojisSport();
      }
    }).start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        loadEmojisTravel();
      }
    }).start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        loadEmojisObject();
      }
    }).start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        loadEmojisSymbol();
      }
    }).start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        loadEmojisFlag();
      }
    }).start();

    keyboard = new Keyboard(this, R.xml.keyboard_qwerty_full);
    keyboard.setShifted(false);

    keyboardView = rootView.findViewById(R.id.magicKeyboardView);
    keyboardView.setKeyboard(keyboard);
    keyboardView.setOnKeyboardActionListener(this);
    keyboardView.setPreviewEnabled(false);

    return rootView;
  }
}
