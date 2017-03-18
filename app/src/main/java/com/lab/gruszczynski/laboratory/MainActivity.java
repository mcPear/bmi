package com.lab.gruszczynski.laboratory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button countBMIButton;
    EditText massEditText;
    EditText heightEditText;
    TextView resultTextView;
    ICountBMI iCountBMI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countBMIButton = (Button)findViewById(R.id.button_countBMI);
        massEditText   = (EditText)findViewById(R.id.editText_mass);
        heightEditText = (EditText)findViewById(R.id.editText_height);
        resultTextView = (TextView)findViewById(R.id.textView_result);
        iCountBMI = new CountBMIForKgM();

        countBMIButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        Float countResult = null;
                        try {
                            countResult = iCountBMI.countBMI(Float.parseFloat(massEditText.getText().toString()), Float.parseFloat(heightEditText.getText().toString()));
                            resultTextView.setText(countResult.toString());
                        }
                        catch(IllegalArgumentException e){
                            e.printStackTrace();
                            resultTextView.setText(e.getMessage());
                        }
                    }
                });
    }
}
