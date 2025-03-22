package summary;

import exceptions.BudgetTrackerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

class SummaryTest {
    
    private Summary summary;
    
    @BeforeEach
    void setUp() throws BudgetTrackerException {
        summary = Summary.getInstance();
        resetSummary();
    }
    
    private void resetSummary() throws BudgetTrackerException {
        // First clear expense and savings to avoid negative balance error
        if (summary.getTotalExpense() > 0) {
            summary.removeExpense(summary.getTotalExpense());
        }
        
        if (summary.getTotalSavings() > 0) {
            summary.removeSavings(summary.getTotalSavings());
        }
        
        // Now we can safely remove income
        if (summary.getTotalIncome() > 0) {
            summary.removeIncome(summary.getTotalIncome());
        }
    }

    @Test
    void getInstance_multipleInvocations_expectSameInstance() {
        Summary firstInstance = Summary.getInstance();
        Summary secondInstance = Summary.getInstance();
        assertSame(firstInstance, secondInstance, "Multiple calls to getInstance() should return the same instance");
    }

    @Test
    void addIncome_positiveAmount_expectCorrectIncrease() throws BudgetTrackerException {
        summary.addIncome(200.0);
        assertEquals(200.0, summary.getTotalIncome(), 0.001, "Income should be added correctly");
    }
    
    @Test
    void addIncome_negativeAmount_expectException() {
        assertThrows(BudgetTrackerException.class, 
                () -> summary.addIncome(-100.0), 
                "Negative income should be rejected");
    }
    
    @Test
    void removeIncome_validAmount_expectCorrectDecrease() throws BudgetTrackerException {
        summary.addIncome(300.0);
        summary.removeIncome(100.0);
        assertEquals(200.0, summary.getTotalIncome(), 0.001, "Income should be removed correctly");
    }
    
    @Test
    void addExpense_positiveAmount_expectCorrectIncrease() throws BudgetTrackerException {
        summary.addIncome(500.0);
        summary.addExpense(200.0);
        assertEquals(200.0, summary.getTotalExpense(), 0.001, "Expense should be added correctly");
    }
    
    @Test
    void addExpense_exceedingAvailableFunds_expectException() throws BudgetTrackerException {
        summary.addIncome(100.0);
        assertThrows(BudgetTrackerException.class, 
                () -> summary.addExpense(150.0), 
                "Expense exceeding available funds should be rejected");
    }
    
    @Test
    void removeExpense_validAmount_expectCorrectDecrease() throws BudgetTrackerException {
        summary.addIncome(500.0);
        summary.addExpense(300.0);
        summary.removeExpense(100.0);
        assertEquals(200.0, summary.getTotalExpense(), 0.001, "Expense should be removed correctly");
    }
    
    @Test
    void addSavings_positiveAmount_expectCorrectIncrease() throws BudgetTrackerException {
        summary.addIncome(500.0);
        summary.addSavings(200.0);
        assertEquals(200.0, summary.getTotalSavings(), 0.001, "Savings should be added correctly");
    }
    
    @Test
    void addSavings_exceedingAvailableFunds_expectException() throws BudgetTrackerException {
        summary.addIncome(100.0);
        assertThrows(BudgetTrackerException.class, 
                () -> summary.addSavings(150.0), 
                "Savings exceeding available funds should be rejected");
    }
    
    @Test
    void removeSavings_validAmount_expectCorrectDecrease() throws BudgetTrackerException {
        summary.addIncome(500.0);
        summary.addSavings(300.0);
        summary.removeSavings(100.0);
        assertEquals(200.0, summary.getTotalSavings(), 0.001, "Savings should be removed correctly");
    }
    
    @Test
    void getSpendableFunds_afterOperations_expectCorrectCalculation() throws BudgetTrackerException {
        summary.addIncome(500.0);
        assertEquals(500.0, summary.getSpendableFunds(), 0.001, "Available funds should increase with income");
        
        summary.addExpense(200.0);
        assertEquals(300.0, summary.getSpendableFunds(), 0.001, "Available funds should decrease with expense");
        
        summary.addSavings(100.0);
        assertEquals(200.0, summary.getSpendableFunds(), 0.001, "Available funds should decrease with savings");
    }
    
    @Test
    void getTotalBalance_afterOperations_expectCorrectCalculation() throws BudgetTrackerException {
        summary.addIncome(500.0);
        assertEquals(500.0, summary.getTotalBalance(), 0.001, "Balance should increase with income");
        
        summary.addExpense(200.0);
        assertEquals(300.0, summary.getTotalBalance(), 0.001, "Balance should decrease with expense");
        
        summary.addSavings(100.0);
        assertEquals(200.0, summary.getTotalBalance(), 0.001, "Balance should decrease with savings");
    }
    
    @Test
    void financialSummary_complexOperations_expectCorrectFinalState() throws BudgetTrackerException {
        summary.addIncome(1000.0);
        summary.addExpense(200.0);
        summary.addExpense(150.0);
        summary.addSavings(300.0);
        summary.addIncome(500.0);
        summary.removeExpense(50.0);
        
        assertEquals(1500.0, summary.getTotalIncome(), 0.001, "Total income should be calculated correctly");
        assertEquals(300.0, summary.getTotalExpense(), 0.001, "Total expense should be calculated correctly");
        assertEquals(300.0, summary.getTotalSavings(), 0.001, "Total savings should be calculated correctly");
        assertEquals(1200.0, summary.getSpendableFunds(), 0.001, "Available funds should be calculated correctly");
        assertEquals(900.0, summary.getTotalBalance(), 0.001, "Total balance should be calculated correctly");
    }
    
    @Test
    void removeIncome_withExistingExpenseAndSavings_expectRemainingFunds() throws BudgetTrackerException {
        summary.addIncome(1000.0);
        summary.addExpense(300.0);
        summary.addSavings(200.0);
        summary.removeIncome(400.0);
        
        assertEquals(600.0, summary.getTotalIncome(), 0.001, "Income should be reduced");
        assertEquals(300.0, summary.getTotalExpense(), 0.001, "Expense should remain unchanged");
        assertEquals(200.0, summary.getTotalSavings(), 0.001, "Savings should remain unchanged");
        assertEquals(100.0, summary.getTotalBalance(), 0.001, "Balance should be correctly calculated");
    }
    
    @Test
    void removeIncome_thatWouldCauseNegativeBalance_expectException() throws BudgetTrackerException {
        summary.addIncome(1000.0);
        summary.addExpense(300.0);
        summary.addSavings(200.0);
        
        assertThrows(BudgetTrackerException.class, 
                () -> summary.removeIncome(600.0), 
                "Removing income that would result in negative balance should be rejected");
    }
}
