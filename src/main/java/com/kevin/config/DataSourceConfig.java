package com.kevin.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 *  数据源配置
 * @author
 *
 */
//@Configuration
//@MapperScan(basePackages = {"com.kevin.dao" }, sqlSessionFactoryRef = "sqlSessionFactoryPrimary")
public class DataSourceConfig {

	/**
	 * 数据源
	 */
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	
	/**
	 * 创建数据源
	 * @return
	 */
    @Bean(name = "dataSource",initMethod = "init")
    @ConfigurationProperties(prefix="spring.datasource.primary")
    @Primary
    public DruidDataSource primaryDataSource() {

		DruidDataSource dataSource=new DruidDataSource();

        return dataSource;
    }

    
    /**
     * 创建sessionfactory
     * @return
     * @throws Exception
     */
    @Bean
	public SqlSessionFactory sqlSessionFactoryPrimary() throws Exception {
			SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		// 使用primary数据源, 连接primary库
			
			
			factoryBean.setDataSource(dataSource);
			factoryBean.setPlugins(new Interceptor[] {new MybatisInterceptor()});

			org.apache.ibatis.session.Configuration configuration=factoryBean.getObject().getConfiguration();
			configuration.setDefaultExecutorType(ExecutorType.REUSE);
			
			return new DefaultSqlSessionFactory(configuration);

		}
    
    

	@Bean
	public SqlSessionTemplate sqlSessionTemplatePrimary() throws Exception {
			SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryPrimary()); // 使用上面配置的Factory
			
			return template;
	}
	
	/**
	 * 事物
	 * @param primaryDataSource
	 * @return
	 */
	@Bean
	public PlatformTransactionManager primaryTransactionManager(@Qualifier("dataSource") DataSource primaryDataSource) {
	    return new DataSourceTransactionManager(primaryDataSource);
	}

}
