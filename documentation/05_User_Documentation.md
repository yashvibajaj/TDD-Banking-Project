# User Documentation
## TDD Banking Application

### Document Information
- **Version**: 1.0
- **Date**: December 2024
- **Project**: TDD Banking Application
- **Target Audience**: Bank Staff, System Administrators, End Users

---

## 1. User Guide Overview

### 1.1 Purpose
This guide provides comprehensive instructions for using the TDD Banking Application. The system supports account management, transaction processing, and automated interest calculations.

### 1.2 System Requirements
- Java 8 or higher
- Command-line interface access
- Basic understanding of banking operations

### 1.3 Getting Started
The banking application processes commands through a text-based interface. Commands are case-insensitive and space-separated.

---

## 2. Customer User Guide

### 2.1 Account Types

#### 2.1.1 Checking Account
- **Purpose**: Daily banking transactions
- **Features**: 
  - Unlimited deposits and withdrawals
  - No withdrawal restrictions
  - Simple interest calculation
- **Minimum Balance**: $0 (no minimum required)
- **Interest**: Calculated monthly based on APR

#### 2.1.2 Savings Account
- **Purpose**: Long-term savings with interest
- **Features**:
  - Unlimited deposits
  - Withdrawal status tracking
  - Simple interest calculation
- **Minimum Balance**: $0 (no minimum required)
- **Interest**: Calculated monthly based on APR
- **Special Rules**: Withdrawal status changes when balance reaches $0

#### 2.1.3 Certificate of Deposit (CD)
- **Purpose**: High-yield savings with time commitment
- **Features**:
  - Initial deposit required ($1,000 - $10,000)
  - Full withdrawal capability
  - Compound interest calculation
- **Minimum Balance**: $1,000 initial deposit
- **Interest**: Compound interest calculated quarterly
- **Special Rules**: Higher interest rates, initial deposit requirement

### 2.2 Understanding Account Information

#### 2.2.1 Account Status Format
Each account is displayed as:
```
<ACCOUNT_TYPE> <ACCOUNT_ID> <BALANCE> <APR>
```

**Example**:
```
Checking 12345678 1500.00 0.60
Savings 87654321 2500.50 1.20
Cd 11223344 5000.00 2.50
```

#### 2.2.2 Account ID
- **Format**: 8-digit number
- **Example**: 12345678
- **Uniqueness**: Each account has a unique ID

#### 2.2.3 Balance
- **Format**: Dollar amount with 2 decimal places
- **Example**: 1500.00
- **Updates**: Real-time balance updates after transactions

#### 2.2.4 APR (Annual Percentage Rate)
- **Format**: Decimal with up to 2 decimal places
- **Range**: 0.00% to 10.00%
- **Example**: 1.25 (represents 1.25%)

---

## 3. Bank Staff Manual

### 3.1 Account Management

#### 3.1.1 Creating Accounts

**Command**: `CREATE <ACCOUNT_TYPE> <ACCOUNT_ID> <APR> [INITIAL_AMOUNT]`

**Steps**:
1. Verify customer information
2. Choose appropriate account type
3. Generate unique 8-digit account ID
4. Set APR within 0-10% range
5. For CD accounts, collect initial deposit ($1,000-$10,000)
6. Execute create command

**Examples**:
```bash
# Create checking account
CREATE checking 12345678 0.6

# Create savings account
CREATE savings 87654321 1.2

# Create CD account
CREATE cd 11223344 2.5 5000
```

**Validation Rules**:
- Account ID must be exactly 8 digits
- Account ID must be unique
- APR must be between 0.0 and 10.0
- CD accounts require initial deposit between $1,000 and $10,000

#### 3.1.2 Account Verification
- Check account exists before processing transactions
- Verify account type matches transaction requirements
- Confirm account is active (not removed)

### 3.2 Transaction Processing

#### 3.2.1 Deposits

**Command**: `DEPOSIT <ACCOUNT_ID> <AMOUNT>`

**Process**:
1. Verify account exists
2. Confirm deposit amount is positive
3. Execute deposit command
4. Update account balance
5. Log transaction

**Examples**:
```bash
# Deposit $500
DEPOSIT 12345678 500

# Deposit $1000.50
DEPOSIT 87654321 1000.50
```

**Best Practices**:
- Always verify account ID before processing
- Confirm amount with customer
- Process deposits immediately
- Maintain transaction records

#### 3.2.2 Withdrawals

**Command**: `WITHDRAW <ACCOUNT_ID> <AMOUNT>`

**Process**:
1. Verify account exists
2. Check sufficient funds available
3. Confirm withdrawal amount
4. Execute withdrawal command
5. Update account balance
6. Log transaction

