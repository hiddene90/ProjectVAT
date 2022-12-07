package com.engeto.VatProject;

import java.io.*;
import java.util.*;

public class ListOfCountries {
    public static final String DELIMITER = "\t";
    static final String SEPARATOR = "====================";

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

    public List<Country> getList(double rate, boolean isOverRate) {
        List<Country> overRate = new ArrayList<>();
        List<Country> bellowRate = new ArrayList<>();
        for (Country country : countries
        ) {
            if (rate < country.getStandardRate() && !country.isSpecialRate()) {
                overRate.add(country);
            } else {
                bellowRate.add(country);
            }
            overRate.sort(Collections.reverseOrder(new ComparatorByStandardRate()));
        }
        if (isOverRate){
            return overRate;
        }else{
            return bellowRate;
        }
    }

    public String bellowRateListToString(double rate){
        String result = "";
        for (Country country: getList(rate,false)
        ) {
            result += country.getShortcut()+", ";
        }
        return (result.length()>2)?result.substring(0,result.length()-2): result;
    }
    public String result(double rate) {
        String result="";
        for (Country country : getList(rate,true)
        ) {
            result += country.getDescription() + "\n";
        }

        result += SEPARATOR + "\nSazba VAT " + rate + " % nebo nižší nebo používají speciální sazbu: " + bellowRateListToString(rate);

        return result;
    }
    public void resultWithAddedRateFromConsole () {
        try{
            Scanner scan = new Scanner(System.in);
            String input = scan.nextLine();
            input = input.replace(',', '.');

            double rate;
            if (input.isEmpty()) rate = 20;
            else rate = Double.parseDouble(input);
            saveListToNewFile(rate);
            System.out.println(result(rate));

        }catch (NumberFormatException e){
            System.err.println("Špatně zadaná sazba: "+ e.getLocalizedMessage());

        }
    }

    public void saveListToNewFile (double rate) {
        try (
                PrintWriter outputWriter = new PrintWriter(new FileWriter("vat-over-" +(int) rate + ".txt"))) {
            for (Country country : getList(rate,true)
            ) {
                outputWriter.println(country.getDescription());
            }
            outputWriter.println(SEPARATOR);
            outputWriter.print("Sazba VAT " + rate + " % nebo nižší nebo používají speciální sazbu: ");
            outputWriter.print(bellowRateListToString(rate));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
