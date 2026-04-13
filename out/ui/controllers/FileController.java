package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import context.EncryptionContext;
import crypto.StrategyFactory;
import util.FileHandler;

import java.io.File;

public class FileController {

    // UI components injected from FXML
    @FXML private TextField filePathField;
    @FXML private ComboBox<String> fileAlgoSelector;
    @FXML private TextField fileKeyField;
    @FXML private TextArea fileOriginalContent;
    @FXML private TextArea fileOutputContent;
    @FXML private Label fileStatusLabel;

    // Core encryption handler
    private final EncryptionContext context = new EncryptionContext();

    // Stores selected file reference
    private File selectedFile;

    @FXML
    public void initialize() {

        // Populate algorithm dropdown
        fileAlgoSelector.getItems().addAll(
                "AES-128-CBC (Recommended)",
                "Blowfish-CBC",
                "Caesar Cipher (Educational)"
        );

        // Select default algorithm
        fileAlgoSelector.getSelectionModel().selectFirst();
    }

    @FXML
    private void onBrowse() {

        // Create file chooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");

        // Set file filters (text files + all files)
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Get current window (stage)
        Stage stage = (Stage) filePathField.getScene().getWindow();

        // Open file dialog and store selected file
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {

            // Display file path
            filePathField.setText(selectedFile.getAbsolutePath());

            try {
                // Read file content using utility class
                String content = FileHandler.readFile(selectedFile.getAbsolutePath());

                // Display file content in UI
                fileOriginalContent.setText(content);

                // Update status
                fileStatusLabel.setText("File loaded: " + selectedFile.getName());

            } catch (Exception e) {

                // Handle read errors
                fileStatusLabel.setText("Error reading file: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onEncryptFile() {

        // Trigger encryption process
        processFile(true);
    }

    @FXML
    private void onDecryptFile() {

        // Trigger decryption process
        processFile(false);
    }

    @FXML
    private void onSaveOutput() {

        // Get processed output
        String output = fileOutputContent.getText();

        // Validate output
        if (output == null || output.isEmpty()) {
            fileStatusLabel.setText("No output to save.");
            return;
        }

        // Create save dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Output");
        fileChooser.setInitialFileName("output.txt");

        Stage stage = (Stage) filePathField.getScene().getWindow();

        // Open save dialog
        File saveFile = fileChooser.showSaveDialog(stage);

        if (saveFile != null) {
            try {
                // Write output to file
                FileHandler.writeFile(saveFile.getAbsolutePath(), output);

                // Update status
                fileStatusLabel.setText("Saved to: " + saveFile.getName());

            } catch (Exception e) {

                // Handle write errors
                fileStatusLabel.setText("Error saving: " + e.getMessage());
            }
        }
    }

    private void processFile(boolean encrypt) {

        // Get inputs
        String content = fileOriginalContent.getText();
        String key = fileKeyField.getText();
        String algo = fileAlgoSelector.getValue();

        // Validate content
        if (content == null || content.isEmpty()) {
            fileStatusLabel.setText("No file content to process.");
            return;
        }

        // Validate key
        if (key == null || key.isEmpty()) {
            fileStatusLabel.setText("Please enter a key.");
            return;
        }

        try {
            // Set encryption strategy dynamically
            context.setStrategy(StrategyFactory.create(algo));

            // Perform encryption or decryption
            String result = encrypt
                    ? context.encrypt(content, key)
                    : context.decrypt(content, key);

            // Display result
            fileOutputContent.setText(result);

            // Update status
            fileStatusLabel.setText(
                    encrypt ? "File encrypted successfully" : "File decrypted successfully"
            );

        } catch (Exception e) {

            // Handle runtime errors
            fileStatusLabel.setText("Error: " + e.getMessage());
        }
    }
}