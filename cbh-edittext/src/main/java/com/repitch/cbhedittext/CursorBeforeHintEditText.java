package com.repitch.cbhedittext;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author i.s.pyavkin
 */
public class CursorBeforeHintEditText extends EditText {

    private boolean selectionBlocked;
    private int savedInputType;

    private ColorStateList savedTextColors;

    private boolean hintSet;
    private boolean innerSetText;
    private boolean innerSetTextColor;

    public CursorBeforeHintEditText(Context context) {
        super(context);
        init();
    }

    public CursorBeforeHintEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CursorBeforeHintEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            processText(getText());
        }
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        if (selectionBlocked) {
            setSelection(0);
        } else {
            super.onSelectionChanged(selStart, selEnd);
        }
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        if (innerSetTextColor) {
            innerSetTextColor = false;
            super.setTextColor(colors);
        } else {
            savedTextColors = colors;
            super.setTextColor(hintSet ? getHintTextColors() : colors);
        }
    }

    public Editable getRealText() {
        Editable text = getText();
        if (hintSet) {
            return cutoffHint(text);
        } else {
            return text;
        }
    }

    private void init() {
        savedTextColors = getTextColors();
        savedInputType = getInputType();
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // empty
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (innerSetText) {
                    innerSetText = false;
                } else {
                    processText(s);
                }
            }
        });
    }

    private Editable cutoffHint(Editable text) {
        CharSequence hint = getHint();
        String textStr = text.toString().replace(hint, "");
        return new SpannableStringBuilder(textStr);
    }

    private void processText(Editable s) {
        String str = s.toString();
        CharSequence hint = getHint();
        if (str.isEmpty()) {
            hintSet = true;
            setTextColorInner(getHintTextColors());
            blockSelection(true);
            setTextInner(hint);
            setSelection(0);
        } else if (hintSet && str.contains(hint) && !str.equals(hint)) {
            hintSet = false;
            setTextColorInner(savedTextColors);
            blockSelection(false);
            String newStr = str.replace(hint, "");
            setTextInner(newStr);
            setSelection(newStr.length());
        }
    }

    private void setTextInner(CharSequence text) {
        innerSetText = true;
        setText(text);
    }

    private void setTextColorInner(ColorStateList colors) {
        innerSetTextColor = true;
        setTextColor(colors);
    }

    private void blockSelection(boolean block) {
        selectionBlocked = block;
        setInputType(selectionBlocked ? InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS : savedInputType);
    }
}
