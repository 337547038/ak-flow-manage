package com.flow.ak.service.impl;

import com.alibaba.fastjson2.JSON;
import com.flow.ak.utils.Utils;
import com.flow.ak.entity.FlowDesign;
import com.flow.ak.dao.FlowDesignDao;
import com.flow.ak.service.FlowDesignService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * (FlowDesign)表服务实现类
 *
 * @author ak.design 337547038
 * @since 2025-05-22 20:14:33
 */
@Service("flowDesignService")
public class FlowDesignServiceImpl implements FlowDesignService {
    @Resource
    private FlowDesignDao flowDesignDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public FlowDesign queryById(Integer id) {
        return this.flowDesignDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param pages  筛选条件分页对象
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Map<String,Object> pages) {
       Map<String,Object> map = Utils.pagination(pages);//处理分页信息
        FlowDesign flowDesign = JSON.parseObject(JSON.toJSONString(map.get("query")), FlowDesign.class);//json字符串转java对象
        
        long total = this.flowDesignDao.count(flowDesign);
        List<Map<String,Object>> list = this.flowDesignDao.queryAllByLimit(flowDesign,map.get("extend"));
        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        return response;
    }

    /**
     * 新增数据
     *
     * @param flowDesign 实例对象
     * @return 实例对象
     */
    @Override
    public FlowDesign insert(FlowDesign flowDesign) {
        this.flowDesignDao.insert(flowDesign);
        return flowDesign;
    }

    /**
     * 修改数据
     *
     * @param flowDesign 实例对象
     * @return 影响的行数
     */
    @Override
    public Integer updateById(FlowDesign flowDesign) {
        return this.flowDesignDao.updateById(flowDesign);
        //return this.queryById(flowDesign.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String[] id) {
        return this.flowDesignDao.deleteById(id) > 0;
    }
}
