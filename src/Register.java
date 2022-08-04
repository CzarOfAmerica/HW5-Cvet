import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * ProductCatalog and Sale object from GUI driver.
 */

public class Register
{
    /**
     * store ProductSpecifications
     */
    ProductCatalog productCatalog;
    /**
     * store values of sold products
     */
    Sale sale;

    /**
     * list of productCatalog
     */
    ObservableList<ProductSpecification> productSpecificationObservableList;
    /**
     * Preset strings
     */
    private static final String
            BREAK_LINE                          = "--------------------",
            PRODUCT_CODE_RECEIPT_MESSAGE        = "Product Code : ",
            PRODUCT_NAME_RECEIPT_MESSAGE        = "Product Name : ",
            PRODUCT_QUANTITY_RECEIPT_MESSAGE    = "Product Quantity : ",
            PRODUCT_TOTAL_RECEIPT_MESSAGE       = "Product Total : $",
            RECEIPT_LINE                        = "----------------------------",
            CHANGE_AMOUNT                       = "Change";

    /**
     * used in Register
     */
    private static final String recipeStringFormat = "%n%22s%s%n%21s%s%n%21s%s%n%24s%7s%n";

    /**
     * currency pass to Register
     */
    private static DecimalFormat currencyFormat;

    /**
     * Initialize sale and productCatalog object
     *
     * @param moneyFormat DecimalFormat, currency format
     * @param fileName String, fileName
     */
    Register(DecimalFormat moneyFormat, String fileName)
    {
        //  Sale
        sale = new Sale();
        // Set currency
        currencyFormat = moneyFormat;
        // Initialize
        productCatalog = new ProductCatalog(fileName,currencyFormat);
        // Update
        updateList();
    }

    /**
     * Takes inputs from GUI driver
     *
     * @param codeInput String, code
     * @param quantityInput int, quantity
     * @return String, code, name, quantity, and total
     */
    public String addProductToSale (String codeInput, int quantityInput)
    {
        // return string
        String returnString = "";

        // already created
        if (sale.checkIfEmpty())
        {
            // line break
            returnString = "\n" + BREAK_LINE;
        }

        // Get product
        ProductSpecification addedSpecification = productCatalog.getProductSpecification(codeInput);
        // Find price
        BigDecimal productPriceTotal = (addedSpecification.getProductPrice().multiply(BigDecimal.valueOf(quantityInput)));
        // Add product
        sale.addSalesLineItem(addedSpecification,quantityInput,productPriceTotal);

        returnString = returnString.concat(String.format(recipeStringFormat,
                PRODUCT_CODE_RECEIPT_MESSAGE, addedSpecification.getProductCode(),
                PRODUCT_NAME_RECEIPT_MESSAGE, addedSpecification.getProductName(),
                PRODUCT_QUANTITY_RECEIPT_MESSAGE, quantityInput,
                PRODUCT_TOTAL_RECEIPT_MESSAGE, currencyFormat.format(productPriceTotal)));

        return returnString;
    }

    /**
     * Updates productSpecification
     */
    public void updateList()
    {
        productSpecificationObservableList = FXCollections.observableList(productCatalog.getListOfSpecification());
    }

    /**
     * Returns size from sale object
     *
     * @return int, size
     */
    public int checkSaleAmount()
    {
        return sale.checkSize();
    }

    /**
     * Resets sale
     */
    public void resetSale()
    {
        sale.resetSale();
    }

    /**
     * Saves productCatalog
     */
    public void saveCatalog()
    {
        productCatalog.updateFileFromData();
    }

    /**
     * Gets receipt
     *
     * @param changeAmountInput, Double change
     * @return String, receipt
     */
    public String checkOutReceiptString(Double changeAmountInput)
    {
        String topReceiptString = sale.createReceipt(currencyFormat);
        String bottomReceiptString = String.format("%n%-25s$%7s%n%s%n",
                CHANGE_AMOUNT,
                currencyFormat.format(changeAmountInput),
                RECEIPT_LINE);

        return topReceiptString + bottomReceiptString;
    }

    // tendered amount > subtotalTax
    /**
     * compares tender input to sale checkout total
     *
     * @param tenderInput Double, tender input
     * @return boolean, true = input is bigger
     */
    public boolean checkOut (Double tenderInput)
    {
        // Convert tender
        return sale.checkCheckoutTotal(BigDecimal.valueOf(tenderInput));
    }

    /**
     * Adds info as a productSpecification to productCatalog
     *
     * @param codeInput String, code input
     * @param nameInput String, name input
     * @param priceInput Double, price input
     */
    public void addProductCatalogProduct(String codeInput, String nameInput, Double priceInput)
    {
        // productSpecification to productCatalog
        productCatalog.addProductSpecification(codeInput,nameInput,BigDecimal.valueOf(priceInput));
        // Update
        updateList();
    }

    /**
     * Removes code input
     *
     * @param codeInput String, code input
     */
    public void deleteProductCatalogProduct(String codeInput) {
        // Delete item
        productCatalog.deleteProductSpecification(codeInput);
        // Update
        updateList();
    }

    /**
     * Removes old productSpecification
     *
     * @param codeInput String, code input
     * @param nameInput String, name input
     * @param priceInput Double, price input
     */
    public void modifyProductCatalogProduct(String codeInput, String nameInput, Double priceInput)
    {
        // Remove productSpecification
        productCatalog.deleteProductSpecification(codeInput);
        // Add productSpecification
        productCatalog.addProductSpecification(codeInput,nameInput,BigDecimal.valueOf(priceInput));
        // Update
        updateList();
    }

    /**
     * Check if code input has been used before
     *
     * @param codeInput String, code input
     * @return boolean, true = created before / false = not
     */
    public boolean checkIfCreate(String codeInput)
    {
        return productCatalog.getProductSpecification(codeInput) != null;
    }

    /**
     * Return observableList
     *
     * @return ObservableList<ProductSpecification>, observableList of productSpecifications
     */
    public ObservableList<ProductSpecification> getProductSpecificationObservableList()
    {
        return productSpecificationObservableList;
    }

    /**
     * Returns subtotal
     *
     * @return BigDecimal, sale subtotal
     */
    public BigDecimal getSubtotal()
    {
        return sale.getSubtotal();
    }

    /**
     * Returns subtotalTax
     *
     * @return BigDecimal, sale subtotalTax
     */
    public BigDecimal getSubtotalTax()
    {
        return sale.getSubtotalTax();
    }

    /**
     * Returns total
     *
     * @return BigDecimal, sale end of day total
     */
    public BigDecimal getEOD()
    {
        return sale.getEodTotal();
    }
}