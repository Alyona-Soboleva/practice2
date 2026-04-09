package part2.part2_2;

/**
 * Задание 2.2 — Банковский перевод (реализация PaymentMethod)
 *
 * Формат process: "Перевод через БАНК: Z руб."
 * Комиссия: фиксированные 50 руб.
 */
public record BankTransfer(String bankName, String iban) implements PaymentMethod {

    /**
     * "Перевод через Сбербанк: 10000.0 руб."
     *
     * Подсказка:
     * return "Перевод через " + bankName + ": " + amount + " руб.";
     */
    @Override
    public String process(double amount) {
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        return String.format("Перевод через %s: %.2f руб.", bankName, amount);
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
    }

    /**
     * Комиссия = фиксированные 50 рублей.
     */
    @Override
    public double fee(double amount) {
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        return 50.0;
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
    }
}