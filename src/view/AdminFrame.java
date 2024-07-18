package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

import controller.IncomeChartData;
import controller.IncomeOutcomeReport;
import controller.MaidControl;
import controller.ReservationControl;
import entity.AdditionalService;
import entity.Admin;
import entity.Employee;
import entity.EmployeeTitle;
import entity.Guest;
import entity.Maid;
import entity.Pricing;
import entity.Reservation;
import entity.Room;
import entity.RoomCriteria;
import entity.RoomPrice;
import entity.RoomType;
import entity.ServicePrice;
import manager.FormatManager;
import manager.ManagerFactory;

public class AdminFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private ManagerFactory managers;
	private Admin admin;
	
	public AdminFrame(ManagerFactory managers, Admin admin) {
		this.managers = managers;
		this.admin = admin;
		adminFrame();
	}
	
	private void adminFrame() {
		JFrame main = new JFrame();
		String title = String.format("Admin Panel - %s %s", admin.getFirstName(), admin.getLastName());
		main.setTitle(title);
		main.setSize(650, 400);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initAdmin(main);
		main.setVisible(true);
	}
	
	private void initAdmin(JFrame frame) {
		frame.getContentPane().setLayout(new BorderLayout());
		
		Container container = frame.getContentPane();
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		
		JButton logoutBtn = new JButton("LOG OUT");
		header.add(logoutBtn);
		container.add(header, BorderLayout.NORTH);
		
		JTabbedPane contentPane = new JTabbedPane();
		contentPane.setBackground(new Color(240, 240, 240));
		
		
		Container employeeContainer = new Container();
		employeeContainer.setForeground(new Color(240, 240, 240));
		employeeContainer.setBackground(new Color(255, 255, 255));
		employeeContainer.setLayout(null);
		
		JLabel lblEmployee = new JLabel("All Employees");
		lblEmployee.setSize(154, 30);
		lblEmployee.setLocation(270, 11);
		employeeContainer.add(lblEmployee);
		
		ArrayList<Employee> allEmployees = managers.getEmployeeManager().readActiveEmployees();
		
		DefaultTableModel employeeTableModel = new DefaultTableModel(new String[] {
					"Name", "Last Name", "Title", "Username", "Password", "Salary"
				}, 0
				);
		JTable employeeTable = new JTable(employeeTableModel);
		employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		employeeTable.setDefaultEditor(Object.class, null);
		
		for (Employee e : allEmployees) {
			employeeTableModel.addRow(new Object[] {e.getFirstName(), e.getLastName(), e.getTitle().toString(),
													e.getUsername(), e.getPassword(), e.getSalary()});
		}
		employeeTable.setBackground(new Color(240, 240, 240));

		JScrollPane employeeScroll = new JScrollPane(employeeTable);
		employeeScroll.setBounds(10, 50, 598, 180);
		
		employeeContainer.add(employeeScroll);
		
		JButton addEmployeeBtn = new JButton("ADD");
		addEmployeeBtn.setSize(110, 30);
		addEmployeeBtn.setLocation(10, 258);
		employeeContainer.add(addEmployeeBtn);
		JButton deleteEmployeeBtn = new JButton("DELETE");
		deleteEmployeeBtn.setSize(110, 30);
		deleteEmployeeBtn.setLocation(250, 258);
		employeeContainer.add(deleteEmployeeBtn);
		JButton updateEmployeeBtn = new JButton("UPDATE");
		updateEmployeeBtn.setSize(110, 30);
		updateEmployeeBtn.setLocation(130, 258);
		employeeContainer.add(updateEmployeeBtn);
		
		contentPane.addTab("Employees", employeeContainer);
		
		String[] title = new String[3];
		int i = 0;
		for (EmployeeTitle t : EmployeeTitle.values()) {
			title[i] = t.toString();
			i++;
		}
		
		Container guestContainer = new Container();
		guestContainer.setForeground(new Color(240, 240, 240));
		guestContainer.setBackground(new Color(255, 255, 255));
		guestContainer.setLayout(null);
		
		JLabel lblGuest = new JLabel("All Guests");
		lblGuest.setSize(154, 30);
		lblGuest.setLocation(270, 11);
		guestContainer.add(lblGuest);
		
		ArrayList<Guest> allGuests = managers.getGuestManager().readAllGuests();
		
		DefaultTableModel guestTableModel = new DefaultTableModel(new String[] {
					"Name", "Last Name", "Username", "Password"
				}, 0
				);
		JTable guestTable = new JTable(guestTableModel);
		guestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		guestTable.setDefaultEditor(Object.class, null);
		
		for (Guest g : allGuests) {
			guestTableModel.addRow(new Object[] {g.getFirstName(), g.getLastName(), g.getUsername(), g.getPassword()});
		}
		guestTable.setBackground(new Color(240, 240, 240));

		JScrollPane guestScroll = new JScrollPane(guestTable);
		guestScroll.setBounds(10, 50, 598, 180);
		
		guestContainer.add(guestScroll);
		
		JButton deleteGuestBtn = new JButton("DELETE");
		deleteGuestBtn.setSize(110, 30);
		deleteGuestBtn.setLocation(130, 258);
		guestContainer.add(deleteGuestBtn);
		JButton updateGuestBtn = new JButton("UPDATE");
		updateGuestBtn.setSize(110, 30);
		updateGuestBtn.setLocation(10, 258);
		guestContainer.add(updateGuestBtn);
		
		contentPane.addTab("Guests", guestContainer);
		
		Container roomContainer = new Container();
		roomContainer.setForeground(new Color(240, 240, 240));
		roomContainer.setBackground(new Color(255, 255, 255));
		roomContainer.setLayout(null);
		
		JLabel lblRoom = new JLabel("All Rooms");
		lblRoom.setSize(154, 30);
		lblRoom.setLocation(10, 9);
		roomContainer.add(lblRoom);
		
		JLabel lblFrom1 = new JLabel("From:");
		lblFrom1.setBounds(234, 9, 35, 30);
		roomContainer.add(lblFrom1);
		
		TextField from1Tf = new TextField();
		from1Tf.setForeground(new Color(0, 0, 0));
		from1Tf.setBounds(275, 9, 100, 30);
		roomContainer.add(from1Tf);
		
		JLabel lblTo1 = new JLabel("To:");
		lblTo1.setBounds(401, 9, 30, 30);
		roomContainer.add(lblTo1);
		
		TextField to1Tf = new TextField();
		to1Tf.setForeground(new Color(0, 0, 0));
		to1Tf.setBounds(437, 9, 100, 30);
		roomContainer.add(to1Tf);
		
		JButton ok1Btn = new JButton("OK");
		ok1Btn.setBounds(543, 9, 65, 30);
		roomContainer.add(ok1Btn);
		
		ArrayList<Room> allRooms = managers.getRoomManager().readAllRooms();
		
		DefaultTableModel roomTableModel = new DefaultTableModel(new String[] {
					"Number", "Type", "Code", "Status", "Criteria", "Nights in", "Income"
				}, 0
				);
		JTable roomTable = new JTable(roomTableModel);
		roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		roomTable.setDefaultEditor(Object.class, null);
		
		for (Room r : allRooms) {
			String s = "";
			for (RoomCriteria c : r.getCriteria()) {
				s += String.valueOf(c) + ",";
			}
			roomTableModel.addRow(new Object[] {r.getRoomNumber(), r.getType().toString(), 
													r.getRoomCode(), r.getStatus().toString(), s, "", ""});
		}
		roomTable.setBackground(new Color(240, 240, 240));

		JScrollPane roomScroll = new JScrollPane(roomTable);
		roomScroll.setBounds(10, 50, 598, 180);
		
		roomContainer.add(roomScroll);
		
		JButton addRoomBtn = new JButton("ADD");
		addRoomBtn.setSize(110, 30);
		addRoomBtn.setLocation(10, 258);
		roomContainer.add(addRoomBtn);
		JButton deleteRoomBtn = new JButton("DELETE");
		deleteRoomBtn.setSize(110, 30);
		deleteRoomBtn.setLocation(250, 258);
		roomContainer.add(deleteRoomBtn);
		JButton updateRoomBtn = new JButton("UPDATE");
		updateRoomBtn.setSize(110, 30);
		updateRoomBtn.setLocation(130, 258);
		roomContainer.add(updateRoomBtn);
		
		contentPane.addTab("Rooms", roomContainer);
		
		Container serviceContainer = new Container();
		serviceContainer.setForeground(new Color(240, 240, 240));
		serviceContainer.setBackground(new Color(255, 255, 255));
		serviceContainer.setLayout(null);
		
		JLabel lblService = new JLabel("All Services");
		lblService.setSize(154, 30);
		lblService.setLocation(270, 11);
		serviceContainer.add(lblService);

		ArrayList<AdditionalService> allServices = managers.getServiceManager().readAllServices();

		DefaultTableModel serviceTableModel = new DefaultTableModel(new String[] {
		            "Code", "Name"
		        }, 0
		        );
		JTable serviceTable = new JTable(serviceTableModel);
		serviceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		serviceTable.setDefaultEditor(Object.class, null);

		for (AdditionalService s : allServices) {
		    serviceTableModel.addRow(new Object[] {s.getId(), s.getType()});
		}
		serviceTable.setBackground(new Color(240, 240, 240));

		JScrollPane serviceScroll = new JScrollPane(serviceTable);
		serviceScroll.setBounds(10, 50, 598, 180);

		serviceContainer.add(serviceScroll);

		JButton addServiceBtn = new JButton("ADD");
		addServiceBtn.setSize(110, 30);
		addServiceBtn.setLocation(10, 258);
		serviceContainer.add(addServiceBtn);
		JButton deleteServiceBtn = new JButton("DELETE");
		deleteServiceBtn.setSize(110, 30);
		deleteServiceBtn.setLocation(250, 258);
		serviceContainer.add(deleteServiceBtn);
		JButton updateServiceBtn = new JButton("UPDATE");
		updateServiceBtn.setSize(110, 30);
		updateServiceBtn.setLocation(130, 258);
		serviceContainer.add(updateServiceBtn);
		
		contentPane.addTab("Services", serviceContainer);
		
		
		Container pricingContainer = new Container();
		pricingContainer.setForeground(new Color(240, 240, 240));
		pricingContainer.setBackground(new Color(255, 255, 255));
		pricingContainer.setLayout(null);
		
		FormatManager formatManager = new FormatManager();
		
		JLabel lblPricing = new JLabel("Pricing:");
		lblPricing.setBounds(10, 11, 65, 30);
		pricingContainer.add(lblPricing);
		
		String[] dates = new String[managers.getPricingManager().getSeasonPricing().size()];
		int k = 0;
		for (Map.Entry<Integer, Pricing> set : managers.getPricingManager().getSeasonPricing().entrySet()) {
			String s = formatManager.dateToString(set.getValue().getStartDate()) 
					+ " - " + formatManager.dateToString(set.getValue().getEndDate());
			dates[k] = s;
			k++;
		}
		JComboBox<String> pricingCb = new JComboBox<>(dates);
		pricingCb.setBounds(85, 11, 225, 30);
		pricingContainer.add(pricingCb);
		
		DefaultTableModel roomPriceTableModel = new DefaultTableModel(new String[] {
					"Room type", "Price"
				}, 0
				);
		JTable roomPriceTable = new JTable(roomPriceTableModel);
		roomPriceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		roomPriceTable.setDefaultEditor(Object.class, null);
		
		roomPriceTable.setBackground(new Color(240, 240, 240));

		JScrollPane roomPriceScroll = new JScrollPane(roomPriceTable);
		roomPriceScroll.setBounds(10, 58, 300, 115);
		
		pricingContainer.add(roomPriceScroll);
		
		DefaultTableModel servicePriceTableModel = new DefaultTableModel(new String[] {
					"Service name", "Price"
				}, 0
				);
		JTable servicePriceTable = new JTable(servicePriceTableModel);
		servicePriceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		servicePriceTable.setDefaultEditor(Object.class, null);
		
		servicePriceTable.setBackground(new Color(240, 240, 240));

		JScrollPane servicePriceScroll = new JScrollPane(servicePriceTable);
		servicePriceScroll.setBounds(10, 184, 300, 115);
		
		pricingContainer.add(servicePriceScroll);
		
		JButton addPricingBtn = new JButton("CREATE NEW");
		addPricingBtn.setSize(160, 30);
		addPricingBtn.setLocation(459, 11);
		pricingContainer.add(addPricingBtn);
		
		JLabel lblUpdateRooms = new JLabel("<html>Add/update<br>room prices</html>");
		lblUpdateRooms.setBounds(320, 58, 130, 30);
		pricingContainer.add(lblUpdateRooms);
		
		String[] roomTypes = new String[5];
		int j = 0;
		for (RoomType type : RoomType.values()) {
			roomTypes[j] = type.toString();
			j++;
		}
		JComboBox<String> rpCb = new JComboBox<>(roomTypes);
		rpCb.setBounds(320, 99, 130, 30);
		pricingContainer.add(rpCb);
		
		JLabel lblNewRp = new JLabel("Enter new room price:");
		lblNewRp.setBounds(459, 57, 160, 30);
		pricingContainer.add(lblNewRp);
		
		JTextField newRpTf = new JTextField();
		newRpTf.setBounds(459, 99, 160, 30);
		pricingContainer.add(newRpTf);
		
		JButton btnOkRoom = new JButton("OK");
		btnOkRoom.setBounds(554, 143, 65, 30);
		pricingContainer.add(btnOkRoom);
		
		JLabel lblServiceP = new JLabel("<html>Add/update<br>service prices</html>");
		lblServiceP.setBounds(320, 184, 130, 30);
		pricingContainer.add(lblServiceP);
		
		String[] services = new String[allServices.size()];
		int l = 0;
		for (AdditionalService s : allServices) {
			services[l] = s.getType();
			l++;
		}
		JComboBox<String> spCb = new JComboBox<>(services);
		spCb.setBounds(320, 226, 130, 30);
		pricingContainer.add(spCb);
		
		JLabel lblNewSp = new JLabel("Enter new service price:");
		lblNewSp.setBounds(459, 185, 160, 30);
		pricingContainer.add(lblNewSp);
		
		JTextField newSpTf = new JTextField();
		newSpTf.setBounds(459, 226, 160, 30);
		pricingContainer.add(newSpTf);
		
		JButton btnOkService = new JButton("OK");
		btnOkService.setBounds(554, 269, 65, 30);
		pricingContainer.add(btnOkService);
		
		contentPane.addTab("Pricing", pricingContainer);
		
		Container reservationContainer = new Container();
		reservationContainer.setForeground(new Color(240, 240, 240));
		reservationContainer.setBackground(new Color(255, 255, 255));
		reservationContainer.setLayout(null);
		
		JLabel label1 = new JLabel("All Reservations");
		label1.setSize(130, 30);
		label1.setLocation(10, 9);
		reservationContainer.add(label1);
		
		ReservationControl reservationControl = new ReservationControl(managers);
		ArrayList<Reservation> reservations = reservationControl.getReservation();
		
		DefaultTableModel tableModel = new DefaultTableModel(new String[] {
					"Code", "Check-in", "Check-out", "Room type", "Room no", "Services", "Status", "Price", "Confirmed"
				}, 0);
		JTable reservationTable = new JTable(tableModel);
		reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		reservationTable.setDefaultEditor(Object.class, null);
		
		for (Reservation r : reservations) {
			String benefits = "";
			for (AdditionalService s : r.getBenefits()) {
				if (s != null) {
					benefits += s.getType() + ",";
				}
			}
			String room;
			if (r.getRoom() == null) {
				room = "";
			} else {
				room = String.valueOf(r.getRoom().getRoomNumber());
			}
			String date;
			if (r.getDateConfirmed() == null) {
				date = "";
			} else {
				date = formatManager.dateToString(r.getDateConfirmed());
			}
			tableModel.addRow(new Object[] {String.valueOf(r.getId()), formatManager.dateToString(r.getCheckInDate()),
											formatManager.dateToString(r.getCheckOutDate()),
											r.getRoomType(), room,
											benefits,
											r.getStatus(), r.getPrice(), date});
		}
		reservationTable.setBackground(new Color(240, 240, 240));
		
		JScrollPane reservationScroll = new JScrollPane(reservationTable);
		reservationScroll.setBounds(10, 50, 609, 249);
		reservationContainer.add(reservationScroll);
		
		contentPane.addTab("Reservations", reservationContainer);
		
		Container incomeContainer = new Container();
		incomeContainer.setForeground(new Color(240, 240, 240));
		incomeContainer.setBackground(new Color(255, 255, 255));
		incomeContainer.setLayout(null);
		
		JLabel incomeLbl = new JLabel("This year's income");
		incomeLbl.setBounds(10, 11, 130, 30);
		incomeContainer.add(incomeLbl);
		
		XYChart chart = new XYChartBuilder()
			     .width(1200)
			     .height(400)
			     .title("Income by room type")
			     .xAxisTitle("Months")
			     .yAxisTitle("Price")
			     .build();
	    
	    double[] xData = new IncomeChartData(managers).getXData(LocalDate.now());
	    double[] yData;
	    double[] allData = new double[xData.length];
	    for (int n = 0; n < xData.length; n++) {
	    	allData[n] = 0;
	    }
	    XYSeries series;
	    for (RoomType type : RoomType.values()) {
	    	yData = new IncomeChartData(managers).getYData(LocalDate.now(), type, xData);
	    	series = chart.addSeries(type.name(), xData, yData);
	    	series.setMarker(SeriesMarkers.CIRCLE);
	    	for (int m = 0; m < xData.length; m++) {
	    		allData[m] += yData[m];
	    	}
	    }
	    series = chart.addSeries("All", xData, allData);
	    series.setMarker(SeriesMarkers.CIRCLE);
	    
	    XChartPanel<XYChart> chartPanel = new XChartPanel<XYChart>(chart);
	    chartPanel.setBounds(10, 40, 609, 259);
	    incomeContainer.add(chartPanel);
		
		contentPane.addTab("Income", incomeContainer);
		
		Container maidContainer = new Container();
		maidContainer.setForeground(new Color(240, 240, 240));
		maidContainer.setBackground(new Color(255, 255, 255));
		maidContainer.setLayout(null);
		
		JLabel lblMaidReport = new JLabel("Maid report - number of rooms cleaned");
		lblMaidReport.setBounds(10, 11, 250, 30);
		maidContainer.add(lblMaidReport);
		
		JLabel lblReport2From = new JLabel("From:");
		lblReport2From.setBounds(10, 52, 45, 30);
		maidContainer.add(lblReport2From);
		
		TextField report2FromTf = new TextField();
		report2FromTf.setForeground(new Color(0, 0, 0));
		report2FromTf.setBounds(61, 57, 95, 25);
		maidContainer.add(report2FromTf);
		report2FromTf.setColumns(10);
		
		JLabel lblReport2To = new JLabel("To:");
		lblReport2To.setBounds(10, 93, 45, 30);
		maidContainer.add(lblReport2To);
		
		TextField report2ToTf = new TextField();
		report2ToTf.setForeground(new Color(0, 0, 0));
		report2ToTf.setBounds(61, 98, 95, 25);
		maidContainer.add(report2ToTf);
		report2ToTf.setColumns(10);
		
		JButton btnOkReport2 = new JButton("OK");
		btnOkReport2.setBounds(91, 140, 65, 30);
		maidContainer.add(btnOkReport2);
		
		DefaultTableModel maidReportModel = new DefaultTableModel(new String[] {
				"ID", "Number of rooms"
			}, 0);
		JTable maidReportTable = new JTable(maidReportModel);
		maidReportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		maidReportTable.setDefaultEditor(Object.class, null);
		
		JScrollPane maidReportScroll = new JScrollPane(maidReportTable);
		maidReportScroll.setBounds(240, 12, 250, 75);
		maidContainer.add(maidReportScroll);
		
		PieChart maidChart = new PieChartBuilder().width(800).height(600).title("Cleaned rooms by maid - last 30 days").build();
		
		LocalDate dateFrom1 = LocalDate.now().minusDays(30);
		LocalDate dateTo1 = LocalDate.now();
		HashMap<Integer, Maid> allMaids = managers.getEmployeeManager().getAllMaids();
		for (Map.Entry<Integer, Maid> set : allMaids.entrySet()) {
			int numberOfCleanedRooms = new MaidControl(managers).getChartData(dateFrom1, dateTo1, set.getValue());
			maidChart.addSeries(String.valueOf(set.getKey()), numberOfCleanedRooms);
		}
		
		XChartPanel<PieChart> chart2Panel = new XChartPanel<PieChart>(maidChart);
		chart2Panel.setBounds(240, 98, 364, 201);
		maidContainer.add(chart2Panel);
		
		contentPane.addTab("Maids", maidContainer);
		
		Container reportContainer = new Container();
		reportContainer.setForeground(new Color(240, 240, 240));
		reportContainer.setBackground(new Color(255, 255, 255));
		reportContainer.setLayout(null);
		
		JLabel lblReport1 = new JLabel("Income and outcome report");
		lblReport1.setBounds(10, 11, 200, 30);
		reportContainer.add(lblReport1);
		
		TextField fromReport1Tf = new TextField();
		fromReport1Tf.setForeground(new Color(0, 0, 0));
		fromReport1Tf.setBounds(10, 75, 95, 25);
		reportContainer.add(fromReport1Tf);
		fromReport1Tf.setColumns(10);
		
		TextField toReport1Tf = new TextField();
		toReport1Tf.setForeground(new Color(0, 0, 0));
		toReport1Tf.setColumns(10);
		toReport1Tf.setBounds(115, 75, 95, 25);
		reportContainer.add(toReport1Tf);
		
		JLabel lblReport1From = new JLabel("From:");
		lblReport1From.setBounds(10, 40, 45, 30);
		reportContainer.add(lblReport1From);
		
		JLabel lblReport1To = new JLabel("To:");
		lblReport1To.setBounds(115, 40, 45, 30);
		reportContainer.add(lblReport1To);
		
		JButton btnReport1 = new JButton("OK");
		btnReport1.setBounds(145, 111, 65, 30);
		reportContainer.add(btnReport1);
		
		DefaultTableModel report1IncomeModel = new DefaultTableModel(new String[] {
				"Room type", "Income"
			}, 0);
		JTable report1IncomeTable = new JTable(report1IncomeModel);
		report1IncomeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		report1IncomeTable.setDefaultEditor(Object.class, null);
		
		JScrollPane report1IncomeScroll = new JScrollPane(report1IncomeTable);
		report1IncomeScroll.setBounds(10, 152, 200, 75);
		reportContainer.add(report1IncomeScroll);
		
		DefaultTableModel report1OutcomeModel = new DefaultTableModel(new String[] {
				"Employee", "Outcome"
			}, 0);
		JTable report1OutcomeTable = new JTable(report1OutcomeModel);
		report1OutcomeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		report1OutcomeTable.setDefaultEditor(Object.class, null);
		
		JScrollPane report1OutcomeScroll = new JScrollPane(report1OutcomeTable);
		report1OutcomeScroll.setBounds(10, 230, 200, 75);
		reportContainer.add(report1OutcomeScroll);
		
		JLabel lblFrom2 = new JLabel("From:");
		lblFrom2.setBounds(240, 40, 45, 30);
		reportContainer.add(lblFrom2);
		
		TextField from2Tf = new TextField();
		from2Tf.setForeground(new Color(0, 0, 0));
		from2Tf.setBounds(291, 45, 95, 25);
		reportContainer.add(from2Tf);
		
		JLabel lblTo2 = new JLabel("To:");
		lblTo2.setBounds(402, 40, 45, 30);
		reportContainer.add(lblTo2);
		
		TextField to2Tf = new TextField();
		to2Tf.setForeground(new Color(0, 0, 0));
		to2Tf.setBounds(450, 45, 95, 25);
		reportContainer.add(to2Tf);
		
		JButton ok2Btn = new JButton("OK");
		ok2Btn.setBounds(554, 40, 65, 30);
		reportContainer.add(ok2Btn);
		
		JLabel lbl1 = new JLabel("Reservation report");
		lbl1.setBounds(240, 11, 130, 30);
		reportContainer.add(lbl1);
		
		DefaultTableModel reportModel = new DefaultTableModel(new String[] {
				"Accepted", "Declined", "Canceled"
			}, 1);
		JTable reportTable = new JTable(reportModel);
		reportTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reportTable.setDefaultEditor(Object.class, null);
		
		JScrollPane reportScroll = new JScrollPane(reportTable);
		reportScroll.setBounds(240, 81, 200, 40);
		reportContainer.add(reportScroll);
		
		PieChart reservationStatusChart = new PieChartBuilder().width(800).height(600).title("Reservation status - last 30 days").build();
		
		LocalDate dateFrom = LocalDate.now().minusDays(30);
		LocalDate dateTo = LocalDate.now();
		Integer[] values = managers.getReservationManager().findStatusAmount(dateFrom, dateTo);
		
		reservationStatusChart.addSeries("accepted", values[0]);
		reservationStatusChart.addSeries("declined", values[1]);
		reservationStatusChart.addSeries("canceled", values[2]);
		reservationStatusChart.addSeries("waiting", values[3]);
		
		XChartPanel<PieChart> chart1Panel = new XChartPanel<PieChart>(reservationStatusChart);
		chart1Panel.setBounds(240, 132, 379, 173);
		reportContainer.add(chart1Panel);
		
		contentPane.addTab("Reports", reportContainer);
		
		container.add(contentPane, BorderLayout.CENTER);
		
		btnOkReport2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				maidReportModel.setRowCount(0);
				LocalDate dateFrom = formatManager.asLocalDate(report2FromTf.getText());
				LocalDate dateTo = formatManager.asLocalDate(report2ToTf.getText());
				HashMap<Integer, Maid> allMaids = managers.getEmployeeManager().getAllMaids();
				for (Map.Entry<Integer, Maid> set : allMaids.entrySet()) {
					int numberOfCleanedRooms = new MaidControl(managers).getChartData(dateFrom, dateTo, set.getValue());
					maidReportModel.addRow(new Object[] {set.getKey(), numberOfCleanedRooms});
				}
			}
		});
		
		btnReport1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int income;
				int outcome;
				report1IncomeModel.setRowCount(0);
				report1OutcomeModel.setRowCount(0);
				IncomeOutcomeReport report = new IncomeOutcomeReport(managers);
				for (RoomType type : RoomType.values()) {
					income = report.getIncomeData(formatManager.asLocalDate(fromReport1Tf.getText()), 
							formatManager.asLocalDate(toReport1Tf.getText()), type); 
					report1IncomeModel.addRow(new Object[] {type.toString(), income});
					System.out.println(type.toString() + income); 
				}
				ArrayList<Employee> allEmployees = managers.getEmployeeManager().readAllEmployees();
				for (Employee employee : allEmployees) {
					outcome = report.getOutcomeData(formatManager.asLocalDate(fromReport1Tf.getText()), 
							formatManager.asLocalDate(toReport1Tf.getText()), employee);
					report1OutcomeModel.addRow(new Object[] {employee.getId(), outcome});
				}
			}
		});
		
		logoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				managers.saveData();
				frame.dispose();
				new LoginDialog(managers);
			}
		});
		
		addEmployeeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new EmployeeCRUDFrame(managers, frame, admin, null);
			}
		});
		
		updateEmployeeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = employeeTable.getSelectedRow();
				if (selectedRow != -1) {
					Employee x = allEmployees.get(selectedRow);
					new EmployeeCRUDFrame(managers, frame, admin, x);
				} else {
					new InfoDialog(managers, "Employee not selected");
				}
			}
		});
		
		deleteEmployeeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = employeeTable.getSelectedRow();
				if (selectedRow != -1) {
					Employee x = allEmployees.get(selectedRow);
					int id = x.getId();
					managers.getEmployeeManager().deleteEmployee(id);
					
					frame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Employee removed successfully!");
				} else {
					new InfoDialog(managers, "Employee not selected");
				}
			}
		});
		
		updateGuestBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = guestTable.getSelectedRow();
				if (selectedRow != -1) {
					Guest x = allGuests.get(selectedRow);
					new GuestCRUDFrame(managers, frame, admin, null, null, x);
				} else {
					new InfoDialog(managers, "Guest not selected");
				}
			}
		});
		
		deleteGuestBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = guestTable.getSelectedRow();
				if (selectedRow != -1) {
					Guest x = allGuests.get(selectedRow);
					int id = x.getId();
					managers.getGuestManager().deleteGuest(id);
					frame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Guest removed successfully!");
				} else {
					new InfoDialog(managers, "Guest not selected");
				}
			}
		});
		
		addRoomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new RoomCRUDFrame(managers, admin, frame, null);
			}
		});
		
		updateRoomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = roomTable.getSelectedRow();
				if (selectedRow != -1) {
					Room x = allRooms.get(selectedRow);
					new RoomCRUDFrame(managers, admin, frame, x);
				} else {
					new InfoDialog(managers, "Room not selected");
				}
			}
		});
		
		deleteRoomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = roomTable.getSelectedRow();
				if (selectedRow != -1) {
					Room x = allRooms.get(selectedRow);
					int id = x.getRoomNumber();					
					managers.getRoomManager().deleteRoom(id);
					frame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Room removed successfully!");
				} else {
					new InfoDialog(managers, "Room not selected");
				}
			}
		});
		
		addServiceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ServiceCRUDFrame(managers, admin, frame, null);
			}
		});
		
		updateServiceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = serviceTable.getSelectedRow();
				if (selectedRow != -1) {
					AdditionalService x = allServices.get(selectedRow);
					new ServiceCRUDFrame(managers, admin, frame, x);
				} else {
					new InfoDialog(managers, "Service not selected");
				}
			}
		});
		
		deleteServiceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = serviceTable.getSelectedRow();
				if (selectedRow != -1) {
					AdditionalService x = allServices.get(selectedRow);
					int id = x.getId();					
					managers.getServiceManager().deleteService(id);
					frame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Service removed successfully!");
				} else {
					new InfoDialog(managers, "Service not selected");
				}
			}
		});
		
		pricingCb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				roomPriceTableModel.setRowCount(0);
				servicePriceTableModel.setRowCount(0);
				String fullString = (String) pricingCb.getSelectedItem();
				String[] s = fullString.split(" - ");
				Pricing pricing = managers.getPricingManager().findPricingByID(
						managers.getPricingManager().findPricingIDByDate(formatManager.asLocalDate(s[0]), 
																		 formatManager.asLocalDate(s[1])));
				ArrayList<RoomPrice> roomPrice = pricing.getRoomPrice();
				for (RoomPrice rp : roomPrice) {
					roomPriceTableModel.addRow(new Object[] {rp.getRoom(), rp.getPrice()});
				}
				ArrayList<ServicePrice> servicePrice = pricing.getServicePrice();
				for (ServicePrice sp : servicePrice) {
					servicePriceTableModel.addRow(new Object[] {sp.getService().getType(), sp.getPrice()});
				}
			}
		});
		
		btnOkRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fullString = (String) pricingCb.getSelectedItem();
				String[] s = fullString.split(" - ");
				Pricing pricing = managers.getPricingManager().findPricingByID(
						managers.getPricingManager().findPricingIDByDate(formatManager.asLocalDate(s[0]), 
																		 formatManager.asLocalDate(s[1])));
				Object roomType = (String) rpCb.getEditor().getItem();
				RoomType type;
				if (roomType.equals("single bed")) {
					type = RoomType.SINGLE_BED;
				} else if (roomType.equals("double bed")) {
					type = RoomType.DOUBLE_BED;
				} else if (roomType.equals("king bed")) {
					type = RoomType.KING_BED;
				} else if (roomType.equals("triple bed")) {
					type = RoomType.TRIPLE_BED;
				} else {
					type = RoomType.QUAD_BED;
				}
				double newPrice = Double.parseDouble(newRpTf.getText());
				managers.getPricingManager().updateRoomPricing(type, newPrice, pricing);
				frame.dispose();
				new AdminFrame(managers, admin);
				new InfoDialog(managers, "<html>Room price added/updated<br>successfully</html>!");
			}
		});
		
		btnOkService.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fullString = (String) pricingCb.getSelectedItem();
				String[] s = fullString.split(" - ");
				Pricing pricing = managers.getPricingManager().findPricingByID(
						managers.getPricingManager().findPricingIDByDate(formatManager.asLocalDate(s[0]), 
																		 formatManager.asLocalDate(s[1])));
				String type = (String) spCb.getEditor().getItem();
				double newPrice = Double.parseDouble(newSpTf.getText());
				ArrayList<ServicePrice> servicePrice = pricing.getServicePrice();
				ArrayList<AdditionalService> as = new ArrayList<>();
				for (ServicePrice sp : servicePrice) {
					as.add(sp.getService());
				}
				AdditionalService service = managers.getServiceManager().findServiceByName(type);
				if (as.contains(managers.getServiceManager().findServiceByName(type))) {
					managers.getPricingManager().updateServicePricing(type, newPrice, pricing);
					frame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Service price updated successfully!");
				} else {
					managers.getPricingManager().addNewServicePricing(service, newPrice, pricing);
					frame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Service price added successfully!");
				}
				
			}
		});
		
		addPricingBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new PricingFrame(managers, admin, frame);
			}
		});
		
		ok1Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDate dateFrom = formatManager.asLocalDate(from1Tf.getText());
				LocalDate dateTo = formatManager.asLocalDate(to1Tf.getText());
				roomTableModel.setRowCount(0);
				for (Room r : allRooms) {
					String s = "";
					for (RoomCriteria c : r.getCriteria()) {
						s += String.valueOf(c) + ",";
					}
					Integer[] value = managers.getRoomManager().findNightsIn(dateFrom, dateTo, r, managers.getPricingManager());
					roomTableModel.addRow(new Object[] {r.getRoomNumber(), r.getType().toString(), 
															r.getRoomCode(), r.getStatus().toString(), s, value[0], value[1]});
				}
			}
		});
		
		ok2Btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDate dateFrom = formatManager.asLocalDate(from2Tf.getText());
				LocalDate dateTo = formatManager.asLocalDate(to2Tf.getText());
				reportModel.setRowCount(0);
				Integer[] value = managers.getReservationManager().findStatusAmount(dateFrom, dateTo);
				reportModel.addRow(new Object[] {value[0], value[1], value[2]});
			}
		});
	}
	
	public ManagerFactory getManagers() {
		return managers;
	}
	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
}
