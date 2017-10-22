package com.jrejaud.carlot.model;

import android.support.annotation.IntRange;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by jrejaud on 10/22/17.
 */

public class CarMake {

    @Retention(SOURCE)
    @StringDef({
            TOYOTA,
            LEXUS,
            FORD,
            TESLA,
            VW
    })
    public @interface Make {}
    public static final String TOYOTA = "Toyota";
    public static final String LEXUS = "Lexus";
    public static final String TESLA = "Tesla";
    public static final String VW = "Volkswagen";
    public static final String FORD = "Ford";

    public static String[] getCarMakes() {
        return new String[]{TOYOTA, LEXUS, TESLA, VW, FORD};
    }
}
