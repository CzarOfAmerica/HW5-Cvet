import java.math.BigDecimal;

/**
 * Holds info of products sold
 */

public class SalesLineItem implements Comparable<SalesLineItem>{

    /**
     * value of product
     */
    private BigDecimal productTotal;

    /**
     * product code and name
     */
    private String productCode, productName;

    /**
     * product quantity
     */
    private int productQuantity;

    /**
     * taxable value
     */
    private boolean productTaxable;

    /**
     * sets code and name to empty strings
     */
    SalesLineItem()
    {
        productCode = "";
        productName = "";
        productTotal = BigDecimal.valueOf(0.00);
        productQuantity = 0;
        productTaxable = false;
    }

    /**
     * Creates SalesLineItem
     *
     * @param specification ProductSpecification, product being sold
     * @param quantity int, amount of product
     * @param total BigDecimal, price total
     */
    SalesLineItem(ProductSpecification specification, int quantity, BigDecimal total)
    {
        productCode = specification.getProductCode();
        productName = specification.getProductName();
        productTotal = total;
        productQuantity = quantity;
        productTaxable = specification.getProductTaxable();
    }

    /**
     * Get code
     *
     * @return String, productCode
     */
    public String getProductCode()
    {
        return productCode;
    }

    /**
     * Get name
     *
     * @return String, productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * Get total
     *
     * @return BigDecimal, productTotal
     */
    public BigDecimal getProductTotal()
    {
        return productTotal;
    }

    /**
     * Get quantity
     *
     * @return int, productQuantity
     */
    public int getProductQuantity()
    {
        return productQuantity;
    }

    /**
     * Gets taxable
     *
     * @return boolean, true = taxable / false = nontaxable
     */
    public boolean isProductTaxable()
    {
        return productTaxable;
    }

    /**
     * product code to input
     *
     * @param productCode String, productCode
     */
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    /**
     * product name to input
     *
     * @param productName String, productName
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * product total to input
     *
     * @param productTotal BigDecimal, productTotal
     */
    public void setProductTotal(BigDecimal productTotal)
    {
        this.productTotal = productTotal;
    }

    /**
     * product quantity to input
     *
     * @param productQuantity int, productQuantity
     */
    public void setProductQuantity(int productQuantity)
    {
        this.productQuantity = productQuantity;
    }

    /**
     * Overrides compareTo
     *
     * @param o  compared.
     * @return int
     */
    @Override
    public int compareTo(SalesLineItem o)
    {
        return (this.getProductName().compareTo(o.getProductName()));
    }
}