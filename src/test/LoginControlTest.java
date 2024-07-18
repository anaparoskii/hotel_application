package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.LoginControl;
import manager.ManagerFactory;

public class LoginControlTest {
	
	ManagerFactory managers;
	LoginControl loginControl;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("login control test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("login control test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  managers = new ManagerFactory();
	  loginControl = new LoginControl(managers);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testLoginGuestSuccess() {
	  managers.getGuestManager().createGuest(0, "imenko", "prezimic", null, null, "060123456", "Ulice 1", "imeprezzzz", "123456789");
	  Assert.assertFalse(loginControl.loginGuestSuccess("imeprez", "123456789"));
	  Assert.assertTrue(loginControl.loginGuestSuccess("imeprezzzz", "123456789"));
  }

  @Test
  public final void testLoginEmployeeSuccess() {
	  managers.getEmployeeManager().createAdmin(0, "ime", "prezime", null, null, "060000000", "Ulica 1", "imeprez", "ip123", 0, 0, 0);
	  Assert.assertFalse(loginControl.loginEmployeeSuccess("imeprez", "123456789"));
	  Assert.assertTrue(loginControl.loginEmployeeSuccess("imeprez", "ip123"));
  }
}
