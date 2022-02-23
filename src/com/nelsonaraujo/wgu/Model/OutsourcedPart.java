package com.nelsonaraujo.wgu.Model;

/** Outsourced part handlers.
 * Handlers for outsourced parts.
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

public class OutsourcedPart extends Part{
    String companyName;

    /** OutsourcedPart constructor.
     * Constructor for the outsourced parts
     * @param id Part ID.
     * @param name Part name.
     * @param price Part's price.
     * @param stock Parts in stock.
     * @param min Minimum parts allowed in stock.
     * @param max Maximum parts allowed in stock.
     * @param companyName Company that manufactures part.
     */
    public OutsourcedPart(int id, String name, double price, int stock, int min, int max, String companyName) {
        setPartId(id);
        setPartName(name);
        setPartPrice(price);
        setPartStock(stock);
        setPartMin(min);
        setPartMax(max);
        this.companyName = companyName;
    }

    // -------------------- Getters --------------------
    /** Return the company name.
     * Return the company name that manufacturers the part.
     * @return Company name.
     */
    public String getCompanyName() {
        return companyName;
    }


    // -------------------- Setters --------------------

    /** Set the company name.
     * Set the company name that manufacturers the part.
     * @param companyName Company name that manufactures the part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
