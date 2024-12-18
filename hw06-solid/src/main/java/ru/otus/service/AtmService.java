package ru.otus.service;

import ru.otus.enums.Banknote;

import java.util.Map;

public interface AtmService {

    void put(Banknote banknote, int count);

    void put (Map<Banknote, Integer> map);

    int getBalance();

    Map<Banknote, Integer> get(int requestSum);

    Map<Banknote, Integer> getContent();
}
