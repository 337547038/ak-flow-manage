-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1
-- 生成日期： 2025-06-05 05:52:30
-- 服务器版本： 10.4.27-MariaDB
-- PHP 版本： 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `akflow`
--

-- --------------------------------------------------------

--
-- 表的结构 `sys_flow`
--

CREATE TABLE `sys_flow` (
  `id` int(10) NOT NULL,
  `userId` int(11) NOT NULL COMMENT '申请人',
  `startTime` datetime NOT NULL COMMENT '申请时间',
  `endTime` datetime DEFAULT NULL COMMENT '完成时间',
  `formContent` text NOT NULL COMMENT '提交的表单内容',
  `status` int(2) NOT NULL COMMENT '0进行中 1完成 2拒绝 3已撤回 4退回',
  `currentNode` varchar(200) DEFAULT NULL COMMENT '当前节点id,多个,隔开',
  `currentUserId` varchar(50) DEFAULT NULL COMMENT '当前审批人',
  `flowId` int(10) NOT NULL COMMENT '所属流程id',
  `approver` varchar(200) DEFAULT NULL COMMENT '自选节点指定审批人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 转存表中的数据 `sys_flow`
--

INSERT INTO `sys_flow` (`id`, `userId`, `startTime`, `endTime`, `formContent`, `status`, `currentNode`, `currentUserId`, `flowId`, `approver`) VALUES
(26, 1, '2025-06-05 11:29:03', '2025-06-05 11:34:22', '{\"remark\":\"申请通用流程理由\"}', 2, '', '', 9, ''),
(27, 2, '2025-06-05 11:33:21', NULL, '{\"remark\":\"申请理由\"}', 0, '{\"779800dd-98be-4ac3-87ac-bf10ff20180b\":{\"nodeName\":\"用户任务\",\"userId\":\"1,3\"}}', '1,3', 9, ''),
(28, 1, '2025-06-05 11:36:49', '2025-06-05 11:37:19', '{\"remark\":\"理由是什么\"}', 1, '', '', 9, ''),
(29, 1, '2025-06-05 11:42:07', NULL, '{\"type\":\"1\",\"startDate\":\"2025-06-03T16:00:00.000Z\",\"endDate\":\"2025-05-27T16:00:00.000Z\",\"day\":\"2\",\"remark\":\"想请就请，哪有那么多理由\"}', 0, '{\"4257ec39-ae7a-4a82-963d-cdeb7e2c9082\":{\"nodeName\":\"主管审批\",\"userId\":\"3\"}}', '3', 7, ''),
(30, 2, '2025-06-05 11:43:07', NULL, '{\"type\":\"1\",\"startDate\":\"2025-06-03T16:00:00.000Z\",\"endDate\":\"2025-06-03T16:00:00.000Z\",\"day\":\"10\",\"remark\":\"不想上班\"}', 0, '{\"723d2767-9b50-4e0d-9039-7462279acd5c\":{\"nodeName\":\"经理审批\",\"userId\":\"3\"}}', '3', 7, ''),
(31, 3, '2025-06-05 11:46:00', NULL, '{\"type\":\"2\",\"startDate\":\"2025-06-02T16:00:00.000Z\",\"endDate\":\"2025-06-02T16:00:00.000Z\",\"day\":\"6\",\"remark\":\"请假\"}', 0, '{\"4257ec39-ae7a-4a82-963d-cdeb7e2c9082\":{\"nodeName\":\"主管审批\",\"userId\":\"2\"}}', '2', 7, ''),
(32, 1, '2025-06-05 11:47:48', NULL, '{\"type\":\"1\",\"startDate\":\"2025-06-03T16:00:00.000Z\",\"endDate\":\"2025-06-03T16:00:00.000Z\",\"day\":\"15\",\"remark\":\"理由\"}', 0, '{\"5886c5d4-3b49-4d15-8dea-63fba456b783\":{\"nodeName\":\"总经理审批\",\"userId\":\"4\"}}', '4', 7, ''),
(33, 2, '2025-06-05 11:50:32', NULL, '{\"type\":\"4\",\"startDate\":\"2025-06-03T16:00:00.000Z\",\"endDate\":\"2025-06-03T16:00:00.000Z\",\"day\":\"3\",\"remark\":\"请3天假\"}', 0, '{\"4257ec39-ae7a-4a82-963d-cdeb7e2c9082\":{\"nodeName\":\"主管审批\",\"userId\":\"1\"}}', '1', 7, '');

-- --------------------------------------------------------

--
-- 表的结构 `sys_flow_design`
--

CREATE TABLE `sys_flow_design` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '流程名称',
  `classify` int(4) DEFAULT NULL COMMENT '分类',
  `status` int(2) DEFAULT NULL COMMENT '状态',
  `updateTime` datetime NOT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `formType` int(2) DEFAULT NULL COMMENT '表单类型0ak-form 1本地',
  `formId` varchar(50) DEFAULT NULL COMMENT '选择的表单id或名称',
  `content` text NOT NULL COMMENT '设计的流程数据'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 转存表中的数据 `sys_flow_design`
--

INSERT INTO `sys_flow_design` (`id`, `name`, `classify`, `status`, `updateTime`, `icon`, `formType`, `formId`, `content`) VALUES
(5, '调休流程', 1, 0, '2025-06-05 11:16:49', NULL, 0, '1', '{\"nodes\":[{\"id\":\"start\",\"type\":\"start\",\"x\":233,\"y\":379,\"properties\":{\"width\":40,\"height\":40},\"text\":{\"x\":233,\"y\":379,\"value\":\"开始\"}},{\"id\":\"225ad617-7420-4e61-9387-e8b9588232eb\",\"type\":\"userTask\",\"x\":398,\"y\":379,\"properties\":{\"width\":120,\"nodeName\":\"上级主管审批\",\"flowType\":2,\"height\":80,\"userType\":\"3\",\"joinName\":\"直接领导\"},\"text\":{\"x\":398,\"y\":379,\"value\":\"上级主管审批\"}},{\"id\":\"b6ff9a0a-49d1-4341-8553-ae736183df11\",\"type\":\"condition\",\"x\":546,\"y\":379,\"properties\":{\"width\":50,\"height\":50}},{\"id\":\"57c651c1-e9ac-48a1-b356-40194a261df4\",\"type\":\"userTask\",\"x\":750,\"y\":302,\"properties\":{\"width\":120,\"nodeName\":\"总经理审批\",\"flowType\":2,\"height\":80,\"userType\":\"1\",\"joinName\":\"manage\",\"joinUserId\":\"2\"},\"text\":{\"x\":750,\"y\":302,\"value\":\"总经理审批\"}},{\"id\":\"59bc0e18-b4d0-4cec-a6ef-751278fad639\",\"type\":\"userTask\",\"x\":755,\"y\":477,\"properties\":{\"width\":120,\"nodeName\":\"部门经理审批\",\"height\":80,\"flowType\":2,\"userType\":\"1\",\"joinName\":\"user\",\"joinUserId\":\"1\"},\"text\":{\"x\":755,\"y\":477,\"value\":\"部门经理审批\"}},{\"id\":\"dd76c04b-2ead-4119-9b99-77ec5e7eaf70\",\"type\":\"sysTask\",\"x\":1055,\"y\":408,\"properties\":{\"width\":120,\"nodeName\":\"抄送行政\",\"flowType\":2,\"height\":80,\"userType\":\"1\",\"joinName\":\"hr\",\"joinUserId\":\"5\"},\"text\":{\"x\":1055,\"y\":408,\"value\":\"抄送行政\"}},{\"id\":\"fb1db69e-a27b-477e-a66e-f837497d376d\",\"type\":\"end\",\"x\":1229,\"y\":408,\"properties\":{\"width\":40,\"height\":40}}],\"edges\":[{\"id\":\"9ac86e76-5a05-4e28-b843-6be5283f15ce\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"start\",\"targetNodeId\":\"225ad617-7420-4e61-9387-e8b9588232eb\",\"sourceAnchorId\":\"start_1\",\"targetAnchorId\":\"225ad617-7420-4e61-9387-e8b9588232eb_3\",\"startPoint\":{\"x\":253,\"y\":379,\"id\":\"284-164\"},\"endPoint\":{\"x\":338,\"y\":379,\"id\":\"369-164\"},\"pointsList\":[{\"x\":253,\"y\":379},{\"x\":338,\"y\":379}]},{\"id\":\"281cf407-e63e-4cb9-8234-d709a2a7143e\",\"type\":\"connection\",\"properties\":{\"flowType\":2,\"expr\":\"day>=3\"},\"sourceNodeId\":\"b6ff9a0a-49d1-4341-8553-ae736183df11\",\"targetNodeId\":\"57c651c1-e9ac-48a1-b356-40194a261df4\",\"sourceAnchorId\":\"b6ff9a0a-49d1-4341-8553-ae736183df11_0\",\"targetAnchorId\":\"57c651c1-e9ac-48a1-b356-40194a261df4_3\",\"startPoint\":{\"x\":546,\"y\":354},\"endPoint\":{\"x\":690,\"y\":302},\"text\":{\"x\":618,\"y\":302,\"value\":\"请假大于3天\"},\"pointsList\":[{\"x\":546,\"y\":354},{\"x\":546,\"y\":302},{\"x\":690,\"y\":302}]},{\"id\":\"2926c889-380d-4254-aa6d-922c04b938a1\",\"type\":\"connection\",\"properties\":{\"flowType\":2,\"expr\":\"day<3\"},\"sourceNodeId\":\"b6ff9a0a-49d1-4341-8553-ae736183df11\",\"targetNodeId\":\"59bc0e18-b4d0-4cec-a6ef-751278fad639\",\"sourceAnchorId\":\"b6ff9a0a-49d1-4341-8553-ae736183df11_2\",\"targetAnchorId\":\"59bc0e18-b4d0-4cec-a6ef-751278fad639_3\",\"startPoint\":{\"x\":546,\"y\":404},\"endPoint\":{\"x\":695,\"y\":477},\"text\":{\"x\":620.5,\"y\":477,\"value\":\"请假小于3天\"},\"pointsList\":[{\"x\":546,\"y\":404},{\"x\":546,\"y\":477},{\"x\":695,\"y\":477}]},{\"id\":\"49e93750-6a77-4a84-8c98-91c80f78720c\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"225ad617-7420-4e61-9387-e8b9588232eb\",\"targetNodeId\":\"b6ff9a0a-49d1-4341-8553-ae736183df11\",\"sourceAnchorId\":\"225ad617-7420-4e61-9387-e8b9588232eb_1\",\"targetAnchorId\":\"b6ff9a0a-49d1-4341-8553-ae736183df11_3\",\"startPoint\":{\"x\":458,\"y\":379},\"endPoint\":{\"x\":521,\"y\":379},\"pointsList\":[{\"x\":458,\"y\":379},{\"x\":521,\"y\":379}]},{\"id\":\"54de3eb9-8b3e-4424-b7dd-f2f5a083a525\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"59bc0e18-b4d0-4cec-a6ef-751278fad639\",\"targetNodeId\":\"dd76c04b-2ead-4119-9b99-77ec5e7eaf70\",\"sourceAnchorId\":\"59bc0e18-b4d0-4cec-a6ef-751278fad639_1\",\"targetAnchorId\":\"dd76c04b-2ead-4119-9b99-77ec5e7eaf70_2\",\"startPoint\":{\"x\":815,\"y\":477},\"endPoint\":{\"x\":1055,\"y\":448},\"pointsList\":[{\"x\":815,\"y\":477},{\"x\":845,\"y\":477},{\"x\":845,\"y\":478},{\"x\":1055,\"y\":478},{\"x\":1055,\"y\":448}]},{\"id\":\"f2d8a58d-ec20-4577-9b04-a11fb0536867\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"57c651c1-e9ac-48a1-b356-40194a261df4\",\"targetNodeId\":\"dd76c04b-2ead-4119-9b99-77ec5e7eaf70\",\"sourceAnchorId\":\"57c651c1-e9ac-48a1-b356-40194a261df4_1\",\"targetAnchorId\":\"dd76c04b-2ead-4119-9b99-77ec5e7eaf70_0\",\"startPoint\":{\"x\":810,\"y\":302},\"endPoint\":{\"x\":1055,\"y\":368},\"pointsList\":[{\"x\":810,\"y\":302},{\"x\":1055,\"y\":302},{\"x\":1055,\"y\":368}]},{\"id\":\"b484c232-be82-4671-a0da-c6e8869ee2fd\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"dd76c04b-2ead-4119-9b99-77ec5e7eaf70\",\"targetNodeId\":\"fb1db69e-a27b-477e-a66e-f837497d376d\",\"sourceAnchorId\":\"dd76c04b-2ead-4119-9b99-77ec5e7eaf70_1\",\"targetAnchorId\":\"fb1db69e-a27b-477e-a66e-f837497d376d_3\",\"startPoint\":{\"x\":1115,\"y\":408},\"endPoint\":{\"x\":1209,\"y\":408},\"pointsList\":[{\"x\":1115,\"y\":408},{\"x\":1209,\"y\":408}]}]}'),
(6, '加班申请', 2, 1, '2025-05-29 21:14:35', NULL, 1, 'form2', '{\"nodes\":[{\"id\":\"start\",\"type\":\"start\",\"x\":232,\"y\":166,\"properties\":{\"width\":40,\"height\":40},\"text\":{\"x\":232,\"y\":166,\"value\":\"开始\"}},{\"id\":\"18e7c864-575a-4be9-a995-d0f356d79bda\",\"type\":\"userTask\",\"x\":387,\"y\":166,\"properties\":{\"width\":120,\"nodeName\":\"主管审批\",\"flowType\":2,\"height\":80,\"userType\":\"3\",\"joinName\":\"直接领导\"},\"text\":{\"x\":387,\"y\":166,\"value\":\"主管审批\"}},{\"id\":\"5ca95d99-dcf9-4076-bc09-c2595c57dde6\",\"type\":\"sysTask\",\"x\":559,\"y\":166,\"properties\":{\"width\":120,\"nodeName\":\"前台行政\",\"flowType\":2,\"height\":80,\"userType\":\"1\",\"joinName\":\"hr\",\"joinUserId\":\"5\"},\"text\":{\"x\":559,\"y\":166,\"value\":\"前台行政\"}},{\"id\":\"05309907-c7df-4c4c-8705-fdf057aa2da0\",\"type\":\"end\",\"x\":728,\"y\":166,\"properties\":{\"width\":40,\"height\":40}}],\"edges\":[{\"id\":\"b0bfe224-8af7-446b-8a63-394824b54035\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"start\",\"targetNodeId\":\"18e7c864-575a-4be9-a995-d0f356d79bda\",\"sourceAnchorId\":\"start_1\",\"targetAnchorId\":\"18e7c864-575a-4be9-a995-d0f356d79bda_3\",\"startPoint\":{\"x\":252,\"y\":166},\"endPoint\":{\"x\":327,\"y\":166},\"pointsList\":[{\"x\":252,\"y\":166},{\"x\":327,\"y\":166}]},{\"id\":\"183e97ef-efd6-451e-8e4d-78bd94b03984\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"18e7c864-575a-4be9-a995-d0f356d79bda\",\"targetNodeId\":\"5ca95d99-dcf9-4076-bc09-c2595c57dde6\",\"sourceAnchorId\":\"18e7c864-575a-4be9-a995-d0f356d79bda_1\",\"targetAnchorId\":\"5ca95d99-dcf9-4076-bc09-c2595c57dde6_3\",\"startPoint\":{\"x\":447,\"y\":166},\"endPoint\":{\"x\":499,\"y\":166},\"pointsList\":[{\"x\":447,\"y\":166},{\"x\":477,\"y\":166},{\"x\":477,\"y\":166},{\"x\":469,\"y\":166},{\"x\":469,\"y\":166},{\"x\":499,\"y\":166}]},{\"id\":\"7b28c227-c45d-4749-8663-7c8792292128\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"5ca95d99-dcf9-4076-bc09-c2595c57dde6\",\"targetNodeId\":\"05309907-c7df-4c4c-8705-fdf057aa2da0\",\"sourceAnchorId\":\"5ca95d99-dcf9-4076-bc09-c2595c57dde6_1\",\"targetAnchorId\":\"05309907-c7df-4c4c-8705-fdf057aa2da0_3\",\"startPoint\":{\"x\":619,\"y\":166},\"endPoint\":{\"x\":708,\"y\":166},\"pointsList\":[{\"x\":619,\"y\":166},{\"x\":708,\"y\":166}]}]}'),
(7, '请假申请', 1, 1, '2025-06-05 11:41:12', NULL, 1, 'form1', '{\"nodes\":[{\"id\":\"start\",\"type\":\"start\",\"x\":313,\"y\":283,\"properties\":{\"width\":40,\"height\":40,\"flowType\":2},\"text\":{\"x\":313,\"y\":283,\"value\":\"申请\"}},{\"id\":\"723d2767-9b50-4e0d-9039-7462279acd5c\",\"type\":\"userTask\",\"x\":829,\"y\":473,\"properties\":{\"width\":120,\"nodeName\":\"经理审批\",\"height\":80,\"flowType\":2,\"userType\":\"1\",\"joinName\":\"manage\",\"joinUserId\":\"3\"},\"text\":{\"x\":829,\"y\":473,\"value\":\"经理审批\"}},{\"id\":\"4257ec39-ae7a-4a82-963d-cdeb7e2c9082\",\"type\":\"userTask\",\"x\":490,\"y\":283,\"properties\":{\"width\":120,\"nodeName\":\"主管审批\",\"height\":80,\"flowType\":2,\"userType\":\"3\",\"joinName\":\"直接领导\"},\"text\":{\"x\":490,\"y\":283,\"value\":\"主管审批\"}},{\"id\":\"5886c5d4-3b49-4d15-8dea-63fba456b783\",\"type\":\"userTask\",\"x\":1011,\"y\":473,\"properties\":{\"width\":120,\"nodeName\":\"总经理审批\",\"flowType\":2,\"height\":80,\"userType\":\"1\",\"joinName\":\"admin\",\"joinUserId\":\"4\"},\"text\":{\"x\":1011,\"y\":473,\"value\":\"总经理审批\"}},{\"id\":\"604c73b7-525a-49cd-a714-6d7855edc66d\",\"type\":\"condition\",\"x\":635,\"y\":283,\"properties\":{\"width\":50,\"height\":50}},{\"id\":\"9f336b1a-1df6-454b-8394-e4e743d6a037\",\"type\":\"userTask\",\"x\":831,\"y\":283,\"properties\":{\"width\":120,\"nodeName\":\"经理审批\",\"flowType\":2,\"height\":80,\"userType\":\"1\",\"joinName\":\"user\",\"joinUserId\":\"1\"},\"text\":{\"x\":831,\"y\":283,\"value\":\"经理审批\"}},{\"id\":\"b38ab502-07ce-4367-b12c-8e8b48d230ec\",\"type\":\"sysTask\",\"x\":1027,\"y\":131,\"properties\":{\"width\":120,\"nodeName\":\"人事行政\",\"flowType\":2,\"height\":80,\"userType\":\"1\",\"joinName\":\"hr\",\"joinUserId\":\"5\"},\"text\":{\"x\":1027,\"y\":131,\"value\":\"人事行政\"}},{\"id\":\"b9de6086-b1b0-43eb-805d-972e7b1daa9d\",\"type\":\"sysTask\",\"x\":1246,\"y\":131,\"properties\":{\"width\":120,\"nodeName\":\"前台\",\"flowType\":2,\"height\":80,\"userType\":\"1\",\"joinName\":\"user\",\"joinUserId\":\"1\"},\"text\":{\"x\":1246,\"y\":131,\"value\":\"前台\"}},{\"id\":\"44171ef1-8b0a-458c-8dbe-2703c97c20ab\",\"type\":\"end\",\"x\":1246,\"y\":341,\"properties\":{\"width\":40,\"height\":40}}],\"edges\":[{\"id\":\"9656fc63-ae5e-4107-9136-50ea53442a9b\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"start\",\"targetNodeId\":\"4257ec39-ae7a-4a82-963d-cdeb7e2c9082\",\"sourceAnchorId\":\"start_1\",\"targetAnchorId\":\"4257ec39-ae7a-4a82-963d-cdeb7e2c9082_3\",\"startPoint\":{\"x\":333,\"y\":283},\"endPoint\":{\"x\":430,\"y\":283},\"pointsList\":[{\"x\":333,\"y\":283},{\"x\":430,\"y\":283}]},{\"id\":\"6d4af52d-68e1-48ac-8d80-6af21093971d\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"4257ec39-ae7a-4a82-963d-cdeb7e2c9082\",\"targetNodeId\":\"604c73b7-525a-49cd-a714-6d7855edc66d\",\"sourceAnchorId\":\"4257ec39-ae7a-4a82-963d-cdeb7e2c9082_1\",\"targetAnchorId\":\"604c73b7-525a-49cd-a714-6d7855edc66d_3\",\"startPoint\":{\"x\":550,\"y\":283},\"endPoint\":{\"x\":610,\"y\":283},\"pointsList\":[{\"x\":550,\"y\":283},{\"x\":610,\"y\":283}]},{\"id\":\"b9e21230-5002-409a-9f79-d67a57f0b49c\",\"type\":\"connection\",\"properties\":{\"flowType\":2,\"expr\":\"day>5\"},\"sourceNodeId\":\"604c73b7-525a-49cd-a714-6d7855edc66d\",\"targetNodeId\":\"723d2767-9b50-4e0d-9039-7462279acd5c\",\"sourceAnchorId\":\"604c73b7-525a-49cd-a714-6d7855edc66d_2\",\"targetAnchorId\":\"723d2767-9b50-4e0d-9039-7462279acd5c_3\",\"startPoint\":{\"x\":635,\"y\":308},\"endPoint\":{\"x\":769,\"y\":473},\"text\":{\"x\":636,\"y\":473,\"value\":\"请假大于5天\"},\"pointsList\":[{\"x\":635,\"y\":308},{\"x\":635,\"y\":473},{\"x\":769,\"y\":473}]},{\"id\":\"489a7ce9-50ea-4f52-8b7a-5d118617fced\",\"type\":\"connection\",\"properties\":{\"flowType\":2,\"expr\":\"day>=3&&day<=5\"},\"sourceNodeId\":\"604c73b7-525a-49cd-a714-6d7855edc66d\",\"targetNodeId\":\"9f336b1a-1df6-454b-8394-e4e743d6a037\",\"sourceAnchorId\":\"604c73b7-525a-49cd-a714-6d7855edc66d_1\",\"targetAnchorId\":\"9f336b1a-1df6-454b-8394-e4e743d6a037_3\",\"startPoint\":{\"x\":660,\"y\":283},\"endPoint\":{\"x\":771,\"y\":283},\"text\":{\"x\":715.5,\"y\":283,\"value\":\"请假3-5天\"},\"pointsList\":[{\"x\":660,\"y\":283},{\"x\":771,\"y\":283}]},{\"id\":\"a82216b5-ce4b-424b-824d-18cad498343e\",\"type\":\"connection\",\"properties\":{\"flowType\":2,\"expr\":\"day<=2\"},\"sourceNodeId\":\"604c73b7-525a-49cd-a714-6d7855edc66d\",\"targetNodeId\":\"b38ab502-07ce-4367-b12c-8e8b48d230ec\",\"sourceAnchorId\":\"604c73b7-525a-49cd-a714-6d7855edc66d_0\",\"targetAnchorId\":\"b38ab502-07ce-4367-b12c-8e8b48d230ec_3\",\"startPoint\":{\"x\":635,\"y\":258},\"endPoint\":{\"x\":967,\"y\":131},\"text\":{\"x\":801,\"y\":131,\"value\":\"请假1-2天\"},\"pointsList\":[{\"x\":635,\"y\":258},{\"x\":635,\"y\":131},{\"x\":967,\"y\":131}]},{\"id\":\"87d29e46-dd4d-42b3-aeca-a205c26263eb\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"9f336b1a-1df6-454b-8394-e4e743d6a037\",\"targetNodeId\":\"b38ab502-07ce-4367-b12c-8e8b48d230ec\",\"sourceAnchorId\":\"9f336b1a-1df6-454b-8394-e4e743d6a037_1\",\"targetAnchorId\":\"b38ab502-07ce-4367-b12c-8e8b48d230ec_2\",\"startPoint\":{\"x\":891,\"y\":283},\"endPoint\":{\"x\":1027,\"y\":171},\"pointsList\":[{\"x\":891,\"y\":283},{\"x\":1027,\"y\":283},{\"x\":1027,\"y\":171}]},{\"id\":\"a406e270-f625-4aed-9c0b-2329cd636140\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"723d2767-9b50-4e0d-9039-7462279acd5c\",\"targetNodeId\":\"5886c5d4-3b49-4d15-8dea-63fba456b783\",\"sourceAnchorId\":\"723d2767-9b50-4e0d-9039-7462279acd5c_1\",\"targetAnchorId\":\"5886c5d4-3b49-4d15-8dea-63fba456b783_3\",\"startPoint\":{\"x\":889,\"y\":473},\"endPoint\":{\"x\":951,\"y\":473},\"pointsList\":[{\"x\":889,\"y\":473},{\"x\":951,\"y\":473}]},{\"id\":\"09ecf4ea-e93d-48dc-a817-adb2dcf7ecad\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"5886c5d4-3b49-4d15-8dea-63fba456b783\",\"targetNodeId\":\"b38ab502-07ce-4367-b12c-8e8b48d230ec\",\"sourceAnchorId\":\"5886c5d4-3b49-4d15-8dea-63fba456b783_1\",\"targetAnchorId\":\"b38ab502-07ce-4367-b12c-8e8b48d230ec_2\",\"startPoint\":{\"x\":1071,\"y\":473},\"endPoint\":{\"x\":1027,\"y\":171},\"pointsList\":[{\"x\":1071,\"y\":473},{\"x\":1101,\"y\":473},{\"x\":1101,\"y\":201},{\"x\":1027,\"y\":201},{\"x\":1027,\"y\":171}]},{\"id\":\"830a7bba-ce69-45d1-9964-a2cb1b5383e0\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"b38ab502-07ce-4367-b12c-8e8b48d230ec\",\"targetNodeId\":\"b9de6086-b1b0-43eb-805d-972e7b1daa9d\",\"sourceAnchorId\":\"b38ab502-07ce-4367-b12c-8e8b48d230ec_1\",\"targetAnchorId\":\"b9de6086-b1b0-43eb-805d-972e7b1daa9d_3\",\"startPoint\":{\"x\":1087,\"y\":131},\"endPoint\":{\"x\":1186,\"y\":131},\"pointsList\":[{\"x\":1087,\"y\":131},{\"x\":1186,\"y\":131}]},{\"id\":\"2251b796-01e6-4fb7-8ed8-51d5ae7b1f4d\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"b9de6086-b1b0-43eb-805d-972e7b1daa9d\",\"targetNodeId\":\"44171ef1-8b0a-458c-8dbe-2703c97c20ab\",\"sourceAnchorId\":\"b9de6086-b1b0-43eb-805d-972e7b1daa9d_2\",\"targetAnchorId\":\"44171ef1-8b0a-458c-8dbe-2703c97c20ab_0\",\"startPoint\":{\"x\":1246,\"y\":171},\"endPoint\":{\"x\":1246,\"y\":321},\"pointsList\":[{\"x\":1246,\"y\":171},{\"x\":1246,\"y\":321}]}]}'),
(8, '通用流程', 4, 1, '2025-06-05 11:22:14', NULL, 1, 'form2', '{\"nodes\":[{\"id\":\"start\",\"type\":\"start\",\"x\":264,\"y\":164,\"properties\":{\"width\":40,\"height\":40},\"text\":{\"x\":264,\"y\":164,\"value\":\"开始\"}},{\"id\":\"eb338503-649f-4604-8cc9-b3cf004dbad6\",\"type\":\"userTask\",\"x\":416,\"y\":164,\"properties\":{\"width\":120,\"nodeName\":\"用户任务\",\"flowType\":2,\"height\":80,\"userType\":\"3\",\"joinName\":\"直接领导\"},\"text\":{\"x\":416,\"y\":164,\"value\":\"用户任务\"}},{\"id\":\"53304f8c-83a1-453c-88bb-c1a3267a633d\",\"type\":\"end\",\"x\":885,\"y\":164,\"properties\":{\"width\":40,\"height\":40}},{\"id\":\"02c41109-41fb-41bc-9b67-67cd7446a54a\",\"type\":\"userTask\",\"x\":643,\"y\":164,\"properties\":{\"width\":120,\"nodeName\":\"自定审批人\",\"flowType\":2,\"height\":80,\"userType\":\"4\",\"joinName\":\"发起人自选\"},\"text\":{\"x\":643,\"y\":164,\"value\":\"自定审批人\"}}],\"edges\":[{\"id\":\"0ddc2aab-13a7-4fc1-b7da-8eeeeee80813\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"start\",\"targetNodeId\":\"eb338503-649f-4604-8cc9-b3cf004dbad6\",\"sourceAnchorId\":\"start_1\",\"targetAnchorId\":\"eb338503-649f-4604-8cc9-b3cf004dbad6_3\",\"startPoint\":{\"x\":284,\"y\":164},\"endPoint\":{\"x\":356,\"y\":164},\"pointsList\":[{\"x\":284,\"y\":164},{\"x\":356,\"y\":164}]},{\"id\":\"ec5d6c29-472b-4639-b56d-7462c8f968ae\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"eb338503-649f-4604-8cc9-b3cf004dbad6\",\"targetNodeId\":\"02c41109-41fb-41bc-9b67-67cd7446a54a\",\"sourceAnchorId\":\"eb338503-649f-4604-8cc9-b3cf004dbad6_1\",\"targetAnchorId\":\"02c41109-41fb-41bc-9b67-67cd7446a54a_3\",\"startPoint\":{\"x\":476,\"y\":164},\"endPoint\":{\"x\":583,\"y\":164},\"pointsList\":[{\"x\":476,\"y\":164},{\"x\":583,\"y\":164}]},{\"id\":\"5ea5bafb-52bc-4793-83b2-0ec6b60c6db6\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"02c41109-41fb-41bc-9b67-67cd7446a54a\",\"targetNodeId\":\"53304f8c-83a1-453c-88bb-c1a3267a633d\",\"sourceAnchorId\":\"02c41109-41fb-41bc-9b67-67cd7446a54a_1\",\"targetAnchorId\":\"53304f8c-83a1-453c-88bb-c1a3267a633d_3\",\"startPoint\":{\"x\":703,\"y\":164},\"endPoint\":{\"x\":865,\"y\":164},\"pointsList\":[{\"x\":703,\"y\":164},{\"x\":865,\"y\":164}]}]}'),
(9, '通用流程审批', 4, 1, '2025-06-05 11:28:22', NULL, 1, 'form3', '{\"nodes\":[{\"id\":\"start\",\"type\":\"start\",\"x\":233,\"y\":166,\"properties\":{\"width\":40,\"height\":40},\"text\":{\"x\":233,\"y\":166,\"value\":\"开始\"}},{\"id\":\"779800dd-98be-4ac3-87ac-bf10ff20180b\",\"type\":\"userTask\",\"x\":387,\"y\":166,\"properties\":{\"width\":120,\"nodeName\":\"用户任务\",\"flowType\":2,\"height\":80,\"userType\":\"1\",\"joinName\":\"user,manage\",\"joinUserId\":\"1,3\"},\"text\":{\"x\":387,\"y\":166,\"value\":\"用户任务\"}},{\"id\":\"c8e23610-1862-46e3-a195-047595e7efd3\",\"type\":\"sysTask\",\"x\":635,\"y\":166,\"properties\":{\"width\":120,\"nodeName\":\"抄送\",\"flowType\":2,\"height\":80,\"userType\":\"1\",\"joinName\":\"user,hr\",\"joinUserId\":\"1,5\"},\"text\":{\"x\":635,\"y\":166,\"value\":\"抄送\"}},{\"id\":\"d495fbc1-1dde-4c4f-8792-ddc98f266016\",\"type\":\"end\",\"x\":803,\"y\":164,\"properties\":{\"width\":40,\"height\":40}}],\"edges\":[{\"id\":\"e33171f9-2987-44d8-9b6b-d1dda0c22159\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"start\",\"targetNodeId\":\"779800dd-98be-4ac3-87ac-bf10ff20180b\",\"sourceAnchorId\":\"start_1\",\"targetAnchorId\":\"779800dd-98be-4ac3-87ac-bf10ff20180b_3\",\"startPoint\":{\"x\":253,\"y\":166},\"endPoint\":{\"x\":327,\"y\":166},\"pointsList\":[{\"x\":253,\"y\":166},{\"x\":327,\"y\":166}]},{\"id\":\"4e09d3f0-418c-4de2-ad48-990a540971eb\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"779800dd-98be-4ac3-87ac-bf10ff20180b\",\"targetNodeId\":\"c8e23610-1862-46e3-a195-047595e7efd3\",\"sourceAnchorId\":\"779800dd-98be-4ac3-87ac-bf10ff20180b_1\",\"targetAnchorId\":\"c8e23610-1862-46e3-a195-047595e7efd3_3\",\"startPoint\":{\"x\":447,\"y\":166},\"endPoint\":{\"x\":575,\"y\":166},\"pointsList\":[{\"x\":447,\"y\":166},{\"x\":575,\"y\":166}]},{\"id\":\"09bba063-f3f9-4e46-b380-ebf0bb49eb35\",\"type\":\"connection\",\"properties\":{},\"sourceNodeId\":\"c8e23610-1862-46e3-a195-047595e7efd3\",\"targetNodeId\":\"d495fbc1-1dde-4c4f-8792-ddc98f266016\",\"sourceAnchorId\":\"c8e23610-1862-46e3-a195-047595e7efd3_1\",\"targetAnchorId\":\"d495fbc1-1dde-4c4f-8792-ddc98f266016_3\",\"startPoint\":{\"x\":695,\"y\":166},\"endPoint\":{\"x\":783,\"y\":164},\"pointsList\":[{\"x\":695,\"y\":166},{\"x\":739,\"y\":166},{\"x\":739,\"y\":164},{\"x\":783,\"y\":164}]}]}');

-- --------------------------------------------------------

--
-- 表的结构 `sys_flow_record`
--

CREATE TABLE `sys_flow_record` (
  `id` int(11) NOT NULL,
  `flowId` int(10) NOT NULL COMMENT '所属流程',
  `userId` int(10) NOT NULL COMMENT '节点处理人id 0表示系统',
  `dateTime` datetime NOT NULL COMMENT '处理时间',
  `remark` varchar(200) DEFAULT NULL COMMENT '处理意见',
  `status` int(2) NOT NULL COMMENT '1同意 2拒绝 3返回发起人 4撤回 5抄送同意 6系统通过',
  `nodeId` varchar(50) NOT NULL COMMENT '当前节点id',
  `nodeName` varchar(50) NOT NULL COMMENT '当前节点名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- 转存表中的数据 `sys_flow_record`
--

INSERT INTO `sys_flow_record` (`id`, `flowId`, `userId`, `dateTime`, `remark`, `status`, `nodeId`, `nodeName`) VALUES
(46, 26, 1, '2025-06-05 11:34:22', '不同意', 2, '779800dd-98be-4ac3-87ac-bf10ff20180b', '用户任务'),
(47, 28, 1, '2025-06-05 11:37:19', NULL, 1, '779800dd-98be-4ac3-87ac-bf10ff20180b', '用户任务'),
(48, 28, 1, '2025-06-05 11:37:19', '抄送成功', 5, 'c8e23610-1862-46e3-a195-047595e7efd3', '抄送'),
(49, 28, 5, '2025-06-05 11:37:19', '抄送成功', 5, 'c8e23610-1862-46e3-a195-047595e7efd3', '抄送'),
(50, 30, 1, '2025-06-05 11:44:10', NULL, 1, '4257ec39-ae7a-4a82-963d-cdeb7e2c9082', '主管审批'),
(51, 32, 3, '2025-06-05 11:48:34', NULL, 1, '4257ec39-ae7a-4a82-963d-cdeb7e2c9082', '主管审批'),
(52, 32, 3, '2025-06-05 11:49:25', NULL, 1, '723d2767-9b50-4e0d-9039-7462279acd5c', '经理审批');

-- --------------------------------------------------------

--
-- 表的结构 `sys_user`
--

CREATE TABLE `sys_user` (
  `id` int(10) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `leaderId` int(10) DEFAULT NULL COMMENT '上级id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT='用户表';

--
-- 转存表中的数据 `sys_user`
--

INSERT INTO `sys_user` (`id`, `userName`, `leaderId`) VALUES
(1, 'user', 3),
(2, 'user2', 1),
(3, 'manage', 2),
(4, 'admin', 0),
(5, 'hr', 3);

--
-- 转储表的索引
--

--
-- 表的索引 `sys_flow`
--
ALTER TABLE `sys_flow`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `sys_flow_design`
--
ALTER TABLE `sys_flow_design`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `sys_flow_record`
--
ALTER TABLE `sys_flow_record`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `sys_user`
--
ALTER TABLE `sys_user`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `sys_flow`
--
ALTER TABLE `sys_flow`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- 使用表AUTO_INCREMENT `sys_flow_design`
--
ALTER TABLE `sys_flow_design`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- 使用表AUTO_INCREMENT `sys_flow_record`
--
ALTER TABLE `sys_flow_record`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- 使用表AUTO_INCREMENT `sys_user`
--
ALTER TABLE `sys_user`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
