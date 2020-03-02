public class InsertUserTest {

	@Test
	public void insert() {
		User john = new User();
		john.setName("John Doe");
		john.setCareer("Programmer");
		UserDao dao = new UserDao();
		Long id = dao.insert(john);
		assertEquals(id, john.getId());
	}

}
