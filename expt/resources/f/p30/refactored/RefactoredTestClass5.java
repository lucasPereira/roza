import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	@Before()
	public void setup() {
		this.company = new Company("Empresa Ltda");
		this.company.createProject("Projeto 1");
	}

	@Test()
	public void testAssignInexistentWorkerToProject() {
		this.company.assignWorkerToProject("Projeto 1", "João");
	}

	@Test()
	public void testCreateProject() {
		assertTrue(this.company.getProjects().containsKey("Projeto 1"));
		assertEquals(this.company.getProjects().size(), 1);
	}

	@Test()
	public void testRemoveProject() {
		this.company.removeProject("Projeto 1");
		assertFalse(this.company.getProjects().containsKey("Projeto 1"));
		assertEquals(this.company.getProjects().size(), 0);
	}
}
