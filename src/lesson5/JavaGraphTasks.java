package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     *
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     *
     * Оценка ресурсоемкости: R = O(V + E),
     * Оценка трудоемкости: T = O(V * E)
     *
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        Set<Graph.Edge> edge = graph.getEdges();
        Set<Graph.Vertex> vertex = graph.getVertices();
        List<Graph.Vertex> listOfVertex = new ArrayList<>(); //лучше Array, т.к. доступ по индексу у него происходит
        //за O(1), а у Linked  - за O(N). Добавление в конец у них происходит одинаково за O(1)
        List<Graph.Edge> res = new LinkedList<>(); //здесь мы не обращаемся по индексу, только добавление

        if (edge.isEmpty() || vertex.isEmpty()) return res;

        for (Graph.Vertex V : vertex) {
            int connections = graph.getConnections(V).size();
            if (connections % 2 != 0)
                return res;
        }

        searchLoop(graph, vertex, listOfVertex, edge, vertex.iterator().next());

        for (int i = 0; i < listOfVertex.size() - 1; i++)
            res.add(graph.getConnection(listOfVertex.get(i), listOfVertex.get(i+1)));

        return res;
    }

    private static void searchLoop (Graph graph, Set<Graph.Vertex> vertex, List<Graph.Vertex> listOfVertex,
                                    Set<Graph.Edge> currentListOfEdge, Graph.Vertex actualVertex) {
        for (Graph.Vertex v : vertex) {
            Graph.Edge edgeBetweenThem = graph.getConnection(v, actualVertex);
            if (currentListOfEdge.contains(edgeBetweenThem)) {
                currentListOfEdge.remove(edgeBetweenThem);
                searchLoop(graph, vertex, listOfVertex, currentListOfEdge, v);
            }
        }
        listOfVertex.add(actualVertex);
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     *
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ:
     *
     *      G    H
     *      |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     *
     * Дан граф без циклов (получатель), например
     *
     *      G -- H -- J
     *      |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     *
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     *
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     *
     * В данном случае ответ (A, E, F, D, G, J)
     *
     * Если на входе граф с циклами, бросить IllegalArgumentException
     *
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     *
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     *
     * Пример:
     *
     *      G -- H
     *      |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     *
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     *
     * Оценка ресурсоемкости: R = O(V), худший вариант - все вершины
     * Оценка трудоемкости: T = O(V!), т.к. рекурсивная функция
     */
    public static Path longestSimplePath(Graph graph) {
        List<Graph.Vertex> vertex = new LinkedList<>(graph.getVertices());

        Set<Graph.Vertex> path = new LinkedHashSet<>();
        List<Graph.Vertex> result = new LinkedList<>();

        int sizeVertex = vertex.size();

        if (sizeVertex == 0) {
            return new Path();
        }

        for (int i = 0; i < sizeVertex; i++) {
            Graph.Vertex curVertex = vertex.get(i);

            path.add(curVertex);
            findMaxPath(graph, path, curVertex, result);

            if (result.size() == sizeVertex) break;
        }

        Path p = new Path(result.get(0));
        for (int i = 1; i < result.size(); i++)
            p = new Path(p, graph, result.get(i));
        return p;
    }

    private static void findMaxPath(Graph graph, Set<Graph.Vertex> path, Graph.Vertex vertex, List<Graph.Vertex> result) {
        Set<Graph.Vertex> neighbors = graph.getNeighbors(vertex);

        int pathSize = path.size();
        int vertexGraphSize = graph.getVertices().size();

        for (Graph.Vertex neighbor : neighbors) {
            if (!path.contains(neighbor)) {
                path.add(neighbor);

                if (pathSize != vertexGraphSize) {
                    findMaxPath(graph, path, neighbor, result);
                }
            }
        }

        if (pathSize <= vertexGraphSize) {
            if (pathSize > result.size()) {
                result.clear();
                result.addAll(path);
            }
            path.remove(vertex);
        }
    }

}
