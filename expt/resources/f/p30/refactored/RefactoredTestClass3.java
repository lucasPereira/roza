import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	@Before()
	public void setup() {
		this.company = new Company("Empresa Ltda");
		this.company.createWorker("João");
	}

	@Test()
	public void testAssignWorkerToInexistentProject() {
		this.company.assignWorkerToProject("Projeto1", "João");
	}

	@Test()
	public void testCreateWorker() {
		assertTrue(this.company.getWorkers().containsKey("João"));
		assertEquals(this.company.getWorkers().size(), 1);
	}
}
