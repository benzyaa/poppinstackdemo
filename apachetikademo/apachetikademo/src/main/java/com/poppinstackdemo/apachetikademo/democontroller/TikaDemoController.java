package com.poppinstackdemo.apachetikademo.democontroller;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.detect.Detector;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@RestController
public class TikaDemoController {

    @GetMapping("/")
    @ResponseBody
    private String welcomeIndex() {
        return "Welcome to tika demo project.";
    }

    @GetMapping(value = "/getfiletypefromfileinputstream",produces = "application/json")
    @ResponseBody
    private Map<String,String> getFileTypeFromFileInputStream() throws IOException, TikaException, SAXException {
        Map<String,String> responseMap = new LinkedHashMap<>();
        URL fileUrl = this.getClass().getClassLoader().getResource("testimage.png");
        String fileName = fileUrl.getFile();
        System.out.println("fileName : "+fileName);
        String fileType = this.getFileTypeFromFileName(fileName);
        System.out.println("fileName : "+fileName + " | fieltype : "+fileType);
        responseMap.put(fileName,fileType);
        return responseMap;
    }

    @GetMapping(value = "/getfilemetadatafromfileinputstream",produces = "application/json")
    @ResponseBody
    private Map<String,String> getFileMetadataFromFileInputStream() throws Exception {
        URL fileUrl = this.getClass().getClassLoader().getResource("testimage.png");
        String fileName = fileUrl.getFile();
        Map<String,String> responseMap = this.getMetadataMapFromฺFileName(fileName);
        return responseMap;
    }

    @PostMapping(value = "/getfiletype",produces = "application/json")
    @ResponseBody
    private Map<String,String> getFileType(@RequestParam("file") MultipartFile[] fileData) throws IOException, TikaException, SAXException {
        Map<String,String> responseMap = new LinkedHashMap<>();
        for(MultipartFile file:fileData){
                String fileName = file.getOriginalFilename();
                String fileType = this.getFileTypeFromByteArray(file.getBytes());
                System.out.println("fileName : "+fileName + " | fieltype : "+fileType);
                responseMap.put(fileName,fileType);
        }
        return responseMap;
    }

    @PostMapping(value = "/getmetadata",produces = "application/json")
    @ResponseBody
    private List<Map<String,String>> getMetadata(@RequestParam("file") MultipartFile[] fileData) throws Exception {
        List<Map<String,String>> dataList = new LinkedList<>();
        for(MultipartFile file:fileData){
            Map<String,String> dataMap = this.getMetadataMapFromฺByte(file.getBytes());
            dataList.add(dataMap);
        }
        return dataList;
    }

    // The result is depend on detector implementation.
    private String getFileTypeFromByteArray(byte[] dataByte) throws IOException, TikaException {
        String fileType;
        try(InputStream tikaInputStream = TikaInputStream.get(dataByte)){
            TikaConfig tikaConfig = new TikaConfig();
            Detector detector = tikaConfig.getDetector();
            MediaType mediaType = detector.detect(tikaInputStream,new Metadata());
            fileType = mediaType.getBaseType().toString();
        }
        return fileType;
    }


    private String getFileTypeFromFileName(String filePath) throws IOException, TikaException {
        String fileType;

        try(InputStream fileInputStream = new FileInputStream(filePath);
            InputStream tikaInputStream = TikaInputStream.get(fileInputStream)){
            TikaConfig tikaConfig = new TikaConfig();
            Detector detector = tikaConfig.getDetector();
            MediaType mediaType = detector.detect(tikaInputStream,new Metadata());
            fileType = mediaType.getBaseType().toString();
        }
        return fileType;
    }

    private Map<String,String> getMetadataMapFromฺByte(byte[] dataByte) throws  Exception {
        Map<String,String> metaDataMap = new LinkedHashMap<>();
        Parser parser = new AutoDetectParser();
        try(InputStream inp = TikaInputStream.get(dataByte)) {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();
            Set<MediaType> supportedTypes = parser.getSupportedTypes(context);
            for(MediaType mediaType : supportedTypes){
                System.out.println("Supported types : "+mediaType.getBaseType().toString());
            }
            parser.parse(inp, handler, metadata, context);
            System.out.println("metadata.names.size : "+metadata.names().length);
            for(String name:metadata.names()){
                metaDataMap.put(name,metadata.get(name));
            }
        }
        return metaDataMap;
    }

    private Map<String,String> getMetadataMapFromฺFileName(String filePath) throws  Exception {
        Map<String,String> metaDataMap = new LinkedHashMap<>();
        Parser parser = new AutoDetectParser();
        try(InputStream fileInputStream = new FileInputStream(filePath);
            InputStream inp = TikaInputStream.get(fileInputStream)) {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();
            Set<MediaType> supportedTypes = parser.getSupportedTypes(context);
            for(MediaType mediaType : supportedTypes){
                System.out.println("Supported types : "+mediaType.getBaseType().toString());
            }
            parser.parse(inp, handler, metadata, context);
            System.out.println("metadata.names.size : "+metadata.names().length);
            for(String name:metadata.names()){
                metaDataMap.put(name,metadata.get(name));
            }
        }
        return metaDataMap;
    }
}
