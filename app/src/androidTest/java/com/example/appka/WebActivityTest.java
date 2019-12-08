package com.example.appka;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class WebActivityTest {
    @Rule
    public ActivityTestRule<WebActivity> webActivityRule = new ActivityTestRule<>(WebActivity.class);

    private WebActivity webActivity = null;
    @Before
    public void setUp(){
        webActivity = webActivityRule.getActivity();
    }
//    @Test
//    public void skanujKod() {
//        webActivity.skanujKod("1234");
//    }

    @Test
    public void getBarcodes() {
        webActivity.getBarcodes("test");

    }
    @After
    public void tearDown(){
        webActivity = null;
    }
}