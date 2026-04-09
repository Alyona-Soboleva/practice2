package part2.part2_3;

/**
 * Задание 2.3 — Сервис базы данных (реализация Loggable)
 *
 * Реализует единственный обязательный метод getComponentName().
 * Все default-методы (log, logError) доступны автоматически.
 */
public class DatabaseService implements Loggable {

    /**
     * Подсказка: return "DatabaseService";
     */
    @Override
    public String getComponentName() {
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        return "DatabaseService";
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
    }

    /**
     * Подключается к базе данных и логирует процесс.
     *
     * Алгоритм:
     *   1. log("Подключение к " + url);
     *   2. log("Подключение установлено");
     */
    public void connect(String url) {
        // ▼ ИСПРАВЛЕННЫЙ КОД ▼
        log("Подключение к " + url);
        log("Подключение установлено");
        // ▲ КОНЕЦ ИСПРАВЛЕННОГО КОДА ▲
    }
}