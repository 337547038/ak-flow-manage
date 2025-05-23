package com.flow.ak.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import java.io.Serial;


/**
 * (FlowDesign)实体类
 *
 * @author ak.design 337547038
 * @since 2025-05-22 20:14:32
 */
@Data
public class FlowDesign implements Serializable {
    @Serial
    private static final long serialVersionUID = 515814056418356965L;

    private Integer id;
/**
     * 流程名称
     */
    private String name;
/**
     * 分类
     */
    private Integer classify;
/**
     * 状态
     */
    private Integer status;

    private Date updateTime;

    private String icon;
/**
     * 表单类型0ak-form 1本地
     */
    private Integer formType;
/**
     * 选择的表单id或名称
     */
    private String formId;
/**
     * 设计的流程数据
     */
    private String content;


}

