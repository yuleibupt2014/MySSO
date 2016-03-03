package wtist.web.sso.mapper;

import wtist.web.sso.dao.Service;

public interface ServiceMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Service record);

	int insertSelective(Service record);

	Service selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Service record);

	int updateByPrimaryKey(Service record);
}