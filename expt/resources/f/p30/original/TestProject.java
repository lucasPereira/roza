package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.*;
import exceptions.*;

public class TestProject {
	
	private Company company;
	private Project project;
	private Worker worker;
	private Worker auxWorker;
	private Issue issue;
	
	@Before
	public void setUp() throws ProjectNotFoundException, WorkerNotFoundException, WorkerFullException, DuplicateIssueException, IssueClosedException {
		this.company = new Company("Empresa Ltda"); 
		this.project = this.company.createProject("Projeto 1");
		this.worker = this.company.createWorker("João");
		this.auxWorker = this.company.createWorker("Edivaldo");
		this.company.assignWorkerToProject("Projeto 1", "João");
		this.company.assignWorkerToProject("Projeto 1", "Edivaldo");
		this.issue = this.project.createIssue(this.worker, "Tarefa Principal", IssueType.INTERN);
	}
	
	@Test
	public void testCreateValidIssue() throws WorkerNotFoundException, WorkerFullException, DuplicateIssueException, IssueClosedException {
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
	
	@Test
	public void testCreateValidTwoIssues() throws WorkerNotFoundException, WorkerFullException, DuplicateIssueException, IssueClosedException {
		Integer currentNOfIssues = this.project.getIssues().size();
		Issue issue1 = this.project.createIssue(this.worker, "Tarefa Teste 1", IssueType.FEATURE);
		Issue issue2 = this.project.createIssue(this.worker, "Tarefa Teste 1", IssueType.FEATURE);		// Atributos das duas ocorrências estão exatamente iguais para garantir que a chave não é derivada de nenhum deles
		assertNotEquals(issue1.getKey(), issue2.getKey());
		assertEquals(this.project.getIssues().size(), currentNOfIssues + 2);
	}
	
	@Test(expected=WorkerNotFoundException.class)
	public void testCreateIssueWithInexistentWorker() throws WorkerNotFoundException, WorkerFullException, DuplicateIssueException, IssueClosedException {
		Issue newIssue = this.project.createIssue(new Worker("Fernanda"), "Tarefa Teste", IssueType.FEATURE);
	}
	
	@Test
	public void testChangeIssueOwner() throws WorkerFullException, DuplicateIssueException, IssueNotFoundException, IssueClosedException, WorkerNotFoundException {
		this.project.changeIssueOwner(this.auxWorker, this.issue);
		assertFalse(this.worker.getAssignedIssues().contains(this.issue));
		assertTrue(this.auxWorker.getAssignedIssues().contains(this.issue));
		assertEquals(issue.getOwner(), this.auxWorker);
	}
	
	@Test(expected=IssueNotFoundException.class) 
	public void testChangeOwnerOfInexistentIssue() throws WorkerNotFoundException, WorkerFullException, DuplicateIssueException, IssueNotFoundException, IssueClosedException, ProjectNotFoundException {
		Project newProject = this.company.createProject("Projeto 2");
		this.company.assignWorkerToProject("Projeto 2", "João");
		Issue newIssue = newProject.createIssue(this.worker, "Tarefa Teste 2", IssueType.BUG);
		this.project.changeIssueOwner(this.worker, newIssue);
	}
	
	@Test(expected=WorkerNotFoundException.class) 
	public void testChangeIssueToInexistentWorker() throws WorkerNotFoundException, WorkerFullException, DuplicateIssueException, IssueNotFoundException, IssueClosedException {
		Worker newWorker = this.company.createWorker("Marta");
		Issue issue = this.project.createIssue(this.worker, "Tarefa Teste", IssueType.FEATURE);
		this.project.changeIssueOwner(newWorker, issue);
	}
	
	@Test(expected=IssueClosedException.class) 
	public void testChangeClosedIssueOwner() throws WorkerNotFoundException, WorkerFullException, DuplicateIssueException, IssueNotFoundException, IssueClosedException, ProjectNotFoundException {
		this.worker.finishIssue(this.issue);
		this.project.changeIssueOwner(auxWorker, issue);
	}
	
	@Test(expected=IssueClosedException.class) 
	public void testChangeClosedIssuePriority() throws IssueNotFoundException, IssueClosedException {
		this.worker.finishIssue(this.issue);
		this.project.changeIssuePriority(this.issue, IssuePriority.HIGH);
	}
	
	@Test(expected=IssueNotFoundException.class) 
	public void testChangePriorityOfInexistentIssue() throws IssueNotFoundException, IssueClosedException {
		Issue newIssue = new Issue(this.project, this.worker, "Tarefa Teste 2", IssueType.BUG);
		this.project.changeIssuePriority(newIssue, IssuePriority.LOW);
	}
	
	@Test
	public void testChangeIssuePriority() throws IssueNotFoundException, IssueClosedException {
		this.project.changeIssuePriority(this.issue, IssuePriority.LOW);
		assertEquals(this.issue.getPriority(), IssuePriority.LOW);
	}
	
	@Test
	public void testRemoveExistentWorker() throws WorkerNotFoundException {
		this.project.removeWorker(this.worker);
		assertFalse(this.project.getWorkers().contains(this.worker));
	}
	
	@Test(expected=WorkerNotFoundException.class)
	public void testRemoveInexistentWorker() throws WorkerNotFoundException {
		this.project.removeWorker(this.worker);
		this.project.removeWorker(this.worker);
	}
	
	@Test
	public void testDeleteIssue() throws IssueNotFoundException {
		this.project.deleteIssue(this.issue.getKey());
		assertFalse(this.project.getIssues().containsKey(this.issue.getKey()));
		assertNull(this.issue.getOwner());
		assertFalse(this.worker.getAssignedIssues().contains(this.issue));
		assertEquals(this.project.getIssues().size(), 0);
	}
}
