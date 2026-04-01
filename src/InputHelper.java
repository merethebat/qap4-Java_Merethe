import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHelper {

    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number, try again");
            }
        }
    }

    public static BigDecimal readBigDecimal(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();

            try {
                BigDecimal val = new BigDecimal(input);

                if (val.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Number must be >= 0");
                    continue;
                }

                return val;
            } catch (Exception e) {
                System.out.println("Invalid amount");
            }
        }
    }

    public static String readNonEmpty(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();

            if (!input.trim().equals("")) {
                return input.trim();
            }

            System.out.println("Cannot be empty");
        }
    }

    public static LocalDate readDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date (use YYYY-MM-DD)");
            }
        }
    }
}
