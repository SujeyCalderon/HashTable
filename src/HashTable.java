import java.util.LinkedList;

public class HashTable<K, V> {

    private LinkedList<HashNode<K, V>>[] chainArray;
    private int numBuckets = 11;
    private int sizeTable;

    public HashTable() {
        chainArray = new LinkedList[numBuckets];
        for (int i = 0; i < numBuckets; i++) {
            chainArray[i] = new LinkedList<>();
        }
        sizeTable = 0;
    }

    // Función hash1
    private int hashFunction1(K key) {
        int hashCode = key.hashCode();
        return (hashCode & Integer.MAX_VALUE) % numBuckets;
    }

    // Función hash2
    private int hashFunction2(K key) {
        int hashCode = key.hashCode() * 31;
        return (hashCode & Integer.MAX_VALUE) % numBuckets;
    }

    // Método para insertar un nuevo elemento en la tabla
    public void put(K key, V value, boolean useHashFunction1) {
        int bucketIndex = useHashFunction1 ? hashFunction1(key) : hashFunction2(key);
        for (HashNode<K, V> node : chainArray[bucketIndex]) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        chainArray[bucketIndex].add(new HashNode<>(key, value));
        sizeTable++;
    }

    public int size() {
        return sizeTable;
    }
}
