# Common Cents User Guide

## Introduction

Common Cents is a Command Line Interface (CLI) financial management application that helps you track your income, expenses, and savings. It provides a simple way to monitor your financial health and alerts you when your funds drop below a set threshold.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest version of `Common Cents` from [here](https://github.com/AY2425S2-CS2113-T11A-4/tp).
3. Copy the file to the folder you want to use as the home folder for Common Cents.
4. Open a command terminal and navigate to the folder.
5. Run the command `java -jar commoncents.jar` to start the application.
6. Type `help` to view available commands.

## Features 

Common Cents offers several features to help you manage your finances effectively:

### Input Notes
* IMPORTANT: Parameters should be padded with spaces! eg. `add expense 30 / lunch / f`
* Commands should be entered in lowercase.

### Income Management

#### Adding Income: `add income`

Adds a new income entry to your financial record.

Format: `add income <AMOUNT> / <SOURCE>`

* `<AMOUNT>` must be a positive number.
* `<SOURCE>` is a description of where the income came from.

Example of usage:

```
> add income 50 / salary
Added income: $50.0 from salary
```

#### Viewing Income: `view income`

Lists all your income entries.

Format: `view income`

Example of usage:

```
> view income
===== INCOME RECORDS =====
1.  $50.00 from salary
=========================
Total Income: $50.0
```

#### Deleting Income: `delete income`

Removes an income entry from your records.

Format: `delete income <INDEX>`

* `<INDEX>` refers to the index number shown in the income list.
* The index must be a positive integer (1, 2, 3, ...).

Example of usage:

```
> delete income 1
Deleted income entry at index 1
```

### Expense Management

#### Adding Expense: `add expense`

Adds a new expense entry to your financial record.

Format: `add expense <AMOUNT> / <DESCRIPTION> / <CATEGORY>`

* `<AMOUNT>` must be a positive number.
* `<DESCRIPTION>` is a description of what the expense was for.
* `<DESCRIPTION>` should not contain '/' symbols.
* `<CATEGORY>` is the category of the expense (F (Food), T (Transport), B (Bills), O (Others))
* Able to input either 'F', 'T', 'B' or 'O' into the `<CATEGORY>` parameter. This is NOT case-sensitive!

Example of usage:

```
> add expense 25 / food / f
Added expense: [FOOD] $25.00 for food
```

#### Viewing Expenses: `view expense`

Lists all your expense entries.

Format: `view expense`

Example of usage:

```
> view expense
===== EXPENSE ENTRIES =====
1. [FOOD] $25.00 for food
2. [BILLS] $50.00 for utilities
==========================
```

#### Deleting Expense: `delete expense`

Removes an expense entry from your records.

Format: `delete expense <INDEX>`

* `<INDEX>` refers to the index number shown in the expense list.
* The index must be a positive integer (1, 2, 3, ...).

Example of usage:

```
> delete expense 1
Deleted expense: [FOOD] $25.00 for food
```

### Savings Management

#### Adding to Savings: `add savings`

Add a saving.

Format: `add savings <AMOUNT> / <DESCRIPTION>`

* `<AMOUNT>` must be a positive number.
* `<DESCRIPTION>` is a description of the savings purpose.

Example of usage:

```
> add savings 20 / emergency fund
Added to savings: $20.00 for emergency fund
```

#### Viewing Savings: `view savings`

Lists all your savings entries. 
Gives a savings indicator based on the ratio of total savings to total income, 
which is either Good, Neutral or Bad.

Format: `view savings`

Example of usage:

```
> view savings
===== SAVINGS RECORDS ====
1.  $20.00 for emergency fund
2.  $10.00 for vacation
==========================
Savings Indicator: Bad - Try to save more.
```

#### Deleting Savings: `delete savings`

Removes a savings entry.

Format: `delete savings <INDEX>`

* `<INDEX>` refers to the index number shown in the savings list.
* The index must be a positive integer (1, 2, 3, ...).

Example of usage:

```
> delete savings 1
Deleted savings: $20.00 for emergency fund
```

#### Transferring Savings: `transfer savings`

Transfers a specified amount from one savings entry to another.

Format: `transfer savings <FROM_INDEX> <TO_INDEX> <AMOUNT>`

* `<FROM_INDEX>` and `<TO_INDEX>` refer to the index numbers shown in the savings list.
* `<AMOUNT>` is the amount to transfer

Example of usage:

```
> transfer savings 1 2 10
Transferred $10.00 from emergency fund to vacation
```

#### Setting Savings Goals: `savings goal set`

Edits the savings goal description. Note: If there are 2 saving entries with 
the same amount in the saving list, this command will only set the
goal of the first saving entry to the provided goal. However, if user
would like to change the saving goal of the second/third... saving entry
with the same amount, they may consider using command "savings goal update",
which is shown below.

Format: `savings goal set <AMOUNT> / <DESCRIPTION>`

* `<AMOUNT>` is the target amount for your savings goal.
* `<DESCRIPTION>` is a description of what you're saving for.

Example of usage:

```
> savings goal set 500 / new laptop
Savings goal set: $500.00 for new laptop
```

#### Updating Savings Goals: `savings goal update`

Updates an existing savings goal. One difference between this command 
and "savings goal set" is this command can also update the amount, even
though it is not that obvious to see in the command ("savings goal update")
that it can also update amount.

Format: `savings goal update <INDEX> <AMOUNT> / <DESCRIPTION>`

* `<INDEX>` refers to the index number shown in the savings goals list.
* `<AMOUNT>` is the new target amount.
* `<DESCRIPTION>` is the new description.

Example of usage:

```
> savings goal update 1 600 / gaming laptop
Updated savings goal: $600.00 for gaming laptop
```

#### Deleting Savings Goals: `savings goal delete`

Removes a savings goal.

Format: `savings goal delete <INDEX>`

* `<INDEX>` refers to the index number shown in the savings goals list.

Example of usage:

```
> savings goal delete 1
Deleted savings goal: $600.00 for gaming laptop(now the saving goal for this entry is empty)
```

### Summary Management

#### View Summary: `view summary`

Displays a summary of your current financial status, including total income, expenses, available balance, and savings.

Format: `view summary`

Example of usage: 

```
> view summary
===== BUDGET SUMMARY =====
Total Income:         $150.00
Total Expenses:       $75.00
Available Balance:    $75.00
Total Savings:        $30.00
===========================
```

#### Detailed Financial Tracking

The Summary component maintains your financial data using these calculations:
- Available Funds = Total Income - Total Expenses
- All calculations are performed automatically when you add or remove entries

#### Data Validation

The Summary component includes built-in validation to ensure financial data integrity:
- All amounts (income, expenses, savings) must be positive numbers
- You cannot remove more income than your current total
- You cannot add expenses or remove income that would result in negative available funds
- You cannot remove more income, expenses or savings than currently recorded

#### Financial Calculations

The Summary component automatically:
- Recalculates available funds after every transaction
- Updates all observers (like the alert system) when financial data changes
- Maintains running totals for all financial categories
- Ensures data consistency across the application

### Alert System

#### Low Funds Alert System

Common Cents includes a low funds alert feature that warns you when your available funds fall below a set threshold.

#### Set Alert Threshold: `alert set`

Sets the threshold for low funds alerts. When your available funds drop below this amount, Common Cents will display a warning. Threshold is automatically rounded to 2 decimal places for values more than 2 decimal places (E.g. 0.004 will be rounded down to 0.00).

_Note: There should only be a single whitespace between `alert` and `set`_.

Format: `alert set <AMOUNT>`

* `<AMOUNT>` must be a positive number.
* The default threshold is $5.00 if not explicitly set.

Example of usage:

```
> alert set 20
Funds alert threshold set to $20.00
```

#### Alert Trigger

When your available funds drop below the set threshold due to adding expenses, Common Cents will automatically display a warning:

```
> add expense 40 / groceries / F

====== ALERT ======
WARNING: Available funds ($10.00) are below warning threshold ($20.00)
===================

Added expense: [FOOD] $40.0 for groceries
```

#### Alert System Implementation

The alert system:
- Uses an observer pattern to monitor financial changes
- Has a default threshold of $5.00
- Can be customized to any positive amount
- Shows warnings immediately when funds drop below the threshold

#### Alert System Features

The FundsAlert component provides these additional features:
- **Initial Notification**: Displays information about the alert feature when you start the application
- **Real-time Monitoring**: Continuously checks your financial status after every transaction
- **Customizable Threshold**: Allows you to set a warning level that matches your financial comfort zone
- **Clear Visual Warnings**: Displays prominent alerts when your funds drop below the threshold

### Help Display: `help`

Shows a list of all available commands and their formats.

Format: `help`

Example of usage:

```
> help
===== Budget Tracker Help =====

--- Income Management ---
add income <AMOUNT> / <SOURCE>                               Adds an income record.
delete income <INDEX>                                        Deletes an income record 
                                                             by index.
view income                                                  Lists all income records.

--- Expense Management ---
add expense <AMOUNT> / <DESCRIPTION> / <CATEGORY>            Adds an expense record.
delete expense <INDEX>                                       Deletes an expense record
                                                             by index.
view expense                                                 Lists all expense records.

--- Savings Management ---
add savings <AMOUNT> / <SAVINGS GOAL>                        Adds a savings record 
                                                             with savings goal.
delete savings <INDEX>                                       Deletes a savings record 
                                                             by index.
transfer savings <FROM_INDEX> <TO_INDEX> <AMOUNT>            transfers a specified amount 
                                                             from one savings record to 
                                                             another.
view savings                                                 Lists all savings records.

--- Summary Management ---
view summary                                                 Lists all income, expense 
                                                             and saving records.

--- Savings Goals ---
savings goal set <AMOUNT> / <DESCRIPTION>                    Sets a new savings goal.
savings goal update <INDEX> <AMOUNT> / <DESCRIPTION>         Updates an existing 
                                                             savings goal.
savings goal delete <INDEX>                                  Deletes a savings goal 
                                                             by index.

--- Funds Alerts ---
alert set <AMOUNT>                                           Sets the warning threshold 
                                                             for low available funds.

--- General Commands ---
help                                                         Displays this help message.
===============================
```
_Note: Some of the explanations for the help display have been appended to the next line to prevent being cut off in pdf format._
### Help Command Details

The HelpDisplay component:
- Organizes commands into logical categories for easier reference
- Formats command information consistently with clear descriptions
- Aligns text for better readability
- Provides a comprehensive overview of all available functionality
- Includes proper syntax examples for each command

### Exit: `bye`

Exits the application.

Format: `bye`

Example of usage:

```
> bye
Exiting the application. Goodbye!
```

## Command Summary

* **Help**: `help`
* **View Summary**: `view summary`
* **Add Income**: `add income <AMOUNT> / <DESCRIPTION>`
* **View Income**: `view income`
* **Delete Income**: `delete income <INDEX>`
* **Add Expense**: `add expense <AMOUNT> / <DESCRIPTION> / <CATEGORY>`
* **View Expenses**: `view expense`
* **Delete Expense**: `delete expense <INDEX>`
* **Add Savings**: `add savings <AMOUNT> / <DESCRIPTION>`
* **View Savings**: `view savings`
* **Delete Savings**: `delete savings <INDEX>`
* **Transfer Savings**: `transfer savings <FROM_INDEX> <TO_INDEX> <AMOUNT>`
* **Set Savings Goal**: `savings goal set <AMOUNT> / <DESCRIPTION>`
* **Update Savings Goal**: `savings goal update <INDEX> / <AMOUNT> / <DESCRIPTION>`
* **Delete Savings Goal**: `savings goal delete <INDEX>`
* **Set Alert Threshold**: `alert set <AMOUNT>`
* **Exit Application**: `bye`

## Features coming soon

### Local Saving to Disk

**Planned Feature:** We plan to implement functionality to save all financial data (income, expenses, savings, alert settings) locally to a file on the user's disk. This will allow users to persist their data between application sessions.

**Reason for Delay:** Implementing local storage requires careful consideration of file formats, data serialization, error handling (e.g., corrupted files), and user experience for loading/saving data. We prioritized the development of core functionalities like tracking income/expenses, managing savings, viewing summaries, and setting alerts to deliver a functional baseline product first. The effort required for robust local saving was deferred to focus on these essential features.

## Error Messages

Common Cents provides helpful error messages to guide you when something goes wrong:

* **Invalid command:**
  * If you enter an unrecognized command, Common Cents will suggest using the help command.
  * Example: `hello` → `Oops! I don't recognize that command. Type 'help' to see available commands.`

* **Invalid command format:**
  * If you enter a command with incorrect formatting, Common Cents will explain the proper format.
  * Example: `add income 50` → `Invalid format for 'add income' command. Please use 'add income <AMOUNT> / <SOURCE>'`

* **Negative amount:**
  * If you try to add a negative amount, Common Cents will remind you that amounts must be positive.
  * Example: `add income -50 / salary` → `Income amount must be greater than zero.`

* **Insufficient funds:**
  * If you try to add expenses more than your available balance, Common Cents will warn you.
  * Example: `add expense 100 / vacation / B` when you only have $50 available → `Error adding expense: Cannot add this expense as it would exceed your available funds. Available balance: 0.0`

* **Invalid index:**
  * If you try to delete an entry with an invalid index, Common Cents will inform you.
  * Example: `delete income 10` when you only have 2 income entries → `Invalid index. Please provide a valid income index between 1 and 2.`


