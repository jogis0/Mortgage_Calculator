package csvExport;

import com.example.busto_skaiciuokle.MonthlyData;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVHandler {
    ObservableList<MonthlyData> list;
    public CSVHandler(ObservableList<MonthlyData> list) {
        this.list = list;
    }

    public void exportCSV() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Išsaugoti lentelę");
        File file = fileChooser.showSaveDialog(new Stage());

        FileWriter writer = new FileWriter(file);
        for(int i = 0; i < list.size(); ++i){
            writer.write(Integer.toString(list.get(i).getMonth()) + ";" +
                    Double.toString(list.get(i).getPayment()) + ";" +
                    Double.toString(list.get(i).getInterestAmount()) + ";" +
                    Double.toString(list.get(i).getLoanBalance()) + ";" +
                    Double.toString(list.get(i).getPaidAmount()) + "\n");
        }
        writer.close();
    }
}
