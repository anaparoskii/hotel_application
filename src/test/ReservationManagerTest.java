package test;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.Guest;
import entity.Pricing;
import entity.ReservationStatus;
import entity.RoomPrice;
import entity.RoomType;
import manager.GuestManager;
import manager.PricingManager;
import manager.ReservationManager;

public class ReservationManagerTest {
	
	ReservationManager reservationManager;
	PricingManager pricingManager;
	GuestManager guestManager;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("reservation manager test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("reservation manager test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  reservationManager = new ReservationManager();
	  pricingManager = new PricingManager();
	  guestManager = new GuestManager();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCreateReservation() {
	  guestManager.createGuest(0, "imenko", "prezimic", null, null, "060123456", "Ulice 1", "imeprezzzz", "123456789");
	  Guest guest = guestManager.findGuestByID(0);
	  pricingManager.createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  Pricing pricing = pricingManager.findPricingByID(1000);
	  RoomPrice roomPrice = new RoomPrice(RoomType.SINGLE_BED, 100);
	  ArrayList<RoomPrice> list = new ArrayList<>();
	  list.add(roomPrice);
	  pricing.setRoomPrice(list);
	  reservationManager.createReservation(guest, RoomType.SINGLE_BED, LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 
			  null, pricingManager, null);
	  Assert.assertEquals(1, reservationManager.getAllReservations().size());
	  Assert.assertTrue(reservationManager.getAllReservations().get(1000).getPrice() == 400);
  }

  @Test
  public final void testUpdateReservation() {
	  guestManager.createGuest(0, "imenko", "prezimic", null, null, "060123456", "Ulice 1", "imeprezzzz", "123456789");
	  Guest guest = guestManager.findGuestByID(0);
	  pricingManager.createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  Pricing pricing = pricingManager.findPricingByID(1000);
	  RoomPrice roomPrice = new RoomPrice(RoomType.SINGLE_BED, 100);
	  ArrayList<RoomPrice> list = new ArrayList<>();
	  list.add(roomPrice);
	  pricing.setRoomPrice(list);
	  reservationManager.createReservation(guest, RoomType.SINGLE_BED, LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 
			  null, pricingManager, null);
	  Assert.assertTrue(reservationManager.findReservationByID(1000).getStatus().equals(ReservationStatus.WAITING));
	  reservationManager.updateReservation(1000, ReservationStatus.ACCEPTED);
	  Assert.assertFalse(reservationManager.findReservationByID(1000).getStatus().equals(ReservationStatus.WAITING));
	  Assert.assertTrue(reservationManager.findReservationByID(1000).getStatus().equals(ReservationStatus.ACCEPTED));
  }

  @Test
  public final void testFindStatusAmount() {
	  guestManager.createGuest(0, "imenko", "prezimic", null, null, "060123456", "Ulice 1", "imeprezzzz", "123456789");
	  Guest guest = guestManager.findGuestByID(0);
	  pricingManager.createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  Pricing pricing = pricingManager.findPricingByID(1000);
	  RoomPrice roomPrice = new RoomPrice(RoomType.SINGLE_BED, 100);
	  ArrayList<RoomPrice> list = new ArrayList<>();
	  list.add(roomPrice);
	  pricing.setRoomPrice(list);
	  reservationManager.createReservation(guest, RoomType.SINGLE_BED, LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), 
			  null, pricingManager, null);
	  Integer[] amount = reservationManager.findStatusAmount(LocalDate.now().minusDays(5), LocalDate.now().plusDays(5));
	  Assert.assertTrue(amount[0] == 0);
	  Assert.assertTrue(amount[3] == 1);
  }
}
