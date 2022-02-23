package com.nelsonaraujo.wgu.View_Controller;

/** Add parts controller.
 * Controls for PartAddView.fxml
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

import com.nelsonaraujo.wgu.Model.InhousePart;
import com.nelsonaraujo.wgu.Model.Inventory;
import com.nelsonaraujo.wgu.Model.OutsourcedPart;
import com.nelsonaraujo.wgu.Model.Part;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class PartAddController {
    @FXML
    private Button okBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ToggleGroup partTypeGroup;

    @FXML
    private ToggleButton inHouse;

    @FXML
    private ToggleButton outsourced;

    @FXML
    private TextField partID;

    @FXML
    private TextField partName;

    @FXML
    private TextField partInventoryLevel;

    @FXML
    private TextField partPrice;

    @FXML
    private TextField partInventoryMaximum;

    @FXML
    private TextField partInventoryMinimum;

    @FXML
    private Label machineIdCompNameLabel;

    @FXML
    private TextField machineIdCompNameField;

    Inventory primaryInv;

    /** PartAddController constructor.
     * Assigns passed inventory to local inventory
     * @param primaryInv The primary inventory list
     */
    public PartAddController(Inventory primaryInv) {
        this.primaryInv = primaryInv; // Assign the passes inventory to the instance inventory
    }

    /** Create new part.
     * Create a new part based on the user input.
     */
    @FXML
    private void saveNewPart(){
        if(inHouse.isSelected()){
            Part newPart = new InhousePart(
                    Integer.parseInt(partID.getText().trim()),
                    partName.getText().trim(),
                    Double.parseDouble(partPrice.getText().trim()),
                    Integer.parseInt(partInventoryLevel.getText().trim()),
                    Integer.parseInt(partInventoryMinimum.getText().trim()),
                    Integer.parseInt(partInventoryMaximum.getText().trim()),
                    Integer.parseInt(machineIdCompNameField.getText().trim()));

            primaryInv.addPart(newPart);

        } else if(outsourced.isSelected()){
            Part newPart = new OutsourcedPart(
                    Integer.parseInt(partID.getText().trim()),
                    partName.getText().trim(),
                    Double.parseDouble(partPrice.getText().trim()),
                    Integer.parseInt(partInventoryLevel.getText().trim()),
                    Integer.parseInt(partInventoryMinimum.getText().trim()),
                    Integer.parseInt(partInventoryMaximum.getText().trim()),
                    machineIdCompNameField.getText().trim());

            primaryInv.addPart(newPart);

        } else { // Fail safe
            AlertMessage.errorPart(10); // Unknown part type error
        }
    }

    /** Update the machineIdCompNameLabel based on part type selected.
     * Set the label to "Machine ID" if In-house is selected and "Company Name" if outsourced is selected. Change Opacity of field to display.
     */
    @FXML
    private void partTypeGroupAction(){
        getNewPartId(); // Generate the part ID

        // In-House is selected
        if(partTypeGroup.getSelectedToggle().toString().contains("In-House")) {
            machineIdCompNameLabel.setText("Machine ID");
            machineIdCompNameField.setOpacity(100);
        }

        // Outsourced is selected
        if(partTypeGroup.getSelectedToggle().toString().contains("Outsourced")) {
            machineIdCompNameLabel.setText("Company Name");
            machineIdCompNameField.setOpacity(100);
        }

    }

    /** When OK button is clicked, validate and save.
     * Validate all fields and save if validation is successful.
     */
    @FXML
    private void okBtnAction(){
        Stage stage = (Stage) okBtn.getScene().getWindow(); // Get the stage from button

         if ( isFieldsValid() == true ) {
             saveNewPart(); // Save part
             stage.close(); // Close stage
        } else {
             // Do nothing
         }
    }

    /** Validate fields per requirements.
     *
     * @return True is all fields are valid, False if not.
     */
    @FXML
    private boolean isFieldsValid() {
        boolean isValid = false;

        // Reset all field borders to default
        fieldStyleReset();


        try { // Check for invalid formats
            if (partTypeGroup.getSelectedToggle() == null) { // Part type not selected
                AlertMessage.errorPart(2);

            } else if (partID.getText().trim().isEmpty() || // Check for empty fields and highlight
                    partName.getText().trim().isEmpty() ||
                    partInventoryLevel.getText().trim().isEmpty() ||
                    partPrice.getText().trim().isEmpty() ||
                    partInventoryMaximum.getText().trim().isEmpty() ||
                    partInventoryMinimum.getText().trim().isEmpty() ||
                    machineIdCompNameField.getText().trim().isEmpty()) {

                if (partName.getText().trim().isEmpty()) {
                    AlertMessage.fieldError(partName);
                }

                if (partInventoryLevel.getText().trim().isEmpty()) {
                    AlertMessage.fieldError(partInventoryLevel);
                }

                if (partPrice.getText().trim().isEmpty()) {
                    AlertMessage.fieldError(partPrice);
                }

                if (partInventoryMaximum.getText().trim().isEmpty()) {
                    AlertMessage.fieldError(partInventoryMaximum);
                }

                if (partInventoryMinimum.getText().trim().isEmpty()) {
                    AlertMessage.fieldError(partInventoryMinimum);
                }

                if (machineIdCompNameField.getText().trim().isEmpty()) {
                    AlertMessage.fieldError(machineIdCompNameField);
                }

                AlertMessage.errorPart(1); // Notify user of empty fields

                // Inventory minimum is higher than maximum
            } else if (Integer.parseInt(partInventoryMaximum.getText().trim()) < Integer.parseInt(partInventoryMinimum.getText().trim())) {
                AlertMessage.fieldError(partInventoryMinimum); // Highlight field
                AlertMessage.fieldError(partInventoryMaximum); // Highlight field
                AlertMessage.errorPart(8); // Display error dialog

            } else if (Double.parseDouble(partPrice.getText().trim()) <= 0) {
                AlertMessage.fieldError(partPrice); // Highlight field
                AlertMessage.errorPart(5); // Display error dialog

                // Inventory level is lower than minimum
            } else if (Integer.parseInt(partInventoryLevel.getText().trim()) < Integer.parseInt(partInventoryMinimum.getText().trim())) {
                AlertMessage.fieldError(partInventoryLevel); // Highlight field
                AlertMessage.errorPart(6); // Display error dialog

                // Inventory level is higher than maximum
            } else if (Integer.parseInt(partInventoryLevel.getText().trim()) > Integer.parseInt(partInventoryMaximum.getText().trim())) {
                AlertMessage.fieldError(partInventoryLevel); // Highlight field
                AlertMessage.errorPart(7); // Display error dialog

                // In-House machine ID is an integer
            } else if (inHouse.isSelected()) {
                boolean isInt = true;

                try {
                    // Try if the field will return an error
                    Integer.parseInt(machineIdCompNameField.getText().trim());
                } catch (NumberFormatException e) {
                    // Field is not an integer
                    isInt = false;
                    AlertMessage.fieldError(machineIdCompNameField); // Highlight field
                    AlertMessage.errorPart(9); // Display error dialog
                }

                // Set isValid
                if (isInt) {
                    isValid = true;
                }


            } else { // All validations passed
                isValid = true;
            }

        } catch (NumberFormatException e){ // Invalid format
            AlertMessage.errorPart(3);
        }

        return isValid;
    }

    /** Set part ID.
     * Sets a unused part ID.
     */
    @FXML
    private void getNewPartId(){
        partID.setText( String.valueOf(primaryInv.getUniquePartID()) );
    }

    /** Close the window.
     * Closes window without processing any data
     */
    @FXML
    private void cancelBtnAction(){
        Stage stage= (Stage) cancelBtn.getScene().getWindow();
        stage.close();
    }

    /** Clear text error border.
     * Set the field border back to null.
     */
    @FXML
    private void fieldStyleReset(){
        partName.setStyle(null);
        partInventoryLevel.setStyle(null);
        partPrice.setStyle(null);
        partInventoryMaximum.setStyle(null);
        partInventoryMinimum.setStyle(null);
        machineIdCompNameField.setStyle(null);
    }

}
