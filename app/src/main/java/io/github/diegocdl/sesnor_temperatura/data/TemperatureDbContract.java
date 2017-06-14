package io.github.diegocdl.sesnor_temperatura.data;

import android.provider.BaseColumns;

/**
 * Created by diego on 6/14/2017.
 */
public final class TemperatureDbContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TemperatureDbContract() {}

    /* Inner class that defines the table contents */
    public static class TempEntry implements BaseColumns {
        public static final String TABLE_NAME = "temperature_log";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_VAL = "value";
    }
}
