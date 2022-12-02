package com.engeto.VatProject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static final String VAT = "vat-eu.csv";
    private static final String SEPARATOR = "--------------------------------\n";

    public static void main(String[] args) {
        ListOfCountries listOfCountries = new ListOfCountries();
        try {
             listOfCountries.readFromFile(VAT);
        } catch (CountryException e) {
            System.err.println("Chyba při čtení souboru: " + e.getLocalizedMessage());
        }


        List<Country> countries = listOfCountries.getCountries();
        //Výpis všech států
        countries.forEach(country -> System.out.println(country.getDescription()));


        // Výpis států se základní sazbou vyšší než 20% a nemají speciální daň
        System.out.println(SEPARATOR);
        List<Country> listForSorting = new ArrayList<>();
        for (Country country: countries
             ) {
            if (country.getStandardRate()>20.0 && !country.isSpecialRate()) {
               System.out.println(country.getDescription());
               listForSorting.add(country);
            }
        }
        //Seřazení předchozího Výpisu
        System.out.println(SEPARATOR);
        Collections.sort(listForSorting,Collections.reverseOrder(new ComparatorByStandardRate()));
        listForSorting.forEach(country -> System.out.println(country.getDescription()));

        listOfCountries.saveListToNewFile();


    }
}