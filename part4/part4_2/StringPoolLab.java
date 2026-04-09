package part4.part4_2;

/**
 * Задание 4.2 — Исследование String Pool
 *
 * Тема: String Pool, оператор == и метод .equals().
 *
 * Ключевая теория:
 *   - String Pool — специальная область памяти JVM, где хранятся
 *     уникальные строковые литералы.
 *   - Строковый литерал "Hello" попадает в пул. Все литералы с одинаковым
 *     содержимым ссылаются на один объект.
 *   - new String("Hello") создаёт НОВЫЙ объект в куче (heap),
 *     даже если такая строка уже есть в пуле.
 *   - == сравнивает ссылки (адреса в памяти).
 *     .equals() сравнивает содержимое.
 *   - intern() — возвращает ссылку из пула (добавляет строку в пул, если её нет).
 *   - Конкатенация литералов ("Hel" + "lo") вычисляется компилятором →
 *     результат берётся из пула.
 *   - Конкатенация с переменной выполняется в рантайме → создаётся новый объект.
 *
 * Как запустить: нажмите ▶ рядом с main.
 */
public class StringPoolLab {

    public static void main(String[] args) {

        // === Создание строк разными способами ===

        String s1 = "Hello";                    // литерал → пул
        String s2 = "Hello";                    // тот же литерал → тот же объект в пуле
        String s3 = new String("Hello");        // new → новый объект в куче
        String s4 = new String("Hello");        // new → ещё один новый объект
        String s5 = s3.intern();                // intern() → ссылка из пула
        String s6 = "Hel" + "lo";              // конкатенация литералов → компилятор → пул
        String half = "Hel";
        String s7 = half + "lo";               // конкатенация с переменной → рантайм → новый объект

        // === Сравнения ===

        System.out.println("=== Исследование String Pool ===\n");

        // Прогноз: true. Причина: оба литерала, указывают на один объект в String Pool
        System.out.println("Сравнение s1 и s2 (литералы):");
        System.out.println("  Прогноз: true (оба из пула)");
        System.out.println("  s1 == s2      : " + (s1 == s2));
        System.out.println("  s1.equals(s2) : " + s1.equals(s2));
        System.out.println();

        // Прогноз: false. Причина: s1 из пула, s3 новый объект в куче → разные ссылки
        System.out.println("Сравнение s1 и s3 (литерал vs new String):");
        System.out.println("  Прогноз: false (разные объекты в памяти)");
        System.out.println("  s1 == s3      : " + (s1 == s3));
        System.out.println("  s1.equals(s3) : " + s1.equals(s3));
        System.out.println();

        // Прогноз: false. Причина: два разных объекта, созданных через new
        System.out.println("Сравнение s3 и s4 (new String vs new String):");
        System.out.println("  Прогноз: false (разные объекты в куче)");
        System.out.println("  s3 == s4      : " + (s3 == s4));
        System.out.println("  s3.equals(s4) : " + s3.equals(s4));
        System.out.println();

        // Прогноз: true. Причина: intern() возвращает ссылку из пула, где уже есть "Hello"
        System.out.println("Сравнение s1 и s5 (литерал vs intern()):");
        System.out.println("  Прогноз: true (s3.intern() вернул ссылку из пула)");
        System.out.println("  s1 == s5      : " + (s1 == s5));
        System.out.println("  s1.equals(s5) : " + s1.equals(s5));
        System.out.println();

        // Прогноз: true. Причина: конкатенация литералов вычислена компилятором → результат в пуле
        System.out.println("Сравнение s1 и s6 (литерал vs конкатенация литералов):");
        System.out.println("  Прогноз: true (компилятор сложил \"Hel\" + \"lo\" в \"Hello\" из пула)");
        System.out.println("  s1 == s6      : " + (s1 == s6));
        System.out.println("  s1.equals(s6) : " + s1.equals(s6));
        System.out.println();

        // Прогноз: false. Причина: конкатенация с переменной выполняется в рантайме → новый объект
        System.out.println("Сравнение s1 и s7 (литерал vs конкатенация с переменной):");
        System.out.println("  Прогноз: false (конкатенация с переменной создаёт новый объект)");
        System.out.println("  s1 == s7      : " + (s1 == s7));
        System.out.println("  s1.equals(s7) : " + s1.equals(s7));
        System.out.println();

        // === StringBuilder ===

        System.out.println("=== StringBuilder ===\n");
        StringBuilder sb = new StringBuilder();
        sb.append('H').append('e').append('l').append('l').append('o');
        String s8 = sb.toString();

        // Прогноз: false. Причина: StringBuilder создаёт новый объект в куче, не из пула
        System.out.println("Сравнение s1 и s8 (литерал vs StringBuilder):");
        System.out.println("  Прогноз: false (StringBuilder создаёт новый объект в куче)");
        System.out.println("  s1 == s8      : " + (s1 == s8));
        System.out.println("  s1.equals(s8) : " + s1.equals(s8));
    }
}