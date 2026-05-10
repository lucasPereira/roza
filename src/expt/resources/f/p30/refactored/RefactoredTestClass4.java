import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Company company;

	private Project project;

	private Worker worker;

	@Before()
	public void setup() {
		company = new Company("Empresa Ltda");
		project = company.createProject("Projeto 1");
		worker = company.createWorker("João");
		company.assignWorkerToProject("Projeto 1", "João");
		this.issue = project.createIssue(worker, "Tarefa Teste", IssueType.INTERN);
		this.issue.close();
	}

	@Test()
	public void testCloseClosedIssue() {
		this.issue.close();
	}

	@Test()
	public void testCloseOpenIssue() {
		assertEquals(this.issue.getStatus(), IssueStatus.CLOSED);
		assertNull(this.issue.getOwner());
	}
}
