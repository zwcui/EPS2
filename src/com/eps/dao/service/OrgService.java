package com.eps.dao.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eps.bean.Org;
import com.eps.dao.OrgDAO;

@Service
@Transactional
public class OrgService extends BaseService {

	private OrgDAO orgDAO;

	@Resource(name="orgDAO")
	public void setOrgDAO(OrgDAO orgDAO) {
		this.orgDAO = orgDAO;
	}
	
	public List<Org> getAllOrg(){
		logger.info("getAllOrg()");
		return orgDAO.getAllOrg();
	}
	
	public Org getOrgByOrgID(String orgID){
		logger.info("getOrgByOrgID()");
		return orgDAO.getOrgByOrgID(orgID);
	}
	
	public void insertOrg(Org org){
		logger.info("insertOrg()");
		orgDAO.insertOrg(org);
	}
	
	public void updateOrg(Org org){
		logger.info("updateOrg()");
		orgDAO.updateOrg(org);
	}
	
	public int deleteOrg(String orgID){
		logger.info("deleteOrg()");
		return orgDAO.deleteOrg(orgID);
	}
	
}
