package com.rocketseat.redirectUrlShortener;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Main implements RequestHandler<Map<String,Object>,Map<String,Object>> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final S3Client s3Client = S3Client.builder().build();

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {

        String pathParameters = input.get("rawPath").toString();
        String shortUrlCode = pathParameters.replace("/","");

        if(shortUrlCode == null || shortUrlCode.isEmpty()){

            throw new IllegalArgumentException("Invalid Input: 'shortUrlCode' is required.");
        }

        GetObjectRequest getObjectRequest =  GetObjectRequest.builder()
                .bucket("urlshortener-bucket-ryan")
                .key(shortUrlCode + ".json")
                .build();

        InputStream s3ObjectStream;

        try{

            s3ObjectStream = s3Client.getObject(getObjectRequest);

        }catch (Exception exception){

            throw new RuntimeException("Error fetching URL data from S3: " + exception.getMessage(), exception);
        }

        UrlData urlData;

        try{

            urlData = objectMapper.readValue(s3ObjectStream, UrlData.class);

        }catch (Exception exception){

            throw new RuntimeException("Error deserializing URL data: "+ exception.getMessage(),exception);
        }

        long currentTimeInSeconds = System.currentTimeMillis() / 1000;

        Map<String, Object> response = new HashMap<>();

        if (currentTimeInSeconds > urlData.getExpirationTime()){//URL EXPIRED

            response.put("statusCode",410);
            response.put("body","This URL has expired");

        }else {

            response.put("statusCode",302);

            Map<String,String> headers = new HashMap<>();

            headers.put("Location",urlData.getOriginalUrl());

            response.put("headers",headers);
        }

        return response;
    }
}