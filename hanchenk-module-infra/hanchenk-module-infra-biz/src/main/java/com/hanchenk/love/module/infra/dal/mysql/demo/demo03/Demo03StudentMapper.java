package com.hanchenk.love.module.infra.dal.mysql.demo.demo03;

import com.hanchenk.love.framework.common.pojo.PageResult;
import com.hanchenk.love.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.hanchenk.love.framework.mybatis.core.mapper.BaseMapperX;
import com.hanchenk.love.module.infra.dal.dataobject.demo.demo03.Demo03StudentDO;
import org.apache.ibatis.annotations.Mapper;
import com.hanchenk.love.module.infra.controller.admin.demo.demo03.vo.*;

/**
 * 学生 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface Demo03StudentMapper extends BaseMapperX<Demo03StudentDO> {

    default PageResult<Demo03StudentDO> selectPage(Demo03StudentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<Demo03StudentDO>()
                .likeIfPresent(Demo03StudentDO::getName, reqVO.getName())
                .eqIfPresent(Demo03StudentDO::getSex, reqVO.getSex())
                .eqIfPresent(Demo03StudentDO::getDescription, reqVO.getDescription())
                .betweenIfPresent(Demo03StudentDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(Demo03StudentDO::getId));
    }

}