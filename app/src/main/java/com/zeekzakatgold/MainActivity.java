package com.zeekzakatgold;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText edWeight, edPrice;
    private Spinner spinner;
    private Button btnCalculate, btnReset;
    private TextView tvAboutUs;

    private final int X_WEAR = 200;
    private final int X_KEEP = 85;

    private final String KEEP = "Keep";
    private final String WEAR = "Wear";

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate (R.menu.menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.tv_about_us:
                //Toast.makeText(this,"About Us",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AboutUsActivity.class);
                startActivity(intent);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String getWeight = sharedPref.getString("weight", "");
        String getPrice = sharedPref.getString("price", "");

        edWeight = findViewById(R.id.edWeight);
        edPrice = findViewById(R.id.edPrice);

        edWeight.setText(getWeight);
        edPrice.setText(getPrice);

        spinner = findViewById(R.id.spinner);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);

        DecimalFormat df = new DecimalFormat("##,###,###.00");


        String[] items = new String[]{KEEP, WEAR};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);


        btnCalculate.setOnClickListener(view -> {

            if (edWeight.getText().toString().length() == 0) {
                edWeight.setError("Please input weight in number.");
                Toast.makeText(this, "Missing input", Toast.LENGTH_SHORT).show();
                return;
            }

            if (edPrice.getText().toString().length() == 0) {
                edPrice.setError("Please input price in number");
                Toast.makeText(this, "Missing input", Toast.LENGTH_SHORT).show();
                return;
            }


            double weight = Double.parseDouble(edWeight.getText().toString());
            double price = Double.parseDouble(edPrice.getText().toString());

            int xValue;
            String spinValue = spinner.getSelectedItem().toString();
            if (spinValue.equals(KEEP)) {
                xValue = X_KEEP;
            } else if (spinValue.equals(WEAR)) {
                xValue = X_WEAR;
            } else {
                xValue = 0;
            }

            double totalValueOfGold = weight * price;
            double uruf = weight - xValue;
            double zakatPayable = uruf <= 0 ? 0 : price * uruf;
            double totalZakat = zakatPayable * 0.025;


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Result\n");
            builder.setMessage("" +
                    "Total Value of Gold : RM " + df.format(totalValueOfGold) +
                    "\n\nZakat Payable : RM " + df.format(zakatPayable) +
                    "\n\nUruf : RM " + df.format(uruf) +
                    "\n\nTotal Zakat : RM " + df.format(totalZakat));

            // add the buttons
            builder.setPositiveButton("Continue", null);

            // create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("weight", String.valueOf(weight));
            editor.putString("price", String.valueOf(price));
            editor.apply();

        });

        btnReset.setOnClickListener(view ->{
            edWeight.setText("");
            edPrice.setText("");

            return;
        });

    }
}