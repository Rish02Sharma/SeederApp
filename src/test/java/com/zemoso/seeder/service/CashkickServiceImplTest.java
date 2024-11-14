package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.ApproveCashkickResponse;
import com.zemoso.seeder.dto.CashkickContractDto;
import com.zemoso.seeder.dto.CashkickRequestDto;
import com.zemoso.seeder.dto.CashkickResponseDto;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.entity.UserContract;
import com.zemoso.seeder.repository.CashkickRepository;
import com.zemoso.seeder.service.impl.CashkickServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CashkickServiceImplTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CashkickRepository cashkickRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserContractService userContractService;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private CashkickServiceImpl cashkickService;

    private CashkickRequestDto cashkickRequestDto;
    private Cashkick cashkick;
    private User user;
    private List<CashkickContractDto> contractData;
    private CashkickResponseDto cashkickResponseDto;
    private Contract contract;
    private UserContract userContract;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setAvailableCredit(10000d);

        cashkickRequestDto = new CashkickRequestDto();
        cashkickRequestDto.setUserId(user.getId());
        contractData = new ArrayList<>();
        cashkickRequestDto.setContractData(contractData);

        cashkick = new Cashkick();
        cashkick.setName("New Cashkick");
        cashkick.setId(1L);
        cashkick.setUser(user);
        cashkick.setTotalFinanced(11200d);
        cashkick.setTotalRecieved(10000d);
        cashkick.setTotalOutstanding(11200d);
        cashkick.setStatus(Cashkick.STATUS.PENDING);
        cashkick.setStartDate(LocalDate.now().plusDays(1));
        cashkick.setEndDate(LocalDate.now().plusMonths(12));

        contract = new Contract();
        contract.setId(1L);
        contract.setName("Contract 1");
        contract.setStatus(Contract.STATUS.AVAILABLE);
        contract.setTotalAvailable(1000d);
        contract.setRate(5d);
        contract.setPerPayment(100d);
        contract.setTermLength(12);

        userContract = new UserContract();
        userContract.setCashkickId(cashkick.getId());
        userContract.setContract(contract);
        userContract.setUser(user);
        userContract.setId(1L);

        cashkickResponseDto = new CashkickResponseDto();
        cashkickResponseDto.setId(cashkick.getId());
    }

    @Test
    void testCreateNewCashkick_whenContractDataEmpty() {
        cashkickRequestDto.setContractData(new ArrayList<>());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cashkickService.createNewCashkick(cashkickRequestDto);
        });

        assertEquals("Contract data cant be empty while creating a cashkick", exception.getMessage());
    }

    @Test
    void testCreateNewCashkick_whenValidData() {
        CashkickContractDto contractDto = new CashkickContractDto();
        contractDto.setPaymentAvailed(1000d);
        contractData.add(contractDto);

        when(userService.getById(anyLong())).thenReturn(user);
        when(modelMapper.map(any(CashkickRequestDto.class), eq(Cashkick.class))).thenReturn(cashkick);
        when(cashkickRepository.save(any(Cashkick.class))).thenReturn(cashkick);
        when(modelMapper.map(cashkick, CashkickResponseDto.class)).thenReturn(cashkickResponseDto);

        CashkickResponseDto response = cashkickService.createNewCashkick(cashkickRequestDto);

        assertNotNull(response);
        assertEquals(cashkick.getId(), response.getId());
        verify(cashkickRepository, times(1)).save(cashkick);
    }

    @Test
    void testGetUsersCashkick_whenCashkicksExist() {
        List<Cashkick> cashkicks = new ArrayList<>();
        cashkicks.add(cashkick);

        when(cashkickRepository.findAllByUserId(anyLong())).thenReturn(cashkicks);
        when(modelMapper.map(any(Cashkick.class), eq(CashkickResponseDto.class))).thenReturn(cashkickResponseDto);
        when(userContractService.findAllByCashkickId(anyLong())).thenReturn(List.of(userContract));
        CashkickContractDto cashkickContractDto = mock(CashkickContractDto.class);
        when(modelMapper.map(contract, CashkickContractDto.class)).thenReturn(cashkickContractDto);


        List<CashkickResponseDto> response = cashkickService.getUsersCashkick(1L);

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(cashkick.getId(), response.get(0).getId());
        verify(cashkickRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void testGetUsersCashkick_whenNoCashkicksFound() {
        when(cashkickRepository.findAllByUserId(anyLong())).thenReturn(new ArrayList<>());

        List<CashkickResponseDto> response = cashkickService.getUsersCashkick(1L);

        assertNotNull(response);
        assertTrue(response.isEmpty());
        verify(cashkickRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void testApproveCashkick_whenCashkickExists() {
        ApproveCashkickResponse cr = new ApproveCashkickResponse();
        cr.setId(1L);
        cr.setUserId(1L);
        cr.setName("New Cashkick");
        cr.setStatus(Cashkick.STATUS.APPROVED);

        when(cashkickRepository.findById(anyLong())).thenReturn(Optional.of(cashkick));
        when(cashkickRepository.save(any(Cashkick.class))).thenReturn(cashkick);
        when(modelMapper.map(cashkick, ApproveCashkickResponse.class)).thenReturn(cr);

        ApproveCashkickResponse response = cashkickService.approveCashkick(1L);

        assertNotNull(response);
        assertEquals(cashkick.getId(), response.getId());
        verify(paymentService, times(1)).createInstallmentForCashkick(cashkick);  // Ensure the installment creation
    }
}
