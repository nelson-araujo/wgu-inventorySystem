package com.nelsonaraujo.wgu.View_Controller;

/** Add products controller.
 * Controls for ProductAddView.fxml
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

import com.nelsonaraujo.wgu.Model.Inventory;
import com.nelsonaraujo.wgu.Model.Part;
import com.nelsonaraujo.wgu.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ProductAddController {
    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button addPartBtn;

    @FXML
    private Button removeAssociatedPartBtn;

    @FXML
    private TextField allPartsSearchField;

    @FXML
    private TextField productId;

    @FXML
    private TextField productName;
    
    @FXML
    private TextField productInventoryLevel;

    @FXML
    private TextField productPrice;

    @FXML
    private TextField productInventoryMaximum;

    @FXML
    private TextField productInventoryMinimum;

    @FXML
    private TextField machineIdCompNameField;

    @FXML
    private TableView<Part> allPartsTable;

    @FXML
    private TableView<Part> productPartsTable;


    Inventory primaryInv;
    private ObservableList<Part> allPartsList = FXCollections.observableArrayList();
    private ObservableList<Part> allPartsSearchList = FXCollections.observableArrayList();
    private ObservableList<Part> productPartsList = FXCollections.observableArrayList();


    /** ProductAddController constructor.
     * Assigns passed inventory to local inventory
     * @param primaryInv The primary inventory list
     */
    public ProductAddController(Inventory primaryInv) {
        this.primaryInv = primaryInv;
    }

    /** Initialize table.
     * Initialize the all parts table.
     */
    public void initialize(){
        generateInitialAllPartsTable();
        setUniqueProductID();

    }

    /** Populate the initial all parts table.
     * Retrieve all parts fromm inventory and populate the initial all parts table.
     */
    @FXML
    private void generateInitialAllPartsTable(){
        allPartsList.setAll(primaryInv.getAllParts());
        allPartsTable.setItems(allPartsList);
        allPartsTable.refresh();

    }

    /** Search parts list and display.
     * Using the string in the search field identify parts matching the part name and part ID. Update the table view after search.
     */
    @FXML
    private void allPartsSearchKeyPressed(){
        if(!allPartsSearchField.getText().trim().isEmpty() ){ // Search field is not empty
            allPartsSearchList.clear(); // Clear search list

            for (Part partsSubList : primaryInv.getAllParts()){
                // Search part name
                if(partsSubList.getPartName().toLowerCase().contains(allPartsSearchField.getText().trim().toLowerCase())){
                    allPartsSearchList.add(partsSubList);
                }

                // Search part ID
                try {
                    Integer.parseInt(allPartsSearchField.getText().trim()); // Test that the string can be converted to an int

                    if(partsSubList.getPartId() == Integer.parseInt(allPartsSearchField.getText().trim())){
                        allPartsSearchList.add(partsSubList);
                    }
                } catch (NumberFormatException nfe){
                    // Do nothing
                }

            }

            // Update table
            allPartsTable.setItems(allPartsSearchList);
            allPartsTable.refresh();

        } else { // Search field is empty

            // Update table
            allPartsTable.setItems(allPartsList);
            allPartsTable.refresh();
        }
    }

    /** Add part to the product parts list.
     * Validated the part is not already associated with this product, add part to product parts list, and remove it from all parts.
     */
    @FXML
    private void addPartBtnAction(){
        Part selectedPart = allPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            // Check if part is already assigned to product
            boolean isAssigned = isPartAssignedToProduct(selectedPart);

            if (!isAssigned) { // Part is not already associated
                productPartsList.add(selectedPart); // Add part to product parts list
                generateProductPartsTable();

                allPartsList.remove(selectedPart); // Remove parts from all parts list
                allPartsTable.setItems(allPartsList);
                allPartsTable.refresh();

            } else { // Part is already associated
                AlertMessage.partAlreadyAssociatedWithProductError(selectedPart.getPartName()); // Display error dialog
            }

        } else {
            if (allPartsList.isEmpty()) {
                AlertMessage.selectionError(1); // All parts list is empty
            } else {
                AlertMessage.selectionError(2); // No selection error
            }
        }
    }

    /** Check is part is already assigned.
     * Check if part is already assigned to product.
     * @param selectedPart Selected part
     * @return If part is already assigned
     */
    private boolean isPartAssignedToProduct(Part selectedPart){
        boolean isAssigned = false;

        for(int i=0 ; i<productPartsList.size() ; i++){
            if(productPartsList.get(i).getPartId() == selectedPart.getPartId()){
                isAssigned = true;
            }
        }

        return isAssigned;
    }

    /** Remove an associated part.
     * Removes a part associated with the product and adds it back to the all parts list.
     */
    @FXML
    private void removeAssociatedPartBtnAction(){
        Part selectedPart = productPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            // Remove part to product parts list
            productPartsList.remove(selectedPart);

            // Add part to all parts list
            allPartsList.add(selectedPart);
            allPartsTable.setItems(allPartsList);
            allPartsTable.refresh();

        } else {
            if (productPartsList.isEmpty()) {
                AlertMessage.selectionError(1); // All parts list is empty
            } else {
                AlertMessage.selectionError(2); // No selection error
            }
        }

    }

    /** Generate the product parts table.
     * Display the parts associated with the product.
     */
    @FXML
    private void generateProductPartsTable(){
        productPartsTable.setItems(productPartsList);
        productPartsTable.refresh();

    }

    /** When the Save button is selected, validate and save.
     * Validate all fields and save if validation is successful.
     */
    @FXML
    private void saveBtnAction(){
        Stage stage = (Stage) saveBtn.getScene().getWindow(); // Get the stage the button is attached to

        if( isFieldsValid() == true){
            saveProduct(); // Save product
            stage.close(); // Close the stage
        } else {
            // Do nothing
        }
    }

    /** Close the stage.
     * Closes the stage without processing any data.
     */
    @FXML
    private void cancelBtnAction(){
        Stage stage = (Stage) cancelBtn.getScene().getWindow(); // Get the stage the button is attached to
        stage.close(); // Do nothing and close the stage
    }

    /** Validate fields per requirements.
     *
     * @return True if all fields are valid, false if not.
     */
    @FXML
    private boolean isFieldsValid(){
        boolean isValid = false;

        // Reset all field borders to default
        fieldStyleReset();

        // Total cost of associated parts
        double totalPartsCost = 0;
        for(int i=0 ; i < productPartsList.size() ; i++){
            totalPartsCost += productPartsList.get(i).getPartPrice();
        }


        try { // Check for invalid formats
           if (productId.getText().trim().isEmpty() || // Check for empty fields and highlight
                   productName.getText().trim().isEmpty() ||
                   productInventoryLevel.getText().trim().isEmpty() ||
                   productPrice.getText().trim().isEmpty() ||
                   productInventoryMaximum.getText().trim().isEmpty() ||
                   productInventoryMinimum.getText().trim().isEmpty()) {

               if (productName.getText().trim().isEmpty()) {
                   AlertMessage.fieldError(productName);
               }

               if (productInventoryLevel.getText().trim().isEmpty()) {
                   AlertMessage.fieldError(productInventoryLevel);
               }

               if (productPrice.getText().trim().isEmpty()) {
                   AlertMessage.fieldError(productPrice);
               }

               if (productInventoryMaximum.getText().trim().isEmpty()) {
                   AlertMessage.fieldError(productInventoryMaximum);
               }

               if (productInventoryMinimum.getText().trim().isEmpty()) {
                   AlertMessage.fieldError(productInventoryMinimum);
               }

               AlertMessage.errorAddProduct(1); // Notify user of empty fields

               // Inventory minimum is higher than maximum
           } else if (Integer.parseInt(productInventoryMaximum.getText().trim()) < Integer.parseInt(productInventoryMinimum.getText().trim())) {
               AlertMessage.fieldError(productInventoryMinimum); // Highlight field
               AlertMessage.fieldError(productInventoryMaximum); // Highlight field
               AlertMessage.errorAddProduct(6); // Display error dialog

               // Price not negative
           } else if (Double.parseDouble(productPrice.getText().trim()) < 0) {
               AlertMessage.fieldError(productPrice); // Highlight field
               AlertMessage.errorAddProduct(4); // Display error dialog

           } else if (Double.parseDouble(productPrice.getText().trim()) < totalPartsCost) {
               AlertMessage.fieldError(productPrice); // Highlight field
               AlertMessage.errorAddProduct(8); // Display error dialog

               // Inventory level is lower than minimum
           } else if (Integer.parseInt(productInventoryLevel.getText().trim()) < Integer.parseInt(productInventoryMinimum.getText().trim())) {
               AlertMessage.fieldError(productInventoryLevel); // Highlight field
               AlertMessage.errorAddProduct(5); // Display error dialog

               // Inventory level is higher than maximum
           } else if (Integer.parseInt(productInventoryLevel.getText().trim()) > Integer.parseInt(productInventoryMaximum.getText().trim())) {
               AlertMessage.fieldError(productInventoryLevel); // Highlight field
               AlertMessage.errorAddProduct(6); // Display error dialog

               // Part associated with product
           } else if (productPartsList.isEmpty()) {
               AlertMessage.errorAddProduct(2);

           } else { // All validations passed
               isValid = true;
           }

       } catch(NumberFormatException e){ // Invalid format
           AlertMessage.errorAddProduct(3);
       }

        return isValid;

    }

    /** Create new product.
     * Create a new part based on the user input.
     */
    @FXML
    private void saveProduct(){
        // Create the new product
        Product newProduct = new Product(
                Integer.parseInt(productId.getText().trim()),
                productName.getText().trim(),
                Double.parseDouble(productPrice.getText().trim()),
                Integer.parseInt(productInventoryLevel.getText().trim()),
                Integer.parseInt(productInventoryMinimum.getText().trim()),
                Integer.parseInt(productInventoryMaximum.getText().trim())
        );

        // Save the new product to the inventory
        primaryInv.addProduct(newProduct);

    }

    /** Populate unique product ID.
     * Get a unique product ID and set the text field.
     */
    @FXML
    private void setUniqueProductID(){
        productId.setText(String.valueOf(primaryInv.getUniqueProductID()));
    }

    /** Clear text error border.
     * Set the field border back to null.
     */
    @FXML
    private void fieldStyleReset(){
        productName.setStyle(null);
        productInventoryLevel.setStyle(null);
        productPrice.setStyle(null);
        productInventoryMaximum.setStyle(null);
        productInventoryMinimum.setStyle(null);
    }

}
