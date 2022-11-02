package com.example.productlist.api.response;

import lombok.Data;

@Data
public class ResponseStatus<T> {

    private String status;
    private T answer;

}
