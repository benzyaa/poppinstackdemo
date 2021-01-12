const loadMoreRows = (pageNo,rowPerPage)=>{
    var rowToReturn = [];
    var pageNoMultiple = rowPerPage*pageNo;
    for(var i=0;i<rowPerPage;i++){
        rowToReturn.push({
            rowNumber:(i+1)+pageNoMultiple
        });
    }
    return rowToReturn;
}

const appendRows = (rows,elementForAppending)=>{
    for(var i=0;i<rows.length;i++){
        var rowData = rows[i];
        var rowHtml = `
            <div 
                class="row-of-infinite-scroll" 
                id="row-number-${rowData["rowNumber"]}"
            >
                <span id="row-number-label">Row Number : ${rowData["rowNumber"]}</span>
                <br />
                <span id="row-number-text">This is the row in the infinite Scroll.</span>
            </div>
        `;
        elementForAppending.append(rowHtml);
    }
}

$(()=>{
    // 1. get element for enable infinite scroll.
        //1.1 get element of component which contains infinite scroll component.
    const infiniteScrollOuterObj = $("#infinite-scroll-outer-component");
    
        //1.2 get infinite scroll element.
    const infiniteScrollObj = $("#infinite-scroll-component",infiniteScrollOuterObj);

    // 2. set height (in pixel)  of outer scroll element;
    infiniteScrollOuterObj.height(500);
    
    //3. initial variable of infinite scroll element height and container height;
    var infiniteScrollObjOuterHeight =  infiniteScrollObj.outerHeight();
    var infiniteScrollContainerHeight =  infiniteScrollOuterObj.height();
    var containerAndOuterDiff = infiniteScrollObjOuterHeight - infiniteScrollContainerHeight;

    /*4. set interval to observe enabletoload variable is true or false. If true, will
    load more rows and set new height of infinite scroll and container */
    var currentPage = 0;
    var enableToLoad = true;
    setInterval(()=>{
        if(enableToLoad){
            loadedRows = loadMoreRows(currentPage,30);
            appendRows(loadedRows,infiniteScrollObj);
            infiniteScrollObjOuterHeight =  infiniteScrollObj.outerHeight();
            infiniteScrollContainerHeight =  infiniteScrollOuterObj.height();
            containerAndOuterDiff = infiniteScrollObjOuterHeight - infiniteScrollContainerHeight
            enableToLoad = false;
        }
    },1000);

    /* 5. set scroll event listener to infinite scroll container. Current page will be
        increased and enable to load will set to true to load more rows.
    */
    infiniteScrollOuterObj.on("scroll",()=>{
        var scrollTop = infiniteScrollOuterObj.scrollTop();
        if(scrollTop >= containerAndOuterDiff){
            enableToLoad = true;
            currentPage = currentPage+1;
        }        
    });
});