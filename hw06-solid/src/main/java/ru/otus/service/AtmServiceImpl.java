package ru.otus.service;

import ru.otus.enums.Banknote;
import ru.otus.exceptions.BalanceException;

import java.util.Map;

public class AtmServiceImpl implements AtmService {

    private final WalletService walletService;
    private final CalculatorService calculatorService;

    public AtmServiceImpl(WalletService walletService, CalculatorService calculatorService) {
        this.walletService = walletService;
        this.calculatorService = calculatorService;
    }

    @Override
    public void put(Banknote banknote, int count) {
        walletService.put(banknote, count);
    }

    @Override
    public void put(Map<Banknote, Integer> map) {
        walletService.put(map);
    }

    @Override
    public int getBalance() {
        return walletService.getBalance();
    }

    @Override
    public Map<Banknote, Integer> get(int sum) {
        if (sum > walletService.getBalance()) {
            throw new BalanceException(walletService.getBalance(), sum);
        }

        return calculatorService.calculate(sum);
    }

    @Override
    public Map<Banknote, Integer> getContent() {
        return walletService.getContent();
    }
}
