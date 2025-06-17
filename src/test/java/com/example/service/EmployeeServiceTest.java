package com.example.service;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private static Employee employee1;
    private static Employee employee2;

    @BeforeAll
    static void setUp() {
        employee1 = new Employee();
        employee1.setId(1);
        employee1.setName("山田太郎");

        employee2 = new Employee();
        employee2.setId(2);
        employee2.setName("鈴木一郎");
    }

    @Test
    void showList_正常系_従業員リストが取得できるか() {
        // Arrange(準備)
        List<Employee> expectedList = Arrays.asList(employee1, employee2);
        when(employeeRepository.findAll()).thenReturn(expectedList);

        // Act(実行)
        List<Employee> actualList = employeeService.showList();

        // Assert(検証)
        assertThat(actualList).isNotEmpty();
        assertThat(actualList).hasSize(2);
        assertThat(actualList.get(0).getName()).isEqualTo("山田太郎");
        assertThat(actualList.get(1).getName()).isEqualTo("鈴木一郎");
        verify(employeeRepository).findAll();
    }

    @Test
    void showDetail_正常系_指定したIDの従業員が取得できるか() {
        // Arrange(準備)
        Employee expected = new Employee();
        expected.setId(1);
        expected.setName("山田太郎");

        when(employeeRepository.load(1)).thenReturn(employee1);

        // Act(実行)
        Employee result = employeeService.showDetail(1);

        // Assert(検証)
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(expected.getId());
        assertThat(result.getName()).isEqualTo(expected.getName());
        verify(employeeRepository).load(1);
    }
}