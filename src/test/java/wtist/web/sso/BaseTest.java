package wtist.web.sso;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wtist.web.sso.Encryption.Encryption;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class BaseTest {
	@Test
	public void print() {
		System.out.println(Encryption.MD5("123"));
	}
}
