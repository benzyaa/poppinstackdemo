package com.poppinstackdemo.rocksdbjnidemo;

import com.poppinstackdemo.rocksdbjnidemo.action.RocksDBDemoAction;

public class App
{
    public static void main( String[] args )
    {
        RocksDBDemoAction rocksDBDemoAction = new RocksDBDemoAction();
        rocksDBDemoAction.doDemo();
    }
}
