package lovelylm.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lovelylm.cms.bo.GroupPermissionBO;
import lovelylm.cms.dto.admin.DispatchPermissionDTO;
import lovelylm.cms.dto.admin.DispatchPermissionsDTO;
import lovelylm.cms.dto.admin.NewGroupDTO;
import lovelylm.cms.dto.admin.RemovePermissionsDTO;
import lovelylm.cms.dto.admin.ResetPasswordDTO;
import lovelylm.cms.dto.admin.UpdateGroupDTO;
import lovelylm.cms.dto.admin.UpdateUserInfoDTO;
import lovelylm.cms.model.GroupDO;
import lovelylm.cms.model.PermissionDO;
import lovelylm.cms.model.UserDO;

import java.util.List;
import java.util.Map;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
public interface AdminService {

    /**
     * 通过分组id分页获取用户数据
     *
     * @param groupId 分组id
     * @param count   当前页数目
     * @param page    当前分页
     * @return 用户数据
     */
    IPage<UserDO> getUserPageByGroupId(Integer groupId, Integer count, Integer page);

    /**
     * 修改用户密码（重置用户密码）
     *
     * @param id  用户id
     * @param dto 密码信息
     * @return 是否修改成功
     */
    boolean changeUserPassword(Integer id, ResetPasswordDTO dto);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 是否删除成功
     */
    boolean deleteUser(Integer id);

    /**
     * 更新用户信息
     *
     * @param id  用户id
     * @param dto 数据信息
     * @return 是否成功
     */
    boolean updateUserInfo(Integer id, UpdateUserInfoDTO dto);

    /**
     * 分页获取分组数据
     *
     * @param page  当前页
     * @param count 当前页数量
     * @return 分组数据
     */
    IPage<GroupDO> getGroupPage(Integer page, Integer count);

    /**
     * 获得分组数据
     *
     * @param id 分组id
     * @return 分组数据
     */
    GroupPermissionBO getGroup(Integer id);

    /**
     * 新建分组
     *
     * @param dto 分组信息
     * @return 是否成功
     */
    boolean createGroup(NewGroupDTO dto);

    /**
     * 更新分组
     *
     * @param id  分组id
     * @param dto 分组信息
     * @return 是否成功
     */
    boolean updateGroup(Integer id, UpdateGroupDTO dto);

    /**
     * 删除分组
     *
     * @param id 分组id
     * @return 是否成功
     */
    boolean deleteGroup(Integer id);

    /**
     * 分配权限
     *
     * @param dto 数据
     * @return 是否成功
     */
    boolean dispatchPermission(DispatchPermissionDTO dto);

    /**
     * 分配权限
     *
     * @param dto 数据
     * @return 是否成功
     */
    boolean dispatchPermissions(DispatchPermissionsDTO dto);

    /**
     * 删除权限
     *
     * @param dto 数据
     * @return 是否成功
     */
    boolean removePermissions(RemovePermissionsDTO dto);

    /**
     * 获得所有分组信息
     */
    List<GroupDO> getAllGroups();

    /**
     * 获得所有权限信息
     */
    List<PermissionDO> getAllPermissions();

    /**
     * 获得结构化的权限信息
     */
    Map<String, List<PermissionDO>> getAllStructuralPermissions();
}