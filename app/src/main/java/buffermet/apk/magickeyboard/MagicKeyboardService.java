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

import androidx.annotation.Dimension;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

@SuppressLint("ClickableViewAccessibility")
public class MagicKeyboardService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
//    final int[][] emojis = new int[][]{new int[]{0x1F600},new int[]{0x1F603},new int[]{0x1F604},new int[]{0x1F601},new int[]{0x1F606},new int[]{0x1F605},new int[]{0x1F923},new int[]{0x1F602},new int[]{0x1F642},new int[]{0x1F643},new int[]{0x1F609},new int[]{0x1F60A},new int[]{0x1F607},new int[]{0x1F970},new int[]{0x1F60D},new int[]{0x1F929},new int[]{0x1F618},new int[]{0x1F617},new int[]{0x263A,0xFE0F},new int[]{0x263A},new int[]{0x1F61A},new int[]{0x1F619},new int[]{0x1F60B},new int[]{0x1F61B},new int[]{0x1F61C},new int[]{0x1F92A},new int[]{0x1F61D},new int[]{0x1F911},new int[]{0x1F917},new int[]{0x1F92D},new int[]{0x1F92B},new int[]{0x1F914},new int[]{0x1F910},new int[]{0x1F928},new int[]{0x1F610},new int[]{0x1F611},new int[]{0x1F636},new int[]{0x1F60F},new int[]{0x1F612},new int[]{0x1F644},new int[]{0x1F62C},new int[]{0x1F925},new int[]{0x1F60C},new int[]{0x1F614},new int[]{0x1F62A},new int[]{0x1F924},new int[]{0x1F634},new int[]{0x1F637},new int[]{0x1F912},new int[]{0x1F915},new int[]{0x1F922},new int[]{0x1F92E},new int[]{0x1F927},new int[]{0x1F975},new int[]{0x1F976},new int[]{0x1F974},new int[]{0x1F635},new int[]{0x1F92F},new int[]{0x1F920},new int[]{0x1F973},new int[]{0x1F60E},new int[]{0x1F913},new int[]{0x1F9D0},new int[]{0x1F615},new int[]{0x1F61F},new int[]{0x1F641},new int[]{0x2639,0xFE0F},new int[]{0x2639},new int[]{0x1F62E},new int[]{0x1F62F},new int[]{0x1F632},new int[]{0x1F633},new int[]{0x1F97A},new int[]{0x1F626},new int[]{0x1F627},new int[]{0x1F628},new int[]{0x1F630},new int[]{0x1F625},new int[]{0x1F622},new int[]{0x1F62D},new int[]{0x1F631},new int[]{0x1F616},new int[]{0x1F623},new int[]{0x1F61E},new int[]{0x1F613},new int[]{0x1F629},new int[]{0x1F62B},new int[]{0x1F971},new int[]{0x1F624},new int[]{0x1F621},new int[]{0x1F620},new int[]{0x1F92C},new int[]{0x1F608},new int[]{0x1F47F},new int[]{0x1F480},new int[]{0x2620,0xFE0F},new int[]{0x2620},new int[]{0x1F4A9},new int[]{0x1F921},new int[]{0x1F479},new int[]{0x1F47A},new int[]{0x1F47B},new int[]{0x1F47D},new int[]{0x1F47E},new int[]{0x1F916},new int[]{0x1F63A},new int[]{0x1F638},new int[]{0x1F639},new int[]{0x1F63B},new int[]{0x1F63C},new int[]{0x1F63D},new int[]{0x1F640},new int[]{0x1F63F},new int[]{0x1F63E},new int[]{0x1F648},new int[]{0x1F649},new int[]{0x1F64A},new int[]{0x1F48B},new int[]{0x1F48C},new int[]{0x1F498},new int[]{0x1F49D},new int[]{0x1F496},new int[]{0x1F497},new int[]{0x1F493},new int[]{0x1F49E},new int[]{0x1F495},new int[]{0x1F49F},new int[]{0x2763,0xFE0F},new int[]{0x2763},new int[]{0x1F494},new int[]{0x2764,0xFE0F},new int[]{0x2764},new int[]{0x1F9E1},new int[]{0x1F49B},new int[]{0x1F49A},new int[]{0x1F499},new int[]{0x1F49C},new int[]{0x1F90E},new int[]{0x1F5A4},new int[]{0x1F90D},new int[]{0x1F4AF},new int[]{0x1F4A2},new int[]{0x1F4A5},new int[]{0x1F4AB},new int[]{0x1F4A6},new int[]{0x1F4A8},new int[]{0x1F573,0xFE0F},new int[]{0x1F573},new int[]{0x1F4A3},new int[]{0x1F4AC},new int[]{0x1F441,0xFE0F,0x200D,0x1F5E8,0xFE0F},new int[]{0x1F441,0x200D,0x1F5E8,0xFE0F},new int[]{0x1F441,0xFE0F,0x200D,0x1F5E8},new int[]{0x1F441,0x200D,0x1F5E8},new int[]{0x1F5E8,0xFE0F},new int[]{0x1F5E8},new int[]{0x1F5EF,0xFE0F},new int[]{0x1F5EF},new int[]{0x1F4AD},new int[]{0x1F4A4},new int[]{0x1F44B},new int[]{0x1F44B,0x1F3FB},new int[]{0x1F44B,0x1F3FC},new int[]{0x1F44B,0x1F3FD},new int[]{0x1F44B,0x1F3FE},new int[]{0x1F44B,0x1F3FF},new int[]{0x1F91A},new int[]{0x1F91A,0x1F3FB},new int[]{0x1F91A,0x1F3FC},new int[]{0x1F91A,0x1F3FD},new int[]{0x1F91A,0x1F3FE},new int[]{0x1F91A,0x1F3FF},new int[]{0x1F590,0xFE0F},new int[]{0x1F590},new int[]{0x1F590,0x1F3FB},new int[]{0x1F590,0x1F3FC},new int[]{0x1F590,0x1F3FD},new int[]{0x1F590,0x1F3FE},new int[]{0x1F590,0x1F3FF},new int[]{0x270B},new int[]{0x270B,0x1F3FB},new int[]{0x270B,0x1F3FC},new int[]{0x270B,0x1F3FD},new int[]{0x270B,0x1F3FE},new int[]{0x270B,0x1F3FF},new int[]{0x1F596},new int[]{0x1F596,0x1F3FB},new int[]{0x1F596,0x1F3FC},new int[]{0x1F596,0x1F3FD},new int[]{0x1F596,0x1F3FE},new int[]{0x1F596,0x1F3FF},new int[]{0x1F44C},new int[]{0x1F44C,0x1F3FB},new int[]{0x1F44C,0x1F3FC},new int[]{0x1F44C,0x1F3FD},new int[]{0x1F44C,0x1F3FE},new int[]{0x1F44C,0x1F3FF},new int[]{0x1F90F},new int[]{0x1F90F,0x1F3FB},new int[]{0x1F90F,0x1F3FC},new int[]{0x1F90F,0x1F3FD},new int[]{0x1F90F,0x1F3FE},new int[]{0x1F90F,0x1F3FF},new int[]{0x270C,0xFE0F},new int[]{0x270C},new int[]{0x270C,0x1F3FB},new int[]{0x270C,0x1F3FC},new int[]{0x270C,0x1F3FD},new int[]{0x270C,0x1F3FE},new int[]{0x270C,0x1F3FF},new int[]{0x1F91E},new int[]{0x1F91E,0x1F3FB},new int[]{0x1F91E,0x1F3FC},new int[]{0x1F91E,0x1F3FD},new int[]{0x1F91E,0x1F3FE},new int[]{0x1F91E,0x1F3FF},new int[]{0x1F91F},new int[]{0x1F91F,0x1F3FB},new int[]{0x1F91F,0x1F3FC},new int[]{0x1F91F,0x1F3FD},new int[]{0x1F91F,0x1F3FE},new int[]{0x1F91F,0x1F3FF},new int[]{0x1F918},new int[]{0x1F918,0x1F3FB},new int[]{0x1F918,0x1F3FC},new int[]{0x1F918,0x1F3FD},new int[]{0x1F918,0x1F3FE},new int[]{0x1F918,0x1F3FF},new int[]{0x1F919},new int[]{0x1F919,0x1F3FB},new int[]{0x1F919,0x1F3FC},new int[]{0x1F919,0x1F3FD},new int[]{0x1F919,0x1F3FE},new int[]{0x1F919,0x1F3FF},new int[]{0x1F448},new int[]{0x1F448,0x1F3FB},new int[]{0x1F448,0x1F3FC},new int[]{0x1F448,0x1F3FD},new int[]{0x1F448,0x1F3FE},new int[]{0x1F448,0x1F3FF},new int[]{0x1F449},new int[]{0x1F449,0x1F3FB},new int[]{0x1F449,0x1F3FC},new int[]{0x1F449,0x1F3FD},new int[]{0x1F449,0x1F3FE},new int[]{0x1F449,0x1F3FF},new int[]{0x1F446},new int[]{0x1F446,0x1F3FB},new int[]{0x1F446,0x1F3FC},new int[]{0x1F446,0x1F3FD},new int[]{0x1F446,0x1F3FE},new int[]{0x1F446,0x1F3FF},new int[]{0x1F595},new int[]{0x1F595,0x1F3FB},new int[]{0x1F595,0x1F3FC},new int[]{0x1F595,0x1F3FD},new int[]{0x1F595,0x1F3FE},new int[]{0x1F595,0x1F3FF},new int[]{0x1F447},new int[]{0x1F447,0x1F3FB},new int[]{0x1F447,0x1F3FC},new int[]{0x1F447,0x1F3FD},new int[]{0x1F447,0x1F3FE},new int[]{0x1F447,0x1F3FF},new int[]{0x261D,0xFE0F},new int[]{0x261D},new int[]{0x261D,0x1F3FB},new int[]{0x261D,0x1F3FC},new int[]{0x261D,0x1F3FD},new int[]{0x261D,0x1F3FE},new int[]{0x261D,0x1F3FF},new int[]{0x1F44D},new int[]{0x1F44D,0x1F3FB},new int[]{0x1F44D,0x1F3FC},new int[]{0x1F44D,0x1F3FD},new int[]{0x1F44D,0x1F3FE},new int[]{0x1F44D,0x1F3FF},new int[]{0x1F44E},new int[]{0x1F44E,0x1F3FB},new int[]{0x1F44E,0x1F3FC},new int[]{0x1F44E,0x1F3FD},new int[]{0x1F44E,0x1F3FE},new int[]{0x1F44E,0x1F3FF},new int[]{0x270A},new int[]{0x270A,0x1F3FB},new int[]{0x270A,0x1F3FC},new int[]{0x270A,0x1F3FD},new int[]{0x270A,0x1F3FE},new int[]{0x270A,0x1F3FF},new int[]{0x1F44A},new int[]{0x1F44A,0x1F3FB},new int[]{0x1F44A,0x1F3FC},new int[]{0x1F44A,0x1F3FD},new int[]{0x1F44A,0x1F3FE},new int[]{0x1F44A,0x1F3FF},new int[]{0x1F91B},new int[]{0x1F91B,0x1F3FB},new int[]{0x1F91B,0x1F3FC},new int[]{0x1F91B,0x1F3FD},new int[]{0x1F91B,0x1F3FE},new int[]{0x1F91B,0x1F3FF},new int[]{0x1F91C},new int[]{0x1F91C,0x1F3FB},new int[]{0x1F91C,0x1F3FC},new int[]{0x1F91C,0x1F3FD},new int[]{0x1F91C,0x1F3FE},new int[]{0x1F91C,0x1F3FF},new int[]{0x1F44F},new int[]{0x1F44F,0x1F3FB},new int[]{0x1F44F,0x1F3FC},new int[]{0x1F44F,0x1F3FD},new int[]{0x1F44F,0x1F3FE},new int[]{0x1F44F,0x1F3FF},new int[]{0x1F64C},new int[]{0x1F64C,0x1F3FB},new int[]{0x1F64C,0x1F3FC},new int[]{0x1F64C,0x1F3FD},new int[]{0x1F64C,0x1F3FE},new int[]{0x1F64C,0x1F3FF},new int[]{0x1F450},new int[]{0x1F450,0x1F3FB},new int[]{0x1F450,0x1F3FC},new int[]{0x1F450,0x1F3FD},new int[]{0x1F450,0x1F3FE},new int[]{0x1F450,0x1F3FF},new int[]{0x1F932},new int[]{0x1F932,0x1F3FB},new int[]{0x1F932,0x1F3FC},new int[]{0x1F932,0x1F3FD},new int[]{0x1F932,0x1F3FE},new int[]{0x1F932,0x1F3FF},new int[]{0x1F91D},new int[]{0x1F64F},new int[]{0x1F64F,0x1F3FB},new int[]{0x1F64F,0x1F3FC},new int[]{0x1F64F,0x1F3FD},new int[]{0x1F64F,0x1F3FE},new int[]{0x1F64F,0x1F3FF},new int[]{0x270D,0xFE0F},new int[]{0x270D},new int[]{0x270D,0x1F3FB},new int[]{0x270D,0x1F3FC},new int[]{0x270D,0x1F3FD},new int[]{0x270D,0x1F3FE},new int[]{0x270D,0x1F3FF},new int[]{0x1F485},new int[]{0x1F485,0x1F3FB},new int[]{0x1F485,0x1F3FC},new int[]{0x1F485,0x1F3FD},new int[]{0x1F485,0x1F3FE},new int[]{0x1F485,0x1F3FF},new int[]{0x1F933},new int[]{0x1F933,0x1F3FB},new int[]{0x1F933,0x1F3FC},new int[]{0x1F933,0x1F3FD},new int[]{0x1F933,0x1F3FE},new int[]{0x1F933,0x1F3FF},new int[]{0x1F4AA},new int[]{0x1F4AA,0x1F3FB},new int[]{0x1F4AA,0x1F3FC},new int[]{0x1F4AA,0x1F3FD},new int[]{0x1F4AA,0x1F3FE},new int[]{0x1F4AA,0x1F3FF},new int[]{0x1F9BE},new int[]{0x1F9BF},new int[]{0x1F9B5},new int[]{0x1F9B5,0x1F3FB},new int[]{0x1F9B5,0x1F3FC},new int[]{0x1F9B5,0x1F3FD},new int[]{0x1F9B5,0x1F3FE},new int[]{0x1F9B5,0x1F3FF},new int[]{0x1F9B6},new int[]{0x1F9B6,0x1F3FB},new int[]{0x1F9B6,0x1F3FC},new int[]{0x1F9B6,0x1F3FD},new int[]{0x1F9B6,0x1F3FE},new int[]{0x1F9B6,0x1F3FF},new int[]{0x1F442},new int[]{0x1F442,0x1F3FB},new int[]{0x1F442,0x1F3FC},new int[]{0x1F442,0x1F3FD},new int[]{0x1F442,0x1F3FE},new int[]{0x1F442,0x1F3FF},new int[]{0x1F9BB},new int[]{0x1F9BB,0x1F3FB},new int[]{0x1F9BB,0x1F3FC},new int[]{0x1F9BB,0x1F3FD},new int[]{0x1F9BB,0x1F3FE},new int[]{0x1F9BB,0x1F3FF},new int[]{0x1F443},new int[]{0x1F443,0x1F3FB},new int[]{0x1F443,0x1F3FC},new int[]{0x1F443,0x1F3FD},new int[]{0x1F443,0x1F3FE},new int[]{0x1F443,0x1F3FF},new int[]{0x1F9E0},new int[]{0x1F9B7},new int[]{0x1F9B4},new int[]{0x1F440},new int[]{0x1F441,0xFE0F},new int[]{0x1F441},new int[]{0x1F445},new int[]{0x1F444},new int[]{0x1F476},new int[]{0x1F476,0x1F3FB},new int[]{0x1F476,0x1F3FC},new int[]{0x1F476,0x1F3FD},new int[]{0x1F476,0x1F3FE},new int[]{0x1F476,0x1F3FF},new int[]{0x1F9D2},new int[]{0x1F9D2,0x1F3FB},new int[]{0x1F9D2,0x1F3FC},new int[]{0x1F9D2,0x1F3FD},new int[]{0x1F9D2,0x1F3FE},new int[]{0x1F9D2,0x1F3FF},new int[]{0x1F466},new int[]{0x1F466,0x1F3FB},new int[]{0x1F466,0x1F3FC},new int[]{0x1F466,0x1F3FD},new int[]{0x1F466,0x1F3FE},new int[]{0x1F466,0x1F3FF},new int[]{0x1F467},new int[]{0x1F467,0x1F3FB},new int[]{0x1F467,0x1F3FC},new int[]{0x1F467,0x1F3FD},new int[]{0x1F467,0x1F3FE},new int[]{0x1F467,0x1F3FF},new int[]{0x1F9D1},new int[]{0x1F9D1,0x1F3FB},new int[]{0x1F9D1,0x1F3FC},new int[]{0x1F9D1,0x1F3FD},new int[]{0x1F9D1,0x1F3FE},new int[]{0x1F9D1,0x1F3FF},new int[]{0x1F471},new int[]{0x1F471,0x1F3FB},new int[]{0x1F471,0x1F3FC},new int[]{0x1F471,0x1F3FD},new int[]{0x1F471,0x1F3FE},new int[]{0x1F471,0x1F3FF},new int[]{0x1F468},new int[]{0x1F468,0x1F3FB},new int[]{0x1F468,0x1F3FC},new int[]{0x1F468,0x1F3FD},new int[]{0x1F468,0x1F3FE},new int[]{0x1F468,0x1F3FF},new int[]{0x1F9D4},new int[]{0x1F9D4,0x1F3FB},new int[]{0x1F9D4,0x1F3FC},new int[]{0x1F9D4,0x1F3FD},new int[]{0x1F9D4,0x1F3FE},new int[]{0x1F9D4,0x1F3FF},new int[]{0x1F468,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9B0},new int[]{0x1F468,0x200D,0x1F9B1},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9B1},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9B1},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9B1},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9B1},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9B1},new int[]{0x1F468,0x200D,0x1F9B3},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9B3},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9B3},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9B3},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9B3},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9B3},new int[]{0x1F468,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9B2},new int[]{0x1F469},new int[]{0x1F469,0x1F3FB},new int[]{0x1F469,0x1F3FC},new int[]{0x1F469,0x1F3FD},new int[]{0x1F469,0x1F3FE},new int[]{0x1F469,0x1F3FF},new int[]{0x1F469,0x200D,0x1F9B0},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9B0},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9B0},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9B0},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9B0},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9B0},new int[]{0x1F9D1,0x200D,0x1F9B0},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9B0},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9B0},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9B0},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9B0},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9B0},new int[]{0x1F469,0x200D,0x1F9B1},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9B1},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9B1},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9B1},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9B1},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9B1},new int[]{0x1F9D1,0x200D,0x1F9B1},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9B1},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9B1},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9B1},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9B1},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9B1},new int[]{0x1F469,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9B3},new int[]{0x1F9D1,0x200D,0x1F9B3},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9B3},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9B3},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9B3},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9B3},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9B3},new int[]{0x1F469,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9B2},new int[]{0x1F9D1,0x200D,0x1F9B2},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9B2},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9B2},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9B2},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9B2},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9B2},new int[]{0x1F471,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x200D,0x2640},new int[]{0x1F471,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FB,0x200D,0x2640},new int[]{0x1F471,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FC,0x200D,0x2640},new int[]{0x1F471,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FD,0x200D,0x2640},new int[]{0x1F471,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FE,0x200D,0x2640},new int[]{0x1F471,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FF,0x200D,0x2640},new int[]{0x1F471,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x200D,0x2642},new int[]{0x1F471,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FB,0x200D,0x2642},new int[]{0x1F471,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FC,0x200D,0x2642},new int[]{0x1F471,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FD,0x200D,0x2642},new int[]{0x1F471,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FE,0x200D,0x2642},new int[]{0x1F471,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FF,0x200D,0x2642},new int[]{0x1F9D3},new int[]{0x1F9D3,0x1F3FB},new int[]{0x1F9D3,0x1F3FC},new int[]{0x1F9D3,0x1F3FD},new int[]{0x1F9D3,0x1F3FE},new int[]{0x1F9D3,0x1F3FF},new int[]{0x1F474},new int[]{0x1F474,0x1F3FB},new int[]{0x1F474,0x1F3FC},new int[]{0x1F474,0x1F3FD},new int[]{0x1F474,0x1F3FE},new int[]{0x1F474,0x1F3FF},new int[]{0x1F475},new int[]{0x1F475,0x1F3FB},new int[]{0x1F475,0x1F3FC},new int[]{0x1F475,0x1F3FD},new int[]{0x1F475,0x1F3FE},new int[]{0x1F475,0x1F3FF},new int[]{0x1F64D},new int[]{0x1F64D,0x1F3FB},new int[]{0x1F64D,0x1F3FC},new int[]{0x1F64D,0x1F3FD},new int[]{0x1F64D,0x1F3FE},new int[]{0x1F64D,0x1F3FF},new int[]{0x1F64D,0x200D,0x2642,0xFE0F},new int[]{0x1F64D,0x200D,0x2642},new int[]{0x1F64D,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F64D,0x1F3FB,0x200D,0x2642},new int[]{0x1F64D,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F64D,0x1F3FC,0x200D,0x2642},new int[]{0x1F64D,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F64D,0x1F3FD,0x200D,0x2642},new int[]{0x1F64D,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F64D,0x1F3FE,0x200D,0x2642},new int[]{0x1F64D,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F64D,0x1F3FF,0x200D,0x2642},new int[]{0x1F64D,0x200D,0x2640,0xFE0F},new int[]{0x1F64D,0x200D,0x2640},new int[]{0x1F64D,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F64D,0x1F3FB,0x200D,0x2640},new int[]{0x1F64D,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F64D,0x1F3FC,0x200D,0x2640},new int[]{0x1F64D,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F64D,0x1F3FD,0x200D,0x2640},new int[]{0x1F64D,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F64D,0x1F3FE,0x200D,0x2640},new int[]{0x1F64D,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F64D,0x1F3FF,0x200D,0x2640},new int[]{0x1F64E},new int[]{0x1F64E,0x1F3FB},new int[]{0x1F64E,0x1F3FC},new int[]{0x1F64E,0x1F3FD},new int[]{0x1F64E,0x1F3FE},new int[]{0x1F64E,0x1F3FF},new int[]{0x1F64E,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x200D,0x2642},new int[]{0x1F64E,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FB,0x200D,0x2642},new int[]{0x1F64E,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FC,0x200D,0x2642},new int[]{0x1F64E,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FD,0x200D,0x2642},new int[]{0x1F64E,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FE,0x200D,0x2642},new int[]{0x1F64E,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FF,0x200D,0x2642},new int[]{0x1F64E,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x200D,0x2640},new int[]{0x1F64E,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FB,0x200D,0x2640},new int[]{0x1F64E,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FC,0x200D,0x2640},new int[]{0x1F64E,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FD,0x200D,0x2640},new int[]{0x1F64E,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FE,0x200D,0x2640},new int[]{0x1F64E,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FF,0x200D,0x2640},new int[]{0x1F645},new int[]{0x1F645,0x1F3FB},new int[]{0x1F645,0x1F3FC},new int[]{0x1F645,0x1F3FD},new int[]{0x1F645,0x1F3FE},new int[]{0x1F645,0x1F3FF},new int[]{0x1F645,0x200D,0x2642,0xFE0F},new int[]{0x1F645,0x200D,0x2642},new int[]{0x1F645,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F645,0x1F3FB,0x200D,0x2642},new int[]{0x1F645,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F645,0x1F3FC,0x200D,0x2642},new int[]{0x1F645,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F645,0x1F3FD,0x200D,0x2642},new int[]{0x1F645,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F645,0x1F3FE,0x200D,0x2642},new int[]{0x1F645,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F645,0x1F3FF,0x200D,0x2642},new int[]{0x1F645,0x200D,0x2640,0xFE0F},new int[]{0x1F645,0x200D,0x2640},new int[]{0x1F645,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F645,0x1F3FB,0x200D,0x2640},new int[]{0x1F645,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F645,0x1F3FC,0x200D,0x2640},new int[]{0x1F645,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F645,0x1F3FD,0x200D,0x2640},new int[]{0x1F645,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F645,0x1F3FE,0x200D,0x2640},new int[]{0x1F645,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F645,0x1F3FF,0x200D,0x2640},new int[]{0x1F646},new int[]{0x1F646,0x1F3FB},new int[]{0x1F646,0x1F3FC},new int[]{0x1F646,0x1F3FD},new int[]{0x1F646,0x1F3FE},new int[]{0x1F646,0x1F3FF},new int[]{0x1F646,0x200D,0x2642,0xFE0F},new int[]{0x1F646,0x200D,0x2642},new int[]{0x1F646,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F646,0x1F3FB,0x200D,0x2642},new int[]{0x1F646,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F646,0x1F3FC,0x200D,0x2642},new int[]{0x1F646,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F646,0x1F3FD,0x200D,0x2642},new int[]{0x1F646,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F646,0x1F3FE,0x200D,0x2642},new int[]{0x1F646,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F646,0x1F3FF,0x200D,0x2642},new int[]{0x1F646,0x200D,0x2640,0xFE0F},new int[]{0x1F646,0x200D,0x2640},new int[]{0x1F646,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F646,0x1F3FB,0x200D,0x2640},new int[]{0x1F646,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F646,0x1F3FC,0x200D,0x2640},new int[]{0x1F646,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F646,0x1F3FD,0x200D,0x2640},new int[]{0x1F646,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F646,0x1F3FE,0x200D,0x2640},new int[]{0x1F646,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F646,0x1F3FF,0x200D,0x2640},new int[]{0x1F481},new int[]{0x1F481,0x1F3FB},new int[]{0x1F481,0x1F3FC},new int[]{0x1F481,0x1F3FD},new int[]{0x1F481,0x1F3FE},new int[]{0x1F481,0x1F3FF},new int[]{0x1F481,0x200D,0x2642,0xFE0F},new int[]{0x1F481,0x200D,0x2642},new int[]{0x1F481,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F481,0x1F3FB,0x200D,0x2642},new int[]{0x1F481,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F481,0x1F3FC,0x200D,0x2642},new int[]{0x1F481,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F481,0x1F3FD,0x200D,0x2642},new int[]{0x1F481,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F481,0x1F3FE,0x200D,0x2642},new int[]{0x1F481,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F481,0x1F3FF,0x200D,0x2642},new int[]{0x1F481,0x200D,0x2640,0xFE0F},new int[]{0x1F481,0x200D,0x2640},new int[]{0x1F481,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F481,0x1F3FB,0x200D,0x2640},new int[]{0x1F481,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F481,0x1F3FC,0x200D,0x2640},new int[]{0x1F481,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F481,0x1F3FD,0x200D,0x2640},new int[]{0x1F481,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F481,0x1F3FE,0x200D,0x2640},new int[]{0x1F481,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F481,0x1F3FF,0x200D,0x2640},new int[]{0x1F64B},new int[]{0x1F64B,0x1F3FB},new int[]{0x1F64B,0x1F3FC},new int[]{0x1F64B,0x1F3FD},new int[]{0x1F64B,0x1F3FE},new int[]{0x1F64B,0x1F3FF},new int[]{0x1F64B,0x200D,0x2642,0xFE0F},new int[]{0x1F64B,0x200D,0x2642},new int[]{0x1F64B,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F64B,0x1F3FB,0x200D,0x2642},new int[]{0x1F64B,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F64B,0x1F3FC,0x200D,0x2642},new int[]{0x1F64B,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F64B,0x1F3FD,0x200D,0x2642},new int[]{0x1F64B,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F64B,0x1F3FE,0x200D,0x2642},new int[]{0x1F64B,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F64B,0x1F3FF,0x200D,0x2642},new int[]{0x1F64B,0x200D,0x2640,0xFE0F},new int[]{0x1F64B,0x200D,0x2640},new int[]{0x1F64B,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F64B,0x1F3FB,0x200D,0x2640},new int[]{0x1F64B,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F64B,0x1F3FC,0x200D,0x2640},new int[]{0x1F64B,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F64B,0x1F3FD,0x200D,0x2640},new int[]{0x1F64B,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F64B,0x1F3FE,0x200D,0x2640},new int[]{0x1F64B,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F64B,0x1F3FF,0x200D,0x2640},new int[]{0x1F9CF},new int[]{0x1F9CF,0x1F3FB},new int[]{0x1F9CF,0x1F3FC},new int[]{0x1F9CF,0x1F3FD},new int[]{0x1F9CF,0x1F3FE},new int[]{0x1F9CF,0x1F3FF},new int[]{0x1F9CF,0x200D,0x2642,0xFE0F},new int[]{0x1F9CF,0x200D,0x2642},new int[]{0x1F9CF,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9CF,0x1F3FB,0x200D,0x2642},new int[]{0x1F9CF,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9CF,0x1F3FC,0x200D,0x2642},new int[]{0x1F9CF,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9CF,0x1F3FD,0x200D,0x2642},new int[]{0x1F9CF,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9CF,0x1F3FE,0x200D,0x2642},new int[]{0x1F9CF,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9CF,0x1F3FF,0x200D,0x2642},new int[]{0x1F9CF,0x200D,0x2640,0xFE0F},new int[]{0x1F9CF,0x200D,0x2640},new int[]{0x1F9CF,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9CF,0x1F3FB,0x200D,0x2640},new int[]{0x1F9CF,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9CF,0x1F3FC,0x200D,0x2640},new int[]{0x1F9CF,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9CF,0x1F3FD,0x200D,0x2640},new int[]{0x1F9CF,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9CF,0x1F3FE,0x200D,0x2640},new int[]{0x1F9CF,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9CF,0x1F3FF,0x200D,0x2640},new int[]{0x1F647},new int[]{0x1F647,0x1F3FB},new int[]{0x1F647,0x1F3FC},new int[]{0x1F647,0x1F3FD},new int[]{0x1F647,0x1F3FE},new int[]{0x1F647,0x1F3FF},new int[]{0x1F647,0x200D,0x2642,0xFE0F},new int[]{0x1F647,0x200D,0x2642},new int[]{0x1F647,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F647,0x1F3FB,0x200D,0x2642},new int[]{0x1F647,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F647,0x1F3FC,0x200D,0x2642},new int[]{0x1F647,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F647,0x1F3FD,0x200D,0x2642},new int[]{0x1F647,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F647,0x1F3FE,0x200D,0x2642},new int[]{0x1F647,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F647,0x1F3FF,0x200D,0x2642},new int[]{0x1F647,0x200D,0x2640,0xFE0F},new int[]{0x1F647,0x200D,0x2640},new int[]{0x1F647,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F647,0x1F3FB,0x200D,0x2640},new int[]{0x1F647,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F647,0x1F3FC,0x200D,0x2640},new int[]{0x1F647,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F647,0x1F3FD,0x200D,0x2640},new int[]{0x1F647,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F647,0x1F3FE,0x200D,0x2640},new int[]{0x1F647,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F647,0x1F3FF,0x200D,0x2640},new int[]{0x1F926},new int[]{0x1F926,0x1F3FB},new int[]{0x1F926,0x1F3FC},new int[]{0x1F926,0x1F3FD},new int[]{0x1F926,0x1F3FE},new int[]{0x1F926,0x1F3FF},new int[]{0x1F926,0x200D,0x2642,0xFE0F},new int[]{0x1F926,0x200D,0x2642},new int[]{0x1F926,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F926,0x1F3FB,0x200D,0x2642},new int[]{0x1F926,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F926,0x1F3FC,0x200D,0x2642},new int[]{0x1F926,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F926,0x1F3FD,0x200D,0x2642},new int[]{0x1F926,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F926,0x1F3FE,0x200D,0x2642},new int[]{0x1F926,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F926,0x1F3FF,0x200D,0x2642},new int[]{0x1F926,0x200D,0x2640,0xFE0F},new int[]{0x1F926,0x200D,0x2640},new int[]{0x1F926,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F926,0x1F3FB,0x200D,0x2640},new int[]{0x1F926,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F926,0x1F3FC,0x200D,0x2640},new int[]{0x1F926,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F926,0x1F3FD,0x200D,0x2640},new int[]{0x1F926,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F926,0x1F3FE,0x200D,0x2640},new int[]{0x1F926,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F926,0x1F3FF,0x200D,0x2640},new int[]{0x1F937},new int[]{0x1F937,0x1F3FB},new int[]{0x1F937,0x1F3FC},new int[]{0x1F937,0x1F3FD},new int[]{0x1F937,0x1F3FE},new int[]{0x1F937,0x1F3FF},new int[]{0x1F937,0x200D,0x2642,0xFE0F},new int[]{0x1F937,0x200D,0x2642},new int[]{0x1F937,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F937,0x1F3FB,0x200D,0x2642},new int[]{0x1F937,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F937,0x1F3FC,0x200D,0x2642},new int[]{0x1F937,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F937,0x1F3FD,0x200D,0x2642},new int[]{0x1F937,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F937,0x1F3FE,0x200D,0x2642},new int[]{0x1F937,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F937,0x1F3FF,0x200D,0x2642},new int[]{0x1F937,0x200D,0x2640,0xFE0F},new int[]{0x1F937,0x200D,0x2640},new int[]{0x1F937,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F937,0x1F3FB,0x200D,0x2640},new int[]{0x1F937,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F937,0x1F3FC,0x200D,0x2640},new int[]{0x1F937,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F937,0x1F3FD,0x200D,0x2640},new int[]{0x1F937,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F937,0x1F3FE,0x200D,0x2640},new int[]{0x1F937,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F937,0x1F3FF,0x200D,0x2640},new int[]{0x1F9D1,0x200D,0x2695,0xFE0F},new int[]{0x1F9D1,0x200D,0x2695},new int[]{0x1F9D1,0x1F3FB,0x200D,0x2695,0xFE0F},new int[]{0x1F9D1,0x1F3FB,0x200D,0x2695},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2695,0xFE0F},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2695},new int[]{0x1F9D1,0x1F3FD,0x200D,0x2695,0xFE0F},new int[]{0x1F9D1,0x1F3FD,0x200D,0x2695},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2695,0xFE0F},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2695},new int[]{0x1F9D1,0x1F3FF,0x200D,0x2695,0xFE0F},new int[]{0x1F9D1,0x1F3FF,0x200D,0x2695},new int[]{0x1F468,0x200D,0x2695,0xFE0F},new int[]{0x1F468,0x200D,0x2695},new int[]{0x1F468,0x1F3FB,0x200D,0x2695,0xFE0F},new int[]{0x1F468,0x1F3FB,0x200D,0x2695},new int[]{0x1F468,0x1F3FC,0x200D,0x2695,0xFE0F},new int[]{0x1F468,0x1F3FC,0x200D,0x2695},new int[]{0x1F468,0x1F3FD,0x200D,0x2695,0xFE0F},new int[]{0x1F468,0x1F3FD,0x200D,0x2695},new int[]{0x1F468,0x1F3FE,0x200D,0x2695,0xFE0F},new int[]{0x1F468,0x1F3FE,0x200D,0x2695},new int[]{0x1F468,0x1F3FF,0x200D,0x2695,0xFE0F},new int[]{0x1F468,0x1F3FF,0x200D,0x2695},new int[]{0x1F469,0x200D,0x2695,0xFE0F},new int[]{0x1F469,0x200D,0x2695},new int[]{0x1F469,0x1F3FB,0x200D,0x2695,0xFE0F},new int[]{0x1F469,0x1F3FB,0x200D,0x2695},new int[]{0x1F469,0x1F3FC,0x200D,0x2695,0xFE0F},new int[]{0x1F469,0x1F3FC,0x200D,0x2695},new int[]{0x1F469,0x1F3FD,0x200D,0x2695,0xFE0F},new int[]{0x1F469,0x1F3FD,0x200D,0x2695},new int[]{0x1F469,0x1F3FE,0x200D,0x2695,0xFE0F},new int[]{0x1F469,0x1F3FE,0x200D,0x2695},new int[]{0x1F469,0x1F3FF,0x200D,0x2695,0xFE0F},new int[]{0x1F469,0x1F3FF,0x200D,0x2695},new int[]{0x1F9D1,0x200D,0x1F393},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F393},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F393},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F393},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F393},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F393},new int[]{0x1F468,0x200D,0x1F393},new int[]{0x1F468,0x1F3FB,0x200D,0x1F393},new int[]{0x1F468,0x1F3FC,0x200D,0x1F393},new int[]{0x1F468,0x1F3FD,0x200D,0x1F393},new int[]{0x1F468,0x1F3FE,0x200D,0x1F393},new int[]{0x1F468,0x1F3FF,0x200D,0x1F393},new int[]{0x1F469,0x200D,0x1F393},new int[]{0x1F469,0x1F3FB,0x200D,0x1F393},new int[]{0x1F469,0x1F3FC,0x200D,0x1F393},new int[]{0x1F469,0x1F3FD,0x200D,0x1F393},new int[]{0x1F469,0x1F3FE,0x200D,0x1F393},new int[]{0x1F469,0x1F3FF,0x200D,0x1F393},new int[]{0x1F9D1,0x200D,0x1F3EB},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F3EB},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F3EB},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F3EB},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F3EB},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F3EB},new int[]{0x1F468,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FB,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FC,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FD,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FE,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FF,0x200D,0x1F3EB},new int[]{0x1F469,0x200D,0x1F3EB},new int[]{0x1F469,0x1F3FB,0x200D,0x1F3EB},new int[]{0x1F469,0x1F3FC,0x200D,0x1F3EB},new int[]{0x1F469,0x1F3FD,0x200D,0x1F3EB},new int[]{0x1F469,0x1F3FE,0x200D,0x1F3EB},new int[]{0x1F469,0x1F3FF,0x200D,0x1F3EB},new int[]{0x1F9D1,0x200D,0x2696,0xFE0F},new int[]{0x1F9D1,0x200D,0x2696},new int[]{0x1F9D1,0x1F3FB,0x200D,0x2696,0xFE0F},new int[]{0x1F9D1,0x1F3FB,0x200D,0x2696},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2696,0xFE0F},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2696},new int[]{0x1F9D1,0x1F3FD,0x200D,0x2696,0xFE0F},new int[]{0x1F9D1,0x1F3FD,0x200D,0x2696},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2696,0xFE0F},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2696},new int[]{0x1F9D1,0x1F3FF,0x200D,0x2696,0xFE0F},new int[]{0x1F9D1,0x1F3FF,0x200D,0x2696},new int[]{0x1F468,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x200D,0x2696},new int[]{0x1F468,0x1F3FB,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FB,0x200D,0x2696},new int[]{0x1F468,0x1F3FC,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FC,0x200D,0x2696},new int[]{0x1F468,0x1F3FD,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FD,0x200D,0x2696},new int[]{0x1F468,0x1F3FE,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FE,0x200D,0x2696},new int[]{0x1F468,0x1F3FF,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FF,0x200D,0x2696},new int[]{0x1F469,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x200D,0x2696},new int[]{0x1F469,0x1F3FB,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FB,0x200D,0x2696},new int[]{0x1F469,0x1F3FC,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FC,0x200D,0x2696},new int[]{0x1F469,0x1F3FD,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FD,0x200D,0x2696},new int[]{0x1F469,0x1F3FE,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FE,0x200D,0x2696},new int[]{0x1F469,0x1F3FF,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FF,0x200D,0x2696},new int[]{0x1F9D1,0x200D,0x1F33E},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F33E},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F33E},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F33E},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F33E},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F33E},new int[]{0x1F468,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FB,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FC,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FD,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FE,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FF,0x200D,0x1F33E},new int[]{0x1F469,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FB,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FC,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FD,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FE,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FF,0x200D,0x1F33E},new int[]{0x1F9D1,0x200D,0x1F373},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F373},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F373},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F373},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F373},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F373},new int[]{0x1F468,0x200D,0x1F373},new int[]{0x1F468,0x1F3FB,0x200D,0x1F373},new int[]{0x1F468,0x1F3FC,0x200D,0x1F373},new int[]{0x1F468,0x1F3FD,0x200D,0x1F373},new int[]{0x1F468,0x1F3FE,0x200D,0x1F373},new int[]{0x1F468,0x1F3FF,0x200D,0x1F373},new int[]{0x1F469,0x200D,0x1F373},new int[]{0x1F469,0x1F3FB,0x200D,0x1F373},new int[]{0x1F469,0x1F3FC,0x200D,0x1F373},new int[]{0x1F469,0x1F3FD,0x200D,0x1F373},new int[]{0x1F469,0x1F3FE,0x200D,0x1F373},new int[]{0x1F469,0x1F3FF,0x200D,0x1F373},new int[]{0x1F9D1,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F527},new int[]{0x1F468,0x200D,0x1F527},new int[]{0x1F468,0x1F3FB,0x200D,0x1F527},new int[]{0x1F468,0x1F3FC,0x200D,0x1F527},new int[]{0x1F468,0x1F3FD,0x200D,0x1F527},new int[]{0x1F468,0x1F3FE,0x200D,0x1F527},new int[]{0x1F468,0x1F3FF,0x200D,0x1F527},new int[]{0x1F469,0x200D,0x1F527},new int[]{0x1F469,0x1F3FB,0x200D,0x1F527},new int[]{0x1F469,0x1F3FC,0x200D,0x1F527},new int[]{0x1F469,0x1F3FD,0x200D,0x1F527},new int[]{0x1F469,0x1F3FE,0x200D,0x1F527},new int[]{0x1F469,0x1F3FF,0x200D,0x1F527},new int[]{0x1F9D1,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F3ED},new int[]{0x1F468,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FB,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FC,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FD,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FE,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FF,0x200D,0x1F3ED},new int[]{0x1F469,0x200D,0x1F3ED},new int[]{0x1F469,0x1F3FB,0x200D,0x1F3ED},new int[]{0x1F469,0x1F3FC,0x200D,0x1F3ED},new int[]{0x1F469,0x1F3FD,0x200D,0x1F3ED},new int[]{0x1F469,0x1F3FE,0x200D,0x1F3ED},new int[]{0x1F469,0x1F3FF,0x200D,0x1F3ED},new int[]{0x1F9D1,0x200D,0x1F4BC},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F4BC},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F4BC},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F4BC},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F4BC},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F4BC},new int[]{0x1F468,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FB,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FC,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FD,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FE,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FF,0x200D,0x1F4BC},new int[]{0x1F469,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FB,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FC,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FD,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FE,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FF,0x200D,0x1F4BC},new int[]{0x1F9D1,0x200D,0x1F52C},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F52C},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F52C},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F52C},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F52C},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F52C},new int[]{0x1F468,0x200D,0x1F52C},new int[]{0x1F468,0x1F3FB,0x200D,0x1F52C},new int[]{0x1F468,0x1F3FC,0x200D,0x1F52C},new int[]{0x1F468,0x1F3FD,0x200D,0x1F52C},new int[]{0x1F468,0x1F3FE,0x200D,0x1F52C},new int[]{0x1F468,0x1F3FF,0x200D,0x1F52C},new int[]{0x1F469,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FB,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FC,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FD,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FE,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FF,0x200D,0x1F52C},new int[]{0x1F9D1,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F4BB},new int[]{0x1F468,0x200D,0x1F4BB},new int[]{0x1F468,0x1F3FB,0x200D,0x1F4BB},new int[]{0x1F468,0x1F3FC,0x200D,0x1F4BB},new int[]{0x1F468,0x1F3FD,0x200D,0x1F4BB},new int[]{0x1F468,0x1F3FE,0x200D,0x1F4BB},new int[]{0x1F468,0x1F3FF,0x200D,0x1F4BB},new int[]{0x1F469,0x200D,0x1F4BB},new int[]{0x1F469,0x1F3FB,0x200D,0x1F4BB},new int[]{0x1F469,0x1F3FC,0x200D,0x1F4BB},new int[]{0x1F469,0x1F3FD,0x200D,0x1F4BB},new int[]{0x1F469,0x1F3FE,0x200D,0x1F4BB},new int[]{0x1F469,0x1F3FF,0x200D,0x1F4BB},new int[]{0x1F9D1,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F3A4},new int[]{0x1F468,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FB,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FC,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FD,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FE,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FF,0x200D,0x1F3A4},new int[]{0x1F469,0x200D,0x1F3A4},new int[]{0x1F469,0x1F3FB,0x200D,0x1F3A4},new int[]{0x1F469,0x1F3FC,0x200D,0x1F3A4},new int[]{0x1F469,0x1F3FD,0x200D,0x1F3A4},new int[]{0x1F469,0x1F3FE,0x200D,0x1F3A4},new int[]{0x1F469,0x1F3FF,0x200D,0x1F3A4},new int[]{0x1F9D1,0x200D,0x1F3A8},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F3A8},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F3A8},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F3A8},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F3A8},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F3A8},new int[]{0x1F468,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FB,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FC,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FD,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FE,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FF,0x200D,0x1F3A8},new int[]{0x1F469,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FB,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FC,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FD,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FE,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FF,0x200D,0x1F3A8},new int[]{0x1F9D1,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x200D,0x2708},new int[]{0x1F9D1,0x1F3FB,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FB,0x200D,0x2708},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2708},new int[]{0x1F9D1,0x1F3FD,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FD,0x200D,0x2708},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2708},new int[]{0x1F9D1,0x1F3FF,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FF,0x200D,0x2708},new int[]{0x1F468,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x200D,0x2708},new int[]{0x1F468,0x1F3FB,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FB,0x200D,0x2708},new int[]{0x1F468,0x1F3FC,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FC,0x200D,0x2708},new int[]{0x1F468,0x1F3FD,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FD,0x200D,0x2708},new int[]{0x1F468,0x1F3FE,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FE,0x200D,0x2708},new int[]{0x1F468,0x1F3FF,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FF,0x200D,0x2708},new int[]{0x1F469,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x200D,0x2708},new int[]{0x1F469,0x1F3FB,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FB,0x200D,0x2708},new int[]{0x1F469,0x1F3FC,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FC,0x200D,0x2708},new int[]{0x1F469,0x1F3FD,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FD,0x200D,0x2708},new int[]{0x1F469,0x1F3FE,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FE,0x200D,0x2708},new int[]{0x1F469,0x1F3FF,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FF,0x200D,0x2708},new int[]{0x1F9D1,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F680},new int[]{0x1F468,0x200D,0x1F680},new int[]{0x1F468,0x1F3FB,0x200D,0x1F680},new int[]{0x1F468,0x1F3FC,0x200D,0x1F680},new int[]{0x1F468,0x1F3FD,0x200D,0x1F680},new int[]{0x1F468,0x1F3FE,0x200D,0x1F680},new int[]{0x1F468,0x1F3FF,0x200D,0x1F680},new int[]{0x1F469,0x200D,0x1F680},new int[]{0x1F469,0x1F3FB,0x200D,0x1F680},new int[]{0x1F469,0x1F3FC,0x200D,0x1F680},new int[]{0x1F469,0x1F3FD,0x200D,0x1F680},new int[]{0x1F469,0x1F3FE,0x200D,0x1F680},new int[]{0x1F469,0x1F3FF,0x200D,0x1F680},new int[]{0x1F9D1,0x200D,0x1F692},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F692},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F692},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F692},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F692},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F692},new int[]{0x1F468,0x200D,0x1F692},new int[]{0x1F468,0x1F3FB,0x200D,0x1F692},new int[]{0x1F468,0x1F3FC,0x200D,0x1F692},new int[]{0x1F468,0x1F3FD,0x200D,0x1F692},new int[]{0x1F468,0x1F3FE,0x200D,0x1F692},new int[]{0x1F468,0x1F3FF,0x200D,0x1F692},new int[]{0x1F469,0x200D,0x1F692},new int[]{0x1F469,0x1F3FB,0x200D,0x1F692},new int[]{0x1F469,0x1F3FC,0x200D,0x1F692},new int[]{0x1F469,0x1F3FD,0x200D,0x1F692},new int[]{0x1F469,0x1F3FE,0x200D,0x1F692},new int[]{0x1F469,0x1F3FF,0x200D,0x1F692},new int[]{0x1F46E},new int[]{0x1F46E,0x1F3FB},new int[]{0x1F46E,0x1F3FC},new int[]{0x1F46E,0x1F3FD},new int[]{0x1F46E,0x1F3FE},new int[]{0x1F46E,0x1F3FF},new int[]{0x1F46E,0x200D,0x2642,0xFE0F},new int[]{0x1F46E,0x200D,0x2642},new int[]{0x1F46E,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F46E,0x1F3FB,0x200D,0x2642},new int[]{0x1F46E,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F46E,0x1F3FC,0x200D,0x2642},new int[]{0x1F46E,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F46E,0x1F3FD,0x200D,0x2642},new int[]{0x1F46E,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F46E,0x1F3FE,0x200D,0x2642},new int[]{0x1F46E,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F46E,0x1F3FF,0x200D,0x2642},new int[]{0x1F46E,0x200D,0x2640,0xFE0F},new int[]{0x1F46E,0x200D,0x2640},new int[]{0x1F46E,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F46E,0x1F3FB,0x200D,0x2640},new int[]{0x1F46E,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F46E,0x1F3FC,0x200D,0x2640},new int[]{0x1F46E,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F46E,0x1F3FD,0x200D,0x2640},new int[]{0x1F46E,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F46E,0x1F3FE,0x200D,0x2640},new int[]{0x1F46E,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F46E,0x1F3FF,0x200D,0x2640},new int[]{0x1F575,0xFE0F},new int[]{0x1F575},new int[]{0x1F575,0x1F3FB},new int[]{0x1F575,0x1F3FC},new int[]{0x1F575,0x1F3FD},new int[]{0x1F575,0x1F3FE},new int[]{0x1F575,0x1F3FF},new int[]{0x1F575,0xFE0F,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0xFE0F,0x200D,0x2642},new int[]{0x1F575,0x200D,0x2642},new int[]{0x1F575,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FB,0x200D,0x2642},new int[]{0x1F575,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FC,0x200D,0x2642},new int[]{0x1F575,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FD,0x200D,0x2642},new int[]{0x1F575,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FE,0x200D,0x2642},new int[]{0x1F575,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FF,0x200D,0x2642},new int[]{0x1F575,0xFE0F,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0xFE0F,0x200D,0x2640},new int[]{0x1F575,0x200D,0x2640},new int[]{0x1F575,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FB,0x200D,0x2640},new int[]{0x1F575,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FC,0x200D,0x2640},new int[]{0x1F575,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FD,0x200D,0x2640},new int[]{0x1F575,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FE,0x200D,0x2640},new int[]{0x1F575,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FF,0x200D,0x2640},new int[]{0x1F482},new int[]{0x1F482,0x1F3FB},new int[]{0x1F482,0x1F3FC},new int[]{0x1F482,0x1F3FD},new int[]{0x1F482,0x1F3FE},new int[]{0x1F482,0x1F3FF},new int[]{0x1F482,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x200D,0x2642},new int[]{0x1F482,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FB,0x200D,0x2642},new int[]{0x1F482,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FC,0x200D,0x2642},new int[]{0x1F482,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FD,0x200D,0x2642},new int[]{0x1F482,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FE,0x200D,0x2642},new int[]{0x1F482,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FF,0x200D,0x2642},new int[]{0x1F482,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x200D,0x2640},new int[]{0x1F482,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FB,0x200D,0x2640},new int[]{0x1F482,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FC,0x200D,0x2640},new int[]{0x1F482,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FD,0x200D,0x2640},new int[]{0x1F482,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FE,0x200D,0x2640},new int[]{0x1F482,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FF,0x200D,0x2640},new int[]{0x1F477},new int[]{0x1F477,0x1F3FB},new int[]{0x1F477,0x1F3FC},new int[]{0x1F477,0x1F3FD},new int[]{0x1F477,0x1F3FE},new int[]{0x1F477,0x1F3FF},new int[]{0x1F477,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x200D,0x2642},new int[]{0x1F477,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FB,0x200D,0x2642},new int[]{0x1F477,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FC,0x200D,0x2642},new int[]{0x1F477,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FD,0x200D,0x2642},new int[]{0x1F477,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FE,0x200D,0x2642},new int[]{0x1F477,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FF,0x200D,0x2642},new int[]{0x1F477,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x200D,0x2640},new int[]{0x1F477,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FB,0x200D,0x2640},new int[]{0x1F477,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FC,0x200D,0x2640},new int[]{0x1F477,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FD,0x200D,0x2640},new int[]{0x1F477,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FE,0x200D,0x2640},new int[]{0x1F477,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FF,0x200D,0x2640},new int[]{0x1F934},new int[]{0x1F934,0x1F3FB},new int[]{0x1F934,0x1F3FC},new int[]{0x1F934,0x1F3FD},new int[]{0x1F934,0x1F3FE},new int[]{0x1F934,0x1F3FF},new int[]{0x1F478},new int[]{0x1F478,0x1F3FB},new int[]{0x1F478,0x1F3FC},new int[]{0x1F478,0x1F3FD},new int[]{0x1F478,0x1F3FE},new int[]{0x1F478,0x1F3FF},new int[]{0x1F473},new int[]{0x1F473,0x1F3FB},new int[]{0x1F473,0x1F3FC},new int[]{0x1F473,0x1F3FD},new int[]{0x1F473,0x1F3FE},new int[]{0x1F473,0x1F3FF},new int[]{0x1F473,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x200D,0x2642},new int[]{0x1F473,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FB,0x200D,0x2642},new int[]{0x1F473,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FC,0x200D,0x2642},new int[]{0x1F473,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FD,0x200D,0x2642},new int[]{0x1F473,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FE,0x200D,0x2642},new int[]{0x1F473,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FF,0x200D,0x2642},new int[]{0x1F473,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x200D,0x2640},new int[]{0x1F473,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FB,0x200D,0x2640},new int[]{0x1F473,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FC,0x200D,0x2640},new int[]{0x1F473,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FD,0x200D,0x2640},new int[]{0x1F473,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FE,0x200D,0x2640},new int[]{0x1F473,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FF,0x200D,0x2640},new int[]{0x1F472},new int[]{0x1F472,0x1F3FB},new int[]{0x1F472,0x1F3FC},new int[]{0x1F472,0x1F3FD},new int[]{0x1F472,0x1F3FE},new int[]{0x1F472,0x1F3FF},new int[]{0x1F9D5},new int[]{0x1F9D5,0x1F3FB},new int[]{0x1F9D5,0x1F3FC},new int[]{0x1F9D5,0x1F3FD},new int[]{0x1F9D5,0x1F3FE},new int[]{0x1F9D5,0x1F3FF},new int[]{0x1F935},new int[]{0x1F935,0x1F3FB},new int[]{0x1F935,0x1F3FC},new int[]{0x1F935,0x1F3FD},new int[]{0x1F935,0x1F3FE},new int[]{0x1F935,0x1F3FF},new int[]{0x1F470},new int[]{0x1F470,0x1F3FB},new int[]{0x1F470,0x1F3FC},new int[]{0x1F470,0x1F3FD},new int[]{0x1F470,0x1F3FE},new int[]{0x1F470,0x1F3FF},new int[]{0x1F930},new int[]{0x1F930,0x1F3FB},new int[]{0x1F930,0x1F3FC},new int[]{0x1F930,0x1F3FD},new int[]{0x1F930,0x1F3FE},new int[]{0x1F930,0x1F3FF},new int[]{0x1F931},new int[]{0x1F931,0x1F3FB},new int[]{0x1F931,0x1F3FC},new int[]{0x1F931,0x1F3FD},new int[]{0x1F931,0x1F3FE},new int[]{0x1F931,0x1F3FF},new int[]{0x1F47C},new int[]{0x1F47C,0x1F3FB},new int[]{0x1F47C,0x1F3FC},new int[]{0x1F47C,0x1F3FD},new int[]{0x1F47C,0x1F3FE},new int[]{0x1F47C,0x1F3FF},new int[]{0x1F385},new int[]{0x1F385,0x1F3FB},new int[]{0x1F385,0x1F3FC},new int[]{0x1F385,0x1F3FD},new int[]{0x1F385,0x1F3FE},new int[]{0x1F385,0x1F3FF},new int[]{0x1F936},new int[]{0x1F936,0x1F3FB},new int[]{0x1F936,0x1F3FC},new int[]{0x1F936,0x1F3FD},new int[]{0x1F936,0x1F3FE},new int[]{0x1F936,0x1F3FF},new int[]{0x1F9B8},new int[]{0x1F9B8,0x1F3FB},new int[]{0x1F9B8,0x1F3FC},new int[]{0x1F9B8,0x1F3FD},new int[]{0x1F9B8,0x1F3FE},new int[]{0x1F9B8,0x1F3FF},new int[]{0x1F9B8,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FB,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FC,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FD,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FE,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FF,0x200D,0x2642},new int[]{0x1F9B8,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FB,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FC,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FD,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FE,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FF,0x200D,0x2640},new int[]{0x1F9B9},new int[]{0x1F9B9,0x1F3FB},new int[]{0x1F9B9,0x1F3FC},new int[]{0x1F9B9,0x1F3FD},new int[]{0x1F9B9,0x1F3FE},new int[]{0x1F9B9,0x1F3FF},new int[]{0x1F9B9,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FB,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FC,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FD,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FE,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FF,0x200D,0x2642},new int[]{0x1F9B9,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FB,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FC,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FD,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FE,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FF,0x200D,0x2640},new int[]{0x1F9D9},new int[]{0x1F9D9,0x1F3FB},new int[]{0x1F9D9,0x1F3FC},new int[]{0x1F9D9,0x1F3FD},new int[]{0x1F9D9,0x1F3FE},new int[]{0x1F9D9,0x1F3FF},new int[]{0x1F9D9,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FB,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FC,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FD,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FE,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FF,0x200D,0x2642},new int[]{0x1F9D9,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FB,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FC,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FD,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FE,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DA},new int[]{0x1F9DA,0x1F3FB},new int[]{0x1F9DA,0x1F3FC},new int[]{0x1F9DA,0x1F3FD},new int[]{0x1F9DA,0x1F3FE},new int[]{0x1F9DA,0x1F3FF},new int[]{0x1F9DA,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FB,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FC,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FD,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FE,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FF,0x200D,0x2642},new int[]{0x1F9DA,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FB,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FC,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FD,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FE,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DB},new int[]{0x1F9DB,0x1F3FB},new int[]{0x1F9DB,0x1F3FC},new int[]{0x1F9DB,0x1F3FD},new int[]{0x1F9DB,0x1F3FE},new int[]{0x1F9DB,0x1F3FF},new int[]{0x1F9DB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FB,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FC,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FD,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FE,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FF,0x200D,0x2642},new int[]{0x1F9DB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FB,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FC,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FD,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FE,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DC},new int[]{0x1F9DC,0x1F3FB},new int[]{0x1F9DC,0x1F3FC},new int[]{0x1F9DC,0x1F3FD},new int[]{0x1F9DC,0x1F3FE},new int[]{0x1F9DC,0x1F3FF},new int[]{0x1F9DC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FB,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FC,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FD,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FE,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FF,0x200D,0x2642},new int[]{0x1F9DC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FB,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FC,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FD,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FE,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DD},new int[]{0x1F9DD,0x1F3FB},new int[]{0x1F9DD,0x1F3FC},new int[]{0x1F9DD,0x1F3FD},new int[]{0x1F9DD,0x1F3FE},new int[]{0x1F9DD,0x1F3FF},new int[]{0x1F9DD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FB,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FC,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FD,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FE,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FF,0x200D,0x2642},new int[]{0x1F9DD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FB,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FC,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FD,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FE,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DE},new int[]{0x1F9DE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DE,0x200D,0x2642},new int[]{0x1F9DE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DE,0x200D,0x2640},new int[]{0x1F9DF},new int[]{0x1F9DF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DF,0x200D,0x2642},new int[]{0x1F9DF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DF,0x200D,0x2640},new int[]{0x1F486},new int[]{0x1F486,0x1F3FB},new int[]{0x1F486,0x1F3FC},new int[]{0x1F486,0x1F3FD},new int[]{0x1F486,0x1F3FE},new int[]{0x1F486,0x1F3FF},new int[]{0x1F486,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x200D,0x2642},new int[]{0x1F486,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FB,0x200D,0x2642},new int[]{0x1F486,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FC,0x200D,0x2642},new int[]{0x1F486,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FD,0x200D,0x2642},new int[]{0x1F486,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FE,0x200D,0x2642},new int[]{0x1F486,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FF,0x200D,0x2642},new int[]{0x1F486,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x200D,0x2640},new int[]{0x1F486,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FB,0x200D,0x2640},new int[]{0x1F486,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FC,0x200D,0x2640},new int[]{0x1F486,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FD,0x200D,0x2640},new int[]{0x1F486,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FE,0x200D,0x2640},new int[]{0x1F486,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FF,0x200D,0x2640},new int[]{0x1F487},new int[]{0x1F487,0x1F3FB},new int[]{0x1F487,0x1F3FC},new int[]{0x1F487,0x1F3FD},new int[]{0x1F487,0x1F3FE},new int[]{0x1F487,0x1F3FF},new int[]{0x1F487,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x200D,0x2642},new int[]{0x1F487,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FB,0x200D,0x2642},new int[]{0x1F487,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FC,0x200D,0x2642},new int[]{0x1F487,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FD,0x200D,0x2642},new int[]{0x1F487,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FE,0x200D,0x2642},new int[]{0x1F487,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FF,0x200D,0x2642},new int[]{0x1F487,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x200D,0x2640},new int[]{0x1F487,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FB,0x200D,0x2640},new int[]{0x1F487,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FC,0x200D,0x2640},new int[]{0x1F487,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FD,0x200D,0x2640},new int[]{0x1F487,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FE,0x200D,0x2640},new int[]{0x1F487,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FF,0x200D,0x2640},new int[]{0x1F6B6},new int[]{0x1F6B6,0x1F3FB},new int[]{0x1F6B6,0x1F3FC},new int[]{0x1F6B6,0x1F3FD},new int[]{0x1F6B6,0x1F3FE},new int[]{0x1F6B6,0x1F3FF},new int[]{0x1F6B6,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FB,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FC,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FD,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FE,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FF,0x200D,0x2642},new int[]{0x1F6B6,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FB,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FC,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FD,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FE,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FF,0x200D,0x2640},new int[]{0x1F9CD},new int[]{0x1F9CD,0x1F3FB},new int[]{0x1F9CD,0x1F3FC},new int[]{0x1F9CD,0x1F3FD},new int[]{0x1F9CD,0x1F3FE},new int[]{0x1F9CD,0x1F3FF},new int[]{0x1F9CD,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FB,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FC,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FD,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FE,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FF,0x200D,0x2642},new int[]{0x1F9CD,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FB,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FC,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FD,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FE,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FF,0x200D,0x2640},new int[]{0x1F9CE},new int[]{0x1F9CE,0x1F3FB},new int[]{0x1F9CE,0x1F3FC},new int[]{0x1F9CE,0x1F3FD},new int[]{0x1F9CE,0x1F3FE},new int[]{0x1F9CE,0x1F3FF},new int[]{0x1F9CE,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FB,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FC,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FD,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FE,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FF,0x200D,0x2642},new int[]{0x1F9CE,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FB,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FC,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FD,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FE,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FF,0x200D,0x2640},new int[]{0x1F9D1,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9AF},new int[]{0x1F468,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9AF},new int[]{0x1F469,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9AF},new int[]{0x1F9D1,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9BC},new int[]{0x1F468,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9BC},new int[]{0x1F469,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9BC},new int[]{0x1F9D1,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9BD},new int[]{0x1F468,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9BD},new int[]{0x1F469,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9BD},new int[]{0x1F3C3},new int[]{0x1F3C3,0x1F3FB},new int[]{0x1F3C3,0x1F3FC},new int[]{0x1F3C3,0x1F3FD},new int[]{0x1F3C3,0x1F3FE},new int[]{0x1F3C3,0x1F3FF},new int[]{0x1F3C3,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FB,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FC,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FD,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FE,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FF,0x200D,0x2642},new int[]{0x1F3C3,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FB,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FC,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FD,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FE,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FF,0x200D,0x2640},new int[]{0x1F483},new int[]{0x1F483,0x1F3FB},new int[]{0x1F483,0x1F3FC},new int[]{0x1F483,0x1F3FD},new int[]{0x1F483,0x1F3FE},new int[]{0x1F483,0x1F3FF},new int[]{0x1F57A},new int[]{0x1F57A,0x1F3FB},new int[]{0x1F57A,0x1F3FC},new int[]{0x1F57A,0x1F3FD},new int[]{0x1F57A,0x1F3FE},new int[]{0x1F57A,0x1F3FF},new int[]{0x1F574,0xFE0F},new int[]{0x1F574},new int[]{0x1F574,0x1F3FB},new int[]{0x1F574,0x1F3FC},new int[]{0x1F574,0x1F3FD},new int[]{0x1F574,0x1F3FE},new int[]{0x1F574,0x1F3FF},new int[]{0x1F46F},new int[]{0x1F46F,0x200D,0x2642,0xFE0F},new int[]{0x1F46F,0x200D,0x2642},new int[]{0x1F46F,0x200D,0x2640,0xFE0F},new int[]{0x1F46F,0x200D,0x2640},new int[]{0x1F9D6},new int[]{0x1F9D6,0x1F3FB},new int[]{0x1F9D6,0x1F3FC},new int[]{0x1F9D6,0x1F3FD},new int[]{0x1F9D6,0x1F3FE},new int[]{0x1F9D6,0x1F3FF},new int[]{0x1F9D6,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FB,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FC,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FD,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FE,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FF,0x200D,0x2642},new int[]{0x1F9D6,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FB,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FC,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FD,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FE,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FF,0x200D,0x2640},new int[]{0x1F9D7},new int[]{0x1F9D7,0x1F3FB},new int[]{0x1F9D7,0x1F3FC},new int[]{0x1F9D7,0x1F3FD},new int[]{0x1F9D7,0x1F3FE},new int[]{0x1F9D7,0x1F3FF},new int[]{0x1F9D7,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FB,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FC,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FD,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FE,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FF,0x200D,0x2642},new int[]{0x1F9D7,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FB,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FC,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FD,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FE,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FF,0x200D,0x2640},new int[]{0x1F93A},new int[]{0x1F3C7},new int[]{0x1F3C7,0x1F3FB},new int[]{0x1F3C7,0x1F3FC},new int[]{0x1F3C7,0x1F3FD},new int[]{0x1F3C7,0x1F3FE},new int[]{0x1F3C7,0x1F3FF},new int[]{0x26F7,0xFE0F},new int[]{0x26F7},new int[]{0x1F3C2},new int[]{0x1F3C2,0x1F3FB},new int[]{0x1F3C2,0x1F3FC},new int[]{0x1F3C2,0x1F3FD},new int[]{0x1F3C2,0x1F3FE},new int[]{0x1F3C2,0x1F3FF},new int[]{0x1F3CC,0xFE0F},new int[]{0x1F3CC},new int[]{0x1F3CC,0x1F3FB},new int[]{0x1F3CC,0x1F3FC},new int[]{0x1F3CC,0x1F3FD},new int[]{0x1F3CC,0x1F3FE},new int[]{0x1F3CC,0x1F3FF},new int[]{0x1F3CC,0xFE0F,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0xFE0F,0x200D,0x2642},new int[]{0x1F3CC,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FB,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FC,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FD,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FE,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FF,0x200D,0x2642},new int[]{0x1F3CC,0xFE0F,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0xFE0F,0x200D,0x2640},new int[]{0x1F3CC,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FB,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FC,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FD,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FE,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FF,0x200D,0x2640},new int[]{0x1F3C4},new int[]{0x1F3C4,0x1F3FB},new int[]{0x1F3C4,0x1F3FC},new int[]{0x1F3C4,0x1F3FD},new int[]{0x1F3C4,0x1F3FE},new int[]{0x1F3C4,0x1F3FF},new int[]{0x1F3C4,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FB,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FC,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FD,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FE,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FF,0x200D,0x2642},new int[]{0x1F3C4,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FB,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FC,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FD,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FE,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FF,0x200D,0x2640},new int[]{0x1F6A3},new int[]{0x1F6A3,0x1F3FB},new int[]{0x1F6A3,0x1F3FC},new int[]{0x1F6A3,0x1F3FD},new int[]{0x1F6A3,0x1F3FE},new int[]{0x1F6A3,0x1F3FF},new int[]{0x1F6A3,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FB,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FC,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FD,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FE,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FF,0x200D,0x2642},new int[]{0x1F6A3,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FB,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FC,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FD,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FE,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FF,0x200D,0x2640},new int[]{0x1F3CA},new int[]{0x1F3CA,0x1F3FB},new int[]{0x1F3CA,0x1F3FC},new int[]{0x1F3CA,0x1F3FD},new int[]{0x1F3CA,0x1F3FE},new int[]{0x1F3CA,0x1F3FF},new int[]{0x1F3CA,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FB,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FC,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FD,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FE,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FF,0x200D,0x2642},new int[]{0x1F3CA,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FB,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FC,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FD,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FE,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FF,0x200D,0x2640},new int[]{0x26F9,0xFE0F},new int[]{0x26F9},new int[]{0x26F9,0x1F3FB},new int[]{0x26F9,0x1F3FC},new int[]{0x26F9,0x1F3FD},new int[]{0x26F9,0x1F3FE},new int[]{0x26F9,0x1F3FF},new int[]{0x26F9,0xFE0F,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0xFE0F,0x200D,0x2642},new int[]{0x26F9,0x200D,0x2642},new int[]{0x26F9,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FB,0x200D,0x2642},new int[]{0x26F9,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FC,0x200D,0x2642},new int[]{0x26F9,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FD,0x200D,0x2642},new int[]{0x26F9,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FE,0x200D,0x2642},new int[]{0x26F9,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FF,0x200D,0x2642},new int[]{0x26F9,0xFE0F,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0xFE0F,0x200D,0x2640},new int[]{0x26F9,0x200D,0x2640},new int[]{0x26F9,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FB,0x200D,0x2640},new int[]{0x26F9,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FC,0x200D,0x2640},new int[]{0x26F9,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FD,0x200D,0x2640},new int[]{0x26F9,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FE,0x200D,0x2640},new int[]{0x26F9,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FF,0x200D,0x2640},new int[]{0x1F3CB,0xFE0F},new int[]{0x1F3CB},new int[]{0x1F3CB,0x1F3FB},new int[]{0x1F3CB,0x1F3FC},new int[]{0x1F3CB,0x1F3FD},new int[]{0x1F3CB,0x1F3FE},new int[]{0x1F3CB,0x1F3FF},new int[]{0x1F3CB,0xFE0F,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0xFE0F,0x200D,0x2642},new int[]{0x1F3CB,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FB,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FC,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FD,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FE,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FF,0x200D,0x2642},new int[]{0x1F3CB,0xFE0F,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0xFE0F,0x200D,0x2640},new int[]{0x1F3CB,0x200D,0x2640},new int[]{0x1F3CB,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x1F3FB,0x200D,0x2640},new int[]{0x1F3CB,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x1F3FC,0x200D,0x2640},new int[]{0x1F3CB,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x1F3FD,0x200D,0x2640},new int[]{0x1F3CB,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x1F3FE,0x200D,0x2640},new int[]{0x1F3CB,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x1F3FF,0x200D,0x2640},new int[]{0x1F6B4},new int[]{0x1F6B4,0x1F3FB},new int[]{0x1F6B4,0x1F3FC},new int[]{0x1F6B4,0x1F3FD},new int[]{0x1F6B4,0x1F3FE},new int[]{0x1F6B4,0x1F3FF},new int[]{0x1F6B4,0x200D,0x2642,0xFE0F},new int[]{0x1F6B4,0x200D,0x2642},new int[]{0x1F6B4,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F6B4,0x1F3FB,0x200D,0x2642},new int[]{0x1F6B4,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F6B4,0x1F3FC,0x200D,0x2642},new int[]{0x1F6B4,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F6B4,0x1F3FD,0x200D,0x2642},new int[]{0x1F6B4,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F6B4,0x1F3FE,0x200D,0x2642},new int[]{0x1F6B4,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F6B4,0x1F3FF,0x200D,0x2642},new int[]{0x1F6B4,0x200D,0x2640,0xFE0F},new int[]{0x1F6B4,0x200D,0x2640},new int[]{0x1F6B4,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F6B4,0x1F3FB,0x200D,0x2640},new int[]{0x1F6B4,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F6B4,0x1F3FC,0x200D,0x2640},new int[]{0x1F6B4,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F6B4,0x1F3FD,0x200D,0x2640},new int[]{0x1F6B4,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F6B4,0x1F3FE,0x200D,0x2640},new int[]{0x1F6B4,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F6B4,0x1F3FF,0x200D,0x2640},new int[]{0x1F6B5},new int[]{0x1F6B5,0x1F3FB},new int[]{0x1F6B5,0x1F3FC},new int[]{0x1F6B5,0x1F3FD},new int[]{0x1F6B5,0x1F3FE},new int[]{0x1F6B5,0x1F3FF},new int[]{0x1F6B5,0x200D,0x2642,0xFE0F},new int[]{0x1F6B5,0x200D,0x2642},new int[]{0x1F6B5,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F6B5,0x1F3FB,0x200D,0x2642},new int[]{0x1F6B5,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F6B5,0x1F3FC,0x200D,0x2642},new int[]{0x1F6B5,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F6B5,0x1F3FD,0x200D,0x2642},new int[]{0x1F6B5,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F6B5,0x1F3FE,0x200D,0x2642},new int[]{0x1F6B5,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F6B5,0x1F3FF,0x200D,0x2642},new int[]{0x1F6B5,0x200D,0x2640,0xFE0F},new int[]{0x1F6B5,0x200D,0x2640},new int[]{0x1F6B5,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F6B5,0x1F3FB,0x200D,0x2640},new int[]{0x1F6B5,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F6B5,0x1F3FC,0x200D,0x2640},new int[]{0x1F6B5,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F6B5,0x1F3FD,0x200D,0x2640},new int[]{0x1F6B5,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F6B5,0x1F3FE,0x200D,0x2640},new int[]{0x1F6B5,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F6B5,0x1F3FF,0x200D,0x2640},new int[]{0x1F938},new int[]{0x1F938,0x1F3FB},new int[]{0x1F938,0x1F3FC},new int[]{0x1F938,0x1F3FD},new int[]{0x1F938,0x1F3FE},new int[]{0x1F938,0x1F3FF},new int[]{0x1F938,0x200D,0x2642,0xFE0F},new int[]{0x1F938,0x200D,0x2642},new int[]{0x1F938,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F938,0x1F3FB,0x200D,0x2642},new int[]{0x1F938,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F938,0x1F3FC,0x200D,0x2642},new int[]{0x1F938,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F938,0x1F3FD,0x200D,0x2642},new int[]{0x1F938,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F938,0x1F3FE,0x200D,0x2642},new int[]{0x1F938,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F938,0x1F3FF,0x200D,0x2642},new int[]{0x1F938,0x200D,0x2640,0xFE0F},new int[]{0x1F938,0x200D,0x2640},new int[]{0x1F938,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F938,0x1F3FB,0x200D,0x2640},new int[]{0x1F938,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F938,0x1F3FC,0x200D,0x2640},new int[]{0x1F938,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F938,0x1F3FD,0x200D,0x2640},new int[]{0x1F938,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F938,0x1F3FE,0x200D,0x2640},new int[]{0x1F938,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F938,0x1F3FF,0x200D,0x2640},new int[]{0x1F93C},new int[]{0x1F93C,0x200D,0x2642,0xFE0F},new int[]{0x1F93C,0x200D,0x2642},new int[]{0x1F93C,0x200D,0x2640,0xFE0F},new int[]{0x1F93C,0x200D,0x2640},new int[]{0x1F93D},new int[]{0x1F93D,0x1F3FB},new int[]{0x1F93D,0x1F3FC},new int[]{0x1F93D,0x1F3FD},new int[]{0x1F93D,0x1F3FE},new int[]{0x1F93D,0x1F3FF},new int[]{0x1F93D,0x200D,0x2642,0xFE0F},new int[]{0x1F93D,0x200D,0x2642},new int[]{0x1F93D,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F93D,0x1F3FB,0x200D,0x2642},new int[]{0x1F93D,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F93D,0x1F3FC,0x200D,0x2642},new int[]{0x1F93D,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F93D,0x1F3FD,0x200D,0x2642},new int[]{0x1F93D,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F93D,0x1F3FE,0x200D,0x2642},new int[]{0x1F93D,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F93D,0x1F3FF,0x200D,0x2642},new int[]{0x1F93D,0x200D,0x2640,0xFE0F},new int[]{0x1F93D,0x200D,0x2640},new int[]{0x1F93D,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F93D,0x1F3FB,0x200D,0x2640},new int[]{0x1F93D,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F93D,0x1F3FC,0x200D,0x2640},new int[]{0x1F93D,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F93D,0x1F3FD,0x200D,0x2640},new int[]{0x1F93D,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F93D,0x1F3FE,0x200D,0x2640},new int[]{0x1F93D,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F93D,0x1F3FF,0x200D,0x2640},new int[]{0x1F93E},new int[]{0x1F93E,0x1F3FB},new int[]{0x1F93E,0x1F3FC},new int[]{0x1F93E,0x1F3FD},new int[]{0x1F93E,0x1F3FE},new int[]{0x1F93E,0x1F3FF},new int[]{0x1F93E,0x200D,0x2642,0xFE0F},new int[]{0x1F93E,0x200D,0x2642},new int[]{0x1F93E,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F93E,0x1F3FB,0x200D,0x2642},new int[]{0x1F93E,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F93E,0x1F3FC,0x200D,0x2642},new int[]{0x1F93E,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F93E,0x1F3FD,0x200D,0x2642},new int[]{0x1F93E,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F93E,0x1F3FE,0x200D,0x2642},new int[]{0x1F93E,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F93E,0x1F3FF,0x200D,0x2642},new int[]{0x1F93E,0x200D,0x2640,0xFE0F},new int[]{0x1F93E,0x200D,0x2640},new int[]{0x1F93E,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F93E,0x1F3FB,0x200D,0x2640},new int[]{0x1F93E,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F93E,0x1F3FC,0x200D,0x2640},new int[]{0x1F93E,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F93E,0x1F3FD,0x200D,0x2640},new int[]{0x1F93E,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F93E,0x1F3FE,0x200D,0x2640},new int[]{0x1F93E,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F93E,0x1F3FF,0x200D,0x2640},new int[]{0x1F939},new int[]{0x1F939,0x1F3FB},new int[]{0x1F939,0x1F3FC},new int[]{0x1F939,0x1F3FD},new int[]{0x1F939,0x1F3FE},new int[]{0x1F939,0x1F3FF},new int[]{0x1F939,0x200D,0x2642,0xFE0F},new int[]{0x1F939,0x200D,0x2642},new int[]{0x1F939,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F939,0x1F3FB,0x200D,0x2642},new int[]{0x1F939,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F939,0x1F3FC,0x200D,0x2642},new int[]{0x1F939,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F939,0x1F3FD,0x200D,0x2642},new int[]{0x1F939,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F939,0x1F3FE,0x200D,0x2642},new int[]{0x1F939,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F939,0x1F3FF,0x200D,0x2642},new int[]{0x1F939,0x200D,0x2640,0xFE0F},new int[]{0x1F939,0x200D,0x2640},new int[]{0x1F939,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F939,0x1F3FB,0x200D,0x2640},new int[]{0x1F939,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F939,0x1F3FC,0x200D,0x2640},new int[]{0x1F939,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F939,0x1F3FD,0x200D,0x2640},new int[]{0x1F939,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F939,0x1F3FE,0x200D,0x2640},new int[]{0x1F939,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F939,0x1F3FF,0x200D,0x2640},new int[]{0x1F9D8},new int[]{0x1F9D8,0x1F3FB},new int[]{0x1F9D8,0x1F3FC},new int[]{0x1F9D8,0x1F3FD},new int[]{0x1F9D8,0x1F3FE},new int[]{0x1F9D8,0x1F3FF},new int[]{0x1F9D8,0x200D,0x2642,0xFE0F},new int[]{0x1F9D8,0x200D,0x2642},new int[]{0x1F9D8,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9D8,0x1F3FB,0x200D,0x2642},new int[]{0x1F9D8,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9D8,0x1F3FC,0x200D,0x2642},new int[]{0x1F9D8,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9D8,0x1F3FD,0x200D,0x2642},new int[]{0x1F9D8,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9D8,0x1F3FE,0x200D,0x2642},new int[]{0x1F9D8,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9D8,0x1F3FF,0x200D,0x2642},new int[]{0x1F9D8,0x200D,0x2640,0xFE0F},new int[]{0x1F9D8,0x200D,0x2640},new int[]{0x1F9D8,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9D8,0x1F3FB,0x200D,0x2640},new int[]{0x1F9D8,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9D8,0x1F3FC,0x200D,0x2640},new int[]{0x1F9D8,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9D8,0x1F3FD,0x200D,0x2640},new int[]{0x1F9D8,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9D8,0x1F3FE,0x200D,0x2640},new int[]{0x1F9D8,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9D8,0x1F3FF,0x200D,0x2640},new int[]{0x1F6C0},new int[]{0x1F6C0,0x1F3FB},new int[]{0x1F6C0,0x1F3FC},new int[]{0x1F6C0,0x1F3FD},new int[]{0x1F6C0,0x1F3FE},new int[]{0x1F6C0,0x1F3FF},new int[]{0x1F6CC},new int[]{0x1F6CC,0x1F3FB},new int[]{0x1F6CC,0x1F3FC},new int[]{0x1F6CC,0x1F3FD},new int[]{0x1F6CC,0x1F3FE},new int[]{0x1F6CC,0x1F3FF},new int[]{0x1F9D1,0x200D,0x1F91D,0x200D,0x1F9D1},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FB},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FC},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FD},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FE},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FF},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FB},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FC},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FD},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FE},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FF},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FB},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FC},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FD},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FE},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FF},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FB},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FC},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FD},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FE},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FF},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FB},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FC},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FD},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FE},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F9D1,0x1F3FF},new int[]{0x1F46D},new int[]{0x1F46D,0x1F3FB},new int[]{0x1F469,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FC},new int[]{0x1F469,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FD},new int[]{0x1F469,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FE},new int[]{0x1F469,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FF},new int[]{0x1F469,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FB},new int[]{0x1F46D,0x1F3FC},new int[]{0x1F469,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FD},new int[]{0x1F469,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FE},new int[]{0x1F469,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FF},new int[]{0x1F469,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FB},new int[]{0x1F469,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FC},new int[]{0x1F46D,0x1F3FD},new int[]{0x1F469,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FE},new int[]{0x1F469,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FF},new int[]{0x1F469,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FB},new int[]{0x1F469,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FC},new int[]{0x1F469,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FD},new int[]{0x1F46D,0x1F3FE},new int[]{0x1F469,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FF},new int[]{0x1F469,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FB},new int[]{0x1F469,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FC},new int[]{0x1F469,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FD},new int[]{0x1F469,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F469,0x1F3FE},new int[]{0x1F46D,0x1F3FF},new int[]{0x1F46B},new int[]{0x1F46B,0x1F3FB},new int[]{0x1F469,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FC},new int[]{0x1F469,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FD},new int[]{0x1F469,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FE},new int[]{0x1F469,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FF},new int[]{0x1F469,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FB},new int[]{0x1F46B,0x1F3FC},new int[]{0x1F469,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FD},new int[]{0x1F469,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FE},new int[]{0x1F469,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FF},new int[]{0x1F469,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FB},new int[]{0x1F469,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FC},new int[]{0x1F46B,0x1F3FD},new int[]{0x1F469,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FE},new int[]{0x1F469,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FF},new int[]{0x1F469,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FB},new int[]{0x1F469,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FC},new int[]{0x1F469,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FD},new int[]{0x1F46B,0x1F3FE},new int[]{0x1F469,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FF},new int[]{0x1F469,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FB},new int[]{0x1F469,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FC},new int[]{0x1F469,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FD},new int[]{0x1F469,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FE},new int[]{0x1F46B,0x1F3FF},new int[]{0x1F46C},new int[]{0x1F46C,0x1F3FB},new int[]{0x1F468,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FC},new int[]{0x1F468,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FD},new int[]{0x1F468,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FE},new int[]{0x1F468,0x1F3FB,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FF},new int[]{0x1F468,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FB},new int[]{0x1F46C,0x1F3FC},new int[]{0x1F468,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FD},new int[]{0x1F468,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FE},new int[]{0x1F468,0x1F3FC,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FF},new int[]{0x1F468,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FB},new int[]{0x1F468,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FC},new int[]{0x1F46C,0x1F3FD},new int[]{0x1F468,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FE},new int[]{0x1F468,0x1F3FD,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FF},new int[]{0x1F468,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FB},new int[]{0x1F468,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FC},new int[]{0x1F468,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FD},new int[]{0x1F46C,0x1F3FE},new int[]{0x1F468,0x1F3FE,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FF},new int[]{0x1F468,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FB},new int[]{0x1F468,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FC},new int[]{0x1F468,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FD},new int[]{0x1F468,0x1F3FF,0x200D,0x1F91D,0x200D,0x1F468,0x1F3FE},new int[]{0x1F46C,0x1F3FF},new int[]{0x1F48F},new int[]{0x1F469,0x200D,0x2764,0xFE0F,0x200D,0x1F48B,0x200D,0x1F468},new int[]{0x1F469,0x200D,0x2764,0x200D,0x1F48B,0x200D,0x1F468},new int[]{0x1F468,0x200D,0x2764,0xFE0F,0x200D,0x1F48B,0x200D,0x1F468},new int[]{0x1F468,0x200D,0x2764,0x200D,0x1F48B,0x200D,0x1F468},new int[]{0x1F469,0x200D,0x2764,0xFE0F,0x200D,0x1F48B,0x200D,0x1F469},new int[]{0x1F469,0x200D,0x2764,0x200D,0x1F48B,0x200D,0x1F469},new int[]{0x1F491},new int[]{0x1F469,0x200D,0x2764,0xFE0F,0x200D,0x1F468},new int[]{0x1F469,0x200D,0x2764,0x200D,0x1F468},new int[]{0x1F468,0x200D,0x2764,0xFE0F,0x200D,0x1F468},new int[]{0x1F468,0x200D,0x2764,0x200D,0x1F468},new int[]{0x1F469,0x200D,0x2764,0xFE0F,0x200D,0x1F469},new int[]{0x1F469,0x200D,0x2764,0x200D,0x1F469},new int[]{0x1F46A},new int[]{0x1F468,0x200D,0x1F469,0x200D,0x1F466},new int[]{0x1F468,0x200D,0x1F469,0x200D,0x1F467},new int[]{0x1F468,0x200D,0x1F469,0x200D,0x1F467,0x200D,0x1F466},new int[]{0x1F468,0x200D,0x1F469,0x200D,0x1F466,0x200D,0x1F466},new int[]{0x1F468,0x200D,0x1F469,0x200D,0x1F467,0x200D,0x1F467},new int[]{0x1F468,0x200D,0x1F468,0x200D,0x1F466},new int[]{0x1F468,0x200D,0x1F468,0x200D,0x1F467},new int[]{0x1F468,0x200D,0x1F468,0x200D,0x1F467,0x200D,0x1F466},new int[]{0x1F468,0x200D,0x1F468,0x200D,0x1F466,0x200D,0x1F466},new int[]{0x1F468,0x200D,0x1F468,0x200D,0x1F467,0x200D,0x1F467},new int[]{0x1F469,0x200D,0x1F469,0x200D,0x1F466},new int[]{0x1F469,0x200D,0x1F469,0x200D,0x1F467},new int[]{0x1F469,0x200D,0x1F469,0x200D,0x1F467,0x200D,0x1F466},new int[]{0x1F469,0x200D,0x1F469,0x200D,0x1F466,0x200D,0x1F466},new int[]{0x1F469,0x200D,0x1F469,0x200D,0x1F467,0x200D,0x1F467},new int[]{0x1F468,0x200D,0x1F466},new int[]{0x1F468,0x200D,0x1F466,0x200D,0x1F466},new int[]{0x1F468,0x200D,0x1F467},new int[]{0x1F468,0x200D,0x1F467,0x200D,0x1F466},new int[]{0x1F468,0x200D,0x1F467,0x200D,0x1F467},new int[]{0x1F469,0x200D,0x1F466},new int[]{0x1F469,0x200D,0x1F466,0x200D,0x1F466},new int[]{0x1F469,0x200D,0x1F467},new int[]{0x1F469,0x200D,0x1F467,0x200D,0x1F466},new int[]{0x1F469,0x200D,0x1F467,0x200D,0x1F467},new int[]{0x1F5E3,0xFE0F},new int[]{0x1F5E3},new int[]{0x1F464},new int[]{0x1F465},new int[]{0x1F463},new int[]{0x1F3FB},new int[]{0x1F3FC},new int[]{0x1F3FD},new int[]{0x1F3FE},new int[]{0x1F3FF},new int[]{0x1F9B0},new int[]{0x1F9B1},new int[]{0x1F9B3},new int[]{0x1F9B2},new int[]{0x1F435},new int[]{0x1F412},new int[]{0x1F98D},new int[]{0x1F9A7},new int[]{0x1F436},new int[]{0x1F415},new int[]{0x1F9AE},new int[]{0x1F415,0x200D,0x1F9BA},new int[]{0x1F429},new int[]{0x1F43A},new int[]{0x1F98A},new int[]{0x1F99D},new int[]{0x1F431},new int[]{0x1F408},new int[]{0x1F981},new int[]{0x1F42F},new int[]{0x1F405},new int[]{0x1F406},new int[]{0x1F434},new int[]{0x1F40E},new int[]{0x1F984},new int[]{0x1F993},new int[]{0x1F98C},new int[]{0x1F42E},new int[]{0x1F402},new int[]{0x1F403},new int[]{0x1F404},new int[]{0x1F437},new int[]{0x1F416},new int[]{0x1F417},new int[]{0x1F43D},new int[]{0x1F40F},new int[]{0x1F411},new int[]{0x1F410},new int[]{0x1F42A},new int[]{0x1F42B},new int[]{0x1F999},new int[]{0x1F992},new int[]{0x1F418},new int[]{0x1F98F},new int[]{0x1F99B},new int[]{0x1F42D},new int[]{0x1F401},new int[]{0x1F400},new int[]{0x1F439},new int[]{0x1F430},new int[]{0x1F407},new int[]{0x1F43F,0xFE0F},new int[]{0x1F43F},new int[]{0x1F994},new int[]{0x1F987},new int[]{0x1F43B},new int[]{0x1F428},new int[]{0x1F43C},new int[]{0x1F9A5},new int[]{0x1F9A6},new int[]{0x1F9A8},new int[]{0x1F998},new int[]{0x1F9A1},new int[]{0x1F43E},new int[]{0x1F983},new int[]{0x1F414},new int[]{0x1F413},new int[]{0x1F423},new int[]{0x1F424},new int[]{0x1F425},new int[]{0x1F426},new int[]{0x1F427},new int[]{0x1F54A,0xFE0F},new int[]{0x1F54A},new int[]{0x1F985},new int[]{0x1F986},new int[]{0x1F9A2},new int[]{0x1F989},new int[]{0x1F9A9},new int[]{0x1F99A},new int[]{0x1F99C},new int[]{0x1F438},new int[]{0x1F40A},new int[]{0x1F422},new int[]{0x1F98E},new int[]{0x1F40D},new int[]{0x1F432},new int[]{0x1F409},new int[]{0x1F995},new int[]{0x1F996},new int[]{0x1F433},new int[]{0x1F40B},new int[]{0x1F42C},new int[]{0x1F41F},new int[]{0x1F420},new int[]{0x1F421},new int[]{0x1F988},new int[]{0x1F419},new int[]{0x1F41A},new int[]{0x1F40C},new int[]{0x1F98B},new int[]{0x1F41B},new int[]{0x1F41C},new int[]{0x1F41D},new int[]{0x1F41E},new int[]{0x1F997},new int[]{0x1F577,0xFE0F},new int[]{0x1F577},new int[]{0x1F578,0xFE0F},new int[]{0x1F578},new int[]{0x1F982},new int[]{0x1F99F},new int[]{0x1F9A0},new int[]{0x1F490},new int[]{0x1F338},new int[]{0x1F4AE},new int[]{0x1F3F5,0xFE0F},new int[]{0x1F3F5},new int[]{0x1F339},new int[]{0x1F940},new int[]{0x1F33A},new int[]{0x1F33B},new int[]{0x1F33C},new int[]{0x1F337},new int[]{0x1F331},new int[]{0x1F332},new int[]{0x1F333},new int[]{0x1F334},new int[]{0x1F335},new int[]{0x1F33E},new int[]{0x1F33F},new int[]{0x2618,0xFE0F},new int[]{0x2618},new int[]{0x1F340},new int[]{0x1F341},new int[]{0x1F342},new int[]{0x1F343},new int[]{0x1F347},new int[]{0x1F348},new int[]{0x1F349},new int[]{0x1F34A},new int[]{0x1F34B},new int[]{0x1F34C},new int[]{0x1F34D},new int[]{0x1F96D},new int[]{0x1F34E},new int[]{0x1F34F},new int[]{0x1F350},new int[]{0x1F351},new int[]{0x1F352},new int[]{0x1F353},new int[]{0x1F95D},new int[]{0x1F345},new int[]{0x1F965},new int[]{0x1F951},new int[]{0x1F346},new int[]{0x1F954},new int[]{0x1F955},new int[]{0x1F33D},new int[]{0x1F336,0xFE0F},new int[]{0x1F336},new int[]{0x1F952},new int[]{0x1F96C},new int[]{0x1F966},new int[]{0x1F9C4},new int[]{0x1F9C5},new int[]{0x1F344},new int[]{0x1F95C},new int[]{0x1F330},new int[]{0x1F35E},new int[]{0x1F950},new int[]{0x1F956},new int[]{0x1F968},new int[]{0x1F96F},new int[]{0x1F95E},new int[]{0x1F9C7},new int[]{0x1F9C0},new int[]{0x1F356},new int[]{0x1F357},new int[]{0x1F969},new int[]{0x1F953},new int[]{0x1F354},new int[]{0x1F35F},new int[]{0x1F355},new int[]{0x1F32D},new int[]{0x1F96A},new int[]{0x1F32E},new int[]{0x1F32F},new int[]{0x1F959},new int[]{0x1F9C6},new int[]{0x1F95A},new int[]{0x1F373},new int[]{0x1F958},new int[]{0x1F372},new int[]{0x1F963},new int[]{0x1F957},new int[]{0x1F37F},new int[]{0x1F9C8},new int[]{0x1F9C2},new int[]{0x1F96B},new int[]{0x1F371},new int[]{0x1F358},new int[]{0x1F359},new int[]{0x1F35A},new int[]{0x1F35B},new int[]{0x1F35C},new int[]{0x1F35D},new int[]{0x1F360},new int[]{0x1F362},new int[]{0x1F363},new int[]{0x1F364},new int[]{0x1F365},new int[]{0x1F96E},new int[]{0x1F361},new int[]{0x1F95F},new int[]{0x1F960},new int[]{0x1F961},new int[]{0x1F980},new int[]{0x1F99E},new int[]{0x1F990},new int[]{0x1F991},new int[]{0x1F9AA},new int[]{0x1F366},new int[]{0x1F367},new int[]{0x1F368},new int[]{0x1F369},new int[]{0x1F36A},new int[]{0x1F382},new int[]{0x1F370},new int[]{0x1F9C1},new int[]{0x1F967},new int[]{0x1F36B},new int[]{0x1F36C},new int[]{0x1F36D},new int[]{0x1F36E},new int[]{0x1F36F},new int[]{0x1F37C},new int[]{0x1F95B},new int[]{0x2615},new int[]{0x1F375},new int[]{0x1F376},new int[]{0x1F37E},new int[]{0x1F377},new int[]{0x1F378},new int[]{0x1F379},new int[]{0x1F37A},new int[]{0x1F37B},new int[]{0x1F942},new int[]{0x1F943},new int[]{0x1F964},new int[]{0x1F9C3},new int[]{0x1F9C9},new int[]{0x1F9CA},new int[]{0x1F962},new int[]{0x1F37D,0xFE0F},new int[]{0x1F37D},new int[]{0x1F374},new int[]{0x1F944},new int[]{0x1F52A},new int[]{0x1F3FA},new int[]{0x1F30D},new int[]{0x1F30E},new int[]{0x1F30F},new int[]{0x1F310},new int[]{0x1F5FA,0xFE0F},new int[]{0x1F5FA},new int[]{0x1F5FE},new int[]{0x1F9ED},new int[]{0x1F3D4,0xFE0F},new int[]{0x1F3D4},new int[]{0x26F0,0xFE0F},new int[]{0x26F0},new int[]{0x1F30B},new int[]{0x1F5FB},new int[]{0x1F3D5,0xFE0F},new int[]{0x1F3D5},new int[]{0x1F3D6,0xFE0F},new int[]{0x1F3D6},new int[]{0x1F3DC,0xFE0F},new int[]{0x1F3DC},new int[]{0x1F3DD,0xFE0F},new int[]{0x1F3DD},new int[]{0x1F3DE,0xFE0F},new int[]{0x1F3DE},new int[]{0x1F3DF,0xFE0F},new int[]{0x1F3DF},new int[]{0x1F3DB,0xFE0F},new int[]{0x1F3DB},new int[]{0x1F3D7,0xFE0F},new int[]{0x1F3D7},new int[]{0x1F9F1},new int[]{0x1F3D8,0xFE0F},new int[]{0x1F3D8},new int[]{0x1F3DA,0xFE0F},new int[]{0x1F3DA},new int[]{0x1F3E0},new int[]{0x1F3E1},new int[]{0x1F3E2},new int[]{0x1F3E3},new int[]{0x1F3E4},new int[]{0x1F3E5},new int[]{0x1F3E6},new int[]{0x1F3E8},new int[]{0x1F3E9},new int[]{0x1F3EA},new int[]{0x1F3EB},new int[]{0x1F3EC},new int[]{0x1F3ED},new int[]{0x1F3EF},new int[]{0x1F3F0},new int[]{0x1F492},new int[]{0x1F5FC},new int[]{0x1F5FD},new int[]{0x26EA},new int[]{0x1F54C},new int[]{0x1F6D5},new int[]{0x1F54D},new int[]{0x26E9,0xFE0F},new int[]{0x26E9},new int[]{0x1F54B},new int[]{0x26F2},new int[]{0x26FA},new int[]{0x1F301},new int[]{0x1F303},new int[]{0x1F3D9,0xFE0F},new int[]{0x1F3D9},new int[]{0x1F304},new int[]{0x1F305},new int[]{0x1F306},new int[]{0x1F307},new int[]{0x1F309},new int[]{0x2668,0xFE0F},new int[]{0x2668},new int[]{0x1F3A0},new int[]{0x1F3A1},new int[]{0x1F3A2},new int[]{0x1F488},new int[]{0x1F3AA},new int[]{0x1F682},new int[]{0x1F683},new int[]{0x1F684},new int[]{0x1F685},new int[]{0x1F686},new int[]{0x1F687},new int[]{0x1F688},new int[]{0x1F689},new int[]{0x1F68A},new int[]{0x1F69D},new int[]{0x1F69E},new int[]{0x1F68B},new int[]{0x1F68C},new int[]{0x1F68D},new int[]{0x1F68E},new int[]{0x1F690},new int[]{0x1F691},new int[]{0x1F692},new int[]{0x1F693},new int[]{0x1F694},new int[]{0x1F695},new int[]{0x1F696},new int[]{0x1F697},new int[]{0x1F698},new int[]{0x1F699},new int[]{0x1F69A},new int[]{0x1F69B},new int[]{0x1F69C},new int[]{0x1F3CE,0xFE0F},new int[]{0x1F3CE},new int[]{0x1F3CD,0xFE0F},new int[]{0x1F3CD},new int[]{0x1F6F5},new int[]{0x1F9BD},new int[]{0x1F9BC},new int[]{0x1F6FA},new int[]{0x1F6B2},new int[]{0x1F6F4},new int[]{0x1F6F9},new int[]{0x1F68F},new int[]{0x1F6E3,0xFE0F},new int[]{0x1F6E3},new int[]{0x1F6E4,0xFE0F},new int[]{0x1F6E4},new int[]{0x1F6E2,0xFE0F},new int[]{0x1F6E2},new int[]{0x26FD},new int[]{0x1F6A8},new int[]{0x1F6A5},new int[]{0x1F6A6},new int[]{0x1F6D1},new int[]{0x1F6A7},new int[]{0x2693},new int[]{0x26F5},new int[]{0x1F6F6},new int[]{0x1F6A4},new int[]{0x1F6F3,0xFE0F},new int[]{0x1F6F3},new int[]{0x26F4,0xFE0F},new int[]{0x26F4},new int[]{0x1F6E5,0xFE0F},new int[]{0x1F6E5},new int[]{0x1F6A2},new int[]{0x2708,0xFE0F},new int[]{0x2708},new int[]{0x1F6E9,0xFE0F},new int[]{0x1F6E9},new int[]{0x1F6EB},new int[]{0x1F6EC},new int[]{0x1FA82},new int[]{0x1F4BA},new int[]{0x1F681},new int[]{0x1F69F},new int[]{0x1F6A0},new int[]{0x1F6A1},new int[]{0x1F6F0,0xFE0F},new int[]{0x1F6F0},new int[]{0x1F680},new int[]{0x1F6F8},new int[]{0x1F6CE,0xFE0F},new int[]{0x1F6CE},new int[]{0x1F9F3},new int[]{0x231B},new int[]{0x23F3},new int[]{0x231A},new int[]{0x23F0},new int[]{0x23F1,0xFE0F},new int[]{0x23F1},new int[]{0x23F2,0xFE0F},new int[]{0x23F2},new int[]{0x1F570,0xFE0F},new int[]{0x1F570},new int[]{0x1F55B},new int[]{0x1F567},new int[]{0x1F550},new int[]{0x1F55C},new int[]{0x1F551},new int[]{0x1F55D},new int[]{0x1F552},new int[]{0x1F55E},new int[]{0x1F553},new int[]{0x1F55F},new int[]{0x1F554},new int[]{0x1F560},new int[]{0x1F555},new int[]{0x1F561},new int[]{0x1F556},new int[]{0x1F562},new int[]{0x1F557},new int[]{0x1F563},new int[]{0x1F558},new int[]{0x1F564},new int[]{0x1F559},new int[]{0x1F565},new int[]{0x1F55A},new int[]{0x1F566},new int[]{0x1F311},new int[]{0x1F312},new int[]{0x1F313},new int[]{0x1F314},new int[]{0x1F315},new int[]{0x1F316},new int[]{0x1F317},new int[]{0x1F318},new int[]{0x1F319},new int[]{0x1F31A},new int[]{0x1F31B},new int[]{0x1F31C},new int[]{0x1F321,0xFE0F},new int[]{0x1F321},new int[]{0x2600,0xFE0F},new int[]{0x2600},new int[]{0x1F31D},new int[]{0x1F31E},new int[]{0x1FA90},new int[]{0x2B50},new int[]{0x1F31F},new int[]{0x1F320},new int[]{0x1F30C},new int[]{0x2601,0xFE0F},new int[]{0x2601},new int[]{0x26C5},new int[]{0x26C8,0xFE0F},new int[]{0x26C8},new int[]{0x1F324,0xFE0F},new int[]{0x1F324},new int[]{0x1F325,0xFE0F},new int[]{0x1F325},new int[]{0x1F326,0xFE0F},new int[]{0x1F326},new int[]{0x1F327,0xFE0F},new int[]{0x1F327},new int[]{0x1F328,0xFE0F},new int[]{0x1F328},new int[]{0x1F329,0xFE0F},new int[]{0x1F329},new int[]{0x1F32A,0xFE0F},new int[]{0x1F32A},new int[]{0x1F32B,0xFE0F},new int[]{0x1F32B},new int[]{0x1F32C,0xFE0F},new int[]{0x1F32C},new int[]{0x1F300},new int[]{0x1F308},new int[]{0x1F302},new int[]{0x2602,0xFE0F},new int[]{0x2602},new int[]{0x2614},new int[]{0x26F1,0xFE0F},new int[]{0x26F1},new int[]{0x26A1},new int[]{0x2744,0xFE0F},new int[]{0x2744},new int[]{0x2603,0xFE0F},new int[]{0x2603},new int[]{0x26C4},new int[]{0x2604,0xFE0F},new int[]{0x2604},new int[]{0x1F525},new int[]{0x1F4A7},new int[]{0x1F30A},new int[]{0x1F383},new int[]{0x1F384},new int[]{0x1F386},new int[]{0x1F387},new int[]{0x1F9E8},new int[]{0x2728},new int[]{0x1F388},new int[]{0x1F389},new int[]{0x1F38A},new int[]{0x1F38B},new int[]{0x1F38D},new int[]{0x1F38E},new int[]{0x1F38F},new int[]{0x1F390},new int[]{0x1F391},new int[]{0x1F9E7},new int[]{0x1F380},new int[]{0x1F381},new int[]{0x1F397,0xFE0F},new int[]{0x1F397},new int[]{0x1F39F,0xFE0F},new int[]{0x1F39F},new int[]{0x1F3AB},new int[]{0x1F396,0xFE0F},new int[]{0x1F396},new int[]{0x1F3C6},new int[]{0x1F3C5},new int[]{0x1F947},new int[]{0x1F948},new int[]{0x1F949},new int[]{0x26BD},new int[]{0x26BE},new int[]{0x1F94E},new int[]{0x1F3C0},new int[]{0x1F3D0},new int[]{0x1F3C8},new int[]{0x1F3C9},new int[]{0x1F3BE},new int[]{0x1F94F},new int[]{0x1F3B3},new int[]{0x1F3CF},new int[]{0x1F3D1},new int[]{0x1F3D2},new int[]{0x1F94D},new int[]{0x1F3D3},new int[]{0x1F3F8},new int[]{0x1F94A},new int[]{0x1F94B},new int[]{0x1F945},new int[]{0x26F3},new int[]{0x26F8,0xFE0F},new int[]{0x26F8},new int[]{0x1F3A3},new int[]{0x1F93F},new int[]{0x1F3BD},new int[]{0x1F3BF},new int[]{0x1F6F7},new int[]{0x1F94C},new int[]{0x1F3AF},new int[]{0x1FA80},new int[]{0x1FA81},new int[]{0x1F3B1},new int[]{0x1F52E},new int[]{0x1F9FF},new int[]{0x1F3AE},new int[]{0x1F579,0xFE0F},new int[]{0x1F579},new int[]{0x1F3B0},new int[]{0x1F3B2},new int[]{0x1F9E9},new int[]{0x1F9F8},new int[]{0x2660,0xFE0F},new int[]{0x2660},new int[]{0x2665,0xFE0F},new int[]{0x2665},new int[]{0x2666,0xFE0F},new int[]{0x2666},new int[]{0x2663,0xFE0F},new int[]{0x2663},new int[]{0x265F,0xFE0F},new int[]{0x265F},new int[]{0x1F0CF},new int[]{0x1F004},new int[]{0x1F3B4},new int[]{0x1F3AD},new int[]{0x1F5BC,0xFE0F},new int[]{0x1F5BC},new int[]{0x1F3A8},new int[]{0x1F9F5},new int[]{0x1F9F6},new int[]{0x1F453},new int[]{0x1F576,0xFE0F},new int[]{0x1F576},new int[]{0x1F97D},new int[]{0x1F97C},new int[]{0x1F9BA},new int[]{0x1F454},new int[]{0x1F455},new int[]{0x1F456},new int[]{0x1F9E3},new int[]{0x1F9E4},new int[]{0x1F9E5},new int[]{0x1F9E6},new int[]{0x1F457},new int[]{0x1F458},new int[]{0x1F97B},new int[]{0x1FA71},new int[]{0x1FA72},new int[]{0x1FA73},new int[]{0x1F459},new int[]{0x1F45A},new int[]{0x1F45B},new int[]{0x1F45C},new int[]{0x1F45D},new int[]{0x1F6CD,0xFE0F},new int[]{0x1F6CD},new int[]{0x1F392},new int[]{0x1F45E},new int[]{0x1F45F},new int[]{0x1F97E},new int[]{0x1F97F},new int[]{0x1F460},new int[]{0x1F461},new int[]{0x1FA70},new int[]{0x1F462},new int[]{0x1F451},new int[]{0x1F452},new int[]{0x1F3A9},new int[]{0x1F393},new int[]{0x1F9E2},new int[]{0x26D1,0xFE0F},new int[]{0x26D1},new int[]{0x1F4FF},new int[]{0x1F484},new int[]{0x1F48D},new int[]{0x1F48E},new int[]{0x1F507},new int[]{0x1F508},new int[]{0x1F509},new int[]{0x1F50A},new int[]{0x1F4E2},new int[]{0x1F4E3},new int[]{0x1F4EF},new int[]{0x1F514},new int[]{0x1F515},new int[]{0x1F3BC},new int[]{0x1F3B5},new int[]{0x1F3B6},new int[]{0x1F399,0xFE0F},new int[]{0x1F399},new int[]{0x1F39A,0xFE0F},new int[]{0x1F39A},new int[]{0x1F39B,0xFE0F},new int[]{0x1F39B},new int[]{0x1F3A4},new int[]{0x1F3A7},new int[]{0x1F4FB},new int[]{0x1F3B7},new int[]{0x1F3B8},new int[]{0x1F3B9},new int[]{0x1F3BA},new int[]{0x1F3BB},new int[]{0x1FA95},new int[]{0x1F941},new int[]{0x1F4F1},new int[]{0x1F4F2},new int[]{0x260E,0xFE0F},new int[]{0x260E},new int[]{0x1F4DE},new int[]{0x1F4DF},new int[]{0x1F4E0},new int[]{0x1F50B},new int[]{0x1F50C},new int[]{0x1F4BB},new int[]{0x1F5A5,0xFE0F},new int[]{0x1F5A5},new int[]{0x1F5A8,0xFE0F},new int[]{0x1F5A8},new int[]{0x2328,0xFE0F},new int[]{0x2328},new int[]{0x1F5B1,0xFE0F},new int[]{0x1F5B1},new int[]{0x1F5B2,0xFE0F},new int[]{0x1F5B2},new int[]{0x1F4BD},new int[]{0x1F4BE},new int[]{0x1F4BF},new int[]{0x1F4C0},new int[]{0x1F9EE},new int[]{0x1F3A5},new int[]{0x1F39E,0xFE0F},new int[]{0x1F39E},new int[]{0x1F4FD,0xFE0F},new int[]{0x1F4FD},new int[]{0x1F3AC},new int[]{0x1F4FA},new int[]{0x1F4F7},new int[]{0x1F4F8},new int[]{0x1F4F9},new int[]{0x1F4FC},new int[]{0x1F50D},new int[]{0x1F50E},new int[]{0x1F56F,0xFE0F},new int[]{0x1F56F},new int[]{0x1F4A1},new int[]{0x1F526},new int[]{0x1F3EE},new int[]{0x1FA94},new int[]{0x1F4D4},new int[]{0x1F4D5},new int[]{0x1F4D6},new int[]{0x1F4D7},new int[]{0x1F4D8},new int[]{0x1F4D9},new int[]{0x1F4DA},new int[]{0x1F4D3},new int[]{0x1F4D2},new int[]{0x1F4C3},new int[]{0x1F4DC},new int[]{0x1F4C4},new int[]{0x1F4F0},new int[]{0x1F5DE,0xFE0F},new int[]{0x1F5DE},new int[]{0x1F4D1},new int[]{0x1F516},new int[]{0x1F3F7,0xFE0F},new int[]{0x1F3F7},new int[]{0x1F4B0},new int[]{0x1F4B4},new int[]{0x1F4B5},new int[]{0x1F4B6},new int[]{0x1F4B7},new int[]{0x1F4B8},new int[]{0x1F4B3},new int[]{0x1F9FE},new int[]{0x1F4B9},new int[]{0x1F4B1},new int[]{0x1F4B2},new int[]{0x2709,0xFE0F},new int[]{0x2709},new int[]{0x1F4E7},new int[]{0x1F4E8},new int[]{0x1F4E9},new int[]{0x1F4E4},new int[]{0x1F4E5},new int[]{0x1F4E6},new int[]{0x1F4EB},new int[]{0x1F4EA},new int[]{0x1F4EC},new int[]{0x1F4ED},new int[]{0x1F4EE},new int[]{0x1F5F3,0xFE0F},new int[]{0x1F5F3},new int[]{0x270F,0xFE0F},new int[]{0x270F},new int[]{0x2712,0xFE0F},new int[]{0x2712},new int[]{0x1F58B,0xFE0F},new int[]{0x1F58B},new int[]{0x1F58A,0xFE0F},new int[]{0x1F58A},new int[]{0x1F58C,0xFE0F},new int[]{0x1F58C},new int[]{0x1F58D,0xFE0F},new int[]{0x1F58D},new int[]{0x1F4DD},new int[]{0x1F4BC},new int[]{0x1F4C1},new int[]{0x1F4C2},new int[]{0x1F5C2,0xFE0F},new int[]{0x1F5C2},new int[]{0x1F4C5},new int[]{0x1F4C6},new int[]{0x1F5D2,0xFE0F},new int[]{0x1F5D2},new int[]{0x1F5D3,0xFE0F},new int[]{0x1F5D3},new int[]{0x1F4C7},new int[]{0x1F4C8},new int[]{0x1F4C9},new int[]{0x1F4CA},new int[]{0x1F4CB},new int[]{0x1F4CC},new int[]{0x1F4CD},new int[]{0x1F4CE},new int[]{0x1F587,0xFE0F},new int[]{0x1F587},new int[]{0x1F4CF},new int[]{0x1F4D0},new int[]{0x2702,0xFE0F},new int[]{0x2702},new int[]{0x1F5C3,0xFE0F},new int[]{0x1F5C3},new int[]{0x1F5C4,0xFE0F},new int[]{0x1F5C4},new int[]{0x1F5D1,0xFE0F},new int[]{0x1F5D1},new int[]{0x1F512},new int[]{0x1F513},new int[]{0x1F50F},new int[]{0x1F510},new int[]{0x1F511},new int[]{0x1F5DD,0xFE0F},new int[]{0x1F5DD},new int[]{0x1F528},new int[]{0x1FA93},new int[]{0x26CF,0xFE0F},new int[]{0x26CF},new int[]{0x2692,0xFE0F},new int[]{0x2692},new int[]{0x1F6E0,0xFE0F},new int[]{0x1F6E0},new int[]{0x1F5E1,0xFE0F},new int[]{0x1F5E1},new int[]{0x2694,0xFE0F},new int[]{0x2694},new int[]{0x1F52B},new int[]{0x1F3F9},new int[]{0x1F6E1,0xFE0F},new int[]{0x1F6E1},new int[]{0x1F527},new int[]{0x1F529},new int[]{0x2699,0xFE0F},new int[]{0x2699},new int[]{0x1F5DC,0xFE0F},new int[]{0x1F5DC},new int[]{0x2696,0xFE0F},new int[]{0x2696},new int[]{0x1F9AF},new int[]{0x1F517},new int[]{0x26D3,0xFE0F},new int[]{0x26D3},new int[]{0x1F9F0},new int[]{0x1F9F2},new int[]{0x2697,0xFE0F},new int[]{0x2697},new int[]{0x1F9EA},new int[]{0x1F9EB},new int[]{0x1F9EC},new int[]{0x1F52C},new int[]{0x1F52D},new int[]{0x1F4E1},new int[]{0x1F489},new int[]{0x1FA78},new int[]{0x1F48A},new int[]{0x1FA79},new int[]{0x1FA7A},new int[]{0x1F6AA},new int[]{0x1F6CF,0xFE0F},new int[]{0x1F6CF},new int[]{0x1F6CB,0xFE0F},new int[]{0x1F6CB},new int[]{0x1FA91},new int[]{0x1F6BD},new int[]{0x1F6BF},new int[]{0x1F6C1},new int[]{0x1FA92},new int[]{0x1F9F4},new int[]{0x1F9F7},new int[]{0x1F9F9},new int[]{0x1F9FA},new int[]{0x1F9FB},new int[]{0x1F9FC},new int[]{0x1F9FD},new int[]{0x1F9EF},new int[]{0x1F6D2},new int[]{0x1F6AC},new int[]{0x26B0,0xFE0F},new int[]{0x26B0},new int[]{0x26B1,0xFE0F},new int[]{0x26B1},new int[]{0x1F5FF},new int[]{0x1F3E7},new int[]{0x1F6AE},new int[]{0x1F6B0},new int[]{0x267F},new int[]{0x1F6B9},new int[]{0x1F6BA},new int[]{0x1F6BB},new int[]{0x1F6BC},new int[]{0x1F6BE},new int[]{0x1F6C2},new int[]{0x1F6C3},new int[]{0x1F6C4},new int[]{0x1F6C5},new int[]{0x26A0,0xFE0F},new int[]{0x26A0},new int[]{0x1F6B8},new int[]{0x26D4},new int[]{0x1F6AB},new int[]{0x1F6B3},new int[]{0x1F6AD},new int[]{0x1F6AF},new int[]{0x1F6B1},new int[]{0x1F6B7},new int[]{0x1F4F5},new int[]{0x1F51E},new int[]{0x2622,0xFE0F},new int[]{0x2622},new int[]{0x2623,0xFE0F},new int[]{0x2623},new int[]{0x2B06,0xFE0F},new int[]{0x2B06},new int[]{0x2197,0xFE0F},new int[]{0x2197},new int[]{0x27A1,0xFE0F},new int[]{0x27A1},new int[]{0x2198,0xFE0F},new int[]{0x2198},new int[]{0x2B07,0xFE0F},new int[]{0x2B07},new int[]{0x2199,0xFE0F},new int[]{0x2199},new int[]{0x2B05,0xFE0F},new int[]{0x2B05},new int[]{0x2196,0xFE0F},new int[]{0x2196},new int[]{0x2195,0xFE0F},new int[]{0x2195},new int[]{0x2194,0xFE0F},new int[]{0x2194},new int[]{0x21A9,0xFE0F},new int[]{0x21A9},new int[]{0x21AA,0xFE0F},new int[]{0x21AA},new int[]{0x2934,0xFE0F},new int[]{0x2934},new int[]{0x2935,0xFE0F},new int[]{0x2935},new int[]{0x1F503},new int[]{0x1F504},new int[]{0x1F519},new int[]{0x1F51A},new int[]{0x1F51B},new int[]{0x1F51C},new int[]{0x1F51D},new int[]{0x1F6D0},new int[]{0x269B,0xFE0F},new int[]{0x269B},new int[]{0x1F549,0xFE0F},new int[]{0x1F549},new int[]{0x2721,0xFE0F},new int[]{0x2721},new int[]{0x2638,0xFE0F},new int[]{0x2638},new int[]{0x262F,0xFE0F},new int[]{0x262F},new int[]{0x271D,0xFE0F},new int[]{0x271D},new int[]{0x2626,0xFE0F},new int[]{0x2626},new int[]{0x262A,0xFE0F},new int[]{0x262A},new int[]{0x262E,0xFE0F},new int[]{0x262E},new int[]{0x1F54E},new int[]{0x1F52F},new int[]{0x2648},new int[]{0x2649},new int[]{0x264A},new int[]{0x264B},new int[]{0x264C},new int[]{0x264D},new int[]{0x264E},new int[]{0x264F},new int[]{0x2650},new int[]{0x2651},new int[]{0x2652},new int[]{0x2653},new int[]{0x26CE},new int[]{0x1F500},new int[]{0x1F501},new int[]{0x1F502},new int[]{0x25B6,0xFE0F},new int[]{0x25B6},new int[]{0x23E9},new int[]{0x23ED,0xFE0F},new int[]{0x23ED},new int[]{0x23EF,0xFE0F},new int[]{0x23EF},new int[]{0x25C0,0xFE0F},new int[]{0x25C0},new int[]{0x23EA},new int[]{0x23EE,0xFE0F},new int[]{0x23EE},new int[]{0x1F53C},new int[]{0x23EB},new int[]{0x1F53D},new int[]{0x23EC},new int[]{0x23F8,0xFE0F},new int[]{0x23F8},new int[]{0x23F9,0xFE0F},new int[]{0x23F9},new int[]{0x23FA,0xFE0F},new int[]{0x23FA},new int[]{0x23CF,0xFE0F},new int[]{0x23CF},new int[]{0x1F3A6},new int[]{0x1F505},new int[]{0x1F506},new int[]{0x1F4F6},new int[]{0x1F4F3},new int[]{0x1F4F4},new int[]{0x2640,0xFE0F},new int[]{0x2640},new int[]{0x2642,0xFE0F},new int[]{0x2642},new int[]{0x2695,0xFE0F},new int[]{0x2695},new int[]{0x267E,0xFE0F},new int[]{0x267E},new int[]{0x267B,0xFE0F},new int[]{0x267B},new int[]{0x269C,0xFE0F},new int[]{0x269C},new int[]{0x1F531},new int[]{0x1F4DB},new int[]{0x1F530},new int[]{0x2B55},new int[]{0x2705},new int[]{0x2611,0xFE0F},new int[]{0x2611},new int[]{0x2714,0xFE0F},new int[]{0x2714},new int[]{0x2716,0xFE0F},new int[]{0x2716},new int[]{0x274C},new int[]{0x274E},new int[]{0x2795},new int[]{0x2796},new int[]{0x2797},new int[]{0x27B0},new int[]{0x27BF},new int[]{0x303D,0xFE0F},new int[]{0x303D},new int[]{0x2733,0xFE0F},new int[]{0x2733},new int[]{0x2734,0xFE0F},new int[]{0x2734},new int[]{0x2747,0xFE0F},new int[]{0x2747},new int[]{0x203C,0xFE0F},new int[]{0x203C},new int[]{0x2049,0xFE0F},new int[]{0x2049},new int[]{0x2753},new int[]{0x2754},new int[]{0x2755},new int[]{0x2757},new int[]{0x3030,0xFE0F},new int[]{0x3030},new int[]{0x00A9,0xFE0F},new int[]{0x00A9},new int[]{0x00AE,0xFE0F},new int[]{0x00AE},new int[]{0x2122,0xFE0F},new int[]{0x2122},new int[]{0x0023,0xFE0F,0x20E3},new int[]{0x0023,0x20E3},new int[]{0x002A,0xFE0F,0x20E3},new int[]{0x002A,0x20E3},new int[]{0x0030,0xFE0F,0x20E3},new int[]{0x0030,0x20E3},new int[]{0x0031,0xFE0F,0x20E3},new int[]{0x0031,0x20E3},new int[]{0x0032,0xFE0F,0x20E3},new int[]{0x0032,0x20E3},new int[]{0x0033,0xFE0F,0x20E3},new int[]{0x0033,0x20E3},new int[]{0x0034,0xFE0F,0x20E3},new int[]{0x0034,0x20E3},new int[]{0x0035,0xFE0F,0x20E3},new int[]{0x0035,0x20E3},new int[]{0x0036,0xFE0F,0x20E3},new int[]{0x0036,0x20E3},new int[]{0x0037,0xFE0F,0x20E3},new int[]{0x0037,0x20E3},new int[]{0x0038,0xFE0F,0x20E3},new int[]{0x0038,0x20E3},new int[]{0x0039,0xFE0F,0x20E3},new int[]{0x0039,0x20E3},new int[]{0x1F51F},new int[]{0x1F520},new int[]{0x1F521},new int[]{0x1F522},new int[]{0x1F523},new int[]{0x1F524},new int[]{0x1F170,0xFE0F},new int[]{0x1F170},new int[]{0x1F18E},new int[]{0x1F171,0xFE0F},new int[]{0x1F171},new int[]{0x1F191},new int[]{0x1F192},new int[]{0x1F193},new int[]{0x2139,0xFE0F},new int[]{0x2139},new int[]{0x1F194},new int[]{0x24C2,0xFE0F},new int[]{0x24C2},new int[]{0x1F195},new int[]{0x1F196},new int[]{0x1F17E,0xFE0F},new int[]{0x1F17E},new int[]{0x1F197},new int[]{0x1F17F,0xFE0F},new int[]{0x1F17F},new int[]{0x1F198},new int[]{0x1F199},new int[]{0x1F19A},new int[]{0x1F201},new int[]{0x1F202,0xFE0F},new int[]{0x1F202},new int[]{0x1F237,0xFE0F},new int[]{0x1F237},new int[]{0x1F236},new int[]{0x1F22F},new int[]{0x1F250},new int[]{0x1F239},new int[]{0x1F21A},new int[]{0x1F232},new int[]{0x1F251},new int[]{0x1F238},new int[]{0x1F234},new int[]{0x1F233},new int[]{0x3297,0xFE0F},new int[]{0x3297},new int[]{0x3299,0xFE0F},new int[]{0x3299},new int[]{0x1F23A},new int[]{0x1F235},new int[]{0x1F534},new int[]{0x1F7E0},new int[]{0x1F7E1},new int[]{0x1F7E2},new int[]{0x1F535},new int[]{0x1F7E3},new int[]{0x1F7E4},new int[]{0x26AB},new int[]{0x26AA},new int[]{0x1F7E5},new int[]{0x1F7E7},new int[]{0x1F7E8},new int[]{0x1F7E9},new int[]{0x1F7E6},new int[]{0x1F7EA},new int[]{0x1F7EB},new int[]{0x2B1B},new int[]{0x2B1C},new int[]{0x25FC,0xFE0F},new int[]{0x25FC},new int[]{0x25FB,0xFE0F},new int[]{0x25FB},new int[]{0x25FE},new int[]{0x25FD},new int[]{0x25AA,0xFE0F},new int[]{0x25AA},new int[]{0x25AB,0xFE0F},new int[]{0x25AB},new int[]{0x1F536},new int[]{0x1F537},new int[]{0x1F538},new int[]{0x1F539},new int[]{0x1F53A},new int[]{0x1F53B},new int[]{0x1F4A0},new int[]{0x1F518},new int[]{0x1F533},new int[]{0x1F532},new int[]{0x1F3C1},new int[]{0x1F6A9},new int[]{0x1F38C},new int[]{0x1F3F4},new int[]{0x1F3F3,0xFE0F},new int[]{0x1F3F3},new int[]{0x1F3F3,0xFE0F,0x200D,0x1F308},new int[]{0x1F3F3,0x200D,0x1F308},new int[]{0x1F3F4,0x200D,0x2620,0xFE0F},new int[]{0x1F3F4,0x200D,0x2620},new int[]{0x1F1E6,0x1F1E8},new int[]{0x1F1E6,0x1F1E9},new int[]{0x1F1E6,0x1F1EA},new int[]{0x1F1E6,0x1F1EB},new int[]{0x1F1E6,0x1F1EC},new int[]{0x1F1E6,0x1F1EE},new int[]{0x1F1E6,0x1F1F1},new int[]{0x1F1E6,0x1F1F2},new int[]{0x1F1E6,0x1F1F4},new int[]{0x1F1E6,0x1F1F6},new int[]{0x1F1E6,0x1F1F7},new int[]{0x1F1E6,0x1F1F8},new int[]{0x1F1E6,0x1F1F9},new int[]{0x1F1E6,0x1F1FA},new int[]{0x1F1E6,0x1F1FC},new int[]{0x1F1E6,0x1F1FD},new int[]{0x1F1E6,0x1F1FF},new int[]{0x1F1E7,0x1F1E6},new int[]{0x1F1E7,0x1F1E7},new int[]{0x1F1E7,0x1F1E9},new int[]{0x1F1E7,0x1F1EA},new int[]{0x1F1E7,0x1F1EB},new int[]{0x1F1E7,0x1F1EC},new int[]{0x1F1E7,0x1F1ED},new int[]{0x1F1E7,0x1F1EE},new int[]{0x1F1E7,0x1F1EF},new int[]{0x1F1E7,0x1F1F1},new int[]{0x1F1E7,0x1F1F2},new int[]{0x1F1E7,0x1F1F3},new int[]{0x1F1E7,0x1F1F4},new int[]{0x1F1E7,0x1F1F6},new int[]{0x1F1E7,0x1F1F7},new int[]{0x1F1E7,0x1F1F8},new int[]{0x1F1E7,0x1F1F9},new int[]{0x1F1E7,0x1F1FB},new int[]{0x1F1E7,0x1F1FC},new int[]{0x1F1E7,0x1F1FE},new int[]{0x1F1E7,0x1F1FF},new int[]{0x1F1E8,0x1F1E6},new int[]{0x1F1E8,0x1F1E8},new int[]{0x1F1E8,0x1F1E9},new int[]{0x1F1E8,0x1F1EB},new int[]{0x1F1E8,0x1F1EC},new int[]{0x1F1E8,0x1F1ED},new int[]{0x1F1E8,0x1F1EE},new int[]{0x1F1E8,0x1F1F0},new int[]{0x1F1E8,0x1F1F1},new int[]{0x1F1E8,0x1F1F2},new int[]{0x1F1E8,0x1F1F3},new int[]{0x1F1E8,0x1F1F4},new int[]{0x1F1E8,0x1F1F5},new int[]{0x1F1E8,0x1F1F7},new int[]{0x1F1E8,0x1F1FA},new int[]{0x1F1E8,0x1F1FB},new int[]{0x1F1E8,0x1F1FC},new int[]{0x1F1E8,0x1F1FD},new int[]{0x1F1E8,0x1F1FE},new int[]{0x1F1E8,0x1F1FF},new int[]{0x1F1E9,0x1F1EA},new int[]{0x1F1E9,0x1F1EC},new int[]{0x1F1E9,0x1F1EF},new int[]{0x1F1E9,0x1F1F0},new int[]{0x1F1E9,0x1F1F2},new int[]{0x1F1E9,0x1F1F4},new int[]{0x1F1E9,0x1F1FF},new int[]{0x1F1EA,0x1F1E6},new int[]{0x1F1EA,0x1F1E8},new int[]{0x1F1EA,0x1F1EA},new int[]{0x1F1EA,0x1F1EC},new int[]{0x1F1EA,0x1F1ED},new int[]{0x1F1EA,0x1F1F7},new int[]{0x1F1EA,0x1F1F8},new int[]{0x1F1EA,0x1F1F9},new int[]{0x1F1EA,0x1F1FA},new int[]{0x1F1EB,0x1F1EE},new int[]{0x1F1EB,0x1F1EF},new int[]{0x1F1EB,0x1F1F0},new int[]{0x1F1EB,0x1F1F2},new int[]{0x1F1EB,0x1F1F4},new int[]{0x1F1EB,0x1F1F7},new int[]{0x1F1EC,0x1F1E6},new int[]{0x1F1EC,0x1F1E7},new int[]{0x1F1EC,0x1F1E9},new int[]{0x1F1EC,0x1F1EA},new int[]{0x1F1EC,0x1F1EB},new int[]{0x1F1EC,0x1F1EC},new int[]{0x1F1EC,0x1F1ED},new int[]{0x1F1EC,0x1F1EE},new int[]{0x1F1EC,0x1F1F1},new int[]{0x1F1EC,0x1F1F2},new int[]{0x1F1EC,0x1F1F3},new int[]{0x1F1EC,0x1F1F5},new int[]{0x1F1EC,0x1F1F6},new int[]{0x1F1EC,0x1F1F7},new int[]{0x1F1EC,0x1F1F8},new int[]{0x1F1EC,0x1F1F9},new int[]{0x1F1EC,0x1F1FA},new int[]{0x1F1EC,0x1F1FC},new int[]{0x1F1EC,0x1F1FE},new int[]{0x1F1ED,0x1F1F0},new int[]{0x1F1ED,0x1F1F2},new int[]{0x1F1ED,0x1F1F3},new int[]{0x1F1ED,0x1F1F7},new int[]{0x1F1ED,0x1F1F9},new int[]{0x1F1ED,0x1F1FA},new int[]{0x1F1EE,0x1F1E8},new int[]{0x1F1EE,0x1F1E9},new int[]{0x1F1EE,0x1F1EA},new int[]{0x1F1EE,0x1F1F1},new int[]{0x1F1EE,0x1F1F2},new int[]{0x1F1EE,0x1F1F3},new int[]{0x1F1EE,0x1F1F4},new int[]{0x1F1EE,0x1F1F6},new int[]{0x1F1EE,0x1F1F7},new int[]{0x1F1EE,0x1F1F8},new int[]{0x1F1EE,0x1F1F9},new int[]{0x1F1EF,0x1F1EA},new int[]{0x1F1EF,0x1F1F2},new int[]{0x1F1EF,0x1F1F4},new int[]{0x1F1EF,0x1F1F5},new int[]{0x1F1F0,0x1F1EA},new int[]{0x1F1F0,0x1F1EC},new int[]{0x1F1F0,0x1F1ED},new int[]{0x1F1F0,0x1F1EE},new int[]{0x1F1F0,0x1F1F2},new int[]{0x1F1F0,0x1F1F3},new int[]{0x1F1F0,0x1F1F5},new int[]{0x1F1F0,0x1F1F7},new int[]{0x1F1F0,0x1F1FC},new int[]{0x1F1F0,0x1F1FE},new int[]{0x1F1F0,0x1F1FF},new int[]{0x1F1F1,0x1F1E6},new int[]{0x1F1F1,0x1F1E7},new int[]{0x1F1F1,0x1F1E8},new int[]{0x1F1F1,0x1F1EE},new int[]{0x1F1F1,0x1F1F0},new int[]{0x1F1F1,0x1F1F7},new int[]{0x1F1F1,0x1F1F8},new int[]{0x1F1F1,0x1F1F9},new int[]{0x1F1F1,0x1F1FA},new int[]{0x1F1F1,0x1F1FB},new int[]{0x1F1F1,0x1F1FE},new int[]{0x1F1F2,0x1F1E6},new int[]{0x1F1F2,0x1F1E8},new int[]{0x1F1F2,0x1F1E9},new int[]{0x1F1F2,0x1F1EA},new int[]{0x1F1F2,0x1F1EB},new int[]{0x1F1F2,0x1F1EC},new int[]{0x1F1F2,0x1F1ED},new int[]{0x1F1F2,0x1F1F0},new int[]{0x1F1F2,0x1F1F1},new int[]{0x1F1F2,0x1F1F2},new int[]{0x1F1F2,0x1F1F3},new int[]{0x1F1F2,0x1F1F4},new int[]{0x1F1F2,0x1F1F5},new int[]{0x1F1F2,0x1F1F6},new int[]{0x1F1F2,0x1F1F7},new int[]{0x1F1F2,0x1F1F8},new int[]{0x1F1F2,0x1F1F9},new int[]{0x1F1F2,0x1F1FA},new int[]{0x1F1F2,0x1F1FB},new int[]{0x1F1F2,0x1F1FC},new int[]{0x1F1F2,0x1F1FD},new int[]{0x1F1F2,0x1F1FE},new int[]{0x1F1F2,0x1F1FF},new int[]{0x1F1F3,0x1F1E6},new int[]{0x1F1F3,0x1F1E8},new int[]{0x1F1F3,0x1F1EA},new int[]{0x1F1F3,0x1F1EB},new int[]{0x1F1F3,0x1F1EC},new int[]{0x1F1F3,0x1F1EE},new int[]{0x1F1F3,0x1F1F1},new int[]{0x1F1F3,0x1F1F4},new int[]{0x1F1F3,0x1F1F5},new int[]{0x1F1F3,0x1F1F7},new int[]{0x1F1F3,0x1F1FA},new int[]{0x1F1F3,0x1F1FF},new int[]{0x1F1F4,0x1F1F2},new int[]{0x1F1F5,0x1F1E6},new int[]{0x1F1F5,0x1F1EA},new int[]{0x1F1F5,0x1F1EB},new int[]{0x1F1F5,0x1F1EC},new int[]{0x1F1F5,0x1F1ED},new int[]{0x1F1F5,0x1F1F0},new int[]{0x1F1F5,0x1F1F1},new int[]{0x1F1F5,0x1F1F2},new int[]{0x1F1F5,0x1F1F3},new int[]{0x1F1F5,0x1F1F7},new int[]{0x1F1F5,0x1F1F8},new int[]{0x1F1F5,0x1F1F9},new int[]{0x1F1F5,0x1F1FC},new int[]{0x1F1F5,0x1F1FE},new int[]{0x1F1F6,0x1F1E6},new int[]{0x1F1F7,0x1F1EA},new int[]{0x1F1F7,0x1F1F4},new int[]{0x1F1F7,0x1F1F8},new int[]{0x1F1F7,0x1F1FA},new int[]{0x1F1F7,0x1F1FC},new int[]{0x1F1F8,0x1F1E6},new int[]{0x1F1F8,0x1F1E7},new int[]{0x1F1F8,0x1F1E8},new int[]{0x1F1F8,0x1F1E9},new int[]{0x1F1F8,0x1F1EA},new int[]{0x1F1F8,0x1F1EC},new int[]{0x1F1F8,0x1F1ED},new int[]{0x1F1F8,0x1F1EE},new int[]{0x1F1F8,0x1F1EF},new int[]{0x1F1F8,0x1F1F0},new int[]{0x1F1F8,0x1F1F1},new int[]{0x1F1F8,0x1F1F2},new int[]{0x1F1F8,0x1F1F3},new int[]{0x1F1F8,0x1F1F4},new int[]{0x1F1F8,0x1F1F7},new int[]{0x1F1F8,0x1F1F8},new int[]{0x1F1F8,0x1F1F9},new int[]{0x1F1F8,0x1F1FB},new int[]{0x1F1F8,0x1F1FD},new int[]{0x1F1F8,0x1F1FE},new int[]{0x1F1F8,0x1F1FF},new int[]{0x1F1F9,0x1F1E6},new int[]{0x1F1F9,0x1F1E8},new int[]{0x1F1F9,0x1F1E9},new int[]{0x1F1F9,0x1F1EB},new int[]{0x1F1F9,0x1F1EC},new int[]{0x1F1F9,0x1F1ED},new int[]{0x1F1F9,0x1F1EF},new int[]{0x1F1F9,0x1F1F0},new int[]{0x1F1F9,0x1F1F1},new int[]{0x1F1F9,0x1F1F2},new int[]{0x1F1F9,0x1F1F3},new int[]{0x1F1F9,0x1F1F4},new int[]{0x1F1F9,0x1F1F7},new int[]{0x1F1F9,0x1F1F9},new int[]{0x1F1F9,0x1F1FB},new int[]{0x1F1F9,0x1F1FC},new int[]{0x1F1F9,0x1F1FF},new int[]{0x1F1FA,0x1F1E6},new int[]{0x1F1FA,0x1F1EC},new int[]{0x1F1FA,0x1F1F2},new int[]{0x1F1FA,0x1F1F3},new int[]{0x1F1FA,0x1F1F8},new int[]{0x1F1FA,0x1F1FE},new int[]{0x1F1FA,0x1F1FF},new int[]{0x1F1FB,0x1F1E6},new int[]{0x1F1FB,0x1F1E8},new int[]{0x1F1FB,0x1F1EA},new int[]{0x1F1FB,0x1F1EC},new int[]{0x1F1FB,0x1F1EE},new int[]{0x1F1FB,0x1F1F3},new int[]{0x1F1FB,0x1F1FA},new int[]{0x1F1FC,0x1F1EB},new int[]{0x1F1FC,0x1F1F8},new int[]{0x1F1FD,0x1F1F0},new int[]{0x1F1FE,0x1F1EA},new int[]{0x1F1FE,0x1F1F9},new int[]{0x1F1FF,0x1F1E6},new int[]{0x1F1FF,0x1F1F2},new int[]{0x1F1FF,0x1F1FC},new int[]{0x1F3F4,0xE0067,0xE0062,0xE0065,0xE006E,0xE0067,0xE007F},new int[]{0x1F3F4,0xE0067,0xE0062,0xE0073,0xE0063,0xE0074,0xE007F},new int[]{0x1F3F4,0xE0067,0xE0062,0xE0077,0xE006C,0xE0073,0xE007F}};

  private int[][] emojisRecent = new int[64][0];
  final private int[][] emojisPeople = new int[][]{
    new int[]{0x1F600},new int[]{0x1F603},new int[]{0x1F604},new int[]{0x1F601},new int[]{0x1F606},new int[]{0x1F605},new int[]{0x1F923},new int[]{0x1F602},
    new int[]{0x1F642},new int[]{0x1F643},new int[]{0x1F609},new int[]{0x1F60A},new int[]{0x1F607},new int[]{0x1F970},new int[]{0x1F60D},new int[]{0x1F929},
    new int[]{0x1F618},new int[]{0x1F617},new int[]{0x263A,0xFE0F},new int[]{0x263A},new int[]{0x1F61A},new int[]{0x1F619},new int[]{0x1F60B},new int[]{0x1F61B},
    new int[]{0x1F61C},new int[]{0x1F92A},new int[]{0x1F61D},new int[]{0x1F911},new int[]{0x1F917},new int[]{0x1F92D},new int[]{0x1F92B},new int[]{0x1F914},
    new int[]{0x1F910},new int[]{0x1F928},new int[]{0x1F610},new int[]{0x1F611},new int[]{0x1F636},new int[]{0x1F60F},new int[]{0x1F612},new int[]{0x1F644},
    new int[]{0x1F62C},new int[]{0x1F925},new int[]{0x1F60C},new int[]{0x1F614},new int[]{0x1F62A},new int[]{0x1F924},new int[]{0x1F634},new int[]{0x1F637},
    new int[]{0x1F912},new int[]{0x1F915},new int[]{0x1F922},new int[]{0x1F92E},new int[]{0x1F927},new int[]{0x1F975},new int[]{0x1F976},new int[]{0x1F974},
    new int[]{0x1F635},new int[]{0x1F92F},new int[]{0x1F920},new int[]{0x1F973},new int[]{0x1F60E},new int[]{0x1F913},new int[]{0x1F9D0},new int[]{0x1F615},
    new int[]{0x1F61F},new int[]{0x1F641},new int[]{0x2639,0xFE0F},new int[]{0x1F62E},new int[]{0x1F62F},new int[]{0x1F632},new int[]{0x1F633},new int[]{0x1F97A},
    new int[]{0x1F626},new int[]{0x1F627},new int[]{0x1F628},new int[]{0x1F630},new int[]{0x1F625},new int[]{0x1F622},new int[]{0x1F62D},new int[]{0x1F631},
    new int[]{0x1F616},new int[]{0x1F623},new int[]{0x1F61E},new int[]{0x1F613},new int[]{0x1F629},new int[]{0x1F62B},new int[]{0x1F624},new int[]{0x1F621},
    new int[]{0x1F620},new int[]{0x1F92C},new int[]{0x1F608},new int[]{0x1F47F},new int[]{0x1F480},new int[]{0x2620,0xFE0F},new int[]{0x1F4A9},new int[]{0x1F921},
    new int[]{0x1F479},new int[]{0x1F47A},new int[]{0x1F47B},new int[]{0x1F47D},new int[]{0x1F47E},new int[]{0x1F916},new int[]{0x1F63A},new int[]{0x1F638},
    new int[]{0x1F639},new int[]{0x1F63B},new int[]{0x1F63C},new int[]{0x1F63D},new int[]{0x1F640},new int[]{0x1F63F},new int[]{0x1F63E},new int[]{0x1F648},
    new int[]{0x1F649},new int[]{0x1F64A},new int[]{0x1F48B},new int[]{0x1F48C},new int[]{0x1F498},new int[]{0x1F49D},new int[]{0x1F496},new int[]{0x1F497},
    new int[]{0x1F493},new int[]{0x1F49E},new int[]{0x1F495},new int[]{0x1F49F},new int[]{0x1F494},new int[]{0x2764},new int[]{0x1F9E1},new int[]{0x1F49B},
    new int[]{0x1F49A},new int[]{0x1F499},new int[]{0x1F4AF},new int[]{0x1F4A2},new int[]{0x1F4A5},new int[]{0x1F4AB},new int[]{0x1F4A6},new int[]{0x1F4A8},
    new int[]{0x1F573},new int[]{0x1F4A3},new int[]{0x1F4AC},new int[]{0x1F441,0xFE0F,0x200D,0x1F5E8,0xFE0F},new int[]{0x1F5E8},new int[]{0x1F5EF},new int[]{0x1F4AD},new int[]{0x1F4A4},
    new int[]{0x1F44B},new int[]{0x1F44B,0x1F3FB},new int[]{0x1F44B,0x1F3FC},new int[]{0x1F44B,0x1F3FD},new int[]{0x1F44B,0x1F3FE},new int[]{0x1F44B,0x1F3FF},new int[]{0x1F91A},new int[]{0x1F91A,0x1F3FB},
    new int[]{0x1F91A,0x1F3FC},new int[]{0x1F91A,0x1F3FD},new int[]{0x1F91A,0x1F3FE},new int[]{0x1F91A,0x1F3FF},new int[]{0x1F590,0xFE0F},new int[]{0x1F590},new int[]{0x1F590,0x1F3FB},new int[]{0x1F590,0x1F3FC},
    new int[]{0x1F590,0x1F3FD},new int[]{0x1F590,0x1F3FE},new int[]{0x1F590,0x1F3FF},new int[]{0x270B},new int[]{0x270B,0x1F3FB},new int[]{0x1F590,0x1F3FD},new int[]{0x1F590,0x1F3FE},new int[]{0x1F590,0x1F3FF},
    new int[]{0x270B},new int[]{0x270B,0x1F3FB},new int[]{0x270B,0x1F3FC},new int[]{0x270B,0x1F3FD},new int[]{0x270B,0x1F3FE},new int[]{0x270B,0x1F3FF},new int[]{0x1F596},new int[]{0x1F596,0x1F3FB},
    new int[]{0x1F596,0x1F3FC},new int[]{0x1F596,0x1F3FD},new int[]{0x1F596,0x1F3FE},new int[]{0x1F596,0x1F3FF},new int[]{0x1F44C},new int[]{0x1F44C,0x1F3FB},new int[]{0x1F44C,0x1F3FC},new int[]{0x1F44C,0x1F3FD},
    new int[]{0x1F44C,0x1F3FE},new int[]{0x1F44C,0x1F3FF},new int[]{0x270C,0xFE0F},new int[]{0x270C},new int[]{0x270C,0x1F3FB},new int[]{0x270C,0x1F3FC},new int[]{0x270C,0x1F3FD},new int[]{0x270C,0x1F3FE},
    new int[]{0x270C,0x1F3FF},new int[]{0x1F91E},new int[]{0x1F91E,0x1F3FB},new int[]{0x1F91E,0x1F3FC},new int[]{0x1F91E,0x1F3FD},new int[]{0x1F91E,0x1F3FE},new int[]{0x1F91E,0x1F3FF},new int[]{0x1F91F},
    new int[]{0x1F91F,0x1F3FB},new int[]{0x1F91F,0x1F3FC},new int[]{0x1F91F,0x1F3FD},new int[]{0x1F91F,0x1F3FE},new int[]{0x1F91F,0x1F3FF},new int[]{0x1F918},new int[]{0x1F918,0x1F3FB},new int[]{0x1F918,0x1F3FC},
    new int[]{0x1F918,0x1F3FD},new int[]{0x1F918,0x1F3FE},new int[]{0x1F918,0x1F3FF},new int[]{0x1F919},new int[]{0x1F919,0x1F3FB},new int[]{0x1F919,0x1F3FC},new int[]{0x1F919,0x1F3FD},new int[]{0x1F919,0x1F3FE},
    new int[]{0x1F919,0x1F3FF},new int[]{0x1F448},new int[]{0x1F448,0x1F3FB},new int[]{0x1F448,0x1F3FC},new int[]{0x1F448,0x1F3FD},new int[]{0x1F448,0x1F3FE},new int[]{0x1F448,0x1F3FF},new int[]{0x1F449},
    new int[]{0x1F449,0x1F3FB},new int[]{0x1F449,0x1F3FC},new int[]{0x1F449,0x1F3FD},new int[]{0x1F449,0x1F3FE},new int[]{0x1F449,0x1F3FF},new int[]{0x1F446},new int[]{0x1F446,0x1F3FB},new int[]{0x1F446,0x1F3FC},
    new int[]{0x1F446,0x1F3FD},new int[]{0x1F446,0x1F3FE},new int[]{0x1F446,0x1F3FF},new int[]{0x1F595},new int[]{0x1F595,0x1F3FB},new int[]{0x1F595,0x1F3FC},new int[]{0x1F595,0x1F3FD},new int[]{0x1F595,0x1F3FE},
    new int[]{0x1F595,0x1F3FF},new int[]{0x1F447},new int[]{0x1F447,0x1F3FB},new int[]{0x1F447,0x1F3FC},new int[]{0x1F447,0x1F3FD},new int[]{0x1F447,0x1F3FE},new int[]{0x1F447,0x1F3FF},new int[]{0x261D,0xFE0F},
    new int[]{0x261D},new int[]{0x261D,0x1F3FB},new int[]{0x261D,0x1F3FC},new int[]{0x261D,0x1F3FD},new int[]{0x261D,0x1F3FE},new int[]{0x261D,0x1F3FF},new int[]{0x1F44D},new int[]{0x1F44D,0x1F3FB},
    new int[]{0x1F44D,0x1F3FC},new int[]{0x1F44D,0x1F3FD},new int[]{0x1F44D,0x1F3FE},new int[]{0x1F44D,0x1F3FF},new int[]{0x1F44E},new int[]{0x1F44E,0x1F3FB},new int[]{0x1F44E,0x1F3FC},new int[]{0x1F44E,0x1F3FD},
    new int[]{0x1F44E,0x1F3FE},new int[]{0x1F44E,0x1F3FF},new int[]{0x270A},new int[]{0x270A,0x1F3FB},new int[]{0x270A,0x1F3FC},new int[]{0x270A,0x1F3FD},new int[]{0x270A,0x1F3FE},new int[]{0x270A,0x1F3FF},
    new int[]{0x1F44A},new int[]{0x1F44A,0x1F3FB},new int[]{0x1F44A,0x1F3FC},new int[]{0x1F44A,0x1F3FD},new int[]{0x1F44A,0x1F3FE},new int[]{0x1F44A,0x1F3FF},new int[]{0x1F91B},new int[]{0x1F91B,0x1F3FB},
    new int[]{0x1F91B,0x1F3FC},new int[]{0x1F91B,0x1F3FD},new int[]{0x1F91B,0x1F3FE},new int[]{0x1F91B,0x1F3FF},new int[]{0x1F91C},new int[]{0x1F91C,0x1F3FB},new int[]{0x1F91C,0x1F3FC},new int[]{0x1F91C,0x1F3FD},
    new int[]{0x1F91C,0x1F3FE},new int[]{0x1F91C,0x1F3FF},new int[]{0x1F44F},new int[]{0x1F44F,0x1F3FB},new int[]{0x1F44F,0x1F3FC},new int[]{0x1F44F,0x1F3FD},new int[]{0x1F44F,0x1F3FE},new int[]{0x1F44F,0x1F3FF},
    new int[]{0x1F64C},new int[]{0x1F64C,0x1F3FB},new int[]{0x1F64C,0x1F3FC},new int[]{0x1F64C,0x1F3FD},new int[]{0x1F64C,0x1F3FE},new int[]{0x1F64C,0x1F3FF},new int[]{0x1F450},new int[]{0x1F450,0x1F3FB},
    new int[]{0x1F450,0x1F3FC},new int[]{0x1F450,0x1F3FD},new int[]{0x1F450,0x1F3FE},new int[]{0x1F450,0x1F3FF},new int[]{0x1F932},new int[]{0x1F932,0x1F3FB},new int[]{0x1F932,0x1F3FC},new int[]{0x1F932,0x1F3FD},
    new int[]{0x1F932,0x1F3FE},new int[]{0x1F932,0x1F3FF},new int[]{0x1F91D},new int[]{0x1F64F},new int[]{0x1F64F,0x1F3FB},new int[]{0x1F64F,0x1F3FC},new int[]{0x1F64F,0x1F3FD},new int[]{0x1F64F,0x1F3FE},
    new int[]{0x1F64F,0x1F3FF},new int[]{0x270D,0xFE0F},new int[]{0x270D,0x1F3FB},new int[]{0x270D,0x1F3FC},new int[]{0x270D,0x1F3FD},new int[]{0x270D,0x1F3FE},new int[]{0x270D,0x1F3FF},new int[]{0x1F485},
    new int[]{0x1F485,0x1F3FB},new int[]{0x1F485,0x1F3FC},new int[]{0x1F485,0x1F3FD},new int[]{0x1F485,0x1F3FE},new int[]{0x1F485,0x1F3FF},new int[]{0x1F933},new int[]{0x1F933,0x1F3FB},new int[]{0x1F933,0x1F3FC},
    new int[]{0x1F933,0x1F3FD},new int[]{0x1F933,0x1F3FE},new int[]{0x1F933,0x1F3FF},new int[]{0x1F4AA},new int[]{0x1F4AA,0x1F3FB},new int[]{0x1F4AA,0x1F3FC},new int[]{0x1F4AA,0x1F3FD},new int[]{0x1F4AA,0x1F3FE},
    new int[]{0x1F4AA,0x1F3FF},new int[]{0x1F9B5},new int[]{0x1F9B5,0x1F3FB},new int[]{0x1F9B5,0x1F3FC},new int[]{0x1F9B5,0x1F3FD},new int[]{0x1F9B5,0x1F3FE},new int[]{0x1F9B5,0x1F3FF},new int[]{0x1F9B6},
    new int[]{0x1F9B6,0x1F3FB},new int[]{0x1F9B6,0x1F3FC},new int[]{0x1F9B6,0x1F3FD},new int[]{0x1F9B6,0x1F3FE},new int[]{0x1F9B6,0x1F3FF},new int[]{0x1F442},new int[]{0x1F442,0x1F3FB},new int[]{0x1F442,0x1F3FC},
    new int[]{0x1F442,0x1F3FD},new int[]{0x1F442,0x1F3FE},new int[]{0x1F442,0x1F3FF},new int[]{0x1F443},new int[]{0x1F443,0x1F3FB},new int[]{0x1F443,0x1F3FC},new int[]{0x1F443,0x1F3FD},new int[]{0x1F443,0x1F3FE},
    new int[]{0x1F443,0x1F3FF},new int[]{0x1F9E0},new int[]{0x1F9B7},new int[]{0x1F9B4},new int[]{0x1F440},new int[]{0x1F441},new int[]{0x1F445},new int[]{0x1F444},
    new int[]{0x1F476},new int[]{0x1F476,0x1F3FB},new int[]{0x1F476,0x1F3FC},new int[]{0x1F476,0x1F3FD},new int[]{0x1F476,0x1F3FE},new int[]{0x1F476,0x1F3FF},new int[]{0x1F9D2},new int[]{0x1F9D2,0x1F3FB},
    new int[]{0x1F9D2,0x1F3FC},new int[]{0x1F9D2,0x1F3FD},new int[]{0x1F9D2,0x1F3FE},new int[]{0x1F9D2,0x1F3FF},new int[]{0x1F466},new int[]{0x1F466,0x1F3FB},new int[]{0x1F466,0x1F3FC},new int[]{0x1F466,0x1F3FD},
    new int[]{0x1F466,0x1F3FE},new int[]{0x1F466,0x1F3FF},new int[]{0x1F467},new int[]{0x1F467,0x1F3FB},new int[]{0x1F467,0x1F3FC},new int[]{0x1F467,0x1F3FD},new int[]{0x1F467,0x1F3FE},new int[]{0x1F467,0x1F3FF},
    new int[]{0x1F9D1},new int[]{0x1F9D1,0x1F3FB},new int[]{0x1F9D1,0x1F3FC},new int[]{0x1F9D1,0x1F3FD},new int[]{0x1F9D1,0x1F3FE},new int[]{0x1F9D1,0x1F3FF},new int[]{0x1F471},new int[]{0x1F471,0x1F3FB},
    new int[]{0x1F471,0x1F3FC},new int[]{0x1F471,0x1F3FD},new int[]{0x1F471,0x1F3FE},new int[]{0x1F471,0x1F3FF},new int[]{0x1F468},new int[]{0x1F468,0x1F3FB},new int[]{0x1F468,0x1F3FC},new int[]{0x1F468,0x1F3FD},
    new int[]{0x1F468,0x1F3FE},new int[]{0x1F468,0x1F3FF},new int[]{0x1F9D4},new int[]{0x1F9D4,0x1F3FB},new int[]{0x1F9D4,0x1F3FC},new int[]{0x1F9D4,0x1F3FD},new int[]{0x1F9D4,0x1F3FE},new int[]{0x1F9D4,0x1F3FF},
    new int[]{0x1F468,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9B0},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9B0},new int[]{0x1F468,0x200D,0x1F9B1},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9B1},
    new int[]{0x1F468,0x1F3FC,0x200D,0x1F9B1},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9B1},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9B1},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9B1},new int[]{0x1F468,0x200D,0x1F9B3},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9B3},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9B3},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9B3},
    new int[]{0x1F468,0x1F3FE,0x200D,0x1F9B3},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9B3},new int[]{0x1F468,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9B2},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9B2},
    new int[]{0x1F469},new int[]{0x1F469,0x1F3FB},new int[]{0x1F469,0x1F3FC},new int[]{0x1F469,0x1F3FD},new int[]{0x1F469,0x1F3FE},new int[]{0x1F469,0x1F3FF},new int[]{0x1F469,0x200D,0x1F9B0},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9B0},
    new int[]{0x1F469,0x1F3FC,0x200D,0x1F9B0},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9B0},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9B0},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9B0},new int[]{0x1F469,0x200D,0x1F9B1},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9B1},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9B1},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9B1},
    new int[]{0x1F469,0x1F3FE,0x200D,0x1F9B1},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9B1},new int[]{0x1F469,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9B3},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9B3},
    new int[]{0x1F469,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9B2},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9B2},new int[]{0x1F471,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x200D,0x2640},
    new int[]{0x1F471,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FB,0x200D,0x2640},new int[]{0x1F471,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FC,0x200D,0x2640},new int[]{0x1F471,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FD,0x200D,0x2640},new int[]{0x1F471,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FE,0x200D,0x2640},
    new int[]{0x1F471,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F471,0x1F3FF,0x200D,0x2640},new int[]{0x1F471,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x200D,0x2642},new int[]{0x1F471,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FB,0x200D,0x2642},new int[]{0x1F471,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FC,0x200D,0x2642},
    new int[]{0x1F471,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FD,0x200D,0x2642},new int[]{0x1F471,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FE,0x200D,0x2642},new int[]{0x1F471,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F471,0x1F3FF,0x200D,0x2642},new int[]{0x1F9D3},new int[]{0x1F9D3,0x1F3FB},
    new int[]{0x1F9D3,0x1F3FC},new int[]{0x1F9D3,0x1F3FD},new int[]{0x1F9D3,0x1F3FE},new int[]{0x1F9D3,0x1F3FF},new int[]{0x1F474},new int[]{0x1F474,0x1F3FB},new int[]{0x1F474,0x1F3FC},new int[]{0x1F474,0x1F3FD},
    new int[]{0x1F474,0x1F3FE},new int[]{0x1F474,0x1F3FF},new int[]{0x1F475},new int[]{0x1F475,0x1F3FB},new int[]{0x1F475,0x1F3FC},new int[]{0x1F475,0x1F3FD},new int[]{0x1F475,0x1F3FE},new int[]{0x1F475,0x1F3FF},
    new int[]{0x1F64D},new int[]{0x1F64D,0x1F3FB},new int[]{0x1F64D,0x1F3FC},new int[]{0x1F64D,0x1F3FD},new int[]{0x1F64D,0x1F3FE},new int[]{0x1F64D,0x1F3FF},new int[]{0x1F64D,0x200D,0x2642},new int[]{0x1F64D,0x1F3FB,0x200D,0x2642},
    new int[]{0x1F64D,0x1F3FC,0x200D,0x2642},new int[]{0x1F64D,0x1F3FD,0x200D,0x2642},new int[]{0x1F64D,0x1F3FE,0x200D,0x2642},new int[]{0x1F64D,0x1F3FF,0x200D,0x2642},new int[]{0x1F64D,0x200D,0x2640},new int[]{0x1F64D,0x1F3FB,0x200D,0x2640},new int[]{0x1F64D,0x1F3FC,0x200D,0x2640},new int[]{0x1F64D,0x1F3FD,0x200D,0x2640},
    new int[]{0x1F64D,0x1F3FE,0x200D,0x2640},new int[]{0x1F64D,0x1F3FF,0x200D,0x2640},new int[]{0x1F64E},new int[]{0x1F64E,0x1F3FB},new int[]{0x1F64E,0x1F3FC},new int[]{0x1F64E,0x1F3FD},new int[]{0x1F64E,0x1F3FE},new int[]{0x1F64E,0x1F3FF},
    new int[]{0x1F64E,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x200D,0x2642},new int[]{0x1F64E,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FB,0x200D,0x2642},new int[]{0x1F64E,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FC,0x200D,0x2642},new int[]{0x1F64E,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FD,0x200D,0x2642},
    new int[]{0x1F64E,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FE,0x200D,0x2642},new int[]{0x1F64E,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F64E,0x1F3FF,0x200D,0x2642},new int[]{0x1F64E,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x200D,0x2640},new int[]{0x1F64E,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FB,0x200D,0x2640},
    new int[]{0x1F64E,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FC,0x200D,0x2640},new int[]{0x1F64E,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FD,0x200D,0x2640},new int[]{0x1F64E,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FE,0x200D,0x2640},new int[]{0x1F64E,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F64E,0x1F3FF,0x200D,0x2640},
    new int[]{0x1F645},new int[]{0x1F645,0x1F3FB},new int[]{0x1F645,0x1F3FC},new int[]{0x1F645,0x1F3FD},new int[]{0x1F645,0x1F3FE},new int[]{0x1F645,0x1F3FF},new int[]{0x1F645,0x200D,0x2642},new int[]{0x1F645,0x1F3FB,0x200D,0x2642},
    new int[]{0x1F645,0x1F3FC,0x200D,0x2642},new int[]{0x1F645,0x1F3FD,0x200D,0x2642},new int[]{0x1F645,0x1F3FE,0x200D,0x2642},new int[]{0x1F645,0x1F3FF,0x200D,0x2642},new int[]{0x1F645,0x200D,0x2640},new int[]{0x1F645,0x1F3FB,0x200D,0x2640},new int[]{0x1F645,0x1F3FC,0x200D,0x2640},new int[]{0x1F645,0x1F3FD,0x200D,0x2640,0xFE0F},
    new int[]{0x1F645,0x1F3FD,0x200D,0x2640},new int[]{0x1F645,0x1F3FE,0x200D,0x2640},new int[]{0x1F645,0x1F3FF,0x200D,0x2640},new int[]{0x1F646},new int[]{0x1F646,0x1F3FB},new int[]{0x1F646,0x1F3FC},new int[]{0x1F646,0x1F3FD},new int[]{0x1F646,0x1F3FE},
    new int[]{0x1F646,0x1F3FF},new int[]{0x1F646,0x200D,0x2642},new int[]{0x1F646,0x1F3FB,0x200D,0x2642},new int[]{0x1F646,0x1F3FC,0x200D,0x2642},new int[]{0x1F646,0x1F3FD,0x200D,0x2642},new int[]{0x1F646,0x1F3FE,0x200D,0x2642},new int[]{0x1F646,0x1F3FF,0x200D,0x2642},new int[]{0x1F646,0x200D,0x2640},
    new int[]{0x1F646,0x1F3FB,0x200D,0x2640},new int[]{0x1F646,0x1F3FC,0x200D,0x2640},new int[]{0x1F646,0x1F3FD,0x200D,0x2640},new int[]{0x1F646,0x1F3FE,0x200D,0x2640},new int[]{0x1F646,0x1F3FF,0x200D,0x2640},new int[]{0x1F481},new int[]{0x1F481,0x1F3FB},new int[]{0x1F481,0x1F3FC},
    new int[]{0x1F481,0x1F3FD},new int[]{0x1F481,0x1F3FE},new int[]{0x1F481,0x1F3FF},new int[]{0x1F481,0x200D,0x2642},new int[]{0x1F481,0x1F3FB,0x200D,0x2642},new int[]{0x1F481,0x1F3FC,0x200D,0x2642},new int[]{0x1F481,0x1F3FD,0x200D,0x2642},new int[]{0x1F481,0x1F3FE,0x200D,0x2642},
    new int[]{0x1F481,0x1F3FF,0x200D,0x2642},new int[]{0x1F481,0x200D,0x2640},new int[]{0x1F481,0x1F3FB,0x200D,0x2640},new int[]{0x1F481,0x1F3FC,0x200D,0x2640},new int[]{0x1F481,0x1F3FD,0x200D,0x2640},new int[]{0x1F481,0x1F3FE,0x200D,0x2640},new int[]{0x1F481,0x1F3FF,0x200D,0x2640},new int[]{0x1F64B},
    new int[]{0x1F64B,0x1F3FB},new int[]{0x1F64B,0x1F3FC},new int[]{0x1F64B,0x1F3FD},new int[]{0x1F64B,0x1F3FE},new int[]{0x1F64B,0x1F3FF},new int[]{0x1F64B,0x200D,0x2642},new int[]{0x1F64B,0x1F3FB,0x200D,0x2642},new int[]{0x1F64B,0x1F3FC,0x200D,0x2642},
    new int[]{0x1F64B,0x1F3FD,0x200D,0x2642},new int[]{0x1F64B,0x1F3FE,0x200D,0x2642},new int[]{0x1F64B,0x1F3FF,0x200D,0x2642},new int[]{0x1F64B,0x200D,0x2640},new int[]{0x1F64B,0x1F3FB,0x200D,0x2640},new int[]{0x1F64B,0x1F3FC,0x200D,0x2640},new int[]{0x1F64B,0x1F3FD,0x200D,0x2640},new int[]{0x1F64B,0x1F3FE,0x200D,0x2640},
    new int[]{0x1F64B,0x1F3FF,0x200D,0x2640},new int[]{0x1F9CF,0x200D,0x2642,0xFE0F},new int[]{0x1F9CF,0x200D,0x2640,0xFE0F},new int[]{0x1F647},new int[]{0x1F647,0x1F3FB},new int[]{0x1F647,0x1F3FC},new int[]{0x1F647,0x1F3FD},new int[]{0x1F647,0x1F3FE},
    new int[]{0x1F647,0x1F3FF},new int[]{0x1F647,0x200D,0x2640},new int[]{0x1F647,0x1F3FB,0x200D,0x2640},new int[]{0x1F647,0x1F3FC,0x200D,0x2640},new int[]{0x1F647,0x1F3FD,0x200D,0x2640},new int[]{0x1F647,0x1F3FE,0x200D,0x2640},new int[]{0x1F647,0x1F3FF,0x200D,0x2640},new int[]{0x1F926},
    new int[]{0x1F926,0x1F3FB},new int[]{0x1F926,0x1F3FC},new int[]{0x1F926,0x1F3FD},new int[]{0x1F926,0x1F3FE},new int[]{0x1F926,0x1F3FF},new int[]{0x1F926,0x200D,0x2642},new int[]{0x1F926,0x1F3FB,0x200D,0x2642},new int[]{0x1F926,0x1F3FC,0x200D,0x2642},
    new int[]{0x1F926,0x1F3FD,0x200D,0x2642},new int[]{0x1F926,0x1F3FE,0x200D,0x2642},new int[]{0x1F926,0x1F3FF,0x200D,0x2642},new int[]{0x1F926,0x200D,0x2640},new int[]{0x1F926,0x1F3FB,0x200D,0x2640},new int[]{0x1F926,0x1F3FC,0x200D,0x2640},new int[]{0x1F926,0x1F3FD,0x200D,0x2640},new int[]{0x1F926,0x1F3FE,0x200D,0x2640},
    new int[]{0x1F926,0x1F3FF,0x200D,0x2640},new int[]{0x1F937},new int[]{0x1F937,0x1F3FB},new int[]{0x1F937,0x1F3FC},new int[]{0x1F937,0x1F3FD},new int[]{0x1F937,0x1F3FE},new int[]{0x1F937,0x1F3FF},new int[]{0x1F937,0x200D,0x2642},
    new int[]{0x1F937,0x1F3FB,0x200D,0x2642},new int[]{0x1F937,0x1F3FC,0x200D,0x2642},new int[]{0x1F937,0x1F3FD,0x200D,0x2642},new int[]{0x1F937,0x1F3FE,0x200D,0x2642},new int[]{0x1F937,0x1F3FF,0x200D,0x2642},new int[]{0x1F937,0x200D,0x2640},new int[]{0x1F937,0x1F3FB,0x200D,0x2640},new int[]{0x1F937,0x1F3FC,0x200D,0x2640},
    new int[]{0x1F937,0x1F3FD,0x200D,0x2640},new int[]{0x1F937,0x1F3FE,0x200D,0x2640},new int[]{0x1F937,0x1F3FF,0x200D,0x2640},new int[]{0x1F9D1,0x200D,0x2695},new int[]{0x1F9D1,0x1F3FB,0x200D,0x2695},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2695},new int[]{0x1F9D1,0x1F3FD,0x200D,0x2695},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2695},
    new int[]{0x1F9D1,0x1F3FF,0x200D,0x2695},new int[]{0x1F468,0x200D,0x2695},new int[]{0x1F468,0x1F3FB,0x200D,0x2695},new int[]{0x1F468,0x1F3FC,0x200D,0x2695},new int[]{0x1F468,0x1F3FD,0x200D,0x2695},new int[]{0x1F468,0x1F3FE,0x200D,0x2695},new int[]{0x1F468,0x1F3FF,0x200D,0x2695},new int[]{0x1F469,0x200D,0x2695},
    new int[]{0x1F469,0x1F3FB,0x200D,0x2695},new int[]{0x1F469,0x1F3FC,0x200D,0x2695},new int[]{0x1F469,0x1F3FD,0x200D,0x2695},new int[]{0x1F469,0x1F3FE,0x200D,0x2695},new int[]{0x1F469,0x1F3FF,0x200D,0x2695},new int[]{0x1F468,0x200D,0x1F393},new int[]{0x1F468,0x1F3FB,0x200D,0x1F393},new int[]{0x1F468,0x1F3FC,0x200D,0x1F393},
    new int[]{0x1F468,0x1F3FD,0x200D,0x1F393},new int[]{0x1F468,0x1F3FE,0x200D,0x1F393},new int[]{0x1F468,0x1F3FF,0x200D,0x1F393},new int[]{0x1F469,0x200D,0x1F393},new int[]{0x1F469,0x1F3FB,0x200D,0x1F393},new int[]{0x1F469,0x1F3FC,0x200D,0x1F393},new int[]{0x1F469,0x1F3FD,0x200D,0x1F393},new int[]{0x1F469,0x1F3FE,0x200D,0x1F393},
    new int[]{0x1F469,0x1F3FF,0x200D,0x1F393},new int[]{0x1F468,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FB,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FC,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FD,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FE,0x200D,0x1F3EB},new int[]{0x1F468,0x1F3FF,0x200D,0x1F3EB},new int[]{0x1F469,0x200D,0x1F3EB},
    new int[]{0x1F469,0x1F3FB,0x200D,0x1F3EB},new int[]{0x1F469,0x1F3FC,0x200D,0x1F3EB},new int[]{0x1F469,0x1F3FD,0x200D,0x1F3EB},new int[]{0x1F469,0x1F3FE,0x200D,0x1F3EB},new int[]{0x1F469,0x1F3FF,0x200D,0x1F3EB},new int[]{0x1F9D1,0x200D,0x2696},new int[]{0x1F9D1,0x1F3FB,0x200D,0x2696},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2696},
    new int[]{0x1F9D1,0x1F3FD,0x200D,0x2696},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2696},new int[]{0x1F9D1,0x1F3FF,0x200D,0x2696,0xFE0F},new int[]{0x1F9D1,0x1F3FF,0x200D,0x2696},new int[]{0x1F468,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x200D,0x2696},new int[]{0x1F468,0x1F3FB,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FB,0x200D,0x2696},
    new int[]{0x1F468,0x1F3FC,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FC,0x200D,0x2696},new int[]{0x1F468,0x1F3FD,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FD,0x200D,0x2696},new int[]{0x1F468,0x1F3FE,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FE,0x200D,0x2696},new int[]{0x1F468,0x1F3FF,0x200D,0x2696,0xFE0F},new int[]{0x1F468,0x1F3FF,0x200D,0x2696},
    new int[]{0x1F469,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x200D,0x2696},new int[]{0x1F469,0x1F3FB,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FB,0x200D,0x2696},new int[]{0x1F469,0x1F3FC,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FC,0x200D,0x2696},new int[]{0x1F469,0x1F3FD,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FD,0x200D,0x2696},
    new int[]{0x1F469,0x1F3FE,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FE,0x200D,0x2696},new int[]{0x1F469,0x1F3FF,0x200D,0x2696,0xFE0F},new int[]{0x1F469,0x1F3FF,0x200D,0x2696},new int[]{0x1F9D1,0x200D,0x1F33E},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F33E},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F33E},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F33E},
    new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F33E},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F33E},new int[]{0x1F468,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FB,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FC,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FD,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FE,0x200D,0x1F33E},new int[]{0x1F468,0x1F3FF,0x200D,0x1F33E},
    new int[]{0x1F469,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FB,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FC,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FD,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FE,0x200D,0x1F33E},new int[]{0x1F469,0x1F3FF,0x200D,0x1F33E},new int[]{0x1F9D1,0x200D,0x1F373},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F373},
    new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F373},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F373},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F373},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F373},new int[]{0x1F468,0x200D,0x1F373},new int[]{0x1F468,0x1F3FB,0x200D,0x1F373},new int[]{0x1F468,0x1F3FC,0x200D,0x1F373},new int[]{0x1F468,0x1F3FD,0x200D,0x1F373},
    new int[]{0x1F468,0x1F3FE,0x200D,0x1F373},new int[]{0x1F468,0x1F3FF,0x200D,0x1F373},new int[]{0x1F469,0x200D,0x1F373},new int[]{0x1F469,0x1F3FB,0x200D,0x1F373},new int[]{0x1F469,0x1F3FC,0x200D,0x1F373},new int[]{0x1F469,0x1F3FD,0x200D,0x1F373},new int[]{0x1F469,0x1F3FE,0x200D,0x1F373},new int[]{0x1F469,0x1F3FF,0x200D,0x1F373},
    new int[]{0x1F9D1,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F527},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F527},new int[]{0x1F468,0x200D,0x1F527},new int[]{0x1F468,0x1F3FB,0x200D,0x1F527},
    new int[]{0x1F468,0x1F3FC,0x200D,0x1F527},new int[]{0x1F468,0x1F3FD,0x200D,0x1F527},new int[]{0x1F468,0x1F3FE,0x200D,0x1F527},new int[]{0x1F468,0x1F3FF,0x200D,0x1F527},new int[]{0x1F469,0x200D,0x1F527},new int[]{0x1F469,0x1F3FB,0x200D,0x1F527},new int[]{0x1F469,0x1F3FC,0x200D,0x1F527},new int[]{0x1F469,0x1F3FD,0x200D,0x1F527},
    new int[]{0x1F469,0x1F3FE,0x200D,0x1F527},new int[]{0x1F469,0x1F3FF,0x200D,0x1F527},new int[]{0x1F9D1,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F3ED},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F3ED},
    new int[]{0x1F468,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FB,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FC,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FD,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FE,0x200D,0x1F3ED},new int[]{0x1F468,0x1F3FF,0x200D,0x1F3ED},new int[]{0x1F469,0x200D,0x1F3ED},new int[]{0x1F469,0x1F3FB,0x200D,0x1F3ED},
    new int[]{0x1F469,0x1F3FC,0x200D,0x1F3ED},new int[]{0x1F469,0x1F3FD,0x200D,0x1F3ED},new int[]{0x1F469,0x1F3FE,0x200D,0x1F3ED},new int[]{0x1F469,0x1F3FF,0x200D,0x1F3ED},new int[]{0x1F9D1,0x200D,0x1F4BC},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F4BC},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F4BC},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F4BC},
    new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F4BC},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F4BC},new int[]{0x1F468,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FB,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FC,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FD,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FE,0x200D,0x1F4BC},new int[]{0x1F468,0x1F3FF,0x200D,0x1F4BC},
    new int[]{0x1F469,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FB,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FC,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FD,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FE,0x200D,0x1F4BC},new int[]{0x1F469,0x1F3FF,0x200D,0x1F4BC},new int[]{0x1F9D1,0x200D,0x1F52C},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F52C},
    new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F52C},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F52C},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F52C},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F52C},new int[]{0x1F468,0x200D,0x1F52C},new int[]{0x1F468,0x1F3FB,0x200D,0x1F52C},new int[]{0x1F468,0x1F3FC,0x200D,0x1F52C},new int[]{0x1F468,0x1F3FD,0x200D,0x1F52C},
    new int[]{0x1F468,0x1F3FE,0x200D,0x1F52C},new int[]{0x1F468,0x1F3FF,0x200D,0x1F52C},new int[]{0x1F469,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FB,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FC,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FD,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FE,0x200D,0x1F52C},new int[]{0x1F469,0x1F3FF,0x200D,0x1F52C},
    new int[]{0x1F9D1,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F4BB},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F4BB},new int[]{0x1F468,0x200D,0x1F4BB},new int[]{0x1F468,0x1F3FB,0x200D,0x1F4BB},
    new int[]{0x1F468,0x1F3FC,0x200D,0x1F4BB},new int[]{0x1F468,0x1F3FD,0x200D,0x1F4BB},new int[]{0x1F468,0x1F3FE,0x200D,0x1F4BB},new int[]{0x1F468,0x1F3FF,0x200D,0x1F4BB},new int[]{0x1F469,0x200D,0x1F4BB},new int[]{0x1F469,0x1F3FB,0x200D,0x1F4BB},new int[]{0x1F469,0x1F3FC,0x200D,0x1F4BB},new int[]{0x1F469,0x1F3FD,0x200D,0x1F4BB},
    new int[]{0x1F469,0x1F3FE,0x200D,0x1F4BB},new int[]{0x1F469,0x1F3FF,0x200D,0x1F4BB},new int[]{0x1F9D1,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F3A4},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F3A4},
    new int[]{0x1F468,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FB,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FC,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FD,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FE,0x200D,0x1F3A4},new int[]{0x1F468,0x1F3FF,0x200D,0x1F3A4},new int[]{0x1F469,0x200D,0x1F3A4},new int[]{0x1F469,0x1F3FB,0x200D,0x1F3A4},
    new int[]{0x1F469,0x1F3FC,0x200D,0x1F3A4},new int[]{0x1F469,0x1F3FD,0x200D,0x1F3A4},new int[]{0x1F469,0x1F3FE,0x200D,0x1F3A4},new int[]{0x1F469,0x1F3FF,0x200D,0x1F3A4},new int[]{0x1F9D1,0x200D,0x1F3A8},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F3A8},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F3A8},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F3A8},
    new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F3A8},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F3A8},new int[]{0x1F468,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FB,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FC,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FD,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FE,0x200D,0x1F3A8},new int[]{0x1F468,0x1F3FF,0x200D,0x1F3A8},
    new int[]{0x1F469,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FB,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FC,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FD,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FE,0x200D,0x1F3A8},new int[]{0x1F469,0x1F3FF,0x200D,0x1F3A8},new int[]{0x1F9D1,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x200D,0x2708},
    new int[]{0x1F9D1,0x1F3FB,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FB,0x200D,0x2708},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FC,0x200D,0x2708},new int[]{0x1F9D1,0x1F3FD,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FD,0x200D,0x2708},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FE,0x200D,0x2708},
    new int[]{0x1F9D1,0x1F3FF,0x200D,0x2708,0xFE0F},new int[]{0x1F9D1,0x1F3FF,0x200D,0x2708},new int[]{0x1F468,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x200D,0x2708},new int[]{0x1F468,0x1F3FB,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FB,0x200D,0x2708},new int[]{0x1F468,0x1F3FC,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FC,0x200D,0x2708},
    new int[]{0x1F468,0x1F3FD,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FD,0x200D,0x2708},new int[]{0x1F468,0x1F3FE,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FE,0x200D,0x2708},new int[]{0x1F468,0x1F3FF,0x200D,0x2708,0xFE0F},new int[]{0x1F468,0x1F3FF,0x200D,0x2708},new int[]{0x1F469,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x200D,0x2708},
    new int[]{0x1F469,0x1F3FB,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FB,0x200D,0x2708},new int[]{0x1F469,0x1F3FC,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FC,0x200D,0x2708},new int[]{0x1F469,0x1F3FD,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FD,0x200D,0x2708},new int[]{0x1F469,0x1F3FE,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FE,0x200D,0x2708},
    new int[]{0x1F469,0x1F3FF,0x200D,0x2708,0xFE0F},new int[]{0x1F469,0x1F3FF,0x200D,0x2708},new int[]{0x1F9D1,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F680},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F680},
    new int[]{0x1F468,0x200D,0x1F680},new int[]{0x1F468,0x1F3FB,0x200D,0x1F680},new int[]{0x1F468,0x1F3FC,0x200D,0x1F680},new int[]{0x1F468,0x1F3FD,0x200D,0x1F680},new int[]{0x1F468,0x1F3FE,0x200D,0x1F680},new int[]{0x1F468,0x1F3FF,0x200D,0x1F680},new int[]{0x1F469,0x200D,0x1F680},new int[]{0x1F469,0x1F3FB,0x200D,0x1F680},
    new int[]{0x1F469,0x1F3FC,0x200D,0x1F680},new int[]{0x1F469,0x1F3FD,0x200D,0x1F680},new int[]{0x1F469,0x1F3FE,0x200D,0x1F680},new int[]{0x1F469,0x1F3FF,0x200D,0x1F680},new int[]{0x1F468,0x200D,0x1F692},new int[]{0x1F468,0x1F3FB,0x200D,0x1F692},new int[]{0x1F468,0x1F3FC,0x200D,0x1F692},new int[]{0x1F468,0x1F3FD,0x200D,0x1F692},
    new int[]{0x1F468,0x1F3FE,0x200D,0x1F692},new int[]{0x1F468,0x1F3FF,0x200D,0x1F692},new int[]{0x1F469,0x200D,0x1F692},new int[]{0x1F469,0x1F3FB,0x200D,0x1F692},new int[]{0x1F469,0x1F3FC,0x200D,0x1F692},new int[]{0x1F469,0x1F3FD,0x200D,0x1F692},new int[]{0x1F469,0x1F3FE,0x200D,0x1F692},new int[]{0x1F469,0x1F3FF,0x200D,0x1F692},

    new int[]{0x1F46E},new int[]{0x1F46E,0x1F3FB},new int[]{0x1F46E,0x1F3FC},new int[]{0x1F46E,0x1F3FD},new int[]{0x1F46E,0x1F3FE},new int[]{0x1F46E,0x1F3FF},new int[]{0x1F46E,0x200D,0x2642},new int[]{0x1F46E,0x1F3FB,0x200D,0x2642},new int[]{0x1F46E,0x1F3FC,0x200D,0x2642},new int[]{0x1F46E,0x1F3FD,0x200D,0x2642},new int[]{0x1F46E,0x1F3FE,0x200D,0x2642},new int[]{0x1F46E,0x1F3FF,0x200D,0x2642},new int[]{0x1F46E,0x200D,0x2640},new int[]{0x1F46E,0x1F3FB,0x200D,0x2640},new int[]{0x1F46E,0x1F3FC,0x200D,0x2640},new int[]{0x1F46E,0x1F3FD,0x200D,0x2640},new int[]{0x1F46E,0x1F3FE,0x200D,0x2640},new int[]{0x1F46E,0x1F3FF,0x200D,0x2640},new int[]{0x1F575},new int[]{0x1F575,0x1F3FB},new int[]{0x1F575,0x1F3FC},new int[]{0x1F575,0x1F3FD},new int[]{0x1F575,0x1F3FE},new int[]{0x1F575,0x1F3FF},new int[]{0x1F575,0xFE0F,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0xFE0F,0x200D,0x2642},new int[]{0x1F575,0x200D,0x2642},new int[]{0x1F575,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FB,0x200D,0x2642},new int[]{0x1F575,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FC,0x200D,0x2642},new int[]{0x1F575,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FD,0x200D,0x2642},new int[]{0x1F575,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FE,0x200D,0x2642},new int[]{0x1F575,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F575,0x1F3FF,0x200D,0x2642},new int[]{0x1F575,0xFE0F,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0xFE0F,0x200D,0x2640},new int[]{0x1F575,0x200D,0x2640},new int[]{0x1F575,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FB,0x200D,0x2640},new int[]{0x1F575,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FC,0x200D,0x2640},new int[]{0x1F575,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FD,0x200D,0x2640},new int[]{0x1F575,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FE,0x200D,0x2640},new int[]{0x1F575,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F575,0x1F3FF,0x200D,0x2640},new int[]{0x1F482},new int[]{0x1F482,0x1F3FB},new int[]{0x1F482,0x1F3FC},new int[]{0x1F482,0x1F3FD},new int[]{0x1F482,0x1F3FE},new int[]{0x1F482,0x1F3FF},new int[]{0x1F482,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x200D,0x2642},new int[]{0x1F482,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FB,0x200D,0x2642},new int[]{0x1F482,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FC,0x200D,0x2642},new int[]{0x1F482,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FD,0x200D,0x2642},new int[]{0x1F482,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FE,0x200D,0x2642},new int[]{0x1F482,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F482,0x1F3FF,0x200D,0x2642},new int[]{0x1F482,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x200D,0x2640},new int[]{0x1F482,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FB,0x200D,0x2640},new int[]{0x1F482,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FC,0x200D,0x2640},new int[]{0x1F482,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FD,0x200D,0x2640},new int[]{0x1F482,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FE,0x200D,0x2640},new int[]{0x1F482,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F482,0x1F3FF,0x200D,0x2640},new int[]{0x1F477},new int[]{0x1F477,0x1F3FB},new int[]{0x1F477,0x1F3FC},new int[]{0x1F477,0x1F3FD},new int[]{0x1F477,0x1F3FE},new int[]{0x1F477,0x1F3FF},new int[]{0x1F477,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x200D,0x2642},new int[]{0x1F477,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FB,0x200D,0x2642},new int[]{0x1F477,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FC,0x200D,0x2642},new int[]{0x1F477,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FD,0x200D,0x2642},new int[]{0x1F477,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FE,0x200D,0x2642},new int[]{0x1F477,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F477,0x1F3FF,0x200D,0x2642},new int[]{0x1F477,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x200D,0x2640},new int[]{0x1F477,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FB,0x200D,0x2640},new int[]{0x1F477,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FC,0x200D,0x2640},new int[]{0x1F477,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FD,0x200D,0x2640},new int[]{0x1F477,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FE,0x200D,0x2640},new int[]{0x1F477,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F477,0x1F3FF,0x200D,0x2640},new int[]{0x1F934},new int[]{0x1F934,0x1F3FB},new int[]{0x1F934,0x1F3FC},new int[]{0x1F934,0x1F3FD},new int[]{0x1F934,0x1F3FE},new int[]{0x1F934,0x1F3FF},new int[]{0x1F478},new int[]{0x1F478,0x1F3FB},new int[]{0x1F478,0x1F3FC},new int[]{0x1F478,0x1F3FD},new int[]{0x1F478,0x1F3FE},new int[]{0x1F478,0x1F3FF},new int[]{0x1F473},new int[]{0x1F473,0x1F3FB},new int[]{0x1F473,0x1F3FC},new int[]{0x1F473,0x1F3FD},new int[]{0x1F473,0x1F3FE},new int[]{0x1F473,0x1F3FF},new int[]{0x1F473,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x200D,0x2642},new int[]{0x1F473,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FB,0x200D,0x2642},new int[]{0x1F473,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FC,0x200D,0x2642},new int[]{0x1F473,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FD,0x200D,0x2642},new int[]{0x1F473,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FE,0x200D,0x2642},new int[]{0x1F473,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F473,0x1F3FF,0x200D,0x2642},new int[]{0x1F473,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x200D,0x2640},new int[]{0x1F473,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FB,0x200D,0x2640},new int[]{0x1F473,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FC,0x200D,0x2640},new int[]{0x1F473,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FD,0x200D,0x2640},new int[]{0x1F473,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FE,0x200D,0x2640},new int[]{0x1F473,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F473,0x1F3FF,0x200D,0x2640},new int[]{0x1F472},new int[]{0x1F472,0x1F3FB},new int[]{0x1F472,0x1F3FC},new int[]{0x1F472,0x1F3FD},new int[]{0x1F472,0x1F3FE},new int[]{0x1F472,0x1F3FF},new int[]{0x1F9D5},new int[]{0x1F9D5,0x1F3FB},new int[]{0x1F9D5,0x1F3FC},new int[]{0x1F9D5,0x1F3FD},new int[]{0x1F9D5,0x1F3FE},new int[]{0x1F9D5,0x1F3FF},new int[]{0x1F935},new int[]{0x1F935,0x1F3FB},new int[]{0x1F935,0x1F3FC},new int[]{0x1F935,0x1F3FD},new int[]{0x1F935,0x1F3FE},new int[]{0x1F935,0x1F3FF},new int[]{0x1F470},new int[]{0x1F470,0x1F3FB},new int[]{0x1F470,0x1F3FC},new int[]{0x1F470,0x1F3FD},new int[]{0x1F470,0x1F3FE},new int[]{0x1F470,0x1F3FF},new int[]{0x1F930},new int[]{0x1F930,0x1F3FB},new int[]{0x1F930,0x1F3FC},new int[]{0x1F930,0x1F3FD},new int[]{0x1F930,0x1F3FE},new int[]{0x1F930,0x1F3FF},new int[]{0x1F931},new int[]{0x1F931,0x1F3FB},new int[]{0x1F931,0x1F3FC},new int[]{0x1F931,0x1F3FD},new int[]{0x1F931,0x1F3FE},new int[]{0x1F931,0x1F3FF},new int[]{0x1F47C},new int[]{0x1F47C,0x1F3FB},new int[]{0x1F47C,0x1F3FC},new int[]{0x1F47C,0x1F3FD},new int[]{0x1F47C,0x1F3FE},new int[]{0x1F47C,0x1F3FF},new int[]{0x1F385},new int[]{0x1F385,0x1F3FB},new int[]{0x1F385,0x1F3FC},new int[]{0x1F385,0x1F3FD},new int[]{0x1F385,0x1F3FE},new int[]{0x1F385,0x1F3FF},new int[]{0x1F936},new int[]{0x1F936,0x1F3FB},new int[]{0x1F936,0x1F3FC},new int[]{0x1F936,0x1F3FD},new int[]{0x1F936,0x1F3FE},new int[]{0x1F936,0x1F3FF},new int[]{0x1F9B8},new int[]{0x1F9B8,0x1F3FB},new int[]{0x1F9B8,0x1F3FC},new int[]{0x1F9B8,0x1F3FD},new int[]{0x1F9B8,0x1F3FE},new int[]{0x1F9B8,0x1F3FF},new int[]{0x1F9B8,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FB,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FC,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FD,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FE,0x200D,0x2642},new int[]{0x1F9B8,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9B8,0x1F3FF,0x200D,0x2642},new int[]{0x1F9B8,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FB,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FC,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FD,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FE,0x200D,0x2640},new int[]{0x1F9B8,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9B8,0x1F3FF,0x200D,0x2640},new int[]{0x1F9B9},new int[]{0x1F9B9,0x1F3FB},new int[]{0x1F9B9,0x1F3FC},new int[]{0x1F9B9,0x1F3FD},new int[]{0x1F9B9,0x1F3FE},new int[]{0x1F9B9,0x1F3FF},new int[]{0x1F9B9,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FB,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FC,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FD,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FE,0x200D,0x2642},new int[]{0x1F9B9,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9B9,0x1F3FF,0x200D,0x2642},new int[]{0x1F9B9,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FB,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FC,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FD,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FE,0x200D,0x2640},new int[]{0x1F9B9,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9B9,0x1F3FF,0x200D,0x2640},new int[]{0x1F9D9},new int[]{0x1F9D9,0x1F3FB},new int[]{0x1F9D9,0x1F3FC},new int[]{0x1F9D9,0x1F3FD},new int[]{0x1F9D9,0x1F3FE},new int[]{0x1F9D9,0x1F3FF},new int[]{0x1F9D9,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FB,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FC,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FD,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FE,0x200D,0x2642},new int[]{0x1F9D9,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9D9,0x1F3FF,0x200D,0x2642},new int[]{0x1F9D9,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FB,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FC,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FD,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FE,0x200D,0x2640},new int[]{0x1F9D9,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9D9,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DA},new int[]{0x1F9DA,0x1F3FB},new int[]{0x1F9DA,0x1F3FC},new int[]{0x1F9DA,0x1F3FD},new int[]{0x1F9DA,0x1F3FE},new int[]{0x1F9DA,0x1F3FF},new int[]{0x1F9DA,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FB,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FC,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FD,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FE,0x200D,0x2642},new int[]{0x1F9DA,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DA,0x1F3FF,0x200D,0x2642},new int[]{0x1F9DA,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FB,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FC,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FD,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FE,0x200D,0x2640},new int[]{0x1F9DA,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DA,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DB},new int[]{0x1F9DB,0x1F3FB},new int[]{0x1F9DB,0x1F3FC},new int[]{0x1F9DB,0x1F3FD},new int[]{0x1F9DB,0x1F3FE},new int[]{0x1F9DB,0x1F3FF},new int[]{0x1F9DB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FB,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FC,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FD,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FE,0x200D,0x2642},new int[]{0x1F9DB,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DB,0x1F3FF,0x200D,0x2642},new int[]{0x1F9DB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FB,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FC,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FD,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FE,0x200D,0x2640},new int[]{0x1F9DB,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DB,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DC},new int[]{0x1F9DC,0x1F3FB},new int[]{0x1F9DC,0x1F3FC},new int[]{0x1F9DC,0x1F3FD},new int[]{0x1F9DC,0x1F3FE},new int[]{0x1F9DC,0x1F3FF},new int[]{0x1F9DC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FB,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FC,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FD,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FE,0x200D,0x2642},new int[]{0x1F9DC,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DC,0x1F3FF,0x200D,0x2642},new int[]{0x1F9DC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FB,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FC,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FD,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FE,0x200D,0x2640},new int[]{0x1F9DC,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DC,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DD},new int[]{0x1F9DD,0x1F3FB},new int[]{0x1F9DD,0x1F3FC},new int[]{0x1F9DD,0x1F3FD},new int[]{0x1F9DD,0x1F3FE},new int[]{0x1F9DD,0x1F3FF},new int[]{0x1F9DD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FB,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FC,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FD,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FE,0x200D,0x2642},new int[]{0x1F9DD,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DD,0x1F3FF,0x200D,0x2642},new int[]{0x1F9DD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FB,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FC,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FD,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FE,0x200D,0x2640},new int[]{0x1F9DD,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DD,0x1F3FF,0x200D,0x2640},new int[]{0x1F9DE},new int[]{0x1F9DE,0x200D,0x2642,0xFE0F},new int[]{0x1F9DE,0x200D,0x2642},new int[]{0x1F9DE,0x200D,0x2640,0xFE0F},new int[]{0x1F9DE,0x200D,0x2640},new int[]{0x1F9DF},new int[]{0x1F9DF,0x200D,0x2642,0xFE0F},new int[]{0x1F9DF,0x200D,0x2642},new int[]{0x1F9DF,0x200D,0x2640,0xFE0F},new int[]{0x1F9DF,0x200D,0x2640},new int[]{0x1F486},new int[]{0x1F486,0x1F3FB},new int[]{0x1F486,0x1F3FC},new int[]{0x1F486,0x1F3FD},new int[]{0x1F486,0x1F3FE},new int[]{0x1F486,0x1F3FF},new int[]{0x1F486,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x200D,0x2642},new int[]{0x1F486,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FB,0x200D,0x2642},new int[]{0x1F486,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FC,0x200D,0x2642},new int[]{0x1F486,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FD,0x200D,0x2642},new int[]{0x1F486,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FE,0x200D,0x2642},new int[]{0x1F486,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F486,0x1F3FF,0x200D,0x2642},new int[]{0x1F486,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x200D,0x2640},new int[]{0x1F486,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FB,0x200D,0x2640},new int[]{0x1F486,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FC,0x200D,0x2640},new int[]{0x1F486,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FD,0x200D,0x2640},new int[]{0x1F486,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FE,0x200D,0x2640},new int[]{0x1F486,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F486,0x1F3FF,0x200D,0x2640},new int[]{0x1F487},new int[]{0x1F487,0x1F3FB},new int[]{0x1F487,0x1F3FC},new int[]{0x1F487,0x1F3FD},new int[]{0x1F487,0x1F3FE},new int[]{0x1F487,0x1F3FF},new int[]{0x1F487,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x200D,0x2642},new int[]{0x1F487,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FB,0x200D,0x2642},new int[]{0x1F487,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FC,0x200D,0x2642},new int[]{0x1F487,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FD,0x200D,0x2642},new int[]{0x1F487,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FE,0x200D,0x2642},new int[]{0x1F487,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F487,0x1F3FF,0x200D,0x2642},new int[]{0x1F487,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x200D,0x2640},new int[]{0x1F487,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FB,0x200D,0x2640},new int[]{0x1F487,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FC,0x200D,0x2640},new int[]{0x1F487,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FD,0x200D,0x2640},new int[]{0x1F487,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FE,0x200D,0x2640},new int[]{0x1F487,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F487,0x1F3FF,0x200D,0x2640},new int[]{0x1F6B6},new int[]{0x1F6B6,0x1F3FB},new int[]{0x1F6B6,0x1F3FC},new int[]{0x1F6B6,0x1F3FD},new int[]{0x1F6B6,0x1F3FE},new int[]{0x1F6B6,0x1F3FF},new int[]{0x1F6B6,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FB,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FC,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FD,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FE,0x200D,0x2642},new int[]{0x1F6B6,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F6B6,0x1F3FF,0x200D,0x2642},new int[]{0x1F6B6,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FB,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FC,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FD,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FE,0x200D,0x2640},new int[]{0x1F6B6,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F6B6,0x1F3FF,0x200D,0x2640},new int[]{0x1F9CD},new int[]{0x1F9CD,0x1F3FB},new int[]{0x1F9CD,0x1F3FC},new int[]{0x1F9CD,0x1F3FD},new int[]{0x1F9CD,0x1F3FE},new int[]{0x1F9CD,0x1F3FF},new int[]{0x1F9CD,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FB,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FC,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FD,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FE,0x200D,0x2642},new int[]{0x1F9CD,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9CD,0x1F3FF,0x200D,0x2642},new int[]{0x1F9CD,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FB,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FC,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FD,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FE,0x200D,0x2640},new int[]{0x1F9CD,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9CD,0x1F3FF,0x200D,0x2640},new int[]{0x1F9CE},new int[]{0x1F9CE,0x1F3FB},new int[]{0x1F9CE,0x1F3FC},new int[]{0x1F9CE,0x1F3FD},new int[]{0x1F9CE,0x1F3FE},new int[]{0x1F9CE,0x1F3FF},new int[]{0x1F9CE,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FB,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FC,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FD,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FE,0x200D,0x2642},new int[]{0x1F9CE,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9CE,0x1F3FF,0x200D,0x2642},new int[]{0x1F9CE,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FB,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FC,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FD,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FE,0x200D,0x2640},new int[]{0x1F9CE,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9CE,0x1F3FF,0x200D,0x2640},new int[]{0x1F9D1,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9AF},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9AF},new int[]{0x1F468,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9AF},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9AF},new int[]{0x1F469,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9AF},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9AF},new int[]{0x1F9D1,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9BC},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9BC},new int[]{0x1F468,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9BC},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9BC},new int[]{0x1F469,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9BC},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9BC},new int[]{0x1F9D1,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FB,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FC,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FD,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FE,0x200D,0x1F9BD},new int[]{0x1F9D1,0x1F3FF,0x200D,0x1F9BD},new int[]{0x1F468,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FB,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FC,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FD,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FE,0x200D,0x1F9BD},new int[]{0x1F468,0x1F3FF,0x200D,0x1F9BD},new int[]{0x1F469,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FB,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FC,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FD,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FE,0x200D,0x1F9BD},new int[]{0x1F469,0x1F3FF,0x200D,0x1F9BD},new int[]{0x1F3C3},new int[]{0x1F3C3,0x1F3FB},new int[]{0x1F3C3,0x1F3FC},new int[]{0x1F3C3,0x1F3FD},new int[]{0x1F3C3,0x1F3FE},new int[]{0x1F3C3,0x1F3FF},new int[]{0x1F3C3,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FB,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FC,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FD,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FE,0x200D,0x2642},new int[]{0x1F3C3,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3C3,0x1F3FF,0x200D,0x2642},new int[]{0x1F3C3,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FB,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FC,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FD,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FE,0x200D,0x2640},new int[]{0x1F3C3,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F3C3,0x1F3FF,0x200D,0x2640},new int[]{0x1F483},new int[]{0x1F483,0x1F3FB},new int[]{0x1F483,0x1F3FC},new int[]{0x1F483,0x1F3FD},new int[]{0x1F483,0x1F3FE},new int[]{0x1F483,0x1F3FF},new int[]{0x1F57A},new int[]{0x1F57A,0x1F3FB},new int[]{0x1F57A,0x1F3FC},new int[]{0x1F57A,0x1F3FD},new int[]{0x1F57A,0x1F3FE},new int[]{0x1F57A,0x1F3FF},new int[]{0x1F574,0xFE0F},new int[]{0x1F574},new int[]{0x1F574,0x1F3FB},new int[]{0x1F574,0x1F3FC},new int[]{0x1F574,0x1F3FD},new int[]{0x1F574,0x1F3FE},new int[]{0x1F574,0x1F3FF},new int[]{0x1F46F},new int[]{0x1F46F,0x200D,0x2642,0xFE0F},new int[]{0x1F46F,0x200D,0x2642},new int[]{0x1F46F,0x200D,0x2640,0xFE0F},new int[]{0x1F46F,0x200D,0x2640},new int[]{0x1F9D6},new int[]{0x1F9D6,0x1F3FB},new int[]{0x1F9D6,0x1F3FC},new int[]{0x1F9D6,0x1F3FD},new int[]{0x1F9D6,0x1F3FE},new int[]{0x1F9D6,0x1F3FF},new int[]{0x1F9D6,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FB,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FC,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FD,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FE,0x200D,0x2642},new int[]{0x1F9D6,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9D6,0x1F3FF,0x200D,0x2642},new int[]{0x1F9D6,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FB,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FC,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FD,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FE,0x200D,0x2640},new int[]{0x1F9D6,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9D6,0x1F3FF,0x200D,0x2640},new int[]{0x1F9D7},new int[]{0x1F9D7,0x1F3FB},new int[]{0x1F9D7,0x1F3FC},new int[]{0x1F9D7,0x1F3FD},new int[]{0x1F9D7,0x1F3FE},new int[]{0x1F9D7,0x1F3FF},new int[]{0x1F9D7,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FB,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FC,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FD,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FE,0x200D,0x2642},new int[]{0x1F9D7,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F9D7,0x1F3FF,0x200D,0x2642},new int[]{0x1F9D7,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FB,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FC,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FD,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FE,0x200D,0x2640},new int[]{0x1F9D7,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F9D7,0x1F3FF,0x200D,0x2640},new int[]{0x1F93A},new int[]{0x1F3C7},new int[]{0x1F3C7,0x1F3FB},new int[]{0x1F3C7,0x1F3FC},new int[]{0x1F3C7,0x1F3FD},new int[]{0x1F3C7,0x1F3FE},new int[]{0x1F3C7,0x1F3FF},new int[]{0x26F7,0xFE0F},new int[]{0x26F7},new int[]{0x1F3C2},new int[]{0x1F3C2,0x1F3FB},new int[]{0x1F3C2,0x1F3FC},new int[]{0x1F3C2,0x1F3FD},new int[]{0x1F3C2,0x1F3FE},new int[]{0x1F3C2,0x1F3FF},new int[]{0x1F3CC,0xFE0F},new int[]{0x1F3CC},new int[]{0x1F3CC,0x1F3FB},new int[]{0x1F3CC,0x1F3FC},new int[]{0x1F3CC,0x1F3FD},new int[]{0x1F3CC,0x1F3FE},new int[]{0x1F3CC,0x1F3FF},new int[]{0x1F3CC,0xFE0F,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0xFE0F,0x200D,0x2642},new int[]{0x1F3CC,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FB,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FC,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FD,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FE,0x200D,0x2642},new int[]{0x1F3CC,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3CC,0x1F3FF,0x200D,0x2642},new int[]{0x1F3CC,0xFE0F,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0xFE0F,0x200D,0x2640},new int[]{0x1F3CC,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FB,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FC,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FD,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FE,0x200D,0x2640},new int[]{0x1F3CC,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F3CC,0x1F3FF,0x200D,0x2640},new int[]{0x1F3C4},new int[]{0x1F3C4,0x1F3FB},new int[]{0x1F3C4,0x1F3FC},new int[]{0x1F3C4,0x1F3FD},new int[]{0x1F3C4,0x1F3FE},new int[]{0x1F3C4,0x1F3FF},new int[]{0x1F3C4,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FB,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FC,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FD,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FE,0x200D,0x2642},new int[]{0x1F3C4,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3C4,0x1F3FF,0x200D,0x2642},new int[]{0x1F3C4,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FB,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FC,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FD,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FE,0x200D,0x2640},new int[]{0x1F3C4,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F3C4,0x1F3FF,0x200D,0x2640},new int[]{0x1F6A3},new int[]{0x1F6A3,0x1F3FB},new int[]{0x1F6A3,0x1F3FC},new int[]{0x1F6A3,0x1F3FD},new int[]{0x1F6A3,0x1F3FE},new int[]{0x1F6A3,0x1F3FF},new int[]{0x1F6A3,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FB,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FC,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FD,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FE,0x200D,0x2642},new int[]{0x1F6A3,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F6A3,0x1F3FF,0x200D,0x2642},new int[]{0x1F6A3,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FB,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FC,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FD,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FE,0x200D,0x2640},new int[]{0x1F6A3,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F6A3,0x1F3FF,0x200D,0x2640},new int[]{0x1F3CA},new int[]{0x1F3CA,0x1F3FB},new int[]{0x1F3CA,0x1F3FC},new int[]{0x1F3CA,0x1F3FD},new int[]{0x1F3CA,0x1F3FE},new int[]{0x1F3CA,0x1F3FF},new int[]{0x1F3CA,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FB,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FC,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FD,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FE,0x200D,0x2642},new int[]{0x1F3CA,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3CA,0x1F3FF,0x200D,0x2642},new int[]{0x1F3CA,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FB,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FC,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FD,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FE,0x200D,0x2640},new int[]{0x1F3CA,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x1F3CA,0x1F3FF,0x200D,0x2640},new int[]{0x26F9,0xFE0F},new int[]{0x26F9},new int[]{0x26F9,0x1F3FB},new int[]{0x26F9,0x1F3FC},new int[]{0x26F9,0x1F3FD},new int[]{0x26F9,0x1F3FE},new int[]{0x26F9,0x1F3FF},new int[]{0x26F9,0xFE0F,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0xFE0F,0x200D,0x2642},new int[]{0x26F9,0x200D,0x2642},new int[]{0x26F9,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FB,0x200D,0x2642},new int[]{0x26F9,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FC,0x200D,0x2642},new int[]{0x26F9,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FD,0x200D,0x2642},
    new int[]{0x26F9,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FE,0x200D,0x2642},new int[]{0x26F9,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x26F9,0x1F3FF,0x200D,0x2642},new int[]{0x26F9,0xFE0F,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0xFE0F,0x200D,0x2640},new int[]{0x26F9,0x200D,0x2640},new int[]{0x26F9,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FB,0x200D,0x2640},new int[]{0x26F9,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FC,0x200D,0x2640},new int[]{0x26F9,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FD,0x200D,0x2640},new int[]{0x26F9,0x1F3FE,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FE,0x200D,0x2640},new int[]{0x26F9,0x1F3FF,0x200D,0x2640,0xFE0F},new int[]{0x26F9,0x1F3FF,0x200D,0x2640},new int[]{0x1F3CB,0xFE0F},new int[]{0x1F3CB},new int[]{0x1F3CB,0x1F3FB},new int[]{0x1F3CB,0x1F3FC},new int[]{0x1F3CB,0x1F3FD},new int[]{0x1F3CB,0x1F3FE},new int[]{0x1F3CB,0x1F3FF},new int[]{0x1F3CB,0xFE0F,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0xFE0F,0x200D,0x2642},new int[]{0x1F3CB,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FB,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FB,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FC,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FC,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FD,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FD,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FE,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FE,0x200D,0x2642},new int[]{0x1F3CB,0x1F3FF,0x200D,0x2642,0xFE0F},new int[]{0x1F3CB,0x1F3FF,0x200D,0x2642},new int[]{0x1F3CB,0xFE0F,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0xFE0F,0x200D,0x2640},new int[]{0x1F3CB,0x200D,0x2640},new int[]{0x1F3CB,0x1F3FB,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x1F3FB,0x200D,0x2640},new int[]{0x1F3CB,0x1F3FC,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x1F3FC,0x200D,0x2640},new int[]{0x1F3CB,0x1F3FD,0x200D,0x2640,0xFE0F},new int[]{0x1F3CB,0x1F3FD,0x200D,0x2640},new int[]{0x1F3CB,0x1F3FE,0x200D,0x2640,0xFE0F}};

  final private int[][] emojisAnimals = new int[][]{};
  final private int[][] emojisFood = new int[][]{};
  final private int[][] emojisSports = new int[][]{};
  final private int[][] emojisTravel = new int[][]{};
  final private int[][] emojisObjects = new int[][]{};
  final private int[][] emojisSymbols = new int[][]{};
  final private int[][] emojisFlags = new int[][]{};

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
      if (keyPressed) {
        MagicKeyboardService.this.onKeyLongPress(lastPressedKeyCode);
      }
    }
  };

  private String foregroundPackageName;

  private int currentEmojiCategory;
  // 0=recent 1=people 2=animals 3=food 4=sport 5=travel 6=objects 7=symbols 8=flags

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
    ic.sendKeyEvent(new KeyEvent(
      KeyEvent.ACTION_DOWN,
      keyCode));
  }

  private void sendKeyUp(final InputConnection ic, final int keyCode) {
    ic.sendKeyEvent(new KeyEvent(
      KeyEvent.ACTION_UP,
      keyCode));
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
          case Keyboard.KEYCODE_DONE:
            //
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
          final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
              case Keyboard.KEYCODE_DONE:
                //
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
//
//
//
//
//
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
                }
              }
            } else {
              ctrl = true;
              for (Keyboard.Key key : keys) {
                if (key.codes[0] == primaryCode) {
                  key.on = true;
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
                }
              }
            } else {
              alt = true;
              for (Keyboard.Key key : keys) {
                if (key.codes[0] == primaryCode) {
                  key.on = true;
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
              }
            }
            keyboardView.invalidateAllKeys();
            break;
          case -9994: // search
            //
            break;
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
                }
              }
            } else {
              shift = true;
              caps = false;
              keyboard.setShifted(true);
              for (Keyboard.Key key : keys) {
                if (key.codes[0] == primaryCode) {
                  key.on = true;
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
      case -9994: // search
      case -4:    // return
        sendKeyUp(
          getCurrentInputConnection(),
          KeyEvent.KEYCODE_ENTER);
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
