package test;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.CheckInControl;
import entity.AdditionalService;
import entity.Guest;
import entity.Pricing;
import entity.ReservationStatus;
import entity.Room;
import entity.RoomPrice;
import entity.RoomStatus;
import entity.RoomType;
import manager.ManagerFactory;

public class CheckInControlTest {
	ManagerFactory managers;
	CheckInControl checkInControl;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("check in control test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("check in control test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  managers = new ManagerFactory();
	  checkInControl = new CheckInControl(managers);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCheckInProcess() {
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
	  checkInControl.checkInProcess(1000, null);
	  Assert.assertFalse(managers.getReservationManager().findReservationByID(1000).getRoom().getStatus().equals(RoomStatus.AVAILABLE));
	  Assert.assertTrue(managers.getReservationManager().findReservationByID(1000).getRoom().getStatus().equals(RoomStatus.TAKEN));
	  Assert.assertFalse(managers.getReservationManager().findReservationByID(1000).isActive());
  }
}
