package com.flow.ak.dao;

import com.flow.ak.entity.Flow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (Flow)表数据库访问层
 *
 * @author ak.design 337547038
 * @since 2025-05-22 18:47:48
 */
 @Mapper
public interface FlowDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Flow queryById(Integer id);

    /**
     * 查询指定行数据
     *@param flow 筛选条件
     *@param extend 扩展参数
     * @return 对象列表
     */
    List<Map<String,Object>> queryAllByLimit(@Param("query") Flow flow,@Param("extend") Object extend);

    /**
     * 统计总行数
     *
     * @param flow 查询条件
     * @return 总行数
     */
    long count(Flow flow);

    /**
     * 新增数据
     *
     * @param flow 实例对象
     * @return 影响行数
     */
    int insert(Flow flow);

    /**
     * 修改数据
     *
     * @param flow 实例对象
     * @return 影响行数
     */
    int updateById(Flow flow);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String[] id);

}

