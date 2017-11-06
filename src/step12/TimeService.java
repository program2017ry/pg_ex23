package step12;

public abstract class TimeService implements Service {

	public abstract boolean isJoined();
	public abstract void checkService(Record record);
	public abstract void clear();
	public abstract boolean isServiceTime(int start_time);
	public abstract int calcUnitPrice(Record record, int unit_price);
	public abstract int calcBasicCharge(int basic_charge);
}
