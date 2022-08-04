// Homework 1: Sales Register Program
// Course: CIS357
// Due date: 8/3/2022
// Name: Robert Cvetanovski
// GitHub: https://github.com/CzarOfAmerica/HW5-Cvet
// Instructor: Il-Hyung Cho
// Program description: This project creates a cash register program

import java.text.DecimalFormat;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * GUI
 */

public class cvetanovski extends Application
{

    /**
     * Register
     */
    private static Register register;

    /**
     * Observation
     */
    private static ObservableList<ProductSpecification> productSpecificationSaleObservableList,
            productSpecificationDeleteObservableList, productSpecificationModifyObservableList;

    /**
     * ComboBox
     */
    private static ComboBox<ProductSpecification> productSpecificationSaleComboBox,
            productSpecificationDeleteComboBox, productSpecificationModifyComboBox;

    /**
     * Scenes
     */
    private Scene addProductScene,deleteProductScene,modifyProductScene,dataAltScene,saleScene,mainScene;

    /**
     * name key
     */
    private static String FILE_NAME_KEY   = "item.txt";

    /**
     * code input
     */
    private static final String CODE_INPUT_REGEX_INPUT = "[AB]\\d\\d\\d";

    /**
     * update field
     */
    private static String RECEIPT_TEXT = "";

    /**
     *  fonts
     */
    private final Font titleFont = new Font("Lucida Sans Unicode",30),
            bodyFont = new Font("Lucida Sans Unicode",15),
            buttonFont = new Font("Lucida Sans Unicode",15),
            receiptFont = new Font("Lucida Sans Unicode",10);

    /**
     * Format variable
     */
    private final int sceneSpace = 20;

    /**
     * Format currency
     */
    private static final DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");

