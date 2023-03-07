package com.example.urbanmetroapp;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
public class SignUp {
    private static byte[] getSha(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(password.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private static String getEncryptedPassword(String password){
        try{
            BigInteger num = new BigInteger(getSha(password));
            StringBuilder haxString = new StringBuilder(num.toString(16));
            return haxString.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean customerSignUp( String name, String email, int mobile, String address, String password){
        // "INSERT INTO CUSTOMER (name, email, mobile, address, password) values ('swapnil', 'swap@gmail.com', 7709, 'Nagpur', '123');"
        String encryptedPass = getEncryptedPassword(password);
        try{
            String addCustomer = "INSERT INTO CUSTOMER (name, email, mobile, address, password) values ('"+ name + "', '" +
                    email +" '," +  mobile + ", '" + address + "', '" + encryptedPass + "')";
            DataBaseConnection dbConn = new DataBaseConnection();
            return dbConn.insertUpdate(addCustomer);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
