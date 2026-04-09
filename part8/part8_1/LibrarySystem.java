package part8.part8_1;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Задание 8.1 — Система управления библиотекой (интеграционное задание)
 *
 * Тема: объединение всех концепций частей 1–7.
 *
 * Архитектура системы:
 *
 *   Genre (enum, Часть 5) ← Book (record, Часть 5) ← LibraryItem (sealed, Часть 2)
 *                                                      ├── PhysicalBook (record)
 *                                                      └── EBook (record)
 *   Searchable (interface с default, Часть 2)
 *   Library (класс с коллекциями и Stream API, Части 3/7)
 *
 * Как запустить: нажмите ▶ рядом с main.
 */
public class LibrarySystem {

    // ======================== enum Genre (Часть 5) ========================

    /**
     * Жанры книг. Каждый жанр имеет русское название.
     *
     * Подсказка: FICTION("Художественная литература") и т.д.
     */
    enum Genre {
        FICTION("Художественная литература"),
        SCIENCE("Научная литература"),
        HISTORY("История"),
        PROGRAMMING("Программирование"),
        ART("Искусство");

        private final String russianName;

        Genre(String russianName) {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            this.russianName = russianName;
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /** Возвращает русское название жанра. */
        public String getRussianName() {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            return russianName;
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /**
         * Находит жанр по русскому названию.
         *
         * Подсказка: пройдите по values(), сравните russianName.
         * Если не найден — выбросите IllegalArgumentException.
         */
        public static Genre fromString(String name) {
            for (Genre g : values()) {
                if (g.russianName.equals(name)) {
                    return g;
                }
            }
            throw new IllegalArgumentException("Неизвестный жанр: " + name);
        }
    }

    // ======================== record Book (Часть 5) ========================

    /**
     * Книга с валидацией в компактном конструкторе.
     *
     * Проверки:
     *   - title и author не пустые
     *   - year от 1450 до текущего года
     *   - price >= 0
     */
    record Book(String title, String author, int year, Genre genre, double price) {
        Book {
            if (title == null || title.isBlank()) {
                throw new IllegalArgumentException("Название не может быть пустым");
            }
            if (author == null || author.isBlank()) {
                throw new IllegalArgumentException("Автор не может быть пустым");
            }
            int maxYear = Year.now().getValue();
            if (year < 1450 || year > maxYear) {
                throw new IllegalArgumentException("Год издания вне допустимого диапазона");
            }
            if (price < 0) {
                throw new IllegalArgumentException("Цена не может быть отрицательной");
            }
        }
    }

    // ======================== sealed interface LibraryItem (Часть 2) ========================

    /**
     * Элемент библиотеки. Sealed — только PhysicalBook и EBook допустимы.
     */
    sealed interface LibraryItem permits PhysicalBook, EBook {
        /** Возвращает информацию об элементе. */
        String getInfo();

        /** Возвращает книгу, содержащуюся в этом элементе. */
        Book book();
    }

    /**
     * Физическая книга с указанием полки.
     *
     * Подсказка для getInfo:
     *   return "[Полка " + shelf + "] " + book.title() + " — " + book.author();
     */
    record PhysicalBook(Book book, String shelf) implements LibraryItem {
        @Override
        public String getInfo() {
            return "[Полка " + shelf + "] " + book.title() + " — " + book.author() + " (" + book.year() + ")";
        }
    }

    /**
     * Электронная книга с форматом и размером файла.
     *
     * Подсказка для getInfo:
     *   return "[" + format + ", " + sizeMB + " МБ] " + book.title() + " — " + book.author();
     */
    record EBook(Book book, String format, double sizeMB) implements LibraryItem {
        @Override
        public String getInfo() {
            return "[" + format + ", " + sizeMB + " МБ] " + book.title() + " — " + book.author() + " (" + book.year() + ")";
        }
    }

    // ======================== Класс Library (Части 3, 7) ========================

    /**
     * Библиотека — хранит коллекцию LibraryItem и предоставляет методы анализа.
     */
    static class Library {
        private final List<LibraryItem> items = new ArrayList<>();

        /** Добавляет элемент в библиотеку. */
        public void add(LibraryItem item) {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            items.add(item);
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /**
         * Выводит каталог с использованием switch и паттерн-матчинга (Java 21).
         *
         * Подсказка:
         *   for (LibraryItem item : items) {
         *       switch (item) {
         *           case PhysicalBook pb -> System.out.println("Физ.: " + pb.getInfo());
         *           case EBook eb        -> System.out.println("Эл.:  " + eb.getInfo());
         *       }
         *   }
         */
        public void printCatalog() {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            for (int i = 0; i < items.size(); i++) {
                LibraryItem item = items.get(i);
                switch (item) {
                    case PhysicalBook pb -> System.out.printf("%d. 📚 Физ.: %s%n", i + 1, pb.getInfo());
                    case EBook eb -> System.out.printf("%d. 💻 Эл.:  %s%n", i + 1, eb.getInfo());
                }
            }
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /**
         * Группирует элементы по жанру через EnumMap и Stream API.
         *
         * Подсказка:
         *   items.stream().collect(Collectors.groupingBy(i -> i.book().genre(), () -> new EnumMap<>(Genre.class), Collectors.toList()));
         */
        public Map<Genre, List<LibraryItem>> groupByGenre() {
            return items.stream().collect(Collectors.groupingBy(
                    i -> i.book().genre(),
                    () -> new EnumMap<>(Genre.class),
                    Collectors.toList()));
        }

        /**
         * Общая стоимость всех книг через Stream API.
         *
         * Подсказка:
         *   return items.stream().mapToDouble(i -> i.book().price()).sum();
         */
        public double totalValue() {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            return items.stream().mapToDouble(i -> i.book().price()).sum();
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /**
         * Самая дорогая книга.
         *
         * Подсказка:
         *   return items.stream().map(LibraryItem::book).max(Comparator.comparingDouble(Book::price));
         */
        public Optional<Book> mostExpensive() {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            return items.stream()
                    .map(LibraryItem::book)
                    .max(Comparator.comparingDouble(Book::price));
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }

        /**
         * Уникальные авторы указанного жанра, отсортированные по алфавиту.
         *
         * Подсказка:
         *   items.stream().map(LibraryItem::book).filter(b -> b.genre() == genre).map(Book::author).distinct().sorted().collect(Collectors.toList());
         */
        public List<String> authorsByGenre(Genre genre) {
            // ▼ ИСПРАВЛЕННЫЙ КОД ▼
            return items.stream()
                    .map(LibraryItem::book)
                    .filter(b -> b.genre() == genre)
                    .map(Book::author)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
        }
    }

    // ======================== main ========================

    public static void main(String[] args) {
        Library lib = new Library();

        // Добавляем книги
        lib.add(new PhysicalBook(new Book("Война и мир", "Лев Толстой", 1869, Genre.FICTION, 800), "A-12"));
        lib.add(new PhysicalBook(new Book("История России", "Сергей Соловьёв", 1851, Genre.HISTORY, 1200), "H-3"));
        lib.add(new EBook(new Book("Clean Code", "Роберт Мартин", 2008, Genre.PROGRAMMING, 1500), "PDF", 5.2));
        lib.add(new EBook(new Book("Effective Java", "Джошуа Блох", 2018, Genre.PROGRAMMING, 2200), "EPUB", 3.1));
        lib.add(new PhysicalBook(new Book("Краткая история времени", "Стивен Хокинг", 1988, Genre.SCIENCE, 950), "S-7"));
        lib.add(new EBook(new Book("Искусство войны", "Сунь-цзы", 2000, Genre.ART, 400), "PDF", 1.0));
        lib.add(new PhysicalBook(new Book("Преступление и наказание", "Фёдор Достоевский", 1866, Genre.FICTION, 700), "A-5"));
        lib.add(new EBook(new Book("Sapiens", "Юваль Ной Харари", 2014, Genre.HISTORY, 1100), "MOBI", 8.0));

        // Демонстрация всех методов
        System.out.println("=== КАТАЛОГ БИБЛИОТЕКИ ===\n");
        lib.printCatalog();

        System.out.println("\n=== ГРУППИРОВКА ПО ЖАНРАМ ===\n");
        lib.groupByGenre().forEach((genre, list) ->
                System.out.printf("%s (%s): %d элемент(ов)%n",
                        genre, genre.getRussianName(), list.size()));

        System.out.printf("%n=== ОБЩАЯ СТОИМОСТЬ ===\n%.2f руб.%n", lib.totalValue());

        System.out.println("\n=== САМАЯ ДОРОГАЯ КНИГА ===");
        lib.mostExpensive().ifPresent(book ->
                System.out.printf("«%s» - %s (%.2f руб.)%n",
                        book.title(), book.author(), book.price()));

        System.out.println("\n=== АВТОРЫ ЖАНРА ПРОГРАММИРОВАНИЕ ===");
        List<String> authors = lib.authorsByGenre(Genre.PROGRAMMING);
        authors.forEach(author -> System.out.println("  - " + author));

        System.out.println("\n=== АВТОРЫ ЖАНРА ХУДОЖЕСТВЕННАЯ ЛИТЕРАТУРА ===");
        lib.authorsByGenre(Genre.FICTION).forEach(author -> System.out.println("  - " + author));
    }
}