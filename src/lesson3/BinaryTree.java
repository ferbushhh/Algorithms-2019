package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.IconUIResource;
import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     *
     * Оценка ресурсоемкости: R = O(1),
     * Оценка трудоемкости: T = O(h) , где h - высота дерева
     *
     * В худшем случае будет если приемник будет в самом низу по левой стороне правой ветки удаляемого (и если удалять
     * будем корень)
     */
    @Override
    public boolean remove(Object o) {
        T t = (T) o;
        //Node<T> node = find(t);

        boolean isRightChild = false; //нужна для определения каким является удаляемый лист для родителя

        if (root == null || t == null)
            return false;

        /*if (node == null) //значит, что мы не нашли лист => удалять нечего
            return false;


        if (node == root && node.left == null && node.right == null) { //лист - корень и единственный элемент в дереве
            root = null;
            size--;
            return true;
        }*/

        Node<T> actual = root;
        Node<T> parent = root;

        while(!actual.value.equals(t)) { //в цикле определяем родителя для удаляемого листа и сам лист
            parent = actual;
            if (t.compareTo(actual.value) > 0) {
                actual = actual.right;
                isRightChild = true;
            }
            else {
                actual = actual.left;
                isRightChild = false;
            }
        }

        if (actual.left == null && actual.right == null) { //если у лист нет потомков, то просто удаляем лист, ссылку у родителя меняем на null
            if (isRightChild)
                parent.right = null;
            else
                parent.left = null;
        } else if (actual.left == null) { //если у листа только правый потомок - вместо удаляемого листа присваиваем родителю ссылку на потомка удаляемого
            if (actual == root)
                root = actual.right;
            else if (isRightChild)
                parent.right = actual.right;
            else
                parent.left = actual.right;
        } else if (actual.right == null) { //аналогично
            if (actual == root)
                root = actual.left;
            else if (isRightChild)
                parent.right = actual.left;
            else
                parent.left = actual.left;
        } else {
            Node<T> follower = findFollower(actual); //находим того, кто его заменит

            if (actual == root)
                root = follower;
            else if (isRightChild)
                parent.right = follower;
            else
                parent.left = follower;

            follower.left = actual.left;
        }

        size--;
        return true;
    }

    private Node<T> findFollower(Node node) { //приемник ищется по алгоритму: по правой ветке самый левый лист
        Node<T> parent = node;
        Node<T> follower = node;
        Node<T> actual = node.right;

        while (actual != null) { //находим самый левый лист по правой ветке
            parent = follower;
            follower = actual;
            actual = actual.left;
        }

        if (follower != node.right) {
            parent.left = follower.right;
            follower.right = node.right;
        }

        return follower;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {

        private int i = 0;
        //private ArrayList <Node<T>> listOfValues;
        private Stack<Node> stack;

        private Node<T> current;

        /*
        Оценка ресурсоемкости: R = O(n),
        Оценка трудоемкости: T = O(n) (в худшем случае, когда все листья слева)
         */
        private BinaryTreeIterator() {
            current = root;
            stack = new Stack<>();
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }


        /**
         * Проверка наличия следующего элемента
         * Средняя
         *
         * Оценка ресурсоемкости: R = O(1),
         * Оценка трудоемкости: T = O(1)
         */
        @Override
        public boolean hasNext() {
            //return i < listOfValues.size();
            return !stack.isEmpty();
        }

        /**
         * Поиск следующего элемента
         * Средняя
         *
         * Оценка ресурсоемкости: R = O(n),
         * Оценка трудоемкости: T = O(n)
         */
        @Override
        public T next() {

            current = stack.pop();

            Node<T> node = current;

            if (node != null) {
                if (node.right != null) {
                    node = node.right;

                    while (node != null) {
                        stack.push(node);
                        node = node.left;
                    }
                }

            }

            return current.value;
        }


        /**
         * Удаление следующего элемента
         * Сложная
         *
         * Оценка ресурсоемкости: R = O(1),
         * Оценка трудоемкости: T = O(n)
         */
        @Override
        public void remove() {
            /*
            Node<T> nn = stack.pop();
            Node<T> node = nn;
            if (node != null) {
                if (node.right != null) {
                    node = node.right;

                    while (node != null) {
                        //stack.push(node);
                        node = node.left;
                    }
                }
                stack.push(node);
            }

            Node<T> pop = stack.pop();
            while (pop.value.compareTo(nn.value) > 0) {
                pop = stack.pop();
            }

            stack.push(pop);
            BinaryTree.this.remove(nn); */

            // TODO
            throw new NotImplementedError();

        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        // TODO
        throw new NotImplementedError();
    }


    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        // TODO
        throw new NotImplementedError();
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
