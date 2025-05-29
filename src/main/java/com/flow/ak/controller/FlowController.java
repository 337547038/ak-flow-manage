package com.flow.ak.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.alibaba.fastjson2.util.TypeUtils;
import com.flow.ak.entity.Flow;
import com.flow.ak.service.FlowService;
import com.flow.ak.utils.Utils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Flow)表控制层
 *
 * @author ak.design 337547038
 * @since 2025-05-23 17:10:32
 */

@RestController
@RequestMapping("flow")
public class FlowController {
    /**
     * 服务对象
     */
    @Resource
    private FlowService flowService;

    /**
     * 分页查询
     * 前端传参:
     * * @param pages 筛选条件分页对象
     * {
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
    /*@PostMapping("list")
    public ResponseEntity<Map<String, Object>> queryByPage(@RequestBody Map<String, Object> pages) {
        return ResponseEntity.ok(this.flowService.queryByPage(pages));
    }*/
    // 我发起的分页查询
    @PostMapping("my")
    public ResponseEntity<Map<String, Object>> queryByPage(@RequestBody Map<String, Object> query) {
        query.put("userId", Utils.getCurrentUserId());
        return ResponseEntity.ok(this.flowService.queryByPage(query));
    }

    /**
     * 撤回流程申请
     *
     * @param query 流程id
     * @return 结果
     */
    @PostMapping("cancel")
    public ResponseEntity<Boolean> queryCancel(@RequestBody Map<String, Integer> query) {
        System.out.println("cancel controller");
        return ResponseEntity.ok(this.flowService.queryCancel(query.get("id")));
    }

    /**
     * 我的待办
     *
     * @param query 分页相关参数
     * @return 结果
     */
    @PostMapping("todo")
    public ResponseEntity<Map<String, Object>> getTodo(@RequestBody Map<String, Object> query) {
        query.put("currentUserId", Utils.getCurrentUserId());
        query.put("status", 0); // 进行中
        return ResponseEntity.ok(this.flowService.queryByPage(query));
    }

    @PostMapping("approval")
    public ResponseEntity<Boolean> submitApproval(@RequestBody Map<String, Object> query) {
        return ResponseEntity.ok(this.flowService.approval(query));
    }


    /**
     * 通过主键查询单条数据
     *
     * @param query 主键
     * @return 单条数据
     */

    @PostMapping("get")
    public ResponseEntity<Map<String, Object>> queryById(@RequestBody Map<String, Integer> query) {
        return ResponseEntity.ok(this.flowService.queryById(query.get("id")));
    }

    /**
     * 新增数据
     *
     * @param flow 实体
     * @return 新增结果Id
     */

    @PostMapping("save")
    public ResponseEntity<Integer> add(@RequestBody Flow flow) {
        Flow result = flowService.insert(flow);
        return ResponseEntity.ok(result.getId());
    }

    /**
     * 编辑数据
     *
     * @param flow 实体
     * @return 影响行数
     */
    @PostMapping("edit")
    public ResponseEntity<Integer> edit(@RequestBody Flow flow) {
        return ResponseEntity.ok(this.flowService.updateById(flow));
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
        return ResponseEntity.ok(this.flowService.deleteById(idList));
    }

}

