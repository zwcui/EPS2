package com.eps.dao;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.eps.bean.Org;

@MapperScan
public interface OrgDAO {

	public List<Org> getAllOrg();
	
	public Org getOrgByOrgID(String orgID);
	
	public void insertOrg(Org org);
	
	public void updateOrg(Org org);
	
	public int deleteOrg(String orgID);
	
}
