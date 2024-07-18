package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import manager.GuestManager;

public class GuestManagerTest {
	
	GuestManager guestManager;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("guest manager test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("guest manager test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  guestManager = new GuestManager();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCreateGuest() {
	  guestManager.createGuest(0, "imenko", "prezimic", null, null, "060123456", "Ulice 1", "imeprezzzz", "123456789");
	  Assert.assertEquals(1, guestManager.getAllGuests().size());
  }

  @Test
  public final void testDeleteGuest() {
	  guestManager.createGuest(0, "imenko", "prezimic", null, null, "060123456", "Ulice 1", "imeprezzzz", "123456789");
	  guestManager.deleteGuest(0);
	  Assert.assertEquals(0, guestManager.getAllGuests().size());
  }
}
