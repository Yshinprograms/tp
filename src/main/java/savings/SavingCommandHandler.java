package savings;

import exceptions.BudgetTrackerException;

public class SavingCommandHandler {
    private final Saving saving;

    public SavingCommandHandler(Saving saving) {
        this.saving = saving;
    }

    /**
     * Handles add savings input from user.
     * @param input is user input.
     */
    public void handleAddSavings(String input){
        try {
            String amountPart;
            String goal = "(savings goal not provided)";

            if (input.contains("/")) {
                String[] parts = input.split("/", 2);
                amountPart = parts[0].replaceFirst("add savings", "").trim();
                if (parts.length == 2 && !parts[1].trim().isEmpty()) {
                    goal = parts[1].trim();
                }
            } else {
                amountPart = input.replaceFirst("add savings", "").trim();
            }

            double amount = Double.parseDouble(amountPart);

            saving.addSavings(amount, goal);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException | BudgetTrackerException e) {
            System.out.println("Invalid format.");
        }
    }

    /**
     * Handles delete savings input from user.
     * @param input is user input.
     */
    public void handleDeleteSavings(String input) {
        try {
            if (saving.getSavingsRecords().isEmpty()) {
                System.out.println("No saving records.");
                return;
            }
            String indexStr = input.replaceFirst("delete savings", "").trim();

            if (indexStr.isEmpty()) {
                System.out.println("Please provide an index.");
                return;
            }

            int index = Integer.parseInt(indexStr);
            saving.deleteSavings(index);

        } catch (NumberFormatException e) {
            System.out.println("Invalid index format.");
        } catch (Exception e) {
            System.out.println("An error occurred while deleting savings.");
        }
    }

    /**
     * Handles view savings input from user.
     * @param input is user input.
     */
    public void handleViewSavings(String input) {
        if (input.replaceFirst("view savings", "").trim().isEmpty()) {
            saving.viewSavings();
        } else {
            System.out.println("Invalid command.");
        }
    }

    /**
     * Handles setting a savings goal input from user.
     * @param input is user input.
     */
    public void handleSetSavingsGoal(String input) {
        try {
            if (saving.getSavingsRecords().isEmpty()) {
                System.out.println("No saving records.");
                return;
            }
            String commandKeyword = "savings goal set";

            if (!input.startsWith(commandKeyword)) {
                System.out.println("Invalid command format.");
                return;
            }

            String details = input.substring(commandKeyword.length()).trim();

            int separatorIndex = details.indexOf("/");

            if (separatorIndex == -1) {
                System.out.println("Invalid format. Use: savings goal set <AMOUNT> / <DESCRIPTION>");
                return;
            }

            String amountStr = details.substring(0, separatorIndex).trim();
            String description = details.substring(separatorIndex + 1).trim();

            if (amountStr.isEmpty() || description.isEmpty()) {
                System.out.println("Invalid format. Use: savings goal set <AMOUNT> / <DESCRIPTION>");
                return;
            }

            double amount = Double.parseDouble(amountStr);
            saving.setSavingsGoal(amount, description);

        } catch (NumberFormatException e) {
            System.out.println("Invalid format. Use: savings goal set <AMOUNT> / <DESCRIPTION>");
        } catch (Exception e) {
            System.out.println("An error occurred while setting savings goal.");
        }
    }

    /**
     * Handles delete savings goal input from user.
     * @param input is user input.
     */
    public void handleDeleteSavingsGoal(String input) {
        try {
            if (saving.getSavingsRecords().isEmpty()) {
                System.out.println("No saving records.");
                return;
            }
            String commandKeyword = "savings goal delete";

            if (!input.startsWith(commandKeyword)) {
                System.out.println("Invalid command format.");
                return;
            }

            String indexStr = input.substring(commandKeyword.length()).trim();

            if (indexStr.isEmpty()) {
                System.out.println("Please provide an index.");
                return;
            }

            int index = Integer.parseInt(indexStr);
            saving.deleteSavingsGoal(index);

        } catch (NumberFormatException e) {
            System.out.println("Invalid index format.");
        } catch (BudgetTrackerException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred while deleting savings goal.");
        }
    }

    /**
     * Handles transfer savings input from user.
     * @param input is user input.
     */
    public void handleTransferSavings(String input) {
        try {
            if (saving.getSavingsRecords().isEmpty()) {
                System.out.println("No saving records.");
                return;
            }
            int trimTransferSavingsInUserCommand = 17;
            String[] partsTransfer = input.substring(trimTransferSavingsInUserCommand).split(" ");
            int fromIndex = Integer.parseInt(partsTransfer[0]);
            int toIndex = Integer.parseInt(partsTransfer[1]);
            double amount = Double.parseDouble(partsTransfer[2]);
            saving.transferSavings(fromIndex, toIndex, amount);
        } catch (Exception e) {
            System.out.println("Invalid format. Use: transfer savings <FROM_INDEX> <TO_INDEX> <AMOUNT>");
        }
    }

    /**
     * Handles update savings goal input from user.
     * @param input is user input.
     */
    public void handleUpdateSavingsGoal(String input) {
        try {
            if (saving.getSavingsRecords().isEmpty()) {
                System.out.println("No saving records.");
                return;
            }

            String commandKeyword = "savings goal update";

            if (!input.startsWith(commandKeyword)) {
                System.out.println("Invalid command format.");
                return;
            }

            String details = input.substring(commandKeyword.length()).trim();

            int separatorIndex = details.indexOf(" / ");
            if (separatorIndex == -1) {
                System.out.println("Invalid format. Use: savings goal update <INDEX> <AMOUNT> / <DESCRIPTION>");
                return;
            }

            String indexAmountPart = details.substring(0, separatorIndex).trim();
            String[] indexAmount = indexAmountPart.split(" ");
            if (indexAmount.length != 2) {
                System.out.println("Invalid format. Use: savings goal update <INDEX> <AMOUNT> / <DESCRIPTION>");
                return;
            }

            int index = Integer.parseInt(indexAmount[0].trim()) - 1;
            double amount = Double.parseDouble(indexAmount[1].trim());

            String description = details.substring(separatorIndex + 3).trim();
            if (description.isEmpty()) {
                System.out.println("Description cannot be empty.");
                return;
            }

            saving.updateSavingsGoal(index, amount, description);

        } catch (NumberFormatException e) {
            System.out.println("Invalid format. Use: savings goal update <INDEX> <AMOUNT> / <DESCRIPTION>");
        } catch (Exception e) {
            System.out.println("An error occurred while updating savings goal.");
        }
    }

    /**
     * Handles unknown command from user.
     */
    public void handleUnknownCommand() {
        System.out.println("Unknown command.");
    }

    /**
     * Runs the savings management system, processing user commands.
     * @param input The Scanner object for user input.
     */
    public void processSavingCommand(String input) throws BudgetTrackerException {
        String[] parts = input.split(" ", 5);
        if (parts.length < 2) {
            System.out.println("Invalid command.");
            return;
        }

        if (input.startsWith("add savings")) {
            handleAddSavings(input);
        } else if (input.startsWith("delete savings")) {
            handleDeleteSavings(input);
        } else if (input.startsWith("savings goal set")) {
            handleSetSavingsGoal(input);
        } else if (input.startsWith("savings goal delete")) {
            handleDeleteSavingsGoal(input);
        } else if (input.startsWith("transfer savings")) {
            handleTransferSavings(input);
        }  else if (input.startsWith("view savings")) {
            handleViewSavings(input);
        } else if (input.startsWith("savings goal update")) {
            handleUpdateSavingsGoal(input);
        } else {
            handleUnknownCommand();
        }
    }
}
