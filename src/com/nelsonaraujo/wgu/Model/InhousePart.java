package com.nelsonaraujo.wgu.Model;

/** In-house part handlers.
 * Handlers for in-house parts.
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

public class InhousePart extends Part{
    int machineId;

    /** InhousePart Constructor.
     * Constructor for the in-house parts.
     * @param id Part ID.
     * @param name Part Name.
     * @param price Part's price.
     * @param stock Parts in stock.
     * @param min Minimum parts allowed in stock.
     * @param max Maximum parts allowed in stock.
     * @param machineId Machine ID that creates the part.
     */
    public InhousePart(int id, String name, double price, int stock, int min, int max, int machineId) {
        setPartId(id);
        setPartName(name);
        setPartPrice(price);
        setPartStock(stock);
        setPartMin(min);
        setPartMax(max);
        this.machineId = machineId;
    }

    // -------------------- Getters --------------------
    /** Return the part's machine ID.
     * Return the machine's ID that creates the part.
     * @return Machine ID.
     */
    public int getMachineId() {
        return machineId;
    }


    // -------------------- Setters --------------------
    /** Set the part's machine ID.
     * Set the machine's ID that creates the part.
     * @param machineId Machine ID.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}