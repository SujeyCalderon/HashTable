public class HashNode<K, V> {
    K key;
    V value;
    HashNode<K, V> next; // Pasa al siguiente nodo en caso de colisión

    public HashNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}