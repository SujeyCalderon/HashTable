import models.HashTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HashTable hashTable1 = new HashTable(5000, 1);
        HashTable hashTable2 = new HashTable(5000, 2);

        String line;
        String splitBy = ",";
        int id = 1;

        long totalInsertionTime1 = 0;
        long totalInsertionTime2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("bussines.csv"))) {
            while ((line = br.readLine()) != null) {
                String[] business = line.split(splitBy);
                if (business.length >= 5) {
                    String key = business[1];
                    String value = "ID=" + business[0] + ", Address=" + business[2] +
                            ", City=" + business[3] + ", State=" + business[4];

                    long time1 = hashTable1.calculateInsertionTime(key, value);
                    long time2 = hashTable2.calculateInsertionTime(key, value);

                    totalInsertionTime1 += time1;
                    totalInsertionTime2 += time2;

                    System.out.println("[" + id + "] Business [ID=" + business[0] + ", Name=" +
                            business[1] + ", Address=" + business[2] + ", City=" +
                            business[3] + ", State=" + business[4] + "]");
                    System.out.println("Tiempo para HashFunction1: " + time1 + " ns");
                    System.out.println("Tiempo para HashFunction2: " + time2 + " ns");
                    id++;
                }
            }

            System.out.println("\nTiempo total de inserción usando HashFunction1: " + totalInsertionTime1 + " ns");
            System.out.println("Tiempo total de inserción usando HashFunction2: " + totalInsertionTime2 + " ns");
            if (totalInsertionTime1 < totalInsertionTime2) {
                System.out.println("HashFunction1 fue más eficiente en la inserción.");
            } else {
                System.out.println("HashFunction2 fue más eficiente en la inserción.");
            }

            Scanner scanner = new Scanner(System.in);
            boolean exit = false;
            while (!exit) {
                System.out.println("\nMenu:");
                System.out.println("1. Buscar por nombre");
                System.out.println("2. Mostrar datos por índice");
                System.out.println("3. Salir");
                System.out.print("Selecciona una opción: ");
                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        handleSearchByKey(hashTable1, hashTable2, scanner);
                        break;

                    case 2:
                        handleShowDataByIndex(hashTable1, hashTable2, scanner);
                        break;

                    case 3:
                        exit = true;
                        break;

                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                        break;
                }
            }
            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleSearchByKey(HashTable hashTable1, HashTable hashTable2, Scanner scanner) {
        System.out.print("Introduce el nombre a buscar: ");
        String searchKey = scanner.nextLine().trim();

        try {
            long searchTime1 = measureSearchTime(hashTable1, searchKey);
            long searchTime2 = measureSearchTime(hashTable2, searchKey);

            if (searchTime1 != -1 || searchTime2 != -1) {
                if (searchTime1 != -1) {
                    System.out.println("Nombre '" + searchKey + "' encontrada en Tabla Hash 1. " + hashTable1.searchEntryByKey(searchKey));
                }
                if (searchTime2 != -1) {
                    System.out.println("Nombre '" + searchKey + "' encontrada en Tabla Hash 2. " + hashTable2.searchEntryByKey(searchKey));
                }
            } else {
                System.out.println("Nombre '" + searchKey + "' no encontrada.");
            }

            if (searchTime1 < searchTime2) {
                System.out.println("HashFunction1 fue más eficiente en la búsqueda.");
            } else {
                System.out.println("HashFunction2 fue más eficiente en la búsqueda.");
            }
        } catch (Exception e) {
            System.err.println("Error al buscar el nombre: " + e.getMessage());
        }
    }

    private static long measureSearchTime(HashTable hashTable, String key) {
        long startTime = System.nanoTime();
        String foundValue = hashTable.searchEntryByKey(key);
        long endTime = System.nanoTime();
        return (foundValue != null) ? endTime - startTime : -1;
    }

    private static void handleShowDataByIndex(HashTable hashTable1, HashTable hashTable2, Scanner scanner) {
        System.out.print("Introduce el índice para mostrar datos: ");
        int searchIndex = scanner.nextInt();
        scanner.nextLine();

        try {
            if (searchIndex < 0 || searchIndex >= hashTable1.getTableSize()) {
                System.out.println("Índice fuera de rango. El rango válido es 0 a " + (hashTable1.getTableSize() - 1));
            } else {
                System.out.println("Datos en el índice " + searchIndex + " de la Tabla Hash 1:");
                List<String> data1 = hashTable1.fetchEntriesAtIndex(searchIndex);
                data1.forEach(System.out::println);

                System.out.println("Datos en el índice " + searchIndex + " de la Tabla Hash 2:");
                List<String> data2 = hashTable2.fetchEntriesAtIndex(searchIndex);
                data2.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener los datos del índice: " + e.getMessage());
        }
    }
}
