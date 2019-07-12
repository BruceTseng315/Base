package com.kevin.dao.daoExt;

import com.kevin.config.PageInfo;
import com.kevin.dao.DeviceMapper;
import com.kevin.model.Device;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2019-05-09
 * Time: 14:41
 */
public interface DeviceMapperExt extends DeviceMapper {
    @Select({
            "select",
            "id, device_serial_number, device_mac, tenancy_id, create_at, update_at",
            "from DEVICE"

    })
    @Results({
            @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="device_serial_number", property="deviceSerialNumber", jdbcType=JdbcType.VARCHAR),
            @Result(column="device_mac", property="deviceMac", jdbcType=JdbcType.VARCHAR),
            @Result(column="tenancy_id", property="tenancyId", jdbcType=JdbcType.BIGINT),
            @Result(column="create_at", property="createAt", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="update_at", property="updateAt", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Device> selectPage(PageInfo pageInfo);
}
