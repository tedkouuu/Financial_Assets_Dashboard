import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.locks.Lock;

public class PricesContainer {

    /*

     Two threads are going to access all those prices at the same time,
     so we need to make sure that we don't have any concurrency issues.

     */

    @Getter
    @Setter
    private Lock lock;

    @Setter
    @Getter
    private double bitcoinPrice;

    @Setter
    @Getter
    private double ethereumPrice;

    @Setter
    @Getter
    private double litecoinPrice;

    @Setter
    @Getter
    private double bitcoinCashPrice;

    @Setter
    @Getter
    private double ripplePrice;


}
