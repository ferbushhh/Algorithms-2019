package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Math.sqrt;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) {
        throw new NotImplementedError();
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String first, String second) {
        int lengthFirst = first.length();
        int lengthSecond = second.length();
        int startIndex = 0;
        int maxCount = 0;

        if (first.equals(second))
            return first;


        if (lengthFirst == 0 || lengthSecond == 0)
            return "";


        for (int i = 0; i < lengthFirst; i++) {
            for (int j = 0; j < lengthSecond; j++) {
                int count = 0;
                while ((first.charAt(i + count) == second.charAt(j + count)) && (i + 1 + count) < lengthFirst
                                        && (j + 1 + count) < lengthSecond)
                    count++;
                if (count > maxCount) {
                    maxCount = count;
                    startIndex = i;
                }
            }
        }

        if (maxCount != 0)
            return first.substring(startIndex, startIndex + maxCount);
        else
            return "";

        /* Оценка ресурсоемкости: R = O(1),
        Оценка трудоемкости: T = O(MN), где N - длина первой строки, M - длина второй строки
         */
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     *
     * Оценка ресурсоемкости: R = O(1),
     * Оценка трудоемкости: T = O(N*sqrt(N))
     */
    static public int calcPrimesNumber(int limit) {
        if (limit < 2) return 0;

        int sq = (int) sqrt(limit) + 1;

        int ans = 0;

        boolean flag;
        for (int i = 2; i <= limit; i++) {
            flag = false;
            for (int j = 2; j <= sq; j++) {
                if (i % j == 0 && i != j) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                ans++;
        }
        return ans;
    }

    /**
     * Балда
     * Сложная
     *
     * В файле с именем inputName задана матрица из букв в следующем формате
     * (отдельные буквы в ряду разделены пробелами):
     *
     * И Т Ы Н
     * К Р А Н
     * А К В А
     *
      * В аргументе words содержится множество слов для поиска, например,
     * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
     *
     * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
     * и вернуть множество найденных слов. В данном случае:
     * ТРАВА, КРАН, АКВА, НАРТЫ
     *
     * И т Ы Н     И т ы Н
     * К р а Н     К р а н
     * А К в а     А К В А
     *
     * Все слова и буквы -- русские или английские, прописные.
     * В файле буквы разделены пробелами, строки -- переносами строк.
     * Остальные символы ни в файле, ни в словах не допускаются.
     */

    static public Set<String> baldaSearcher(String inputName, Set<String> words) throws IOException {
        try (//BufferedReader reader = new BufferedReader(new FileReader(new File(inputName), StandardCharsets.UTF_8));
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(
                             new FileInputStream(inputName), StandardCharsets.UTF_8))) {

            ArrayList<String[]> matrix = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                matrix.add(line.split(" "));
            }

            int width = matrix.size();
            int height = matrix.get(0).length;

            Set<String> result = new HashSet<>();

            for (String word : words) {
                boolean flag = false;
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        flag = recursion(matrix, word, i, j, 0);
                        if (flag) result.add(word);
                        if (flag) break;
                    }
                    if (flag) break;
                }
            }
            return result;
        }
    }

    static private boolean recursion(ArrayList<String[]> matrix, String word, int i, int j, int count) {
        if (count == word.length())
            return true;
        if (i<0 || j<0 || i>=matrix.size() || j>=matrix.get(0).length)
            return false;
        if (!(matrix.get(i)[j].equals(word.substring(count, count + 1))))
            return false;
        if (recursion(matrix, word, i+1, j, count + 1))
            return true;
        if (recursion(matrix, word, i-1, j, count + 1))
            return true;
        if (recursion(matrix, word, i, j+1, count + 1))
            return true;
        if (recursion(matrix, word, i, j-1, count + 1))
            return true;
        return false;
    }
    /* Оценка ресурсоемкости: R = O(MN),
        Оценка трудоемкости: T = O(M*N*K*4^Q), где N - длина массива, M - ширина массива, K - кол-во слов,
        Q - максимальное количество букв в слове (худший вариант)
        Проходим по массиву в худшем случае M*N*K раз и вызываем рекурсию. В худшем случае рекурсия может вызваться
        4 раза (поэтому 4 в основании степени), а вызывается она "вглубь" по количеству букв в слове.
    */
}
