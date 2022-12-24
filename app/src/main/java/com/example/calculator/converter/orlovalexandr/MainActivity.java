package com.example.calculator.converter.orlovalexandr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ArrayList<RadioButton> listRBType;

    ArrayList<ArrayList<Unit>> listsUnits; //0 length, 1 square, 2 volume, 3 mass
    ArrayList<Unit> slctUnit;

    ArrayAdapter<String> adp;

    EditText etFrom;
    TextView tvTo;

    String unitsType;

    Spinner spFrom, spTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listRBType = new <RadioButton> ArrayList();
        listsUnits = new <ArrayList<Unit>> ArrayList(); //0 length, 1 square, 2 volume, 3 mass
        slctUnit = new ArrayList<Unit>();
        adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        unitsType = "len";

        listRBType.add(findViewById(R.id.rbLenght));
        listRBType.add(findViewById(R.id.rbSquare));
        listRBType.add(findViewById(R.id.rbVolume));
        listRBType.add(findViewById(R.id.rbMass));
        listRBType.get(0).setChecked(true);

        etFrom = findViewById(R.id.editTextFrom);
        tvTo = findViewById(R.id.editTextTo);

        spFrom = findViewById(R.id.spFrom);
        spTo = findViewById(R.id.spTo);

        for (int i = 0; i < 4; i++)
        {
            listsUnits.add(new ArrayList<Unit>());
            for (int j = 0; j < 4; j++)
                listsUnits.get(i).add(new Unit());
        }

        // len
        {
            listsUnits.get(0).get(0).coef = 0.001;
            listsUnits.get(0).get(0).name = "mm";
            listsUnits.get(0).get(1).coef = 0.01;
            listsUnits.get(0).get(1).name = "cm";
            listsUnits.get(0).get(2).coef = 1;
            listsUnits.get(0).get(2).name = "m";
            listsUnits.get(0).get(3).coef = 1000;
            listsUnits.get(0).get(3).name = "km";
        }
        // square
        {
            listsUnits.get(1).get(0).coef = 0.000001;
            listsUnits.get(1).get(0).name = "mm²";
            listsUnits.get(1).get(1).coef = 0.0001;
            listsUnits.get(1).get(1).name = "cm²";
            listsUnits.get(1).get(2).coef = 1;
            listsUnits.get(1).get(2).name = "m²";
            listsUnits.get(1).get(3).coef = 1000000;
            listsUnits.get(1).get(3).name = "km²";
        }
        // vol
        {
            listsUnits.get(2).get(0).coef = 0.000000001;
            listsUnits.get(2).get(0).name = "mm³";
            listsUnits.get(2).get(1).coef = 0.000001;
            listsUnits.get(2).get(1).name = "cm³";
            listsUnits.get(2).get(2).coef = 1;
            listsUnits.get(2).get(2).name = "m³";
            listsUnits.get(2).get(3).coef = 1000000000;
            listsUnits.get(2).get(3).name = "km³";
        }
        // mass
        {
            listsUnits.get(3).get(0).coef = 0.001;
            listsUnits.get(3).get(0).name = "mg";
            listsUnits.get(3).get(1).coef = 0.01;
            listsUnits.get(3).get(1).name = "g";
            listsUnits.get(3).get(2).coef = 1000;
            listsUnits.get(3).get(2).name = "kg";
            listsUnits.get(3).get(3).coef = 1000000;
            listsUnits.get(3).get(3).name = "t";
        }

        ChangeType();

        listRBType.get(0).setText(R.string.length);
        listRBType.get(1).setText(R.string.square);
        listRBType.get(2).setText(R.string.volume);
        listRBType.get(3).setText(R.string.mass);

    }
    public void onClickRB(View v)
    {
        RadioButton rb = (RadioButton) v;
        switch (rb.getId())
        {
            case R.id.rbLenght:
                unitsType = "len";
                ChangeType();
                break;
            case R.id.rbSquare:
                unitsType = "square";
                ChangeType();
                break;
            case R.id.rbVolume:
                unitsType = "vol";
                ChangeType();
                break;
            case R.id.rbMass:
                unitsType = "mass";
                ChangeType();
                break;
            default:
                break;
            }
        }

    public void Convert(View v) {
        double from, to = 0;
        try {
            from = Double.parseDouble(etFrom.getText().toString());
            to = from * slctUnit.get( (int)spFrom.getSelectedItemId() ).coef / slctUnit.get( (int)spTo.getSelectedItemId() ).coef;
        }
        catch (Exception e) {
            Toast tst = Toast.makeText(this, "Ошибка конвертации", Toast.LENGTH_SHORT);
            tst.show();
            return;
        }
        
        tvTo.setText(String.format("%.5f", to));
    }

    public void ChangeType()
    {
        switch (unitsType)
        {
            case "len":
                slctUnit = listsUnits.get(0);
                break;
            case "square":
                slctUnit = listsUnits.get(1);
                break;
            case "vol":
                slctUnit = listsUnits.get(2);
                break;
            case "mass":
                slctUnit = listsUnits.get(3);
                break;
            default:
                break;
        }
        adp.clear();
        for (int i = 0; i < 4; i++) adp.add(slctUnit.get(i).name);
        spFrom.setAdapter(adp);
        spTo.setAdapter(adp);
    }
}