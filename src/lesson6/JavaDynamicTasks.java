package lesson6;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     *
     * Оценка ресурсоемкости: R = O(N * M), где M и N - длины строк
     * Оценка трудоемкости: T = O(N * M)
     */
    public static String longestCommonSubSequence(String first, String second) {
        if (first.equals(second))
            return first;

        int lenF = first.length();
        int lenS = second.length();

        if (lenF == 0 || lenS == 0)
            return "";

        int[][] mas = new int[lenF + 1][lenS + 1];

        for (int i = 1; i <= lenF; i++) {
            for (int j = 1; j <= lenS; j++) {
                if (first.charAt(i - 1) == second.charAt(j - 1))
                    mas[i][j] = 1 + mas[i - 1][j - 1];
                else mas[i][j] = Math.max(mas[i - 1][j], mas[i][j - 1]);
            }
        }

        String result = "";

        while (lenF > 0 && lenS > 0) {
            if (first.charAt(lenF - 1) == second.charAt(lenS - 1)) {
                result = first.charAt(lenF - 1) + result;
                lenF--;
                lenS--;
            } else if (mas[lenF][lenS] == mas[lenF - 1][lenS]) lenF--;
            else lenS--;
        }

        return result;
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     *
     * Оценка ресурсоемкости: R = O(N),
     * Оценка трудоемкости: T = O(N*N)
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {

        if (list.size() == 0) return new LinkedList<>();

        if (list.size() == 1) return list;

        int size = list.size();

        int[] early = new int[size];
        int[] length = new int[size];

        for (int i = 0; i < size; i++) {
            early[i] = -1;
            length[i] = 1;

            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && length[j] + 1 > length[i]) {
                    length[i] = length[j] + 1;
                    early[i] = j;
                }
            }
        }

        int pos = 0;
        int maxLength = 1;

        for (int i = 0; i < size; i++) {
            if (length[i] > maxLength) {
                pos = i;
                maxLength = length[i];
            }
        }

        List<Integer> prevResult = new LinkedList<>();

        while (pos != -1) {
            prevResult.add(list.get(pos));
            pos = early[pos];
        }

        List<Integer> result = new LinkedList<>();

        for (int i = prevResult.size() - 1; i > -1; i--)
            result.add(prevResult.get(i));

        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
