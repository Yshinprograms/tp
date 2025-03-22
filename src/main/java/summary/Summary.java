package summary;

import exceptions.BudgetTrackerException;

/**
 * Represents a financial summary, storing total income, expenses, balance, and savings.
 * Provides methods for adding and removing income, expenses, and savings.
 * Implemented as a Singleton to ensure only one instance exists in the application.
 */
public class Summary {
    private double totalIncome;
    private double totalExpense;
    private double totalSavings;

    private static Summary instance;
    
    /**
     * Private constructor to prevent external instantiation.
     * Constructs a new Summary object with all values initialized to 0.
     */
    private Summary() {
        totalIncome = 0;
        totalExpense = 0;
        totalSavings = 0;

        assert totalIncome == 0 : "Initial income should be 0";
        assert totalExpense == 0 : "Initial expense should be 0";
        assert totalSavings == 0 : "Initial savings should be 0";
    }
    
    /**
     * Gets the singleton instance of Summary, creating it if it doesn't exist.
     * 
     * @return The singleton instance of Summary.
     */
    public static Summary getInstance() {
        if (instance == null) {
            instance = new Summary();
        }
        return instance;
    }

    /**
     * Gets the total income.
     *
     * @return The total income.
     */
    public double getTotalIncome() {
        assert totalIncome >= 0 : "Total income should never be negative";
        return totalIncome;
    }

    /**
     * Gets the total expenses.
     *
     * @return The total expenses.
     */
    public double getTotalExpense() {
        assert totalExpense >= 0 : "Total expense should never be negative";
        return totalExpense;
    }

    /**
     * Gets the spendable funds (calculated as income - expenses - savings).
     * This represents the amount available for spending or saving.
     *
     * @return The available funds.
     */
    public double getSpendableFunds() {
        return totalIncome - totalExpense - totalSavings;
    }

    /**
     * Gets the total balance (calculated as income - expenses - savings).
     * This represents the amount left after setting aside savings.
     *
     * @return The total balance.
     */
    public double getTotalBalance() {
        return getSpendableFunds() - totalSavings;
    }

    /**
     * Gets the total savings.
     *
     * @return The total savings.
     */
    public double getTotalSavings() {
        assert totalSavings >= 0 : "Total savings should never be negative";
        return totalSavings;
    }


    /**
     * Adds income to the total income.
     *
     * @param income The amount of income to add.
     * @throws BudgetTrackerException If the income is negative.
     */
    public void addIncome(double income) throws BudgetTrackerException {
        if (income < 0) {
            throw new BudgetTrackerException("Income cannot be negative.");
        }
        double oldIncome = this.totalIncome;
        this.totalIncome += income;

        assert this.totalIncome == oldIncome + income : "Income was not added correctly";
    }

    /**
     * Removes income from the total income.
     *
     * @param income The amount of income to remove.
     * @throws BudgetTrackerException If the income is negative or greater than the current total income,
     *     or if removing it would result in a negative balance.
     */
    public void removeIncome(double income) throws BudgetTrackerException {
        if (income < 0) {
            throw new BudgetTrackerException("Income cannot be negative.");
        }
        if (income > this.totalIncome) {
            throw new BudgetTrackerException("Cannot remove more income than the current total income.");
        }
        
        // Check if removing income would result in a negative balance
        double potentialNewTotalIncome = this.totalIncome - income;
        double potentialNewBalance = potentialNewTotalIncome - this.totalExpense - this.totalSavings;
        
        if (potentialNewBalance < 0) {
            throw new BudgetTrackerException("Cannot remove this income as it would result in a negative balance. "
                    + "Please reduce your expenses or savings first.");
        }
        
        double oldIncome = this.totalIncome;
        this.totalIncome -= income;

        assert this.totalIncome == oldIncome - income : "Income was not removed correctly";
        assert this.totalIncome >= 0 : "Total income should never be negative after removal";
    }

    /**
     * Adds an expense to the total expenses.
     *
     * @param expense The amount of the expense to add.
     * @throws BudgetTrackerException If the expense is negative or would result in a negative balance.
     */
    public void addExpense(double expense) throws BudgetTrackerException {
        if (expense < 0) {
            throw new BudgetTrackerException("Expense cannot be negative.");
        }
        
        // Check if adding this expense would result in a negative balance
        double availableBalance = getTotalBalance();
        if (expense > availableBalance) {
            throw new BudgetTrackerException("Cannot add this expense as it would exceed your available funds. "
                    + "Available balance: " + availableBalance);
        }
        
        double oldExpense = this.totalExpense;
        this.totalExpense += expense;

        assert this.totalExpense == oldExpense + expense : "Expense was not added correctly";
    }

    /**
     * Removes an expense from the total expenses.
     *
     * @param expense The amount of the expense to remove.
     * @throws BudgetTrackerException If the expense is negative or greater than the current total expenses.
     */
    public void removeExpense(double expense) throws BudgetTrackerException {
        if (expense < 0) {
            throw new BudgetTrackerException("Expense cannot be negative.");
        }
        if (expense > this.totalExpense) {
            throw new BudgetTrackerException("Cannot remove more expense than the current total expenses.");
        }
        double oldExpense = this.totalExpense;
        this.totalExpense -= expense;

        assert this.totalExpense == oldExpense - expense : "Expense was not removed correctly";
        assert this.totalExpense >= 0 : "Total expense should never be negative after removal";
    }

    /**
     * Adds savings to the total savings.
     *
     * @param savings The amount of savings to add.
     * @throws BudgetTrackerException If the savings are negative or greater than spendable funds.
     */
    public void addSavings(double savings) throws BudgetTrackerException {
        if (savings < 0) {
            throw new BudgetTrackerException("Savings cannot be negative.");
        }

        double spendableFunds = getSpendableFunds();
        
        if (savings > spendableFunds) {
            throw new BudgetTrackerException("Savings cannot be greater than available funds.");
        }
        double oldSavings = this.totalSavings;
        this.totalSavings += savings;

        assert this.totalSavings == oldSavings + savings : "Savings was not added correctly";
    }

    /**
     * Removes savings from the total savings.
     *
     * @param savings The amount of savings to remove.
     * @throws BudgetTrackerException If the savings are negative or greater than the current total savings.
     */
    public void removeSavings(double savings) throws BudgetTrackerException {
        if (savings < 0) {
            throw new BudgetTrackerException("Savings cannot be negative.");
        }
        if (savings > this.totalSavings) {
            throw new BudgetTrackerException("Cannot remove more savings than the current total savings.");
        }
        double oldSavings = this.totalSavings;
        this.totalSavings -= savings;

        assert this.totalSavings == oldSavings - savings : "Savings was not removed correctly";
        assert this.totalSavings >= 0 : "Total savings should never be negative after removal";
    }
}
