import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	@Before()
	public void setup() {
		this.worker = new Worker("João");
		this.project = new Project("Projeto 1");
		this.issue = new Issue(this.project, this.worker, "Tarefa Teste", IssueType.FEATURE);
	}

	@Test()
	public void testAssignClosedIssue() {
		this.issue.close();
		this.worker.assignIssue(this.issue);
	}

	@Test()
	public void testAssignDuplicateIssue() {
		this.worker.assignIssue(this.issue);
		this.worker.assignIssue(this.issue);
	}

	@Test()
	public void testAssignIssue() {
		this.worker.assignIssue(this.issue);
		assertEquals(this.worker.getAssignedIssues().size(), 1);
		assertTrue(this.worker.getAssignedIssues().contains(this.issue));
	}

	@Test()
	public void testAssignIssueToFullWorker() {
		assignTenIssuesToWorker();
		Issue newIssue = new Issue(this.project, this.worker, "Tarefa Teste", IssueType.FEATURE);
		this.worker.assignIssue(newIssue);
	}

	@Test()
	public void testAssignTenIssuesToWorker() {
		assignTenIssuesToWorker();
		assertEquals(this.worker.getAssignedIssues().size(), 10);
	}

	@Test()
	public void testFinishClosedIssue() {
		this.worker.assignIssue(this.issue);
		this.issue.close();
		this.worker.finishIssue(this.issue);
	}

	@Test()
	public void testFinishInexistentIssue() {
		this.worker.finishIssue(this.issue);
	}

	@Test()
	public void testFinishIssue() {
		this.worker.assignIssue(this.issue);
		this.worker.finishIssue(this.issue);
		assertEquals(this.worker.getAssignedIssues().size(), 0);
		assertEquals(this.issue.getStatus(), IssueStatus.CLOSED);
		assertNull(this.issue.getOwner());
	}

	@Test()
	public void testUnassignInexistentIssue() {
		this.worker.assignIssue(this.issue);
		Issue newIssue = new Issue(this.project, this.worker, "Tarefa Teste", IssueType.FEATURE);
		this.worker.unassignIssue(newIssue);
	}

	@Test()
	public void testUnassignIssue() {
		this.worker.assignIssue(this.issue);
		this.worker.unassignIssue(this.issue);
		assertEquals(this.worker.getAssignedIssues().size(), 0);
	}
}
