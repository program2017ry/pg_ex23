package step12;

import java.util.ArrayList;

public class NightService extends TimeService {
	private static final int START_TIME = 23;
	private static final int END_TIME = 5;
	private static final String SERVICE_CODE = "E2";
	private static final int BASIC_CHARGE = 500;
	private static final int UNIT_PRICE_DISCOUNT = 5;

	ArrayList <String> serviceCodeList = new ArrayList();

	public boolean isJoined() {
		if (this.serviceCodeList.contains(SERVICE_CODE)) {
			return true;
		}
		return false;
	}

	public void checkService(Record record) {
		serviceCodeList.add(record.getServiceCode());
	}

	public void clear() {
		serviceCodeList.clear();
	}

	public boolean isServiceTime(int start_time) {
		if (isJoined() == true && (START_TIME <= start_time || start_time <=END_TIME)) {
			return true;
		}
		return false;
	}

	public int calcUnitPrice(Record record, int unit_price) {
		if (isServiceTime(record.getStartHour()) == true) {
			return unit_price -= UNIT_PRICE_DISCOUNT;
		}
		return unit_price;
	}

	public int calcBasicCharge(int basic_charge) {
		if (isJoined() == true) {
			return basic_charge + BASIC_CHARGE;
		}
		return basic_charge;
	}


}
