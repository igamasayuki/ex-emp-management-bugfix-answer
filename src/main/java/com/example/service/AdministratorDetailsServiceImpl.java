package com.example.service;

import com.example.domain.Administrator;
import com.example.domain.LoginAdministrator;
import com.example.repository.AdministratorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ログイン後の管理者情報に権限情報を付与するサービスクラス.
 *
 * @author igamasayuki
 */
@Service
public class AdministratorDetailsServiceImpl implements UserDetailsService {
    /**
     * DBから情報を得るためのリポジトリ
     */
    private final AdministratorRepository administratorRepository;

    public AdministratorDetailsServiceImpl(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetailsService#
     * loadUserByUsername(java.lang.String) DBから検索をし、ログイン情報を構成して返す。
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email:" + email);
        Administrator administrator = administratorRepository.findByMailAddress(email);
        if (administrator == null) {
            throw new UsernameNotFoundException("そのEmailは登録されていません。");
        }
        // 権限付与の例
        Collection<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_USER")); // ユーザ権限付与
//		if(administrator.isAdmin()) {
//			authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // 管理者権限付与
//		}
        return new LoginAdministrator(administrator, authorityList);
    }
}
