package step12;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServiceCollectionTest {
	@Test
	public void testCalcBasicCharge() {
		Service service = new ServiceCollection();

		// どのサービスにも加入していない場合は基本料金は変わらない
		assertEquals(1000,service.calcBasicCharge(1000));

		// 昼トク割引に加入している場合は基本料金は200円増し
		service.checkService(new Record("2 E1"));
		assertEquals(1200, service.calcBasicCharge(1000));

		service.clear();

		// 夜トク割引に加入している場合は基本料金は200円増し
		service.checkService(new Record("2 E2"));
		assertEquals(1500, service.calcBasicCharge(1000));

		service.clear();

		// 昼トク割引に加入している場合は基本料金は200円増し
		service.checkService(new Record("2 E3"));
		assertEquals(1200, service.calcBasicCharge(1000));

		service.clear();

		// 家族割引に加入している場合は基本料金は100円増し
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(1100, service.calcBasicCharge(1000));

		service.clear();

		// 昼トク割引と家族割引に加入している場合は基本料金は300円増し
		service.checkService(new Record("2 E1"));
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(1300, service.calcBasicCharge(1000));

		service.clear();

		// 夜トク割引と家族割引に加入している場合は基本料金は600円増し
		service.checkService(new Record("2 E2"));
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(1600, service.calcBasicCharge(1000));

		service.clear();

		// 昼トク割引と家族割引に加入している場合は基本料金は300円増し
		service.checkService(new Record("2 E3"));
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(1300, service.calcBasicCharge(1000));

		service.clear();

		// 昼トク割引と夜トク割引に加入している場合は基本料金は700円増し
		service.checkService(new Record("2 E1"));
		service.checkService(new Record("2 E2"));
		assertEquals(1700, service.calcBasicCharge(1000));

		service.clear();

		// 昼トク割引と夜トク割引と家族割引に加入している場合は基本料金は800円増し
		service.checkService(new Record("2 E1"));
		service.checkService(new Record("2 E2"));
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(1800, service.calcBasicCharge(1000));

		service.clear();

		// 朝トクと昼トク割引と夜トク割引と家族割引に加入している場合は基本料金は800円増し
		service.checkService(new Record("2 E1"));
		service.checkService(new Record("2 E2"));
		service.checkService(new Record("2 E3"));
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(2000, service.calcBasicCharge(1000));
	}



	@Test
	public void testCalcUnitPrice() {
		Service service = new ServiceCollection();

		// どのサービスにも加入していない場合は通話単価は変わらない
		assertEquals(20, service.calcUnitPrice(new Record("5 2004/06/05 17:50 010 090-1234-0001"), 20));

		// 昼トク割引に加入している場合は通話単価は5円引き
		service.checkService(new Record("2 E1"));
		assertEquals(15, service.calcUnitPrice(new Record("5 2004/06/05 17:50 010 090-1234-0001"), 20));

		service.clear();

		// 夜トク割引に加入している場合は通話単価は5円引き
		service.checkService(new Record("2 E2"));
		assertEquals(15, service.calcUnitPrice(new Record("5 2004/06/05 04:50 010 090-1234-0001"), 20));

		service.clear();

		// 朝トク割引に加入している場合は通話単価は10円引き
		service.checkService(new Record("2 E3"));
		assertEquals(10, service.calcUnitPrice(new Record("5 2004/06/05 06:50 010 090-1234-0001"), 20));

		service.clear();

		// 家族割引に加入している場合は通話単価は半額で小数点以下切り捨て
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(7, service.calcUnitPrice(new Record("5 2004/06/05 17:50 010 090-1234-0001"), 15));

		service.clear();

		// 昼トク割引と家族割引に加入している場合は基本料金は、5円引きのさらに半額で小数点以下切り捨て
		service.checkService(new Record("2 E1"));
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(7, service.calcUnitPrice(new Record("5 2004/06/05 17:50 010 090-1234-0001"), 20));

		service.clear();

		// 夜トク割引と家族割引に加入している場合は基本料金は、5円引きのさらに半額で小数点以下切り捨て
		service.checkService(new Record("2 E2"));
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(7, service.calcUnitPrice(new Record("5 2004/06/05 04:50 010 090-1234-0001"), 20));

		service.clear();

		// 朝トク割引と家族割引に加入している場合は基本料金は、10円引きのさらに半額で小数点以下切り捨て
		service.checkService(new Record("2 E3"));
		service.checkService(new Record("2 C1 090-1234-0001"));
		assertEquals(5, service.calcUnitPrice(new Record("5 2004/06/05 06:50 010 090-1234-0001"), 20));
	}
}