**Examples**:
```bash
# Withdraw $200
WITHDRAW 12345678 200

# Withdraw $50.25
WITHDRAW 87654321 50.25
```

**Business Rules**:
- Account balance cannot go below $0
- Withdrawal amount must be positive
- Savings accounts: Withdrawal status changes when balance reaches $0

#### 3.2.3 Transfers

**Command**: `TRANSFER <FROM_ACCOUNT_ID> <TO_ACCOUNT_ID> <AMOUNT>`

**Process**:
1. Verify both accounts exist
2. Check source account has sufficient funds
3. Confirm transfer amount
4. Execute transfer command
5. Update both account balances
6. Log transaction

**Examples**:
```bash
# Transfer $300 between accounts
TRANSFER 12345678 87654321 300

# Transfer $1000
TRANSFER 11223344 55667788 1000
```

**Business Rules**:
- Both accounts must exist
- Source account must have sufficient funds
- If insufficient funds, transfer amount is limited to available balance
- Transfer is processed as withdraw + deposit

### 3.3 Time Management

#### 3.3.1 Time Progression

**Command**: `PASS <MONTHS>`

**Process**:
1. Verify months parameter is positive
2. Execute time progression
3. Calculate interest for all accounts
4. Apply monthly fees where applicable
5. Remove zero-balance accounts
6. Update all account balances

**Examples**:
```bash
# Advance 1 month
PASS 1

# Advance 12 months
PASS 12
```

**Interest Calculations**:
- **Checking/Savings**: Simple interest (APR/12 per month)
- **CD**: Compound interest (quarterly compounding)

**Fees**:
- Accounts with balance below $100 incur $25 monthly fee
- Zero-balance accounts are removed after time progression

---

## 4. System Administrator Manual

### 4.1 System Overview

#### 4.1.1 Architecture
- **Command Processing**: Sequential command processing
- **Data Storage**: In-memory storage using HashMap
- **Validation**: Comprehensive input validation
- **Error Handling**: Graceful error handling with logging

#### 4.1.2 Components
- **MasterControl**: Central command orchestrator
- **Bank**: Core business logic and account management
- **Validators**: Input validation for all commands
- **Command Processors**: Business logic execution
- **Command Storage**: Transaction history and output generation

### 4.2 System Configuration

#### 4.2.1 Build Configuration
The system uses Gradle for build management:

```gradle
plugins {
    id 'java'
    id 'idea'
    id 'jacoco'
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.4.2'
}
```

#### 4.2.2 Testing Configuration
- **Test Framework**: JUnit 5
- **Coverage Tool**: JaCoCo
- **Test Execution**: Gradle test task

### 4.3 System Monitoring

#### 4.3.1 Command Processing
- Monitor command processing times
- Track validation success rates
- Monitor error rates and types

#### 4.3.2 Account Management
- Track account creation rates
- Monitor account removal (zero balance)
- Track transaction volumes

#### 4.3.3 Performance Metrics
- Command processing latency
- Memory usage patterns
- Error rates by command type

### 4.4 Maintenance Procedures

#### 4.4.1 Regular Maintenance
- Review error logs
- Monitor system performance
- Update documentation as needed

#### 4.4.2 Troubleshooting
- **Invalid Commands**: Check command syntax and parameters
- **Account Issues**: Verify account existence and status
- **Transaction Errors**: Check business rule compliance

---

## 5. Command Reference

### 5.1 Complete Command List

| Command | Syntax | Description |
|---------|--------|-------------|
| CREATE | `CREATE <TYPE> <ID> <APR> [AMOUNT]` | Create new account |
| DEPOSIT | `DEPOSIT <ID> <AMOUNT>` | Deposit money to account |
| WITHDRAW | `WITHDRAW <ID> <AMOUNT>` | Withdraw money from account |
| TRANSFER | `TRANSFER <FROM_ID> <TO_ID> <AMOUNT>` | Transfer money between accounts |
| PASS | `PASS <MONTHS>` | Advance time and calculate interest |

### 5.2 Command Examples

#### 5.2.1 Account Creation Examples
```bash
# Create checking account
CREATE checking 12345678 0.6

# Create savings account
CREATE savings 87654321 1.2

# Create CD account
CREATE cd 11223344 2.5 5000
```

#### 5.2.2 Transaction Examples
```bash
# Deposit $500
DEPOSIT 12345678 500

# Withdraw $200
WITHDRAW 12345678 200

# Transfer $300
TRANSFER 12345678 87654321 300
```

#### 5.2.3 Time Progression Examples
```bash
# Advance 1 month
PASS 1

# Advance 12 months
PASS 12
```

