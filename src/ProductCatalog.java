import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * creates randomAccessFile from file
 */

public class ProductCatalog
{

    /**
     * storing ProductSpecifications
     */
    private Map<String, ProductSpecification> productCatalogHashMap;

    /**
     * productSpecification objects
     */
    private ArrayList<ProductSpecification> listOfSpecification;

    /**
     * RandomAccessProductFile of  file
     */
    private RandomAccessProductFile randomAccessProductFile;

    /**
     * Format currency
     */
    private DecimalFormat currencyFormat;

    /**
     * sets currency format from passed DecimalFormat
     *
     * @param fileName String, file name
     * @param decimalFormat DecimalFormat, currency format
     */
    ProductCatalog(String fileName, DecimalFormat decimalFormat)
    {

        // Initialize
        productCatalogHashMap = new HashMap<>();

        // currency
        currencyFormat = decimalFormat;

        try
        {
            // product file
            File productFile = new File(".\\src\\resources\\" + fileName);
            // randomAccessFile
            randomAccessProductFile = new RandomAccessProductFile(productFile, "rw");
            // Input data from file
            updateDataFromFile();
        }
        catch (IOException ioException)
        {
            // failed
            ioException.printStackTrace();
        }

        // Initialize
        listOfSpecification = new ArrayList<>(productCatalogHashMap.values());
    }

    /**
     * Updates arrayList
     */
    public void updateDataFromFile() {
        try {
            // Set to beginning
            randomAccessProductFile.seek(0);
            String randomAccessFileString = randomAccessProductFile.readLine();
            String[] randomAccessFileStringSplit = randomAccessFileString.split(Pattern.quote(","));
            // While
            int index = 0;
            while (index < randomAccessFileStringSplit.length)
            {
                productCatalogHashMap.put(randomAccessFileStringSplit[index],
                        new ProductSpecification(randomAccessFileStringSplit[index],
                                randomAccessFileStringSplit[index + 1],
                                BigDecimal.valueOf(Double.parseDouble(randomAccessFileStringSplit[index+2])))
                );

                // index counter
                index+=3;
            }

        }
        catch (IOException ioException)
        {
            // failed
            ioException.printStackTrace();
        }
    }

    /**
     * Updates file from arrayList
     */
    public void updateFileFromData() {
        try {
            // Set to beginning
            randomAccessProductFile.seek(0);
            // Specifications
            listOfSpecification = new ArrayList<>(productCatalogHashMap.values());
            // Sort input
            Collections.sort(listOfSpecification);
            int index = 0, lengthCounter = 0;
            String productString = "";
            while (index < listOfSpecification.size())
            {
                productString = listOfSpecification.get(index).getProductCode() + "," +
                        listOfSpecification.get(index).getProductName() + "," +
                        currencyFormat.format(listOfSpecification.get(index).getProductPrice()) + ",";
                randomAccessProductFile.writeProductToRandomAccessProductFile(lengthCounter,productString);
                lengthCounter += productString.length();
                // Increase
                index += 1;
            }


            // longer than new file
            if (lengthCounter < randomAccessProductFile.length())
            {
                // set length to new length
                randomAccessProductFile.setLength(lengthCounter);
            }

        }
        catch (IOException ioException)
        {
            //  failed
            ioException.printStackTrace();
        }
    }

    /**
     * Adds productSpecification using code, name, and price inputs to productCatalogHashMap
     *
     * @param codeInput String, code input
     * @param nameInput String, name input
     * @param priceInput BigDecimal, price input
     */
    public void addProductSpecification(String codeInput, String nameInput, BigDecimal priceInput)
    {
        // hashmap
        productCatalogHashMap.put(codeInput,new ProductSpecification(codeInput,nameInput,priceInput));
        // Arraylist of values
        listOfSpecification = new ArrayList<>(productCatalogHashMap.values());
    }

    /**
     * Removes productCatalogHashMap
     *
     * @param codeInput String, find product
     */
    public void deleteProductSpecification(String codeInput)
    {
        //  hashmap
        productCatalogHashMap.remove(codeInput);
        // Arraylist of values
        listOfSpecification = new ArrayList<>(productCatalogHashMap.values());
    }

    /**
     * Creates and returns String of all info
     *
     * @param currentFormat DecimalFormat, currency
     * @return String, formatted string
     */
    public String getProductsStrings(DecimalFormat currentFormat) {
        // Declare strings
        String codeNamePriceLabelFormat = "%-11s %-15s %-12s%n", codeNamePriceListFormat = "%-11s %-15s %-8s%n";
        String returnString = "";

        // Add labels
        returnString = returnString.concat(
                String.format(codeNamePriceLabelFormat,
                        "item code",
                        "item name",
                        "unit price"));

        // Loop for info of each product
        for (Map.Entry<String, ProductSpecification> productCatalogTracker : productCatalogHashMap.entrySet())
        {
            returnString = returnString.concat(
                    String.format(codeNamePriceListFormat,
                            productCatalogTracker.getValue().getProductCode(),
                            productCatalogTracker.getValue().getProductName(),
                            currentFormat.format(productCatalogTracker.getValue().getProductPrice())));
        }

        return returnString;
    }

    /**
     * Searches for productSpecification object using key
     *
     * @param key String, item's code
     * @return ProductSpecification, return productSpecification
     */
    public ProductSpecification getProductSpecification(String key)
    {
        return productCatalogHashMap.getOrDefault(key, null);
    }

    /**
     * Returns array list
     *
     * @return ArrayList<ProductSpecification>, list productCatalog
     */
    public ArrayList<ProductSpecification> getListOfSpecification() {
        return listOfSpecification;
    }
}
