import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String line;
        String splitBy = ",";
        int id = 1;

        HashTable<String, String[]> hashTable1 = new HashTable<>();
        HashTable<String, String[]> hashTable2 = new HashTable<>();

        long startTime1 = 0, endTime1 = 0;
        long startTime2 = 0, endTime2 = 0;
        long totalTime1 = 0, totalTime2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("bussines.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] business = line.split(splitBy);
                System.out.println("[" + id + "] Business [ID=" + business[0] + ", Name=" + business[1] + ", Address=" + business[2] + ", City=" + business[3] + ", State= " + business[4] + "]");

                id++;

                String key = business[0]; //Toma el ID como key

                // Medir tiempo para hashTable1
                startTime1 = System.nanoTime();
                hashTable1.put(key, business, true);
                endTime1 = System.nanoTime();
                totalTime1 += (endTime1 - startTime1);

                // Medir tiempo para hashTable2
                startTime2 = System.nanoTime();
                hashTable2.put(key, business, false);
                endTime2 = System.nanoTime();
                totalTime2 += (endTime2 - startTime2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Tiempo total para hashTable1.put: " + totalTime1 + " nanosegundos");
        System.out.println("Tiempo total para hashTable2.put: " + totalTime2 + " nanosegundos");

        if (totalTime1 < totalTime2) {
            System.out.println("hashTable1.put es más eficiente.");
        } else {
            System.out.println("hashTable2.put es más eficiente.");
        }
    }
}
