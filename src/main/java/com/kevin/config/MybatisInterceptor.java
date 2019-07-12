package com.kevin.config;


import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;

/**
 * page 处理拦截器
 * 
 * @author tzj
 *
 */
@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class MybatisInterceptor implements Interceptor {

	Properties properties;

	Logger logger = LoggerFactory.getLogger(getClass());

	String COUNT_SQL = "select count(*) from ({0}) t";

	final String EXECUTE_SQL_KEY = "delegate.boundSql.sql";

	final String MAPPERSTATEMENT_KEY = "delegate.mappedStatement";

	final String PARAMTERHANGLER_KEY = "delegate.parameterHandler";

	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
				SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
		MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(MAPPERSTATEMENT_KEY);
		String id = mappedStatement.getId();

		if (!id.matches(".+Page$")) {
			return invocation.proceed();
		}

		BoundSql boundSql = statementHandler.getBoundSql();
		Object paramObj = boundSql.getParameterObject();

		PageInfo page = null;
		if (paramObj instanceof Map) {
			Map<String, Object> params = (Map<String, Object>) paramObj;
			if (!params.containsKey("arg0")) {
				return invocation.proceed();
			}
			page = (PageInfo) params.get("arg0");
		} else if (paramObj instanceof PageInfo) {
			page = (PageInfo) paramObj;
		}

		if (page == null) {
			return invocation.proceed();
		}
		
		

		String sql = boundSql.getSql();
		
		if(page.getTotalCount()==null) {
			String countSql = MessageFormat.format(COUNT_SQL, sql);
			Connection connection = (Connection) invocation.getArgs()[0];
			PreparedStatement countStatement = connection.prepareStatement(countSql);
			logger.info("countsql:{}",countSql);
			ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue(PARAMTERHANGLER_KEY);
			parameterHandler.setParameters(countStatement);
			ResultSet rs = countStatement.executeQuery();
			
			if (rs.next()) {
				page.setTotalCount(rs.getInt(1));
				if(logger.isDebugEnabled()) {
					logger.debug("拦截器得知page的记录总数为：" + page.getTotalCount());
				}
			}
		}

		StringBuilder executePageSql = new StringBuilder(sql);
		executePageSql.append(" LIMIT ");
		executePageSql.append((page.getPageNo() - 1) * page.getPageSize());
		executePageSql.append(",");
		executePageSql.append(page.getPageSize());

		metaObject.setValue(EXECUTE_SQL_KEY, executePageSql.toString());

		return invocation.proceed();

	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

}
