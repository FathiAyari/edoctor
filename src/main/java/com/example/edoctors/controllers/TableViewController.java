package com.example.edoctors.controllers;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.example.edoctors.App;
import com.example.edoctors.helpers.DbConnect;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.example.edoctors.models.Meet;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import com.example.edoctors.models.Patient;
import org.w3c.dom.Text;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class TableViewController implements Initializable {
    @FXML
    private TextField matriculeMeet;
    @FXML
    private DatePicker dateMeet;
    @FXML
    private TableColumn<Meet, String> idPatientMeetCol;
    @FXML
    private TableColumn<Meet, String> idMeetCol;
    @FXML
    private TableColumn<Meet, String> nameMeetCol;
    @FXML
    private TableColumn<Meet, String> lastNameMeetCol;

    @FXML
    private TableColumn<Meet, String> dateMeetCol;
    @FXML
    private TableView<Meet> meetTable;
    @FXML
    private TableColumn<Meet, String> editMeetCol;
    @FXML
    private Label alertText;
    @FXML
    private Label alertTextMeet;
    @FXML
    private TextField nameField;

    @FXML
    ToggleGroup right;
    @FXML
    private TextField lastNameField;
    @FXML
    private DatePicker birthDateField;

    @FXML
    private TextField phoneField;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> idCol;
    @FXML
    private TableColumn<Patient, String> nameCol;
    @FXML
    private TableColumn<Patient, String> lastNameCol;
    @FXML
    private TableColumn<Patient, LocalDate> birthCol;
    @FXML
    private TableColumn<Patient, String> phoneCol;
    @FXML
    private TableColumn<Patient, String> sexeCol;


    @FXML
    private TableColumn<Patient, String> editCol;
    String query = null;
    String verifQuery = null;
    String queryMeet = null;
    int idToUpdate;
    boolean insert = true;
    boolean insertMeet = true;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatementMeet = null;
    ResultSet resultSet = null;
    ResultSet resultSetMeet = null;
    Patient patient = null;
    Meet meet = null;

    ObservableList<Patient> patients = FXCollections.observableArrayList();
    ObservableList<Meet> meets = FXCollections.observableArrayList();
    private boolean first;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDate();
    }


    @FXML
    private void refreshTable() {
        connection = DbConnect.getConnect();
        patients.clear();
        meets.clear();

        try {


            query = "SELECT * FROM `patients` ORDER BY `id` DESC";

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                patients.add(new Patient(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("last_name").toUpperCase(),
                        resultSet.getDate("birth_date"),
                        resultSet.getString("phone"),
                        resultSet.getString("sexe").toUpperCase()));
                patientTable.setItems(patients);




            }

            queryMeet = "SELECT * FROM `meets` WHERE `date_meet`>='"+ LocalDate.now() +"' ORDER BY `date_meet` ASC";
            System.out.println(java.time.LocalDate.now());
            preparedStatementMeet = connection.prepareStatement(queryMeet);
            resultSetMeet = preparedStatementMeet.executeQuery();
            while (resultSetMeet.next()) {
                meets.add(new Meet(
                        resultSetMeet.getInt("id"),
                        resultSetMeet.getInt("id_patient"),
                        resultSetMeet.getString("name_meet"),
                        resultSetMeet.getString("last_name_meet").toUpperCase(),
                        resultSetMeet.getDate("date_meet")
                ));

                meetTable.setItems(meets);


            }
        } catch (SQLException ex) {
            System.out.println("error here" + ex);
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }


    @FXML
    void loadDate() {
        int index = 0;
        refreshTable();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        birthCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        sexeCol.setCellValueFactory(new PropertyValueFactory<>("sexe"));


        int finalIndex = index;
        Callback<TableColumn<Patient, String>, TableCell<Patient, String>> cellFoctory = (TableColumn<Patient, String> param) -> {
            // make cell containing buttons
            final TableCell<Patient, String> cell = new TableCell<Patient, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button btn1 = new Button();
                        Button btn2 = new Button();
                        btn1.setText("Effacer");
                        btn2.setText("Modifier");
                        btn1.setOnMouseClicked(e -> {

                            try {

                                queryMeet="SELECT * FROM `meets` WHERE id_patient="+patients.get(finalIndex).getId();
                                connection = DbConnect.getConnect();
                                preparedStatementMeet = connection.prepareStatement(queryMeet);
                                resultSetMeet = preparedStatementMeet.executeQuery();

                                if(resultSetMeet.first()){
                                    queryMeet = "DELETE FROM `meets` WHERE id_patient  =" + patients.get(finalIndex).getId();
                                    connection = DbConnect.getConnect();
                                    preparedStatement = connection.prepareStatement(queryMeet);
                                    preparedStatement.execute();

                                }
                                query = "DELETE FROM `patients` WHERE id  =" + patients.get(finalIndex).getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();



                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }


                        });
                        btn2.setOnMouseClicked((MouseEvent event) -> {
                            insert = false;
                            idToUpdate = patients.get(finalIndex).getId();
                            nameField.setText(patients.get(finalIndex).getName());
                            lastNameField.setText(patients.get(finalIndex).getLastName());
                            birthDateField.setValue(convertToLocalDateViaMilisecond(patients.get(finalIndex).getBirthDate()));
                            phoneField.setText(patients.get(finalIndex).getPhoneNumber());

                        });


                        HBox managebtn = new HBox(btn1, btn2);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(btn1, new Insets(2, 2, 0, 3));
                        HBox.setMargin(btn2, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };

        editCol.setCellFactory(cellFoctory);
        patientTable.setItems(patients);


        idMeetCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idPatientMeetCol.setCellValueFactory(new PropertyValueFactory<>("idPatient"));
        nameMeetCol.setCellValueFactory(new PropertyValueFactory<>("nameMeet"));
        lastNameMeetCol.setCellValueFactory(new PropertyValueFactory<>("lastNameMeet"));
        dateMeetCol.setCellValueFactory(new PropertyValueFactory<>("dateMeet"));
        Callback<TableColumn<Meet, String>, TableCell<Meet, String>> cellFoctory2 = (TableColumn<Meet, String> param) -> {
            // make cell containing buttons
            final TableCell<Meet, String> cell = new TableCell<Meet, String>() {
                @Override
                public void updateItem(String item, boolean empty) {

                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button btnMeet = new Button();

                        btnMeet.setText("Effacer");

                        btnMeet.setOnMouseClicked(e -> {

                            try {


                                query = "DELETE FROM `meets` WHERE id  =" + meets.get(finalIndex).getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();



                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }


                        });



                        HBox managebtn = new HBox(btnMeet);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(btnMeet, new Insets(2, 2, 0, 3));


                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };

        editMeetCol.setCellFactory(cellFoctory2);














        meetTable.setItems(meets);
    }

    @FXML
    void clearFields() {
        nameField.setText("");
        lastNameField.setText("");
        phoneField.setText("");
        birthDateField.setValue(null);
    }

    @FXML
    void clearFieldsMeet() {
        matriculeMeet.setText("");

        dateMeet.setValue(null);
    }

    @FXML
    void addPatient() throws IOException {
        RadioButton selectedRadioButton = (RadioButton) right.getSelectedToggle();
        System.out.println(nameField.getText().isEmpty());
        String alertMessage = "";
        if (nameField.getText().isEmpty()) {
            alertMessage = "Nom de patient obligatoire";
        } else if (lastNameField.getText().isEmpty()) {
            alertMessage = "Prénom de patient obligatoire";
        } else if (phoneField.getText().isEmpty()) {
            alertMessage = "GSM de patient obligatoire";

        } else if (selectedRadioButton == null) {
            alertMessage = "Sexe de patient obligatoire";
        } else if (birthDateField.getValue() == null) {
            alertMessage = "Date de naissance de patient obligatoire";
        }

        if (!alertMessage.isEmpty()) {
            alertMessage += " X ";
            alertText.setText(alertMessage);


        } else {

            try {
                connection = DbConnect.getConnect();
                patients.clear();
                meets.clear();
                if (insert) {
                    query = "INSERT INTO `patients`( `name`, `last_name`, `birth_date`, `sexe`,`phone`) VALUES (?,?,?,?,?)";

                } else {
                    query = "UPDATE `patients` SET "
                            + "`name`=?,"
                            + "`last_name`=?,"
                            + "`birth_date`=?,"
                            + "`sexe`=?,"
                            + "`phone`= ? WHERE id = '" + idToUpdate + "'";
                }
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nameField.getText());
                preparedStatement.setString(2, lastNameField.getText());
                preparedStatement.setString(3, String.valueOf(birthDateField.getValue()));
                preparedStatement.setString(4, selectedRadioButton.getText());
                preparedStatement.setString(5, phoneField.getText());
                preparedStatement.execute();
                insert = true;
                clearFields();

                alertText.setText("");
                refreshTable();
            } catch (SQLException ex) {
                System.out.println("error here" + ex);
                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void addMeet() {
        String alertMessage = "";
        if (matriculeMeet.getText().isEmpty()) {
            alertMessage = "Matricule de patient obligatoire";
        } else if (!matriculeMeet.getText().matches("\\d+")) {
            alertMessage = "Matricule incorrecte";
        } else if (dateMeet.getValue() == null) {
            alertMessage = "Date de rendez vous obligatoire";

        }
        if (!alertMessage.isEmpty()) {
            alertMessage += " X ";
            alertTextMeet.setText(alertMessage);


        } else {
            try {
                connection = DbConnect.getConnect();
                meets.clear();

                query = "INSERT INTO `meets`( `id_patient`, `name_meet`, `last_name_meet`, `date_meet`) VALUES (?,?,?,?)";
                verifQuery = "SELECT  * FROM `patients` WHERE id=" +  Integer.parseInt(matriculeMeet.getText());
                preparedStatement = connection.prepareStatement(verifQuery);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.first()) {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, Integer.parseInt(matriculeMeet.getText()));
                    preparedStatement.setString(2, resultSet.getString("name"));
                    preparedStatement.setString(3, resultSet.getString("last_name"));
                    preparedStatement.setString(4, String.valueOf(dateMeet.getValue()));


                    preparedStatement.execute();

                    clearFieldsMeet();

                    alertText.setText("");
                    refreshTable();
                } else {
                    alertTextMeet.setText("Pas de patient avec cette matricule X");
                }


            } catch (SQLException ex) {
                System.out.println("error here" + ex);
                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    void removeAlert() {
        alertText.setText("");
    }

    @FXML
    void removeAlertMeet() {
        alertTextMeet.setText("");
    }

    public static LocalDate convert(Date date) {
        return date.toInstant()
                .atZone(ZoneId.of("UTC"))
                .toLocalDate();
    }

    public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}