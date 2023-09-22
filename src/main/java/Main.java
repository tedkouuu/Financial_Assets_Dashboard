import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Crypto Prices");

        GridPane grid = createGrid();
        Map<String, Label> cryptoLabels = createCryptoPriceLabels();

        addLabelsToGrid(cryptoLabels, grid);

        double width = 300;
        double height = 250;

        StackPane root = new StackPane();

        Rectangle background = createBackgroundRectangleWithAnimation(width, height);

        root.getChildren().add(background);
        root.getChildren().add(grid);

        primaryStage.setScene(new Scene(root, width, height));

        PricesContainer pricesContainer = new PricesContainer();

        PricesUpdater pricesUpdater = new PricesUpdater(pricesContainer);

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (pricesContainer.getLock().tryLock()) {
                    try {
                        Label bitcoinPriceLabel = cryptoLabels.get("BTC");
                        bitcoinPriceLabel.setText(String.valueOf(pricesContainer.getBitcoinPrice()));

                        Label ethereumPriceLabel = cryptoLabels.get("ETH");
                        ethereumPriceLabel.setText(String.valueOf(pricesContainer.getEthereumPrice()));

                        Label litecoinPriceLabel = cryptoLabels.get("LTC");
                        litecoinPriceLabel.setText(String.valueOf(pricesContainer.getLitecoinPrice()));

                        Label bitcoinCashPriceLabel = cryptoLabels.get("BCH");
                        bitcoinCashPriceLabel.setText(String.valueOf(pricesContainer.getBitcoinCashPrice()));

                        Label ripplePriceLabel = cryptoLabels.get("XRP");
                        ripplePriceLabel.setText(String.valueOf(pricesContainer.getRipplePrice()));

                    } finally {
                        pricesContainer.getLock().unlock();
                    }
                }
            }
        };

        animationTimer.start();

        pricesUpdater.start();

        primaryStage.show();
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);
        return grid;
    }

    private Map<String, Label> createCryptoPriceLabels() {
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

    private void addLabelsToGrid(Map<String, Label> labels, GridPane grid) {
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

    private Rectangle createBackgroundRectangleWithAnimation(double width, double height) {
        Rectangle background = new Rectangle(width, height);
        FillTransition fillTransition = new FillTransition(Duration.millis(1000), background, Color.LIGHTGREEN, Color.LIGHTBLUE);
        fillTransition.setCycleCount(Timeline.INDEFINITE);
        fillTransition.setAutoReverse(true);
        fillTransition.play();
        return background;
    }
}