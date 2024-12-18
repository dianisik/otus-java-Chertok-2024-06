package ru.otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;
import ru.otus.enums.Banknote;
import ru.otus.exceptions.BalanceException;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Банкомат должен выполнить")
class AtmServiceImplTest {

    private AtmService atmService;

    @BeforeEach
    void setUp() {
        WalletService wallet = new WalletServiceImpl();
        CalculatorService calculator = new CalculatorServiceImpl(wallet);
        atmService = new AtmServiceImpl(wallet, calculator);
    }

    @Test
    @DisplayName("Положить деньги")
    void put() {
        initAtm();
        assertEquals(8500, atmService.getBalance());
    }

    @Test
    @DisplayName("Проверить баланс")
    void getBalance() {
        int currentBalance = atmService.getBalance();
        atmService.put(Banknote.BANKNOTE_1000, 5);
        int newBalance = atmService.getBalance();
        assertEquals(0, currentBalance);
        assertEquals(5000, newBalance);
    }

    @Test
    @DisplayName("Выдать деньги")
    void get() {
        initAtm();
        int initBalance = atmService.getBalance();
        int requestedSum = 3500;
        atmService.get(requestedSum);
        int newBalance = atmService.getBalance();
        assertEquals(newBalance, initBalance - requestedSum);
    }

    @Test
    @DisplayName("Показать содержимое банкомата")
    void getContent() {
        Map<Banknote, Integer> content = Map.of(Banknote.BANKNOTE_1000, 5);
        atmService.put(content);
        assertEquals(content, atmService.getContent());
    }

    @Test
    @DisplayName("выбросить ошибку баланса")
    void shouldThrowBalanceException() {
        initAtm();
        int initBalance = atmService.getBalance();
        int requestedSum = initBalance + 1;
        assertThatExceptionOfType(BalanceException.class).isThrownBy(() -> atmService.get(requestedSum));
    }

    private void initAtm() {
        atmService.put(Banknote.BANKNOTE_1000, 5);
        atmService.put(Banknote.BANKNOTE_500, 2);
        atmService.put(Banknote.BANKNOTE_500, 4);
        atmService.put(Banknote.BANKNOTE_100, 5);
    }
}