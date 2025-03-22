package summary.ui;

import exceptions.BudgetTrackerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import summary.Summary;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SummaryDisplayTest {
    
    private Summary summary;
    
    @BeforeEach
    void setUp() throws BudgetTrackerException {
        // Get the singleton instance and reset it for each test
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
    void displaySummary_validSummary_correctOutput() throws BudgetTrackerException {
        summary.addIncome(1000.50);
        summary.addExpense(250.25);
        summary.addSavings(100);

        SummaryDisplay display = new SummaryDisplay(summary);

        String expectedOutput =
                "===== BUDGET SUMMARY =====\n" +
                        "Total Income:        $1000.50\n" +
                        "Total Expenses:      $250.25\n" +
                        "Available Balance:   $750.25\n" +
                        "Total Savings:       $100.00\n" +
                        "===========================\n";

        // Normalize line endings AND trim whitespace from each line:
        String expected = normalizeLineEndingsAndTrim(expectedOutput);
        String actual = normalizeLineEndingsAndTrim(display.displaySummary());

        assertEquals(expected, actual);
    }

    @Test
    void displaySummary_emptySummary_correctOutput() {
        SummaryDisplay display = new SummaryDisplay(summary);

        String expectedOutput =
                "===== BUDGET SUMMARY =====\n" +
                        "Total Income:        $0.00\n" +
                        "Total Expenses:      $0.00\n" +
                        "Available Balance:   $0.00\n" +
                        "Total Savings:       $0.00\n" +
                        "===========================\n";


        String expected = normalizeLineEndingsAndTrim(expectedOutput);
        String actual = normalizeLineEndingsAndTrim(display.displaySummary());
        assertEquals(expected, actual);
    }

    // Helper method to normalize line endings and trim whitespace from each line
    private String normalizeLineEndingsAndTrim(String input) {
        String[] lines = input.split("\\r?\\n");
        StringBuilder sb = new StringBuilder();

        for (String line : lines) {
            sb.append(line.trim()).append("\n");
        }

        return sb.toString();
    }
}
