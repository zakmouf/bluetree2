package com.zakmouf.bluetree.util;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.zakmouf.bluetree.BaseTest;

public class RandomizerTest extends BaseTest {

    @Test
    public void testRandomizer() {
	logger.debug(ArrayUtils.toString(new Randomizer(5, 10).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(5, 10).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(5, 10).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(5, 5).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(10, 10).nextBasket()));
	logger.debug(ArrayUtils.toString(new Randomizer(20, 20).nextBasket()));
    }

}
