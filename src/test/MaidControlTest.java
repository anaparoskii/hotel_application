package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.MaidControl;
import entity.RoomStatus;
import entity.RoomType;
import manager.ManagerFactory;

public class MaidControlTest {
	
	ManagerFactory managers;
	MaidControl maidControl;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("maid control test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("maid control test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  managers = new ManagerFactory();
	  maidControl = new MaidControl(managers);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCleanRoom() {
	  managers.getEmployeeManager().createMaid(2, "imenka", "prezimic", null, null, "060000000", "Ulica 3", "imeeep", "i1p2", 0, 0, 0);
	  managers.getRoomManager().createRoom(RoomType.DOUBLE_BED, 1, null);
	  managers.getRoomManager().findRoomByID(1).setStatus(RoomStatus.PREPARATION);
	  maidControl.cleanRoom("imenka", "prezimic", managers.getRoomManager().findRoomByID(1));
	  Assert.assertTrue(managers.getRoomManager().findRoomByID(1).getStatus().equals(RoomStatus.AVAILABLE));
  }
}
