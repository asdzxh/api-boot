package org.example.rbac.event;
import lombok.AllArgsConstructor;
import org.example.common.constant.Constant;
import org.example.rbac.enums.LoginOperationEnum;
import org.example.rbac.service.SysLogLoginService;
import org.example.security.user.UserDetail;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;


/**
 * 认证事件处理
 *
 * @author
 **/
@Component
@AllArgsConstructor
public class AuthenticationEvents {
    private final SysLogLoginService sysLogLoginService;

    /**
     * 认证成功事件处理
     *
     * @param event 事件对象
     */
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        // 获取用户信息
        UserDetail user = (UserDetail) event.getAuthentication().getPrincipal();
        // 保存登录成功日志
        sysLogLoginService.save(user.getUsername(), Constant.SUCCESS, LoginOperationEnum.LOGIN_SUCCESS.getValue());
    }

    /**
     * 认证失败事件处理
     *
     * @param event 事件对象
     */
    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent event) {
        // 获取用户名
        String username = (String) event.getAuthentication().getPrincipal();
        // 保存登录失败日志
        sysLogLoginService.save(username, Constant.FAIL, LoginOperationEnum.ACCOUNT_FAIL.getValue());
    }
}
