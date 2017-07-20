package com.eps.dao.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eps.bean.Role;
import com.eps.bean.User;
import com.eps.bean.UserRole;
import com.eps.dao.UserDAO;

@Service
@Transactional
public class UserService extends BaseService{

	private UserDAO userDAO;
	
	@Resource(name="userDAO")
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public User getUserByUserID(String userID) {
		//sqlSessionFactory.getConfiguration().getMappedStatement("com.dao.ResourceDao.save").getBoundSql(null).getSql()
		logger.info("getUserByUserID("+userID+")");
		return userDAO.getUserByUserID(userID);
	}

	public List<User> getAllUser(){
		logger.info("-------------------------getAllUser()");
		return userDAO.getAllUser();
	}
	
	public void insertUser(User user){
		logger.info("insertUser()");
		userDAO.insertUser(user);
	}
	
	public void updateUser(User user){
		logger.info("updateUser()");
		userDAO.updateUser(user);
	}
	
	public int updateUserPassword(User user){
		logger.info("updateUserPassword()");
		return userDAO.updateUserPassword(user);
	}
	
	public void deleteUser(String userID){
		logger.info("deleteUser()");
		userDAO.deleteUser(userID);
	}
	
	public List<Role> getAllRole(){
		logger.info("getAllRole()");
		return userDAO.getAllRole();
	}
	
	public Role getRoleByRoleID(String roleID){
		logger.info("getRoleByRoleID()");
		return userDAO.getRoleByRoleID(roleID);
	}
	
	public void insertRole(Role role){
		logger.info("insertRole()");
		userDAO.insertRole(role);
	}
	
	public void updateRole(Role role){
		logger.info("updateRole()");
		userDAO.updateRole(role);
	}
	
	public void deleteRoleByRoleID(String roleID){
		logger.info("deleteRoleByRoleID()");
		userDAO.deleteRoleByRoleID(roleID);
	}
	
	public UserRole getUserRole(Map paramMap){
		logger.info("getUserRole()");
		return userDAO.getUserRole(paramMap);
	}
	
	public List<UserRole> getUserRoleByUserID(String userID){
		logger.info("getUserRoleByUserID()");
		return userDAO.getUserRoleByUserID(userID);
	}	
	
	public void insertUserRole(UserRole userRole){
		logger.info("insertUserRole()");
		userDAO.insertUserRole(userRole);
	}
	
	public void deleteUserRole(UserRole userRole){
		logger.info("deleteUserRole()");
		userDAO.deleteUserRole(userRole);
	}
	
	public List<User> getUserFromRoleID(String roleID){
		logger.info("deleteUserRole()");
		return userDAO.getUserFromRoleID(roleID);
	}
	
	public List<Role> getRolesByUserID(String userID){
		logger.info("getRolesByUserID()");
		return userDAO.getRolesByUserID(userID);
	}
}
