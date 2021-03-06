package com.kevin.dao;

import com.kevin.model.Device;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface DeviceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEVICE
     *
     * @mbg.generated Thu May 09 14:35:21 CST 2019
     */
    @Delete({
        "delete from DEVICE",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEVICE
     *
     * @mbg.generated Thu May 09 14:35:21 CST 2019
     */
    @Insert({
        "insert into DEVICE (device_serial_number, device_mac, ",
        "tenancy_id, create_at, ",
        "update_at)",
        "values (#{deviceSerialNumber,jdbcType=VARCHAR}, #{deviceMac,jdbcType=VARCHAR}, ",
        "#{tenancyId,jdbcType=BIGINT}, #{createAt,jdbcType=TIMESTAMP}, ",
        "#{updateAt,jdbcType=TIMESTAMP})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insert(Device record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEVICE
     *
     * @mbg.generated Thu May 09 14:35:21 CST 2019
     */
    @InsertProvider(type=DeviceSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Long.class)
    int insertSelective(Device record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEVICE
     *
     * @mbg.generated Thu May 09 14:35:21 CST 2019
     */
    @Select({
        "select",
        "id, device_serial_number, device_mac, tenancy_id, create_at, update_at",
        "from DEVICE",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="device_serial_number", property="deviceSerialNumber", jdbcType=JdbcType.VARCHAR),
        @Result(column="device_mac", property="deviceMac", jdbcType=JdbcType.VARCHAR),
        @Result(column="tenancy_id", property="tenancyId", jdbcType=JdbcType.BIGINT),
        @Result(column="create_at", property="createAt", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_at", property="updateAt", jdbcType=JdbcType.TIMESTAMP)
    })
    Device selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEVICE
     *
     * @mbg.generated Thu May 09 14:35:21 CST 2019
     */
    @UpdateProvider(type=DeviceSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Device record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEVICE
     *
     * @mbg.generated Thu May 09 14:35:21 CST 2019
     */
    @Update({
        "update DEVICE",
        "set device_serial_number = #{deviceSerialNumber,jdbcType=VARCHAR},",
          "device_mac = #{deviceMac,jdbcType=VARCHAR},",
          "tenancy_id = #{tenancyId,jdbcType=BIGINT},",
          "create_at = #{createAt,jdbcType=TIMESTAMP},",
          "update_at = #{updateAt,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Device record);
}