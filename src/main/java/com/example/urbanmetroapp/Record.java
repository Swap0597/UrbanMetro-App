package com.example.urbanmetroapp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.ResultSet;

public class Record {
    private SimpleStringProperty src;
    private SimpleStringProperty dest;
    private SimpleIntegerProperty fair;
    private SimpleIntegerProperty distance;

    public String getSrc() {
        return src.get();
    }

    public String getDest() {
        return dest.get();
    }

    public int getFair() {
        return fair.get();
    }

    public int getDistance() {
        return distance.get();
    }

    static TableView<Record> recordTable;

    public Record(String src, String dest, int fair, int distance) {
        this.src = new SimpleStringProperty(src);
        this.dest = new SimpleStringProperty(dest);
        this.fair = new SimpleIntegerProperty(fair);
        this.distance = new SimpleIntegerProperty(distance);
    }

    public static Pane createRecordTable(ObservableList<Record> records) {

        recordTable = new TableView<>();
        recordTable.setPrefSize(330, 370);
        recordTable.setTranslateY(10);
        recordTable.setItems(records);

        TableColumn source = new TableColumn("Source");
        source.setCellValueFactory(new PropertyValueFactory<>("src"));

        TableColumn destination = new TableColumn("Destination");
        destination.setCellValueFactory(new PropertyValueFactory<>("dest"));

        TableColumn fair = new TableColumn("Fair");
        fair.setCellValueFactory(new PropertyValueFactory<>("fair"));

        TableColumn distance = new TableColumn("Distance");
        distance.setCellValueFactory(new PropertyValueFactory<>("distance"));

        recordTable.getColumns().addAll(source, destination, fair, distance);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(recordTable);
        tablePane.setTranslateX(15);
        return tablePane;
    }

    public static Pane getRecordOfCustomer(Customer loggedInCustomer) {
        String record = "select record.src, record.dest, record.fair, record.distance from record inner join customer on customer.cid = record.cid where customer.cid =" + loggedInCustomer.getId();
        ObservableList<Record> orderList = Record.getRecordOfCustomer(record);

        return Record.createRecordTable(orderList);
    }

    private static ObservableList<Record> getRecordOfCustomer(String query) {
        DataBaseConnection dbConn = new DataBaseConnection();
        ResultSet rs = dbConn.getQueryTable(query);
        ObservableList<Record> result = FXCollections.observableArrayList();
        try {
            if (rs != null) {
                while (rs.next()) {
                    // taking out values from RseultSet
                    result.add(new Record(
                                    rs.getString("src"),
                                    rs.getString("dest"),
                                    rs.getInt("fair"),
                                    rs.getInt("distance")
                            )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getTotalFair(Customer loggedInCustomer) {
        String query = "select sum(fair) from record where cid = " + loggedInCustomer.getId();
        DataBaseConnection dbConn = new DataBaseConnection();
        try {
            ResultSet rs = dbConn.getQueryTable(query);
            if (rs != null && rs.next()) {
                return String.valueOf(rs.getInt("sum(fair)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getTotalDist(Customer loggedInCustomer) {
        String query = "select sum(distance) from record where cid = " + loggedInCustomer.getId();
        DataBaseConnection dbConn = new DataBaseConnection();
        try {
            ResultSet rs = dbConn.getQueryTable(query);

            if (rs != null && rs.next()) {
                return String.valueOf(rs.getInt("sum(distance)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clearAllRecord(Customer loggedInCustomer) {
        String query = "delete from record where cid = " + loggedInCustomer.getId();
        DataBaseConnection dbConn = new DataBaseConnection();
        dbConn.insertUpdate(query);
    }
}
