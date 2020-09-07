package buffermet.apk.magickeyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

class PromptService {
    static int waitGroup = 0;

    static class PromptButton {
        String text;
        Runnable action;
    }

    static void hideAllPrompts(final RelativeLayout container) {
        waitGroup = 0;

        container.animate().alpha(0).setDuration(300).setListener(null).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (waitGroup == 0) {
                    container.removeAllViews();
                    container.setVisibility(View.GONE);
                    container.setAlpha(0);
                }
            }
        }, 300);
    }

    static void hidePrompt(final RelativeLayout container) {
        if (waitGroup == 1) {
            waitGroup = 0;

            container.setOnClickListener(null);
            container.animate().alpha(0).setDuration(300).setListener(null).start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (waitGroup == 0) {
                        container.removeAllViews();
                        container.setVisibility(View.GONE);
                        container.setAlpha(0);
                    }
                }
            }, 300);
        } else if (waitGroup > 1) {
            waitGroup--;

            final int indexPrompt = container.getChildCount() - 1;
            final LinearLayout topPrompt = (LinearLayout) container.getChildAt(indexPrompt);
            topPrompt.setClickable(false);
            topPrompt.animate().translationY(-100).setDuration(300).setListener(null).start();
            topPrompt.animate().alpha(0).setDuration(300).setListener(null).start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (waitGroup == 0) {
                        container.removeViewAt(indexPrompt);
                    }
                }
            }, 300);
        } else {
            waitGroup = 0;

            container.animate().alpha(0).setDuration(300).setListener(null).start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    container.removeAllViews();
                    container.setVisibility(View.GONE);
                    container.setAlpha(0);
                }
            }, 300);
        }

    }

    static void showPrompt(final Context ctxt, final String title, final String message, final ArrayList<PromptButton> buttons, final RelativeLayout container) {
        waitGroup++;

        final Resources resources = ctxt.getResources();
        final float pixelDensity = resources.getDisplayMetrics().density;

        final LinearLayout linearLayoutPrompt = new LinearLayout(ctxt);
        final RelativeLayout.LayoutParams linearLayoutPromptParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutPromptParams.setMargins(
            (int) (16 * pixelDensity),
            (int) (16 * pixelDensity),
            (int) (16 * pixelDensity),
            (int) (16 * pixelDensity));
        linearLayoutPrompt.setLayoutParams(linearLayoutPromptParams);
        linearLayoutPrompt.setClickable(false);
        linearLayoutPrompt.setFocusable(false);
        linearLayoutPrompt.setBackgroundDrawable(ctxt.getResources().getDrawable(R.drawable.prompt_background));
        linearLayoutPrompt.setAlpha(0);

        final LinearLayout linearLayoutContent = new LinearLayout(ctxt);
        final LinearLayout.LayoutParams linearLayoutContentParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutContentParams.setMargins(
            (int) (24 * pixelDensity),
            (int) (24 * pixelDensity),
            (int) (24 * pixelDensity),
            (int) (24 * pixelDensity));
        linearLayoutContent.setLayoutParams(linearLayoutContentParams);
        linearLayoutContent.setOrientation(LinearLayout.VERTICAL);

        final LinearLayout linearLayoutWidthBar = new LinearLayout(ctxt);
        final LinearLayout.LayoutParams linearLayoutWidthBarParams = new LinearLayout.LayoutParams(
            (int) (320 * pixelDensity),
            LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutWidthBar.setLayoutParams(linearLayoutWidthBarParams);

        final TextView textViewTitle = new TextView(ctxt);
        final LinearLayout.LayoutParams textViewTitleParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewTitle.setLayoutParams(textViewTitleParams);
        textViewTitle.setMinWidth((int) (128 * pixelDensity));
        textViewTitle.setTextSize(18);
        textViewTitle.setTextColor(Color.parseColor("#777777"));
        textViewTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textViewTitle.setText(title);

        final TextView textViewMessage = new TextView(ctxt);
        final LinearLayout.LayoutParams textViewMessageParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewMessageParams.setMargins(
            0,
            (int) (24 * pixelDensity),
            0,
            (int) (24 * pixelDensity));
        textViewMessage.setLayoutParams(textViewMessageParams);
        textViewMessage.setTextSize(18);
        textViewMessage.setTextColor(Color.WHITE);
        textViewMessage.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        textViewMessage.setText(message);

        final LinearLayout linearLayoutButtonsContainer = new LinearLayout(ctxt);
        final LinearLayout.LayoutParams linearLayoutButtonsContainerParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutButtonsContainer.setLayoutParams(linearLayoutButtonsContainerParams);
        linearLayoutButtonsContainer.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutButtonsContainer.setGravity(Gravity.END);

        for (int i = 0; i < buttons.size(); i++) {
            final PromptButton thisButton = buttons.get(i);

            final RelativeLayout relativeLayoutButton = new RelativeLayout(ctxt);
            final RelativeLayout.LayoutParams relativeLayoutButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayoutButtonParams.setMargins(
                (int) (8 * pixelDensity),
                0,
                0,
                0);
            relativeLayoutButton.setLayoutParams(relativeLayoutButtonParams);
            if (Build.VERSION.SDK_INT >= 16) {
                relativeLayoutButton.setBackground(resources.getDrawable(R.drawable.button_filleted_invisible_light));
            } else {
                relativeLayoutButton.setBackgroundColor(Color.parseColor("#20ffffff"));
            }
            relativeLayoutButton.setGravity(Gravity.CENTER);
            relativeLayoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thisButton.action.run();
                }
            });
            relativeLayoutButton.setClickable(true);
            relativeLayoutButton.setFocusable(true);

            final TextView textView = new TextView(ctxt);
            final RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.setMargins(
                (int) (16 * pixelDensity),
                (int) (8 * pixelDensity),
                (int) (16 * pixelDensity),
                (int) (8 * pixelDensity));
            textView.setLayoutParams(textViewParams);
            textView.setTextSize(16);
            textView.setTextColor(Color.WHITE);
            textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            textView.setText(thisButton.text);

            relativeLayoutButton.addView(textView);

            linearLayoutButtonsContainer.addView(relativeLayoutButton);
        }

        linearLayoutContent.addView(textViewTitle);
        linearLayoutContent.addView(textViewMessage);
        linearLayoutContent.addView(linearLayoutButtonsContainer);

        linearLayoutPrompt.addView(linearLayoutContent);

        container.addView(linearLayoutPrompt);

        container.setBackgroundColor(Color.parseColor("#AA000000"));
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePrompt(container);
            }
        });
        container.setFocusable(false);
        container.setClickable(true);
        container.setAlpha(0);
        container.setVisibility(View.VISIBLE);

        container.animate().alpha(1).setDuration(300).setListener(null).start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayoutPrompt.animate().alpha(1).setDuration(300).setListener(null).start();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayoutPrompt.setClickable(true);
                    }
                }, 300);
            }
        }, 300);
    }

    static void showViewPrompt(final Context ctxt, final String title, final View viewMessage, final ArrayList<PromptButton> buttons, final RelativeLayout container) {
        waitGroup++;

        final Resources resources = ctxt.getResources();
        final float pixelDensity = resources.getDisplayMetrics().density;

        final LinearLayout linearLayoutPrompt = new LinearLayout(ctxt);
        final RelativeLayout.LayoutParams linearLayoutPromptParams = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutPromptParams.setMargins(
            (int) (16 * pixelDensity),
            (int) (16 * pixelDensity),
            (int) (16 * pixelDensity),
            (int) (16 * pixelDensity));
        linearLayoutPrompt.setLayoutParams(linearLayoutPromptParams);
        linearLayoutPrompt.setClickable(false);
        linearLayoutPrompt.setFocusable(false);
        linearLayoutPrompt.setBackgroundDrawable(
            ctxt.getResources().getDrawable(R.drawable.prompt_background));
        linearLayoutPrompt.setAlpha(0);

        final LinearLayout linearLayoutContent = new LinearLayout(ctxt);
        final LinearLayout.LayoutParams linearLayoutContentParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutContentParams.setMargins(
            (int) (24 * pixelDensity),
            (int) (24 * pixelDensity),
            (int) (24 * pixelDensity),
            (int) (24 * pixelDensity));
        linearLayoutContent.setLayoutParams(linearLayoutContentParams);
        linearLayoutContent.setOrientation(LinearLayout.VERTICAL);

        final TextView textViewTitle = new TextView(ctxt);
        final LinearLayout.LayoutParams textViewTitleParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewTitle.setLayoutParams(textViewTitleParams);
        textViewTitle.setMinWidth((int) (128 * pixelDensity));
        textViewTitle.setTextSize(18);
        textViewTitle.setTextColor(Color.parseColor("#777777"));
        textViewTitle.setTypeface(
            Typeface.create("sans-serif-medium", Typeface.NORMAL));
        textViewTitle.setText(title);

        final RelativeLayout relativeLayoutMessage = new RelativeLayout(ctxt);
        final LinearLayout.LayoutParams relativeLayoutMessageParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        relativeLayoutMessageParams.setMargins(
            0,
            (int) (16 * pixelDensity),
            0,
            (int) (16 * pixelDensity));
        relativeLayoutMessage.setLayoutParams(relativeLayoutMessageParams);
        relativeLayoutMessage.addView(viewMessage);

        final LinearLayout linearLayoutButtonsContainer = new LinearLayout(ctxt);
        final LinearLayout.LayoutParams linearLayoutButtonsContainerParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutButtonsContainer.setLayoutParams(linearLayoutButtonsContainerParams);
        linearLayoutButtonsContainer.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutButtonsContainer.setGravity(Gravity.END);

        for (int i = 0; i < buttons.size(); i++) {
            final PromptButton thisButton = buttons.get(i);

            final RelativeLayout relativeLayoutButton = new RelativeLayout(ctxt);
            final RelativeLayout.LayoutParams relativeLayoutButtonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayoutButtonParams.setMargins(
                (int) (8 * pixelDensity),
                0,
                0,
                0);
            relativeLayoutButton.setLayoutParams(relativeLayoutButtonParams);
            if (Build.VERSION.SDK_INT >= 16) {
                relativeLayoutButton.setBackground(
                    resources.getDrawable(R.drawable.button_filleted_invisible_light));
            } else {
                relativeLayoutButton.setBackgroundColor(Color.parseColor("#20ffffff"));
            }
            relativeLayoutButton.setGravity(Gravity.CENTER);
            relativeLayoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    thisButton.action.run();
                }
            });
            relativeLayoutButton.setClickable(true);
            relativeLayoutButton.setFocusable(true);

            final TextView textView = new TextView(ctxt);
            final RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.setMargins(
                (int) (16 * pixelDensity),
                (int) (8 * pixelDensity),
                (int) (16 * pixelDensity),
                (int) (8 * pixelDensity));
            textView.setLayoutParams(textViewParams);
            textView.setTextSize(16);
            textView.setTextColor(Color.WHITE);
            textView.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            textView.setText(thisButton.text);

            relativeLayoutButton.addView(textView);

            linearLayoutButtonsContainer.addView(relativeLayoutButton);
        }

        linearLayoutContent.addView(textViewTitle);
        linearLayoutContent.addView(relativeLayoutMessage);
        linearLayoutContent.addView(linearLayoutButtonsContainer);

        linearLayoutPrompt.addView(linearLayoutContent);

        container.addView(linearLayoutPrompt);

        container.setBackgroundColor(Color.parseColor("#AA000000"));
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePrompt(container);
            }
        });
        container.setFocusable(false);
        container.setClickable(true);
        container.setAlpha(0);
        container.setVisibility(View.VISIBLE);

        container.animate().alpha(1).setDuration(300).setListener(null).start();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayoutPrompt.animate().alpha(1).setDuration(300).setListener(null).start();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayoutPrompt.setClickable(true);
                    }
                }, 300);
            }
        }, 300);
    }
}
