package wtist.web.sso.cache;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisCache {
	@Autowired
	private JedisPool jedisPool;

	public void put(String key, String value, int seconds) {
		Jedis j = jedisPool.getResource();
		j.sadd(key, value);
		if (seconds > 0)
			j.setex(key, seconds, value);
	}

	public Set<String> get(String key) {
		return jedisPool.getResource().smembers(key);
	}

	public String getLastLoginTime(String account) {
		Jedis j = jedisPool.getResource();
		List<String> l = j.lrange(account, 0, 0);
		String lastLoginTime = "";
		if (!l.isEmpty())
			lastLoginTime = l.get(0);
		return lastLoginTime;
	}

	public void del(String key) {
		jedisPool.getResource().del(key);
	}

	public Boolean isExist(String key) {
		Jedis j = jedisPool.getResource();
		return j.exists(key);
	}

	public String saveLoginTime(String account, String nowtime) {
		Jedis j = jedisPool.getResource();
		List<String> l = j.lrange(account, 0, 0);
		String lastLoginTime = "";
		if (!l.isEmpty())
			lastLoginTime = l.get(0);
		j.lpush(account, nowtime);
		return lastLoginTime;
	}

	public String saveLoginID(String account, String nowtime) {
		Jedis j = jedisPool.getResource();
		List<String> l = j.lrange(account, 0, 0);
		String lastLoginTime = "";
		if (!l.isEmpty())
			lastLoginTime = l.get(0);
		j.lpush(account, nowtime);
		return lastLoginTime;
	}

	public void saveUsername(String account, String username, int seconds) {
		Jedis j = jedisPool.getResource();
		j.lpush("username." + account, username);

	}

	public String getUsername(String account) {
		Jedis j = jedisPool.getResource();
		List<String> l = null;
		if (j.exists("username." + account)) {
			l = j.lrange("username." + account, 0, 0);
		}
		String username = null;
		if (l != null && !l.isEmpty())
			username = l.get(0);
		return username;
	}

}
