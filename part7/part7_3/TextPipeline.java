package part7.part7_3;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Задание 7.3 — Композиция функций и локальный класс
 *
 * Тема: Function<T, R>, композиция через andThen(), локальные классы.
 *
 * Ключевая теория:
 *   - Function<T, R> — функциональный интерфейс: принимает T, возвращает R.
 *   - f.andThen(g) = сначала f, потом g (f → g).
 *   - f.compose(g) = сначала g, потом f (g → f).
 *   - Локальный класс — класс, объявленный внутри метода. Виден только в этом методе.
 *
 * Как запустить: нажмите ▶ рядом с main.
 */
public class TextPipeline {

    public static void main(String[] args) {

        // === Часть A: Композиция функций ===
        // ▼ ИСПРАВЛЕННЫЙ КОД (Часть A) ▼

        // Создаём 4 функции
        Function<String, String> trim = String::trim;
        Function<String, String> lower = String::toLowerCase;
        Function<String, String> removeExtraSpaces = s -> s.replaceAll("\\s+", " ");
        Function<String, String> capitalize = s -> s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);

        // Компонуем в одну функцию через andThen()
        var normalize = trim.andThen(lower).andThen(removeExtraSpaces).andThen(capitalize);

        // Применяем к нескольким строкам
        String[] testStrings = {
                "  пРИВЕТ    МИР  ",
                "   jAVA   пРОГРАММИРОВАНИЕ   ",
                "ТЕСТ"
        };

        System.out.println("=== Часть A: Композиция функций ===\n");
        for (String s : testStrings) {
            System.out.println("\"" + s + "\" → \"" + normalize.apply(s) + "\"");
        }
        System.out.println();

        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА (Часть A) ▲

        // === Часть B: Локальный класс ===
        // ▼ ИСПРАВЛЕННЫЙ КОД (Часть B) ▼

        // Объявляем локальный класс WordCounter прямо внутри main
        class WordCounter {
            private final String text;

            WordCounter(String text) {
                this.text = text;
            }

            // Возвращает частотный словарь слов
            Map<String, Integer> count() {
                Map<String, Integer> freq = new HashMap<>();
                if (text == null || text.isEmpty()) {
                    return freq;
                }
                String[] words = text.split(" ");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        freq.merge(word, 1, Integer::sum);
                    }
                }
                return freq;
            }

            // Возвращает самое частое слово
            String mostFrequent() {
                Map<String, Integer> freq = count();
                if (freq.isEmpty()) {
                    return null;
                }
                return freq.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .map(Map.Entry::getKey)
                        .orElse(null);
            }

            // Дополнительный метод для красивого вывода
            void printStats() {
                System.out.println("Текст: \"" + text + "\"");
                System.out.println("Частоты: " + count());
                System.out.println("Самое частое слово: " + mostFrequent());
            }
        }

        // Используем WordCounter для анализа нормализованной строки
        System.out.println("=== Часть B: Локальный класс WordCounter ===\n");

        String sampleText = normalize.apply("  java java PYTHON  java python  ");
        WordCounter wc = new WordCounter(sampleText);
        wc.printStats();

        System.out.println("\n--- Дополнительный пример ---");
        String anotherText = normalize.apply("  мир  мир привет мир  java JAVA  ");
        WordCounter wc2 = new WordCounter(anotherText);
        wc2.printStats();

        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА (Часть B) ▲
    }
}