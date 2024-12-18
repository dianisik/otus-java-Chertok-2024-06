package ru.otus.service;

import ru.otus.enums.Banknote;

import java.util.List;
import java.util.Map;

public interface WalletService {

    void put(Banknote banknote, int count);

    void put (Map<Banknote, Integer> map);

    int getBalance();

    Map<Banknote, Integer> getBanknotes(Map<Banknote, Integer> banknotes);

    List<Banknote> getBanknotes();

    Map<Banknote, Integer> getContent();
}
