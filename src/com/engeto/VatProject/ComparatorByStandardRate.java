package com.engeto.VatProject;

import java.util.Comparator;

public class ComparatorByStandardRate implements Comparator<Country> {

    @Override
    public int compare(Country country1, Country country2) {
        return  Double.compare(country1.getStandardRate(),country2.getStandardRate());

    }
}