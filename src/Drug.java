import java.math.BigDecimal;
//Drug class
public class Drug {
    private int drugId;
    private String drugName;
    private BigDecimal drugCost;
    private String dosage;

    public Drug(int id, String name, BigDecimal cost, String dose) {
        this.drugId = id;
        this.drugName = name;
        this.drugCost = cost;
        this.dosage = dose;
    }

    public int getDrugId() {
        return drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public BigDecimal getDrugCost() {
        return drugCost;
    }

    public String getDosage() {
        return dosage;
    }

    // format: id|name|cost|dosage
    public String toFileLine() {
        return drugId + "|" + clean(drugName) + "|" + drugCost.toString() + "|" + clean(dosage);
    }

    public static Drug fromFileLine(String line) {
        String[] parts = line.split("\\|");

        if (parts.length != 4) {
            System.out.println("Invalid line: " + line);
            return null;
        }

        try {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            BigDecimal cost = new BigDecimal(parts[2]);
            String dose = parts[3];

            return new Drug(id, name, cost, dose);
        } catch (Exception e) {
            System.out.println("Error reading line: " + line);
            return null;
        }
    }

    @Override
    public String toString() {
        return drugName + " (" + dosage + ") - $" + drugCost;
    }

    // simple helper method
    private String clean(String text) {
        if (text == null) return "";
        return text.replace("|", "/");
    }
}