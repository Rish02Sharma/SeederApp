package com.zemoso.seeder.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.zemoso.seeder.dto.CashkickContractDto;
import com.zemoso.seeder.entity.Cashkick;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.entity.UserContract;
import com.zemoso.seeder.repository.UserContractRepository;
import com.zemoso.seeder.service.impl.UserContractServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class UserContractServiceImplTest {

    @Mock
    private UserContractRepository userContractRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserContractServiceImpl userContractService;

    private CashkickContractDto contractRequest;
    private User user;
    private Cashkick cashkick;
    private UserContract userContract;
    private Contract contract;

    @BeforeEach
    public void setUp() {
        user = new User(1L, "Rishabh Sharma", "rishabh@example.com", "password123", 1000d);

        cashkick = new Cashkick(
                1L,
                user,
                "Cashkick 1",
                Cashkick.STATUS.PENDING,
                1000d,
                500d,
                500d,
                LocalDate.now().plusDays(1).plusMonths(12),
                LocalDate.now().plusDays(1)
        );

        contract = new Contract();
        contract.setId(1L);
        contract.setName("Contract 1");
        contract.setStatus(Contract.STATUS.AVAILABLE);
        contract.setTotalAvailable(1000d);
        contract.setRate(5d);
        contract.setPerPayment(100d);
        contract.setTermLength(12);

        contractRequest = new CashkickContractDto();
        contractRequest.setId(1L);
        contractRequest.setName("Contract 1");
        contractRequest.setType("MONTHLY");
        contractRequest.setTotalAvailable(1000d);
        contractRequest.setPaymentAvailed(500d);
        contractRequest.setPerPayment(100d);
        contractRequest.setRate(5d);
        contractRequest.setTermLength(12);
    }

    @Test
    void testAddUserContract() {
        when(modelMapper.map(contractRequest, Contract.class)).thenReturn(contract);

        when(userContractRepository.save(any(UserContract.class))).thenReturn(userContract);

        userContractService.addUserContract(contractRequest, user, cashkick);

        verify(userContractRepository, times(1)).save(any(UserContract.class));
    }

    @Test
    void testFindAllByCashkickId() {
        when(userContractRepository.findAllByCashkickId(1L)).thenReturn(Arrays.asList(userContract));

        List<UserContract> result = userContractService.findAllByCashkickId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userContract, result.get(0));

        verify(userContractRepository, times(1)).findAllByCashkickId(1L);
    }
}

