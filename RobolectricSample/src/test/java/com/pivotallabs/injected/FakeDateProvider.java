package com.pivotallabs.injected;

import com.google.inject.Provider;
import com.google.inject.Singleton;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

@Singleton
public class FakeDateProvider implements Provider<Date> {
    private Date date = new Date();

    @Override public Date get() {
        return date;
    }

    public void setDate(String dateString) {
        try {
            date = DateFormat.getDateInstance(DateFormat.LONG, Locale.US).parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("bad date!!");
        }
    }
}
