package com.jrejaud.carlot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.jrejaud.carlot.adapter.CarAdapter;
import com.jrejaud.carlot.model.Car;
import com.jrejaud.carlot.model.CarMake;
import com.jrejaud.carlot.realm.CarLotInitialDataTransaction;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    private CarAdapter carAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Get a Realm instance for this thread
        realm = Realm.getInstance(config);
        carAdapter = new CarAdapter(this, realm.where(Car.class).findAll());
        ListView carlist = (ListView) findViewById(R.id.car_list);
        carlist.setAdapter(carAdapter);

        //On item click, delete the car
        carlist.setOnItemClickListener(carListOnItemClick);

        //Create New Car
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_car);
        fab.setOnClickListener(addCarButton);
    }

    private RealmConfiguration config = new RealmConfiguration.Builder()
            .name("carlot.realm")
            .initialData(new CarLotInitialDataTransaction())
            .build();

    //Create a new car
    private View.OnClickListener addCarButton = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Show new car dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.create_new_car));
            View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_add_car, null);
            builder.setView(dialogView);

            //Set list of auto complete options for the make
            AutoCompleteTextView autoCompleteTextView = dialogView.findViewById(R.id.make);
            ArrayAdapter<String> makeAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, CarMake.getCarMakes());
            autoCompleteTextView.setAdapter(makeAdapter);

            builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Override later below
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Don't do anything
                }
            });

            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            @CarMake.Make String make = ((EditText)dialog.findViewById(R.id.make)).getText().toString();
                            String model = ((EditText)dialog.findViewById(R.id.model)).getText().toString();

                            //Check if make is valid
                            if (!Arrays.asList(CarMake.getCarMakes()).contains(make)) {
                                Toast.makeText(MainActivity.this, getString(R.string.error_make), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //Check if model is valid
                            if (model.isEmpty()) {
                                Toast.makeText(MainActivity.this, getString(R.string.error_model), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //Check if year is valid
                            int year = Integer.valueOf(((EditText)dialog.findViewById(R.id.year)).getText().toString());
                            if (year < Car.MIN_YEAR || year > Car.MAX_YEAR) {
                                Toast.makeText(MainActivity.this, getString(R.string.error_date)+" "+Car.MIN_YEAR+" and "+Car.MAX_YEAR, Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //Create a car object
                            final Car car = new Car(make, model, year);
                            dialog.findViewById(R.id.year);
                            realm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    realm.insert(car);
                                    carAdapter.notifyDataSetChanged();
                                    //Dismiss once everything is OK.
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                }
            });
            dialog.show();
        }
    };

    //Delete a car
    private AdapterView.OnItemClickListener carListOnItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //Confirmation Dialog
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle(getString(R.string.delete_title));
            final Car car = carAdapter.getItem(i);
            dialog.setMessage(getString(R.string.delete_message)+"\n"+car.getMake()+" "+car.getModel()+" "+car.getYear()+"?");
            dialog.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            car.deleteFromRealm();
                            carAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
            dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //Don't do anything
                }
            });
            dialog.show();
        }
    };


}
