import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { ProjectService } from '../../services/project.service';
import { Project } from '../../models/project.model';

@Component({
  selector: 'app-form',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, ReactiveFormsModule],
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {
  projectForm!: FormGroup;
  editMode = false;
  projectId: string | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private projectService: ProjectService
  ) {}

  ngOnInit(): void {
    this.projectForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      technologies: this.fb.array([])
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.editMode = true;
      this.projectId = id;
      this.projectService.getById(id).subscribe(project => {
        if (project) {
          this.projectForm.patchValue({
            name: project.name,
            description: project.description || ''
          });
          this.technologies.clear();
          project.technologies?.forEach(tech => this.addTechnology(tech));
        }
      });
    } else {
      this.addTechnology();
    }
  }

  get technologies(): FormArray {
    return this.projectForm.get('technologies') as FormArray;
  }

  addTechnology(value = ''): void {
    this.technologies.push(this.fb.control(value));
  }

  removeTechnology(index: number): void {
    this.technologies.removeAt(index);
  }

  onSubmit(): void {
    if (this.projectForm.invalid) {
      return;
    }

    const formValue: Project = this.projectForm.value;

    if (this.editMode && this.projectId) {
      this.projectService.update(this.projectId, formValue)
        .then(() => this.router.navigate(['/projects']))
        .catch(err => console.error('Error actualizando:', err));
    } else {
      this.projectService.create(formValue)
        .then(() => {
          console.log('Proyecto creado correctamente.');
          this.router.navigate(['/projects']);
        })
        .catch(err => console.error('Error creando proyecto:', err));
    }
  }

  cancel(): void {
    this.router.navigate(['/projects']);
  }
}
