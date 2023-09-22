import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Map;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Crypto Prices");

        GridPane grid = Utils.createGrid();
        Map<String, Label> cryptoLabels = Utils.createCryptoPriceLabels();

        Utils.addLabelsToGrid(cryptoLabels, grid);

        double width = 300;
        double height = 250;

        StackPane root = new StackPane();

        Rectangle background = Utils.createBackgroundRectangleWithAnimation(width, height);

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
}