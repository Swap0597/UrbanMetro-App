package com.example.urbanmetroapp;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
public class StationNames {
    static TableView<Stations> stationTable;
    public Pane getAllStations() {
        ObservableList<Stations> stationList = Stations.getAllStations();
        return createTableFromList(stationList);
    }
    public Pane getSearchedStation(String String) {
        ObservableList<Stations> stationList = Stations.getSearchedStation(String);
        return createTableFromList(stationList);
    }
    public static Pane createTableFromList(ObservableList<Stations> stationList) {
        TableColumn stationId = new TableColumn("Station Id");
        stationId.setCellValueFactory(new PropertyValueFactory<>("stationId"));
        TableColumn stationName = new TableColumn("Station Name");
        stationName.setCellValueFactory(new PropertyValueFactory<>("stationName"));

        stationTable = new TableView<>();
        stationTable.setTranslateY(10);
        stationTable.setPrefSize(260, 370);
        stationTable.setItems(stationList);
        stationTable.getColumns().addAll(stationId, stationName);

        Pane tablePane = new Pane();
        tablePane.getChildren().add(stationTable);
        tablePane.setTranslateX(15);
        stationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tablePane;
    }
}
