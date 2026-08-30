# Expense Tracking System

> A modular Java-based expense management application demonstrating Object-Oriented Programming, layered architecture, authentication, authorization, validation, exception handling, file persistence, serialization, and operation-history tracking.

---

## 📌 Project Overview

The **Expense Tracking System** is a console-based Java application designed to provide structured management of users and expenses while demonstrating practical software-development principles used in real-world Java applications.

The system separates responsibilities across entities, services, repositories, utilities, exceptions, menus, and test components.

The application supports two primary roles:

* **USER** — performs permitted expense-management operations.
* **ADMIN** — performs controlled user-account administration.

The project was developed with maintainability in mind so that another developer can understand the codebase, identify the responsible layer, execute the application, and extend the system without unnecessarily affecting unrelated modules.

---

## 🎯 Objective

The primary objectives of the project are to:

* Manage user accounts securely.
* Authenticate users using email and password.
* Control access using user roles and account status.
* Manage expense records through CRUD operations.
* Validate user and expense information.
* Prevent duplicate user IDs and email addresses.
* Persist application data using Java serialization.
* Maintain operation history for important activities.
* Handle application-specific failures using custom exceptions.
* Demonstrate clean separation of presentation, business and persistence responsibilities.

---

## ❗ Problem Statement

Manual expense tracking can lead to:

* inconsistent records
* difficulty locating transactions
* accidental data loss
* weak input validation
* poor account-management controls
* limited visibility into user operations

The Expense Tracking System addresses these challenges by introducing structured data management, authentication, authorization, validation, persistence and operation-history tracking.

---

# 🏗️ Architecture

The project follows a layered architecture:

```text
                    ┌──────────────────────┐
                    │   User / Admin       │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │ Menu / Presentation  │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │    Service Layer     │
                    │ Business Rules       │
                    │ Validation           │
                    │ Authorization        │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │  Repository Layer    │
                    │ Collection Handling  │
                    │ Persistence          │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │ Utility / File Layer │
                    │ Serialization        │
                    │ Password Hashing     │
                    └──────────┬───────────┘
                               │
                               ▼
                ┌──────────────────────────────────┐
                │ Serialized .dat Persistence      │
                │ users.dat / expense.dat / etc.   │
                └──────────────────────────────────┘
```

### Architectural Principle

Each layer has a defined responsibility:

| Layer      | Responsibility                      |
| ---------- | ----------------------------------- |
| Entity     | Represents domain objects           |
| Service    | Business logic and validation       |
| Repository | Data access and persistence         |
| Utility    | Reusable technical operations       |
| Exception  | Application-specific error handling |
| Menu       | Console interaction                 |
| Test       | Functional verification             |

---

# 📦 Package Structure

```text
com.expensetracker
│
├── entity
│   ├── User
│   ├── Expense
│   └── OperationHistory
│
├── service
│   ├── UserService
│   ├── ExpenseService
│   └── OperationHistoryService
│
├── service.impl
│   ├── UserServiceImpl
│   ├── ExpenseServiceImpl
│   └── OperationHistoryServiceImpl
│
├── repository
│   ├── UserRepository
│   ├── ExpenseRepository
│   └── OperationHistoryRepository
│
├── utility
│   ├── FileUtility
│   └── PasswordUtility
│
├── exception
│   └── Custom Exceptions
│
├── menu
│   └── Console Menu Components
│
└── test
    └── Test / Execution Components
```

---

# 🚀 Key Features

## 👤 User Management

* User registration
* Unique User ID validation
* Unique email validation
* User search by ID
* User search by email
* User update
* User activation
* User deactivation
* User deletion
* Administrator-account protection

---

## 🔐 Authentication

The application authenticates users using:

```text
Email
   +
Password
   ↓
Password Hash
   ↓
Stored Password Hash
   ↓
Authentication Result
```

Inactive users are prevented from logging into the application.

Example:

```text
========== USER LOGIN ==========

Enter Email:
arjun.kumar@gmail.com

Enter Password:
Arjun@123

Login successful. Welcome, Arjun Kumar!
Role: USER
```

---

# 🛡️ Authorization

Administrative operations are protected through role and status verification.

Before an administrator operation is executed, the system verifies:

