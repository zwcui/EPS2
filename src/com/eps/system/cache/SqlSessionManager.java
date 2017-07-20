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
        //ʹ��MyBatis�ṩ��Resources�����mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
        InputStream is = new FileInputStream(new File(path));
        //����sqlSession�Ĺ���
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        //������ִ��ӳ���ļ���sql��sqlSession
        SqlSession session = sessionFactory.openSession();
		
		return session;
	}
	
	public static SqlSessionFactory getSqlSessionFactory() throws IOException{
		
		if(sqlSessionFactory == null){
			String resource = "dbconfig.xml";
	        String path = CacheManager.getConfigPath() + resource;
	        //ʹ��MyBatis�ṩ��Resources�����mybatis�������ļ�����Ҳ���ع�����ӳ���ļ���
	        InputStream is = new FileInputStream(new File(path));
	        //����sqlSession�Ĺ���
	        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
		}
		
		return sqlSessionFactory;
	}
	
}
