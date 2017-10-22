package com.jrejaud.carlot.model;

import android.support.annotation.IntRange;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import io.realm.RealmObject;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by jrejaud on 10/22/17.
 */

public class Car extends RealmObject{
    private String make;
    private String model;
    private int year;

    public final static int MIN_YEAR = 1940;
    public final static int MAX_YEAR = 2018;

    public Car() {
    }

    public Car(@CarMake.Make String make, String model, @IntRange(from=MIN_YEAR, to=MAX_YEAR) int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    @IntRange(from=MIN_YEAR, to=MAX_YEAR)
    public int getYear() {
        return year;
    }

}
