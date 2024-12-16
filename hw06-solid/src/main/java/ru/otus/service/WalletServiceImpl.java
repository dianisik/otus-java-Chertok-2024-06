package ru.otus.service;

import ru.otus.enums.Banknote;
import ru.otus.exceptions.GettingBanknotesException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WalletServiceImpl implements WalletService {

    private final Map<Banknote, Integer> map;

    public WalletServiceImpl() {
        map = new TreeMap<>(Comparator.comparingInt(Banknote::getValue).reversed());
    }

    @Override
    public void put(Banknote banknote, int count) {
        map.put(banknote, map.getOrDefault(banknote, 0) + count);
    }

    @Override
    public void put(Map<Banknote, Integer> map) {
        this.map.putAll(map);
    }

    @Override
    public int getBalance() {
        return map.keySet().stream()
                .mapToInt(this::getSumByBanknote)
                .sum();
    }

    @Override
    public Map<Banknote, Integer> getBanknotes(Map<Banknote, Integer> banknotes) {
        Map<Banknote, Integer> mapExtracted = new HashMap<>();
        banknotes.entrySet()
                .stream()
                .map(entry -> computeBanknotes(entry.getKey(), entry.getValue()))
                .forEach(mapExtracted::putAll);
        return mapExtracted;
    }

    @Override
    public List<Banknote> getBanknotes() {
        List<Banknote> banknotes = new ArrayList<>();
        map.forEach((banknote, count) -> banknotes.addAll(Collections.nCopies(count, banknote)));
        return banknotes;
    }

    @Override
    public Map<Banknote, Integer> getContent() {
        return Map.copyOf(map);
    }

    private Map<Banknote, Integer> computeBanknotes(Banknote banknote, int requestCount) {
        validateBanknoteResuestCount(banknote, requestCount);
        map.compute(banknote, (key, value) -> value - requestCount);
        return Map.of(banknote, requestCount);
    }

    private int getSumByBanknote(Banknote banknote) {
        return map.getOrDefault(banknote, 0) * banknote.getValue();
    }

    private int getCountByBanknote(Banknote banknote) {
        return map.getOrDefault(banknote, 0);
    }

    private void validateBanknoteResuestCount(Banknote banknote, int requestCount){
        if (requestCount <= 0) {
            throw new IllegalArgumentException("Count must be greater than zero");
        }

        int currentCount = getCountByBanknote(banknote);
        if (currentCount < requestCount) {
            throw new GettingBanknotesException(requestCount, requestCount);
        }
    }

}