---

## 6. Error Handling

### 6.1 Common Errors

#### 6.1.1 Syntax Errors
- **Missing Parameters**: Command missing required parameters
- **Invalid Format**: Incorrect command format
- **Extra Parameters**: Too many parameters provided

#### 6.1.2 Validation Errors
- **Invalid Account ID**: Account ID not 8 digits or not numeric
- **Duplicate Account ID**: Account ID already exists
- **Invalid APR**: APR outside 0-10% range
- **Invalid Amount**: Negative or invalid amount

#### 6.1.3 Business Rule Violations
- **Insufficient Funds**: Withdrawal exceeds account balance
- **Account Not Found**: Account doesn't exist
- **Invalid Account Type**: Unsupported account type

### 6.2 Error Resolution

#### 6.2.1 For Bank Staff
1. **Check Command Syntax**: Verify all parameters are correct
2. **Verify Account Status**: Ensure account exists and is active
3. **Check Business Rules**: Ensure transaction complies with rules
4. **Retry Command**: Correct errors and retry

#### 6.2.2 For System Administrators
1. **Review Error Logs**: Check system logs for patterns
2. **Validate Input**: Ensure input data is correct
3. **Check System Status**: Verify system is functioning properly
4. **Update Documentation**: Document new error patterns

---

## 7. Best Practices

### 7.1 For Bank Staff

#### 7.1.1 Account Management
- Always verify customer identity before creating accounts
- Use descriptive account IDs when possible
- Set appropriate APR based on current rates
- Document account creation process

#### 7.1.2 Transaction Processing
- Verify account details before processing
- Confirm amounts with customers
- Process transactions promptly
- Maintain accurate records

#### 7.1.3 Error Prevention
- Double-check command syntax
- Verify account IDs before processing
- Confirm amounts are correct
- Follow established procedures

### 7.2 For System Administrators

#### 7.2.1 System Maintenance
- Regular system monitoring
- Performance optimization
- Error log analysis
- Documentation updates

#### 7.2.2 Security
- Input validation
- Access control
- Audit logging
- Data integrity

---

## 8. Troubleshooting Guide

### 8.1 Common Issues

#### 8.1.1 Command Not Working
- **Check Syntax**: Verify command format
- **Check Parameters**: Ensure all required parameters provided
- **Check Account**: Verify account exists

#### 8.1.2 Account Issues
- **Account Not Found**: Verify account ID is correct
- **Insufficient Funds**: Check account balance
- **Invalid Transaction**: Verify business rules

#### 8.1.3 System Issues
- **Slow Performance**: Check system resources
- **Memory Issues**: Monitor memory usage
- **Error Rates**: Review error logs

### 8.2 Resolution Steps

#### 8.2.1 Immediate Actions
1. **Stop Processing**: Pause command processing if needed
2. **Check Logs**: Review error logs for details
3. **Verify Data**: Check account and transaction data
4. **Correct Issues**: Fix identified problems
5. **Resume Processing**: Restart command processing

#### 8.2.2 Long-term Solutions
1. **Update Procedures**: Improve operational procedures
2. **Enhance Validation**: Improve input validation
3. **System Updates**: Apply system improvements
4. **Training**: Provide additional staff training

---

## 9. FAQ (Frequently Asked Questions)

### 9.1 General Questions

**Q: What types of accounts can I create?**
A: The system supports three account types: Checking, Savings, and Certificate of Deposit (CD).

**Q: How do I create an account?**
A: Use the CREATE command with the format: `CREATE <TYPE> <ID> <APR> [AMOUNT]`

**Q: Can I transfer money between accounts?**
A: Yes, use the TRANSFER command: `TRANSFER <FROM_ID> <TO_ID> <AMOUNT>`

### 9.2 Account Questions

**Q: What's the difference between account types?**
A: Checking accounts have no restrictions, Savings accounts track withdrawal status, and CD accounts require initial deposits and use compound interest.

**Q: How is interest calculated?**
A: Checking and Savings use simple interest monthly, while CD accounts use compound interest quarterly.

**Q: What happens if I withdraw more than available?**
A: The system prevents overdrafts - account balance cannot go below $0.

### 9.3 Technical Questions

**Q: How do I check if a command is valid?**
A: The system validates all commands before processing. Invalid commands are logged and displayed in output.

**Q: Can I process multiple commands at once?**
A: Yes, the system processes commands sequentially in the order provided.

**Q: How do I advance time for interest calculations?**
A: Use the PASS command: `PASS <MONTHS>` to advance time and calculate interest.

---

*This user documentation is based on the actual implementation of the TDD Banking Application and reflects the current system capabilities as of December 2024.*
