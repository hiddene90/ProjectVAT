package com.engeto.VatProject;


import java.util.List;

import static com.engeto.VatProject.ListOfCountries.SEPARATOR;


public class Main {
    public static final String VAT = "vat-eu.csv";

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
        System.out.println(listOfCountries.result(20));
        System.out.println(SEPARATOR);


        //Zadání sazby z konzole
        System.out.println("Zadej sazbu: ");
        listOfCountries.resultWithAddedRateFromConsole();

    }
}