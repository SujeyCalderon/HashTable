package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashTable {
    private LinkedList<String>[] hashTableArray;
    private int tableSize;
    private int hashFunctionId;
    private models.HashFunction1 hashFunction1;
    private models.HashFunction2 hashFunction2;

    public HashTable(int tableSize, int hashFunctionId) {
        this.tableSize = tableSize;
        this.hashFunctionId = hashFunctionId;
        hashTableArray = new LinkedList[tableSize];
        for (int i = 0; i < tableSize; i++) {
            hashTableArray[i] = new LinkedList<>();
        }
        hashFunction1 = new models.HashFunction1();
        hashFunction2 = new models.HashFunction2();
    }

    public int getTableSize() {
        return tableSize;
    }

    public void addEntry(String key, String value) {
        int index = generateHashIndex(key);
        String entry = key + ":" + value;
        hashTableArray[index].add(entry);
    }

    public List<String> fetchEntriesAtIndex(int index) {
        List<String> entries = new ArrayList<>();
        if (index < 0 || index >= tableSize) {
            return entries;
        }
        for (String entry : hashTableArray[index]) {
            int separatorIndex = entry.indexOf(':');
            if (separatorIndex != -1) {
                String key = entry.substring(0, separatorIndex);
                String value = entry.substring(separatorIndex + 1);
                entries.add("Key: " + key + ", Value: " + value);
            }
        }
        return entries;
    }

    public String searchEntryByKey(String key) {
        String searchKey = key + ":";
        for (int i = 0; i < tableSize; i++) {
            for (String entry : hashTableArray[i]) {
                if (entry.startsWith(searchKey)) {
                    return "Índice: " + i + ", Valor: " + entry.substring(searchKey.length());
                }
            }
        }
        return null;
    }

    private int generateHashIndex(String key) {
        if (hashFunctionId == 1) {
            return hashFunction1.hash(key) % tableSize;
        } else {
            return hashFunction2.hash(key, tableSize);
        }
    }

    public long calculateInsertionTime(String key, String value) {
        long startTime = System.nanoTime();
        addEntry(key, value);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
}
