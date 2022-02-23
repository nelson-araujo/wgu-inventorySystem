package com.nelsonaraujo.wgu.View_Controller;

/** Modify parts controller.
 * Controls for PartModifyView.fxml
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

public class PartModifyController {
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
    Part partSelected;

    /** PartModifyController Constructor.
     * Passes inventory and part selected
     * @param primaryInv Main inventory
     * @param partSelected Part selected to be modified
     */
    public PartModifyController(Inventory primaryInv, Part partSelected){
        this.primaryInv = primaryInv;
        this.partSelected = partSelected;
    }

    /** Initialize all fields.
     * Populate all fields based on the selected part
     */
    @FXML
    public void initializeFields(){
        if(partSelected instanceof InhousePart){
            inHouse.setSelected(true);
            machineIdCompNameLabel.setText("Machine ID");
            machineIdCompNameField.setText(Integer.toString( ((InhousePart) partSelected).getMachineId()) );
        } else {
            outsourced.setSelected(true);
            machineIdCompNameLabel.setText("Company Name");
            machineIdCompNameField.setText( ((OutsourcedPart)partSelected).getCompanyName() );
        }
        partID.setText(Integer.toString(partSelected.getPartId()));
        partName.setText(partSelected.getPartName());
        partInventoryLevel.setText(Integer.toString(partSelected.getPartStock()));
        partPrice.setText(Double.toString(partSelected.getPartPrice()));
        partInventoryMaximum.setText(Integer.toString(partSelected.getPartMax()));
        partInventoryMinimum.setText(Integer.toString(partSelected.getPartMin()));
    }

    /** Update part.
     * Update the part based on the user inputs.
     */
    @FXML
    private void updatePart(){
        // Is temp part in-house or outsourced
        if(inHouse.isSelected()){ // Part is in-house
            // Create temp part with user selections
            Part updatedPart = new InhousePart(
                    Integer.parseInt(partID.getText().trim()),
                    partName.getText().trim(),
                    Double.parseDouble(partPrice.getText().trim()),
                    Integer.parseInt(partInventoryLevel.getText().trim()),
                    Integer.parseInt(partInventoryMinimum.getText().trim()),
                    Integer.parseInt(partInventoryMaximum.getText().trim()),
                    Integer.parseInt(machineIdCompNameField.getText().trim()));

            // Update inventory
            primaryInv.updatePart(updatedPart.getPartId(),updatedPart);

        } else if(outsourced.isSelected()) { // Part is outsourced
            // Create temp part with user selections
            Part updatedPart = new OutsourcedPart(
                    Integer.parseInt(partID.getText().trim()),
                    partName.getText().trim(),
                    Double.parseDouble(partPrice.getText().trim()),
                    Integer.parseInt(partInventoryLevel.getText().trim()),
                    Integer.parseInt(partInventoryMinimum.getText().trim()),
                    Integer.parseInt(partInventoryMaximum.getText().trim()),
                    machineIdCompNameField.getText().trim());

            // Update inventory
            primaryInv.updatePart(updatedPart.getPartId(),updatedPart);

        } else{ // Fail safe
            AlertMessage.errorPart(2);
        }
    }

    /** Validate fields.
     * Validate all fields per requirements
     * @return True is all fields are valid, False if not.
     */
    @FXML
    private boolean isFieldsValid() {
        boolean isValid = false;

        // Reset all field borders to default
        fieldStyleReset();

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
            boolean isInt=true;

            try {
                // Try if the field will return an error
                Integer.parseInt(machineIdCompNameField.getText().trim());
            } catch(NumberFormatException e) {
                // Field is not an integer
                isInt=false;
                AlertMessage.fieldError(machineIdCompNameField); // Highlight field
                AlertMessage.errorPart(9); // Display error dialog
            }

            // Set isValid
            if(isInt){
                isValid=true;
            }


        } else { // All validations passed
            isValid = true;
        }

        return isValid;
    }

    /** OK button selecte; validate and save.
     * Validate all fields and save if validation is successful.
     */
    @FXML
    private void okBtnAction(){
        Stage stage = (Stage) okBtn.getScene().getWindow(); // Get the stage from button

        if ( isFieldsValid() == true ) {
            updatePart(); // Save part
            stage.close(); // Close stage
        } else {
            // Do nothing
        }
    }

    /** Cancel button selected; close the window.
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
