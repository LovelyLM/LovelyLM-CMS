package lovelylm.cms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pedro@TaleLin
 * @author Juzi@TaleLin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("lin_log")
@EqualsAndHashCode(callSuper = true)
public class LogDO extends BaseModel implements Serializable {

    private static final long serialVersionUID = -7471474245124682011L;

    private String message;

    private Integer userId;

    private String username;

    private Integer statusCode;

    private String method;

    private String path;

    private String permission;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Date createTime;

}
