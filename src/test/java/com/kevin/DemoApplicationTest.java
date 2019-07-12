package com.kevin;

import com.kevin.config.PageInfo;
import com.kevin.dao.DeviceMapper;
import com.kevin.dao.daoExt.DeviceMapperExt;
import com.kevin.model.Device;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2019-05-09
 * Time: 14:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.kevin.dao")
public class DemoApplicationTest {
    @Autowired
    DeviceMapperExt deviceMapper;

    @Test
    public void deviceTest(){
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
        long id = 1L;
        Device device = deviceMapper.selectByPrimaryKey(id);
        System.out.println(device);
    }

    @Test
    public void devicePageTest(){
        PageInfo pageInfo = new PageInfo(1,3);
        List<Device> devices = deviceMapper.selectPage(pageInfo);
        System.out.println(devices);
        System.out.println(pageInfo);
    }

}