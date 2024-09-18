package com.myaconsultancy.geodatadetails.entity;

import android.location.Address;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Locale;

public class GeoDataDetail extends Address{

    /**
     * Constructs a new Address object set to the given Locale and with all
     * other fields initialized to null or false.
     *
     * @param locale
     */
    public GeoDataDetail(Locale locale) {
        super(locale);
    }
}
