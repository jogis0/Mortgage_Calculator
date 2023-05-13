package com.example.busto_skaiciuokle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.*;

public class Controller implements Initializable {
    @FXML
    private TextField sumField, interestField, yearField, monthField, fPeriodField, fInterestField;
    @FXML
    private ChoiceBox<String> repaymentChoiceBox;
    @FXML
    private Button calculateButton;
    private final String[] repaymentMethods = {"Anuiteto", "Linijinis"};
    public double sum, period, interest, fPeriod, fInterest;
    //fInterest and fPeriod - deferred interest and period
    public void calculate(ActionEvent event) throws IOException {
        calculateButton.setDefaultButton(true);
        sum = Double.parseDouble(sumField.getText());
        interest = Double.parseDouble(interestField.getText()) / 1200; //12 * 100
        period = Double.parseDouble(yearField.getText()) * 12 + Double.parseDouble(monthField.getText());
        fPeriod = Double.parseDouble(fPeriodField.getText());
        fInterest = Double.parseDouble(fInterestField.getText()) / 1200;

        ObservableList<MonthlyData> list = createList(sum, period, interest, repaymentChoiceBox.getValue(), fPeriod, fInterest);

        //Table stage

        Parent root;
        Stage tableStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("table-view.fxml"));
        root = fxmlLoader.load();

        TableController tableController = fxmlLoader.getController();
        tableController.setTable(list);

        Scene tableScene = new Scene(root);
        tableStage.setTitle("Lentele");
        tableStage.setScene(tableScene);
        tableStage.show();

        //Offset window
        double x, y;
        x = tableStage.getX() + 100;
        y = tableStage.getY() + 100;
        tableStage.setX(x);
        tableStage.setY(y);

        //Chart stage

        Stage chartStage = new Stage();

        fxmlLoader = new FXMLLoader(getClass().getResource("chart-view.fxml"));
        root = fxmlLoader.load();

        ChartController chartController = fxmlLoader.getController();
        chartController.setChart(list);

        Scene chartScene = new Scene(root);
        chartStage.setTitle("Grafikas");
        chartStage.setScene(chartScene);
        chartStage.show();

        //Offset window
        x += 100;
        y += 100;
        chartStage.setX(x);
        chartStage.setY(y);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        repaymentChoiceBox.getItems().addAll(repaymentMethods);
    }

    private ObservableList<MonthlyData> createList(double sum, double period, double interest, String methodChoice, double fPeriod, double fInterest){
        ObservableList<MonthlyData> list = FXCollections.observableArrayList();

        double P; //P = monthly payment
        double outstandingBalance = 0.0;
        double paid = 0.0;
        double tempSum = sum * pow(1 + fInterest, fPeriod); //The loan sum after adding deferred interest

        switch(methodChoice){
            case "Anuiteto":
                P = (tempSum * interest) / (1 - pow((1 + interest), (-1) * period));
                outstandingBalance = P * period;


                for(int i = 1; i <= fPeriod; ++i){
                    list.add(new MonthlyData(i, 0.0, 0.0, (double) round(outstandingBalance * 100) / 100, 0.0));
                }

                for(int i = (int) fPeriod + 1; i <= period + fPeriod; ++i){
                    paid += P;
                    list.add(new MonthlyData(i, (double) round(P * 100) / 100,
                            (double) round(outstandingBalance * interest * 100) / 100,
                            (double) round((outstandingBalance - P) * 100) / 100, (double) round(paid * 100) / 100));
                    outstandingBalance -= P;
                }
                break;
            case "Linijinis":
                //For calculating the loan sum + interest
                for(int i = 0; i < period; ++i){
                    P = tempSum / period + (tempSum * i / period) * interest;
                    outstandingBalance += P;
                }

                for(int i = 1; i <= fPeriod; ++i){
                    list.add(new MonthlyData(i, 0.0, 0.0, (double) round(outstandingBalance * 100) / 100, 0.0));
                }

                for(int i = (int) period - 1, j = (int) fPeriod + 1; i >= 0; --i, ++j){
                    P = tempSum / period + (tempSum * i / period) * interest;
                    outstandingBalance -= P;
                    paid += P;
                    list.add(new MonthlyData(j, (double) round(P * 100) / 100,
                            (double) round(abs(outstandingBalance * interest) * 100) / 100,
                            (double) round(abs(outstandingBalance) * 100) / 100, (double) round(paid * 100) / 100));
                }
                break;
        }
        return list;
    }
}