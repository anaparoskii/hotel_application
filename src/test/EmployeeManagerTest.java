package test;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import entity.EmployeeTitle;
import entity.Receptionist;
import manager.EmployeeManager;

public class EmployeeManagerTest {
	
	EmployeeManager employeeManager;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
	  System.out.println("employee manager test start"); 
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
	  System.out.println("employee manager test end"); 
  }

  @Before
  public void setUp() throws Exception {
	  employeeManager = new EmployeeManager();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public final void testCreateAdmin() {
	  employeeManager.createAdmin(0, "ime", "prezime", null, null, "060000000", "Ulica 1", "imeprez", "ip123", 0, 0, 0);
	  Assert.assertEquals(1, employeeManager.getAllEmployees().size());
  }

  @Test
  public final void testCreateReceptionist() {
	  employeeManager.createReceptionist(1, "imenko", "prezimic", null, null, "060000000", "Ulica 2", "imprezzz", "123ip", 0, 0, 0);
	  Assert.assertEquals(1, employeeManager.getAllEmployees().size());
  }

  @Test
  public final void testCreateMaid() {
	  employeeManager.createMaid(2, "imenka", "prezimic", null, null, "060000000", "Ulica 3", "imeeep", "i1p2", 0, 0, 0);
	  Assert.assertEquals(1, employeeManager.getAllEmployees().size());
  }

  @Test
  public final void testDeleteEmployee() {
	  Receptionist r = new Receptionist(0, "imenko", "prezimic", null, null, "060000000", "Ulica 2", "imprezzz", "123ip", 
			  EmployeeTitle.RECEPTIONIST, 0, 0, 0);
	  r.setWork(false);
	  employeeManager.createReceptionist(1, "imenko", "prezimic", null, null, "060000000", "Ulica 2", "imprezzz", "123ip", 0, 0, 0);
	  employeeManager.deleteEmployee(1);
	  Assert.assertEquals(r.isWork(), employeeManager.findEmployeeByID(1).isWork());
  }
}
