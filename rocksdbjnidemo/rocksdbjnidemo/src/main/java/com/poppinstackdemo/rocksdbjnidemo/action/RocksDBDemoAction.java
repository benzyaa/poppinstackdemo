package com.poppinstackdemo.rocksdbjnidemo.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksIterator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RocksDBDemoAction {
    public List<String> generateEventLogString() throws Exception{
        List<String> eventLogStringList = new LinkedList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,String> event1 = new HashMap<>();
        event1.put("customerId","C0001");
        event1.put("eventType","Login");
        event1.put("eventData","IP:127.0.0.1;OS:Android4.0;Device:Acer");
        String event1String = objectMapper.writeValueAsString(event1);
        eventLogStringList.add(event1String);

        Map<String,String> event2 = new HashMap<>();
        event2.put("customerId","C0001");
        event2.put("eventType","Visit Page");
        event2.put("eventData","Page:productDetail;productId:P001");
        objectMapper.writeValueAsString(event2);
        String event2String = objectMapper.writeValueAsString(event2);
        eventLogStringList.add(event2String);

        Map<String,String> event3 = new HashMap<>();
        event3.put("customerId","C0002");
        event3.put("eventType","Add to cart");
        event3.put("eventData","productId:P001;useDiscount:true");
        objectMapper.writeValueAsString(event3);
        String event3String = objectMapper.writeValueAsString(event3);
        eventLogStringList.add(event3String);

        Map<String,String> event4 = new HashMap<>();
        event4.put("customerId","C0002");
        event4.put("eventType","Confirm Cart");
        event4.put("eventData","confirmTime:1606792964194");
        objectMapper.writeValueAsString(event4);
        String event4String = objectMapper.writeValueAsString(event4);
        eventLogStringList.add(event4String);

        Map<String,String> event5 = new HashMap<>();
        event5.put("customerId","C0003");
        event5.put("eventType","Share product");
        event5.put("eventData","quote:I expect to buy;socialType:0");
        objectMapper.writeValueAsString(event5);
        String event5String = objectMapper.writeValueAsString(event5);
        eventLogStringList.add(event5String);


        Map<String,String> event6 = new HashMap<>();
        event6.put("customerId","C0004");
        event6.put("eventType","Logout");
        event6.put("eventData","logoutTime:1606793112728");
        objectMapper.writeValueAsString(event6);
        String event6String = objectMapper.writeValueAsString(event6);
        eventLogStringList.add(event6String);
        return eventLogStringList;
    }

    public void doDemo(){
        RocksDB rocksDB = null;
        try{
            List<String> sampleDataList = this.generateEventLogString();
            System.out.println("============= SAMPLE DATA ============");
            for(String sampleData : sampleDataList){
                System.out.println("sampleData : "+sampleData);
            }
            System.out.println("======================================");
            RocksDB.loadLibrary(); // Initial JNI Library.
            rocksDB = RocksDB.open("/tmp/customereventlog.db");

            //1. Insert Data to DB.
            int eventSeq = 0;
            for(String sampleData : sampleDataList){
                byte[] dataKeybyte = ("EVENT_SEQ_"+eventSeq).getBytes();
                rocksDB.put(dataKeybyte,sampleData.getBytes());
                eventSeq++;
            }

            //2. Retrieve All Data from Database;
            System.out.println("============= RETRIEVE ALL DATA ============");
            RocksIterator rocksIterator = rocksDB.newIterator();
            rocksIterator.seekToFirst();
            byte[] currentKey;
            byte[] currentValue;
            do {
                currentKey = rocksIterator.key();
                currentValue = rocksIterator.value();
                System.out.println("dataKey : " + new String(currentKey));
                System.out.println("dataValue : " + new String(currentValue));
                rocksIterator.next();
            }while (rocksIterator.isValid());
            System.out.println("============================================");

            //3. Get Data by Key;
            System.out.println("============ GET DATA BY KEY ===============");
            String dataKeyToGet = "EVENT_SEQ_0";
            System.out.println("DATA KEY TO GET : "+dataKeyToGet);
            byte[] dataByteKey = dataKeyToGet.getBytes();
            byte[] dataByteValue = rocksDB.get(dataByteKey);
            System.out.println("DATA Value : "+new String(dataByteValue));
            System.out.println("============================================");


            //4. Remove Data by Key;
            System.out.println("============ REMOVE DATA BY KEY ===============");
            String dataKeyToRemove = "EVENT_SEQ_5";
            System.out.println("DATA KEY TO Remove : "+dataKeyToRemove);
            rocksDB.delete(dataByteKey);
            System.out.println("===============================================");

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(rocksDB != null){
                rocksDB.close();
            }
        }
    }
}