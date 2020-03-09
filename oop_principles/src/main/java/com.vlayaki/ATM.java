package com.vlayaki;

import java.util.List;

public interface ATM {
    void deposit(Banknote banknote);

    List<Banknote> withdraw(int amount);

    double balance();

    void insertCard(Card card);

    void returnCard();
}
