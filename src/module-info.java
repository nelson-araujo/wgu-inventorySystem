/** Module requirements.
 * Configure the project to use the JavaFX module.
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

module C482.QKM1.InventorySystem.NelsonAraujo {
    requires javafx.fxml;
    requires javafx.controls;

    opens com.nelsonaraujo.wgu;
    opens com.nelsonaraujo.wgu.Images;
    opens com.nelsonaraujo.wgu.Model;
    opens com.nelsonaraujo.wgu.View_Controller;
}