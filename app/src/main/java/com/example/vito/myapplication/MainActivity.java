package com.example.vito.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        EditText numberEdit = (EditText) findViewById(R.id.number);
        int number = Integer.parseInt(numberEdit.getText().toString());
        if (isNoPowerOfTwo(number)) {
            Toast.makeText(getApplicationContext(), "This number should be power of 2", Toast.LENGTH_SHORT).show();
            return;
        } else {

            RadioGroup randomiserGroup = (RadioGroup) findViewById(R.id.randomiserGroup);
            Intent intent = new Intent(this, PlotActivity.class);
            intent.putExtra("mode", getCheckedButtonIndex(randomiserGroup));
            intent.putExtra("seriesNumber", number);
            startActivity(intent);
        }
    }
    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private boolean isNoPowerOfTwo(int number) { //this is used for filter input data
        if (!((number > 0) && (number & (number - 1)) == 0) || number == 1 || number == 0)
            return true;
        else return false;
    }

    private int getCheckedButtonIndex(RadioGroup radioGroup) {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        return radioGroup.indexOfChild(radioButton);
    }
}
