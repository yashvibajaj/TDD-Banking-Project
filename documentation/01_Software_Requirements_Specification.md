# Software Requirements Specification (SRS)
## TDD Banking Application

### Document Information
- **Version**: 1.0
- **Date**: December 2024
- **Project**: TDD Banking Application
- **Technology Stack**: Java, JUnit 5, Gradle, JaCoCo

---

## 1. Introduction

### 1.1 Purpose
This document specifies the functional and non-functional requirements for a Test-Driven Development (TDD) Banking Application. The system provides core banking operations including account management, transactions, and time-based interest calculations.

### 1.2 Scope
The banking application supports three types of accounts (Checking, Savings, CD), basic transaction operations (deposit, withdraw, transfer), and automated time progression with interest calculations.

### 1.3 Definitions and Acronyms
- **APR**: Annual Percentage Rate
- **CD**: Certificate of Deposit
- **TDD**: Test-Driven Development
- **SRS**: Software Requirements Specification

---

## 2. Overall Description

### 2.1 Product Perspective
The banking application is a standalone Java application that processes banking commands through a command-line interface. It follows a layered architecture with clear separation of concerns.

### 2.2 Product Functions
- Account creation and management
- Transaction processing (deposits, withdrawals, transfers)
- Interest calculation and time progression
- Command validation and error handling
- Transaction history tracking

### 2.3 User Classes and Characteristics
- **System Administrators**: Manage the banking system
- **Bank Staff**: Process customer transactions
- **End Users**: Interact with accounts through commands

---

## 3. Specific Requirements

### 3.1 Functional Requirements

#### 3.1.1 Account Management

**FR-001: Account Creation**
- **Description**: System shall support creation of three account types
- **Priority**: High
- **Acceptance Criteria**:
  - Create Checking accounts with 8-digit numeric ID
  - Create Savings accounts with 8-digit numeric ID  
  - Create CD accounts with 8-digit numeric ID and initial deposit
  - Validate unique account IDs
  - Validate APR between 0-10%
  - CD accounts require initial deposit between $1,000-$10,000

**FR-002: Account Types**
- **Description**: System shall support three distinct account types
- **Priority**: High
- **Acceptance Criteria**:
  - Checking accounts: No withdrawal restrictions
  - Savings accounts: Withdrawal status tracking, no overdraft
  - CD accounts: Full withdrawal capability, compound interest

#### 3.1.2 Transaction Processing

**FR-003: Deposit Operations**
- **Description**: System shall process deposit transactions
- **Priority**: High
- **Acceptance Criteria**:
  - Accept deposits to any valid account
  - Update account balance immediately
  - Validate account exists before processing

**FR-004: Withdrawal Operations**
- **Description**: System shall process withdrawal transactions
- **Priority**: High
- **Acceptance Criteria**:
  - Process withdrawals from valid accounts
  - Prevent overdrafts (balance cannot go below $0)
  - Update Savings account withdrawal status when balance reaches $0

**FR-005: Transfer Operations**
- **Description**: System shall process transfer transactions
- **Priority**: High
- **Acceptance Criteria**:
  - Transfer funds between valid accounts
  - Prevent transfers exceeding source account balance
  - Process partial transfers if insufficient funds

#### 3.1.3 Time and Interest Management

**FR-006: Time Progression**
- **Description**: System shall support time progression for interest calculations
- **Priority**: High
- **Acceptance Criteria**:
  - Process time in monthly increments
  - Calculate interest based on APR
  - Apply different interest calculation methods per account type
  - Remove accounts with zero balance after time progression

**FR-007: Interest Calculations**
- **Description**: System shall calculate interest based on account type
- **Priority**: High
- **Acceptance Criteria**:
  - Simple interest for Checking and Savings accounts
  - Compound interest (quarterly) for CD accounts
  - Apply monthly interest rate (APR/12)

#### 3.1.4 Command Processing

**FR-008: Command Validation**
- **Description**: System shall validate all incoming commands
- **Priority**: High
- **Acceptance Criteria**:
  - Validate command syntax and parameters
  - Check account existence for transaction commands
  - Return appropriate error messages for invalid commands

**FR-009: Command Storage**
- **Description**: System shall maintain command history
- **Priority**: Medium
- **Acceptance Criteria**:
  - Store valid commands by account
  - Track invalid commands separately
  - Generate formatted output with account status

### 3.2 Non-Functional Requirements

#### 3.2.1 Performance Requirements

**NFR-001: Response Time**
- **Description**: System shall process commands within acceptable time limits
- **Priority**: Medium
- **Acceptance Criteria**:
  - Process single command within 100ms
  - Handle batch processing efficiently

**NFR-002: Scalability**
- **Description**: System shall support multiple accounts
- **Priority**: Medium
- **Acceptance Criteria**:
  - Support up to 10,000 accounts
  - Maintain performance with large command batches

#### 3.2.2 Reliability Requirements

**NFR-003: Data Integrity**
- **Description**: System shall maintain data consistency
- **Priority**: High
- **Acceptance Criteria**:
  - Prevent data corruption during transactions
  - Maintain accurate account balances
  - Preserve transaction history

**NFR-004: Error Handling**
- **Description**: System shall handle errors gracefully
- **Priority**: High
- **Acceptance Criteria**:
  - Validate all inputs before processing
  - Provide meaningful error messages
  - Continue processing after encountering errors

