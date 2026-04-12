# CipherForge — Modular Encryption Suite

CipherForge is a modular encryption and decryption platform built in Java using JavaFX. It demonstrates strong object-oriented design principles, a clean separation of concerns, and an extensible architecture for implementing multiple encryption algorithms.

> ⚠️ **Status: Work in Progress** — This project is actively being developed as part of an OOPD Mini Project. Features marked 🚧 are planned but not yet implemented.

---

## Overview

CipherForge is designed as a pluggable encryption framework that allows users to encrypt and decrypt text and files using different algorithms through a graphical interface.

The project focuses on:
- Object-Oriented Programming principles
- Strategy Design Pattern
- Modular and scalable architecture
- Separation of UI and business logic
- Desktop application development using JavaFX

---

## Feature Status

| Feature                           | Status           |
|-----------------------------------|------------------|
| `EncryptionStrategy` interface    | ✅ Done          |
| `CaesarStrategy`                  | ✅ Done          |
| `AESStrategy` (CBC mode)          | ✅ Done          |
| `FileEncryptionUtil`              | ✅ Done          |
| `EncryptionContext`               | ✅ Done          |
| `FileHandler`                     | 🚧 In Progress   |
| `EncryptionException`             | 🚧 In Progress   |
| `InvalidKeyException`             | 🚧 In Progress   |
| JavaFX UI (FXML + CSS)            | ✅ Done          |
| `MainApp` entry point             | ✅ Done          |
| UML Diagrams                      | 🚧 In Progress   |

---

## Project Architecture

```
                        MainApp.java
                             │
                       ConsoleUI.java
                             │
                   EncryptionContext.java
                             │
               ┌─────────────┴─────────────┐
               │                           │
        AESStrategy                 CaesarStrategy
               │
        FileEncryptionUtil
                             │
                    EncryptionStrategy
                        (interface)
                             │
              ┌──────────────┴──────────────┐
              │                             │
      EncryptionException          InvalidKeyException
```

---

## OOP Concepts Covered

| Concept             | Where                                                 |
|---------------------|-------------------------------------------------------|
| Encapsulation       | Private fields with controlled access throughout      |
| Abstraction         | `EncryptionStrategy` interface                        |
| Inheritance         | `AESStrategy`, `CaesarStrategy` implement interface   |
| Polymorphism        | `encrypt()`, `decrypt()` behave differently per class |
| Composition         | `EncryptionContext` has-a `EncryptionStrategy`        |
| Method Overriding   | Each strategy overrides interface methods             |
| Exception Handling  | `EncryptionException`, `InvalidKeyException`          |
| Packages            | Modular structure across crypto, context, util        |
| Design Pattern      | Strategy Pattern                                      |

---

## Folder Structure

```
src/
├── crypto/
│    ├── EncryptionStrategy.java    ← Strategy interface
│    ├── CaesarStrategy.java        ← Caesar Cipher
│    ├── AESStrategy.java           ← AES-128 CBC
│    └── FileEncryptionUtil.java    ← File encryption via AES
├── context/
│    └── EncryptionContext.java     ← Strategy selector
├── util/
│    └── FileHandler.java           ← File read/write
├── exceptions/
│    ├── EncryptionException.java
│    └── InvalidKeyException.java
├── ui/
│    ├── fxml/                      ← Layout files
│    ├── css/                       ← Styling
│    └── controllers/               ← UI controllers
└── MainApp.java                    ← Entry point
```

---

## Encryption Algorithms

### Caesar Cipher
Shifts each character by a numeric key value.

```
Input  : "HELLO", key = 3
Output : "KHOOR"
```

Simple substitution cipher used for testing system flow.

### AES-128 (Advanced Encryption Standard)
Industry-standard symmetric block cipher using Java Cryptography API.

