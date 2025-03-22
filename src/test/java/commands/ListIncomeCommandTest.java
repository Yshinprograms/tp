package commands;

import exceptions.BudgetTrackerException;
import income.Income;
import income.IncomeManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import summary.Summary;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ListIncomeCommandTest {
    private Summary summary;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() throws BudgetTrackerException {
        summary = Summary.getInstance();
        // Reset the summary state to ensure tests don't interfere with each other
        resetSummary();
        IncomeManager.clearIncomeList();
        System.setOut(new PrintStream(outputStream));
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
    void testListIncomeWithEntries() throws BudgetTrackerException {
        IncomeManager.addIncome(new Income(100.0, "Job"));
        IncomeManager.addIncome(new Income(50.0, "Freelance"));
        summary.addIncome(100.0); // Ensure summary gets updated
        summary.addIncome(50.0);

        ListIncomeCommand command = new ListIncomeCommand(summary);
        command.execute();

        String output = outputStream.toString().trim();
        assertTrue(output.contains("1. $100.0 from Job"));
        assertTrue(output.contains("2. $50.0 from Freelance"));
        assertTrue(output.contains("Total Income: $150.0")); // Summary now tracks total income
    }

    @Test
    void testListIncomeWithNoEntries() {
        ListIncomeCommand command = new ListIncomeCommand(summary);
        command.execute();

        assertEquals("No income entries available.", outputStream.toString().trim());
    }
}
