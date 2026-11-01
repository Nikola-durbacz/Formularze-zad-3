
package com.example.formularzezad3;


import androidx.appcompat.app.AlertDialog;
import com.google.android.material.snackbar.Snackbar;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.materialswitch.MaterialSwitch;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MaterialSwitch swDelivery;
    private EditText etAddress;
    private EditText etName;
    private Spinner spPizza;
    private RadioGroup rgSize;
    private CheckBox cbCheese, cbHam, cbMushrooms;
    private SeekBar sbQuantity;
    private TextView tvQuantity, tvSummary;
    private Button btnOrder;

    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectViews();
        setupPizzaSpinner();
        setupQuantity();
        setupListeners();
    }

    private void connectViews() {
        swDelivery = findViewById(R.id.swDelivery);
        etAddress = findViewById(R.id.etAddress);
        etName = findViewById(R.id.etName);
        spPizza = findViewById(R.id.spPizza);
        rgSize = findViewById(R.id.rgSize);
        cbCheese = findViewById(R.id.cbCheese);
        cbHam = findViewById(R.id.cbHam);
        cbMushrooms = findViewById(R.id.cbMushrooms);
        sbQuantity = findViewById(R.id.sbQuantity);
        tvQuantity = findViewById(R.id.tvQuantity);
        tvSummary = findViewById(R.id.tvSummary);
        btnOrder = findViewById(R.id.btnOrder);
    }

    private void setupPizzaSpinner() {
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.pizzas,
                        android.R.layout.simple_spinner_item
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spPizza.setAdapter(adapter);
    }

    private void setupQuantity() {
        sbQuantity.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar, int progress, boolean fromUser) {
                        quantity = progress + 1;
                        tvQuantity.setText("Ilość: " + quantity);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                }
        );
    }

    private void setupListeners() {
        btnOrder.setOnClickListener(v -> showConfirmation());
        swDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etAddress.setVisibility(View.VISIBLE);
            } else {
                etAddress.setVisibility(View.GONE);
                etAddress.setText("");
            }
        });

    }
    private void showConfirmation() {

        if (!validateOrder()) {
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Potwierdzenie zamówienia")
                .setMessage("Czy na pewno chcesz złożyć zamówienie?")
                .setNegativeButton("ANULUJ", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("POTWIERDZAM", (dialog, which) -> showOrderSummary())
                .show();
    }
    private boolean validateOrder() {

        String name = etName.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Wpisz imię i nazwisko");
            etName.requestFocus();
            return false;
        }

        etName.setError(null);

        if (spPizza.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Wybierz pizzę", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (rgSize.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Wybierz rozmiar pizzy", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (swDelivery.isChecked()) {
            String address = etAddress.getText().toString().trim();

            if (address.isEmpty()) {
                etAddress.setError("Wpisz adres dostawy");
                etAddress.requestFocus();
                return false;
            }

            etAddress.setError(null);
        }

        return true;
    }

    private void showOrderSummary() {

        if (!validateOrder()) {
            return;
        }

        String name = etName.getText().toString().trim();
        String pizza = spPizza.getSelectedItem().toString();

        String size = "Nie wybrano";
        int selectedSize = rgSize.getCheckedRadioButtonId();

        if (selectedSize == R.id.rbSmall) {
            size = "Mała";
        } else if (selectedSize == R.id.rbMedium) {
            size = "Średnia";
        } else if (selectedSize == R.id.rbLarge) {
            size = "Duża";
        }

        String toppings = "";

        if (cbCheese.isChecked()) {
            toppings += "Dodatkowy ser, ";
        }

        if (cbHam.isChecked()) {
            toppings += "Szynka, ";
        }

        if (cbMushrooms.isChecked()) {
            toppings += "Pieczarki, ";
        }

        if (toppings.isEmpty()) {
            toppings = "Brak";
        }

        String summary =
                "Zamówienie:\n" +
                        "Imię: " + name + "\n" +
                        "Pizza: " + pizza + "\n" +
                        "Rozmiar: " + size + "\n" +
                        "Dodatki: " + toppings + "\n" +
                        "Ilość: " + quantity;

        tvSummary.setText(summary);
        Snackbar.make(
                findViewById(R.id.main),
                "Zamówienie zostało złożone",
                Snackbar.LENGTH_LONG
        ).setAction("COFNIJ", v -> {
            tvSummary.setText("");
            Toast.makeText(this, "Zamówienie zostało cofnięte", Toast.LENGTH_SHORT).show();
        }).show();
    }
}
