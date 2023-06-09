package org.example.rbac.service.impl;

import lombok.AllArgsConstructor;
import org.example.rbac.dao.SysUserDao;
import org.example.rbac.entity.SysUserEntity;
import org.example.rbac.service.SysUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 我们的账号密码是存储在数据库里面的，SpringSecurity为我们提供了UserDetailsService接口，
 * 只需要实现 UserDetailsService接口
 *
 * @author
 **/
@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final SysUserDetailsService sysUserDetailsService;
    private final SysUserDao sysUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserEntity userEntity = sysUserDao.getByUsername(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return sysUserDetailsService.getUserDetails(userEntity);
    }
}
