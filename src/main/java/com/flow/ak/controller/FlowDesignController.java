package com.flow.ak.controller;

import com.flow.ak.entity.FlowDesign;
import com.flow.ak.service.FlowDesignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.Map;

/**
 * (FlowDesign)表控制层
 *
 * @author ak.design 337547038
 * @since 2025-05-22 20:14:30
 */
@RestController
@RequestMapping("flow/design")
public class FlowDesignController {
    /**
     * 服务对象
     */
    @Resource
    private FlowDesignService flowDesignService;

    /**
     * 分页查询
     * 前端传参:
     * * @param pages 筛选条件分页对象
     * {
     * query:{},//查询条件
     * extend:{
     * pageNum:1,//当前第几页
     * pageSize:20,//每页多少条记录，默认20。小于0返回全部
     * sort:"id desc"//排序
     * columns:""//返回指定查询字段，如'id,name'
     * }
     * }
     *
     * @return 查询结果
     */
    @PostMapping("list")
    public ResponseEntity<Map<String, Object>> queryByPage(@RequestBody Map<String, Object> pages) {
        return ResponseEntity.ok(this.flowDesignService.queryByPage(pages));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param query 主键
     * @return 单条数据
     */
    @PostMapping("get")
    public ResponseEntity<FlowDesign> queryById(@RequestBody Map<String, Integer> query) {
        return ResponseEntity.ok(this.flowDesignService.queryById(query.get("id")));
    }

    /**
     * 新增数据
     *
     * @param flowDesign 实体
     * @return 新增结果Id
     */
    @PostMapping("save")
    public ResponseEntity<Integer> add(@RequestBody FlowDesign flowDesign) {
        FlowDesign result = flowDesignService.insert(flowDesign);
        return ResponseEntity.ok(result.getId());
    }

    /**
     * 编辑数据
     *
     * @param flowDesign 实体
     * @return 影响行数
     */
    @PostMapping("edit")
    public ResponseEntity<Integer> edit(@RequestBody FlowDesign flowDesign) {
        return ResponseEntity.ok(this.flowDesignService.updateById(flowDesign));
    }

    /**
     * 删除数据，删除多个时使用豆号分隔
     *
     * @param ids 主键
     * @return 删除是否成功
     */
    @PostMapping("delete")
    public ResponseEntity<Boolean> deleteById(@RequestBody Map<String, Object> ids) {
        String string = ids.get("id").toString();
        String[] idList = string.split(",");
        return ResponseEntity.ok(this.flowDesignService.deleteById(idList));
    }

}

