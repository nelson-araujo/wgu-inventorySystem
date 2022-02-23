package com.nelsonaraujo.wgu.Model;

/** Parts handler
* Handlers for parts, supplied by WGU.
 * @author WGU
 */

public abstract class Part {
    private int partId;
    private String partName;
    private double partPrice;
    private int PartStock;
    private int partMin;
    private int partMax;

    // -------------------- Getters --------------------
    /** Return the part ID.
     *
     * @return the id
     */
    public int getPartId() {
        return partId;
    }

    /** Return the part name.
     * Returns the name of the part
     * @return part name
     */
    public String getPartName() {
        return partName;
    }

    /** Return the part's price.
     * Return the price of the part.
     * @return The part's price
     */
    public double getPartPrice() {
        return partPrice;
    }

    /** Return part's stock.
     * Return the amount of parts in stock.
     * @return The amount in stock.
     */
    public int getPartStock() {
        return PartStock;
    }

    /** Return the minimum allowed in stock.
     * Return the minimum amount of the part that must be in stock.
     * @return The minimum amount allowed in stock.
     */
    public int getPartMin() {
        return partMin;
    }

    /** Return the maximum allowed in stock.
     * Return the maximum amount of the part that must be in stock.
     * @return The maximum amount allowed in stock.
     */
    public int getPartMax() {
        return partMax;
    }


    // -------------------- Setters --------------------
    /** Set the part ID.
     *
     * @param partId the id to set
     */
    public void setPartId(int partId) {
        this.partId = partId;
    }

    /** Set part name.
     * Set the name of the part.
     * @param partName Name of the part to be used.
     */
    public void setPartName(String partName) {
        this.partName = partName;
    }

    /** Set the part's price.
     * Set the price of the part.
     * @param partPrice Price of the part to be used.
     */
    public void setPartPrice(double partPrice) {
        this.partPrice = partPrice;
    }

    /** Set the amount in stock.
     * Set the amount of the part that is in stock.
     * @param partStock The amount in stock.
     */
    public void setPartStock(int partStock) {
        this.PartStock = partStock;
    }

    /** Set the minimum allowed in stock.
     * Set the minimum amount of the part that must be in stock.
     * @param partMin The minimum amount to be used.
     */
    public void setPartMin(int partMin) {
        this.partMin = partMin;
    }

    /** Set the maximum allowed in stock.
     * Set the maximum amount of the part that must be in stock.
     * @param partMax The maximum amount allowed in stock.
     */
    public void setPartMax(int partMax) {
        this.partMax = partMax;
    }
    
}