#### 3.2.3 Usability Requirements

**NFR-005: Command Interface**
- **Description**: System shall provide intuitive command interface
- **Priority**: Medium
- **Acceptance Criteria**:
  - Support case-insensitive commands
  - Provide clear command syntax
  - Generate readable output format

---

## 4. Business Rules

### 4.1 Account Rules
- Account IDs must be exactly 8 digits
- Account IDs must be unique across the system
- APR must be between 0% and 10%
- CD accounts require initial deposit between $1,000 and $10,000

### 4.2 Transaction Rules
- Account balances cannot go below $0
- Withdrawals from zero-balance accounts are not allowed
- Transfers cannot exceed source account balance
- All monetary amounts are rounded to 2 decimal places

### 4.3 Interest Rules
- Interest is calculated monthly
- CD accounts use compound interest (quarterly compounding)
- Other accounts use simple interest
- Accounts with zero balance are removed after time progression
- Accounts with balance below $100 incur $25 monthly fee

---

## 5. Use Cases

### 5.1 Primary Use Cases

**UC-001: Create Account**
- **Actor**: Bank Staff
- **Precondition**: Valid account parameters
- **Main Flow**:
  1. Staff enters create command with account type, ID, and APR
  2. System validates command parameters
  3. System creates new account
  4. System confirms account creation
- **Postcondition**: New account exists in system

**UC-002: Process Deposit**
- **Actor**: Bank Staff
- **Precondition**: Valid account exists
- **Main Flow**:
  1. Staff enters deposit command with account ID and amount
  2. System validates account exists
  3. System adds amount to account balance
  4. System confirms deposit
- **Postcondition**: Account balance increased

**UC-003: Process Withdrawal**
- **Actor**: Bank Staff
- **Precondition**: Valid account exists with sufficient funds
- **Main Flow**:
  1. Staff enters withdrawal command with account ID and amount
  2. System validates account exists and has sufficient funds
  3. System subtracts amount from account balance
  4. System confirms withdrawal
- **Postcondition**: Account balance decreased

**UC-004: Process Transfer**
- **Actor**: Bank Staff
- **Precondition**: Both accounts exist
- **Main Flow**:
  1. Staff enters transfer command with source, destination, and amount
  2. System validates both accounts exist
  3. System checks source account has sufficient funds
  4. System transfers amount between accounts
  5. System confirms transfer
- **Postcondition**: Funds moved between accounts

**UC-005: Process Time Progression**
- **Actor**: System
- **Precondition**: System has accounts
- **Main Flow**:
  1. System receives pass time command with months
  2. System calculates interest for all accounts
  3. System applies monthly fees where applicable
  4. System removes zero-balance accounts
  5. System confirms time progression
- **Postcondition**: Interest applied, accounts updated

---

## 6. User Stories

### 6.1 Account Management Stories
- As a bank staff member, I want to create different types of accounts so that customers can choose appropriate banking products
- As a bank staff member, I want to validate account creation parameters so that only valid accounts are created
- As a bank staff member, I want to ensure account IDs are unique so that there are no conflicts

### 6.2 Transaction Stories
- As a bank staff member, I want to process deposits so that customers can add money to their accounts
- As a bank staff member, I want to process withdrawals so that customers can access their money
- As a bank staff member, I want to process transfers so that customers can move money between accounts
- As a bank staff member, I want to prevent overdrafts so that the bank doesn't lose money

### 6.3 System Management Stories
- As a system administrator, I want the system to calculate interest automatically so that accounts grow over time
- As a system administrator, I want the system to handle time progression so that interest is applied correctly
- As a system administrator, I want the system to track all commands so that there's a complete audit trail

---

## 7. Assumptions and Dependencies

### 7.1 Assumptions
- All monetary values are in USD
- Interest calculations use standard banking formulas
- System runs in a single-threaded environment
- Commands are processed sequentially

### 7.2 Dependencies
- Java 8 or higher
- JUnit 5 for testing
- Gradle for build management
- JaCoCo for code coverage

---

## 8. Constraints

### 8.1 Technical Constraints
- Must use Java programming language
- Must follow TDD methodology
- Must achieve high test coverage
- Must use object-oriented design principles

### 8.2 Business Constraints
- Account balances cannot be negative
- Interest rates are capped at 10%
- CD accounts have minimum deposit requirements
- All transactions must be logged

---

## 9. Traceability Matrix

| Requirement ID | Test Case | Implementation Class |
|----------------|-----------|---------------------|
| FR-001 | CreateValidatorTest | CreateValidator, CreateCommandProcessor |
| FR-002 | AccountTest, CDAccountsTest | Account, Checking, Savings, CD |
| FR-003 | BankTest | DepositCommandProcessor |
| FR-004 | BankTest | WithdrawCommandProcessor |
| FR-005 | BankTest | TransferCommandProcessor |
| FR-006 | PassTimeValidatorTest | PassTimeProcessor |
| FR-007 | AccountTest | Account.passTimeCalc() |
| FR-008 | ValidatorTest | Validator, *Validator classes |
| FR-009 | CommandStorageTest | CommandStorage |

---

*This document is based on the actual implementation of the TDD Banking Application and reflects the current system capabilities as of December 2024.*
