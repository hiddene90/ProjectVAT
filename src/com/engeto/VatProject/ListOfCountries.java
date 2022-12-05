package com.engeto.VatProject;

import java.io.*;
import java.util.*;


public class ListOfCountries {
    public static final String DELIMITER = "\t";
    static final String SEPARATOR = "====================";
    private static  double RATE = 20;


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

    public List<Country> getListOverRateSorted(double RATE) {
        List<Country> overRate = new ArrayList<>();
        for (Country country : countries
        ) {
            if (country.getStandardRate() > (double) RATE && !country.isSpecialRate())
                overRate.add(country);
        }
        overRate.sort(Collections.reverseOrder(new ComparatorByStandardRate()));
        return overRate;
    }
    public List<Country> getListBellowRate() {
        List<Country> bellowRate = new ArrayList<>();

        for (Country country: countries
        ) {
            if (country.getStandardRate() <= (double) RATE)
                bellowRate.add(country);
        }

            return bellowRate;
        }
    public String bellowRateListToString(){
        String result = "";
        for (Country country: getListBellowRate()
             ) {
              result += country.getShortcut()+", ";
        }
    return (result.length()>2)?result.substring(0,result.length()-2): result;
    }
    public String result(double RATE) {
        String result="";
        for (Country country : getListOverRateSorted(RATE)
        ) {
            result += country.getDescription()+"\n";
        }
        result+= SEPARATOR+"\nSazba VAT "+RATE+" % nebo nižší nebo používají speciální sazbu: "+ bellowRateListToString();

        return result;
    }
    public void resultWithAddedRateFromConsole () {
        try{
            Scanner scan = new Scanner(System.in);
           String input = scan.nextLine();
           input = input.replace(',', '.');


            if (input.isEmpty()) {
                RATE = 20;
                saveListToNewFile();
                System.out.println(result(RATE));
            }else {
                RATE = Double.parseDouble(input);
                saveListToNewFile();
                System.out.println(result(Double.parseDouble(input)));
                }

        }catch (NumberFormatException e){
            System.err.println("Špatně zadaná sazba: "+ e.getLocalizedMessage());

        }


    }

        public void saveListToNewFile () {
            try (
                    PrintWriter outputWriter = new PrintWriter(new FileWriter("vat-over-" + String.valueOf((int)RATE) + ".txt"))) {
                for (Country country : getListOverRateSorted(RATE)
                ) {
                    outputWriter.println(country.getDescription());
                }
                outputWriter.println(SEPARATOR);
                outputWriter.print("Sazba VAT "+RATE+" % nebo nižší nebo používají speciální sazbu: ");
                outputWriter.print(bellowRateListToString());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
