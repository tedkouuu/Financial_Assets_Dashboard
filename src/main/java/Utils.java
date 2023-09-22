import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static void addLabelsToGrid(Map<String, Label> labels, GridPane grid) {
        int row = 0;
        for (Map.Entry<String, Label> entry : labels.entrySet()) {
            String cryptoName = entry.getKey();
            Label nameLabel = new Label(cryptoName);
            nameLabel.setTextFill(Color.BLUE);
            nameLabel.setOnMouseEntered(event -> nameLabel.setTextFill(Color.RED));
            nameLabel.setOnMouseExited(event -> nameLabel.setTextFill(Color.BLUE));

            grid.add(nameLabel, 0, row);
            grid.add(entry.getValue(), 1, row);
            row++;
        }
    }

    public static GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }


    public static Map<String, Label> createCryptoPriceLabels() {
        Label bitcoinPrice = new Label("0");
        bitcoinPrice.setId("BTC");

        Label ethereumPrice = new Label("0");
        ethereumPrice.setId("ETH");

        Label litecoinPrice = new Label("0");
        litecoinPrice.setId("LTC");

        Label bitcoinCashPrice = new Label("0");
        bitcoinCashPrice.setId("BCH");

        Label ripplePrice = new Label("0");
        ripplePrice.setId("XRP");

        Map<String, Label> cryptoLabels = new HashMap<>();
        cryptoLabels.put("BTC", bitcoinPrice);
        cryptoLabels.put("ETH", ethereumPrice);
        cryptoLabels.put("LTC", litecoinPrice);
        cryptoLabels.put("BCH", bitcoinCashPrice);
        cryptoLabels.put("XRP", ripplePrice);

        return cryptoLabels;
    }


    public static Rectangle createBackgroundRectangleWithAnimation(double width, double height) {
        Rectangle background = new Rectangle(width, height);
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), background, Color.LIGHTGREEN, Color.LIGHTBLUE);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.setAutoReverse(true);
        fillTransition.play();
        return background;
    }
}
