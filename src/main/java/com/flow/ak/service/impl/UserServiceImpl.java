package com.flow.ak.service.impl;

import com.alibaba.fastjson2.JSON;
import com.flow.ak.entity.User;
import com.flow.ak.dao.UserDao;
import com.flow.ak.service.UserService;
import org.springframework.stereotype.Service;

import com.flow.ak.utils.Utils;

import jakarta.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 用户表(User)表服务实现类
 *
 * @author ak.design 337547038
 * @since 2025-05-22 17:30:07
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
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
        User user = JSON.parseObject(JSON.toJSONString(map.get("query")), User.class);//json字符串转java对象
        
        long total = this.userDao.count(user);
        List<Map<String,Object>> list = this.userDao.queryAllByLimit(user,map.get("extend"));
        Map<String, Object> response = new HashMap<>();
        response.put("list", list);
        response.put("total", total);
        return response;
    }

    @Override
    public List<Map<String,Object>> queryByIds(String ids){
        return this.userDao.queryByIds(ids);
    }
    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        this.userDao.insert(user);
        return user;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响的行数
     */
    @Override
    public Integer updateById(User user) {
        return this.userDao.updateById(user);
        //return this.queryById(user.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String[] id) {
        return this.userDao.deleteById(id) > 0;
    }
}
