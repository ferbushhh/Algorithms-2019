package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.min;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        int highBorder = 500;
        int lowBorder = -273;
        int border = abs(highBorder)*10+abs(lowBorder)*10; //умножаем на 10 чтобы избавиться от числа после запятой
        int mas[] = new int[border + 1];
        BufferedReader reader = new BufferedReader(new FileReader(new File(inputName)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputName)));
        String str;
        str = reader.readLine();
        while (str != null) {
            //mas[(int) (Double.parseDouble(str)*10+abs(lowBorder)*10)];
            int index = (int) (Double.parseDouble(str)*10);
            mas[index+abs(lowBorder)*10]++;
            str = reader.readLine();
        }
        for (int i = 0; i<border; i++) {
            while (mas[i] != 0) {
                writer.write(String.valueOf((double)((i+lowBorder*10)/10.0)) + "\n");
                mas[i]--;
            }
        }
        writer.close();

        /* Оценка ресурсоемкости: R = O(1)
        Оценка трудоемкости: T = O(N)
         */
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */

    /*public static void main(String[] args) throws IOException {
        sortSequence("inputForMe/seq_in6.txt", "temp.txt");
    }*/

    static public void sortSequence(String inputName, String outputName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(inputName)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputName)));
        String str = reader.readLine();

        Map<Integer, Integer> mas = new HashMap<>();
        int maxVal = 0;
        int maxNum = 0;
        while (str != null) {
            int a = (int) (Integer.parseInt(str));
            if (mas.containsKey(a)) {
                int val = mas.get(a);
                mas.put(a, val+1);
            }
            else
                mas.put(a, 1);

            if (mas.get(a) > maxVal || (mas.get(a) == maxVal && a<maxNum)) {
                maxVal = mas.get(a);
                maxNum = a;
            }
            str = reader.readLine();
        }

        BufferedReader readerSecond = new BufferedReader(new FileReader(new File(inputName)));
        String str2 = readerSecond.readLine();
        while (str2 != null) {
            int a = (int) (Integer.parseInt(str2));
            if (a != maxNum) {
                writer.write(a + "\r\n");
            }
            str2 = readerSecond.readLine();
        }

        for (int i = 0; i < maxVal; i++) {
               writer.write(String.valueOf(maxNum) + "\r\n");
        }
        writer.close();
        /* Оценка ресурсоемкости: R = O(N)
        Оценка трудоемкости: T = O(N)
         */
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
