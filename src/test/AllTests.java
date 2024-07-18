package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
  CheckInControlTest.class,
  CheckOutControlTest.class,
  EmployeeManagerTest.class,
  GuestManagerTest.class,
  LoginControlTest.class,
  MaidControlTest.class,
  PricingManagerTest.class,
  ReservationControlTest.class,
  ReservationManagerTest.class,
  RoomManagerTest.class,
  ServiceManagerTest.class
})
public class AllTests {}
