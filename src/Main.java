import java.util.Scanner;
import java.io.*;

public class Main {
    private static final Animal[] animals = {new Tiger(), new Dolphin(), new Penguin()};

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        boolean continueOuterLoop = true;

        do {
            int choice = animalChoiceMenu(keyboard);
            if (choice >= 1 && choice <= 3) {
                handleAnimal(keyboard, animals[choice - 1]);
            } else if (choice == 4) {
                System.out.println("Write the objects to file");
                writeObjectsToFile((Tiger) animals[0], (Penguin) animals[2], (Dolphin) animals[1]);
            } else if (choice == 5) {
                System.out.println("File read successfully");
                readObjectsFromFile();
            } else {
                System.out.println("Sorry no such animal available.");
            }

            System.out.println("Continue main Zoo menu? (Enter 1 for yes/ 2 for no):");
            continueOuterLoop = keyboard.nextInt() == 1;
        } while (continueOuterLoop);
    }

    private static void handleAnimal(Scanner keyboard, Animal animal) {
        boolean continueInnerLoop = true;
        do {
            System.out.println("The animal which is chosen is : " + animal.getNameOfAnimal());
            int menuChoice = animalDetailsManipulationMenu(keyboard, animal);
            
            switch (menuChoice) {
                case 1 -> setAnimalProperties(keyboard, animal);
                case 2 -> displayAnimalProperties(animal);
                case 3 -> performAnimalMovement(animal);
                case 4 -> {
                    animal.eatingFood();
                    animal.eatingCompleted();
                }
                default -> System.out.println("Not supported");
            }
            
            System.out.println("Continue with this animal ? (Enter 1 for yes/ 2 for no):");
            continueInnerLoop = keyboard.nextInt() == 1;
        } while (continueInnerLoop);
    }

    private static void setAnimalProperties(Scanner keyboard, Animal animal) {
        if (animal instanceof Tiger tiger) {
            System.out.println("Enter the number of Stripes:");
            tiger.setNumberOfStripes(keyboard.nextInt());
            System.out.println("Enter speed:");
            tiger.setSpeed(keyboard.nextInt());
            System.out.println("Enter decibel of roar:");
            tiger.setSoundLevel(keyboard.nextInt());
        } else if (animal instanceof Dolphin dolphin) {
            keyboard.nextLine();
            System.out.println("Enter the color of the dolphin:");
            dolphin.setColor(keyboard.nextLine());
            System.out.println("Enter the speed of the dolphin:");
            dolphin.setSwimmingSpeed(keyboard.nextInt());
        } else if (animal instanceof Penguin penguin) {
            System.out.println("Is the penguin swimming (true/false):");
            penguin.setSwimming(keyboard.nextBoolean());
            System.out.println("Enter the walk speed of the penguin:");
            penguin.setWalkSpeed(keyboard.nextInt());
            System.out.println("Enter the swim speed of the penguin:");
            penguin.setSwimSpeed(keyboard.nextInt());
        }
    }

    private static void displayAnimalProperties(Animal animal) {
        System.out.println("The characteristics of the " + animal.getNameOfAnimal() + ":");
        System.out.println("Age: " + animal.getAge());
        System.out.println("Height: " + animal.getHeight());
        System.out.println("Weight: " + animal.getWeight());
        
        if (animal instanceof Tiger tiger) {
            System.out.println("Number of stripes: " + tiger.getNumberOfStripes());
            System.out.println("Speed: " + tiger.getSpeed());
            System.out.println("Sound level of roar: " + tiger.getSoundLevel());
        } else if (animal instanceof Dolphin dolphin) {
            System.out.println("Color: " + dolphin.getColor());
            System.out.println("Speed: " + dolphin.getSwimmingSpeed());
        } else if (animal instanceof Penguin penguin) {
            System.out.println("Walking Speed: " + penguin.getWalkSpeed());
            System.out.println("Swimming Speed: " + penguin.getSwimSpeed());
        }
    }

    private static void performAnimalMovement(Animal animal) {
        if (animal instanceof Tiger tiger) {
            tiger.walking();
        } else if (animal instanceof Dolphin dolphin) {
            dolphin.swimming();
        } else if (animal instanceof Penguin penguin) {
            if (penguin.isSwimming()) {
                penguin.swimming();
            } else {
                penguin.walking();
            }
        }
    }

    private static final String[] MENU_OPTIONS = {
        "Tiger", "Dolphin", "Penguin", "Save animals to file", "Display saved animals from file"
    };

    private static int animalChoiceMenu(Scanner keyboard) {
        System.out.println("******* ZOO ANIMAL choice menu ******");
        for (int i = 0; i < MENU_OPTIONS.length; i++) {
            System.out.println((i + 1) + ". " + MENU_OPTIONS[i]);
        }
        System.out.println("Enter choice of animal (1-" + MENU_OPTIONS.length + "):");
        return keyboard.nextInt();
    }

    private static int animalDetailsManipulationMenu(Scanner keyboard, Animal animal) {
        int choiceGivenByUser;

        System.out.println("******* ANIMAL details menu for: " + animal.getNameOfAnimal() + " ******");
        System.out.println("1. Set properties");
        System.out.println("2. Display properties");
        System.out.println("3. Display movement");
        System.out.println("4. Display eating");
        System.out.println("Enter choice (1-4):");
        choiceGivenByUser = keyboard.nextInt();
        return choiceGivenByUser;

    }

    private static void writeObjectsToFile(Tiger tiger, Penguin penguin, Dolphin dolphin) {
        try {
            ObjectOutputStream oosTiger = new ObjectOutputStream(new FileOutputStream("tiger.txt"));
            ObjectOutputStream oosPenguin = new ObjectOutputStream(new FileOutputStream("penguin.txt"));
            ObjectOutputStream oosDolphin = new ObjectOutputStream(new FileOutputStream("dolphin.txt"));
            oosTiger.writeObject(tiger);
            oosPenguin.writeObject(penguin);
            oosDolphin.writeObject(dolphin);
            System.out.println("Animal state saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void readObjectsFromFile() {
        try {
            ObjectInputStream oosTiger = new ObjectInputStream(new FileInputStream("tiger.txt"));
            ObjectInputStream oosPenguin = new ObjectInputStream(new FileInputStream("penguin.txt"));
            ObjectInputStream oosDolphin = new ObjectInputStream(new FileInputStream("dolphin.txt"));
            Tiger tiger = (Tiger) oosTiger.readObject();
            Penguin penguin = (Penguin) oosPenguin.readObject();
            Dolphin dolphin = (Dolphin) oosDolphin.readObject();
            System.out.println("Tiger data retrieved from file: " + tiger.toString());
            System.out.println("Penguin data retrieved from file: " + penguin.toString());
            System.out.println("Dolphin data retrieved from file:  " + dolphin.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
