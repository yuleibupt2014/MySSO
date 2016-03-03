package wtist.web.sso.mapper;

import wtist.web.sso.dao.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	User selectByAccountId(String accountId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
}