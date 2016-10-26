package com.repitch.cursorlefttextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.repitch.cbhedittext.CursorBeforeHintEditText;

public class MainActivity extends AppCompatActivity {

    private CursorBeforeHintEditText et1;
    private CursorBeforeHintEditText et2;
    private TextView txt1;
    private TextView txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        et1 = (CursorBeforeHintEditText) findViewById(R.id.et1);
        et2 = (CursorBeforeHintEditText) findViewById(R.id.et2);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);

        et1.addTextChangedListener(new TextWatcher() {
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
                txt1.setText(String.format("'%s'", et1.getRealText()));
            }
        });
        et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(MainActivity.this, "et1 focus: "+hasFocus, Toast.LENGTH_SHORT).show();
            }
        });
        et2.addTextChangedListener(new TextWatcher() {
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
                txt2.setText(String.format("'%s'", et2.getRealText()));
            }
        });
    }
}