```text
Admin ID
   ↓
Account Exists?
   ↓
Role = ADMIN?
   ↓
Status = ACTIVE?
   ↓
Operation Allowed
```

This prevents ordinary users from performing administrator-only operations.

---

# 💰 Expense Management

The expense module supports:

* Add Expense
* View Expenses
* Search Expense
* Update Expense
* Delete Expense

The `Expense` entity contains:

```text
expenseId
expenseName
category
amount
expenseDate
paymentMode
description
```

---

# 📝 Operation History

Important activities are recorded using an operation-history model.

Example:

```text
========== OPERATION HISTORY ==========

History ID       : HIS029
User ID          : USR005
Role             : USER
Operation        : REGISTER
Record Type      : USER
Record ID        : USR005
Description      : User account registered successfully
Date & Time      : 29-08-2026 18:38:22
```

History records provide traceability for operations such as:

* REGISTER
* LOGIN
* UPDATE
* ACTIVATE
* DEACTIVATE
* DELETE

---

# 💾 File Persistence & Serialization

The current implementation uses Java object serialization for persistence.

Conceptually:

```text
Java Objects
     ↓
ObjectOutputStream
     ↓
Byte Stream
     ↓
.dat File
```

When data is loaded:

```text
.dat File
     ↓
ObjectInputStream
     ↓
Byte Stream
     ↓
Java Objects
```

The entities implement `Serializable` where required.

Example:

```java
private static final long serialVersionUID = 1L;
```

This provides an explicit serialization version identifier for the serialized class.

---

# 🔑 Password Handling

Passwords are processed through `PasswordUtility` before persistence/comparison.

The `User` entity stores the value in:

```java
private String passwordHash;
```

The password itself is intentionally not displayed by `toString()`.

---

# ⚠️ Validation & Exception Handling

The system uses application-specific exceptions to communicate business failures.

Examples include:

```text
InvalidUserException
UserNotFoundException
InvalidExpenseException
ExpenseNotFoundException
```

Validation includes:

* blank User ID
* blank username
* blank email
* invalid email format
* blank password
* invalid contact number
* duplicate User ID
* duplicate email
* unknown users
* invalid account status
* unauthorized administrator operations

Example:

```text
========== UPDATE USER ==========

Enter User ID to update: USR002

Update Failed:
An account is already registered with email
sneha.rao@gmail.com.
```

---

# 🧪 Testing

The project contains a completed baseline of:

## **47 / 47 Test Scenarios**

The test coverage includes:

* registration
* duplicate-user validation
* duplicate-email validation
* login
* invalid credentials
* inactive-account login
* user search
* user update
* invalid email
* invalid contact number
* activation
* deactivation
* repeated activation/deactivation
* administrator authorization
* user deletion
* administrator-account protection
* deletion cancellation
* blank input validation
* operation-history verification

### Test Result

```text
Baseline Test Scenarios : 47
Completed               : 47
Baseline Completion     : 100%
```

The 47 scenarios represent the project's completed baseline and are not artificially increased by counting additional variations as new baseline cases.

---

# 📊 Functional Testing

Functional testing verifies whether each implemented business operation behaves according to its requirement.

Major verified workflows:

```text
Registration
     ↓
Authentication
     ↓
Role Verification
     ↓
User / Admin Operations
     ↓
Validation
     ↓
Persistence
     ↓
Operation History
```

---

# 🔬 Non-Functional Considerations

The project also considers:

| Area            | Consideration                      |
| --------------- | ---------------------------------- |
| Usability       | Clear console prompts and messages |
| Maintainability | Layered architecture               |
| Reliability     | Persistent serialized data         |
| Security        | Password hashing and authorization |
| Data Integrity  | Duplicate and validation checks    |
| Extensibility   | Repository/service separation      |
| Performance     | Appropriate for project-scale data |

---

# 🧑‍💻 Developer Maintenance Guide

One of the main design goals is maintainability.

### Where should a developer modify the application?

| Requirement                       | Primary Layer            |
| --------------------------------- | ------------------------ |
| Change console prompt             | Menu                     |
| Change business rule              | Service                  |
| Change validation                 | Service                  |
| Change stored data structure      | Entity                   |
| Change persistence behavior       | Repository / Utility     |
| Change password processing        | PasswordUtility          |
| Add application exception         | Exception package        |
| Change operation-history behavior | OperationHistory Service |
| Replace file persistence with DB  | Repository layer         |

