package com.ec.instructor.core.security.crypto;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Colin.Z.Chen
 * @description
 * @time 2017/12/3.
 */
public class TestSaltedHashProvider {
    @Test
    public void encryptIsNull() {
        String target = null;
        val p1 = new SaltedHashProvider();
        assertEquals("", p1.encrypt(target));
    }

    @Test
    public void encryptIsEmpty() {
        String target = "";
        val p1 = new SaltedHashProvider();
        assertEquals("", p1.encrypt(target));
    }

    @Test
    public void encryptIsBlank() {
        String target = " ";
        val p1 = new SaltedHashProvider();
        assertEquals("", p1.encrypt(target));
    }

    @Test
    public void encryptIsSingleCharacter() {
        String target = "C";
        val p1 = new SaltedHashProvider();
        assertNotNull(p1.encrypt(target));
    }

    @Test
    public void encryptResultFixedLengthIs44(){
        String target = "C";
        val p1 = new SaltedHashProvider();
        String result = p1.encrypt(target);

        String target1 = "ddddddddd";
        val p2 = new SaltedHashProvider("UTF-16");
        String result2 = p2.encrypt(target1);

        assertEquals(result.length(),result2.length());
    }
}
