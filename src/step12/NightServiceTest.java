package step12;

import static org.junit.Assert.*;

import org.junit.Test;

public class NightServiceTest {


	@Test
	public void testCheckService() {
		NightService nightService = new NightService();

		// インスタンスを作成した直後は、加入フラグはfalse
		assertFalse(nightService.isJoined());

		// 夜トク割引意外のレコードの場合、加入フラグはfalseのまま
		nightService.checkService(new Record("2 C1 090-1234-0001"));
		assertFalse(nightService.isJoined());

		// 夜トク割引のレコードの場合、加入フラグがtrueになる
		nightService.checkService(new Record("2 E2"));
		assertTrue(nightService.isJoined());

		// clear()メソッドを呼び出すと、加入フラグがfalseになる
		nightService.clear();
		assertFalse(nightService.isJoined());
	}

	@Test
	public void testIsServiceTime() {
		NightService nightService = new NightService();

		// 夜トク割引に加入していない場合は、どの時間も夜トク割引の対象にならない
		assertFalse(nightService.isServiceTime(6));
		assertFalse(nightService.isServiceTime(5));
		assertFalse(nightService.isServiceTime(23));
		assertFalse(nightService.isServiceTime(22));

		// 昼トク割引に介入している場合は、23:00時から05:59分までに開始された通話は割引対象になる
		nightService.checkService(new Record("2 E2"));
		assertFalse(nightService.isServiceTime(6));
		assertTrue(nightService.isServiceTime(5));
		assertTrue(nightService.isServiceTime(23));
		assertFalse(nightService.isServiceTime(22));
	}
//
	@Test
	public void testCalcUnitPrice() {
		NightService nightService = new NightService();

		nightService.checkService(new Record("2 E2"));

		// 夜トク割引の対象外の時間の場合、単価は変わらない
		int unitPrice = nightService.calcUnitPrice(new Record("5 2004/06/05 06:00 010 090-1234-0002"), 20);
		assertEquals(20, unitPrice);

		// 夜トク割引の対象の時間の場合、単価は5円引き
		unitPrice = nightService.calcUnitPrice(new Record("5 2004/06/05 05:59 010 090-1234-0002"), 20);
		assertEquals(15, unitPrice);
	}

	@Test
	public void testCalcBasicCharge() {
		NightService nightService = new NightService();

		// 夜トク割引に加入していない場合、基本料金は変わらない
		int basicCharge = nightService.calcBasicCharge(1000);
		assertEquals(1000, basicCharge);

		// 夜トク割引に加入している場合、基本料金は500円増し
		nightService.checkService(new Record("2 E2"));
		basicCharge = nightService.calcBasicCharge(1000);
		assertEquals(1500, basicCharge);
	}

}
