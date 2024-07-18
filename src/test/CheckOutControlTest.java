package test;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.CheckOutControl;
import entity.AdditionalService;
import entity.Guest;
import entity.Maid;
import entity.Pricing;
import entity.ReservationStatus;
import entity.Room;
import entity.RoomPrice;
import entity.RoomStatus;
import entity.RoomType;
import manager.ManagerFactory;

public class CheckOutControlTest {
	ManagerFactory managers;
	CheckOutControl checkOutControl;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("check out control test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("check out control test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  managers = new ManagerFactory();
	  checkOutControl = new CheckOutControl(managers);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCheckOutProcess() {
	  managers.getEmployeeManager().createMaid(2, "imenka", "prezimic", null, null, "060000000", "Ulica 3", "imeeep", "i1p2", 0, 0, 0);
	  managers.getGuestManager().createGuest(0, "imenko", "prezimic", null, null, "060123456", "Ulice 1", "imeprezzzz", "123456789");
	  Guest guest = managers.getGuestManager().findGuestByID(0);
	  managers.getPricingManager().createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  Pricing pricing = managers.getPricingManager().findPricingByID(1000);
	  managers.getPricingManager().addNewServicePricing(new AdditionalService(0, "lunch"), 100, pricing);
	  RoomPrice roomPrice = new RoomPrice(RoomType.SINGLE_BED, 100);
	  ArrayList<RoomPrice> list = new ArrayList<>();
	  list.add(roomPrice);
	  pricing.setRoomPrice(list);
	  managers.getReservationManager().createReservation(guest, RoomType.SINGLE_BED, LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 
			  null, managers.getPricingManager(), null);
	  managers.getReservationManager().updateReservation(1000, ReservationStatus.ACCEPTED);
	  managers.getReservationManager().findReservationByID(1000).setRoom(new Room(RoomType.SINGLE_BED, 1, null));
	  Assert.assertTrue(managers.getReservationManager().findReservationByID(1000).getRoom().getStatus().equals(RoomStatus.AVAILABLE));
	  checkOutControl.checkOutProcess(managers.getReservationManager().findReservationByID(1000));
	  Assert.assertFalse(managers.getReservationManager().findReservationByID(1000).getRoom().getStatus().equals(RoomStatus.AVAILABLE));
	  Assert.assertTrue(managers.getReservationManager().findReservationByID(1000).getRoom().getStatus().equals(RoomStatus.PREPARATION));
  }

  @Test
  public final void testAsignRoomToMaid() {
	  Room r = new Room(RoomType.SINGLE_BED, 1, null);
	  managers.getEmployeeManager().createMaid(2, "imenka", "prezimic", null, null, "060000000", "Ulica 3", "imeeep", "i1p2", 0, 0, 0);
	  checkOutControl.asignRoomToMaid(r);
	  Maid m = (Maid) managers.getEmployeeManager().findEmployeeByID(2);
	  Assert.assertEquals(1, m.getRoomsToClean().size());
  }
}
