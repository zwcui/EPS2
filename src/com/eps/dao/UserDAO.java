package com.eps.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.eps.bean.Role;
import com.eps.bean.User;
import com.eps.bean.UserRole;

/** 
* �����@MapperScan��������������Mapperɨ����������Ҫ�����ã����Զ����ɴ������ 
* ע�⣬�ӿ��еķ�������Ҫ�Ͷ�Ӧ��MyBatisӳ���ļ��е�����idֵһ������Ϊ���ɵ� 
* ��̬������������ƥ����Ӧ��Sql���ִ�С�������Ƿ����Ĳ����ͷ���ֵҲ��Ҫע 
* �⡣�ӿ��еķ�����ζ��壬��Ӧ��MyBatisӳ���ļ���Ӧ�ý�����Ӧ�Ķ��塣 
* ��󣬱�ע�е�userDao��������ΪSpring��Bean��id(��name)����ʹ�õģ������� 
* ����Service�����ע��ʹ�á� 
*/ 
@MapperScan
public interface UserDAO {

	//�˴��ķ����������mapper�е�ӳ���ļ��е�idͬ��
	//��ȥӳ���ļ���ͨ��com.hua.saf.dao.UserDao.getUser,��this.getClass().getName()+".getUser"	
	public User getUserByUserID(String userID);
	
	public List<User> getAllUser();
	
	public void insertUser(User user);

	public void updateUser(User user);
	
	public int updateUserPassword(User user);

	public void deleteUser(String userID);
	
	
	public UserRole getUserRole(Map paramMap);
	
	public List<UserRole> getUserRoleByUserID(String userID);
	
	public List<User> getUserFromRoleID(String roleID);

	public void insertUserRole(UserRole userRole);
	
	public void deleteUserRole(UserRole userRole);
	
	
	public List<Role> getAllRole();
	
	public Role getRoleByRoleID(String roleID);
	
	public void insertRole(Role role);
	
	public void updateRole(Role role);
	
	public void deleteRoleByRoleID(String roleID);
	
	public List<Role> getRolesByUserID(String userID);
	
}
