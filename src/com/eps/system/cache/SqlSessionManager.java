package com.eps.system.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionManager {
	
	private SqlSessionManager sqlSessionManager;
	
	private static SqlSessionFactory sqlSessionFactory;
	
	public SqlSessionManager(){
		
	}
	
	public SqlSessionManager getInstance(){
		if(sqlSessionManager == null)
			sqlSessionManager = new SqlSessionManager();
		return sqlSessionManager;
	}

	public SqlSession getSqlSession() throws IOException{
		
		String resource = "dbconfig.xml";
        String path = CacheManager.getConfigPath() + resource;
        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = new FileInputStream(new File(path));
        //构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
		
		return session;
	}
	
	public static SqlSessionFactory getSqlSessionFactory() throws IOException{
		
		if(sqlSessionFactory == null){
			String resource = "dbconfig.xml";
	        String path = CacheManager.getConfigPath() + resource;
	        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
	        InputStream is = new FileInputStream(new File(path));
	        //构建sqlSession的工厂
	        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		}
		
		return sqlSessionFactory;
	}
	
}
