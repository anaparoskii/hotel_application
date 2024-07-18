package test;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.ReservationControl;
import entity.Guest;
import entity.Pricing;
import entity.ReservationStatus;
import entity.RoomPrice;
import entity.RoomType;
import manager.ManagerFactory;

public class ReservationControlTest {
	
	ManagerFactory managers;
	ReservationControl reservationControl;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("reservation control test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("reservation control test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  managers = new ManagerFactory();
	  reservationControl = new ReservationControl(managers);
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCheckReservation() {
	  managers.getGuestManager().createGuest(0, "imenko", "prezimic", null, null, "060123456", "Ulice 1", "imeprezzzz", "123456789");
	  Guest guest = managers.getGuestManager().findGuestByID(0);
	  managers.getPricingManager().createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  Pricing pricing = managers.getPricingManager().findPricingByID(1000);
	  RoomPrice roomPrice = new RoomPrice(RoomType.SINGLE_BED, 100);
	  ArrayList<RoomPrice> list = new ArrayList<>();
	  list.add(roomPrice);
	  pricing.setRoomPrice(list);
	  managers.getRoomManager().createRoom(RoomType.SINGLE_BED, 1, null);
	  managers.getReservationManager().createReservation(guest, RoomType.SINGLE_BED, LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 
			  null, managers.getPricingManager(), null);
	  managers.getReservationManager().createReservation(guest, RoomType.SINGLE_BED, LocalDate.now().minusDays(3), LocalDate.now().plusDays(3), 
			  null, managers.getPricingManager(), null);
	  reservationControl.checkReservation(managers.getReservationManager().findReservationByID(1000));
	  Assert.assertTrue(managers.getReservationManager().findReservationByID(1000).getStatus().equals(ReservationStatus.ACCEPTED));
	  reservationControl.checkReservation(managers.getReservationManager().findReservationByID(1001));
	  Assert.assertTrue(managers.getReservationManager().findReservationByID(1001).getStatus().equals(ReservationStatus.DECLINED));
  }

  @Test
  public final void testCancelReservation() {
	  managers.getGuestManager().createGuest(0, "imenko", "prezimic", null, null, "060123456", "Ulice 1", "imeprezzzz", "123456789");
	  Guest guest = managers.getGuestManager().findGuestByID(0);
	  managers.getPricingManager().createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  Pricing pricing = managers.getPricingManager().findPricingByID(1000);
	  RoomPrice roomPrice = new RoomPrice(RoomType.SINGLE_BED, 100);
	  ArrayList<RoomPrice> list = new ArrayList<>();
	  list.add(roomPrice);
	  pricing.setRoomPrice(list);
	  managers.getReservationManager().createReservation(guest, RoomType.SINGLE_BED, LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 
			  null, managers.getPricingManager(), null);
	  reservationControl.cancelReservation(managers.getReservationManager().findReservationByID(1000));
	  Assert.assertTrue(managers.getReservationManager().findReservationByID(1000).getDateCanceled() != null);
	  Assert.assertTrue(managers.getReservationManager().findReservationByID(1000).getStatus().equals(ReservationStatus.CANCELED));
  }
}
