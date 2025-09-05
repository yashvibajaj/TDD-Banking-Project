# API Documentation
## TDD Banking Application

### Document Information
- **Version**: 1.0
- **Date**: December 2024
- **Project**: TDD Banking Application
- **API Type**: Command-Line Interface (CLI)

---

## 1. Overview

The TDD Banking Application provides a command-line interface for processing banking operations. The system accepts text-based commands and returns formatted output with account status and transaction history.

### 1.1 API Characteristics
- **Interface Type**: Command-Line Interface
- **Input Format**: Space-separated text commands
- **Output Format**: Formatted text with account status
- **Processing**: Sequential command processing
- **Validation**: Comprehensive input validation

---

## 2. Command Reference

### 2.1 Command Syntax
All commands follow the pattern: `<COMMAND> [PARAMETERS]`

Commands are case-insensitive and space-separated.

---

## 3. Account Management Commands

### 3.1 Create Account

**Command**: `CREATE <ACCOUNT_TYPE> <ACCOUNT_ID> <APR> [INITIAL_AMOUNT]`

**Description**: Creates a new bank account of the specified type.

#### Parameters
| Parameter | Type | Required | Description | Constraints |
|-----------|------|----------|-------------|-------------|
| ACCOUNT_TYPE | String | Yes | Type of account to create | checking, savings, cd |
| ACCOUNT_ID | String | Yes | Unique 8-digit account identifier | Must be exactly 8 digits, numeric |
| APR | Decimal | Yes | Annual Percentage Rate | 0.0 to 10.0 |
| INITIAL_AMOUNT | Decimal | No | Initial deposit amount (CD only) | $1,000 to $10,000 (CD accounts only) |

#### Examples
```bash
# Create a checking account
CREATE checking 12345678 0.6

# Create a savings account
CREATE savings 87654321 1.2

# Create a CD account
CREATE cd 11223344 2.5 5000
```

#### Response
- **Success**: Account created successfully (no immediate output)
- **Error**: Command added to invalid commands list

#### Validation Rules
- Account ID must be exactly 8 digits
- Account ID must be unique
- APR must be between 0.0 and 10.0
- CD accounts require initial amount between $1,000 and $10,000
- Account type must be valid (checking, savings, cd)

---

## 4. Transaction Commands

### 4.1 Deposit

**Command**: `DEPOSIT <ACCOUNT_ID> <AMOUNT>`

**Description**: Deposits money into the specified account.

#### Parameters
| Parameter | Type | Required | Description | Constraints |
|-----------|------|----------|-------------|-------------|
| ACCOUNT_ID | String | Yes | 8-digit account identifier | Must exist in system |
| AMOUNT | Decimal | Yes | Amount to deposit | Must be positive |

#### Examples
```bash
# Deposit $500 into account 12345678
DEPOSIT 12345678 500

# Deposit $1000.50 into account 87654321
DEPOSIT 87654321 1000.50
```

#### Response
- **Success**: Amount added to account balance
- **Error**: Command added to invalid commands list

#### Validation Rules
- Account must exist
- Amount must be positive
- Account must be active

---

### 4.2 Withdraw

**Command**: `WITHDRAW <ACCOUNT_ID> <AMOUNT>`

**Description**: Withdraws money from the specified account.

#### Parameters
| Parameter | Type | Required | Description | Constraints |
|-----------|------|----------|-------------|-------------|
| ACCOUNT_ID | String | Yes | 8-digit account identifier | Must exist in system |
| AMOUNT | Decimal | Yes | Amount to withdraw | Must be positive |

#### Examples
```bash
# Withdraw $200 from account 12345678
WITHDRAW 12345678 200

# Withdraw $50.25 from account 87654321
WITHDRAW 87654321 50.25
```

#### Response
- **Success**: Amount subtracted from account balance
- **Error**: Command added to invalid commands list

#### Validation Rules
- Account must exist
- Amount must be positive
- Account must have sufficient funds
- Account balance cannot go below $0

#### Business Rules
- **Checking Accounts**: Can withdraw up to available balance
- **Savings Accounts**: Withdrawal status changes when balance reaches $0
- **CD Accounts**: Can withdraw full amount

---

### 4.3 Transfer

**Command**: `TRANSFER <FROM_ACCOUNT_ID> <TO_ACCOUNT_ID> <AMOUNT>`

**Description**: Transfers money between two accounts.

#### Parameters
| Parameter | Type | Required | Description | Constraints |
|-----------|------|----------|-------------|-------------|
| FROM_ACCOUNT_ID | String | Yes | Source account identifier | Must exist and have sufficient funds |
| TO_ACCOUNT_ID | String | Yes | Destination account identifier | Must exist |
| AMOUNT | Decimal | Yes | Amount to transfer | Must be positive |

#### Examples
```bash
# Transfer $300 from account 12345678 to 87654321
TRANSFER 12345678 87654321 300

# Transfer $1000 from account 11223344 to 55667788
TRANSFER 11223344 55667788 1000
```

#### Response
- **Success**: Amount transferred between accounts
- **Error**: Command added to invalid commands list

#### Validation Rules
- Both accounts must exist
- Source account must have sufficient funds
- Amount must be positive
- Accounts must be different