| Property      | Value                            |
|---------------|----------------------------------|
| Mode          | CBC (Cipher Block Chaining)      |
| Key           | Any string → SHA-256 → 16 bytes  |
| IV            | Random 16 bytes per encryption   |
| Output        | Base64 encoded string            |
| Block Size    | 16 bytes                         |
| Rounds        | 10                               |

### File Encryption
Encrypts any file type using AES internally.

| Operation | Flow                                                |
|-----------|-----------------------------------------------------|
| Encrypt   | file bytes → Base64 → AES encrypt → .encrypted     |
| Decrypt   | .encrypted → AES decrypt → Base64 → original file  |

---

## Security Design Decisions

| Decision                | Reason                                                                                                              |
|-------------------------|---------------------------------------------------------------------------------------------------------------------|
| CBC mode instead of ECB | ECB leaks patterns — identical blocks produce identical ciphertext. CBC chains each block to the previous output.   |
| SHA-256 key derivation  | AES needs exactly 16 bytes. SHA-256 converts any password to a fixed output. Zero-padding is guessable.             |
| SecureRandom for IV     | Regular Random is predictable if seed is known. SecureRandom uses hardware entropy — truly unpredictable.           |
| IV prepended to output  | Decryptor needs same IV. Stored as first 16 bytes of output.                                                        |
| Base64 encoding         | Raw bytes corrupt in String form. Base64 is safe and printable.                                                     |

---

## Error Handling

| Error                  | Handled By                   |
|------------------------|------------------------------|
| Invalid key format     | `InvalidKeyException`        |
| Null strategy          | `EncryptionException`        |
| File read/write errors | `FileHandler` + exceptions   |
| AES key length issues  | `buildKey()` via SHA-256     |

---

## System Flow

```
User Input → MainApp → EncryptionContext → Strategy → Output
```

1. User selects algorithm (AES / Caesar)
2. User provides input data and key
3. Context sets the chosen strategy
4. Strategy performs encryption or decryption
5. Result is displayed to the user

---

## How to Run

> 🚧 Full setup instructions will be added once the project compiles end-to-end.

Requirements: JDK 17+, JavaFX SDK. No external dependencies.

```
# Clone the repository
git clone https://github.com/tanushrijadhav/encryption-decryption-oopd-java.git
cd encryption-decryption-oopd-java

# Run the batch file
run.bat
```

---

## Team Contribution

| Member | Responsibility                             | Modules                            |
|--------|--------------------------------------------|------------------------------------|
| TJ     | Crypto logic — Caesar, AES, interface      | `crypto/`                          |
| TG     | Context, File Handling, Exceptions         | `context/`, `util/`, `exceptions/` |
| AG     | MainApp, UI, Integration                   | `ui/`, `MainApp.java`              |

---

## Future Improvements

| Improvement                  | Description                              |
|------------------------------|------------------------------------------|
| RSA and modern algorithms    | Extend beyond AES and Caesar             |
| Encryption pipelines         | Multi-layer encryption support           |
| File drag-and-drop           | Easier file selection in UI              |
| Secure key management        | Proper key storage and validation        |
| Performance metrics          | Benchmark AES vs Caesar                  |
| Password strength validation | Reject weak keys before encryption       |

---

## Resume Description

**CipherForge — Encryption Framework with Modular OOP Architecture**

- Designed a pluggable encryption system using the Strategy Pattern
- Implemented AES-128 CBC and Caesar Cipher encryption algorithms
- Built a JavaFX-based user interface with FXML and CSS
- Developed a modular architecture separating UI, logic, and utilities
- Enabled extensibility for adding future encryption techniques
- Implemented secure key derivation using SHA-256 hashing

---

## Learning Outcomes

- Practical implementation of OOP concepts
- Understanding of the Strategy Design Pattern
- Hands-on experience with Java Cryptography API
- Modular software design
- Team collaboration using GitHub

---

## License

This project is for educational and demonstration purposes.

---

OOPD Mini Project — actively being developed
