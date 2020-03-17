import org.itstep.Factory;
import org.itstep.domain.Product;
import org.itstep.logic.LogicException;
import org.itstep.storage.DaoException;
import org.itstep.storage.ProductDao;

public class Test {
	public static void main(String[] args) throws ClassNotFoundException {
		try {
			Class.forName("org.postgresql.Driver");
			try(Factory factory = new Factory()) {
				ProductDao dao = factory.getProductDao();
				Product product = dao.read(100L);
				System.out.println(product);
			} catch(LogicException | DaoException e) {
				e.printStackTrace();
				System.out.println("Ошибка доступа к базе данных");
			}
		} catch(ClassNotFoundException e) {
			System.out.println("Ошибка запуска приложения");
		}
	}
}
