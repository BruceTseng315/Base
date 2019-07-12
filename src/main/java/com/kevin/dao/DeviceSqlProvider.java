package com.kevin.dao;

import com.kevin.model.Device;
import org.apache.ibatis.jdbc.SQL;

public class DeviceSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEVICE
     *
     * @mbg.generated Thu May 09 14:35:21 CST 2019
     */
    public String insertSelective(Device record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("DEVICE");
        
        if (record.getDeviceSerialNumber() != null) {
            sql.VALUES("device_serial_number", "#{deviceSerialNumber,jdbcType=VARCHAR}");
        }
        
        if (record.getDeviceMac() != null) {
            sql.VALUES("device_mac", "#{deviceMac,jdbcType=VARCHAR}");
        }
        
        if (record.getTenancyId() != null) {
            sql.VALUES("tenancy_id", "#{tenancyId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateAt() != null) {
            sql.VALUES("create_at", "#{createAt,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateAt() != null) {
            sql.VALUES("update_at", "#{updateAt,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DEVICE
     *
     * @mbg.generated Thu May 09 14:35:21 CST 2019
     */
    public String updateByPrimaryKeySelective(Device record) {
        SQL sql = new SQL();
        sql.UPDATE("DEVICE");
        
        if (record.getDeviceSerialNumber() != null) {
            sql.SET("device_serial_number = #{deviceSerialNumber,jdbcType=VARCHAR}");
        }
        
        if (record.getDeviceMac() != null) {
            sql.SET("device_mac = #{deviceMac,jdbcType=VARCHAR}");
        }
        
        if (record.getTenancyId() != null) {
            sql.SET("tenancy_id = #{tenancyId,jdbcType=BIGINT}");
        }
        
        if (record.getCreateAt() != null) {
            sql.SET("create_at = #{createAt,jdbcType=TIMESTAMP}");
        }
        
        if (record.getUpdateAt() != null) {
            sql.SET("update_at = #{updateAt,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }
}