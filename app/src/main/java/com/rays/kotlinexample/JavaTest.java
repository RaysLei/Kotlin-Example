package com.rays.kotlinexample;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rays on 2017/5/24.
 */
public class JavaTest {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        List<? extends Object> objList = list;


        String[] strings = new String[5];
        Object[] objects = strings;
    }

}
