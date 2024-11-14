package com.zemoso.seeder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zemoso.seeder.dto.*;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.service.CashkickService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CashkickControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CashkickService cashkickService;

    @BeforeEach
    void setUp() {
        reset(cashkickService);
    }

    @Test
    void createNewCashkick_ShouldReturnCashkickDetails_WhenNewCashkickAdded() throws Exception {
        CashkickRequestDto requestDto = new CashkickRequestDto();
        requestDto.setName("My new Cashkick");
        requestDto.setUserId(1L);
        List<CashkickContractDto> list = new ArrayList<>();
        CashkickContractDto c = new CashkickContractDto();
        c.setId(200L);
        c.setPerPayment(12000.25);
        c.setName("Contract 1");
        c.setRate(12.0);
        c.setType("MONTHLY");
        c.setTotalAvailable(126722.64);
        c.setTermLength(12);
        list.add(c);
        requestDto.setContractData(list);

        CashkickResponseDto cashkick = new CashkickResponseDto();
        cashkick.setId(1L);
        cashkick.setName("My new Cashkick");
        cashkick.setStatus(Cashkick.STATUS.PENDING);
        cashkick.setTotalFinanced(141929.35);
        cashkick.setEndDate(LocalDate.now().plusMonths(12));

        CashkickContractDto contract = new CashkickContractDto();
        contract.setId(200L);
        contract.setPerPayment(12000.25);
        contract.setName("Contract 1");
        contract.setRate(12.0);
        contract.setType("MONTHLY");
        contract.setTotalAvailable(126722.64);
        contract.setTermLength(12);
        cashkick.setContracts(Collections.singletonList(contract));

        when(cashkickService.createNewCashkick(any(CashkickRequestDto.class))).thenReturn(cashkick);

        ResultActions response = mockMvc.perform(post("/cashkick")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.totalFinanced").value(141929.35))
                .andExpect(jsonPath("$.name").value("My new Cashkick"));

    }

    @Test
    void getUsersCashkick_ShouldReturnUserCashkick() throws Exception {
        List<CashkickResponseDto> cashkickResponse = new ArrayList<>();
        CashkickResponseDto cashkick = new CashkickResponseDto();
        cashkick.setId(1L);
        cashkick.setName("My new Cashkick");
        cashkick.setStatus(Cashkick.STATUS.PENDING);
        cashkick.setTotalFinanced(141929.35);
        cashkick.setEndDate(LocalDate.now().plusMonths(12));

        CashkickContractDto contract = new CashkickContractDto();
        contract.setId(200L);
        contract.setPerPayment(12000.25);
        contract.setName("Contract 1");
        contract.setRate(12.0);
        contract.setType("MONTHLY");
        contract.setTotalAvailable(126722.64);
        contract.setTermLength(12);
        cashkick.setContracts(Collections.singletonList(contract));
        cashkickResponse.add(cashkick);

        when(cashkickService.getUsersCashkick(1L)).thenReturn(cashkickResponse);
        ResultActions response = mockMvc.perform(get("/cashkick/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].totalFinanced").value(141929.35))
                .andExpect(jsonPath("$[0].contracts[0].id").value(200L))
                .andExpect(jsonPath("$[0].name").value("My new Cashkick"));

    }

    @Test
    void approveCashkick_ShouldReturnApprovedCashkickDetails_WhenRequestIsMadeForCashkickApproval() throws Exception {
        ApproveCashkickResponse cashkick = new ApproveCashkickResponse();
        cashkick.setId(1L);
        cashkick.setName("My new Cashkick");
        cashkick.setStatus(Cashkick.STATUS.APPROVED);

        when(cashkickService.approveCashkick(1L)).thenReturn(cashkick);

        ResultActions response = mockMvc.perform(post("/cashkick/approve/1"));
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.name").value("My new Cashkick"));

    }
}
