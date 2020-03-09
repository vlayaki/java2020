package com.vlayaki;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BanknoteTest {

    @Test
    public void banknotes_should_be_enumerated_in_descending_order() {
        Banknote prev = null;
        for (Banknote banknote : Banknote.values()) {
            if(prev!=null){
                assertTrue(banknote.getNominal() < prev.getNominal());
            }
            prev = banknote;
        }
    }
}