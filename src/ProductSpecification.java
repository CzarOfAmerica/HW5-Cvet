import java.math.BigDecimal;

/**
 * info for products
 */

public class ProductSpecification implements Comparable<ProductSpecification>
{

    /**
     * code and name
     */
    private String productCode, productName;

    /**
     * value of productPrice
     */
    private BigDecimal productPrice;

    /**
     * taxable value
     */
    private boolean productTaxable;

    /**
     * code and name to empty strings
     */
    ProductSpecification()
    {
        productCode = "";
        productName = "";
        productPrice = BigDecimal.valueOf(0.00);
        productTaxable = false;
    }

    /**
     * Creates new ProductSpecification
     *
     * @param code String, code input
     * @param name String, name input
     * @param price BigDecimal, price input
     */
    ProductSpecification(String code, String name, BigDecimal price)
    {
        productCode = code;
        productName = name;
        productPrice = price;
        productTaxable = (code.charAt(0) == 'A');
    }

    /**
     * product code
     *
     * @return String, productCode
     */
    public String getProductCode()
    {
        return productCode;
    }

    /**
     * product name
     *
     * @return String, productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * product price
     *
     * @return BigDecimal, productPrice
     */
    public BigDecimal getProductPrice()
    {
        return productPrice;
    }

    /**
     * taxable value
     *
     * @return boolean, taxable state (true = taxable) (false = nonTaxable)
     */
    public boolean getProductTaxable()
    {
        return productTaxable;
    }

    /**
     * product code
     *
     * @param productCode String, productCode
     */
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    /**
     * name to input
     *
     * @param productName String, productName
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * price to input
     *
     * @param productPrice BigDecimal, productPrice
     */
    public void setProductPrice(BigDecimal productPrice)
    {
        this.productPrice = productPrice;
    }

    /**
     * create string of productSpecification
     *
     * @return String value of productName,productCode
     */
    @Override
    public String toString()
    {
        return productName + "," + productCode;
    }

    /**
     * compare product codes
     *
     * @param o the object to be compared.
     * @return int, greater = greater than 0, equal = 0, lesser = less than 0
     */
    @Override
    public int compareTo(ProductSpecification o)
    {
        return (this.getProductCode().compareTo(o.getProductCode()));
    }

}