package com.flow.ak.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.flow.ak.entity.FlowRecord;
import com.flow.ak.entity.User;
import com.flow.ak.service.FlowRecordService;
import com.flow.ak.service.UserService;
import com.flow.ak.utils.Utils;
import com.flow.ak.entity.Flow;
import com.flow.ak.dao.FlowDao;
import com.flow.ak.service.FlowService;
import com.flow.ak.service.FlowDesignService;
import com.flow.ak.entity.FlowDesign;
import lombok.Data;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.util.*;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.MapContext;

/**
 * (Flow)表服务实现类
 *
 * @author ak.design 337547038
 * @since 2025-05-23 17:10:36
 */
@Service("flowService")
public class FlowServiceImpl implements FlowService {
    @Resource
    private FlowDao flowDao;

    @Resource
    private FlowDesignService flowDesignService;

    @Resource
    private FlowRecordService flowRecordService;
    @Resource
    private UserService userService;
    private static String formContent;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Flow queryById(Integer id) {
        return this.flowDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param pages 筛选条件分页对象
     * @return 查询结果
     */
    @Override
    public Map<String, Object> queryByPage(Map<String, Object> pages) {
        Map<String, Object> map = Utils.pagination(pages);//处理分页信息
        Flow flow = JSON.parseObject(JSON.toJSONString(map.get("query")), Flow.class);//json字符串转java对象

        long total = this.flowDao.count(flow);
        List<Map<String, Object>> list = this.flowDao.queryAllByLimit(flow, map.get("extend"));
        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        return response;
    }

    /**
     * 新增数据
     *
     * @param flow 实例对象
     * @return 实例对象
     */
    @Override
    public Flow insert(Flow flow) {
        flow.setStartTime(new Date());
        // 获取当前需处理的节点
        formContent = flow.getFormContent();
        Map<String, Object> activeNode = getCurrentNode(flow.getFlowId());
        if(!activeNode.isEmpty()){
            flow.setCurrentNode((String) activeNode.get("activeNodes"));
            flow.currentUserId((String) activeNode.get("currentUserId"));
        }else {
            // 没有找到节点，直接标注为完成
            flow.setStatus(1);
        }
        this.flowDao.insert(flow);
        return flow;
    }


    /**
     * 修改数据
     *
     * @param flow 实例对象
     * @return 影响的行数
     */
    @Override
    public Integer updateById(Flow flow) {
        return this.flowDao.updateById(flow);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String[] id) {
        return this.flowDao.deleteById(id) > 0;
    }

    private Map<String, Object> getCurrentNode(Integer flowId) {
        FlowDesign flowDesign = flowDesignService.queryById(flowId);
        String json = flowDesign.getContent();
        FlowChart flowChart = JSON.parseObject(json, FlowChart.class);
        // 找到开始节点
        Node startNode = flowChart.getNodes().stream()
                .filter(node -> "start".equals(node.getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No start node found"));
        // 从开始节点开始遍历
        // 当前节点信息
        Map<String, Map<String, Object>> activeNodes = new HashMap<>();
        // 当前节点处理人
        List<String> activeUserId = new ArrayList<>();
        traverseFlow(startNode, flowChart.getEdges(), flowChart.getNodes(), flowId, activeNodes, activeUserId);
        Map<String, Object> result = new HashMap<>();
        if (!activeUserId.isEmpty()) {
            result.put("activeUserId", activeUserId);
            result.put("activeNodes", activeNodes);
        } else {
            System.out.println("没有找到审批人");
        }
        return result;
    }

    /**
     * 遍历节点
     *
     * @param currentNode  开始节点
     * @param edges        所有连接
     * @param nodes        所有节点
     * @param flowId       当前流程id
     * @param activeNodes  当前节点信息
     * @param activeUserId 当前节点审批人
     */
    private void traverseFlow(Node currentNode, List<Edge> edges, List<Node> nodes, Integer flowId, Map<String, Map<String, Object>> activeNodes, List<String> activeUserId) {
        // 找出当前节点的所有出边
        List<Edge> outgoingEdges = edges.stream()
                .filter(edge -> edge.getSourceNodeId().equals(currentNode.getId()))
                .toList();

        if (outgoingEdges.isEmpty()) {
            System.out.println("到达流程结束");
            return;
        }
        // 当前节点参与人id
        // 处理每条出边
        for (Edge edge : outgoingEdges) {
            Node nextNode = nodes.stream()
                    .filter(node -> node.getId().equals(edge.getTargetNodeId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Target node not found"));

            // 根据节点类型处理

            switch (nextNode.getType()) {
                case "userTask":
                    System.out.println("userTask");
                    executeTask(nextNode, activeNodes, activeUserId, flowId, edges, nodes);
                    break;
                case "sysTask":
                    System.out.println("sysTask");
                    executeTask(nextNode, activeNodes, activeUserId, flowId, edges, nodes);
                    break;
                case "condition":
                    System.out.println("条件节点");
                    // 这里可以添加条件判断逻辑
                    // 获取符合条件的下一节点
                    String nextTaskNodeId = getNextTaskNode(nextNode.getId(), edges);
                    Node nextTaskNode = nodes.stream()
                            .filter(node -> node.getId().equals(nextTaskNodeId))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Target node not found"));
                    System.out.println("nextTaskNodeId:" + nextTaskNodeId);
                    System.out.println("nextTaskNode:" + nextTaskNode);
                    executeTask(nextTaskNode, activeNodes, activeUserId, flowId, edges, nodes);
                    break;
                case "subProcess":
                    System.out.println("子流程");
                    // todo
                    break;
                case "end":
                    System.out.println("流程结束");
                    break;
            }
        }
    }

    /**
     * 执行任务
     *
     * @param nextNode     当前节点
     * @param activeNodes  当前节点信息
     * @param activeUserId 当前节点处理人
     * @param flowId       流程id
     * @param edges        所有连接线
     * @param nodes        所有节点
     */
    private void executeTask(Node nextNode, Map<String, Map<String, Object>> activeNodes, List<String> activeUserId, Integer flowId, List<Edge> edges, List<Node> nodes) {
        // 找出当前节点的参与人
        String taskUserId = getCurrentNodeUserId(nextNode.getProperties());
        if (Objects.equals(nextNode.getType(), "userTask")) {
            if (taskUserId != null && !Objects.equals(taskUserId, "")) {
                Map<String, Object> map = new HashMap<>();
                map.put("nodeName", nextNode.getText().get("value"));
                map.put("userId", taskUserId);
                activeNodes.put(nextNode.getId(), map);
                activeUserId.add(taskUserId);
            } else {
                System.out.println("当前节点没有审批人");
                // 节点没有审批人时，写一条节点记录继续遍历下一个
                addFlowRecord(0, flowId, nextNode.getId(), nextNode.getText().get("value"), 5, "没有审批人自动通过");
                traverseFlow(nextNode, edges, nodes, flowId, activeNodes, activeUserId); // 继续遍历
            }
        } else if (Objects.equals(nextNode.getType(), "sysTask")) {
            // 找出当前节点的参与人
            // 抄送节点，抄送的可能是多个用户
            if (taskUserId != null && !Objects.equals(taskUserId, "")) {
                // 使用逗号分隔字符串-
                String[] uId = taskUserId.split(",");
                for (String s : uId) {
                    addFlowRecord(Integer.valueOf(s), flowId, nextNode.getId(), nextNode.getText().get("value"), 5, "抄送");
                }
            } else {
                // 抄送节点没有设置抄送人时
                addFlowRecord(0, flowId, nextNode.getId(), nextNode.getText().get("value"), 5, "节点没有抄送人");
            }
            traverseFlow(nextNode, edges, nodes, flowId, activeNodes, activeUserId); // 继续遍历
        }
    }

    /**
     * 条件判断时返回符合条件的下一个节点
     */
    private String getNextTaskNode(String nodeId, List<Edge> edges) {
        String targetNodeId = "";
        for (Edge edge : edges) {
            if (edge.getSourceNodeId().equals(nodeId)) {
                Object expr = edge.getProperties().get("expr");
                if (expr != null) {
                    if (evaluateExpression((String) expr)) {
                        targetNodeId = edge.getTargetNodeId();
                        break;
                    }
                }
            }
        }
        return targetNodeId;
    }

    private static boolean evaluateExpression(String expression) {
        Map<String, Object> map = JSONObject.parseObject(formContent, new TypeReference<Map<String, Object>>() {
        });
        try {
            // 创建Jexl引擎
            JexlEngine jexl = new JexlBuilder().create();
            // 创建上下文并设置变量
            MapContext context = new MapContext(map);
            // 计算表达式的结果
            Boolean result = (Boolean) jexl.createExpression(expression).evaluate(context);
            System.out.println("计算结果：" + result);
            System.out.println("计算expression：" + expression);
            System.out.println("计算map：" + map);
            return result;
        } catch (Exception e) {
            System.out.println("表达式计算出错");
            return false;
        }
    }

    /**
     * 添加一条流程记录
     *
     * @param userId   操作人
     * @param flowId   流程
     * @param nodeId   当前节点id
     * @param nodeName 当前节点名称
     * @param status   状态
     * @param remark   备注
     */
    private void addFlowRecord(Integer userId, Integer flowId, String nodeId, Object nodeName, Integer status, String remark) {
        FlowRecord flowRecord = new FlowRecord();
        flowRecord.setFlowId(flowId);
        flowRecord.setUserId(userId); // 操作用户0表示系统
        flowRecord.setDateTime(new Date());
        flowRecord.setStatus(status);
        flowRecord.setNodeId(nodeId);
        flowRecord.setNodeName((String) nodeName);
        flowRecord.setRemark(remark);
        flowRecordService.insert(flowRecord);
    }

    /**
     * 返回当前节点审批人
     *
     * @param properties 节点属性
     * @return 审批人id
     */
    private String getCurrentNodeUserId(Map<String, Object> properties) {
        //   1: '指定成员',
        //  2: '指定角色',
        //  3: '直接领导',
        //  4: '发起人自选',
        //  5: '连续多级主管'
        if (properties.get("userType") == null) {
            return null;
        }
        return switch ((String) properties.get("userType")) {
            case "1" -> // 指定成员
                    properties.get("joinUserId").toString();
            case "3" -> {
                User user = userService.queryById(Utils.getCurrentUserId());
                yield user.getLeaderId().toString();
            }
            case "2", "4", "5" -> "";
            default -> // todo
                    "";
        };
    }

    @Data
    public static class Node {
        private String id;
        private String type;
        private int x;
        private int y;
        private Map<String, Object> properties;
        private Map<String, Object> text;
    }

    @Data
    public static class Text01 {
        private int x;
        private int y;
        private String value;
    }

    @Data
    public class FlowChart {
        private List<Node> nodes;
        private List<Edge> edges;

        // getters and setters
    }

    @Data
    public class Edge {
        private String id;
        private String type; // "connection"
        private Map<String, Object> properties;
        private String sourceNodeId;
        private String targetNodeId;
        private String sourceAnchorId;
        private String targetAnchorId;
        private Map<String, Object> text; // 用于条件节点的条件文本
    }

}
