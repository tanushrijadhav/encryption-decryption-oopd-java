package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import context.EncryptionContext;
import crypto.StrategyFactory;

public class EncryptionController {

    // UI components injected from FXML
    @FXML private ComboBox<String> algorithmSelector;
    @FXML private TextField keyField;
    @FXML private TextArea inputArea;
    @FXML private TextArea outputArea;
    @FXML private Label inputCharCount;
    @FXML private Label outputCharCount;
    @FXML private Label statusLabel;
    @FXML private Button encryptBtn;
    @FXML private Button decryptBtn;

    // Core encryption handler (uses Strategy Pattern)
    private final EncryptionContext context = new EncryptionContext();

    @FXML
    public void initialize() {

        // Populate dropdown with available algorithms
        algorithmSelector.getItems().addAll(
            "AES-128-CBC (Recommended)",
            "Blowfish-CBC",
            "Caesar Cipher (Educational)"
        );

        // Select default algorithm
        algorithmSelector.getSelectionModel().selectFirst();

        // Listener to update input character count dynamically
        inputArea.textProperty().addListener((obs, oldVal, newVal) -> {
            int len = newVal == null ? 0 : newVal.length();
            inputCharCount.setText(len + " characters");
        });

        // Listener to update output character count dynamically
        outputArea.textProperty().addListener((obs, oldVal, newVal) -> {
            int len = newVal == null ? 0 : newVal.length();
            outputCharCount.setText(len + " characters");
        });
    }

    @FXML
    private void onEncrypt() {
        // Trigger encryption flow
        processText(true);
    }

    @FXML
    private void onDecrypt() {
        // Trigger decryption flow
        processText(false);
    }

    @FXML
    private void onClearInput() {
        // Clear input and output fields
        inputArea.clear();
        outputArea.clear();

        // Reset status label
        statusLabel.setText("");
        statusLabel.getStyleClass().setAll("status-label");
    }

    @FXML
    private void onCopyOutput() {

        // Get output text
        String output = outputArea.getText();

        // Copy to clipboard if not empty
        if (output != null && !output.isEmpty()) {
            ClipboardContent content = new ClipboardContent();
            content.putString(output);
            Clipboard.getSystemClipboard().setContent(content);

            // Show success message
            statusLabel.setText("Copied to clipboard!");
            statusLabel.getStyleClass().setAll("status-label", "status-success");
        }
    }

    private void processText(boolean encrypt) {

        // Get user inputs
        String input = inputArea.getText();
        String key = keyField.getText();
        String algo = algorithmSelector.getValue();

        // Validate input text
        if (input == null || input.isEmpty()) {
            showError("Please enter text to process.");
            return;
        }

        // Validate key
        if (key == null || key.isEmpty()) {
            showError("Please enter an encryption key.");
            return;
        }

        try {
            // Set encryption strategy dynamically
            context.setStrategy(StrategyFactory.create(algo));

            String result;

            // Perform encryption or decryption
            if (encrypt) {
                result = context.encrypt(input, key);
            } else {
                result = context.decrypt(input, key);
            }

            // Display result
            outputArea.setText(result);

            // Show success status
            statusLabel.setText(encrypt ? "Encrypted successfully" : "Decrypted successfully");
            statusLabel.getStyleClass().setAll("status-label", "status-success");

        } catch (Exception e) {

            // Handle runtime errors
            showError("Error: " + e.getMessage());
        }
    }

    private void showError(String message) {

        // Display error message
        statusLabel.setText(message);

        // Apply error styling
        statusLabel.getStyleClass().setAll("status-label", "status-error");
    }
}