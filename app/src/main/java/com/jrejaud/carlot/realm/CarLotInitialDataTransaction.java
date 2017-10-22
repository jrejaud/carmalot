package com.jrejaud.carlot.realm;

import com.jrejaud.carlot.model.Car;

import io.realm.Realm;

/**
 * Created by jrejaud on 10/22/17.
 */

public class CarLotInitialDataTransaction implements Realm.Transaction {
    @Override
    public void execute(Realm realm) {
        Car car = new Car(Car.TOYOTA, "Corolla", 2000);
        realm.insertOrUpdate(car);
        car = new Car(Car.LEXUS, "LE", 2006);
        realm.insertOrUpdate(car);
        car = new Car(Car.FORD, "Mustand", 1968);
        realm.insertOrUpdate(car);
        car = new Car(Car.TESLA, "X", 2016);
        realm.insertOrUpdate(car);
        car = new Car(Car.TOYOTA, "Corolla", 1999);
        realm.insertOrUpdate(car);
        car = new Car(Car.TOYOTA, "High Lander", 2010);
        realm.insertOrUpdate(car);
        car = new Car(Car.TESLA, "X", 2016);
        realm.insertOrUpdate(car);
        car = new Car(Car.VW, "Golf", 2012);
        realm.insertOrUpdate(car);
    }
}
