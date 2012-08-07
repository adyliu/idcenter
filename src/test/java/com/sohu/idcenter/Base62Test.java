package com.sohu.idcenter;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * @author adyliu (imxylz@gmail.com)
 * @since 2012-8-7
 */
public class Base62Test {

    /**
     * Test method for {@link com.sohu.idcenter.Base62#encode(long)}.
     */
    @Test
    public void testEncode() {
        assertEquals("1IwymnQs", Base62.encode(6050648952832L));
    }

    /**
     * Test method for {@link com.sohu.idcenter.Base62#decode(java.lang.String)}.
     */
    @Test
    public void testDecode() {
        assertEquals(6050648952832L, Base62.decode("1IwymnQs"));
    }

}
