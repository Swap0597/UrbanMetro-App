package com.example.urbanmetroapp;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class UrbanMetro extends Application {
    private final int width = 700, height = 400, headerLine = 50;
    Font fontForMessages = Font.font("Arial", FontWeight.BOLD, 15);
    Font fontForLabel = Font.font("Arial", FontWeight.BOLD, 12);
    Font fontForText = Font.font("Arial", 11);
    Font fontForButton = Font.font("Arial", 12);
    Customer loggedInCustomer = null;
    Pane headder;
    Pane bodyPane;
    StationNames stationName = new StationNames();
    Button signInButton = new Button("Sign In ");
    Button signOutButton = new Button("Sign Out");
    Button backButton = new Button("Back");
    Button customerRecordButton = new Button("  Record  ");
    Button stationListButton = new Button("Station List");
    Button clearRecord = new Button("Clear Record");
    Button signUpButtonInHeadder = new Button("Sign Up");
    Label sumOfDist = new Label();
    Label sumOfFair = new Label();

    private GridPane headerBar() {

        TextField searchStation = new TextField();
        searchStation.setPromptText("Search Station Here");
        Button searchButton = new Button("Search");

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginPage());
                stationListButton.setVisible(false);
                signUpButtonInHeadder.setVisible(true);
            }
        });
        stationListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(stationName.getAllStations());
                if (loggedInCustomer == null) {
                    stationListButton.setVisible(false);
                    signUpButtonInHeadder.setVisible(true);
                }
            }
        });
        signOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                loggedInCustomer = null;
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signUpPage());
                signOutButton.setVisible(false);
                signInButton.setVisible(true);
                customerRecordButton.setVisible(false);
                backButton.setVisible(false);
                stationListButton.setVisible(true);
            }
        });
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String stationToBeSearched = searchStation.getText();
                bodyPane.getChildren().clear();
                if (loggedInCustomer != null) {
                    bodyPane.getChildren().add(afterLogInPage());
                    bodyPane.getChildren().add(stationName.getSearchedStation(stationToBeSearched));

                }
                if (loggedInCustomer == null) {
                    backButton.setVisible(false);
                    stationListButton.setVisible(false);
                    signUpButtonInHeadder.setVisible(true);
                    bodyPane.getChildren().add(stationName.getSearchedStation(stationToBeSearched));

                }
            }
        });
        signUpButtonInHeadder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signUpPage());
                signUpButtonInHeadder.setVisible(false);
                stationListButton.setVisible(true);
            }
        });
        customerRecordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                customerRecordButton.setVisible(false);
                backButton.setVisible(true);
                bodyPane.getChildren().add(recordPage());
                clearRecord.setVisible(true);
            }
        });
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (loggedInCustomer != null) {
                    backButton.setVisible(false);
                    customerRecordButton.setVisible(true);
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(afterLogInPage());
                    signInButton.setVisible(true);
                } else {
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(signUpPage());
                }
            }
        });
        clearRecord.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Record.clearAllRecord(loggedInCustomer);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(recordPage());
                sumOfFair.setText("");
                sumOfDist.setText("");
                sumOfFair.setText("RS. 00");
                sumOfDist.setText("00 Kms");
            }
        });
        clearRecord.setVisible(false);
        signUpButtonInHeadder.setVisible(false);
        backButton.setVisible(false);
        customerRecordButton.setVisible(false);
        signOutButton.setVisible(false);

        GridPane header = new GridPane();
        header.setHgap(10);
        header.setTranslateX(10);
        header.setTranslateY(10);
        header.add(signInButton, 2, 0);
        header.add(signOutButton, 2, 0);
        header.add(backButton, 3, 0);
        header.add(stationListButton, 3, 0);
        header.add(customerRecordButton, 3, 0);
        header.add(searchStation, 4, 0);
        header.add(searchButton, 5, 0);
        header.add(clearRecord, 6, 0);
        header.add(signUpButtonInHeadder, 3, 0);
        return header;
    }

    private GridPane signUpPage() {
        Label nameLabel = new Label("Name");
        Label passLabel = new Label("Password");
        Label emailLabel = new Label("Email");
        Label mobileLabel = new Label("Mobile No.");
        Label addressLabel = new Label("Address");
        TextField userName = new TextField();
        userName.setPromptText("Enter User Name");
        PasswordField userPassword = new PasswordField();
        userPassword.setPromptText("Enter Password");
        TextField userEmail = new TextField();
        userEmail.setPromptText("Enter Email");
        TextField userMobile = new TextField();
        userMobile.setPromptText("Enter Mobile No.");
        TextField userAddress = new TextField();
        userAddress.setPromptText("Enter Address");
        Button signUpButton = new Button("Sign Up");

        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String password = userPassword.getText();
                String email = userEmail.getText();
                int mobile = Integer.parseInt(userMobile.getText());
                String address = userAddress.getText();

                boolean checkSignUp = SignUp.customerSignUp(name, email, mobile, address, password);
                if(checkSignUp == true){
                    showText("Sign Up Successful \n Please Login To Continue");
                }
                else{
                    showText("Try Again");
                }
            }
        });

        GridPane signUpPane = new GridPane();
        clearRecord.setVisible(false);
        signUpPane.setTranslateY(50);
        signUpPane.setTranslateX(50);
        signUpPane.setVgap(10);
        signUpPane.setHgap(10);
        signUpPane.add(nameLabel, 0, 0);
        nameLabel.setFont(fontForLabel);
        signUpPane.add(userName, 1, 0);
        userName.setFont(fontForText);
        signUpPane.add(passLabel, 0, 1);
        passLabel.setFont(fontForLabel);
        signUpPane.add(userPassword, 1, 1);
        userPassword.setFont(fontForText);
        signUpPane.add(emailLabel, 0, 2);
        emailLabel.setFont(fontForLabel);
        signUpPane.add(userEmail, 1, 2);
        userEmail.setFont(fontForText);
        signUpPane.add(mobileLabel, 0, 3);
        mobileLabel.setFont(fontForLabel);
        signUpPane.add(userMobile, 1, 3);
        userMobile.setFont(fontForText);
        signUpPane.add(addressLabel, 0, 4);
        addressLabel.setFont(fontForLabel);
        signUpPane.add(userAddress, 1, 4);
        userAddress.setFont(fontForText);
        signUpPane.add(signUpButton, 1, 6);
        signUpButton.setFont(fontForButton);

        return signUpPane;
    }

    private GridPane loginPage() {
        Label userLabel = new Label("User Name");
        Label passLabel = new Label("Password");
        TextField userName = new TextField();
        userName.setPromptText("Enter User Name");
        userName.setText("swapnil@7709 ");                // delete
        PasswordField password = new PasswordField();
        password.setPromptText("Enter Password");
        password.setText("123");                            // delete
        Button loginButton = new Button("Login");
        Label messageLable = new Label("");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String user = userName.getText();
                String pass = password.getText();
                loggedInCustomer = LogIn.customerLogin(user, pass);
                if (loggedInCustomer != null) {

                    messageLable.setText("Login SuccessFull");
                    bodyPane.getChildren().clear();
                    signInButton.setVisible(false);
                    signOutButton.setVisible(true);
                    bodyPane.getChildren().addAll(afterLogInPage());
                    stationListButton.setVisible(false);
                    customerRecordButton.setVisible(true);
                    signUpButtonInHeadder.setVisible(false);
                } else {
                    messageLable.setText("Login Unsuccessful");
                }
            }
        });
        GridPane loginPane = new GridPane();
        loginPane.setTranslateY(50);
        loginPane.setTranslateX(50);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.add(userLabel, 0, 2);
        loginPane.add(userName, 1, 2);
        loginPane.add(passLabel, 0, 3);
        loginPane.add(password, 1, 3);
        loginPane.add(loginButton, 1, 4);
        loginPane.add(messageLable, 1, 5);
        clearRecord.setVisible(false);
        messageLable.setFont(fontForMessages);
        userLabel.setFont(fontForLabel);
        passLabel.setFont(fontForLabel);
        userName.setFont(fontForText);
        password.setFont(fontForText);
        loginButton.setFont(fontForButton);

        return loginPane;
    }

    private Pane afterLogInPage() {
        Button getTicket = new Button("Print Ticket");
        Label directDestinationLabel = new Label("Enter Source and Destination");
        Label sourceLabel = new Label("Enter Source Station Code:");
        Label destLabel = new Label("Enter Destination Station Code:");
        TextField sourceText = new TextField();
        sourceText.setPromptText("Enter Source Id");
        TextField destText = new TextField();
        destText.setPromptText("Enter Destination Id");
        Button findRouteButton = new Button("Find Path");
        Text path = new Text();
        path.setWrappingWidth(200);
        GridPane loginPane = new GridPane();
        final int[] minDistance = {0};

        findRouteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int source = Integer.parseInt(sourceText.getText());
                int destination = Integer.parseInt(destText.getText());
                if (loggedInCustomer != null) {
                    minDistance[0] = Shortestpath.srcToDest(loggedInCustomer.getId(), source, destination);
                    String printString = Shortestpath.sendString;
                    printString += "\n\n" + "Minimum Distance : " + minDistance[0] + " Kms\n" + "Minimum Cost : Rs" + minDistance[0] * 3;
                    path.setText(printString);
                    System.out.println(minDistance[0]);
                    getTicket.setVisible(true);
                }
            }
        });
        getTicket.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    int source = Integer.parseInt(sourceText.getText());
                    int destination = Integer.parseInt(destText.getText());
                    PrintTicket.printTicket(loggedInCustomer, source, destination, minDistance);
                    getTicket.setVisible(false);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        GridPane afterLogInPane = new GridPane();
        getTicket.setVisible(false);
        afterLogInPane.setTranslateY(50);
        afterLogInPane.setTranslateX(300);
        afterLogInPane.setVgap(10);
        afterLogInPane.setHgap(10);
        bodyPane.getChildren().add(stationName.getAllStations());
        afterLogInPane.add(directDestinationLabel, 1, 1);
        directDestinationLabel.setFont(fontForLabel);
        afterLogInPane.add(sourceLabel, 1, 2);
        sourceLabel.setFont(fontForLabel);
        afterLogInPane.add(sourceText, 2, 2);
        sourceText.setFont(fontForText);
        afterLogInPane.add(destLabel, 1, 3);
        destLabel.setFont(fontForLabel);
        afterLogInPane.add(destText, 2, 3);
        destText.setFont(fontForText);
        afterLogInPane.add(findRouteButton, 2, 4);
        findRouteButton.setFont(fontForButton);
        afterLogInPane.add(getTicket, 2, 5);
        getTicket.setFont(fontForButton);
        afterLogInPane.add(path, 1, 5);
        clearRecord.setVisible(false);
        return afterLogInPane;
    }

    private Pane recordPage() {
        Label totalDist = new Label("Total Distance Covered :");
        Label totalFair = new Label("Total Fair :");
        sumOfDist.setText(Record.getTotalDist(loggedInCustomer) + " Kms");
        sumOfFair.setText("Rs. " + (Record.getTotalFair(loggedInCustomer)));
        bodyPane.getChildren().add(Record.getRecordOfCustomer(loggedInCustomer));

        GridPane recordPane = new GridPane();
        recordPane.setTranslateY(50);
        recordPane.setTranslateX(370);
        recordPane.setVgap(10);
        recordPane.setHgap(10);
        recordPane.add(totalDist, 0, 0);
        totalDist.setFont(fontForLabel);
        recordPane.add(sumOfDist, 1, 0);
        recordPane.add(totalFair, 0, 1);
        totalFair.setFont(fontForLabel);
        recordPane.add(sumOfFair, 1, 1);
        return recordPane;
    }

    private void showText(String message) {
        Text path = new Text();
        path.setText(message);
    }

    private Pane createContent() {
        Pane root = new Pane();
        root.setPrefSize(width, height + headerLine);
        bodyPane = new Pane();
        headder = new Pane();
        headder.setPrefSize(width, headerLine);
        bodyPane.setPrefSize(width, height);
        bodyPane.setTranslateY(headerLine);
        headder.getChildren().addAll(headerBar());
        bodyPane.getChildren().addAll(signUpPage());

        root.getChildren().addAll(
                headder,
                bodyPane
        );
        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(UrbanMetro.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        scene.setFill(Color.AQUA);
        stage.setTitle("UrbanMetro");
        bodyPane.setStyle("-fx-background-color: rgb(172, 221, 222)");
        headder.setStyle("-fx-background-color: rgb(102, 207, 210)");

        stage.setScene(scene);
        stage.setMinHeight(250);
        stage.setMinWidth(500);
        stage.setMaxHeight(500);
        stage.setMaxWidth(1000);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}