### Maintenance Principle

> **Modify the smallest responsible layer.**

For example, a console-message change should not require changing the repository or entity layer.

---

# ▶️ How to Run

## Prerequisites

* Java Development Kit
* Java IDE such as Eclipse or IntelliJ IDEA
* Project source code
* Write permission for the application's persistence directory

## Execution

```text
1. Import the project into the IDE.
2. Verify the Java version/source configuration.
3. Build the project.
4. Run the application's main class.
5. Authenticate as USER or ADMIN.
6. Select the appropriate menu operation.
7. Verify generated/updated .dat files.
```

---

# 📁 Persistence Files

The application uses serialized files for current persistence.

```text
users.dat
expense.dat
history.dat
```

These files should be treated as application data and should not be manually edited.

---

# 🔮 Future Enhancements

The current architecture provides a foundation for further development.

Potential improvements include:

* MySQL database integration using JDBC
* REST API layer
* Spring Boot migration
* Web frontend
* Role-based permission management
* JUnit automated regression testing
* CI/CD integration
* CSV/PDF reporting
* Backup and restore
* Advanced monthly/annual expense reports
* Centralized application logging
* Production-grade password hashing with salts
* Cloud deployment

---

# 📚 Documentation

The repository should contain the project documentation alongside the source code.

Recommended structure:

```text
Expense-Tracking-System/
│
├── src/
├── docs/
│   └── Expense_Tracking_System_IT_Requirements_Technical_User_Manual.pdf
│
├── README.md
├── .gitignore
└── LICENSE
```

The technical manual documents:

* Requirements
* Architecture
* Package/class responsibilities
* User workflow
* Administrator workflow
* Input/output behavior
* Persistence
* Security
* Exceptions
* Operation history
* 47 baseline test scenarios
* Functional testing
* Non-functional testing
* Deployment
* Maintenance

---

# 🎓 Java Concepts Demonstrated

This project demonstrates practical usage of:

* Object-Oriented Programming
* Encapsulation
* Abstraction
* Interfaces
* Collections
* ArrayList
* Exception Handling
* Custom Exceptions
* File Handling
* Serialization
* Deserialization
* `Serializable`
* `serialVersionUID`
* LocalDateTime
* Regular Expressions
* Service/Repository separation
* Authentication
* Authorization
* Input Validation
* CRUD Operations
* Layered Architecture

---

# 📈 Engineering Learning Outcome

This project was developed to move beyond isolated Java programs and understand how multiple concepts work together inside an application.

The important learning was not simply writing individual classes.

It was understanding the complete flow:

```text
Requirement
    ↓
Entity Design
    ↓
Interface
    ↓
Service Logic
    ↓
Validation
    ↓
Repository
    ↓
Persistence
    ↓
User Interaction
    ↓
Exception Handling
    ↓
Operation History
    ↓
Testing
    ↓
Documentation
```

---

# 👨‍💻 Project Positioning

This project is intentionally presented as a **Java application demonstrating software-engineering fundamentals and maintainable architecture**.

It is not positioned as a production-scale enterprise expense platform.

The architecture provides a foundation from which persistence, APIs, automated testing and UI layers can be introduced as the project evolves.

---

# 📌 Project Status

**Status:** Completed baseline implementation and functional verification

**Baseline Testing:** 47 / 47 scenarios completed

**Architecture:** Layered Java Application

**Persistence:** Java Serialization / `.dat` files

**Roles:** USER / ADMIN

**Documentation:** IT Requirements & Technical User Manual

---

# 👤 Author

**Anudeep Pusapati**

Java Developer | Java Full Stack Development | Software Engineering

---

## ⭐ Repository Goal

The goal of this project is to demonstrate the ability to take a software requirement, design a maintainable structure, implement business rules, validate inputs, persist data, handle failures, test the application and document the solution for future developers.

If you are reviewing this repository as a recruiter or developer, the most important part is not the number of classes.

It is the complete engineering flow from **requirement → design → implementation → validation → testing → documentation**.
