package wtist.web.sso.cache;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import wtist.web.sso.BaseTest;

public class RedisCacheTest extends BaseTest {
	@Autowired
	private JedisPool jedisPool;

	@Test
	public void testSet() {
		Jedis jedis = jedisPool.getResource();
		jedis.set("accountid", "123456789");

		String accountId = jedis.get("accountid");
		System.out.println(accountId);

		jedis.expire("accountid", 5);
		try {
			Thread.sleep(6000);
		} catch (Exception e) {
		}
		accountId = jedis.get("accountid");
		System.out.println(accountId);

		jedis.del("accountid");
		accountId = jedis.get("accountid");
		System.out.println(accountId);

	}
}
