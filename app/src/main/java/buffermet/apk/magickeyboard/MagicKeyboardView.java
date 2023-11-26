package buffermet.apk.magickeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.view.KeyEvent;

import java.lang.reflect.Field;
import java.util.List;

public class MagicKeyboardView extends KeyboardView {
  public MagicKeyboardView(final Context context, final AttributeSet attrs) {
    super(context, attrs);
  }

//    public void showWithAnimation(final Animation animation) {
//        animation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                setVisibility(View.VISIBLE);
//            }
//        });
//
//        setAnimation(animation);
//    }

  @Override
  public void invalidateAllKeys() {
    super.invalidateAllKeys();
  }

  @Override
  public void onDraw(final Canvas canvas) {
    super.onDraw(canvas);

    final Drawable drawableBackground = getResources().getDrawable(R.drawable.empty);
    drawableBackground.setColorFilter(
      Color.parseColor("#" + MagicKeyboardService.fillBackground),
      PorterDuff.Mode.SRC_ATOP);

    final List<Keyboard.Key> keys = getKeyboard().getKeys();
    for (Keyboard.Key key : keys) {
      drawableBackground.setBounds(
        key.x,
        key.y,
        key.x + key.width,
        key.y + key.height);
      drawableBackground.draw(canvas);

      final Drawable drawableKey = getResources().getDrawable(R.drawable.empty);
      switch (key.codes[0]) {
        case 9999:
        case -1:
        case -9996:
        case -9997:
          if (key.pressed) {
            drawableKey.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillModifierPressed),
              PorterDuff.Mode.SRC_ATOP);
          } else {
            drawableKey.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillModifier),
              PorterDuff.Mode.SRC_ATOP);
          }
          drawableKey.setBounds(
            key.x,
            key.y,
            key.x + key.width,
            key.y + key.height);
          drawableKey.draw(canvas);
          if (key.on) {
            final Drawable indicator = getResources().getDrawable(R.drawable.circle);
            indicator.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillToggle),
              PorterDuff.Mode.SRC_ATOP);
            indicator.setBounds(
              key.x + key.width - 30,
              key.y + 16,
              key.x + key.width - 14,
              key.y + 32);
            indicator.draw(canvas);
          }
          break;
        case -4:
        case -5:
        case -9994:
        case -9998:
        case -9999:
          if (key.pressed) {
            drawableKey.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillModifierPressed),
              PorterDuff.Mode.SRC_ATOP);
          } else {
            drawableKey.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillModifier),
              PorterDuff.Mode.SRC_ATOP);
          }
          drawableKey.setBounds(
            key.x,
            key.y,
            key.x + key.width,
            key.y + key.height);
          drawableKey.draw(canvas);
          break;
        case -9995: // caps
          if (key.pressed) {
            drawableKey.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillTogglePressed),
              PorterDuff.Mode.SRC_ATOP);
          } else {
            drawableKey.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillToggle),
              PorterDuff.Mode.SRC_ATOP);
          }
          drawableKey.setBounds(
            key.x,
            key.y,
            key.x + key.width,
            key.y + key.height);
          drawableKey.draw(canvas);
          break;
        default:
          if (key.pressed) {
            drawableKey.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillKeyPressed),
              PorterDuff.Mode.SRC_ATOP);
          } else {
            drawableKey.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillKey),
              PorterDuff.Mode.SRC_ATOP);
          }
          drawableKey.setBounds(
            key.x,
            key.y,
            key.x + key.width,
            key.y + key.height);
          drawableKey.draw(canvas);
      }

      final Rect rectPaintSpecialSymbol = new Rect();
      final Paint paintSpecialSymbol= new Paint();
      paintSpecialSymbol.setAntiAlias(true);
      paintSpecialSymbol.setTextAlign(Paint.Align.CENTER);
      String subLabel = "";
      switch (key.codes[0]) {
        case 39: // '
          subLabel = "\"";
          break;
        case 47: // /
          subLabel = "?";
          break;
        case 48: // 0
          subLabel = ")";
          break;
        case 49: // 1
          subLabel = "!";
          break;
        case 50: // 2
          subLabel = "@";
          break;
        case 51: // 3
          subLabel = "#";
          break;
        case 52: // 4
          subLabel = "$";
          break;
        case 53: // 5
          subLabel = "%";
          break;
        case 54: // 6
          subLabel = "^";
          break;
        case 55: // 7
          subLabel = "&";
          break;
        case 56: // 8
          subLabel = "*";
          break;
        case 57: // 9
          subLabel = "(";
          break;
        case 59: // ;
          subLabel = ":";
          break;
        case 96: // `
          subLabel = "~";
          break;
        case 98: // b
          subLabel = "<";
          break;
//        case 99: // c
//            subLabel = "c";
//            break;
        case 102: // f
          subLabel = "|";
          break;
        case 103: // g
          subLabel = "\\";
          break;
        case 104: // h
          subLabel = "[";
          break;
//        case 101: // e
//            subLabel = "e";
//            break;
        case 105: // i
          subLabel = "-";
          break;
        case 106: // j
          subLabel = "]";
          break;
        case 107: // k
          subLabel = "{";
          break;
        case 108: // l
          subLabel = "}";
          break;
        case 109: // m
          subLabel = ",";
          break;
        case 110: // n
          subLabel = ">";
          break;
        case 111: // o
          subLabel = "+";
          break;
        case 112: // p
          subLabel = "=";
          break;
//        case 113: // q
//            subLabel = "q";
//            break;
//        case 114: // r
//            subLabel = "r";
//            break;
//        case 116: // t
//            subLabel = "|";
//            break;
        case 117: // u
          subLabel = "_";
          break;
//        case 118: // v
//            subLabel = "<";
//            break;
//        case 119: // w
//            subLabel = "w";
//            break;
//        case 121: // y
//            subLabel = "/";
//            break;
      }
      if (!subLabel.equals("")) {
        if (key.pressed) {
          paintSpecialSymbol.setColor(Color.parseColor(
            "#" + MagicKeyboardService.fillKeyPressedSpecialSymbol));
        } else {
          paintSpecialSymbol.setColor(Color.parseColor(
            "#" + MagicKeyboardService.fillKeySpecialSymbol));
        }
        paintSpecialSymbol.setTypeface(Typeface.create(
          "sans-serif-medium",
          Typeface.NORMAL));
        paintSpecialSymbol.setTextSize(30f);
        paintSpecialSymbol.getTextBounds(
          subLabel,
          0,
          subLabel.length(),
          rectPaintSpecialSymbol);
        canvas.drawText(
          subLabel,
          key.x + (key.width / 2) + 26,
          ((key.y + key.height / 2) + rectPaintSpecialSymbol.height() / 2) - 41,
          paintSpecialSymbol);
      }

      final Rect rectPaintSymbol = new Rect();
      final Paint paintSymbol= new Paint();
      paintSymbol.setAntiAlias(true);
      paintSymbol.setTextAlign(Paint.Align.CENTER);
      if (key.label != null) {
        String label;
        if (isShifted()) {
          label = key.label.toString().toUpperCase();
        } else {
          label = key.label.toString();
        }
        if (key.modifier) {
          if (key.pressed) {
            paintSymbol.setColor(Color.parseColor(
              "#" + MagicKeyboardService.fillModifierPressedSymbol));
          } else {
            paintSymbol.setColor(Color.parseColor(
              "#" + MagicKeyboardService.fillModifierSymbol));
          }
        } else {
          if (key.pressed) {
            paintSymbol.setColor(Color.parseColor(
              "#" + MagicKeyboardService.fillKeyPressedSymbol));
          } else {
            paintSymbol.setColor(Color.parseColor(
              "#" + MagicKeyboardService.fillKeySymbol));
          }
        }
        int labelTextSize = 0;
        try {
          final Field field = KeyboardView.class.getDeclaredField(
            "mLabelTextSize");
          field.setAccessible(true);
          labelTextSize = (int) field.get(this);
        } catch (Exception e) {
          e.printStackTrace();
        }
        paintSymbol.setTypeface(Typeface.create(
          "sans-serif-medium",
          Typeface.NORMAL));
        paintSymbol.setTextSize(labelTextSize);
        paintSymbol.getTextBounds(
          label,
          0,
          label.length(),
          rectPaintSymbol);
        canvas.drawText(
          label,
          key.x + (key.width / 2),
          (key.y + key.height / 2) + rectPaintSymbol.height() / 2,
          paintSymbol);
      } else if (key.icon != null) {
        if (key.codes[0] == -9995) {
          if (key.pressed) {
            key.icon.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillTogglePressedSymbol),
              PorterDuff.Mode.SRC_ATOP);
          } else {
            key.icon.setColorFilter(Color.parseColor(
              "#" + MagicKeyboardService.fillToggleSymbol),
              PorterDuff.Mode.SRC_ATOP);
          }
        } else {
          if (key.modifier) {
            if (key.pressed) {
              key.icon.setColorFilter(Color.parseColor(
                "#" + MagicKeyboardService.fillModifierPressedSymbol),
                PorterDuff.Mode.SRC_ATOP);
            } else {
              key.icon.setColorFilter(Color.parseColor(
                "#" + MagicKeyboardService.fillModifierSymbol),
                PorterDuff.Mode.SRC_ATOP);
            }
          } else {
            if (key.pressed) {
              key.icon.setColorFilter(Color.parseColor(
                "#" + MagicKeyboardService.fillKeyPressedSymbol),
                PorterDuff.Mode.SRC_ATOP);
            } else {
              key.icon.setColorFilter(Color.parseColor(
                "#" + MagicKeyboardService.fillKeySymbol),
                PorterDuff.Mode.SRC_ATOP);
            }
          }
        }
        key.icon.setBounds(
          key.x + ((key.width - key.icon.getIntrinsicWidth()) / 2),
          key.y + ((key.height - key.icon.getIntrinsicHeight()) / 2),
          key.x + ((key.width - key.icon.getIntrinsicWidth()) / 2)
            + key.icon.getIntrinsicWidth(),
          key.y + ((key.height - key.icon.getIntrinsicHeight()) / 2)
            + key.icon.getIntrinsicHeight());
        key.icon.draw(canvas);
      }
    }
  }
}
