package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

import domain.*;
import exceptions.*;

public class TestCompany {

	private Company company;
	
	@Before
	public void setUp() {
		this.company = new Company("Empresa Ltda");
	}
	
	@Test
	public void testCreateWorker() {	
		this.company.createWorker("João");
		assertTrue(this.company.getWorkers().containsKey("João"));
		assertEquals(this.company.getWorkers().size(), 1);			// Verificar quantidades de objetos criados (adicionado em vários outros lugares)
	}
	
	@Test
	public void testCreateProject() {	
		this.company.createProject("Projeto 1");
		assertTrue(this.company.getProjects().containsKey("Projeto 1"));
		assertEquals(this.company.getProjects().size(), 1);
	}
	
	@Test
	public void testAssignWorkerToProjectValid() throws ProjectNotFoundException, WorkerNotFoundException {
		Project newProject = this.company.createProject("Projeto 1");
		Worker newWorker = this.company.createWorker("João");
		this.company.assignWorkerToProject("Projeto 1", "João");
		assertTrue(newProject.getWorkers().contains(newWorker));
		assertTrue(newWorker.getEnrolledProjects().contains(newProject));
	}
	
	@Test(expected=ProjectNotFoundException.class)
	public void testAssignWorkerToInexistentProject() throws ProjectNotFoundException, WorkerNotFoundException {
		this.company.createWorker("João");
		this.company.assignWorkerToProject("Projeto1", "João");
	}
	
	@Test(expected=WorkerNotFoundException.class)
	public void testAssignInexistentWorkerToProject() throws ProjectNotFoundException, WorkerNotFoundException {
		this.company.createProject("Projeto 1");
		this.company.assignWorkerToProject("Projeto 1", "João");
	}
	
	@Test
	public void testRemoveProject() {
		this.company.createProject("Projeto 1");
		this.company.removeProject("Projeto 1");
		assertFalse(this.company.getProjects().containsKey("Projeto 1"));
		assertEquals(this.company.getProjects().size(), 0);
	}
	
	@Test
	public void testRemoveWorker() throws WorkerNotFoundException, ProjectNotFoundException, WorkerFullException, DuplicateIssueException, IssueClosedException {
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
