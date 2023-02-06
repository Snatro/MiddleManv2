package dataStorage;

import entities.*;

import javax.xml.transform.Result;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class Database {

    public static Connection connectToDatabase() throws SQLException, IOException {

        Properties configuration = new Properties();
        configuration.load(new FileReader("dat/database.properties"));
        String databaseURL = configuration.getProperty("databaseURL");
        String databaseUsername = configuration.getProperty("databaseUsername");
        String databasePassword = configuration.getProperty("databasePassword");
        Connection connection = DriverManager
                .getConnection(databaseURL,
                        databaseUsername,
                        databasePassword);
        return connection;
    }

    public static List<Role> getListOfRoles(Connection connection) throws SQLException{
        List<Role> roles = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();

        ResultSet rolesResultSet = sqlStatement.executeQuery("SELECT * FROM ROLE");

        while(rolesResultSet.next()){
            int id = rolesResultSet.getInt("id");
            String name = rolesResultSet.getString("name");
            String code = rolesResultSet.getString("code");
            Role role = new Role(id,name,code);
            roles.add(role);
        }
        return roles;
    }

    public static List<Person> getListOfPersons(Connection connection) throws SQLException {
        List<Person> personList = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();

        ResultSet personResultSet = sqlStatement.executeQuery("SELECT * FROM PERSON");

        while(personResultSet.next()){

            int id = personResultSet.getInt("id");
            String name = personResultSet.getString("name");
            int roleId = personResultSet.getInt("roleId");

            Optional<Role> role = getRoleById(connection,roleId);

            if(role.isPresent()) {
                Person person = new Person(id, name, role.get());
                personList.add(person);
            }

        }
        return personList;
    }

    public static List<Category> getListOfCategories(Connection connection) throws SQLException{
        List<Category> categories = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();

        ResultSet categoryResultSet = sqlStatement.executeQuery("SELECT * FROM CATEGORY");

        while(categoryResultSet.next()){
            int id = categoryResultSet.getInt("id");
            String name = categoryResultSet.getString("name");
            Category category = new Category(id,name);
            categories.add(category);
        }
        return categories;
    }

    public static List<Supply> getListOfSupplies(Connection connection) throws SQLException {

        List<Supply> supplyList = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();

        ResultSet supplyResultSet = sqlStatement.executeQuery("SELECT * FROM SUPPLY");

        while(supplyResultSet.next()){

            int id = supplyResultSet.getInt("id");
            String name = supplyResultSet.getString("name");
            int categoryId = supplyResultSet.getInt("categoryId");

            Optional<Category> category = getCategoryById(connection,categoryId);

            if(category.isPresent()) {
                Supply supply = new Supply(id, name, category.get());
                supplyList.add(supply);
            }
        }
        return supplyList;
    }

    public static List<Store> getListOfStores(Connection connection) throws SQLException {

        List<Store> storeList = new ArrayList<>();
        Statement sqlStatement = connection.createStatement();

        ResultSet storeResultSet = sqlStatement.executeQuery("SELECT * FROM STORE");

        while(storeResultSet.next()){

            int id = storeResultSet.getInt("id");
            String name = storeResultSet.getString("name");
            String city = storeResultSet.getString("city");
            String address = storeResultSet.getString("address");
            int managerId = storeResultSet.getInt("managerId");

            Optional<Person> manager = getPersonById(connection,managerId);

            if(manager.isPresent()) {
                Store store = new Store(id, name, manager.get(),city,address);
                storeList.add(store);
            }
        }
        return storeList;
    }

    public static Set<SuppliesInStore> getSuppliesByStoreId(Connection connection, int storeId){

        Set<SuppliesInStore> supplies = new HashSet<>();

        String query = "SELECT supplyId,amount,price FROM SuppliesInStore WHERE storeId = " + String.valueOf(storeId);
        try {
            
            Optional<Store> store = getStoreById(connection,storeId);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()){
                int supplyId = rs.getInt("supplyId");
                int totalAmount = rs.getInt("amount");
                BigDecimal price = rs.getBigDecimal("price");

                Optional<Supply> supply = getSupplyById(connection,supplyId);
                if(supply.isPresent()) {
                    SuppliesInStore supplyInStore = new SuppliesInStore.SuppliesInStoreBuilder(supply.get())
                            .setAmount(totalAmount).setPrice(price).build();
                    supplies.add(supplyInStore);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return supplies;

    }
    public static Optional<Person> getPersonById(Connection connection, int id) throws SQLException{

        PreparedStatement sqlStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE ID = ?");
        Optional<Person> person = Optional.empty();
        sqlStatement.setInt(1,id);
        ResultSet personResultSet = sqlStatement.executeQuery();
        while (personResultSet.next()) {

            int personId = personResultSet.getInt("id");
            String name = personResultSet.getString("name");
            int roleId = personResultSet.getInt("roleId");

            Optional<Role> managerRole = getRoleById(connection,roleId);

            if(managerRole.isPresent())
                person = Optional.of(new Person(personId,name,managerRole.get()));
        }
        return person;
    }
    public static Optional<Role> getRoleById(Connection connection, int id) throws SQLException{

        PreparedStatement sqlStatement = connection.prepareStatement("SELECT * FROM ROLE WHERE ID = ?");
        Optional<Role> role = Optional.empty();
        sqlStatement.setInt(1,id);
        ResultSet roleResultSet = sqlStatement.executeQuery();

        while (roleResultSet.next()) {

            int roleId = roleResultSet.getInt("id");
            String name = roleResultSet.getString("name");
            String code = roleResultSet.getString("code");
            role = Optional.of(new Role(roleId,name,code));

        }
        return role;
    }

    public static Optional<Store> getStoreById(Connection connection, int id) throws SQLException{

        PreparedStatement sqlStatement = connection.prepareStatement("SELECT * FROM STORE WHERE ID = ?");
        Optional<Store> store = Optional.empty();
        sqlStatement.setInt(1,id);
        ResultSet storeResultSet = sqlStatement.executeQuery();

        while (storeResultSet.next()) {

            int storeId = storeResultSet.getInt("id");
            String name = storeResultSet.getString("name");
            String city = storeResultSet.getString("city");
            String address = storeResultSet.getString("address");
            int managerId = storeResultSet.getInt("managerId");

            Optional<Person> manager = getPersonById(connection,id);

            if(manager.isPresent())
                store = Optional.of(new Store(storeId,name,manager.get(),city,address));
        }
        return store;
    }

    public static Optional<Supply> getSupplyById(Connection connection, int id) throws SQLException{

        PreparedStatement sqlStatement = connection.prepareStatement("SELECT * FROM SUPPLY WHERE ID = ?");
        Optional<Supply> supply = Optional.empty();
        sqlStatement.setInt(1,id);
        ResultSet supplyResultSet = sqlStatement.executeQuery();
        while (supplyResultSet.next()) {

            int supplyId = supplyResultSet.getInt("id");
            String name = supplyResultSet.getString("name");
            int categoryId = supplyResultSet.getInt("categoryId");

            Optional<Category> category = getCategoryById(connection,categoryId);
            if(category.isPresent()){
                supply = Optional.of(new Supply(supplyId,name,category.get()));
            }
        }
        return supply;
    }
    public static Optional<Category> getCategoryById(Connection connection, int id) throws SQLException{

        PreparedStatement sqlStatement = connection.prepareStatement("SELECT * FROM CATEGORY WHERE ID = ?");
        Optional<Category> category = Optional.empty();
        sqlStatement.setInt(1,id);

        ResultSet categoryResultSet = sqlStatement.executeQuery();
        while (categoryResultSet.next()) {
            int roleId = categoryResultSet.getInt("id");
            String name = categoryResultSet.getString("name");
            category = Optional.of(new Category(roleId,name));
        }
        return category;
    }

    public static Optional<Person> getPersonByUsername(Connection connection,String username){

        PreparedStatement sqlStatement = null;
        Optional<Person> person = Optional.empty();

        try {
            sqlStatement = connection.prepareStatement("SELECT * FROM PERSON WHERE NAME = ?");
            sqlStatement.setString(1,username);
            ResultSet personResultSet = sqlStatement.executeQuery();
            while (personResultSet.next()) {

                int personId = personResultSet.getInt("id");
                String name = personResultSet.getString("name");
                int roleId = personResultSet.getInt("roleId");

                Optional<Role> managerRole = getRoleById(connection,roleId);

                if(managerRole.isPresent())
                    person = Optional.of(new Person(personId,name,managerRole.get()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;
    }

    public static void insertStoreIntoDatabase(Store store){
        try{
            Connection connection = Database.connectToDatabase();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO STORE(NAME,MANAGERID,ADDRESS,CITY) VALUES (?,?,?,?)");
            stmt.setString(1,store.getName());
            stmt.setInt(2,store.getManager().getId());
            stmt.setString(3,store.getAddress());
            stmt.setString(4, store.getCity());

            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertPersonIntoDatabase(Person person){
        try{
            Connection connection = Database.connectToDatabase();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO PERSON(NAME,ROLEID) VALUES (?,?)");
            stmt.setString(1,person.getName());
            stmt.setInt(2,person.getRole().getId());
            stmt.executeUpdate();
            System.out.println(person.getName() + " has been inserted");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static int insertIntoCategoryReturnId(Category category){
        int newId = 0;
        String query =
                "CREATE ALIAS insert_category_and_return_id AS $$\n" +
                "    DECLARE\n" +
                "        new_id INT;\n" +
                "    BEGIN\n" +
                "        INSERT INTO CATEGORY(Name)\n" +
                "        VALUES (?);\n" +
                "        SET new_id = IDENTITY();\n" +
                "        RETURN new_id;\n" +
                "    END;\n" +
                "$$;";
        try{
            Connection connection = Database.connectToDatabase();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT insert_category_and_return_id(?)");
            preparedStatement.setString(1,category.name());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                 newId = rs.getInt(1);
                System.out.println("New ID: " + newId);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return newId;
    }
    public static void insertSupplyIntoDatabase(Supply supply){
        try{
            Connection connection = Database.connectToDatabase();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO SUPPLY(NAME,CATEGORYID) VALUES (?,?)");
            stmt.setString(1,supply.getName());
            stmt.setInt(2,supply.getCategory().id());
            stmt.executeUpdate();
            System.out.println(supply.getName() + " has been inserted");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertSupplyInStoreIntoDatabase(SuppliesInStore supply,Store store){
        try{
            Connection connection = Database.connectToDatabase();
            PreparedStatement stmt = connection
                    .prepareStatement("INSERT INTO SUPPLIESINSTORE(STOREID,SUPPLYID,AMOUNT,PRICE) VALUES (?,?,?,?)");
            stmt.setInt(1,supply.getSupplies().getId());
            stmt.setInt(2,store.getId());
            stmt.setInt(3,supply.getAmount());
            stmt.setBigDecimal(4,supply.getPrice());
            stmt.executeUpdate();
            System.out.println(supply.getSupplies().getName() + " has been inserted");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteStore(Store store){
        try{
            Connection connection = Database.connectToDatabase();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM STORE WHERE ID = ?");
            stmt.setInt(1,store.getId());
            deleteSuppliesInStoreByStoreId(store.getId(),connection);
            stmt.executeUpdate();
            System.out.println("Store deleted");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteSuppliesInStoreByStoreId(int storeId, Connection connection){
        try
        {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM SUPPLIESINSTORE WHERE STOREID = ? ");
            stmt.setInt(1,storeId);
            stmt.executeUpdate();
            System.out.println("Deleted supplies from store");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deletePerson(Person person){
        try{
            Connection connection = Database.connectToDatabase();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM PERSON WHERE ID = ?");
            stmt.setInt(1,person.getId());
            deleteManagersFromStore(person.getId(),connection);
            stmt.executeUpdate();
            System.out.println("Person deleted");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteManagersFromStore(int managerId,Connection connection){
        try
        {
            PreparedStatement stmt = connection.prepareStatement("UPDATE STORE SET MANAGERID =  NULL WHERE MANAGERID = ? ");
            stmt.setInt(1,managerId);
            stmt.executeUpdate();
            System.out.println("Managers removed from store");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteSupply(Supply supply){
        try{
            Connection connection = Database.connectToDatabase();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM SUPPLY WHERE ID = ?");
            stmt.setInt(1,supply.getId());
            deleteSupplyFromStoreBySupplyId(supply.getId(),connection);
            stmt.executeUpdate();
            System.out.println("Person deleted");
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteSupplyFromStoreBySupplyId(int supplyId,Connection connection){
        try
        {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM SUPPLIESINSTORE WHERE SUPPLYID = ? ");
            stmt.setInt(1,supplyId);
            stmt.executeUpdate();
            System.out.println("Supply removed from store");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteSupplyFromStore(int supplyId,int storeId,Connection connection){
        try
        {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM SUPPLIESINSTORE WHERE SUPPLYID = ? AND STOREID = ? ");
            stmt.setInt(1,supplyId);
            stmt.setInt(2,supplyId);
            stmt.executeUpdate();
            System.out.println("Supply removed from store");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
