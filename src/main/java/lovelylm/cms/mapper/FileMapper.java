package lovelylm.cms.mapper;

import lovelylm.cms.model.FileDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author lovelylm
 */
@Repository
public interface FileMapper extends BaseMapper<FileDO> {

    FileDO selectByMd5(@Param("md5") String md5);

    int selectCountByMd5(@Param("md5") String md5);
}
