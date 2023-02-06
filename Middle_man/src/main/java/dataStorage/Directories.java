package dataStorage;

import entities.Person;
import exceptions.LoginPasswordException;

import java.io.*;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;

public class Directories {

    public static final String FILENAME_LOGIN = "dat/login.txt";


    public static String getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static void setSalt(Properties configuration){

        FileWriter writer = null;
        try {
            writer = new FileWriter(new File("dat/hashSalt.properties"));
            configuration.setProperty("salt", getSalt());
            configuration.store(writer,"HASH SALT! DO NOT CHANGE OR ELSE PASSWORDS WILL BE LOST");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSaltFromProperties(){
        Properties configuration = new Properties();
        String salt;
        try {
            configuration.load(new FileReader("dat/hashSalt.properties"));
            if(configuration.getProperty("salt").isEmpty())
                setSalt(configuration);
            salt = configuration.getProperty("salt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return salt;
    }

    public static String hashPassword(String password, String salt) {
        String passwordAndSalt = password + salt;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(passwordAndSalt.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void newPerson(String username,String password){
        try{

            FileWriter fw = new FileWriter(new File(FILENAME_LOGIN),true);
            PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
            String hashedPassword = hashPassword(password,getSaltFromProperties());
            pw.println(username);
            pw.println(hashedPassword);
            pw.close();
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static Optional<Person> findPerson(String username, String password){
        Optional<Person> person = Optional.empty();
        try(Scanner scan = new Scanner(Paths.get(FILENAME_LOGIN))){

            while(scan.hasNextLine()){
                String tempUser = scan.nextLine();
                String tempPassword = scan.nextLine();
                String hashedPassword = hashPassword(password,getSaltFromProperties());
                if(username.equals(tempUser)){
                    if(!tempPassword.equals(hashedPassword))
                        throw new LoginPasswordException();
                    person = Database.getPersonByUsername(Database.connectToDatabase(),username);
                    break;
                }
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }catch (LoginPasswordException e){
            System.out.println("PASSWORD IS WRONG TRY AGAIN");
        }
        return person;
    }
}
