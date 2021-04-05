package com.codemania.my_application_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class BMI_Result extends AppCompatActivity {

    TextView txv_result;
    TextView txv_classification;
    TextView txv_remarks;
    Button btn_return;
    String recData;

    String[] classifications = {"Body mass deficit",
                                "Normal body mass",
                                "Excessive body mass\n(pre-obesity)",
                                "Obesity 1st degree",
                                "Obesity 2nd degree",
                                "Obesity 3rd degree"};

    String[] remarks = {"Low\n(but higher risk of illnesses)",
                        "Average",
                        "Heightened",
                        "High",
                        "Very High",
                        "Extremely High"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi__result);

        init ();
    }

    public void init() {
        txv_result = findViewById(R.id.tv_result);
        txv_classification = findViewById(R.id.tv_classification);
        txv_remarks = findViewById(R.id.tv_remarks);
        btn_return = findViewById(R.id.btn_return);
        receiveData();
    }

    public void back(View v) {
        writeToFile(recData, getApplicationContext());
        Intent intent = new Intent(BMI_Result.this, MainActivity.class);
        startActivity(intent);
    }

    public void receiveData() {
        Intent recIntent = getIntent();

//        String recName = recIntent.getStringExtra("name");
//        int recAge = recIntent.getIntExtra("age", 0);
//        String recGender = recIntent.getStringExtra("gender");
//        float recHeight = recIntent.getFloatExtra("height", 0.0f);
//        float recWeight = recIntent.getFloatExtra("weight", 0.0f);
        float recResult = recIntent.getFloatExtra("result", 0.0f);
        recData = recIntent.getStringExtra("data");
        txv_result.setText(String.valueOf(recResult));

        if (recResult < 18.5) {
            txv_classification.setText(classifications[0]); // body mass deficit
            txv_remarks.setText(remarks[0]);
        } else if (recResult >= 18.5 && recResult <= 24.9) {
            txv_classification.setText(classifications[1]); // normal body mass
            txv_remarks.setText(remarks[1]);
        } else if (recResult >= 25 && recResult <= 29.9) {
            txv_classification.setText(classifications[2]); // excessive body mass
            txv_remarks.setText(remarks[2]);
        } else if (recResult >= 30 && recResult <= 34.9) {
            txv_classification.setText(classifications[3]); // obesity 1st degree
            txv_remarks.setText(remarks[3]);
        } else if (recResult >= 35 && recResult <= 39.9) {
            txv_classification.setText(classifications[4]); // obesity 2nd degree
            txv_remarks.setText(remarks[4]);
        } else {
            txv_classification.setText(classifications[5]); // obesity 3rd degree
            txv_remarks.setText(remarks[5]);
        }
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void writeDataInFile(String data) {
        File file = new File("src\\main\\java\\com\\codemania\\my_application_1\\bmi_result.txt");

        FileWriter fr = null;
        BufferedWriter br = null;

        try {
            fr = new FileWriter(file, true);
            br = new BufferedWriter(fr);
            br.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}