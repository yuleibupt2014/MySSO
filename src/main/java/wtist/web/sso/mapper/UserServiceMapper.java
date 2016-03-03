package wtist.web.sso.mapper;

import wtist.web.sso.dao.UserService;

public interface UserServiceMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(UserService record);

	int insertSelective(UserService record);

	UserService selectByPrimaryKey(Integer id);

	UserService selectByUserIdAndServiceId(UserService request);

	int updateByPrimaryKeySelective(UserService record);

	int updateByPrimaryKey(UserService record);
}