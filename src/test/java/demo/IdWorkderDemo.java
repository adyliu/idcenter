package demo;

import com.sohu.idcenter.IdWorker;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author adyliu (imxylz@gmail.com)
 * @since 2015-01-19
 */
public class IdWorkderDemo {

    public static void main(String[] args) {

        final long idepo = System.currentTimeMillis() - 3600 * 1000L;
        IdWorker iw = new IdWorker(1, 1, 0, idepo);
        IdWorker iw2 = new IdWorker(idepo);
        for (int i = 0; i < 10; i++) {
            System.out.println(iw.getId() + " -> " + iw2.getId());
        }
        System.out.println(iw);
        System.out.println(iw2);
        long nextId = iw.getId();
        System.out.println(nextId);
        long time = iw.getIdTimestamp(nextId);
        System.out.println(time+" -> "+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(time)));
    }
}
