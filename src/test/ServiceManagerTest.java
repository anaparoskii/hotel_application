package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import manager.ServiceManager;

public class ServiceManagerTest {
	
	ServiceManager serviceManager;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("service manager test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("service manager test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  serviceManager = new ServiceManager();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCreateService() {
	  serviceManager.createService(0, "breakfast");
	  serviceManager.createService(1, "lunch");
	  Assert.assertEquals(2, serviceManager.getAllServices().size());
  }

  @Test
  public final void testUpdateService() {
	  serviceManager.createService(0, "breakfast");
	  serviceManager.updateService(0, "dinner");
	  Assert.assertFalse(serviceManager.findServiceByID(0).getType().equals("breakfast"));
	  Assert.assertTrue(serviceManager.findServiceByID(0).getType().equals("dinner"));
  }

  @Test
  public final void testDeleteService() {
	  serviceManager.createService(0, "breakfast");
	  serviceManager.deleteService(0);
	  Assert.assertEquals(0, serviceManager.getAllServices().size());
  }
}
