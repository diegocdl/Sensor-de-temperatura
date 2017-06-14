package io.github.diegocdl.sesnor_temperatura.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by diego on 6/6/2017.
 */

public class TempRegister {
    protected static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    protected static DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    protected String date;
    protected String time;
    protected String value;

    public TempRegister(String value) {

        this.date = dateFormat.format(new Date());
        this.time = timeFormat.format(new Date());
        this.value = value;
    }

    public TempRegister(String date, String time, String value) {
        this.date = date;
        this.time = time;
        this.value = value;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", date, time, value);
    }
}
