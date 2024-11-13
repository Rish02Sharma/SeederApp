package com.zemoso.seeder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoso.seeder.dto.ContractDto;
import com.zemoso.seeder.dto.ContractResponse;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class ContractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContractService contractService;

    @BeforeEach
    void setUp() {
        reset(contractService);
    }

    @Test
    void getAllAvailableContracts_ShouldReturnUserDetails() throws Exception {
        // Create a mock user
        List<ContractResponse> contracts = new ArrayList<>();
        ContractResponse cr = new ContractResponse();
        cr.setId(100L);
        cr.setPerPayment(12000.25);
        cr.setName("Contract 1");
        cr.setRate(12.0);
        cr.setType("MONTHLY");
        cr.setStatus(Contract.STATUS.AVAILABLE);
        cr.setTotalAvailable(126722.64);
        cr.setTermLength(12);
        contracts.add(cr);

        when(contractService.findAllContracts(1L)).thenReturn(contracts);

        ResultActions response = mockMvc.perform(get("/contract/available/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").value(100L))
                .andExpect(jsonPath("$[0].name").value("Contract 1"));
    }

    @Test
    void createContract_ShouldReturnContractDetails_WhenNewContractAdded() throws Exception {
        // Create a mock user
        ContractDto cr = new ContractDto();
        cr.setName("Contract 1");
        cr.setRate(12.0);
        cr.setType("MONTHLY");
        cr.setStatus(Contract.STATUS.AVAILABLE);
        cr.setTotalAvailable(126722.64);
        cr.setTermLength(12);

        Contract c = new Contract();
        c.setId(200L);
        c.setPerPayment(12000.25);
        c.setName("Contract 1");
        c.setRate(12.0);
        c.setType("MONTHLY");
        c.setStatus(Contract.STATUS.AVAILABLE);
        c.setTotalAvailable(126722.64);
        c.setTermLength(12);

        when(contractService.createNewContract(any(ContractDto.class))).thenReturn(c);

        ResultActions response = mockMvc.perform(post("/contract")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cr)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(200L))
                .andExpect(jsonPath("$.totalAvailable").value(126722.64))
                .andExpect(jsonPath("$.name").value("Contract 1"));
    }
}
