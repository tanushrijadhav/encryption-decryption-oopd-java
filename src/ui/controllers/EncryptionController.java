package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import context.EncryptionContext;
import crypto.StrategyFactory;

public class EncryptionController {

    @FXML
    private ComboBox<String> algorithmSelector;
    @FXML
    private TextField keyField;
    @FXML
    private TextArea inputArea;
    @FXML
    private TextArea outputArea;
    @FXML
    private Label inputCharCount;
    @FXML
    private Label outputCharCount;
    @FXML
    private Label statusLabel;
    @FXML
    private Button encryptBtn;
    @FXML
    private Button decryptBtn;

    private final EncryptionContext context = new EncryptionContext();

    @FXML
    public void initialize() {
        algorithmSelector.getItems().addAll("AES-256-GCM (Recommended)", "Blowfish-CBC", "Caesar Cipher (Educational)");
        algorithmSelector.getSelectionModel().selectFirst();

        inputArea.textProperty().addListener((obs, oldVal, newVal) -> {
            int len = newVal == null ? 0 : newVal.length();
            inputCharCount.setText(len + " characters");
        });

        outputArea.textProperty().addListener((obs, oldVal, newVal) -> {
            int len = newVal == null ? 0 : newVal.length();
            outputCharCount.setText(len + " characters");
        });
    }

    @FXML
    private void onEncrypt() {
        processText(true);
    }

    @FXML
    private void onDecrypt() {
        processText(false);
    }

    @FXML
    private void onClearInput() {
        inputArea.clear();
        outputArea.clear();
        statusLabel.setText("");
        statusLabel.getStyleClass().setAll("status-label");
    }

    @FXML
    private void onCopyOutput() {
        String output = outputArea.getText();
        if (output != null && !output.isEmpty()) {
            ClipboardContent content = new ClipboardContent();
            content.putString(output);
            Clipboard.getSystemClipboard().setContent(content);
            statusLabel.setText("Copied to clipboard!");
            statusLabel.getStyleClass().setAll("status-label", "status-success");
        }
    }

    private void processText(boolean encrypt) {
        String input = inputArea.getText();
        String key = keyField.getText();
        String algo = algorithmSelector.getValue();

        if (input == null || input.isEmpty()) {
            showError("Please enter text to process.");
            return;
        }
        if (key == null || key.isEmpty()) {
            showError("Please enter an encryption key.");
            return;
        }

        try {
            context.setStrategy(StrategyFactory.create(algo));

            String result;
            if (encrypt) {
                result = context.encrypt(input, key);
            } else {
                result = context.decrypt(input, key);
            }

            outputArea.setText(result);
            statusLabel.setText(encrypt ? "Encrypted successfully" : "Decrypted successfully");
            statusLabel.getStyleClass().setAll("status-label", "status-success");
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.getStyleClass().setAll("status-label", "status-error");
    }
}
