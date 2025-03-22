package commands;

import exceptions.BudgetTrackerException;
import income.Income;
import income.IncomeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import summary.Summary;
import expenses.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteIncomeCommandTest {
    private Summary summary;
    private Ui ui;

    @BeforeEach
    void setUp() throws BudgetTrackerException {
        summary = Summary.getInstance();
        // Reset the summary state to ensure tests don't interfere with each other
        resetSummary();
        ui = new Ui(); // Initialize the UI object
        IncomeManager.clearIncomeList();

        // Add some income entries
        IncomeManager.addIncome(new Income(200.0, "Freelance"));
        IncomeManager.addIncome(new Income(50.0, "Gift"));

        // Add income to the summary
        summary.addIncome(200.0);
        summary.addIncome(50.0);
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
    void testDeleteValidIncome() throws BudgetTrackerException {
        DeleteIncomeCommand command = new DeleteIncomeCommand(1, summary);
        command.incomeExecute(IncomeManager.getInstance(), ui);

        // Validate that the income list has the correct size and values after deletion
        assertEquals(1, IncomeManager.getIncomeList().size());
        assertEquals(50.0, IncomeManager.getIncomeList().get(0).getAmount());
        assertEquals(50.0, summary.getTotalIncome());
    }

    @Test
    void testDeleteInvalidIndexThrowsException() {
        // Try to delete an income with an invalid index (greater than the list size)
        Exception exception = assertThrows(BudgetTrackerException.class, () ->
                new DeleteIncomeCommand(5, summary).incomeExecute(IncomeManager.getInstance(), ui)
        );
        assertEquals("Invalid index. Please provide a valid income index between 1 and 2.", exception.getMessage());
    }
}
