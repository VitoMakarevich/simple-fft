package com.example.vito.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                EditText numberEdit = (EditText) findViewById(R.id.number);
                int number = Integer.parseInt(numberEdit.getText().toString());
                if (isNoPowerOfTwo(number)) {
                    Toast.makeText(getApplicationContext(), "This number should be power of 2", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    GraphView graphAmpl = (GraphView) findViewById(R.id.graphAmpl); //setting layout
                    graphAmpl.removeAllSeries();
                    GraphView graphFreq = (GraphView) findViewById(R.id.graphFreq);
                    graphFreq.removeAllSeries();
                    GraphView graphInput = (GraphView) findViewById(R.id.graphInput);
                    graphInput.removeAllSeries();
                    RadioGroup randomiserGroup = (RadioGroup) findViewById(R.id.randomiserGroup);
                    Complex input[] = new Complex[number];
                    switch (getCheckedButtonIndex(randomiserGroup)) //setting input array
                    {
                        case 0:
                            for (int i = 0; i < number; i++) {
                                input[i] = new Complex(Math.cos(i), 0); //generating array
                            }
                            break;
                        case 1:
                            for (int i = 0; i < number; i++) {
                                input[i] = new Complex(Math.sin(i), 0); //generating array
                            }
                            break;
                        case 2:
                            for (int i = 0; i < number; i++) {
                                input[i] = new Complex(Math.random(), 0); //generating random array
                            }
                            break;
                    }
                    Complex result[] = FFT.fft(input); //FFT

                    DataPoint[] seriesInput = new DataPoint[number]; //setting first chart(ampl)
                    for (int i = 0; i < number; i++) {
                        seriesInput[i] = new DataPoint(i, input[i].re());
                    }
                    LineGraphSeries<DataPoint> seriesInputL = new LineGraphSeries<DataPoint>(seriesInput);
                    graphInput.addSeries(seriesInputL);

                    DataPoint[] seriesAmpl = new DataPoint[number]; //setting first chart(ampl)
                    for (int i = 0; i < number; i++) {
                        seriesAmpl[i] = new DataPoint(i, result[i].abs());
                    }
                    LineGraphSeries<DataPoint> seriesAmplL = new LineGraphSeries<DataPoint>(seriesAmpl);
                    graphAmpl.addSeries(seriesAmplL);


                    DataPoint[] seriesFreq = new DataPoint[number]; //setting second chart(freq)
                    for (int i = 0; i < number; i++) {
                        seriesFreq[i] = new DataPoint(i, result[i].phase());
                    }
                    LineGraphSeries<DataPoint> seriesFreqL = new LineGraphSeries<DataPoint>(seriesFreq);
                    graphFreq.addSeries(seriesFreqL);
                }
            }
        });

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
