package test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.RoomType;
import manager.FormatManager;
import manager.RoomManager;

public class RoomManagerTest {
	
	RoomManager roomManager;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("room manager test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("room manager test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  roomManager = new RoomManager();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCreateRoom() {
	  roomManager.createRoom(RoomType.SINGLE_BED, 1, null);
	  Assert.assertEquals(1, roomManager.getAllRooms().size());
  }

  @Test
  public final void testUpdateRoom() {
	  roomManager.createRoom(RoomType.SINGLE_BED, 1, null);
	  roomManager.updateRoom(1, RoomType.DOUBLE_BED);
	  Assert.assertFalse(roomManager.findRoomByID(1).getType().equals(RoomType.SINGLE_BED));
	  Assert.assertTrue(roomManager.findRoomByID(1).getType().equals(RoomType.DOUBLE_BED));
  }

  @Test
  public final void testDeleteRoom() {
	  roomManager.createRoom(RoomType.SINGLE_BED, 1, null);
	  roomManager.deleteRoom(1);
	  Assert.assertEquals(0, roomManager.getAllRooms().size());
  }
  
  @Test
  public final void testIsAvailable() {
	  roomManager.createRoom(RoomType.SINGLE_BED, 1, null);
	  LocalDate dateToCheck = LocalDate.now();
	  Assert.assertTrue(roomManager.isAvailable(dateToCheck, roomManager.findRoomByID(1)));
	  ArrayList<Date> checkIns = new ArrayList<>();
	  ArrayList<Date> checkOuts = new ArrayList<>();
	  LocalDate dateToCheck1 = LocalDate.now().minusDays(1);
	  LocalDate dateToCheck2 = LocalDate.now().plusDays(7);
	  FormatManager formatManager = new FormatManager();
	  checkIns.add(formatManager.asDate(dateToCheck1));
	  checkOuts.add(formatManager.asDate(dateToCheck2));
	  roomManager.findRoomByID(1).setCheckInDate(checkIns);
	  roomManager.findRoomByID(1).setCheckOutDate(checkOuts);
	  Assert.assertFalse(roomManager.isAvailable(dateToCheck, roomManager.findRoomByID(1)));
  }
}
