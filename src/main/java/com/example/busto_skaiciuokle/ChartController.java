package com.example.busto_skaiciuokle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;
public class ChartController implements Initializable {
    @FXML
    private LineChart<?, ?> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    XYChart.Series paymentSeries = new XYChart.Series();
    XYChart.Series interestSeries = new XYChart.Series();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paymentSeries.setName("Monthly payment");
        interestSeries.setName("Monthly interest");
        lineChart.getData().addAll(paymentSeries, interestSeries);
    }

    public void setChart(ObservableList<MonthlyData> list) {
        for(int i = 0; i < list.size(); ++i){
            paymentSeries.getData().add(new XYChart.Data(Integer.toString(i + 1), (int)list.get(i).getPayment()));
            interestSeries.getData().add(new XYChart.Data(Integer.toString(i + 1), (int)list.get(i).getInterestAmount()));
        }
    }
}
