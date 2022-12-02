package com.engeto.VatProject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListOfCountries {
    public static final String DELIMITER = "\t";
    public static final String RATE ="0";
    private static final String OUTPUT_FILE = "vat-over-"+RATE+".txt";
    List<Country> countries = new ArrayList<>();


    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public void addCountry(Country country) {
        countries.add(country);
    }

    public void readFromFile(String filename) throws CountryException {
        String nextLine = null;
        String[] items = new String[0];
        String shortcut = null;
        String name = null;
        double standartRate = 0;
        double reducedRate = 0;
        boolean specialRate = false;
        Country newCountry = null;
        int lineNumber = 0;

        try (Scanner scanner = new Scanner(new BufferedReader((new FileReader(filename))))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                nextLine = scanner.nextLine();
                items = nextLine.split(DELIMITER);
                shortcut = items[0];
                name = items[1];
                standartRate = Double.parseDouble(items[2].replace(",", "."));
                reducedRate = Double.parseDouble(items[3].replace(",", "."));
                specialRate = Boolean.parseBoolean(items[4]);

                newCountry = new Country(shortcut, name, standartRate, reducedRate, specialRate);
                addCountry(newCountry);
            }
        } catch (FileNotFoundException e) {
            throw new CountryException("Nepodařilo se najít soubor" + filename + ": " + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new CountryException("Zadán špatný formát daňové sazby na řádku: " + lineNumber);
        }
    }

    public void saveListToNewFile() {
        try (
                PrintWriter outputWriter = new PrintWriter(new FileWriter(OUTPUT_FILE))) {
            for (Country country : countries
            ) {
                outputWriter.println(country.getDescription());
//             outputWriter.println(country.getName() + "  (" + country.getShortcut() + "):\t" + country.ratesFormatted(country.getStandardRate()) + " %  (" + country.ratesFormatted(country.getReducedRate()) + " %)");

//                String standardRateForOutput = "";
//                String reducedRateForOutput = "";
//                if (Double.toString(country.getStandardRate()).contains(".0")) {
//                    standardRateForOutput = Double.toString(country.getStandardRate()).replace(".0", "");
//                } else {
//                    standardRateForOutput = Double.toString(country.getStandardRate());
//                }
//                if (Double.toString(country.getReducedRate()).contains(".0")) {
//                    reducedRateForOutput = Double.toString(country.getReducedRate()).replace(".0", "");
//                } else {
//                    reducedRateForOutput = Double.toString(country.getReducedRate());
//                }
//                    outputWriter.println(country.getName() + "  (" + country.getShortcut() + "):\t" + standardRateForOutput + " %  (" + reducedRateForOutput + " %)");
            }
            } catch(Exception e){
                e.printStackTrace();
            }

        }

    }
