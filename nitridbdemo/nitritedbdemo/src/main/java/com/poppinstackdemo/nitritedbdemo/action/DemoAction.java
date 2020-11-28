package com.poppinstackdemo.nitritedbdemo.action;

import com.poppinstackdemo.nitritedbdemo.model.Company;
import org.dizitart.no2.*;
import org.dizitart.no2.filters.Filters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DemoAction {

    private List<Company> initialCompanyList(){
        List<Company> companyList = new LinkedList<>();

        //Company 1
        Company company1 = new Company();
        company1.setId("001");
        company1.setName("High Level Contruction Co, Ltd.");
        company1.setEmployeeCount(4000);
        company1.setAveageIncome(200000);
        List<String> company1BranchList = new ArrayList<>();
        company1BranchList.add("Hanoi");
        company1BranchList.add("Cairo");
        company1BranchList.add("Dubai");
        company1BranchList.add("Wuhan");
        company1BranchList.add("Moscow");
        company1.setBranches(company1BranchList);
        companyList.add(company1);

        //Company 2
        Company company2 = new Company();
        company2.setId("002");
        company2.setName("Everywhere Transport Co, Ltd.");
        company2.setEmployeeCount(2000);
        company2.setAveageIncome(600000);
        List<String> company2BranchList = new ArrayList<>();
        company2BranchList.add("Tokyo");
        company2BranchList.add("Dubai");
        company2BranchList.add("Bangkok");
        company2BranchList.add("Osaka");
        company2.setBranches(company2BranchList);
        companyList.add(company2);

        //Company 3
        Company company3 = new Company();
        company3.setId("003");
        company3.setName("Merry Foodland Co, Ltd.");
        company3.setEmployeeCount(800);
        company3.setAveageIncome(140000);
        List<String> company3BranchList = new ArrayList<>();
        company3BranchList.add("MerryLand");
        company3BranchList.add("California");
        company3BranchList.add("Dubai");
        company3BranchList.add("Tokyo");
        company3BranchList.add("Hanoi");
        company3.setBranches(company3BranchList);
        companyList.add(company3);

        //Company 4
        Company company4 = new Company();
        company4.setId("004");
        company4.setName("Excursion high end agency Co, Ltd.");
        company4.setEmployeeCount(400);
        company4.setAveageIncome(90000);
        List<String> company4BranchList = new ArrayList<>();
        company4BranchList.add("Yangon");
        company4BranchList.add("California");
        company4BranchList.add("Seoul");
        company4BranchList.add("Tokyo");
        company4BranchList.add("London");
        company4.setBranches(company4BranchList);
        companyList.add(company4);

        //Company 5
        Company company5 = new Company();
        company5.setId("005");
        company5.setName("Rich Resident estate Co, Ltd.");
        company5.setEmployeeCount(600);
        company5.setAveageIncome(800000);
        List<String> company5BranchList = new ArrayList<>();
        company5BranchList.add("Tokyo");
        company5BranchList.add("London");
        company5BranchList.add("HongKong");
        company5BranchList.add("Mumbai");
        company5BranchList.add("Berlin");
        company5.setBranches(company4BranchList);
        companyList.add(company5);

        //Company 5
        Company company6 = new Company();
        company6.setId("006");
        company6.setName("Engagement Telecommunication Co, Ltd.");
        company6.setEmployeeCount(500);
        company6.setAveageIncome(5000000);
        List<String> company6BranchList = new ArrayList<>();
        company6BranchList.add("Bangkok");
        company6BranchList.add("Singapore");
        company6BranchList.add("Dubai");
        company6BranchList.add("Tokyo");
        company6BranchList.add("PanomPenh");
        company6.setBranches(company6BranchList);
        companyList.add(company6);
        return companyList;
    }

    private static final String COMPANY_DB_FILEPATH = "/tmp/company.db";
    private static final String COMPANY_COLLECTION_NAME = "company";

    public void doDemo(){

        //1. Create Or Open Database and nitrite instance will be created.
        NitriteBuilder nitriteBuilder = Nitrite.builder();
        Nitrite nitriteDb = nitriteBuilder.compressed().filePath(COMPANY_DB_FILEPATH).openOrCreate();

        //2. Get Collection
        NitriteCollection nitriteCollection = nitriteDb.getCollection(COMPANY_COLLECTION_NAME);

        //3. Insert Data to NitriteDB
        List<Company> demoCompanyList = this.initialCompanyList();
        System.out.println("############ Start To Insert Data");
        for(Company company : demoCompanyList){
            Document companyNitriteDocument = new Document();
            companyNitriteDocument.put("id",company.getId());
            companyNitriteDocument.put("name",company.getName());
            companyNitriteDocument.put("employeeCount",company.getEmployeeCount());
            companyNitriteDocument.put("averageIncome",company.getAveageIncome());
            companyNitriteDocument.put("branches",company.getBranches());
            System.out.println("Data to Insert : "+companyNitriteDocument);
            WriteResult nitriteInsertResult = nitriteCollection.insert(companyNitriteDocument);
            Iterator<NitriteId> insertResultIterator = nitriteInsertResult.iterator();
            while(insertResultIterator.hasNext()){
                NitriteId insertedId = insertResultIterator.next();
                long insertedIdLong = insertedId.getIdValue();
                System.out.println("Inserted Id : "+insertedIdLong);
            }
        }
        System.out.println("############ End To Insert Data");

        //4. find by criteria

        //4.1 And criteria
        System.out.println("############ Start To Search With And Criteria");
        Filter andFilter = Filters.and(
                Filters.eq("id","006"),
                Filters.eq("name","Engagement Telecommunication Co, Ltd."));
        System.out.println("AND Criteria : "+andFilter);
        Cursor andFindCursor = nitriteCollection.find(andFilter);
        Iterator<Document> andFindCursorResultIterator = andFindCursor.iterator();
        while(andFindCursorResultIterator.hasNext()){
            Document andFindResult = andFindCursorResultIterator.next();
            System.out.println("Result : "+andFindResult);
        }
        System.out.println("############ End To Search With And Criteria");


        //4.2 Or criteria
        System.out.println("############ Start To Search With OR Criteria");
        Filter orFilter = Filters.or(
                Filters.eq("id","006"),
                Filters.eq("name","Excursion high end agency Co, Ltd."));
        System.out.println("OR Criteria : "+orFilter);
        Cursor orFindCursor = nitriteCollection.find(orFilter);
        Iterator<Document> orFindCursorResultIterator = orFindCursor.iterator();
        while(orFindCursorResultIterator.hasNext()){
            Document orFindResult = orFindCursorResultIterator.next();
            System.out.println("Result : "+orFindResult);
        }
        System.out.println("############ End To Search With OR Criteria");


        //4.3 Or criteria
        System.out.println("############ Start To Search With IN Criteria");
        Filter inFilter = Filters.in("id","001","004","005","006");
        System.out.println("In Criteria : "+inFilter);
        Cursor inFindCursor = nitriteCollection.find(inFilter);
        Iterator<Document> inFindCursorResultIterator = inFindCursor.iterator();
        while(inFindCursorResultIterator.hasNext()){
            Document inFindResult = inFindCursorResultIterator.next();
            System.out.println("Result : "+inFindResult);
        }
        System.out.println("############ End To Search With IN Criteria");

        //4.4 ElementMatch
        System.out.println("############ Start To Search With ElementMatch Criteria");
        Filter elemMatchFilter = Filters.elemMatch("branches",Filters.eq("$","Bangkok"));
        System.out.println("ElementMatch(1) Criteria : "+elemMatchFilter);
        Cursor elemMatchCursor = nitriteCollection.find(elemMatchFilter);
        Iterator<Document> elematchFindCursorResultIterator = elemMatchCursor.iterator();
        while(elematchFindCursorResultIterator.hasNext()){
            Document elematchFindResult = elematchFindCursorResultIterator.next();
            System.out.println("Result : "+elematchFindResult);
        }
        System.out.println("############ End To Search With ElementMatch Criteria");

        //4.5 GreatherOrEqual
        System.out.println("############ Start To Search With GreatherOrEqual Criteria");
        Filter gteFilter = Filters.gte("employeeCount",1000);
        System.out.println("GreatherOrEqual Criteria : "+gteFilter);
        Cursor gteCursor = nitriteCollection.find(gteFilter);
        Iterator<Document> gteFindCursorResultIterator = gteCursor.iterator();
        while(gteFindCursorResultIterator.hasNext()){
            Document gteFindResult = gteFindCursorResultIterator.next();
            System.out.println("Result : "+gteFindResult);
        }
        System.out.println("############ End To Search With GreatherOrEqual Criteria");


        //4.6 LessThanOrEqual
        System.out.println("############ Start To Search With LessThanOrEqual Criteria");
        Filter lteFilter = Filters.lte("averageIncome",100000);
        System.out.println("LessThanOrEqual Criteria : "+lteFilter);
        Cursor lteCursor = nitriteCollection.find(lteFilter);
        Iterator<Document> lteFindCursorResultIterator = lteCursor.iterator();
        while(lteFindCursorResultIterator.hasNext()){
            Document lteFindResult = lteFindCursorResultIterator.next();
            System.out.println("Result : "+lteFindResult);
        }
        System.out.println("############ End To Search With LessThanOrEqual Criteria");

        //6. Update Data
        System.out.println("############ Start To Update Data");
        Filter filterToUpdate = Filters.eq("id","005");
        System.out.println("Remove Data Criteria : "+filterToUpdate);

        Document documentToUpdate = new Document();
        documentToUpdate.put("name","Super Exclusive Resident Inc.");

        WriteResult nitriteUpdateResult = nitriteCollection.update(filterToUpdate,documentToUpdate);
        Iterator<NitriteId> updateResultIterator = nitriteUpdateResult.iterator();
        while(updateResultIterator.hasNext()){
            NitriteId updatedId = updateResultIterator.next();
            long updatedIdLong = updatedId.getIdValue();
            System.out.println("Updated Id : "+updatedIdLong);
        }
        System.out.println("----- Data after Updated");

        Cursor dataAfterUpdatedCursor = nitriteCollection.find();
        Iterator<Document> dataAfterUpdatedIterator = dataAfterUpdatedCursor.iterator();
        while(dataAfterUpdatedIterator.hasNext()){
            Document dataAfterUpdatedResult = dataAfterUpdatedIterator.next();
            System.out.println("Result : "+dataAfterUpdatedResult);
        }
        System.out.println("############  End To Update Data");


        //7. Remove Data
        System.out.println("############ Start To Remove Data");
        Filter filterToRemove = Filters.eq("id","006");
        System.out.println("Remove Data Criteria : "+filterToRemove);

        WriteResult nitriteRemoveResult = nitriteCollection.remove(filterToRemove);
        Iterator<NitriteId> removeResultIterator = nitriteRemoveResult.iterator();
        while(removeResultIterator.hasNext()){
            NitriteId removedId = removeResultIterator.next();
            long removedIdLong = removedId.getIdValue();
            System.out.println("Removed Id : "+removedIdLong);
        }
        System.out.println("----- Data after removed");

        Cursor dataAfterRemovedCursor = nitriteCollection.find();
        Iterator<Document> dataAfterRemovedIterator = dataAfterRemovedCursor.iterator();
        while(dataAfterRemovedIterator.hasNext()){
            Document dataAfterRemovedResult = dataAfterRemovedIterator.next();
            System.out.println("Result : "+dataAfterRemovedResult);
        }
        System.out.println("############  End To Remove Data");

        nitriteCollection.close();
        nitriteDb.close();
    }
}