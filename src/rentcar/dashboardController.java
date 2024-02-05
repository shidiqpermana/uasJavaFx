package rentcar;

import com.mysql.cj.xdevapi.Statement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author mshidiqper
 */
public class dashboardController implements Initializable{
    
    @FXML
    private Button availableCars_DeleteBtn;

    @FXML
    private TextField availableCars_brand;

    @FXML
    private Button availableCars_btn;

    @FXML
    private TextField availableCars_carid;

    @FXML
    private Button availableCars_clearBtn;

    @FXML
    private TableColumn<carData, String> availableCars_col_brand;

    @FXML
    private TableColumn<carData, String> availableCars_col_carid;

    @FXML
    private TableColumn<carData, String> availableCars_col_model;

    @FXML
    private TableColumn<carData, String> availableCars_col_price;

    @FXML
    private TableColumn<carData, String> availableCars_col_status;

    @FXML
    private AnchorPane availableCars_form;

    @FXML
    private Button availableCars_insertBtn;

    @FXML
    private TextField availableCars_model;

    @FXML
    private TextField availableCars_price;

    @FXML
    private ComboBox<?> availableCars_status;

    @FXML
    private TableView<carData> availableCars_tableView;

    @FXML
    private Button availableCars_updateBtn;

    @FXML
    private Button home_availableCars;

    @FXML
    private Button home_btn;

    @FXML
    private LineChart<?, ?> home_customerChart;

    @FXML
    private BarChart<?, ?> home_incomeChart;

    @FXML
    private Button home_totalCustomer;

    @FXML
    private Button home_totalIncome;

    @FXML
    private FontAwesomeIcon logoutBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button rentBtn;

    @FXML
    private Button rentCar_btn;

    @FXML
    private TextField rent_amount;

    @FXML
    private Label rent_balance;

    @FXML
    private ComboBox<?> rent_brand;

    @FXML
    private ComboBox<?> rent_carid;

    @FXML
    private TableColumn<carData, String> rent_col_brand;

    @FXML
    private TableColumn<carData, String> rent_col_carid;

    @FXML
    private TableColumn<carData, String> rent_col_model;

    @FXML
    private TableColumn<carData, String> rent_col_price;

    @FXML
    private TableColumn<carData, String> rent_col_status;

    @FXML
    private DatePicker rent_dateRented;

    @FXML
    private DatePicker rent_dateReturn;

    @FXML
    private TextField rent_firstName;

    @FXML
    private AnchorPane rent_form;

    @FXML
    private ComboBox<?> rent_gender;

    @FXML
    private TextField rent_lastName;

    @FXML
    private ComboBox<?> rent_model;

    @FXML
    private TableView<carData> rent_tableView;

    @FXML
    private Label rent_total;

    @FXML
    private Label username;
    
