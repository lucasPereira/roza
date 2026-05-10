import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testAssignWorkerToProjectValid() {
		this.company = new Company("Empresa Ltda");
		Project newProject = this.company.createProject("Projeto 1");
		Worker newWorker = this.company.createWorker("João");
		this.company.assignWorkerToProject("Projeto 1", "João");
		assertTrue(newProject.getWorkers().contains(newWorker));
		assertTrue(newWorker.getEnrolledProjects().contains(newProject));
	}
}
