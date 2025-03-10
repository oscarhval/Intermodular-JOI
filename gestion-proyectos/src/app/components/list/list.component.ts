import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { Observable } from 'rxjs';
import { Project } from '../../models/project.model';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {
  projects$!: Observable<Project[]>;

  constructor(
    private projectService: ProjectService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.projects$ = this.projectService.getAll();
  }

  goToDetail(id: string): void {
    this.router.navigate(['/projects', id]);
  }

  goToEdit(id: string): void {
    this.router.navigate(['/projects/edit', id]);
  }

  deleteProject(id: string): void {
    if (confirm('¿Estás seguro de eliminar este proyecto?')) {
      this.projectService.delete(id)
        .then(() => console.log('Proyecto eliminado'))
        .catch(err => console.error('Error eliminando proyecto:', err));
    }
  }
}