    @Override
    public void start(Stage primaryStage)
    {
        // references
        String  TYPE_HERE_DEFAULT       = "Type here...",
                SELECT_COMBO_DEFAULT    = "Select from above", MAIN_SCENE_TITLE        = "POST Register",
                EOD_TOTAL_TITLE         = "Total sale for the day is: $", ADD_BUTTON_TITLE        = "Add",
                DONE_BUTTON_TITLE       = "Done", NEW_SALE_BUTTON_TITLE   = "New Sale",
                SALE_BUTTON_TITLE       = "Sale", DATA_ALT_BUTTON_TITLE   = "Data Alteration",
                QUIT_BUTTON_TITLE       = "Quit", PRODUCT_CODE_TITLE      = "Product Code :",
                PRODUCT_NAME_TITLE      = "Product Name :", PRODUCT_PRICE_TITLE     = "Product Price :",
                PRODUCT_QUANTITY_TITLE = "Quantity :", PRODUCT_TOTAL_TITLE    = "Product total :",
                SUBTOTAL_TITLE          = "Sale Subtotal :", SUBTOTAL_TAX_TITLE      = "Sale Tax Subtotal (6%) :",
                TENDER_TITLE            = "Tendered Amount :", CHANGE_TITLE            = "Change :",
                TOTAL_FIELDS_DEFAULT = "0.00", CHECKOUT_BUTTON_TITLE   = "Checkout",
                DATA_ALT_TITLE          = "Data alteration", PRODUCT_SALE_TITLE         = "Item Sale",
                MAIN_TITLE              = "Welcome to Peter's Store!!!", ADD_PRODUCT_BUTTON_TITLE   = "Add Product",
                DELETE_PRODUCT_BUTTON_TITLE= "Delete Product", MODIFY_PRODUCT_BUTTON_TITLE   = "Modify Product",
                ADD_PRODUCT_TITLE    = "Add Product", DELETE_PRODUCT_TITLE = "Delete Product",
                MODIFY_PRODUCT_TITLE = "Modify Product";

        int RECEIPT_WIDTH = 260, RECEIPT_HEIGHT = 100;

        //  itemComBox and input
        initialize();

        // main screen scene nodes
        Label mainTitleLabel = new Label(MAIN_TITLE);
        mainTitleLabel.setFont(titleFont);
        Button newSaleButton = new Button(NEW_SALE_BUTTON_TITLE);
        Button quitButton = new Button(QUIT_BUTTON_TITLE);
        newSaleButton.setOnAction(e -> primaryStage.setScene(saleScene));
        quitButton.setOnAction(e ->
        {
            // Save
            saveCatalog();
            // Quit
            System.exit(0);
        });
        HBox mainButtonHB = new HBox(newSaleButton,quitButton);
        mainButtonHB.setSpacing(sceneSpace);
        mainButtonHB.setAlignment(Pos.CENTER);
        newSaleButton.setFont(buttonFont);
        quitButton.setFont(buttonFont);
        Label eodTotalLabel = new Label(EOD_TOTAL_TITLE + currencyFormat.format(register.getEOD()));
        eodTotalLabel.setFont(bodyFont);
        // Create node
        VBox mainVB = new VBox(mainTitleLabel,mainButtonHB,eodTotalLabel);
        mainVB.setSpacing(sceneSpace);
        mainVB.setAlignment(Pos.CENTER);
        // mainScene
        mainScene = new Scene(mainVB);

        // show stage
        primaryStage.setTitle(MAIN_SCENE_TITLE);
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(windowEvent ->
        {
            saveCatalog();

            // Quit
            System.exit(0);
        });
        primaryStage.show();

        // Create node
        Label productAltAddCodeTitle = new Label(PRODUCT_CODE_TITLE);
        TextField productAltAddCodeField = new TextField();
        productAltAddCodeField.setPromptText(TYPE_HERE_DEFAULT);
        HBox productAltAddCodeHB = new HBox(productAltAddCodeTitle,productAltAddCodeField);
        productAltAddCodeTitle.setFont(bodyFont);
        productAltAddCodeField.setFont(bodyFont);
        productAltAddCodeHB.setSpacing(sceneSpace);
        productAltAddCodeHB.setAlignment(Pos.CENTER);
        Label productAltAddNameTitle = new Label(PRODUCT_NAME_TITLE);
        TextField productAltAddNameField = new TextField();
        productAltAddNameField.setPromptText(TYPE_HERE_DEFAULT);
        HBox productAltAddNameHB = new HBox(productAltAddNameTitle,productAltAddNameField);
        productAltAddNameTitle.setFont(bodyFont);
        productAltAddNameField.setFont(bodyFont);
        productAltAddNameHB.setSpacing(sceneSpace);
        productAltAddNameHB.setAlignment(Pos.CENTER);
        Label productAltAddPriceTitle = new Label(PRODUCT_PRICE_TITLE);
        TextField productAltAddPriceField = new TextField();
        productAltAddPriceField.setPromptText(TYPE_HERE_DEFAULT);
        HBox productAltAddPriceHB = new HBox(productAltAddPriceTitle,productAltAddPriceField);
        productAltAddPriceTitle.setFont(bodyFont);
        productAltAddPriceField.setFont(bodyFont);
        productAltAddPriceHB.setSpacing(sceneSpace);
        productAltAddPriceHB.setAlignment(Pos.CENTER);
        // Create VBox
        VBox productAltAddTitleFieldVB = new VBox(productAltAddCodeHB,productAltAddNameHB,productAltAddPriceHB);
        productAltAddTitleFieldVB.setSpacing(sceneSpace);
        productAltAddTitleFieldVB.setAlignment(Pos.CENTER);
        // title
        Label addProductAltSceneTitle = new Label(ADD_PRODUCT_TITLE);
        addProductAltSceneTitle.setFont(titleFont);
        // Add/Done
        Button addProductButton = new Button(ADD_PRODUCT_BUTTON_TITLE);
        addProductButton.setOnAction(e -> {
            // add productSpecification
            if ( checkAddProduct(productAltAddCodeField.getText(),productAltAddNameField.getText(),productAltAddPriceField.getText()) )
            {
                productAltAddCodeField.setText("");
                productAltAddNameField.setText("");
                productAltAddPriceField.setText("");
            }
        });
        Button addDoneButton = new Button(DONE_BUTTON_TITLE);
        addDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox addButtonHB = new HBox(addProductButton,addDoneButton);
        addProductButton.setFont(buttonFont);
        addDoneButton.setFont(buttonFont);
        addButtonHB.setSpacing(sceneSpace);
        addButtonHB.setAlignment(Pos.CENTER);

        // add item node
        VBox addProductVB = new VBox(addProductAltSceneTitle,productAltAddTitleFieldVB,addButtonHB);
        addProductVB.setSpacing(sceneSpace);
        addProductVB.setAlignment(Pos.CENTER);
        addProductScene = new Scene(addProductVB);

        // delete item scene title
        Label deleteProductAltSceneTitle = new Label(DELETE_PRODUCT_TITLE);
        deleteProductAltSceneTitle.setFont(titleFont);

        // Create node
        Label productAltDeleteCodeTitle = new Label(PRODUCT_CODE_TITLE);
        TextField productAltDeleteCodeField = new TextField();
        productAltDeleteCodeField.setPromptText(SELECT_COMBO_DEFAULT);
        productAltDeleteCodeField.setEditable(false);
        HBox productAltDeleteCodeHB = new HBox(productAltDeleteCodeTitle,productAltDeleteCodeField);
        productAltDeleteCodeTitle.setFont(bodyFont);
        productAltDeleteCodeField.setFont(bodyFont);
        productAltDeleteCodeHB.setSpacing(sceneSpace);
        productAltDeleteCodeHB.setAlignment(Pos.CENTER);
        Label productAltDeleteNameTitle = new Label(PRODUCT_NAME_TITLE);
        TextField productAltDeleteNameField = new TextField();
        productAltDeleteNameField.setPromptText(SELECT_COMBO_DEFAULT);
        productAltDeleteNameField.setEditable(false);
        HBox productAltDeleteNameHB = new HBox(productAltDeleteNameTitle,productAltDeleteNameField);
        productAltDeleteNameTitle.setFont(bodyFont);
        productAltDeleteNameField.setFont(bodyFont);
        productAltDeleteNameHB.setSpacing(sceneSpace);
        productAltDeleteNameHB.setAlignment(Pos.CENTER);
        Label productAltDeletePriceTitle = new Label(PRODUCT_PRICE_TITLE);
        TextField productAltDeletePriceField = new TextField();
        productAltDeletePriceField.setPromptText(SELECT_COMBO_DEFAULT);
        productAltDeletePriceField.setEditable(false);
        HBox productAltDeletePriceHB = new HBox(productAltDeletePriceTitle,productAltDeletePriceField);
        productAltDeletePriceTitle.setFont(bodyFont);
        productAltDeletePriceField.setFont(bodyFont);
        productAltDeletePriceHB.setSpacing(sceneSpace);
        productAltDeletePriceHB.setAlignment(Pos.CENTER);

        // TF VBox
        VBox productAltDeleteTitleFieldVB = new VBox(productAltDeleteCodeHB,productAltDeleteNameHB,productAltDeletePriceHB);
        productAltDeleteTitleFieldVB.setSpacing(sceneSpace);
        productAltDeleteTitleFieldVB.setAlignment(Pos.CENTER);

        // Select code, Create update
        productSpecificationDeleteComboBox.setOnAction(e ->
        {
            try
            {
                // Set fields
                productAltDeleteCodeField.setText(productSpecificationDeleteComboBox.getValue().getProductCode());
                productAltDeleteNameField.setText(productSpecificationDeleteComboBox.getValue().getProductName());
                productAltDeletePriceField.setText(currencyFormat.format(productSpecificationDeleteComboBox.getValue().getProductPrice()));
            }
            catch (NullPointerException nullPointerException)
            {
                // Set fields
                productAltDeleteCodeField.setText("");
                productAltDeleteNameField.setText("");
                productAltDeletePriceField.setText("");
            }
        });
        productSpecificationDeleteComboBox.getEditor().setFont(bodyFont);

        // Delete/Done
        Button deleteProductButton = new Button(DELETE_PRODUCT_BUTTON_TITLE);
        deleteProductButton.setOnAction(e ->
        {
            // delete productSpecification
            if (checkDeleteProduct(productAltDeleteCodeField.getText()))
            {
                productSpecificationDeleteComboBox.setValue(null);
                productAltDeleteCodeField.setText("");
                productAltDeleteNameField.setText("");
                productAltDeletePriceField.setText("");
            }
        });
        Button deleteDoneButton = new Button(DONE_BUTTON_TITLE);
        deleteDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox deleteButtonHB = new HBox(deleteProductButton,deleteDoneButton);
        deleteProductButton.setFont(buttonFont);
        deleteDoneButton.setFont(buttonFont);
        deleteButtonHB.setSpacing(sceneSpace);
        deleteButtonHB.setAlignment(Pos.CENTER);

        // delete item node
        VBox deleteProductVB = new VBox(deleteProductAltSceneTitle,productSpecificationDeleteComboBox,productAltDeleteTitleFieldVB,deleteButtonHB);
        deleteProductVB.setSpacing(sceneSpace);
        deleteProductVB.setAlignment(Pos.CENTER);

        // Create scene
        deleteProductScene = new Scene(deleteProductVB);

        // Modify Scene
        Label modifyProductAltSceneTitle = new Label(MODIFY_PRODUCT_TITLE);
        modifyProductAltSceneTitle.setFont(titleFont);

        // dataAlt modify
        Label productAltModifyCodeTitle = new Label(PRODUCT_CODE_TITLE);
        TextField productAltModifyCodeField = new TextField();
        productAltModifyCodeField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox productAltModifyCodeHB = new HBox(productAltModifyCodeTitle,productAltModifyCodeField);
        productAltModifyCodeTitle.setFont(bodyFont);
        productAltModifyCodeField.setFont(bodyFont);
        productAltModifyCodeHB.setSpacing(sceneSpace);
        productAltModifyCodeHB.setAlignment(Pos.CENTER);
        Label productAltModifyNameTitle = new Label(PRODUCT_NAME_TITLE);
        TextField productAltModifyNameField = new TextField();
        productAltModifyNameField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox productAltModifyNameHB = new HBox(productAltModifyNameTitle,productAltModifyNameField);
        productAltModifyNameTitle.setFont(bodyFont);
        productAltModifyNameField.setFont(bodyFont);
        productAltModifyNameHB.setSpacing(sceneSpace);
        productAltModifyNameHB.setAlignment(Pos.CENTER);
        Label productAltModifyPriceTitle = new Label(PRODUCT_PRICE_TITLE);
        TextField productAltModifyPriceField = new TextField();
        productAltModifyPriceField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox productAltModifyPriceHB = new HBox(productAltModifyPriceTitle,productAltModifyPriceField);
        productAltModifyPriceTitle.setFont(bodyFont);
        productAltModifyPriceField.setFont(bodyFont);
        productAltModifyPriceHB.setSpacing(sceneSpace);
        productAltModifyPriceHB.setAlignment(Pos.CENTER);

        // add item
        VBox productAltModifyTitleFieldVB = new VBox(productAltModifyCodeHB,productAltModifyNameHB,productAltModifyPriceHB);
        productAltModifyTitleFieldVB.setSpacing(sceneSpace);
        productAltModifyTitleFieldVB.setAlignment(Pos.CENTER);

        // code from itemModifyComboBox
        // Create update
        productSpecificationModifyComboBox.setOnAction(e -> {
            try
            {
                // Set fields
                productAltModifyCodeField.setText(productSpecificationModifyComboBox.getValue().getProductCode());
                productAltModifyNameField.setText(productSpecificationModifyComboBox.getValue().getProductName());
                productAltModifyPriceField.setText(currencyFormat.format(productSpecificationModifyComboBox.getValue().getProductPrice()));
            }
            catch (NullPointerException nullPointerException)
            {
                productAltModifyCodeField.setText("");
                productAltModifyNameField.setText("");
                productAltModifyPriceField.setText("");
            }
        });
        productSpecificationModifyComboBox.getEditor().setFont(bodyFont);

        // Modify/Done
        Button modifyProductButton = new Button(MODIFY_PRODUCT_BUTTON_TITLE);
        modifyProductButton.setOnAction(e ->
        {
            if ( checkModifyProduct(productAltModifyCodeField.getText(),productAltModifyNameField.getText(),productAltModifyPriceField.getText()) )
            {
                productSpecificationModifyComboBox.setValue(null);
                productAltModifyCodeField.setText("");
                productAltModifyNameField.setText("");
                productAltModifyPriceField.setText("");
            }
        });
        Button modifyDoneButton = new Button(DONE_BUTTON_TITLE);
        modifyDoneButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox modifyButtonHB = new HBox(modifyProductButton,modifyDoneButton);
        modifyProductButton.setFont(buttonFont);
        modifyDoneButton.setFont(buttonFont);
        modifyButtonHB.setSpacing(sceneSpace);
        modifyButtonHB.setAlignment(Pos.CENTER);

        // modify item node
        VBox modifyProductVB = new VBox(modifyProductAltSceneTitle,productSpecificationModifyComboBox,productAltModifyTitleFieldVB,modifyButtonHB);
        modifyProductVB.setSpacing(sceneSpace);
        modifyProductVB.setAlignment(Pos.CENTER);

        // Create scene
        modifyProductScene = new Scene(modifyProductVB);

        // dataAlt
        Label dataAltLabel = new Label(DATA_ALT_TITLE);
        dataAltLabel.setFont(titleFont);

        // dataAlt button
        Button dataAltAddProductButton = new Button(ADD_PRODUCT_BUTTON_TITLE);
        dataAltAddProductButton.setOnAction(e ->
        {
            // Reset input
            productAltAddCodeField.setText("");
            productAltAddNameField.setText("");
            productAltAddPriceField.setText("");
            primaryStage.setScene(addProductScene);
        });
        Button dataAltDeleteProductButton = new Button(DELETE_PRODUCT_BUTTON_TITLE);
        dataAltDeleteProductButton.setOnAction(e ->
        {
            // Reset input
            productSpecificationDeleteComboBox.setValue(null);
            productAltDeleteCodeField.setText("");
            productAltDeleteNameField.setText("");
            productAltDeletePriceField.setText("");
            primaryStage.setScene(deleteProductScene);
        });
        Button dataAltModProductButton = new Button(MODIFY_PRODUCT_BUTTON_TITLE);
        dataAltModProductButton.setOnAction(e ->
        {
            // Reset input
            productSpecificationModifyComboBox.setValue(null);
            productAltModifyCodeField.setText("");
            productAltModifyNameField.setText("");
            productAltModifyPriceField.setText("");
            primaryStage.setScene(modifyProductScene);
        });
        HBox dataAltButtonHB = new HBox(dataAltAddProductButton,dataAltDeleteProductButton,dataAltModProductButton);
        dataAltAddProductButton.setFont(buttonFont);
        dataAltDeleteProductButton.setFont(buttonFont);
        dataAltModProductButton.setFont(buttonFont);
        dataAltButtonHB.setSpacing(sceneSpace);
        dataAltButtonHB.setAlignment(Pos.CENTER);

        // Done/Quit
        Button saleAltButton = new Button(SALE_BUTTON_TITLE);
        saleAltButton.setOnAction(e -> primaryStage.setScene(saleScene));
        Button quitAltButton = new Button(QUIT_BUTTON_TITLE);
        quitAltButton.setOnAction(e ->
        {
            saveCatalog();
            // Quit
            System.exit(0);
        });
        HBox dataAltDoneQuitButtonHB = new HBox(saleAltButton,quitAltButton);
        saleAltButton.setFont(buttonFont);
        quitAltButton.setFont(buttonFont);
        dataAltDoneQuitButtonHB.setSpacing(sceneSpace);
        dataAltDoneQuitButtonHB.setAlignment(Pos.CENTER);

        // dataAlt node
        VBox dataAltVB = new VBox(dataAltLabel,dataAltButtonHB,dataAltDoneQuitButtonHB);
        dataAltVB.setSpacing(sceneSpace);
        dataAltVB.setAlignment(Pos.CENTER);
        // scene
        dataAltScene = new Scene(dataAltVB);

        // sale
        Label productSaleTitle = new Label(PRODUCT_SALE_TITLE);
        productSaleTitle.setFont(titleFont);

        // middle checkout
        // receipt Label
        TextArea receiptTextArea = new TextArea(RECEIPT_TEXT);
        receiptTextArea.setPrefSize(RECEIPT_WIDTH,RECEIPT_HEIGHT);
        receiptTextArea.setFont(receiptFont);
        receiptTextArea.setEditable(false);

        // title/field
        Label subtotalTitle = new Label(SUBTOTAL_TITLE);
        TextField subtotalField = new TextField();
        subtotalField.setPromptText(TOTAL_FIELDS_DEFAULT);
        subtotalField.setEditable(false);
        HBox subtotalHB = new HBox(subtotalTitle,subtotalField);
        subtotalTitle.setFont(bodyFont);
        subtotalField.setFont(bodyFont);
        subtotalHB.setSpacing(sceneSpace);
        subtotalHB.setAlignment(Pos.CENTER);
        Label subtotalTaxTitle = new Label(SUBTOTAL_TAX_TITLE);
        TextField subtotalTaxField = new TextField();
        subtotalTaxField.setPromptText(TOTAL_FIELDS_DEFAULT);
        subtotalTaxField.setEditable(false);
        HBox subtotalTaxHB = new HBox(subtotalTaxTitle,subtotalTaxField);
        subtotalTaxTitle.setFont(bodyFont);
        subtotalTaxField.setFont(bodyFont);
        subtotalTaxHB.setSpacing(sceneSpace);
        subtotalTaxHB.setAlignment(Pos.CENTER);
        Label tenderTitle = new Label(TENDER_TITLE);
        TextField tenderTF = new TextField();
        tenderTF.setPromptText(TYPE_HERE_DEFAULT);
        HBox tenderHB = new HBox(tenderTitle,tenderTF);
        tenderTitle.setFont(bodyFont);
        tenderTF.setFont(bodyFont);
        tenderHB.setSpacing(sceneSpace);
        tenderHB.setAlignment(Pos.CENTER);
        Label changeTitle = new Label(CHANGE_TITLE);
        TextField changeField = new TextField();
        changeField.setPromptText(TOTAL_FIELDS_DEFAULT);
        changeField.setEditable(false);
        HBox changeHB = new HBox(changeTitle,changeField);
        changeTitle.setFont(bodyFont);
        changeField.setFont(bodyFont);
        changeHB.setSpacing(sceneSpace);
        changeHB.setAlignment(Pos.CENTER);

        // title/field
        Label productSaleCodeTitle = new Label(PRODUCT_NAME_TITLE);
        TextField productSaleCodeField = new TextField();
        productSaleCodeField.setPromptText(SELECT_COMBO_DEFAULT);
        productSaleCodeField.setEditable(false);
        HBox productSaleCodeHB = new HBox(productSaleCodeTitle,productSaleCodeField);
        productSaleCodeTitle.setFont(bodyFont);
        productSaleCodeField.setFont(bodyFont);
        productSaleCodeHB.setSpacing(sceneSpace);
        productSaleCodeHB.setAlignment(Pos.CENTER);
        Label productSaleNameTitle = new Label(PRODUCT_PRICE_TITLE);
        TextField productSaleNameField = new TextField();
        productSaleNameField.setPromptText(SELECT_COMBO_DEFAULT);
        productSaleNameField.setEditable(false);
        HBox productSaleNameHB = new HBox(productSaleNameTitle,productSaleNameField);
        productSaleNameTitle.setFont(bodyFont);
        productSaleNameField.setFont(bodyFont);
        productSaleNameHB.setSpacing(sceneSpace);
        productSaleNameHB.setAlignment(Pos.CENTER);
        Label productSalePriceTitle = new Label(PRODUCT_TOTAL_TITLE);
        TextField productSalePriceField = new TextField();
        productSalePriceField.setPromptText(SELECT_COMBO_DEFAULT);
        productSalePriceField.setEditable(false);
        HBox productSalePriceHB = new HBox(productSalePriceTitle,productSalePriceField);
        productSalePriceTitle.setFont(bodyFont);
        productSalePriceField.setFont(bodyFont);
        productSalePriceHB.setSpacing(sceneSpace);
        productSalePriceHB.setAlignment(Pos.CENTER);

        Label productSaleQuantityTitle = new Label(PRODUCT_QUANTITY_TITLE);
        TextField productSaleQuantityField = new TextField();
        productSaleQuantityField.setPromptText(SELECT_COMBO_DEFAULT);
        HBox productSaleQuantityHB = new HBox(productSaleQuantityTitle,productSaleQuantityField);
        productSaleQuantityField.setPromptText(TYPE_HERE_DEFAULT);
        productSaleQuantityTitle.setFont(bodyFont);
        productSaleQuantityField.setFont(bodyFont);
        productSaleQuantityHB.setSpacing(sceneSpace);
        productSaleQuantityHB.setAlignment(Pos.CENTER);

        // Create item
        VBox productSaleTitleFieldVB = new VBox(productSaleCodeHB,productSaleNameHB,productSalePriceHB,productSaleQuantityHB);
        productSaleTitleFieldVB.setSpacing(sceneSpace);
        productSaleTitleFieldVB.setAlignment(Pos.CENTER);

        // item from itemSaleComboBox
        // update event
        productSpecificationSaleComboBox.setOnAction(e ->
        {
            if (!(productSpecificationSaleComboBox.getValue() == null))
            {
                productSaleCodeField.setText(productSpecificationSaleComboBox.getValue().getProductCode());
                productSaleNameField.setText(productSpecificationSaleComboBox.getValue().getProductName());
                productSalePriceField.setText(currencyFormat.format(productSpecificationSaleComboBox.getValue().getProductPrice()));
            }
        });
        productSpecificationSaleComboBox.getEditor().setFont(bodyFont);

        // add button
        Button addButton = new Button(ADD_BUTTON_TITLE);
        addButton.setOnAction(event -> {
            try
            {
                if (checkSaleAmount() == 0)
                {
                    // Empty
                    // Reset change field
                    changeField.setText("");
                }

                if ( (Integer.parseInt(productSaleQuantityField.getText()) > 0) && !(productSaleCodeField.getText().equals("")))
                {
                    // Add product to sale and update receipt text
                    String newReceiptText = addSaleProduct(productSaleCodeField.getText(), productSaleQuantityField.getText());
                    RECEIPT_TEXT = RECEIPT_TEXT.concat(newReceiptText);

                    // Check if input valid
                    if (!newReceiptText.equals(""))
                    {
                        // Reset field values
                        productSpecificationSaleComboBox.setValue(null);
                        productSaleCodeField.setText("");
                        productSaleNameField.setText("");
                        productSalePriceField.setText("");
                        productSaleQuantityField.setText("");

                        // Set textArea to updated textArea
                        receiptTextArea.setText(RECEIPT_TEXT);

                        // Update subtotal fields
                        subtotalField.setText(currencyFormat.format(register.getSubtotal()));
                        subtotalTaxField.setText(currencyFormat.format(register.getSubtotalTax()));
                    }
                }
            }
            catch (Exception exception)
            {
                // Reset input field
                productSaleQuantityField.setText("");
            }
        });
        addButton.setFont(buttonFont);

        // sale node
        VBox saleTopVB = new VBox(productSpecificationSaleComboBox,productSaleTitleFieldVB,addButton);
        saleTopVB.setSpacing(sceneSpace);
        saleTopVB.setAlignment(Pos.CENTER);

        // checkout button
        Button checkoutButton = new Button(CHECKOUT_BUTTON_TITLE);
        checkoutButton.setOnAction(event -> {
            try
            {
                // Check if tenderField is not empty and the tenderTF is bigger or equal to subtotalTaxField
                if (!tenderTF.getText().equals("") &&
                        (Double.parseDouble(tenderTF.getText()) >= Double.parseDouble(subtotalTaxField.getText())) )
                {
                    // Try checkout
                    if (checkOut(tenderTF.getText()))
                    {
                        // Set up change value
                        Double changeAmount = Double.parseDouble(tenderTF.getText()) - Double.parseDouble(subtotalTaxField.getText());

                        // Update receipt field
                        RECEIPT_TEXT = RECEIPT_TEXT.concat(checkOutReceiptString(tenderTF.getText(),subtotalTaxField.getText()));
                        receiptTextArea.setText(RECEIPT_TEXT);

                        // Update change fields
                        changeField.setText(currencyFormat.format(changeAmount));

                        // Update register fields
                        register.resetSale();

                        // Update GUI total fields
                        subtotalField.setText("");
                        subtotalTaxField.setText("");
                        tenderTF.setText("");
                    }
                }
                else
                {
                    // Reset tender field
                    tenderTF.setText("");
                }
            }
            catch (NumberFormatException numberFormatException)
            {
                // Reset tender field
                tenderTF.setText("");
            }
        });
        checkoutButton.setFont(buttonFont);

        // subtotal
        VBox subtotalVB = new VBox(subtotalHB,subtotalTaxHB,tenderHB,checkoutButton,changeHB);
        subtotalVB.setSpacing(sceneSpace);
        subtotalVB.setAlignment(Pos.CENTER);

        // bottom button
        Button doneButton = new Button(DONE_BUTTON_TITLE);
        Button dataAltButton = new Button(DATA_ALT_BUTTON_TITLE);
        doneButton.setOnAction(event ->
        {
            // Update
            eodTotalLabel.setText(EOD_TOTAL_TITLE + currencyFormat.format(register.getEOD()));

            // Set mainScene
            primaryStage.setScene(mainScene);
        });
        dataAltButton.setOnAction(e -> primaryStage.setScene(dataAltScene));
        HBox buttonHB = new HBox(doneButton,dataAltButton);
        doneButton.setFont(buttonFont);
        dataAltButton.setFont(buttonFont);
        buttonHB.setSpacing(sceneSpace);
        buttonHB.setAlignment(Pos.CENTER);

        // sale border
        HBox saleBodyHB = new HBox(saleTopVB,receiptTextArea,subtotalVB);
        saleBodyHB.setSpacing(sceneSpace);
        saleBodyHB.setAlignment(Pos.CENTER);

        VBox saleVB = new VBox(productSaleTitle,saleBodyHB,buttonHB);
        saleVB.setSpacing(sceneSpace);
        saleVB.setAlignment(Pos.CENTER);

        // set SaleScene
        saleScene = new Scene(saleVB);
    }

