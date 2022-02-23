package com.nelsonaraujo.wgu;

/** Inventory system.
 * Inventory system launch point.
 *
 * ***********************************
 * WGU's Rubric Section G Questions
 * ***********************************
 * Describe a logical or runtime error that you corrected in the code and a detailed description of how you corrected
 * it above the line of code you are referring to.
 *      1 - At the start of this project, while creating the empty class and file structures, I was receiving
 *      “Error: JavaFX runtime components are missing, and are required to run this application” I spent several days
 *      reviewing and modifying the run configuration with little progress. While doing research related to this error
 *      I found some documentation on using a “module-info.java”. It described how to tell the run time what
 *      dependencies and classes it required. I created the file and noted that the program required javafx.fxml and
 *      javafx.controls along with the different custom classes created. This resolved the issue and the program ran
 *      with no errors. Reference: Class “module-info.java”
 *
 *      2 - After implementing the code to open the add part Stage I was receiving a compiler error when I
 *      clicked the Add Part button. As the compiler error was being generated when the button was clicked, I had
 *      just written the code to open the stage, and the compiler was pointing to the loader
 *      (MainController.java line 194) I started looking at the code I had just written to load the stage.
 *      After several days of troubleshooting and researching the possible cause I decided to step back and try to
 *      isolate the issue to a specific line. The first line I tested was the getResource
 *      (MainController.java line 189), I pointed it to another fxml file and to my surprise the stage opened with
 *      no errors. Now having isolated the problem I started looking at the FXML itself and found a missing # in the
 *      onAction call. (PartAddView.fxml line 85)
 *
 *
 * Describe a compatible feature suitable to your application that would extend functionality to the
 * next version if you were to update the application?
 *      1 - Add the ability to double click on a part to associate/un-associate with the product and vice versa.
 *
 *      2 - Create a logger class that will create a text file that will log changes to parts and products and
 *      provide a place for errors.
 *
 *      3 - Connect the program to a database to allow longer term changes.
 *
 *      4 - Implement a user/password function to protect the system from unauthorized personnel and log who made
 *      changes to the system.
 *
 *
 * @author Nelson Araujo
 * @version 1.0
 * @since 2020 December 20
 */

import com.nelsonaraujo.wgu.Model.*;
import com.nelsonaraujo.wgu.View_Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class InventorySystem extends Application {

    /** Start primary stage.
     * Open the main stage for the program.
     * @param primaryStage Main stage for program.
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Inventory primaryInv = new Inventory(); // Create a new inventory
        addTestData(primaryInv); // Add sample data

        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("View_Controller/MainView.fxml"));

        MainController controller = new MainController(primaryInv);
        rootLoader.setController(controller);

        Parent root = rootLoader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.getIcons().add(new Image("com/nelsonaraujo/wgu/Images/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /** Add sample parts and products.
     *
     * @param primaryInv Main inventory system database
     */
    void addTestData(Inventory primaryInv){
        System.out.println("----- Creating test data -----");

        // Add in house parts
        System.out.println("Creating in house parts");
        Part a1 = new InhousePart(1, "Part A1", 2.99, 10, 5, 100, 101);
        Part a2 = new InhousePart(3, "Part A2", 4.99, 11, 5, 100, 103);
        Part b = new InhousePart(2, "Part B", 3.99, 9, 5, 100, 102);

        primaryInv.addPart(a1);
        primaryInv.addPart(b);
        primaryInv.addPart(a2);

        primaryInv.addPart(new InhousePart(4,"Part A3", 5.99, 15, 5,100, 104));
        primaryInv.addPart((new InhousePart(5, "Part A4", 6.99, 5, 5, 100, 105)));

        // Add outsourced parts
        System.out.println("Creating outsourced parts");
        Part o1 = new OutsourcedPart(6, "Part 01", 2.99, 10, 5, 100, "ACME Co.");
        Part p = new OutsourcedPart(7, "Part P", 3.99, 9, 5, 100, "ACME Co.");
        Part q = new OutsourcedPart(8, "Part Q", 2.99, 10, 5, 100, "Florida Co.");

        primaryInv.addPart(o1);
        primaryInv.addPart(p);
        primaryInv.addPart(q);

        primaryInv.addPart(new OutsourcedPart(9, "Part R", 2.99, 10, 5, 100, "Florida Co."));
        primaryInv.addPart(new OutsourcedPart(10, "Part O2", 2.99, 10, 5, 100, "NY Co."));

        // Add products
        System.out.println("Creating Products");
        Product prod1 = new Product(100, "Product 1", 9.99, 20, 5, 100);
//        prod1.getProductName();
        prod1.addAssociatedPart(a1);
        prod1.addAssociatedPart(o1);
        primaryInv.addProduct(prod1);

        Product prod2 = new Product(200, "Product 2", 9.99, 29, 5, 100);
        prod2.addAssociatedPart(a2);
        prod2.addAssociatedPart(p);
        primaryInv.addProduct(prod2);

        Product prod3 = new Product(300, "Product 3", 9.99, 30, 5, 100);
        prod3.addAssociatedPart(a2);
        prod3.addAssociatedPart(q);
        primaryInv.addProduct(prod3);

        primaryInv.addProduct(new Product(400, "Product 4", 29.99, 20,5,100));
        primaryInv.addProduct(new Product(500, "Product 5", 29.99, 9, 5, 100));

        System.out.println("------------------------------");

    }

    /** Launch point for program.
     * Launch point for the InventorySystem.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
