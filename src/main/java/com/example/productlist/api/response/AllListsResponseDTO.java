package com.example.productlist.api.response;

import com.example.productlist.api.response.OneListResponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class AllListsResponseDTO {

    private List<OneListResponseDTO> list;
    private int sumKcal;

}
