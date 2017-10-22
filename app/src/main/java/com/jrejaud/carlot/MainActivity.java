package com.jrejaud.carlot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jrejaud.carlot.adapter.CarAdapter;
import com.jrejaud.carlot.model.Car;
import com.jrejaud.carlot.realm.CarLotInitialDataTransaction;

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
            //Create new car

            final Car car = new Car(Car.TOYOTA, "da", 1975);
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insert(car);
                    carAdapter.notifyDataSetChanged();
                }
            });
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
