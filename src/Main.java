import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // file for drugs
    private static final String DRUG_FILE = "drugs.txt";

    // database info 
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASS = "your_password";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        DrugFileRepository drugRepo = new DrugFileRepository(DRUG_FILE);

        // try to create DB repo 
        PatientDbRepository patientRepo = null;

        try {
            patientRepo = new PatientDbRepository(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            System.out.println("Database not connected (only file part will work)");
        }

        boolean running = true;

        while (running) {
            printMenu();

            int choice = InputHelper.readInt(sc, "Choose option (0-4): ");

            switch (choice) {
                case 1:
                    saveDrug(sc, drugRepo);
                    break;
                case 2:
                    showDrugs(drugRepo);
                    break;
                case 3:
                    if (patientRepo != null) {
                        savePatient(sc, patientRepo);
                    } else {
                        System.out.println("Database not available");
                    }
                    break;
                case 4:
                    if (patientRepo != null) {
                        showPatients(patientRepo);
                    } else {
                        System.out.println("Database not available");
                    }
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice");
            }

            System.out.println();
        }

        sc.close();
    }

    // prints menu
    private static void printMenu() {
        System.out.println("==== Menu ====");
        System.out.println("1 - Save Drug (file)");
        System.out.println("2 - Show Drugs");
        System.out.println("3 - Save Patient (DB)");
        System.out.println("4 - Show Patients");
        System.out.println("0 - Exit");
    }

    // save drug to file
    private static void saveDrug(Scanner sc, DrugFileRepository repo) {
        System.out.println("Enter drug info:");

        int id = InputHelper.readInt(sc, "ID: ");
        String name = InputHelper.readNonEmpty(sc, "Name: ");
        BigDecimal cost = InputHelper.readBigDecimal(sc, "Cost: ");
        String dosage = InputHelper.readNonEmpty(sc, "Dosage: ");

        Drug d = new Drug(id, name, cost, dosage);
        repo.save(d);

        System.out.println("Drug saved");
    }

    // show drugs
    private static void showDrugs(DrugFileRepository repo) {
        System.out.println("Drugs:");

        ArrayList<Drug> list = repo.findAll();

        if (list.size() == 0) {
            System.out.println("No drugs found");
            return;
        }

        for (Drug d : list) {
            System.out.println(d);
        }
    }

    // save patient to DB
    private static void savePatient(Scanner sc, PatientDbRepository repo) {
        System.out.println("Enter patient info:");

        int id = InputHelper.readInt(sc, "ID: ");
        String first = InputHelper.readNonEmpty(sc, "First name: ");
        String last = InputHelper.readNonEmpty(sc, "Last name: ");
        LocalDate dob = InputHelper.readDate(sc, "DOB (YYYY-MM-DD): ");

        Patient p = new Patient(id, first, last, dob);
        repo.save(p);

        System.out.println("Patient saved");
    }

    // show patients
    private static void showPatients(PatientDbRepository repo) {
        System.out.println("Patients:");

        ArrayList<Patient> list = repo.findAll();

        if (list.size() == 0) {
            System.out.println("No patients found");
            return;
        }

        for (Patient p : list) {
            System.out.println(p);
        }
    }
}
