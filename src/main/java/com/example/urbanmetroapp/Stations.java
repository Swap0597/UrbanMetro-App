package com.example.urbanmetroapp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
public class Stations {
    private SimpleIntegerProperty StationId;
    private SimpleStringProperty stationName;
    public int getStationId() {
        return StationId.get();
    }
    public String getStationName() {
        return stationName.get();
    }
    public static boolean updateDistAndFair(String src, String dest, int cid, int minDistance) {
        // insert into record (cid, src, dest, fair, distance) values (1, 'dvs', 'sdfvsd', 34, 456);
        try {
            String insertRecord = "insert into record (cid, src, dest, fair, distance) values (" + cid + ", '" + src + "', '" + dest + "'," + minDistance * 3 + ", " + minDistance + ")";
            DataBaseConnection dbConn = new DataBaseConnection();
            return dbConn.insertUpdate(insertRecord);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static Stations getStationNameFrmId(int id) {
        String query = "SELECT * FROM stationname WHERE stationId = " + id;
        DataBaseConnection dbConn = new DataBaseConnection();
        try {
            ResultSet rs = dbConn.getQueryTable(query);

            if (rs != null && rs.next()) {
                return new Stations(
                        rs.getInt("StationId"),
                        rs.getString("StationName")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Stations(int stationId, String stationName) {
        this.StationId = new SimpleIntegerProperty(stationId);
        this.stationName = new SimpleStringProperty(stationName);
    }
    public static ObservableList<Stations> getAllStations() {
        String allStations = "SELECT * FROM stationname ORDER BY stationid";
        return getStations(allStations);
    }
    public static ObservableList<Stations> getSearchedStation(String query) {
        String searchedStation = "select * from stationname where stationname like '%" + query + "%'";
        return getStations(searchedStation);
    }
    public static ObservableList<Stations> getStations(String query) {
        DataBaseConnection dbConn = new DataBaseConnection();
        ResultSet rs = dbConn.getQueryTable(query);
        ObservableList<Stations> result = FXCollections.observableArrayList();
        try {
            if (rs != null) {
                while (rs.next()) {
                    // taking out values from RseultSet
                    result.add(new Stations(
                                    rs.getInt("stationid"),
                                    rs.getString("stationname")
                            )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
