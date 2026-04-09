package part5.part5_1;

import java.util.*;

/**
 * Задание 5.1 — Система оценок (enum, record, EnumMap, EnumSet)
 *
 * Тема: перечисления (enum), записи (record), EnumMap и EnumSet.
 *
 * Ключевая теория:
 *   - enum — перечисление фиксированных констант. Может иметь поля,
 *     конструктор и методы.
 *   - record (Java 16+) — неизменяемый класс данных. Компилятор генерирует
 *     конструктор, геттеры, equals(), hashCode(), toString().
 *   - Компактный конструктор record: Student { if (name == null) throw ...; }
 *     — без списка параметров, с доступом к ним.
 *   - EnumMap<K, V> — Map, оптимизированный для enum-ключей.
 *     Внутри — массив по ordinal(), O(1) доступ.
 *   - EnumSet — Set для enum, реализован как битовая маска, очень быстрый.
 *
 * Как запустить: нажмите ▶ рядом с main.
 */
public class GradeSystem {

    // === Enum Grade ===

    /**
     * Оценка с описанием и GPA-значением.
     *
     * Синтаксис enum с полями:
     *
     *     enum Grade {
     *         A("Отлично", 4.0),
     *         B("Хорошо", 3.0);
     *         // ...
     *         private final String description;
     *         private final double gpaValue;
     *         Grade(String description, double gpaValue) { ... }
     *     }
     */
    enum Grade {
        A("Отлично", 4.0),
        B("Хорошо", 3.0),
        C("Удовлетворительно", 2.0),
        D("Неудовлетворительно", 1.0),
        F("Провал", 0.0);

        private final String description;
        private final double gpaValue;

        Grade(String description, double gpaValue) {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            this.description = description;
            this.gpaValue = gpaValue;
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /** Возвращает описание оценки (например, "Отлично"). */
        public String getDescription() {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            return description;
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /** Возвращает GPA-значение (например, 4.0). */
        public double getGpaValue() {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            return gpaValue;
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /**
         * Возвращает true, если оценка является проходной (не D и не F).
         *
         * Подсказка: return this != F && this != D;
         */
        public boolean isPassing() {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            return this != F && this != D;
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /**
         * Преобразует числовую оценку (0–100) в Grade.
         *
         * Шкала: A ≥ 90, B ≥ 80, C ≥ 70, D ≥ 60, иначе F.
         *
         * Подсказка:
         * if (score >= 90) return A; else if (score >= 80) return B; ...
         */
        public static Grade fromScore(int score) {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            if (score >= 90) return A;
            if (score >= 80) return B;
            if (score >= 70) return C;
            if (score >= 60) return D;
            return F;
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }
    }

    // === Record Student ===

    /**
     * Студент с именем и ID.
     *
     * Компактный конструктор:
     *
     *     record Student(String name, int id) {
     *         Student {   // без скобок с параметрами!
     *             if (name == null || name.isBlank()) throw new IllegalArgumentException("...");
     *             if (id <= 0) throw new IllegalArgumentException("...");
     *         }
     *     }
     */
    record Student(String name, int id) {
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        Student {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Имя студента не может быть пустым или null");
            }
            if (id <= 0) {
                throw new IllegalArgumentException("ID студента должен быть положительным числом");
            }
        }
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
    }

    // === Метод main ===

    public static void main(String[] args) {

        // ▼ ИСПРАВЛЕННЫЙ КОД ▼

        // Создаём студентов и оценки
        Student[] students = {
                new Student("Анна", 1),
                new Student("Борис", 2),
                new Student("Вера", 3),
                new Student("Глеб", 4),
                new Student("Дмитрий", 5),
                new Student("Елена", 6),
                new Student("Жанна", 7)
        };

        int[] scores = {95, 82, 71, 58, 88, 90, 65};

        // Группируем студентов по оценкам в EnumMap
        EnumMap<Grade, List<Student>> gradeMap = new EnumMap<>(Grade.class);
        double gpaSum = 0;

        for (int i = 0; i < students.length; i++) {
            Grade g = Grade.fromScore(scores[i]);
            gradeMap.computeIfAbsent(g, k -> new ArrayList<>()).add(students[i]);
            gpaSum += g.getGpaValue();
        }

        // Получаем множество проходных оценок с помощью EnumSet
        EnumSet<Grade> passingGrades = EnumSet.noneOf(Grade.class);
        for (Grade g : Grade.values()) {
            if (g.isPassing()) {
                passingGrades.add(g);
            }
        }

        // Выводим сводку
        System.out.println("=== Сводка по оценкам ===\n");

        for (Grade g : Grade.values()) {
            List<Student> list = gradeMap.get(g);
            if (list == null || list.isEmpty()) {
                System.out.printf("%s (%s, GPA: %.1f) - 0 студентов%n",
                        g, g.getDescription(), g.getGpaValue());
                continue;
            }

            System.out.printf("%s (%s, GPA: %.1f) - %d студент(ов):%n",
                    g, g.getDescription(), g.getGpaValue(), list.size());
            for (Student s : list) {
                System.out.printf("  - %s (ID: %d)%n", s.name(), s.id());
            }
            System.out.println();
        }

        // Выводим проходные оценки
        System.out.println("Проходные оценки: " + passingGrades);

        // Выводим средний GPA
        double averageGpa = gpaSum / students.length;
        System.out.printf("%nСредний GPA всех студентов: %.2f%n", averageGpa);

        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
    }
}