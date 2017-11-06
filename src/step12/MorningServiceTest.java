package step12;

import static org.junit.Assert.*;

import org.junit.Test;

public class MorningServiceTest {

	@Test
	public void testCheckService() {
		MorningService morningService = new MorningService();

		// インスタンスを作成した直後は、加入フラグはfalse
		assertFalse(morningService.isJoined());

		// 朝トク割引意外のレコードの場合、加入フラグはfalseのまま
		morningService.checkService(new Record("2 C1 090-1234-0001"));
		assertFalse(morningService.isJoined());

		// 朝割引のレコードの場合、加入フラグがtrueになる
		morningService.checkService(new Record("2 E3"));
		assertTrue(morningService.isJoined());

		// clear()メソッドを呼び出すと、加入フラグがfalseになる
		morningService.clear();
		assertFalse(morningService.isJoined());
	}

	@Test
	public void testIsServiceTime() {
		MorningService morningService = new MorningService();

		// 朝トク割引に加入していない場合は、どの時間も夜トク割引の対象にならない
		assertFalse(morningService.isServiceTime(5));
		assertFalse(morningService.isServiceTime(6));
		assertFalse(morningService.isServiceTime(7));
		assertFalse(morningService.isServiceTime(8));

		// 朝トク割引に介入している場合は、23:00時から05:59分までに開始された通話は割引対象になる
		morningService.checkService(new Record("2 E3"));
		assertFalse(morningService.isServiceTime(5));
		assertTrue(morningService.isServiceTime(6));
		assertTrue(morningService.isServiceTime(7));
		assertFalse(morningService.isServiceTime(8));
	}
//
	@Test
	public void testCalcUnitPrice() {
		MorningService morningService = new MorningService();

		morningService.checkService(new Record("2 E3"));

		// 朝トク割引の対象外の時間の場合、単価は変わらない
		int unitPrice = morningService.calcUnitPrice(new Record("5 2004/06/05 08:00 010 090-1234-0002"), 20);
		assertEquals(20, unitPrice);

		// 朝トク割引の対象の時間の場合、単価は5円引き
		unitPrice = morningService.calcUnitPrice(new Record("5 2004/06/05 07:59 010 090-1234-0002"), 20);
		assertEquals(10, unitPrice);
	}

	@Test
	public void testCalcBasicCharge() {
		MorningService morningService = new MorningService();

		// 朝トク割引に加入していない場合、基本料金は変わらない
		int basicCharge = morningService.calcBasicCharge(1000);
		assertEquals(1000, basicCharge);

		// 朝トク割引に加入している場合、基本料金は500円増し
		morningService.checkService(new Record("2 E3"));
		basicCharge = morningService.calcBasicCharge(1000);
		assertEquals(1200, basicCharge);
	}

}
