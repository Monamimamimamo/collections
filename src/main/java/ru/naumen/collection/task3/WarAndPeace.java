package ru.naumen.collection.task3;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace
{

    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        Map<String, Integer> wordCountMap = new HashMap<>();
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    wordCountMap.put(word, wordCountMap.getOrDefault(word, 0) + 1);
                });

        List<Map.Entry<String, Integer>> sortedEntries =
                wordCountMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .toList();

        System.out.println("Топ 10 самых часто используемых слов:");
        printWords(sortedEntries.subList(0, Math.min(10, sortedEntries.size())));

        System.out.println("\nПоследние 10 слов по частоте:");
        printWords(sortedEntries.subList(Math.max(0, sortedEntries.size() - 10), sortedEntries.size()));
    }


    private static void printWords(List<Map.Entry<String, Integer>> entries) {
        for (int i = 0; i < entries.size(); i++) {
            System.out.printf("%d. %s (%d)%n", i + 1, entries.get(i).getKey(), entries.get(i).getValue());
        }
    }
}
