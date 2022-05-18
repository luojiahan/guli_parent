package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author atguigu
 * @since 2022-05-14
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    // 查询某一天的注册人数
    Integer countRegisterByDay(String day);
}