    /**
     * Initialize register with key and format
     */
    private void initialize(){
        // register object
        register = new Register(currencyFormat,FILE_NAME_KEY);

        // ObserverList and ComboBoxes
        productSpecificationSaleObservableList = FXCollections.observableList(register.getProductSpecificationObservableList());
        productSpecificationDeleteObservableList = FXCollections.observableList(register.getProductSpecificationObservableList());
        productSpecificationModifyObservableList = FXCollections.observableList(register.getProductSpecificationObservableList());

        productSpecificationSaleComboBox = new ComboBox<>(productSpecificationSaleObservableList);
        productSpecificationDeleteComboBox = new ComboBox<>(productSpecificationDeleteObservableList);
        productSpecificationModifyComboBox = new ComboBox<>(productSpecificationModifyObservableList);
    }

    /**
     * Update observableList and ComboBoxes
     */
    private static void updateComboBoxes() {
        // observationList and comboBoxes
        productSpecificationSaleObservableList = FXCollections.observableList(register.getProductSpecificationObservableList());
        productSpecificationDeleteObservableList = FXCollections.observableList(register.getProductSpecificationObservableList());
        productSpecificationModifyObservableList = FXCollections.observableList(register.getProductSpecificationObservableList());

        productSpecificationSaleComboBox.setItems(productSpecificationSaleObservableList);
        productSpecificationDeleteComboBox.setItems(productSpecificationDeleteObservableList);
        productSpecificationModifyComboBox.setItems(productSpecificationModifyObservableList);
    }

