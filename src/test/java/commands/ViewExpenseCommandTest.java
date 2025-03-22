package commands;

import exceptions.BudgetTrackerException;
import income.IncomeManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import expenses.ExpenseList;
import expenses.Ui;
import summary.Summary;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ViewExpenseCommandTest {
    private ExpenseList expenseList;
    private Ui ui;
    private Summary summary;

    @BeforeEach
    void setUp() throws BudgetTrackerException {
        expenseList = new ExpenseList();
        ui = new Ui();
        summary = Summary.getInstance();
        // Reset the summary state to ensure tests don't interfere with each other
        resetSummary();
    }
    
    /**
     * Helper method to reset the Summary object to initial state
     */
    private void resetSummary() throws BudgetTrackerException {
        // Reset income if needed
        if (summary.getTotalIncome() > 0) {
            summary.removeIncome(summary.getTotalIncome());
        }
        
        // Reset expenses if needed
        if (summary.getTotalExpense() > 0) {
            summary.removeExpense(summary.getTotalExpense());
        }
        
        // Reset savings if needed
        if (summary.getTotalSavings() > 0) {
            summary.removeSavings(summary.getTotalSavings());
        }
    }

    @Test
    public void testViewExpense() throws BudgetTrackerException {
        AddIncomeCommand command = new AddIncomeCommand(100.0, "Salary", summary);
        command.incomeExecute(IncomeManager.getInstance(), ui);
        double amount = 50.0;
        String description = "Lunch";
        AddExpenseCommand command1 = new AddExpenseCommand(amount, description, summary);
        command1.execute(expenseList, ui);
        ViewExpenseCommand command2 = new ViewExpenseCommand(expenseList);
        command2.execute(expenseList, ui);

        assertEquals(1, expenseList.getExpenses().size(), "Expense list should contain one expense.");
        assertEquals(amount, expenseList.getExpenses().get(0).getAmount());
        assertEquals(description, expenseList.getExpenses().get(0).getDescription());
    }
}