#### Business Rules
- If source account has insufficient funds, transfer amount is limited to available balance
- Transfer is processed as two separate operations: withdraw from source, deposit to destination

---

## 5. System Commands

### 5.1 Pass Time

**Command**: `PASS <MONTHS>`

**Description**: Advances time by the specified number of months, applying interest and fees.

#### Parameters
| Parameter | Type | Required | Description | Constraints |
|-----------|------|----------|-------------|-------------|
| MONTHS | Integer | Yes | Number of months to advance | Must be positive |

#### Examples
```bash
# Advance time by 1 month
PASS 1

# Advance time by 12 months
PASS 12
```

#### Response
- **Success**: Time advanced, interest applied, fees charged
- **Error**: Command added to invalid commands list

#### Validation Rules
- Months must be a positive integer
- System must have accounts to process

#### Business Rules
- **Interest Calculation**:
  - Checking/Savings: Simple interest (APR/12 per month)
  - CD: Compound interest (quarterly compounding)
- **Fees**: Accounts with balance below $100 incur $25 monthly fee
- **Account Removal**: Accounts with $0 balance are removed after time progression

---

## 6. Output Format

### 6.1 Account Status Format
Each account is displayed in the following format:
```
<ACCOUNT_TYPE> <ACCOUNT_ID> <BALANCE> <APR>
```

#### Example
```
Checking 12345678 1500.00 0.60
Savings 87654321 2500.50 1.20
Cd 11223344 5000.00 2.50
```

### 6.2 Command History Format
Valid commands are displayed in their original format:
```
Deposit 12345678 500
Withdraw 12345678 200
Transfer 12345678 87654321 300
```

### 6.3 Invalid Commands Format
Invalid commands are displayed as-is:
```
creat checking 12345678 1.0
depositt 12345678 100
```

---

## 7. Error Handling

### 7.1 Validation Errors
The system validates all commands before processing. Invalid commands are:
- Logged in the invalid commands list
- Not processed
- Displayed in the final output

### 7.2 Common Error Scenarios

#### Invalid Command Syntax
```bash
# Missing parameters
CREATE checking 12345678
# Response: Command added to invalid list

# Invalid account type
CREATE credit 12345678 1.0
# Response: Command added to invalid list
```

#### Business Rule Violations
```bash
# Duplicate account ID
CREATE checking 12345678 1.0
CREATE checking 12345678 2.0
# Response: Second command added to invalid list

# Invalid APR
CREATE checking 12345678 15.0
# Response: Command added to invalid list
```

#### Account Not Found
```bash
# Deposit to non-existent account
DEPOSIT 99999999 100
# Response: Command added to invalid list
```

---

## 8. Command Processing Flow

### 8.1 Processing Pipeline
1. **Input**: Command received
2. **Validation**: Command syntax and business rules checked
3. **Processing**: If valid, command is executed
4. **Storage**: Command is stored for output generation
5. **Output**: Results are formatted and returned

### 8.2 Batch Processing
Multiple commands can be processed in sequence:
```bash
CREATE checking 12345678 0.6
DEPOSIT 12345678 1000
WITHDRAW 12345678 200
PASS 1
```

---

## 9. Data Types

### 9.1 Account Types
- **checking**: Standard checking account
- **savings**: Savings account with withdrawal status tracking
- **cd**: Certificate of Deposit with compound interest

### 9.2 Numeric Formats
- **Account ID**: 8-digit string (e.g., "12345678")
- **Amount**: Decimal with up to 2 decimal places (e.g., 100.50)
- **APR**: Decimal with up to 2 decimal places (e.g., 1.25)
- **Months**: Integer (e.g., 12)

---

## 10. Integration Examples

### 10.1 Complete Workflow Example
```bash
# Input commands
CREATE savings 12345678 0.6
DEPOSIT 12345678 700
DEPOSIT 12345678 5000
CREATE checking 98765432 0.01
DEPOSIT 98765432 300
TRANSFER 98765432 12345678 300
PASS 1
CREATE cd 23456789 1.2 2000

# Expected output
Savings 12345678 1000.50 0.60
Deposit 12345678 700
Transfer 98765432 12345678 300
Cd 23456789 2000.00 1.20
Deposit 12345678 5000
```

### 10.2 Error Handling Example
```bash
# Input commands
CREATE checking 12345678 1.0
creat checking 12345678 2.0
DEPOSIT 12345678 500
depositt 12345678 100

# Expected output
Checking 12345678 500.00 1.00
Deposit 12345678 500
creat checking 12345678 2.0
depositt 12345678 100
```

---

## 11. Performance Considerations

### 11.1 Command Processing
- Commands are processed sequentially
- Each command is validated before processing
- Invalid commands don't stop processing of subsequent commands

### 11.2 Memory Usage
- Account data is stored in memory
- Command history is maintained for output generation
- No persistent storage between sessions

---

## 12. Security Considerations

### 12.1 Input Validation
- All inputs are validated before processing
- Malformed commands are rejected
- Business rules are enforced

### 12.2 Data Integrity
- Account balances cannot go negative
- All transactions are atomic
- Command history is maintained for audit purposes

---

*This API documentation is based on the actual implementation of the TDD Banking Application and reflects the current command interface as of December 2024.*
