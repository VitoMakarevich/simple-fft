package com.example.vito.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class PlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        Intent intent = getIntent();
        int mode = intent.getIntExtra("mode", 0);
        int number = intent.getIntExtra("seriesNumber", 0);
        GraphView graphAmpl = (GraphView) findViewById(R.id.graphAmpl); //setting layout
        graphAmpl.removeAllSeries();
        GraphView graphFreq = (GraphView) findViewById(R.id.graphFreq);
        graphFreq.removeAllSeries();
        GraphView graphInput = (GraphView) findViewById(R.id.graphInput);
        graphInput.removeAllSeries();
        Complex input[] = new Complex[number];
        switch (mode) //setting input array
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
