package demo;

import com.sohu.idcenter.IdWorker;


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
    }
}
