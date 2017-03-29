package com.lab.gruszczynski.laboratory;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    Button countBMIButton;
    EditText massEditText;
    EditText heightEditText;
    TextView resultTextView;
    TextView messageTextView;
    TextView bmiTextView;
    Spinner spinner;
    ICountBMI iCountBMI;
    CountBMIForKgM countBMIForKgM;
    CountBMIForLbsFt countBMIForLbsFt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_main);

        countBMIButton = (Button)findViewById(R.id.button_countBMI);
        massEditText   = (EditText)findViewById(R.id.editText_mass);
        heightEditText = (EditText)findViewById(R.id.editText_height);
        resultTextView = (TextView)findViewById(R.id.textView_result);
        messageTextView = (TextView)findViewById(R.id.textView_message);
        bmiTextView = (TextView)findViewById(R.id.textView_bmi);
        spinner = (Spinner) findViewById(R.id.spinner);
        countBMIForKgM = new CountBMIForKgM();
        countBMIForLbsFt = new CountBMIForLbsFt();
        iCountBMI = countBMIForKgM;

        bmiTextView.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.measures_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: {
                        iCountBMI=countBMIForKgM;
                        Log.d("@@@@@",iCountBMI.toString());
                        break;
                    }
                    case 1: {
                        iCountBMI=countBMIForLbsFt;
                        Log.d("@@@@@",iCountBMI.toString());
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countBMIButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Float countResult = null;
                        try {
                            countResult = iCountBMI.countBMI(Float.parseFloat(massEditText.getText().toString()), Float.parseFloat(heightEditText.getText().toString()));
                            resultTextView.setText(countResult.toString());
                            setResultTextColor(countResult);
                            setMessageText(countResult);
                            bmiTextView.setVisibility(View.VISIBLE);
                        }
                        catch(IllegalArgumentException e){
                            e.printStackTrace();
                            resultTextView.setText(e.getMessage());
                        }
                    }
                });
    }

    private void setResultTextColor(float bmiValue){
        if (bmiValue<18.5) resultTextView.setTextColor(Color.BLUE);
        else if (bmiValue<25) resultTextView.setTextColor(Color.GREEN);
        else if (bmiValue<30) resultTextView.setTextColor(Color.argb(255,255,165,0));
        else if (bmiValue<35) resultTextView.setTextColor(Color.RED);
        else resultTextView.setTextColor(Color.BLACK);
    }

    private void setMessageText(float bmiValue){
        if (bmiValue<18.5) messageTextView.setText(R.string.underwieght);
        else if (bmiValue<25) messageTextView.setText(R.string.normal);
        else if (bmiValue<30) messageTextView.setText(R.string.overweight);
        else if (bmiValue<35) messageTextView.setText(R.string.obese);
        else messageTextView.setText(R.string.clinically_obese);
    }

}
