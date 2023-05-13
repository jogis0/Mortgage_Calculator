package com.example.busto_skaiciuokle;

import csvExport.CSVHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable{
    @FXML
    private TableView<MonthlyData> table;
    @FXML
    private TableColumn<MonthlyData, Integer> monthColumn;
    @FXML
    private TableColumn<MonthlyData, Double> paymentColumn;
    @FXML
    private TableColumn<MonthlyData, Double> interestColumn;
    @FXML
    private TableColumn<MonthlyData, Double> loanBalanceColumn;
    @FXML
    private TableColumn<MonthlyData, Double> paidAmountColumn;
    @FXML
    private ComboBox<String> filterBox;
    @FXML
    private Button saveButton;

    ObservableList<MonthlyData> list;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));
        interestColumn.setCellValueFactory(new PropertyValueFactory<>("interestAmount"));
        loanBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("loanBalance"));
        paidAmountColumn.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
    }
    public void setTable(ObservableList<MonthlyData> list){
        this.list = list;

        filterBox.getItems().add("Visi");
        filterBox.setValue("Visi");

        //Adding choices to ComboBox
        int rowSize = list.size();
        for(int i = 1; i < rowSize; i += 5){
            if(i + 4 > rowSize){
                filterBox.getItems().add(Integer.toString(i) + "-" + Integer.toString(rowSize));
            }else{
                filterBox.getItems().add(Integer.toString(i) + "-" + Integer.toString(i + 4));
            }
        }

        table.setItems(list);
    }
    public void filterEvent(){
        String boxSelection = filterBox.getValue();
        ObservableList<MonthlyData> subList = list;

        if(boxSelection.equals("Visi")){
            table.setItems(subList);
        }else{
            int lowerBound = Integer.parseInt(boxSelection.split("-")[0]);
            int upperBound = Integer.parseInt(boxSelection.split("-")[1]);
            table.setItems(subList.filtered(row -> row.getMonth() >= lowerBound && row.getMonth() <= upperBound));
        }

    }
    public void exportData() throws IOException {
        CSVHandler csvHandler = new CSVHandler(list);
        csvHandler.exportCSV();
    }
}
