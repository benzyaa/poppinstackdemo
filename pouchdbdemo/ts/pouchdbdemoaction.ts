import PouchDB from 'pouchdb';
import PouchdbFind from 'pouchdb-find';
import {get, set} from 'lodash';

class PouchDBDemoAction {
    async doDemo(){
        //1.Add plugin to PouchDB
        //1.1 Add PouchdbFind plugin for querying.
        PouchDB.plugin(PouchdbFind);
        
        //2.initial database (Create pouchdb instance)
        /*Adapters preferences
            1. memory - In memory database. you have to install memory adapter by npm install pouchdb-adapter-memory
            2. idb - indexDB for browsers.
            3. websql - websql of browsers.
            4. localstorage - localstorage.
            5. cordova-sqlite - for application which developed by Ionic,Cordova,PhoneGap
            */
        const demoDb = new PouchDB("demodb",{
            adapter:'idb'
        });

        //3.insert data by post command
        const insertResult = await demoDb.post({
            'productId':'0001',
            'productName':'Cigarrete',
            'price':20.00,
            'showPrice':40.00,
            'branches':['Sukhumvit','Phra Kanong','Bang na','Bang bo']
        })
        
        /*.then((result:any)=>{
            console.log("result : ",result);
        });*/


        //4.insert multiple data by bulk docs
        const bulkInsertResult = await demoDb.bulkDocs([
            {
                'productId':'0002',
                'productName':'Teacup',
                'price':500.00,
                'showPrice':1000.00,
                'branches':['Sukhumvit','Thra pra','Bang na','Yaowaratch']
            },
            {
                'productId':'0003',
                'productName':'Coffee',
                'price':98.00,
                'showPrice':150.00,
                'branches':['Sukhumvit','Siam','Thong Lor','Yaowaratch']
            },
            {
                'productId':'0004',
                'productName':'Whiskey',
                'price':400.00,
                'showPrice':600.00,
                'branches':['Sukhumvit','Thong Lor','Bang na','Ekkamai']
            }
        ]);

        //5.get all Data
        const getAllDataResult = await demoDb.find({
            selector:{
            }
        });
        console.log("getAllDataResult : ",getAllDataResult);

        //6.query Data by eq criteria
        const getEqDataResult = await demoDb.find({
            selector:{
                productName:{"$eq":"Coffee"}
            }
        });
        console.log("getEqDataResult : ",getEqDataResult);

        //7.query Data by greather than or less than criteria
        const getGteLteDataResult = await demoDb.find({
            selector:{
                price:{"$gte":90,"$lte":400}
            }
        });
        console.log("getGteLteDataResult : ",getGteLteDataResult);

        //8.query Data by elemmatch criteria
        const getElematchResult = await demoDb.find({
            selector:{
                branches:{"$elemMatch":"Bang na"}
            }
        });
        console.log("getElematchResult : ",getElematchResult);

        //8.updateData
        const productToUpdateResult = await demoDb.find({
            selector:{
                productId:{"$eq":"0001"}
            }
        });
        const productToUpdateDocs =  get(productToUpdateResult,"docs");
        for(var docsIndex in productToUpdateDocs){
            var doc = productToUpdateDocs[docsIndex];
            var productName = "Super Ciggarretes";
            set(doc,"productName",productName);
            var updateResult = await demoDb.put(doc);
            console.log("updateResult : ",updateResult);
        }

        const getAllDataAfterUpdateResult = await demoDb.find({
            selector:{
            }
        });
        console.log("getAllDataAfterUpdateResult : ",getAllDataAfterUpdateResult);


         //9.deleteData
         const productToDeleteResult = await demoDb.find({
            selector:{
                productId:{"$eq":"0004"}
            }
        });
        const productToDeleteDocs =  get(productToDeleteResult,"docs");
        for(var deletedDocsIndex in productToDeleteDocs){
            var doc = productToDeleteDocs[deletedDocsIndex];
            set(doc,"_deleted",true);
            var updateResult = await demoDb.put(doc);
            console.log("deleteResult : ",updateResult);
        }

        const getAllDataAfterDeleteResult = await demoDb.find({
            selector:{
            }
        });
        console.log("getAllDataAfterDeleteResult : ",getAllDataAfterDeleteResult);

      
        //10. close DB
        if(demoDb != null){
            await demoDb.close(); //This function is useful when you require to persist data when leave web or mobile application.
            //await demoDb.destroy(); If use destroy function, you do not close database before it.
        }
    }
}
export default PouchDBDemoAction;