    @FXML
    private AnchorPane home_form;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    
    public void homeAvailableCars() {
    String sql = "SELECT COUNT(id) FROM car WHERE status = 'Available'";
    connect = database.connectDb();
    int countAC = 0;
    try {
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();
        while (result.next()) {
            countAC = result.getInt(1);
        }
        home_availableCars.setText(String.valueOf(countAC));
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    public void homeTotalIncome() {
    String sql = "SELECT SUM(total) AS totalIncome FROM customer";
    double sumIncome = 0;
    connect = database.connectDb();
    try {
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();
        while (result.next()) {
            sumIncome = result.getDouble("totalIncome");
        }
        home_totalIncome.setText("$" + String.format("%.2f", sumIncome));
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public void homeTotalCustomer() {
        String sql = "SELECT COUNT(id) FROM  customer";
        int countTC = 0;
        connect = database.connectDb();
        try {
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();
        while (result.next()) {
            countTC = result.getInt("COUNT(id)");
        }
        home_totalCustomer.setText(String.valueOf(countTC));
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    public void availableCarAdd() {
        String sql = "INSERT INTO car (car_id, brand, model, price, status, date)" + "VALUES(?, ?, ?, ?, ?, ?)";
        connect = database.connectDb();
        try{
            Alert alert;
            if(availableCars_carid.getText().isEmpty()
                    || availableCars_brand.getText().isEmpty()
                    || availableCars_model.getText().isEmpty()
                    || availableCars_status.getSelectionModel().getSelectedItem() == null
                    || availableCars_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error message");
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else 
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, availableCars_carid.getText());
            prepare.setString(2, availableCars_brand.getText());
            prepare.setString(3, availableCars_model.getText());
            prepare.setString(4, availableCars_price.getText());
            prepare.setString(5, (String)availableCars_status.getSelectionModel().getSelectedItem());
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            prepare.setString(6, String.valueOf(sqlDate));
            prepare.executeUpdate();
            alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Information message");
            alert.setContentText("Successfully added");
            alert.showAndWait();
            availableCarShowListData();
            availableCarClear();
        } catch(Exception e) {e.printStackTrace();}
    }
    
    public void availableCarUpdate() {
    String sql = "UPDATE car SET brand = ?, model = ?, status = ?, price = ? WHERE car_id = ?";
    connect = database.connectDb();

    try {
        Alert alert;
        if (availableCars_carid.getText().isEmpty()
                || availableCars_brand.getText().isEmpty()
                || availableCars_model.getText().isEmpty()
                || availableCars_status.getSelectionModel().getSelectedItem() == null
                || availableCars_price.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error message");
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to update Car id? " + availableCars_carid.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, availableCars_brand.getText());
                prepare.setString(2, availableCars_model.getText());
                prepare.setString(3, (String) availableCars_status.getSelectionModel().getSelectedItem());
                prepare.setString(4, availableCars_price.getText());
                prepare.setString(5, availableCars_carid.getText());
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Information message");
                alert.setContentText("Successfully updated");
                alert.showAndWait();
                availableCarShowListData();
                availableCarClear();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public void availableCarDelete() {
    String sql = "DELETE FROM car WHERE car_id = ?";
    connect = database.connectDb();

    try {
        Alert alert;
        if (availableCars_carid.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error message");
            alert.setContentText("Please enter Car ID");
            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to DELETE Car id? " + availableCars_carid.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, availableCars_carid.getText());
                prepare.executeUpdate();

                alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Information message");
                alert.setContentText("Successfully DELETED");
                alert.showAndWait();
                availableCarShowListData();
                availableCarClear();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public void availableCarClear() {
        availableCars_carid.setText("");
        availableCars_brand.setText("");
        availableCars_model.setText("");
        availableCars_price.setText("");
        availableCars_status.getSelectionModel().clearSelection();
    }
    
    private String[] listStatus = {"Available", "Not Available"};
    public void availableCarStatusList() {
        List<String> listS = new ArrayList<>();
        for(String data: listStatus) {
            listS.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listS);
        availableCars_status.setItems(listData);
    }
    
    public ObservableList<carData> availableCarListData() {
        ObservableList<carData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM car";
        connect = database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            carData carD;
            while(result.next()) {
            carD = new carData(result.getInt("car_id")
                    , result.getString("brand")
                    , result.getString("model")
                    , result.getDouble("price")
                    , result.getString("status")
                    , result.getDate("date"));
            listData.add(carD);
        }
        }catch(Exception e) {e.printStackTrace();}
        return listData;
    }
    
    private int countDate;
    public void rentDate() {
        if(rent_carid.getSelectionModel().getSelectedItem() == null
                || rent_brand.getSelectionModel().getSelectedItem() == null
                || rent_model.getSelectionModel().getSelectedItem() == null) {
            Alert alert;
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error message");
            alert.setContentText("Soomething wrong");
            alert.showAndWait();
            rent_dateReturn.setValue(null);
            rent_dateRented.setValue(null);
        } else {
            if (rent_dateReturn.getValue().isAfter(rent_dateRented.getValue())) {
                countDate = rent_dateReturn.getValue().compareTo(rent_dateRented.getValue());
            } else {
                Alert alert;
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error message");
                alert.setContentText("Soomething wrong");
                alert.showAndWait();
                rent_dateReturn.setValue(rent_dateRented.getValue().plusDays(1));
            }
        }
    }
    
    private double totalP;
    
    public void rentDisplayTotal() {
        rentDate();
        double price = 0;
        String sql = "SELECT price, model FROM car WHERE model = '"
                +rent_model.getSelectionModel().getSelectedItem()+"'";
        connect = database.connectDb();
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                price = result.getDouble("price");
            }
            totalP = (price * countDate);
            rent_total.setText("$" + String.valueOf(totalP));
        } catch (Exception e) {e.printStackTrace();}
    }
    
    private String[] genderList = {"Male", "Female", "Others"};
    
    public void rentCarGender() {
        List<String> listG = new ArrayList<>();
        for(String data: genderList) {
            listG.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listG);
        rent_gender.setItems(listData);
    }
    
    public void rentCarId() {
        String sql = "SELECT car_id FROM car WHERE status = 'Available'";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            ObservableList listData = FXCollections.observableArrayList();
            while(result.next()) {
                listData.add(result.getString("car_id"));
            }
            rent_carid.setItems(listData);
            rentCarBrand();
            rentCarModel();
        } catch (Exception e) {e.printStackTrace();}
    }
    
    public void rentCarBrand() {
        String sql = "SELECT * FROM car WHERE car_id = '"
                +rent_carid.getSelectionModel().getSelectedItem()+"'";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            ObservableList listData = FXCollections.observableArrayList();
            while(result.next()) {
                listData.add(result.getString("brand"));
            }
            rent_brand.setItems(listData);
            rentCarModel();
        } catch (Exception e) {e.printStackTrace();}
    }
    
    public void rentCarModel() {
        String sql = "SELECT * FROM car WHERE brand = '"
                +rent_brand.getSelectionModel().getSelectedItem()+"'";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            ObservableList listData = FXCollections.observableArrayList();
            while(result.next()) {
                listData.add(result.getString("model"));
            }
            rent_model.setItems(listData);
            
        } catch (Exception e) {e.printStackTrace();}
    }
    
    public ObservableList<carData> availableCarList;
    
    public void availableCarShowListData() {
        availableCarList = availableCarListData();
        availableCars_col_carid.setCellValueFactory(new PropertyValueFactory<>("carId"));
        availableCars_col_model.setCellValueFactory(new PropertyValueFactory<>("Model"));
        availableCars_col_brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
        availableCars_col_price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        availableCars_col_status.setCellValueFactory(new PropertyValueFactory<>("Status"));
        
        availableCars_tableView.setItems(availableCarList);
    }
    
    public void availableCarSelect() {
        carData carD = availableCars_tableView.getSelectionModel().getSelectedItem();
        int num = availableCars_tableView.getSelectionModel().getSelectedIndex();
        if((num - 1) < 1) {return;}
        availableCars_carid.setText(String.valueOf(carD.getCarId()));
        availableCars_brand.setText(carD.getBrand());
        availableCars_model.setText(carD.getModel());
        availableCars_price.setText(String.valueOf(carD.getPrice()));
    }
    
    public void rentPay() {
    rentCustomerId();
    String sql = "INSERT INTO customer (customer_id, firstName, lastName, gender, car_id, brand, model, total, date_rented, date_return) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection connect = database.connectDb();
         PreparedStatement prepare = connect.prepareStatement(sql)) {

        Alert alert;
        if (rent_firstName.getText().isEmpty()
            || rent_lastName.getText().isEmpty()
            || rent_gender.getSelectionModel().getSelectedItem() == null
            || rent_carid.getSelectionModel().getSelectedItem() == null
            || rent_brand.getSelectionModel().getSelectedItem() == null
            || rent_model.getSelectionModel().getSelectedItem() == null
            || totalP == 0 || rent_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Something wrong");
            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Are you sure?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                prepare.setString(1, String.valueOf(customerId));
                prepare.setString(2, rent_firstName.getText());
                prepare.setString(3, rent_lastName.getText());
                prepare.setString(4, (String) rent_gender.getSelectionModel().getSelectedItem());
                prepare.setString(5, (String) rent_carid.getSelectionModel().getSelectedItem());
                prepare.setString(6, (String) rent_brand.getSelectionModel().getSelectedItem());
                prepare.setString(7, (String) rent_model.getSelectionModel().getSelectedItem());
                prepare.setString(8, String.valueOf(totalP));
                prepare.setString(9, String.valueOf(rent_dateRented.getValue()));
                prepare.setString(10, String.valueOf(rent_dateReturn.getValue()));
                prepare.executeUpdate();

                String updateCar = "UPDATE car SET status = 'Not Available' WHERE car_id = ?";
                try (PreparedStatement updatePrepare = connect.prepareStatement(updateCar)) {
                    updatePrepare.setString(1, (String) rent_carid.getSelectionModel().getSelectedItem());
                    updatePrepare.executeUpdate();
                }

                alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Success");
                alert.showAndWait();
                rentCarShowListData();
                rentClear();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
    public void rentClear() {
        totalP = 0;
        rent_firstName.setText("");
        rent_lastName.setText("");
        rent_gender.getSelectionModel().clearSelection();
        amount = 0;
        balance = 0;
        rent_balance.setText("0");
        rent_total.setText("0");
        rent_amount.setText("");
        rent_carid.getSelectionModel().clearSelection();   
        rent_brand.getSelectionModel().clearSelection();
        rent_model.getSelectionModel().clearSelection();
    }
    
    private int customerId;
    
    public void rentCustomerId() {
        String sql = "SELECT id FROM customer";
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            while (result.next()) {
                customerId = result.getInt("id") + 1;
            }
        } catch (Exception e) {e.printStackTrace();}
    }
    
    private double amount;
    
    private double balance;
    
    public void rentAmount() {
        Alert alert;
        if (totalP == 0 || rent_amount.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invalid");
            alert.showAndWait();
            rent_amount.setText("");
        } else {
            amount = Double.parseDouble(rent_amount.getText());
            if(amount >= totalP) {
                balance = (amount - totalP);
                rent_balance.setText("$" + String.valueOf(balance));
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid");
                alert.showAndWait();
                rent_amount.setText("");
            }
        }
    }
    
    public ObservableList<carData> rentCarListData() {
    ObservableList<carData> listData = FXCollections.observableArrayList();
    String sql = "SELECT * FROM car";
    
    connect = database.connectDb();
    try {
        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();
        carData carD;
        while (result.next()) {
            carD = new carData(
                result.getInt("car_id"),
                result.getString("brand"),
                result.getString("model"),
                result.getDouble("price"),
                result.getString("status"),
                result.getDate("date")
            );
            listData.add(carD);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return listData;
}

    
    private ObservableList<carData> rentCarList;
    
    public void rentCarShowListData() {
    rentCarList = rentCarListData();
    rent_col_carid.setCellValueFactory(new PropertyValueFactory<>("car_id"));
    rent_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
    rent_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
    rent_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    rent_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    rent_tableView.setItems(rentCarList);
} 

    
    
    public void displayUsername() {
        String user = getData.username;
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
    }
    
    public void logout() throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Coonfirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        
        try {
            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                logoutBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {e.printStackTrace();}
    }
    
    public void switchForm(ActionEvent event) {
        if(event.getSource() == home_btn) {
        home_form.setVisible(true);
        availableCars_form.setVisible(false);
        rent_form.setVisible(false);
        homeAvailableCars();
        homeTotalCustomer();
        homeTotalIncome();
    } else if(event.getSource() == availableCars_btn) {
        home_form.setVisible(false);
        availableCars_form.setVisible(true);
        rent_form.setVisible(false);
        availableCarShowListData();
        availableCarStatusList();
    }else if(event.getSource() == rentCar_btn) {
        home_form.setVisible(false);
        availableCars_form.setVisible(false);
        rent_form.setVisible(true);
        rentCarShowListData();
        rentCarId();
        rentCarModel();
        rentCarBrand();
        rentCarGender();
    }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        availableCarShowListData();
        availableCarStatusList();
        rentCarShowListData();
        rentCarId();
        rentCarModel();
        rentCarBrand();
        rentCarGender();
        homeAvailableCars();
        homeTotalCustomer();
        homeTotalIncome();
    }
   
}