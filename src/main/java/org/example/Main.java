package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Toy {
    private int id;
    private String name;
    private int quantity;
    private int weight;
    private int frequency;

    public Toy(int id, String name, int quantity, int weight, int frequency) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getWeight() {
        return weight;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class ToyStore {
    private List<Toy> toyList;
    private static final String FILENAME = "toys.txt";

    public ToyStore() {
        toyList = new ArrayList<>();
        File prizesFile = new File("prizes.txt");
        if (!prizesFile.exists()) {
            try {
                prizesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addToy() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите тип игрушки:");
        System.out.println("1 - конструктор");
        System.out.println("2 - робот");
        System.out.println("3 - кукла");
        int type = scanner.nextInt();
        scanner.nextLine();
        String name;
        switch (type) {
            case 1:
                name = "конструктор";
                break;
            case 2:
                name = "робот";
                break;
            case 3:
                name = "кукла";
                break;
            default:
                System.out.println("Вы выбрали неверный тип игрушки.");
                return;
        }
        System.out.println("Вы выбрали " + name);
        System.out.println("Введите название:");
        String toyName = scanner.nextLine();
        System.out.println("Введите количество:");
        int quantity = scanner.nextInt();
        System.out.println("Введите вес:");
        int weight = scanner.nextInt();
        System.out.println("Введите шанс выпадения в %:");
        int frequency = scanner.nextInt();
        Toy toy = new Toy(type, toyName, quantity, weight, frequency);
        toyList.add(toy);
        writeToFile(toy);
    }

    private void writeToFile(Toy toy) {
        try {
            FileWriter writer = new FileWriter("prizes.txt", true);
            writer.write(toy.getName() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void editToy() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите тип игрушки:");
        System.out.println("1 - конструктор");
        System.out.println("2 - робот");
        System.out.println("3 - кукла");
        int type = scanner.nextInt();
        scanner.nextLine();
        String name;
        switch (type) {
            case 1:
                name = "конструктор";
                break;
            case 2:
                name = "робот";
                break;
            case 3:
                name = "кукла";
                break;
            default:
                System.out.println("Вы выбрали неверный тип игрушки.");
                return;
        }
        System.out.println("Вы выбрали " + name);
        System.out.println("Введите что вы хотите изменить:");
        System.out.println("1 - название");
        System.out.println("2 - количество");
        System.out.println("3 - вес");
        System.out.println("4 - шанс выпадения");
        int option = scanner.nextInt();
        scanner.nextLine();
        Toy selectedToy = null;
        for (Toy toy : toyList) {
            if (toy.getId() == type) {
                selectedToy = toy;
                break;
            }
        }
        if (selectedToy == null) {
            System.out.println("Такой игрушки не существует.");
            return;
        }
        switch (option) {
            case 1:
                System.out.println("Введите название, которое хотите изменить:");
                String currentName = scanner.nextLine();
                if (!selectedToy.getName().equals(currentName)) {
                    System.out.println("Такого названия не существует.");
                    return;
                }
                System.out.println("Введите новое название:");
                String newName = scanner.nextLine();
                selectedToy.setName(newName);
                System.out.println("Название изменено.");
                break;
            case 2:
                System.out.println("Введите новое количество:");
                int newQuantity = scanner.nextInt();
                selectedToy.setQuantity(newQuantity);
                System.out.println("Количество изменено.");
                break;
            case 3:
                System.out.println("Введите новый вес:");
                int newWeight = scanner.nextInt();
                selectedToy.setWeight(newWeight);
                System.out.println("Вес изменен.");
                break;
            case 4:
                System.out.println("Введите новый шанс выпадения в %:");
                int newFrequency = scanner.nextInt();
                selectedToy.setFrequency(newFrequency);
                System.out.println("Шанс выпадения изменен.");
                break;
            default:
                System.out.println("Вы выбрали неверный вариант.");
                break;
        }
    }

    public void get() {
        List<Toy> prizes = new ArrayList<>();
        Random random = new Random();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                int weight = Integer.parseInt(parts[3]);
                int frequency = Integer.parseInt(parts[4]);
                Toy toy = new Toy(id, name, quantity, weight, frequency);
                toyList.add(toy);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (toyList.isEmpty()) {
            System.out.println("Список игрушек пуст.");
            return;
        }
        for (int i = 0; i < 10; i++) {
            int randomNumber = random.nextInt(100) + 1;
            Toy selectedToy = null;
            int cumulativeFrequency = 0;
            for (Toy toy : toyList) {
                cumulativeFrequency += toy.getFrequency();
                if (randomNumber <= cumulativeFrequency) {
                    selectedToy = toy;
                    break;
                }
            }
            if (selectedToy != null) {
                prizes.add(selectedToy);
                selectedToy.setQuantity(selectedToy.getQuantity() - 1);
                removeToyFromFile(selectedToy);
            }
            if (selectedToy != null) {
                toyList.remove(selectedToy);
                selectedToy.setQuantity(selectedToy.getQuantity() - 1);
                printToy(selectedToy);
            }
        }
        writePrizesToFile(prizes);
    }
    private void printToy(Toy toy) {
        System.out.println("Выпала игрушка: " + toy.getName());
        writeToFile(toy);
    }

    private void removeToyFromFile(Toy toy) {
        try {
            List<String> lines = new ArrayList<>();
            Scanner scanner = new Scanner(new FileReader(FILENAME));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                if (id != toy.getId()) {
                    lines.add(line);
                } else {
                    int quantity = Integer.parseInt(parts[2]);
                    quantity--;
                    if (quantity > 0) {
                        line = parts[0] + "," + parts[1] + "," + quantity + "," + parts[3] + "," + parts[4];
                        lines.add(line);
                    }
                }
            }
            scanner.close();
            try (FileWriter writer = new FileWriter(FILENAME)) {
                for (String line : lines) {
                    writer.write(line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writePrizesToFile(List<Toy> prizes) {
        try (FileWriter writer = new FileWriter("prizes.txt")) {
            for (Toy toy : prizes) {
                writer.write(toy.getName() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ToyStore toyStore = new ToyStore();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите команду: /add, /edit, /get");
        System.out.println("Введите 'done', чтобы закончить.");
        String input = scanner.nextLine();
        while (!input.equals("done")) {
            if (input.equals("/add")) {
                toyStore.addToy();
            } else if (input.equals("/edit")) {
                toyStore.editToy();
            } else if (input.equals("/get")) {
                toyStore.get();
            } else {
                System.out.println("Вы ввели неверную команду.");
            }
            input = scanner.nextLine();
        }
    }
}