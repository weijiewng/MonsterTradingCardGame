package com.company.server;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ServerApplication {
    Response handleRequest(Request request) throws JsonProcessingException;
}
