package com.company.game.controller;

import com.company.server.http.ContentType;
import com.company.server.http.HttpStatus;
import com.company.server.Request;
import com.company.server.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Controller {

    public abstract Response handleRequest(Request request) throws JsonProcessingException;

    protected Response json(Object object) {
        ObjectMapper mapper = new ObjectMapper();

        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return response(HttpStatus.OK, ContentType.JSON, json);
    }

    protected <T> T toObject(String json, Class<T> c) {
        ObjectMapper mapper = new ObjectMapper();
        T object = null;
        try {
            object = mapper.readValue(json, c);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return object;
    }

    protected Response response(HttpStatus status, ContentType contentType, String content) {
        Response response = new Response();
        response.setStatus(status);
        response.setContentType(contentType);
        response.setContent(content);
        return response;
    }
}
