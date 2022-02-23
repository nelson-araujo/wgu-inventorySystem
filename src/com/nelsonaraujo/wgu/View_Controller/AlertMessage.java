package com.nelsonaraujo.wgu.View_Controller;

/** Notification and error handling.
 * Notification dialogs, error dialogs, and other visual notifiers.
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.util.Optional;

public class AlertMessage {

    // -------------------- General errors --------------------
    /** Highlights text field red.
     * Use to highlight a field when there is an error detected
     * @param field The text field that the error is on
     */
    public static void fieldError(TextField field){
        if(field != null){
            field.setStyle("-fx-border-color: #d99999");
        }
    }

    /** Dialog to confirm item deletion.
     * Present user with a dialog to confirm the deletion of the item.
     * @param name Name of the item to be deleted
     * @return True or False if OK selected
     */
    public static boolean confirmItemDelete(String name){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Item");
        alert.setHeaderText("Are you sure you want to delete: " + name);
        alert.setContentText("Click OK to confirm");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /** Selection error dialogs.
     * Display selection error dialog based on error code passed.
     * @param errorCode 1:Inventory is empty | 2: Nothing is selected
     */
    public static void selectionError(int errorCode){
        if(errorCode == 1){ // Inventory is empty error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Empty Inventory");
            alert.setHeaderText("Empty inventory");
            alert.setContentText("Inventory is empty, there is nothing to select!");
            alert.showAndWait();
        }
        if(errorCode == 2){ // Nothing selected error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error: Invalid Selection");
            alert.setHeaderText("Invalid selection");
            alert.setContentText("You must select an item!");
            alert.showAndWait();
        }
    }


    // -------------------- Part errors --------------------
    /** Display error dialog.
     * Display an error dialog based on the error code passed.
     * @param errorCode Type of error. 1: Field empty | 2: Part type not selected | 3: Invalid format
     *                  | 4: Invalid name | 5: Negative value | 6: Inventory less than minimum
     *                  | 7: Inventory more than maximum | 8: Minimum higher than maximum | 9: Machine ID not number
     *                  | 10: Unknown part type
     */
    public static void errorPart(int errorCode){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error adding part");
        alert.setHeaderText("Cannot add part");

        // Error code types and message
        if(errorCode == 1){
            alert.setContentText("Field(s) is empty!");
        } else if(errorCode == 2){
            alert.setContentText("In House or OutSourced not selected!");
        } else if(errorCode == 3){
            alert.setContentText("Invalid format!");
        } else if(errorCode == 4){
            alert.setContentText("Name is invalid!");
        } else if(errorCode == 5){
            alert.setContentText("Value cannot be zero or less");
        } else if(errorCode == 6){
            alert.setContentText("Inventory cannot be lower than minimum!");
        } else if(errorCode == 7){
            alert.setContentText("Inventory cannot be greater than maximum!");
        } else if(errorCode == 8){
            alert.setContentText("Minimum cannot be higher than maximum!");
        } else if(errorCode == 9){
            alert.setContentText("Machine ID must be a number");
        } else if(errorCode == 10){
            alert.setContentText("Unknown part type");
        } else {
            alert.setContentText("Unknown error!");
        }

        alert.showAndWait();
    }

    /** Part associated with a product error.
     * Display an error dialog stating that the part is associated with a product, asks user to un-associate part first.
     * @param productName Product that the part is associated with.
     */
    public static void partAssociatedDeleteError(String productName){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error: Part Associated");
        alert.setHeaderText("Part is associated with " + productName);
        alert.setContentText("Un-associate part before deleting.");
        alert.showAndWait();
    }

    /** Part already associated with product error.
     * Display an error dialog stating that the part is already associated with the product.
     * @param productName Product that the part is associated with.
     */
    public static void partAlreadyAssociatedWithProductError(String productName){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error: Part Associated");
        alert.setHeaderText("Part already associated");
        alert.setContentText("Part is already associated with this product");
        alert.showAndWait();
    }


    // -------------------- Product errors --------------------
    /** Display error dialog.
     * Display an error dialog based on the error code.
     * @param errorCode Type of error. 1: Field empty | 2: No parts associated with product | 3: Invalid format
     *                  | 4: Value cannot be negative | 5: Inventory cannot be lower than minimum | 6: Inventory cannot be greater than maximum
     *                  | 7: Minimum cannot be higher than maximum | 8: Cost of the product cannot be lower than the sum of the parts
     */
    public static void errorAddProduct(int errorCode){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error adding product");
        alert.setHeaderText("Cannot add product");

        // Error code types and message
        if(errorCode == 1){
            alert.setContentText("Field(s) is empty!");
        } else if(errorCode == 2){
            alert.setContentText("No parts associated with product");
        } else if(errorCode == 3){
            alert.setContentText("Invalid format!");
        }  else if(errorCode == 4){
            alert.setContentText("Value cannot be negative");
        } else if(errorCode == 5){
            alert.setContentText("Inventory cannot be lower than minimum!");
        } else if(errorCode == 6){
            alert.setContentText("Inventory cannot be greater than maximum!");
        } else if(errorCode == 7){
            alert.setContentText("Minimum cannot be higher than maximum!");
        } else if(errorCode == 8){
            alert.setContentText("Cost of the product cannot be lower than the sum of the parts!");
        } else {
            alert.setContentText("Unknown error!");
        }

        alert.showAndWait();
    }

    /** Product has associated parts error.
     * Display an error dialog stating that the product has associated parts, asks user to un-associate part first.
     */
    public static void productHasAssociatedPartsDeleteError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error: Product has associated part(s)");
        alert.setHeaderText("Product has associated part(s)");
        alert.setContentText("Un-associate part(s) before deleting.");
        alert.showAndWait();
    }

}
