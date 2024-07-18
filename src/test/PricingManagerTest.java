package test;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.AdditionalService;
import entity.RoomType;
import manager.PricingManager;

public class PricingManagerTest {
	
	PricingManager pricingManager;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("pricing manager test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("pricing manager test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  pricingManager = new PricingManager();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCreatePricing() {
	  pricingManager.createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  Assert.assertEquals(1, pricingManager.getSeasonPricing().size());
  }

  @Test
  public final void testAddNewServicePricing() {
	  pricingManager.createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  pricingManager.addNewServicePricing(new AdditionalService(0, "lunch"), 100, pricingManager.findPricingByID(1000));
	  Assert.assertEquals(1, pricingManager.findPricingByID(1000).getServicePrice().size());
  }

  @Test
  public final void testUpdateServicePricing() {
	  pricingManager.createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  pricingManager.addNewServicePricing(new AdditionalService(0, "lunch"), 100, pricingManager.findPricingByID(1000));
	  Assert.assertTrue(pricingManager.findPricingByID(1000).getServicePrice().get(0).getPrice() == 100);
	  pricingManager.updateServicePricing("lunch", 200, pricingManager.findPricingByID(1000));
	  Assert.assertFalse(pricingManager.findPricingByID(1000).getServicePrice().get(0).getPrice() == 100);
	  Assert.assertTrue(pricingManager.findPricingByID(1000).getServicePrice().get(0).getPrice() == 200);
  }

  @Test
  public final void testUpdateRoomPricing() {
	  pricingManager.createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  Assert.assertTrue(pricingManager.findPricingByID(1000).getRoomPrice().get(0).getPrice() == 0);
	  RoomType type = pricingManager.findPricingByID(1000).getRoomPrice().get(0).getRoom();
	  pricingManager.updateRoomPricing(type, 200, pricingManager.findPricingByID(1000));
	  Assert.assertFalse(pricingManager.findPricingByID(1000).getRoomPrice().get(0).getPrice() == 0);
	  Assert.assertTrue(pricingManager.findPricingByID(1000).getRoomPrice().get(0).getPrice() == 200);
  }

  @Test
  public final void testDeletePricing() {
	  pricingManager.createPricing(LocalDate.now().minusDays(30), LocalDate.now().plusDays(30), null);
	  pricingManager.deletePricing(1000);
	  Assert.assertFalse(pricingManager.findPricingByID(1000).isActive());
  }
}
