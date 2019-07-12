package com.kevin;

import java.util.List;

public class Test {
    private Solution solution = new Solution();

    @org.junit.Test
    public void letterCombinationsTest(){
        String input = "7";
        List<String> res = solution.letterCombinations(input);
        System.out.println(res);
    }
}
