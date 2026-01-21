import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	@Before()
	public void setup() {
		this.company = new Company("Empresa Ltda");
		this.project = this.company.createProject("Projeto 1");
		this.worker = this.company.createWorker("João");
		this.auxWorker = this.company.createWorker("Edivaldo");
		this.company.assignWorkerToProject("Projeto 1", "João");
		this.company.assignWorkerToProject("Projeto 1", "Edivaldo");
		this.issue = this.project.createIssue(this.worker, "Tarefa Principal", IssueType.INTERN);
	}

	@Test()
	public void testChangeClosedIssueOwner() {
		this.worker.finishIssue(this.issue);
		this.project.changeIssueOwner(auxWorker, issue);
	}

	@Test()
	public void testChangeClosedIssuePriority() {
		this.worker.finishIssue(this.issue);
		this.project.changeIssuePriority(this.issue, IssuePriority.HIGH);
	}

	@Test()
	public void testChangeIssueOwner() {
		this.project.changeIssueOwner(this.auxWorker, this.issue);
		assertFalse(this.worker.getAssignedIssues().contains(this.issue));
		assertTrue(this.auxWorker.getAssignedIssues().contains(this.issue));
		assertEquals(issue.getOwner(), this.auxWorker);
	}

	@Test()
	public void testChangeIssuePriority() {
		this.project.changeIssuePriority(this.issue, IssuePriority.LOW);
		assertEquals(this.issue.getPriority(), IssuePriority.LOW);
	}

	@Test()
	public void testChangeIssueToInexistentWorker() {
		Worker newWorker = this.company.createWorker("Marta");
		Issue issue = this.project.createIssue(this.worker, "Tarefa Teste", IssueType.FEATURE);
		this.project.changeIssueOwner(newWorker, issue);
	}

	@Test()
	public void testChangeOwnerOfInexistentIssue() {
		Project newProject = this.company.createProject("Projeto 2");
		this.company.assignWorkerToProject("Projeto 2", "João");
		Issue newIssue = newProject.createIssue(this.worker, "Tarefa Teste 2", IssueType.BUG);
		this.project.changeIssueOwner(this.worker, newIssue);
	}

	@Test()
	public void testChangePriorityOfInexistentIssue() {
		Issue newIssue = new Issue(this.project, this.worker, "Tarefa Teste 2", IssueType.BUG);
		this.project.changeIssuePriority(newIssue, IssuePriority.LOW);
	}

	@Test()
	public void testCreateIssueWithInexistentWorker() {
		Issue newIssue = this.project.createIssue(new Worker("Fernanda"), "Tarefa Teste", IssueType.FEATURE);
	}

	@Test()
	public void testCreateValidIssue() {
		Integer currentNOfIssues = this.project.getIssues().size();
		Issue newIssue = this.project.createIssue(this.worker, "Tarefa Teste", IssueType.FEATURE);
		assertEquals(this.project.getIssues().size(), currentNOfIssues + 1);
		assertTrue(this.worker.getAssignedIssues().contains(newIssue));
		assertTrue(this.project.getIssues().containsKey(newIssue.getKey()));
		assertEquals(newIssue.getOwner(), this.worker);
		assertEquals(newIssue.getDescription(), "Tarefa Teste");
		assertEquals(newIssue.getStatus(), IssueStatus.OPEN);
		assertEquals(newIssue.getPriority(), IssuePriority.MEDIUM);
		assertEquals(newIssue.getProject(), this.project);
		assertEquals(newIssue.getType(), IssueType.FEATURE);
	}

	@Test()
	public void testCreateValidTwoIssues() {
		Integer currentNOfIssues = this.project.getIssues().size();
		Issue issue1 = this.project.createIssue(this.worker, "Tarefa Teste 1", IssueType.FEATURE);
		Issue issue2 = this.project.createIssue(this.worker, "Tarefa Teste 1", IssueType.FEATURE);
		assertNotEquals(issue1.getKey(), issue2.getKey());
		assertEquals(this.project.getIssues().size(), currentNOfIssues + 2);
	}

	@Test()
	public void testDeleteIssue() {
		this.project.deleteIssue(this.issue.getKey());
		assertFalse(this.project.getIssues().containsKey(this.issue.getKey()));
		assertNull(this.issue.getOwner());
		assertFalse(this.worker.getAssignedIssues().contains(this.issue));
		assertEquals(this.project.getIssues().size(), 0);
	}

	@Test()
	public void testRemoveExistentWorker() {
		this.project.removeWorker(this.worker);
		assertFalse(this.project.getWorkers().contains(this.worker));
	}

	@Test()
	public void testRemoveInexistentWorker() {
		this.project.removeWorker(this.worker);
		this.project.removeWorker(this.worker);
	}
}
