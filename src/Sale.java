import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * tracks an Array list of SalesLineItems and subtotals
 */

public class Sale
{

    /**
     * ArrayList
     */
    ArrayList<SalesLineItem> salesLineItemArrayList;

    /**
     * values to store totals
     */
    BigDecimal eodTotal, subtotal, subtotalTax;

    /**
     * end of day total
     */
    Sale()
    {
        // Initialize
        salesLineItemArrayList = new ArrayList<SalesLineItem>();

        // Initialize
        eodTotal = BigDecimal.valueOf(0);
        subtotal = BigDecimal.valueOf(0);
        subtotalTax = BigDecimal.valueOf(0);
    }

    // Return boolean
    public boolean checkIfEmpty()
    {
        return salesLineItemArrayList.size() == 0;
    }

    public int checkSize()
    {
        return salesLineItemArrayList.size();
    }

    // true if tender >= subtotalTax
    public boolean checkCheckoutTotal(BigDecimal tenderInput)
    {
        return tenderInput.compareTo(subtotalTax) >= 0;
    }

    /**
     * set total and quantities counters to new values or add salesLineItem using specification, quantity,
     * and price inputs
     *
     * @param specification ProductSpecification, productSpecification to add
     * @param quantity int, amount
     * @param priceTotal BigDecimal, price total
     */
    public void addSalesLineItem(ProductSpecification specification, int quantity, BigDecimal priceTotal)
    {
        // Add totals
        eodTotal = eodTotal.add(priceTotal);
        subtotal = subtotal.add(priceTotal);
        if (specification.getProductTaxable())
        {
            // Tax
            subtotalTax = subtotalTax.add( priceTotal.add(priceTotal.multiply(BigDecimal.valueOf(.06))) );
        }
        else
        {
            // Non-tax
            subtotalTax = subtotalTax.add(priceTotal);
        }

        // Loop array list
        for (SalesLineItem SalesLineItemTracker : salesLineItemArrayList)
        {
            if (specification.getProductCode().equals(SalesLineItemTracker.getProductCode()))
            {
                // Set the salesLineItem price
                SalesLineItemTracker.setProductTotal((SalesLineItemTracker.getProductTotal()).add(priceTotal));
                // Set the salesLineItem quantity
                SalesLineItemTracker.setProductQuantity(quantity + SalesLineItemTracker.getProductQuantity());
                return;
            }
        }

        // not found
        salesLineItemArrayList.add(new SalesLineItem(specification,quantity,priceTotal));
    }

    /**
     * Creates a receipt
     *
     * @param currencyFormat DecimalFormat, format for currency
     * @return String, receipt string
     */
    public String createReceipt(DecimalFormat currencyFormat)
    {
        // Initialize
        String receiptString = "\nItems list:\n";
        String quantityNameTotalFormat = "%4s %-19s$%7s%n",
                subtotalFormat = "%-26s$%7s%n%-21s$%7s";
        // Sort
        Collections.sort(salesLineItemArrayList);
        // Loop through
        for (SalesLineItem salesLineItemTracker : salesLineItemArrayList)
        {
            // Add product info
            receiptString = receiptString.concat(
                    String.format(quantityNameTotalFormat,
                            salesLineItemTracker.getProductQuantity(),
                            salesLineItemTracker.getProductName(),
                            currencyFormat.format(salesLineItemTracker.getProductTotal())) );
        }

        // Compile subtotals
        receiptString = receiptString.concat(
                String.format(subtotalFormat,
                        "Subtotal",
                        currencyFormat.format(subtotal),
                        "Total with tax(6%)",
                        currencyFormat.format(subtotalTax)));

        return receiptString;
    }

    /**
     * Resets subtotal
     */
    public void resetSale() {
        // Reset subtotal
        subtotal = BigDecimal.valueOf(0);
        subtotalTax = BigDecimal.valueOf(0);
        // Reset Array
        salesLineItemArrayList = new ArrayList<SalesLineItem>();
    }

    /**
     * end of day total
     *
     * @return BigDecimal, eodTotal
     */
    public BigDecimal getEodTotal()
    {
        return eodTotal;
    }

    /**
     * Get subtotal
     *
     * @return BigDecimal, subtotal
     */
    public BigDecimal getSubtotal()
    {
        return subtotal;
    }

    /**
     * Get subtotalTax
     *
     * @return BigDecimal, subtotalTax
     */
    public BigDecimal getSubtotalTax()
    {
        return subtotalTax;
    }
}