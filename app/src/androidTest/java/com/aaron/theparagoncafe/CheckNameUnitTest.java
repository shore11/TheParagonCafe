package com.aaron.theparagoncafe;

/**
 * Created by Cameron on 6/7/2017.
 */

public class CheckNameUnitTest {
    public void checkName() {
        Food test = new Food("Cheeseburger");

        assert(test.getName() == "Cheeseburger");
    }
}