    /**
     * Connects to register
     */
    private static void saveCatalog() {
        // Save
        register.saveCatalog();
    }

    /**
     * connects to register, adding productsSpecifications to productCatalog
     *
     * @param codeInput String, item code
     * @param nameInput String, item name
     * @param priceInput String, item price
     */
    private static boolean checkAddProduct(String codeInput, String nameInput, String priceInput) {
        // is valid
        try
        {
            if ((codeInput.matches(CODE_INPUT_REGEX_INPUT)) && (Double.parseDouble(priceInput) > 0) &&
                    (!register.checkIfCreate(codeInput)))
            {
                // valid
                // Add product
                register.addProductCatalogProduct(codeInput,nameInput,Double.parseDouble(priceInput));
                // Update
                updateComboBoxes();
                return true;
            }
            else
            {
                // invalid
                return false;
            }

        }
        catch (Exception e)
        {
            //  invalid
            return false;
        }
    }

    /**
     * connect to register, remove productsSpecification from productCatalog
     *
     * @param codeInput String, item code
     */
    private static boolean checkDeleteProduct(String codeInput)
    {
        // is valid
        try
        {
            if ((codeInput.matches(CODE_INPUT_REGEX_INPUT)) && (register.checkIfCreate(codeInput)))
            {
                // valid
                // Remove product
                register.deleteProductCatalogProduct(codeInput);
                // Update
                updateComboBoxes();
                return true;
            }
            else
            {
                // input invalid
                return false;
            }

        }
        catch (Exception e)
        {
            // invalid
            return false;
        }
    }

