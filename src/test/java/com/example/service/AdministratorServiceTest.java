package com.example.service;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AdministratorServiceTest {
    @Mock
    private AdministratorRepository administratorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdministratorService administratorService;

    private static Administrator admin;

    @BeforeAll
    static void setUp() {
        admin = new Administrator();
        admin.setName("Admin");
        admin.setMailAddress("admin@example.com");
        admin.setPassword("password123");
    }

    @Test
    void insert_正常系_管理者登録処理が正常に動作するか() {
        // Arrange(準備)
        doNothing().when(administratorRepository).insert(admin);

        // Act(実行)
        administratorService.insert(admin);

        // Assert(検証)
        verify(administratorRepository).insert(admin);
    }

//    @Test
//    void login_正常系_ログインが正常に処理されるか() {
//        // Arrange(準備)
//        String mailAddress = admin.getMailAddress();
//        String password = admin.getPassword();
//
//        when(administratorRepository.findByMailAddressAndPassward(mailAddress, password))
//                .thenReturn(admin);
//
//        // Act(実行)
//        Administrator actual = administratorService.login(mailAddress, password);
//
//        // Assert(検証)
//        assertThat(actual).isNotNull();
//        assertThat(actual.getId()).isEqualTo(admin.getId());
//        assertThat(actual.getName()).isEqualTo(admin.getName());
//        assertThat(actual.getMailAddress()).isEqualTo(admin.getMailAddress());
//        assertThat(actual.getPassword()).isEqualTo(admin.getPassword());
//        verify(administratorRepository).findByMailAddressAndPassward(mailAddress, password);
//    }
}