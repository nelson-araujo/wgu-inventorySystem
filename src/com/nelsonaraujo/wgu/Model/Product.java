package com.nelsonaraujo.wgu.Model;

/** Products handlers
 * Handlers for products
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    ObservableList<Part> associatedParts = FXCollections.observableArrayList(); // Create associatedParts list
    int productId;
    String productName;
    double productPrice;
    int productStock;
    int productMin;
    int productMax;


    // -------------------- Constructors --------------------
    /** Product constructor.
     *
     * @param id Product ID.
     * @param name Name of the product.
     * @param price Product price.
     * @param stock Quantity in stock.
     * @param min Minimum stock required.
     * @param max Maximum stock required.
     */
    public Product(int id, String name, double price, int stock, int min, int max){
        this.productId = id;
        setProductName(name);
        setProductPrice(price);
        setProductStock(stock);
        setProductMin(min);
        setProductMax(max);
    }


    // -------------------- Destructors --------------------
    /** Delete an associated part.
     * Remove an associated part.
     * @param selectedAssociatedPart Part to be removed.
     * @return True if successful otherwise false.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        // Remove associated part
        try {
            associatedParts.remove(selectedAssociatedPart);

        } catch (Exception e ){ // Removal of part failed.
            return false;
        }

        return true;
    }

    /** Remove all associated parts.
     * Remove allt he associated parts from the product.
     */
    public void deleteAllAssociatedPart(){
        associatedParts.clear();
    }


    // -------------------- Getters --------------------
    /** Get the product ID
     *
     * @return The ID of the product
     */
    public int getProductId() {
        return productId;
    }

    /** Get the product name
     *
     * @return The product name
     */
    public String getProductName() {
        return productName;
    }

    /** Get the product price
     *
     * @return The product's price
     */
    public double getProductPrice() {
        return productPrice;
    }

    /** Get the product's available stock
     *
     * @return The product's available stock
     */
    public int getProductStock() {
        return productStock;
    }

    /** Get the minimum amount of the product that should be in stock
     *
     * @return The minimum of the product that should be in stock
     */
    public int getProductMin() {
        return productMin;
    }

    /** Get the maximum amount of the product that should be in stock
     *
     * @return The maximum of the product that should be in stock
     */
    public int getProductMax() {
        return productMax;
    }

    /** Return all associated parts.
     *  Returns all parts associated with a product.
     * @return Associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }


    // -------------------- Setters --------------------
    /** Set the product name
     *
     * @param productName The product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /** Set the product's price.
     * Set the price of the product.
     * @param productPrice Price of the product
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    /** Set the product's stock quantity.
     * Set the product's stock quantity.
     * @param productStock Stock quantity
     */
    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    /** Set the product's minimum stock quantity.
     * Set the product's minimum stock quantity required.
     * @param productMin Minimum stock quantity
     */
    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    /** Set the product's maximum stock quantity.
     * Set the product's maximum stock quantity allowed.
     * @param productMax Maximum stock quantity.
     */
    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }

    /** Associate a part to a product
     *
     * @param part Part to associate
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

}
