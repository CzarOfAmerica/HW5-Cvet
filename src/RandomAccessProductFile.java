import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * RandomAccessFile to extend reading
 */

public class RandomAccessProductFile extends RandomAccessFile
{

    /**
     * creates using constructor with inputs
     *
     * @param file File, file input
     * @param mode String, mode input
     * @throws FileNotFoundException
     */
    public RandomAccessProductFile(File file, String mode) throws FileNotFoundException
    {
        super(file, mode);
    }

    /**
     * Read from file
     *
     * @param position Int, position inpuht
     * @return String, string from file
     */
    public String readProductFromRandomAccessProductFile(int position)
    {
        String productRecord = "";

        try
        {
            // Move file pointer
            this.seek(position);
            // Read from RandomAccessProductFile
            productRecord = this.readUTF();

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return productRecord;
    }

    /**
     * String input to file
     *
     * @param position Int, position input
     * @param productRecord String, String to input
     */
    public void writeProductToRandomAccessProductFile(int position, String productRecord)
    {
        try {
            // move pointer
            this.seek(position);
            // to RandomAccessProductFile
            this.writeBytes(productRecord);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}