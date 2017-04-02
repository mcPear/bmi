package com.lab.gruszczynski.laboratory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BMIActivity extends AppCompatActivity {


    @BindView(R.id.button_countBMI)
    Button countBMIButton;
    @BindView(R.id.editText_mass)
    EditText massEditText;
    @BindView(R.id.editText_height)
    EditText heightEditText;
    @BindView(R.id.textView_result)
    TextView resultTextView;
    @BindView(R.id.textView_message)
    TextView messageTextView;
    @BindView(R.id.textView_bmi)
    TextView bmiTextView;
    @BindView(R.id.spinner)
    Spinner spinner;
    private ICountBMI iCountBMI;
    private CountBMIForKgM countBMIForKgM;
    private CountBMIForLbsFt countBMIForLbsFt;
    private Boolean BMIcalculated=false;
    private float BMIresult=0f;
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_main);
        ButterKnife.bind(this);

        countBMIForKgM = new CountBMIForKgM();
        countBMIForLbsFt = new CountBMIForLbsFt();
        iCountBMI = countBMIForKgM;

        bmiTextView.setVisibility(View.GONE);

        setSavedEditTextsValues();

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
                        try {
                            BMIresult = iCountBMI.countBMI(Float.parseFloat(massEditText.getText().toString()), Float.parseFloat(heightEditText.getText().toString()));
                            showResultMessages();
                            BMIcalculated = true;
                        }
                        catch(NumberFormatException e){
                            e.printStackTrace();
                            showExceptionMessage("Wrong entry");
                            BMIcalculated = false;
                        }
                        catch(IllegalArgumentException e){
                            e.printStackTrace();
                            showExceptionMessage(e.getMessage());
                            BMIcalculated = false;
                        }
                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        savedInstanceState.putBoolean("BMIcalculated", BMIcalculated);
        if(BMIcalculated) {
            savedInstanceState.putFloat("result", BMIresult);
        }
        else{
            savedInstanceState.putString("noResultMessage",messageTextView.getText().toString());
        }

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        BMIcalculated = savedInstanceState.getBoolean("BMIcalculated");

        if(BMIcalculated){
            BMIresult = savedInstanceState.getFloat("result");
            showResultMessages();
        }
        else{
            messageTextView.setText(savedInstanceState.getString("noResultMessage"));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getTitle().toString()) {
            case "Save":
                if(BMIcalculated){
                    sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putFloat(getString(R.string.mass),Float.parseFloat(massEditText.getText().toString()));
                    editor.putFloat(getString(R.string.height),Float.parseFloat(heightEditText.getText().toString()));
                    editor.commit();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Count first, please", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case "Share":
                if(BMIcalculated){
                    String messageText = messageTextView.getText().toString();
                    String shareText = "Result: Your BMI is "+String.format("%.2f", BMIresult)+" and "+messageText+" !";
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    shareIntent.setType("text/plain");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Count first, please", Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case "Author":
                startActivity(new Intent(this, AuthorInfo.class));
                break;
        }

        return true;
    }

    private void showExceptionMessage(String message){
        bmiTextView.setVisibility(View.GONE);
        resultTextView.setVisibility(View.GONE);
        messageTextView.setText(message);
    }

    private void showResultMessages(){
        bmiTextView.setVisibility(View.VISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        resultTextView.setText(((Float)BMIresult).toString());
        setResultTextColor(BMIresult);
        messageTextView.setVisibility(View.VISIBLE);
        setMessageText(BMIresult);
    }

    private void setResultTextColor(float bmiValue){
        if (bmiValue<18.5) resultTextView.setTextColor(Color.BLUE);
        else if (bmiValue<25) resultTextView.setTextColor(getColor(R.color.green));
        else if (bmiValue<30) resultTextView.setTextColor(getColor(R.color.orange));
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

    private void setSavedEditTextsValues(){
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        massEditText.setText(((Float)sharedPref.getFloat(getString(R.string.mass),0f)).toString());
        heightEditText.setText(((Float)sharedPref.getFloat(getString(R.string.height),0f)).toString());
    }



}
