package com.flow.ak.service;

import com.flow.ak.entity.Flow;

import java.util.Map;
/**
 * (Flow)表服务接口
 *
 * @author ak.design 337547038
 * @since 2025-05-22 18:47:48
 */
public interface FlowService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Flow queryById(Integer id);
    
    /**
     * 分页查询
     * @param pages 筛选条件 分页对象
     * @return 查询结果
     */
    Map<String,Object> queryByPage(Map<String,Object> pages);
    /**
     * 新增数据
     *
     * @param flow 实例对象
     * @return 实例对象
     */
    Flow insert(Flow flow);

    /**
     * 修改数据
     *
     * @param flow 实例对象
     * @return 实例对象
     */
    Integer updateById(Flow flow);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String[] id);

}
