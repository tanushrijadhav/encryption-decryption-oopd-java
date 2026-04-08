package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import context.EncryptionContext;
import crypto.CaesarStrategy;
import crypto.AESStrategy;
import crypto.EncryptionStrategy;
import util.FileHandler;

import java.io.File;

public class FileController {

    @FXML private TextField filePathField;
    @FXML private ComboBox<String> fileAlgoSelector;
    @FXML private TextField fileKeyField;
    @FXML private TextArea fileOriginalContent;
    @FXML private TextArea fileOutputContent;
    @FXML private Label fileStatusLabel;

    private final EncryptionContext context = new EncryptionContext();
    private File selectedFile;

    @FXML
    public void initialize() {
        fileAlgoSelector.getItems().addAll("AES (Recommended)", "Caesar Cipher");
        fileAlgoSelector.getSelectionModel().selectFirst();
    }

    @FXML
    private void onBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Files", "*.txt"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        Stage stage = (Stage) filePathField.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
            try {
                String content = FileHandler.readFile(selectedFile.getAbsolutePath());
                fileOriginalContent.setText(content);
                fileStatusLabel.setText("File loaded: " + selectedFile.getName());
            } catch (Exception e) {
                fileStatusLabel.setText("Error reading file: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onEncryptFile() { processFile(true); }

    @FXML
    private void onDecryptFile() { processFile(false); }

    @FXML
    private void onSaveOutput() {
        String output = fileOutputContent.getText();
        if (output == null || output.isEmpty()) {
            fileStatusLabel.setText("No output to save.");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Output");
        fileChooser.setInitialFileName("output.txt");
        Stage stage = (Stage) filePathField.getScene().getWindow();
        File saveFile = fileChooser.showSaveDialog(stage);

        if (saveFile != null) {
            try {
                FileHandler.writeFile(saveFile.getAbsolutePath(), output);
                fileStatusLabel.setText("Saved to: " + saveFile.getName());
            } catch (Exception e) {
                fileStatusLabel.setText("Error saving: " + e.getMessage());
            }
        }
    }

    private void processFile(boolean encrypt) {
        String content = fileOriginalContent.getText();
        String key = fileKeyField.getText();
        String algo = fileAlgoSelector.getValue();

        if (content == null || content.isEmpty()) {
            fileStatusLabel.setText("No file content to process.");
            return;
        }
        if (key == null || key.isEmpty()) {
            fileStatusLabel.setText("Please enter a key.");
            return;
        }

        try {
            EncryptionStrategy strategy;
            if (algo != null && algo.startsWith("AES")) {
                strategy = new AESStrategy();
            } else {
                strategy = new CaesarStrategy();
            }
            context.setStrategy(strategy);

            String result = encrypt ? context.encrypt(content, key) : context.decrypt(content, key);
            fileOutputContent.setText(result);
            fileStatusLabel.setText(encrypt ? "File encrypted successfully" : "File decrypted successfully");
        } catch (Exception e) {
            fileStatusLabel.setText("Error: " + e.getMessage());
        }
    }
}
