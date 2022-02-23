package com.nelsonaraujo.wgu.View_Controller;

/** Main stage controller.
 * Controls for MainView.fxml
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

import com.nelsonaraujo.wgu.Model.Inventory;
import com.nelsonaraujo.wgu.Model.Part;
import com.nelsonaraujo.wgu.Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    Inventory primaryInv;

    @FXML private GridPane MainView; // Main view layout

    @FXML private TextField partsSearchField; // Parts search box
    @FXML private TableView<Part> partsTable;

    @FXML private TextField productsSearchField; // Products search box
    @FXML TableView<Product> productsTable;

    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    private ObservableList<Product> productsInventorySearch = FXCollections.observableArrayList();


    /** MainController constructor.
     *
     * @param primaryInv The primary inventory list
     */
    public MainController(Inventory primaryInv){
        this.primaryInv = primaryInv;
    }

    /** Initialize the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        generatePartsTable();
        generateProductsTable();
    }

    /** Generate the parts table
     *
     */
    private void generatePartsTable(){
        partInventory.setAll(primaryInv.getAllParts());
        partsTable.setItems(partInventory);
    }

    /** Create the products table.
     * Create the products table from the primary inventory list.
     */
    private void generateProductsTable(){
        productInventory.setAll(primaryInv.getAllProducts());

//        TableColumn<Part, Double> costCol = formatPrice();
//        partsTable.getColumns().addAll(costCol);

        productsTable.setItems(productInventory);
       //        productsTable.refresh();
    }

    /** Exiting protocol.
     *
     * @param event
     */
    @FXML
    public void exitProgram(ActionEvent event) {
        Platform.exit(); // Exit program
    }

    /** Search parts list and display.
     * Using the string in the search field identify parts matching the part name and part ID. Update the table view after search.
     * @param event
     */
    @FXML
    private void partsSearchKeyPressed(KeyEvent event) {
        if(!partsSearchField.getText().trim().isEmpty() ){ // Search field is not empty
            partsInventorySearch.clear(); // Clear search list

            for (Part partsSubList : primaryInv.getAllParts()){
                // Search part name
                if(partsSubList.getPartName().toLowerCase().contains(partsSearchField.getText().trim().toLowerCase())){
                    partsInventorySearch.add(partsSubList);
                }

                // Search part ID
                try {
                    Integer.parseInt(partsSearchField.getText().trim()); // Test that the string can be converted to an int

                    if(partsSubList.getPartId() == Integer.parseInt(partsSearchField.getText().trim())){
                        partsInventorySearch.add(partsSubList);
                    }
                } catch (NumberFormatException nfe){
                    // Do nothing
                }

            }

            // Update table
            partsTable.setItems(partsInventorySearch);
            partsTable.refresh();

        } else { // Search field is empty

            // Update table
            partsTable.setItems(partInventory);
            partsTable.refresh();
        }
    }

    /** Search products list and display.
     * Using the string in the search field identify products matching the product name and product ID. Update the table view after search.
     * @param event
     */
    @FXML
    void productsSearchKeyPressed(KeyEvent event) {
        if(!productsSearchField.getText().trim().isEmpty() ){ // Search field is not empty
            productsInventorySearch.clear(); // Clear search list

            for (Product productsSubList : primaryInv.getAllProducts()){
                // Search product name
                if(productsSubList.getProductName().toLowerCase().contains(productsSearchField.getText().trim().toLowerCase())){
                    productsInventorySearch.add(productsSubList);

                }

                // Search product ID
                try {
                    Integer.parseInt(productsSearchField.getText().trim()); // Test that the string can be converted to an int

                    if(productsSubList.getProductId() == Integer.parseInt(productsSearchField.getText().trim())){
                        productsInventorySearch.add(productsSubList);
                    }
                } catch (NumberFormatException nfe){
                    // Do nothing
                }

            }

            // Update table
            productsTable.setItems(productsInventorySearch);
            productsTable.refresh();

        } else { // Search field is empty

            // Update table
            productsTable.setItems(productInventory);
            productsTable.refresh();
        }
    }

    /** Open the add parts window.
     * Opens the add parts window and passes the primary inventory.
     * @param event
     * @throws IOException
     */
    @FXML
    public void partsAddBtnOnAction(ActionEvent event) throws IOException{
        Stage addPartStage = new Stage();

        addPartStage.initModality(Modality.APPLICATION_MODAL); // Stage must be closed to interact with main program
        FXMLLoader partAddViewLoader = new FXMLLoader(getClass().getResource("/com/nelsonaraujo/wgu/View_Controller/PartAddView.fxml"));

        PartAddController controller = new PartAddController(primaryInv); // Create controller object and pass inventory
        partAddViewLoader.setController(controller); // Set the controller

        Parent root = partAddViewLoader.load();
        Scene scene = new Scene(root);
        addPartStage.setScene(scene);
        addPartStage.setTitle("Add Part");
        addPartStage.getIcons().add(new Image("com/nelsonaraujo/wgu/Images/icon.png"));
        addPartStage.setResizable(false);
        addPartStage.showAndWait();

        generatePartsTable(); // Refresh parts table
    }

    /** Open part modify dialog.
     * Opens the modify parts window and passes the primary inventory and the selected part.
     * @param event
     * @throws IOException
     */
    @FXML
    public void partsModifyBtnOnAction(ActionEvent event) throws IOException{
        Part partSelected = partsTable.getSelectionModel().getSelectedItem();

        // Check that a part is selected
        try{
            if(partInventory.isEmpty()) {
                AlertMessage.selectionError(1);
                return;
            }
            if(!partInventory.isEmpty() && partSelected==null){ // Check if a part is selected
                AlertMessage.selectionError(2);
                return;

            // Open stage
            } else {
                Stage modifyPartStage = new Stage();

                modifyPartStage.initModality(Modality.APPLICATION_MODAL); // Stage must be closed to interact with main program
                FXMLLoader partModifyViewLoader = new FXMLLoader(getClass().getResource("/com/nelsonaraujo/wgu/View_Controller/PartModifyView.fxml"));

                PartModifyController partModifyController = new PartModifyController(primaryInv, partSelected); // Create controller object and pass inventory
                partModifyViewLoader.setController(partModifyController); // Set the controller

                Parent root = partModifyViewLoader.load();
                Scene scene = new Scene(root);
                modifyPartStage.setScene(scene);
                modifyPartStage.setTitle("Modify Part");
                modifyPartStage.getIcons().add(new Image("com/nelsonaraujo/wgu/Images/icon.png"));
                modifyPartStage.setResizable(false);

                partModifyController.initializeFields(); // Initialize fields

                modifyPartStage.showAndWait(); // Display stage and wait

                generatePartsTable(); // Refresh parts table

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Delete a part.
     * Delete a part from the inventory list
     * @param event
     * @throws IOException
     */
    @FXML
    public void partsDeleteBtnOnAction(ActionEvent event) throws IOException{
        // Check if a part is selected
        try{
            Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
            boolean isPartAssociated = false;

            if(AlertMessage.confirmItemDelete(selectedPart.getPartName() ) ){ // User confirmed
                for( int i=0 ; i < productInventory.size() ; i++){ // Parse through all products
                    for( int k=0 ; k < productInventory.get(i).getAllAssociatedParts().size() ; k++ ){ // Parse through all associated parts
                        if(selectedPart.getPartName() == productInventory.get(i).getAllAssociatedParts().get(k).getPartName() ){
                            AlertMessage.partAssociatedDeleteError(productInventory.get(i).getProductName());
                            isPartAssociated = true;
                            break; // Exit associated parts loop
                        }
                    }

                    // Exit product loop if part is associated with a product
                    if(isPartAssociated){
                        break;
                    }
                }

                if(!isPartAssociated){
                    primaryInv.deletePart(selectedPart);
                    partInventory.setAll(primaryInv.getAllParts()); // Update partsInventory with change
                }

            } else { // User canceled request
                // Do nothing
            }
        } catch(Exception e){ // No part selected
            if(partInventory.isEmpty() ){
                AlertMessage.selectionError(1); // List empty error
            } else {
                AlertMessage.selectionError(2); // No part selected error
            }
        }
    }

    /** Open the add product window.
     * Opens the add product window and passes the primary inventory.
     * @param event
     * @throws IOException
     */
    @FXML
    public void productsAddBtnOnAction(ActionEvent event) throws IOException{
        Stage addProductStage = new Stage();

        addProductStage.initModality(Modality.APPLICATION_MODAL); // Stage must be closed to interact with main program
        FXMLLoader ProductAddViewLoader = new FXMLLoader(getClass().getResource("/com/nelsonaraujo/wgu/View_Controller/ProductAddView.fxml"));

        ProductAddController productAddController = new ProductAddController(primaryInv); // Create controller object and pass inventory
        ProductAddViewLoader.setController(productAddController); // Set the controller

        Parent root = ProductAddViewLoader.load();
        Scene scene = new Scene(root);
        addProductStage.setScene(scene);
        addProductStage.setTitle("Add Product");
        addProductStage.getIcons().add(new Image("com/nelsonaraujo/wgu/Images/icon.png"));
        addProductStage.setResizable(false);
        addProductStage.showAndWait();

        generateProductsTable(); // Refresh products table

    }

    /** Open product modify dialog.
     * Opens the modify product window and passes the primary inventory and the selected product.
     * @param event
     * @throws IOException
     */
    @FXML
    public void productsModifyBtnOnAction(ActionEvent event) throws IOException{
        Product productSelected = productsTable.getSelectionModel().getSelectedItem();

        // Check that a product is selected
        try{
            if(productInventory.isEmpty()) {
                AlertMessage.selectionError(1);
                return;
            }
            if(!productInventory.isEmpty() && productSelected==null){ // Check if a product is selected
                AlertMessage.selectionError(2);
                return;

                // Open stage
            } else {
                Stage modifyProductStage = new Stage();

                modifyProductStage.initModality(Modality.APPLICATION_MODAL); // Stage must be closed to interact with main program
                FXMLLoader productModifyViewLoader = new FXMLLoader(getClass().getResource("/com/nelsonaraujo/wgu/View_Controller/ProductModifyView.fxml"));

                ProductModifyController productModifyController = new ProductModifyController(primaryInv, productSelected); // Create controller object and pass inventory
                productModifyViewLoader.setController(productModifyController); // Set the controller

                Parent root = productModifyViewLoader.load();
                Scene scene = new Scene(root);
                modifyProductStage.setScene(scene);
                modifyProductStage.setTitle("Modify Product");
                modifyProductStage.getIcons().add(new Image("com/nelsonaraujo/wgu/Images/icon.png"));
                modifyProductStage.setResizable(false);

                modifyProductStage.showAndWait(); // Display stage and wait

                generateProductsTable(); // Refresh products table

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Delete a product
     * Delete a product from the inventory list
     * @param event
     * @throws IOException
     */
    @FXML
    public void productsDeleteBtnOnAction(ActionEvent event) throws IOException{
        // Check if a product is selected
        try{
            Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
            // Check if product has associated parts
            if(selectedProduct.getAllAssociatedParts().size() > 0 ){
                // Has associated parts error
                AlertMessage.productHasAssociatedPartsDeleteError();
            } else {
                // Delete product
                if (AlertMessage.confirmItemDelete(selectedProduct.getProductName())) { // User confirmed
                    primaryInv.deleteProduct(selectedProduct);
                    productInventory.setAll(primaryInv.getAllProducts()); // Update productInventory with change
                } else { // User canceled request
                    // Do nothing
                }
            }
        } catch(Exception e){
            if(productInventory.isEmpty() ){
                AlertMessage.selectionError(1); // Inventory is empty error
            } else {
                AlertMessage.selectionError(2); // Nothing selected error
            }
        }
    }

}
