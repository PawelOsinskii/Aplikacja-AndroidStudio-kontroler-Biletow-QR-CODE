package com.example.appka;

import org.junit.Test;

import static org.junit.Assert.*;

public class WebActivityTest {

    @Test
    public void skanujKod() {
        WebActivity.skanujKod("1234");
    }

    @Test
    public void getBarcodes() {
        WebActivity.getBarcodes("test");

    }
}