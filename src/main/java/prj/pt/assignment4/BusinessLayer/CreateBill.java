package prj.pt.assignment4.BusinessLayer;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CreateBill {
    private List<String> items;
    private String total;
    private Date date;
    private boolean success = true;
    private static Integer number = 0;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public CreateBill(List<String> items, String total, Date date) {
        this.items = items;
        this.total = total;
        this.date = date;

    }

    public void createBillFile(){
        StringBuilder result = new StringBuilder("========= Bill" + number.toString() + " =========\n");
        String[] field = new String[3];
        for (String item : items) {
            field = item.split("->");
            result.append(field[0] + "\n");
        }
        result.append("\nDate: " + formatter.format(date) + "\n");
        result.append("========= Total: "+ total + " =========");
        number++;

        String name = "Bill"+number.toString()+".txt";

        File file = new File("D:\\PT2022_30423_Deac_DanCristian_Assignment_4\\"+ name);

        boolean check;
        try {
            check = file.createNewFile();
            if(check){
                System.out.println("file created "+file.getCanonicalPath()); //returns the path string
            }
            else {
                System.out.println("File already exist at location: "+file.getCanonicalPath());
                success = false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try
        {

            FileOutputStream fos=new FileOutputStream(name);  // true for append mode, da nu vreau decat write
            String str = result.toString();

            byte[] b= str.getBytes();

            fos.write(b);           //writes bytes into file
            fos.close();            //close the file
            System.out.println("file saved.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public boolean checkSate(){
        return success;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