    /**
     * connect to register, modify productsSpecifications from productCatalog
     *
     * @param codeInput String, item code
     * @param nameInput String, item name
     * @param priceInput String, item price
     */
    private static boolean checkModifyProduct(String codeInput, String nameInput, String priceInput) {
        // is valid
        try
        {
            if ((codeInput.matches(CODE_INPUT_REGEX_INPUT)) && (Double.parseDouble(priceInput) > 0) &&
                    (register.checkIfCreate(codeInput)))
            {
                // valid
                // Modify
                register.modifyProductCatalogProduct(codeInput,nameInput, Double.valueOf(priceInput));
                // Update
                updateComboBoxes();
                return true;
            }
            else
            {
                // invalid
                return false;
            }

        }
        catch (Exception e)
        {
            // invalid
            return false;
        }
    }

    /**
     * connect to register, adds products to sale object.
     *
     * @param codeInput String, code
     * @param quantityInput String, quantity
     */
    private static String addSaleProduct(String codeInput, String quantityInput) {
        try {
            // Convert to Integer
            int quantityInputInt = Integer.parseInt(quantityInput);

            if ( (codeInput.matches(CODE_INPUT_REGEX_INPUT)) && (quantityInputInt > 0 && quantityInputInt <= 100) &&
                    (register.checkIfCreate(codeInput)) )
            {
                // Add to Register, set text
                return register.addProductToSale(codeInput,quantityInputInt);
            }
        }
        catch (Exception e)
        {
            // invalid
            return "";
        }
        return "";
    }

    /**
     * check and returns the size of the sale product size
     *
     * @return int, list size
     */
    private static int checkSaleAmount() {
        return register.checkSaleAmount();
    }

    // Returns boolean

    /**
     * check if checkout is possible based on tender amount
     *
     * @param tenderInput String, tender input
     * @return boolean, true = valid / false = invalid
     */
    private static boolean checkOut(String tenderInput) {
        try {
            // Convert to Double and checkout
            return register.checkOut(Double.parseDouble(tenderInput));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Connects to register to pass the change
     *
     * @param tenderInput String, tender input
     * @param subtotalTaxInput String, subtotal input
     * @return String, checkoutReceiptString
     */
    private static String checkOutReceiptString(String tenderInput, String subtotalTaxInput) {
        // String of receipt
        return register.checkOutReceiptString(Double.parseDouble(tenderInput) - Double.parseDouble(subtotalTaxInput));
    }

    /**
     *  GUI
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        launch();
    }
}