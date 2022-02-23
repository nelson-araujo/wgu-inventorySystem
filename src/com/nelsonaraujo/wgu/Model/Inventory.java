package com.nelsonaraujo.wgu.Model;

/** Inventory handlers
 * Handlers for inventory objects.
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Inventory {
    private ObservableList<Part> allParts = FXCollections.observableArrayList(); // Create parts list
    private ObservableList<Product> allProducts = FXCollections.observableArrayList(); // Create products list


    // -------------------- Parts --------------------
    /** Add a new part.
     *
     * @param newPart New part to be added to the ObservableList
     */
    public void addPart(Part newPart){
        allParts.add(newPart);
    }

    /** Look up a part.
     * Search for a part in the parts list and return it. Return null if none was found.
     * @param partId The part ID to lookup.
     * @return The part if found, if not returns null.
     */
    public Part lookupPart(int partId){
        for(int i=0 ; i < allParts.size() ; i++){
            if(allParts.get(i).getPartId() == partId){
                return allParts.get(i);
            }
        }

        return null;
    }

    /** Update part.
     * Update the part based on the updatedPart passed
     * @param partID ID of the part to be updated.
     * @param updatedPart Temporary part to be referred for updating the inventory
     */
    public void updatePart(int partID, Part updatedPart){
        Part originalPart = lookupPart(partID);

        // Part type in-house, no change
        if(originalPart instanceof  InhousePart && updatedPart instanceof InhousePart){
            originalPart.setPartName(updatedPart.getPartName());
            originalPart.setPartPrice(updatedPart.getPartPrice());
            originalPart.setPartStock(updatedPart.getPartStock());
            originalPart.setPartMin(updatedPart.getPartMin());
            originalPart.setPartMax(updatedPart.getPartMax());
            ((InhousePart) originalPart).setMachineId(((InhousePart) updatedPart).getMachineId());

        // Part type outsourced, no change
        } else if(originalPart instanceof OutsourcedPart && updatedPart instanceof OutsourcedPart){
            originalPart.setPartName(updatedPart.getPartName());
            originalPart.setPartPrice(updatedPart.getPartPrice());
            originalPart.setPartStock(updatedPart.getPartStock());
            originalPart.setPartMin(updatedPart.getPartMin());
            originalPart.setPartMax(updatedPart.getPartMax());
            ((OutsourcedPart) originalPart).setCompanyName(((OutsourcedPart) updatedPart).getCompanyName());

        // Part type changed
        } else {
            // Delete part from inventory
            allParts.remove(allParts.indexOf(originalPart));

            // Create part
            allParts.add(updatedPart);

        }
    }

    /** Delete a part.
     * Delete a part fromm the inventory
     * @param selectedPart Part to be deleted
     * @return True: Part was successfully deleted | False: part was not able to be deleted
     */
    public boolean deletePart(Part selectedPart){
        try {
            allParts.remove(selectedPart);
            return true; // Part deletion successful
        } catch (Exception e){
            return false; // Part deletion failed
        }
    }

    /** Return all parts.
     *
     * @return Parts list
     */
    public ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** Identify a unique part ID.
     * Starting at MIN_ID search for unassigned IDs
     * @return Unique ID
     */
    public int getUniquePartID(){
        final int MIN_ID=1; // Lowest possible ID number
        final int MAX_ID=1000; // Highest possible ID number
        int uniqueID=MIN_ID;

        // Find unique number starting at MIN_ID
        for(int i=0 ; i<MAX_ID && i<getAllParts().size() ; i++){
            if( uniqueID == (getAllParts().get(i).getPartId()) ){
                uniqueID++;
            }
        }

        return uniqueID;
    }


    // -------------------- Products --------------------

    /** Add a new product
     *
     * @param newProduct New product to be added to the ObservableList
     */
    public void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** Look up product.
     * search for a product in the product list and return it. Return null if none was found.
     * @param productId The product ID
     * @return The product if found, otherwise return null.
     */
    public Product lookupProduct(int productId){
        for(int i=0 ; i < allProducts.size() ; i++){
            if(allProducts.get(i).getProductId() == productId){
                return allProducts.get(i);
            }
        }

        return null;
    }

    /** Update the product.
     * updated the product based on the provided part.
     * @param productId The product ID
     * @param updatedProduct The updated part
     */
    public void updateProduct(int productId, Product updatedProduct){
        // Look up the product in the inventory
        Product originalProduct = lookupProduct(productId);

        // Update product with new data
        originalProduct.setProductName(updatedProduct.getProductName());
        originalProduct.setProductStock(updatedProduct.getProductStock());
        originalProduct.setProductPrice(updatedProduct.getProductPrice());
        originalProduct.setProductMax(updatedProduct.getProductMax());
        originalProduct.setProductMin(updatedProduct.getProductMin());

        List<Part> updatedAssociatedParts = updatedProduct.getAllAssociatedParts();

        originalProduct.deleteAllAssociatedPart();

        // Add updated associated parts
        for(int i=0 ; i < updatedAssociatedParts.size() ; i++){
            originalProduct.addAssociatedPart(updatedAssociatedParts.get(i));
        }

    }

    /** Delete a product
     * Delete a product from the inventory
     * @param selectedProduct Product to be deleted
     * @return True: Product was successfully deleted | False: product was not able to be deleted
     */
    public boolean deleteProduct(Product selectedProduct){
        try {
            allProducts.remove(selectedProduct);
            return true; // Part deletion successful
        } catch (Exception e){
            return false; // Part deletion failed
        }
    }

    /** Return all products
     *
     * @return Products list
     */
    public ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /** Identify a unique product ID.
     * Starting at MIN_ID search for unassigned IDs
     * @return Unique ID
     */
    public int getUniqueProductID(){
        final int MIN_ID=100; // Lowest possible ID number
        final int MAX_ID=1000; // Highest possible ID number
        int uniqueID=MIN_ID;

        // Find unique number starting at MIN_ID
        for(int i=0 ; i<MAX_ID && i<getAllProducts().size() ; i++){
            if( uniqueID == (getAllProducts().get(i).getProductId()) ){
                uniqueID++;
            }
        }

        return uniqueID;
    }

}
