import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DrugFileRepository {
    private String fileName;

    // constructor - sets file name and makes sure file exists
    public DrugFileRepository(String fileName) {
        this.fileName = fileName;
        checkFile();
    }

    // creates the file if it doesn't exist
    private void checkFile() {
        File f = new File(fileName);

        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + fileName);
            }
        }
    }

    // saves one drug to the file
    public void save(Drug drug) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(drug.toFileLine()); // convert object to line
            bw.newLine(); // go to next line
        } catch (IOException e) {
            System.out.println("Error saving drug");
        }
    }

    // reads all drugs from file and returns them in a list
    public ArrayList<Drug> findAll() {
        ArrayList<Drug> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {

                // skip empty lines
                if (line.equals("")) continue;

                // convert line to Drug object
                Drug d = Drug.fromFileLine(line);

                // only add if valid
                if (d != null) {
                    list.add(d);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file");
        }

        return list;
    }
}