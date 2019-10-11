public class CreateUserTest {

	@Test
	public void create() {
		User john = new User();
		john.setName("John Doe");
		john.setCareer("Programmer");
		assertNull(john.getId());
		assertEquals("John Doe", john.getName());
		assertEquals("Programmer", john.getCareer());
	}

}
