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

    public Car() {
    }

    public Car(@CarMake String make, String model, @IntRange(from=1940, to=2018) int year) {
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

    @IntRange(from=1990, to=2018)
    public int getYear() {
        return year;
    }

    @Retention(SOURCE)
    @StringDef({
            TOYOTA,
            LEXUS,
            FORD,
            TESLA,
            VW
    })
    public @interface CarMake {}
    public static final String TOYOTA = "Toyota";
    public static final String LEXUS = "Lexus";
    public static final String TESLA = "Tesla";
    public static final String VW = "Volkswagen";
    public static final String FORD = "Ford";


}
