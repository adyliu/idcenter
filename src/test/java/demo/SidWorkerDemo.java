package demo;

import com.sohu.idcenter.SidWorker;

/**
 * @author adyliu (imxylz@gmail.com)
 * @since 2016-06-28
 */
public class SidWorkerDemo {
    public static void main(String[] args) {
        long st = System.currentTimeMillis();
        final int max = 100000;
        for (int i = 0; i < max; i++) {
            SidWorker.nextSid();
        }
        long et = System.currentTimeMillis();
        System.out.println(1000 * max / (et - st) + "/s");

    }
}
