package ru.otus.service;

import ru.otus.enums.Banknote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculatorServiceImpl implements CalculatorService {

    private final WalletService walletService;

    public CalculatorServiceImpl(WalletService walletService) {
        this.walletService = walletService;
    }

    @Override
    public Map<Banknote, Integer> calculate(int requestSum) {
        List<Banknote> list = walletService.getBanknotes();
        Map<Banknote, Integer> extractedBanknotes = getBanknotesWithRequestedSum(requestSum, list);
        return extractedBanknotes == null || extractedBanknotes.isEmpty() ? null : walletService.getBanknotes(extractedBanknotes);
    }

    private Map<Banknote, Integer> getBanknotesWithRequestedSum(int sum, List<Banknote> values) {
        Map<Banknote, Integer> map = new HashMap<>();
        for (Banknote value : values) {
            if (sum >= value.getValue()) {
                map.put(value, map.getOrDefault(value, 0) + 1);
                sum -= value.getValue();
            }
        }
        return sum == 0 ? map : null;
    }

}