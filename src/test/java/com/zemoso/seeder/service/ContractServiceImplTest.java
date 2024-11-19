package com.zemoso.seeder.service;

import com.zemoso.seeder.dto.ContractDto;
import com.zemoso.seeder.dto.ContractResponse;
import com.zemoso.seeder.entity.Contract;
import com.zemoso.seeder.entity.User;
import com.zemoso.seeder.repository.ContractRepository;
import com.zemoso.seeder.service.impl.ContractServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ContractServiceImplTest {
    @Mock
    private UserService userService;

    @Mock
    private ContractRepository contractRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ContractServiceImpl contractService;

    private User user;
    private Contract contract;
    private ContractDto contractDto;

    @BeforeEach
    public void setUp() {
        // Sample data for user
        user = new User();
        user.setId(1L);
        user.setAvailableCredit(10000d);

        // Sample data for contract
        contractDto = new ContractDto();
        contractDto.setName("Test Contract");
        contractDto.setTotalAvailable(1200d);
        contractDto.setRate(5d);
        contractDto.setTermLength(12);

        contract = new Contract();
        contract.setId(1L);
        contract.setName("Test Contract");
        contract.setTotalAvailable(1200d);
        contract.setRate(5d);
        contract.setTermLength(12);
        contract.setPerPayment(105d); // Expected value after calculation
    }

    @Test
    void testFindAllContracts_whenUserFoundAndContractsExist() {
        // Arrange
        long userId = 1L;
        List<Contract> contracts = new ArrayList<>();
        contracts.add(contract); // Add mock contract to the list
        when(userService.getById(userId)).thenReturn(user);
        when(contractRepository.findAllByTotalAvailableLessThan(user.getAvailableCredit())).thenReturn(contracts);
        when(modelMapper.map(contract, ContractResponse.class)).thenReturn(new ContractResponse());

        // Act
        List<ContractResponse> result = contractService.findAllContracts(userId);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userService, times(1)).getById(userId); // Ensure userService.getById() was called once
        verify(contractRepository, times(1)).findAllByTotalAvailableLessThan(user.getAvailableCredit());
    }

    @Test
    void testFindAllContracts_whenNoContractsFound() {
        // Arrange
        long userId = 1L;
        List<Contract> contracts = new ArrayList<>(); // Empty list of contracts
        when(userService.getById(userId)).thenReturn(user);
        when(contractRepository.findAllByTotalAvailableLessThan(user.getAvailableCredit())).thenReturn(contracts);

        // Act
        List<ContractResponse> result = contractService.findAllContracts(userId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userService, times(1)).getById(userId); // Ensure userService.getById() was called once
        verify(contractRepository, times(1)).findAllByTotalAvailableLessThan(user.getAvailableCredit());
    }

    @Test
    void testCreateNewContract() {
        // Arrange
        when(modelMapper.map(contractDto, Contract.class)).thenReturn(contract);
        when(contractRepository.save(contract)).thenReturn(contract); // Mock the save method

        // Act
        Contract result = contractService.createNewContract(contractDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test Contract", result.getName());
        assertEquals(Double.valueOf(105d), result.getPerPayment());
        verify(contractRepository, times(1)).save(contract); // Verify save is called once
    }
}
