package com.jrejaud.carlot.realm;

import com.jrejaud.carlot.model.Car;
import com.jrejaud.carlot.model.CarMake;

import io.realm.Realm;

/**
 * Created by jrejaud on 10/22/17.
 */

public class CarLotInitialDataTransaction implements Realm.Transaction {
    @Override
    public void execute(Realm realm) {
        Car car = new Car(CarMake.TOYOTA, "Corolla", 2000);
        realm.insertOrUpdate(car);
        car = new Car(CarMake.LEXUS, "LE", 2006);
        realm.insertOrUpdate(car);
        car = new Car(CarMake.FORD, "Mustand", 1968);
        realm.insertOrUpdate(car);
        car = new Car(CarMake.TESLA, "X", 2016);
        realm.insertOrUpdate(car);
        car = new Car(CarMake.TOYOTA, "Corolla", 1999);
        realm.insertOrUpdate(car);
        car = new Car(CarMake.TOYOTA, "High Lander", 2010);
        realm.insertOrUpdate(car);
        car = new Car(CarMake.TESLA, "X", 2016);
        realm.insertOrUpdate(car);
        car = new Car(CarMake.VW, "Golf", 2012);
        realm.insertOrUpdate(car);
    }
}
