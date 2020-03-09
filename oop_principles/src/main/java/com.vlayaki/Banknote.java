package com.vlayaki;

import java.util.Comparator;

public enum Banknote implements Comparable<Banknote>{
    FIVE_THOUSANDS(5000),
    THOUSAND(1000),
    FIVE_HUNDREDS(500),
    HUNDRED(100),
    FIFTY(50);

    private int nominal;

    Banknote(int nominal) {
        this.nominal = nominal;
    }
    
    public int getNominal(){
        return nominal;
    }



}
