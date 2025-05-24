package com.flow.ak.service.impl;

import com.alibaba.fastjson2.JSON;
import com.flow.ak.utils.Utils;
import com.flow.ak.entity.Flow;
import com.flow.ak.dao.FlowDao;
import com.flow.ak.service.FlowService;
import com.flow.ak.service.FlowDesignService;
import com.flow.ak.entity.FlowDesign;
import lombok.Data;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        flow.setStatus(0); // 这里应判断流程有没异常
        // 获取当前需处理的节点
        String currentNode = getCurrentNode(flow.getFlowId());
        // this.flowDao.insert(flow);
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
        //return this.queryById(flow.getId());
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

    private String getCurrentNode(Integer flowId) {
        System.out.println(flowId);
        System.out.println("flowId");
        FlowDesign flowDesign = flowDesignService.queryById(flowId);
        String json = flowDesign.getContent();

        FlowChart flowChart = JSON.parseObject(json, FlowChart.class);
        System.out.println(flowChart.getNodes());
        // 1. 找到开始节点
        //System.out.println(flowChart);
        /*Node startNode = flowChart.getNodes().stream()
                .filter(node -> "start".equals(node.getType()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No start node found"));*/
        return "abc";
    }

    /*private Node findStartNode(List<Node> nodes) {
        return nodes.stream()
                .filter(n -> "start".equals(n.getType()))
                .findFirst()
                .orElseThrow();
    }*/
/*    public class FlowChartParser {
        public static FlowChart parseFlowChart(String json) throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, FlowChart.class);
        }
    }*/
    @Data
    public class FlowChart {
        private List<Node> nodes;
        private List<Edge> edges;

        // getters and setters
    }
    @Data
    public class Node {
        private String id;
        private String type; // "start", "task", "condition", "end"
        private int x;
        private int y;
        private Map<String, Object> properties;
        private Text text;

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
        private Point startPoint;
        private Point endPoint;
        private Text text; // 用于条件节点的条件文本
        private List<Point> pointsList;

        // getters and setters
    }
    @Data
    public class Point {
        private int x;
        private int y;

        // getters and setters
    }
    @Data
    public class Text {
        private int x;
        private int y;
        private String value;

        // getters and setters
    }
}
