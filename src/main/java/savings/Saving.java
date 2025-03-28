package savings;

import java.util.ArrayList;
import java.util.List;

import exceptions.BudgetTrackerException;
import summary.Summary;

/**
 * The Saving class manages savings records, allowing users to add, delete,
 * view, and set goals for their savings.
 */
public class Saving {
    /**
     * Represents a single savings record with an amount and an optional goal.
     */
    private static class SavingsRecord {
        double amount;
        String goal;

        /**
         * Constructs a SavingsRecord with a specified amount.
         * @param amount The savings amount.
         */
        public SavingsRecord(double amount) {
            this.amount = amount;
            this.goal = " ";
        }

        /**
         * Sets the goal for this savings record.
         * @param goal The goal description.
         */
        public void setGoal(String goal) {
            this.goal = goal;
        }

        @Override
        public String toString() {
            return "[" + goal + "] " + amount;
        }
    }

    private final List<SavingsRecord> savingsRecords = new ArrayList<>();
    private Summary summary;

    /**
     * Constructor that accepts a Summary instance.
     * @param summary the shared Summary instance
     */
    public Saving(Summary summary) {
        this.summary = summary;
    }

    /**
     * Adds a savings record with the specified amount.
     * @param amount The amount to save.
     */
    public void addSavings(double amount) throws BudgetTrackerException {
        savingsRecords.add(new SavingsRecord(amount));
        System.out.println("Sure! I have added your savings:");
        System.out.println((savingsRecords.size()) + ". \t" + savingsRecords.get(savingsRecords.size() - 1));
        System.out.println("Now you have " + savingsRecords.size() + " saving(s) in your list.");
        summary.addSavings(amount);
    }

    /**
     * Deletes a savings record at the specified index.
     * @param index The index of the savings record to delete.
     */
    public void deleteSavings(int index) {
        if (index >= 0 && index < savingsRecords.size()) {
            System.out.println("Sure! I have deleted the saving:");
            System.out.println((index + 1) + ". \t" + savingsRecords.get(index));
            savingsRecords.remove(index);
            System.out.println("Now you have " + savingsRecords.size() + " savings in your list.");
            try {
                summary.removeSavings(savingsRecords.get(index).amount);
            } catch (BudgetTrackerException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Invalid index.");
        }
    }

    /**
     * Displays all savings records.
     */
    public void viewSavings() {
        if (savingsRecords.isEmpty()) {
            System.out.println("No savings records.");
        } else {
            System.out.println("Here are the savings in your list:");
            for (int i = 0; i < savingsRecords.size(); i++) {
                System.out.println((i + 1) + ". \t" + savingsRecords.get(i));
            }
            System.out.println("You have " + savingsRecords.size() + " saving(s) in total.");
        }
        System.out.println("Savings Indicator: " + getSavingsIndicator());
    }

    /**
     * Sets a savings goal for a specific amount.
     * @param amount The savings amount to associate with the goal.
     * @param description The goal description.
     */
    public void setSavingsGoal(double amount, String description) {
        for (SavingsRecord record : savingsRecords) {
            if (record.amount == amount) {
                record.setGoal(description);
                System.out.println("I have set your saving goal:");
                System.out.println("[" + description + "] " + record.amount);
                return;
            }
        }
        System.out.println("Invalid amount.");
    }

    /**
     * Updates a savings goal for a specified index.
     * @param index The index of the savings record.
     * @param amount The new savings amount.
     * @param description The new goal description.
     */
    public void updateSavingsGoal(int index, double amount, String description) {
        if (index >= 0 && index < savingsRecords.size()) {
            SavingsRecord record = savingsRecords.get(index);
            record.amount = amount;
            record.goal = description;
            System.out.println("I have updated your saving amount and saving goal:");
            System.out.println((index + 1) + ". \t" + record);
        } else {
            System.out.println("Invalid index.");
        }
    }

    /**
     * Deletes the goal of a specified savings record.
     * @param index The index of the savings record.
     */
    public void deleteSavingsGoal(int index) {
        if (index >= 0 && index < savingsRecords.size()) {
            savingsRecords.get(index).goal = " ";
            System.out.println("I have deleted the saving goal:");
            System.out.println((index + 1) + ". \t" + savingsRecords.get(index));
        } else {
            System.out.println("Invalid index.");
        }
    }

    /**
     * Runs the savings management system, processing user commands.
     * @param input The Scanner object for user input.
     */
    public void run(String input) {
        String[] parts = input.split(" ", 4);
        if (parts.length < 2) {
            System.out.println("Invalid command.");
            return;
        }

        if (input.contains("add savings")) {
            try {
                double amount = Double.parseDouble(parts[2]);
                addSavings(amount);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid amount format.");
            } catch (BudgetTrackerException e) {
                throw new RuntimeException(e);
            }
        } else if (input.contains("delete savings")) {
            try {
                int index = Integer.parseInt(parts[2]) - 1;
                deleteSavings(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid index format.");
            }
        } else if (input.contains("savings goal set")) {
            try {
                String[] goalParts = input.substring(17).split(" / ", 2);
                double amount = Double.parseDouble(goalParts[0].trim());
                String description = goalParts[1].trim();
                setSavingsGoal(amount, description);
            } catch (Exception e) {
                System.out.println("Invalid format. Use: savings goal set <AMOUNT> / <DESCRIPTION>");
            }
        } else if (input.contains("savings goal update")) {
            try {
                String[] goalParts = input.substring(20).split(" / ", 2);
                String[] indexAmount = goalParts[0].trim().split(" ");
                int index = Integer.parseInt(indexAmount[0]) - 1;
                double amount = Double.parseDouble(indexAmount[1]);
                String description = goalParts[1].trim();
                updateSavingsGoal(index, amount, description);
            } catch (Exception e) {
                System.out.println("Invalid format. Use: savings goal update <INDEX> <AMOUNT> / <DESCRIPTION>");
            }
        } else if (input.contains("savings goal delete")) {
            try {
                int index = Integer.parseInt(parts[3]) - 1;
                deleteSavingsGoal(index);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid index format.");
            }
        }  else if (input.contains("exit savings")) {
            System.out.println("Exited savings function.");
        } else if (input.contains("view savings") || input.contains("savings goal view")) {
            viewSavings();
        } else {
            System.out.println("Unknown command.");
        }
    }
    /**
     * Determines the savings indicator based on the total income.
     * @return "Good" if savings are above 80% of income, "Bad" if below 50%, otherwise "Neutral".
     */
    public String getSavingsIndicator() {
        double totalIncome = summary.getTotalIncome(); // Get total income from Summary
        double totalSavings = 0;

        for (SavingsRecord record : savingsRecords) {
            totalSavings += record.amount;
        }

        if (totalIncome == 0) {
            return "No income recorded.";
        }

        double savingsRatio = totalSavings / totalIncome;

        if (savingsRatio >= 0.8) {
            return "Good - You are saving well!";
        } else if (savingsRatio < 0.5) {
            return "Bad - Try to save more.";
        } else {
            return "Neutral - You are on track.";
        }
    }

}
