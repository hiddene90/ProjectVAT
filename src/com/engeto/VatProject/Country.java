package com.engeto.VatProject;

public class Country {
    private String shortcut;
    private  String name;
    private double standardRate;
    private double reducedRate;
    private boolean specialRate;

    public Country(String shortcut, String name, double standardRate, double reducedRate, boolean specialRate) {
        this.shortcut = shortcut;
        this.name = name;
        this.standardRate = standardRate;
        this.reducedRate = reducedRate;
        this.specialRate = specialRate;
    }
    ///region
    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(double standardRate) throws CountryException {
        if(standardRate<0){
            throw new CountryException("Daňová sazba nesmí být záporná! Zadaná hodnota: "+standardRate);
        }
        this.standardRate = standardRate;
    }

    public double getReducedRate() {
        return reducedRate;
    }

    public void setReducedRate(double reducedRate) throws CountryException {
        if(reducedRate<0){
            throw new CountryException("Daňová sazba nesmí být záporná! Zadaná hodnota: "+reducedRate);
        }
        this.reducedRate = reducedRate;
    }

    public boolean isSpecialRate() {
        return specialRate;
    }

    public void setSpecialRate(boolean specialRate) {
        this.specialRate = specialRate;
    }
    ///endregion
    public String getDescription(){

//        return name+ " ("+shortcut+"): "+standardRate+ " %";
        return getName() + "  (" + getShortcut() + "):\t" + ratesFormatted(getStandardRate()) + " %  (" + ratesFormatted(getReducedRate()) + " %)";
    }

    public String ratesFormatted(double rate){
        return Double.toString(rate).replace(".0", "");

    }

}
