package prj.pt.assignment4.DataLayer;

import prj.pt.assignment4.BusinessLayer.BaseProduct;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Login implements java.io.Serializable{
    private String username;
    private String password;
    private String filename = "users.txt";

    public Login(String username, String password){
        this.password = password;
        this.username = username;
    }
    public void writeData(){
        try {
            FileWriter myWriter = new FileWriter(filename, true);

            myWriter.append(username).append(":").append(password).append("\n");
            myWriter.close();
           // System.out.println("success");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public List<Login> readData() throws IOException{
        Path path = Path.of("users.txt");
        List<Login> list;
        list = Files.lines(path)
                .map(line-> {
                    String[] fields = line.split(":");
                    return new Login(fields[0],fields[1]);
                }).toList();
        return list;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
