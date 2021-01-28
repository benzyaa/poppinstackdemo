package com.poppinstackdemo.springresponsedemo.controller;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ResponseController {

    @ResponseBody
    @GetMapping(value = "getresponsefilewithresponsebody",produces = {"application/zip"})
    public byte[] getBinaryResponseWithResponseBody(HttpServletResponse response) throws Exception {
        response.setHeader("content-disposition", "inline; filename=response.zip");
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()){
            this.createCompressedZipFile(outputStream);
            outputStream.flush();
            return outputStream.toByteArray();
        }
    }

    @GetMapping("getresponsefilewithoutputstream")
    private void getBinaryResponseWithOutputStream(HttpServletResponse response,OutputStream outputStream) throws Exception {
        response.setContentType("application/zip");
        response.setHeader("content-disposition", "inline; filename=response.zip");
        try(OutputStream outp = outputStream){
            this.createCompressedZipFile(outp);
        }
    }

    private void createCompressedZipFile(OutputStream outputStream) throws Exception {
        try(BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(bufferedOutputStream)){
            List<byte[]> byteArrayListToWrite = new ArrayList<byte[]>();
            byteArrayListToWrite.add("THIS IS DATA TO TEST WHRITE ZIP WITH BYTE ARRAY 1".getBytes());
            byteArrayListToWrite.add("THIS IS DATA TO TEST WHRITE ZIP WITH BYTE ARRAY 2".getBytes());
            byteArrayListToWrite.add("THIS IS DATA TO TEST WHRITE ZIP WITH BYTE ARRAY 3".getBytes());
            int i = 1;
            for(byte[] dataToWrite: byteArrayListToWrite){
                ZipArchiveEntry zEntry = new ZipArchiveEntry("file_"+i+".txt"); // create zip entry and specific file name.
                zEntry.setSize(dataToWrite.length); //set size of data.
                zipArchiveOutputStream.putArchiveEntry(zEntry); //add entry.
                zipArchiveOutputStream.write(dataToWrite);
                zipArchiveOutputStream.closeArchiveEntry();
                i++;
            }
            zipArchiveOutputStream.flush();
        }
    }

}
