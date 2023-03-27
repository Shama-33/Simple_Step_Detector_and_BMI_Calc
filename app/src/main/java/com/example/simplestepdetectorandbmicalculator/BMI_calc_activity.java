package com.example.simplestepdetectorandbmicalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class BMI_calc_activity extends AppCompatActivity {

    private EditText height,height_unit,weight,weight_unit;
    private TextView BMI;
    private Button button3;
    private String wunit,hunit;
    private   float h,w;
    DecimalFormat decimalFormat=new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi_calc);
        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);
        height_unit=findViewById(R.id.height_unit);
        weight_unit=findViewById(R.id.weight_unit);
        BMI=findViewById(R.id.BMI);
        button3=findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalculateBMI();
            }
        });
        weight_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectType();
            }
        });
        height_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTypeHeight();
            }
        });


    }

    private void selectTypeHeight() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("weight unit").setItems(UnitWeight.heightType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String C=UnitWeight.heightType [which];
                height_unit.setText(C);
                hunit=C;


            }
        }).show();
    }

    private void selectType() {


            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("weight unit").setItems(UnitWeight.weightType, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String C=UnitWeight.weightType [which];
                    weight_unit.setText(C);
                    wunit=C;


                }
            }).show();

    }

    private void CalculateBMI() {

        if(wunit.isEmpty() || hunit.isEmpty())
        {
            Toast.makeText(this, "Enter Unit", Toast.LENGTH_SHORT).show();
            return;
        }

        String heightstr=height.getText().toString();
        String weightstr=weight.getText().toString();
        if(heightstr.isEmpty() || weightstr.isEmpty())
        {
            Toast.makeText(this, "Enter value", Toast.LENGTH_SHORT).show();
            return;
        }


        if(!heightstr.isEmpty())
        {h= Integer.parseInt(heightstr);}
        if(!weightstr.isEmpty())
        {w= Integer.parseInt(weightstr);}
         if(h<0 || w<0)
         {
             return;
         }


        if(wunit.equals("pound"))
        {
            w*=0.45;

        }
        else if(wunit.equals("KG"))
        {
            w*=1;

        }
        else
        {
            return;
        }

        if(hunit.equals("inch"))
        {
            h*=0.0254;

        }
        else if(hunit.equals("foot"))
        {
            h*=0.3048;

        }
        else if(wunit.equals("meter"))
        {
            h*=1;

        }
        else
        {
            return;
        }

        double bmi;
        bmi=w/(h*h);
        String s=Double.toString(bmi);
        String c;

        if(bmi<18.0)
        {
            c=" (Underweight)";
        }
        else if(bmi<25.0)
        {
            c="(Optimal)";
        }
        else if (bmi<35.0)
        {
            c="(overweight)";
        }
        else
        {
            c="Risk zone";
        }

        BMI.setText("BMI : "+s+" ;"+c);

    }
}