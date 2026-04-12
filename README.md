# Encryption-Decryption Tool

A Java-based encryption-decryption application that secures text and files
using multiple algorithms, built with strong Object-Oriented Programming
principles and the Strategy Design Pattern.

---

## Problem Statement

With increasing data security concerns, there is a need for systems that can:
- Secure sensitive data
- Provide flexible encryption options
- Maintain clean and scalable design

This project addresses these needs using Java and OOP Design Patterns.

---

## Feature Status

| Feature                           | Status           |
|-----------------------------------|------------------|
| `EncryptionStrategy` interface    | ✅ Done          |
| `CaesarStrategy`                  | ✅ Done          |
| `AESStrategy` (CBC mode)          | ✅ Done          |
| `FileEncryptionUtil`              | ✅ Done          |
| `EncryptionContext`               | ✅ Done          |
| `FileHandler`                     | ✅ Done          |
| `EncryptionException`             | ✅ Done          |
| `InvalidKeyException`             | ✅ Done          |
| `ConsoleUI`                       | ✅ Done          |
| `MainApp` entry point             | ✅ Done          |
| GUI (JavaFX)                      | ✅ Done          |
| UML Diagrams                      | 🚧 In Progress   |

---

## Features

### Basic
- Encrypt and decrypt text
- Support for Caesar Cipher
- Console-based interaction

### Intermediate
- AES encryption using Java Cryptography API
- File handling — read/write encrypted data
- Custom exception handling

### Advanced
- Strategy Design Pattern implementation
- Modular package structure
- Extensible for adding new algorithms
- GUI support via JavaFX

---

## Project Architecture
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

---

## System Flow
User Input → MainApp → EncryptionContext → Strategy → Output

1. User selects algorithm (AES / Caesar)
2. User provides input data and key
3. Context sets the chosen strategy
4. Strategy performs encryption or decryption
5. Result is displayed to the user

---

## Folder Structure
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
│    └── ConsoleUI.java
└── MainApp.java                    ← Entry point

---

## OOP Concepts Used

| Concept             | Implementation                                        |
|---------------------|-------------------------------------------------------|
| Encapsulation       | Private fields with controlled access throughout      |
| Abstraction         | `EncryptionStrategy` interface                        |
| Inheritance         | `AESStrategy`, `CaesarStrategy` implement interface   |
| Polymorphism        | `encrypt()`, `decrypt()` behave differently per class |
| Composition         | `EncryptionContext` has-a `EncryptionStrategy`        |
| Method Overriding   | Each strategy overrides interface methods             |
| Exception Handling  | Custom `EncryptionException`, `InvalidKeyException`   |
| Packages            | Modular structure across crypto, context, util        |
| Design Pattern      | Strategy Pattern                                      |

---

## Encryption Algorithms

### Caesar Cipher
Shifts each character by a numeric key value.
Input  : "HELLO", key = 3
Output : "KHOOR"

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

| Decision                | Reason                                                        |
|-------------------------|---------------------------------------------------------------|
| CBC mode instead of ECB | ECB leaks patterns — identical blocks produce identical       |
|                         | ciphertext. CBC chains each block to the previous output.     |
| SHA-256 key derivation  | AES needs exactly 16 bytes. SHA-256 converts any password     |
|                         | to a fixed output. Zero-padding short keys is guessable.      |
| SecureRandom for IV     | Regular Random is predictable if seed is known.               |
|                         | SecureRandom uses hardware entropy — truly unpredictable.     |
| IV prepended to output  | Decryptor needs same IV. Stored as first 16 bytes of output.  |
| Base64 encoding         | Raw bytes corrupt in String form. Base64 is safe and          |
|                         | printable.                                                    |

---

## Error Handling

| Error                  | Handled By                   |
|------------------------|------------------------------|
| Invalid key format     | `InvalidKeyException`        |
| Null strategy          | `EncryptionException`        |
| File read/write errors | `FileHandler` + exceptions   |
| AES key length issues  | `buildKey()` via SHA-256     |

---

## How to Run

Requirements: JDK 11+, Maven, JavaFX
git clone https://github.com/tanushrijadhav/encryption-decryption-oopd-java.git
cd encryption-decryption-oopd-java
mvn compile
mvn exec:java -Dexec.mainClass="MainApp"

---

## Team Contribution

| Member             | Responsibility                             | Modules                            |
|--------------------|--------------------------------------------|------------------------------------|
| Tanushri Jadhav    | Crypto logic — Caesar, AES, interface      | `crypto/`                          |
| Tanushka Gulhane   | Context, File Handling, Exceptions         | `context/`, `util/`, `exceptions/` |
| Adavit Gajewar     | MainApp, UI, Integration                   | `ui/`, `MainApp.java`              |

---

## Future Improvements

| Improvement                  | Description                          |
|------------------------------|--------------------------------------|
| Factory Pattern              | Dynamic algorithm selection          |
| Password strength validation | Reject weak keys before encryption   |
| Additional algorithms        | XOR, RSA support                     |
| Logging system               | Track encryption operations          |
| Performance comparison       | Benchmark AES vs Caesar              |

---

## Learning Outcomes

- Practical implementation of OOP concepts
- Understanding of the Strategy Design Pattern
- Hands-on experience with Java Cryptography API
- Modular software design
- Team collaboration using GitHub

---

Second Year B.Tech Computer Science — OOPD Mini Project
