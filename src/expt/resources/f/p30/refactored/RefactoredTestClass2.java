import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void testRemoveWorker() {
		this.company = new Company("Empresa Ltda");
		Project project = this.company.createProject("Projeto 1");
		Worker worker = this.company.createWorker("João");
		this.company.assignWorkerToProject("Projeto 1", "João");
		Issue issue = project.createIssue(worker, "Tarefa Teste", IssueType.FEATURE);
		this.company.removeWorker("João");
		assertFalse(this.company.getWorkers().containsKey("João"));
		assertFalse(project.getWorkers().contains(worker));
		assertNull(issue.getOwner());
		assertEquals(this.company.getWorkers().size(), 0);
	}
}
