package part7.part7_1;

import java.util.*;
import java.util.function.*;

/**
 * Задание 7.1, Этап 1 — Замена анонимных классов на лямбда-выражения
 *
 * Тема: лямбда-выражения (Java 8+).
 *
 * Ключевая теория:
 *   - Лямбда заменяет анонимный класс для функционального интерфейса
 *     (интерфейс с одним абстрактным методом).
 *   - Синтаксис: (параметры) -> выражение или (параметры) -> { блок }
 *   - Примеры:
 *       - Comparator<String> c = (a, b) -> Integer.compare(a.length(), b.length());
 *       - Consumer<String> print = s -> System.out.println(s);
 *       - Function<String, String> upper = s -> s.toUpperCase();
 *
 * Задача: замените каждый анонимный класс из RefactorOriginal.java
 * на лямбда-выражение. Результат должен быть идентичен оригиналу.
 */
public class RefactorStep1 {
    public static void main(String[] args) {
        List<String> cities = Arrays.asList("Москва", "Берлин", "Токио", "Нью-Йорк", "Париж");

        // 1. Сортировка по длине → замените анонимный класс на лямбду
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        cities.sort((a, b) -> Integer.compare(a.length(), b.length()));
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲

        // 2. Вывод каждого элемента
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        cities.forEach(city -> System.out.println(city));
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲

        // 3. Преобразование в верхний регистр
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        Function<String, String> toUpper = s -> s.toUpperCase();
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲

        // 4. Проверка длины > 5
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        Predicate<String> isLong = s -> s.length() > 5;
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲

        // 5. Формирование строки с восклицательным знаком
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        Function<String, String> exclaim = s -> s + "!";
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲

        // 6. Создание нового списка
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        Supplier<List<String>> listFactory = () -> new ArrayList<>();
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲

        // Использование (скопируйте из RefactorOriginal и адаптируйте)
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        List<String> result = listFactory.get();
        for (String city : cities) {
            if (isLong.test(city)) {
                result.add(toUpper.apply(city));
            }
        }
        System.out.println("Длинные города: " + result);
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
    }
}