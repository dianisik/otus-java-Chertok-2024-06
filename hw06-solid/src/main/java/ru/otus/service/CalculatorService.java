package ru.otus.service;

import ru.otus.enums.Banknote;

import java.util.Map;

public interface CalculatorService {
    Map<Banknote, Integer> calculate(int sumValue);
}
