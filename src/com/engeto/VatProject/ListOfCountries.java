package com.engeto.VatProject;

import java.util.ArrayList;
import java.util.List;

public class ListOfCountries {
    List<Country> countries = new ArrayList<>();

    public ListOfCountries(List<Country> countries) {
        this.countries = countries;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
    public void addCountry(Country country){
        countries.add(country);
    }

}
