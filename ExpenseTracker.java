import java.util.*;

public class ExpenseTracker {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, Double> expenses = new LinkedHashMap<>();

        System.out.println("===== SIMPLE EXPENSE TRACKER =====");
        System.out.print("Enter current month name (e.g., January): ");
        String month = sc.nextLine().trim();

        System.out.print("Enter type of expense tracking (Weekly/Monthly): ");
        String type = sc.nextLine().trim();

        // Income for savings calculation
        System.out.print("Enter your income for " + month + ": ");
        double income = sc.nextDouble();

        // Predefined categories
        String[] categories = {"Food", "Travel", "Entertainment", "Bills", "Others"};

        // Input current expenses
        for (String category : categories) {
            System.out.print("Enter " + type + " expense for " + category + ": ");
            double value = sc.nextDouble();
            expenses.put(category, value);
        }

        // Total current expenses
        double total = expenses.values().stream().mapToDouble(Double::doubleValue).sum();
        double monthlyTotal = type.equalsIgnoreCase("Weekly") ? total * 4 : total;
        double savings = income - monthlyTotal;

        // Past month data
        System.out.print("\nDo you want to enter past month’s total expenses? (yes/no): ");
        sc.nextLine(); // consume leftover newline
        String choice = sc.nextLine().trim();

        double pastMonthTotal = -1;
        double diff = 0;
        String pastMonth = "";
        if (choice.equalsIgnoreCase("yes")) {
            System.out.print("Enter past month name: ");
            pastMonth = sc.nextLine().trim();
            System.out.print("Enter past month’s total expenses: ");
            pastMonthTotal = sc.nextDouble();
            diff = monthlyTotal - pastMonthTotal;
        }

        // Print header
        System.out.println("\n===== " + month.toUpperCase() + " EXPENSE SUMMARY =====");
        System.out.printf("%-15s | %-12s | %-12s | %-8s\n", "Category", type + " Amt", "Monthly Est.", "Percent");
        System.out.println("---------------------------------------------------------------");

        // Show each category
        for (String category : expenses.keySet()) {
            double value = expenses.get(category);
            double percent = (monthlyTotal > 0) ? (value / monthlyTotal) * 100 : 0;
            double monthlyEstimate = type.equalsIgnoreCase("Weekly") ? value * 4 : value;
            System.out.printf("%-15s | %-12.2f | %-12.2f | %7.2f%%\n",
                              category, value, monthlyEstimate, percent);
        }

        // Totals row
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-15s | %-12.2f | %-12.2f | %7s\n",
                          "TOTAL", total, monthlyTotal, "100%");

        // Savings row
        System.out.println("---------------------------------------------------------------");
        System.out.printf("%-15s : %.2f\n", "Income", income);
        System.out.printf("%-15s : %.2f\n", "Savings", savings);

        // Past month comparison
        if (pastMonthTotal >= 0) {
            System.out.println("\n===== COMPARISON WITH " + pastMonth.toUpperCase() + " =====");
            if (diff > 0) {
                System.out.printf("You spent %.2f MORE than last month.\n", diff);
            } else if (diff < 0) {
                System.out.printf("You spent %.2f LESS than last month.\n", -diff);
            } else {
                System.out.println("Your spending is the SAME as last month.");
            }
        }

        sc.close();
    }
}
