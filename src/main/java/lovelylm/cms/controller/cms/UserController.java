package lovelylm.cms.controller.cms;

import io.github.talelin.autoconfigure.exception.NotFoundException;
import io.github.talelin.autoconfigure.exception.ParameterException;
import io.github.talelin.core.annotation.AdminRequired;
import io.github.talelin.core.annotation.LoginRequired;
import io.github.talelin.core.annotation.PermissionModule;
import io.github.talelin.core.annotation.RefreshRequired;
import io.github.talelin.core.token.DoubleJWT;
import io.github.talelin.core.token.Tokens;
import lovelylm.cms.common.LocalUser;
import lovelylm.cms.dto.user.ChangePasswordDTO;
import lovelylm.cms.dto.user.LoginDTO;
import lovelylm.cms.dto.user.RegisterDTO;
import lovelylm.cms.dto.user.UpdateInfoDTO;
import lovelylm.cms.model.GroupDO;
import lovelylm.cms.model.UserDO;
import lovelylm.cms.service.GroupService;
import lovelylm.cms.service.UserIdentityService;
import lovelylm.cms.service.UserService;
import lovelylm.cms.vo.CreatedVO;
import lovelylm.cms.vo.UpdatedVO;
import lovelylm.cms.vo.UserInfoVO;
import lovelylm.cms.vo.UserPermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author lovelylm
 */
@RestController
@RequestMapping("/cms/user")
@PermissionModule(value = "用户")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private DoubleJWT jwt;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @AdminRequired
    public CreatedVO register(@RequestBody @Validated RegisterDTO validator) {
        userService.createUser(validator);
        return new CreatedVO(11);
    }

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    public Tokens login(@RequestBody @Validated LoginDTO validator) {
        UserDO user = userService.getUserByUsername(validator.getUsername());
        if (user == null) {
            throw new NotFoundException(10021);
        }
        boolean valid = userIdentityService.verifyUsernamePassword(
                user.getId(),
                user.getUsername(),
                validator.getPassword());
        if (!valid) {
            throw new ParameterException(10031);
        }
        return jwt.generateTokens(user.getId());
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    @LoginRequired
    public UpdatedVO update(@RequestBody @Validated UpdateInfoDTO validator) {
        userService.updateUserInfo(validator);
        return new UpdatedVO(6);
    }

    /**
     * 修改密码
     */
    @PutMapping("/change_password")
    @LoginRequired
    public UpdatedVO updatePassword(@RequestBody @Validated ChangePasswordDTO validator) {
        userService.changeUserPassword(validator);
        return new UpdatedVO(4);
    }

    /**
     * 刷新令牌
     */
    @GetMapping("/refresh")
    @RefreshRequired
    public Tokens getRefreshToken() {
        UserDO user = LocalUser.getLocalUser();
        return jwt.generateTokens(user.getId());
    }

    /**
     * 查询拥有权限
     */
    @GetMapping("/permissions")
    @LoginRequired
    public UserPermissionVO getPermissions() {
        UserDO user = LocalUser.getLocalUser();
        boolean admin = groupService.checkIsRootByUserId(user.getId());
        List<Map<String, List<Map<String, String>>>> permissions = userService.getStructuralUserPermissions(user.getId());
        UserPermissionVO userPermissions = new UserPermissionVO(user, permissions);
        userPermissions.setAdmin(admin);
        return userPermissions;
    }

    /**
     * 查询自己信息
     */
    @LoginRequired
    @GetMapping("/information")
    public UserInfoVO getInformation() {
        UserDO user = LocalUser.getLocalUser();
        List<GroupDO> groups = groupService.getUserGroupsByUserId(user.getId());
        return new UserInfoVO(user, groups);
    }
}
