package com.example.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 管理者のログイン情報を格納するエンティティ.
 *
 * @author igamasayuki
 */
public class LoginAdministrator extends User {

    /**
     * 管理者情報
     */
    private final Administrator administrator;

    /**
     * 通常の管理者情報に加え、認可用ロールを設定する.
     *
     * @param administrator 管理者情報
     * @param authorityList 権限情報が入ったリスト
     */
    public LoginAdministrator(Administrator administrator, Collection<GrantedAuthority> authorityList) {
        super(administrator.getMailAddress(), administrator.getPassword(), authorityList);
        this.administrator = administrator;
    }

    /**
     * 管理者情報を返します.
     *
     * @return 管理者情報
     */
    public Administrator getAdministrator() {
        return administrator;
    }

}
