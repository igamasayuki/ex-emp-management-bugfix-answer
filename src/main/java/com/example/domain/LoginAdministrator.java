package com.example.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 管理者のログイン情報を格納するエンティティ.
 *
 * @author igamasayuki
 */
public class LoginAdministrator implements UserDetails {

    /**
     * 管理者情報
     */
    private final Administrator administrator;
    /**
     * 権限情報
     */
    private final Collection<? extends GrantedAuthority> authorityList;

    /**
     * 通常の管理者情報に加え、認可用ロールを設定する.
     *
     * @param administrator 管理者情報
     * @param authorityList 権限情報が入ったリスト
     */
    public LoginAdministrator(Administrator administrator, Collection<? extends GrantedAuthority> authorityList) {
        this.administrator = administrator;
        this.authorityList = authorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return administrator.getPassword();
    }

    @Override
    public String getUsername() {
        return administrator.getMailAddress();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 必要に応じてロジックを実装
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 必要に応じてロジックを実装
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 必要に応じてロジックを実装
    }

    @Override
    public boolean isEnabled() {
        return true; // 必要に応じてロジックを実装
    }

    public Administrator getAdministrator() {
        return administrator;
    }
}
