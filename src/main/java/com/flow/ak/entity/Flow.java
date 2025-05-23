package com.flow.ak.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import java.io.Serial;


/**
 * (Flow)实体类
 *
 * @author ak.design 337547038
 * @since 2025-05-22 18:47:48
 */
@Data
public class Flow implements Serializable {
    @Serial
    private static final long serialVersionUID = -19076217224073917L;

    private Integer id;
/**
     * 申请人
     */
    private Integer name;
/**
     * 申请时间
     */
    private Date startTime;
/**
     * 完成时间
     */
    private Date endTime;
/**
     * 提交的表单内容
     */
    private String formContent;
/**
     * 状态
     */
    private Integer status;
/**
     * 当前节点id
     */
    private String currentNode;
/**
     * 所属流程id
     */
    private Integer flowId;
/**
     * 自选节点指定审批人
     */
    private String approver;


}

