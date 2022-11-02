package com.example.productlist.services;

import com.example.productlist.api.response.ResponseStatus;
import com.example.productlist.api.response.AllListsResponseDTO;
import com.example.productlist.api.request.ListRequestDTO;
import com.example.productlist.api.response.OneListResponseDTO;
import com.example.productlist.entities.ListEntity;
import com.example.productlist.entities.ProductEntity;
import com.example.productlist.mappers.ProductMapper;
import com.example.productlist.repositories.ListRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListService {
    private final ListRepository listRepository;
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    public ListService(ListRepository listRepository) {
        this.listRepository = listRepository;
    }

    public AllListsResponseDTO getAll(){
        List<ListEntity> list = listRepository.findAll();
        AllListsResponseDTO allListsResponseDTO = new AllListsResponseDTO();
        int sum = 0;
        List<OneListResponseDTO> oneListResponseDTOList = new ArrayList<>();
        for (ListEntity l : list) {
            OneListResponseDTO oneListResponseDTO = new OneListResponseDTO();
            oneListResponseDTO.setName(l.getName());
            oneListResponseDTO.setProducts(mapper.productDTOList(l.getLists()));
            for (ProductEntity p : l.getLists()) {
                sum += p.getKcal();
            }
            oneListResponseDTOList.add(oneListResponseDTO);
        }
        allListsResponseDTO.setList(oneListResponseDTOList);
        allListsResponseDTO.setSumKcal(sum);

        return allListsResponseDTO;
    }

    public ResponseStatus saveNewList(ListRequestDTO listRequestDTO) {
        ResponseStatus<String> responseStatus = new ResponseStatus<>();
        if (listRequestDTO == null || listRequestDTO.getName().isEmpty()) {
            responseStatus.setStatus("ERROR");
            responseStatus.setAnswer("Нет данных для записи");
            return responseStatus;
        }

        ListEntity newList = new ListEntity();
        newList.setName(listRequestDTO.getName());

        ListEntity save = listRepository.save(newList);
        responseStatus.setStatus("SUCCESS");
        responseStatus.setAnswer("List добавлен с ID " + save.getId());
        return responseStatus;
    }

}
