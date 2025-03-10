import { ComponentFixture, TestBed } from '@angular/core/testing';
import { DetailComponent } from './detail.component';
import { of } from 'rxjs';
import { ProjectService } from '../../services/project.service';
import { ActivatedRoute } from '@angular/router';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;

  const projectServiceStub = {
    getById: (id: string) => of({
      id,
      name: 'Proyecto de Prueba',
      technologies: ['Angular', 'Firebase'],
      description: 'Descripción de prueba'
    })
  };

  const activatedRouteStub = {
    snapshot: {
      paramMap: {
        get: (key: string) => 'testId'
      }
    }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailComponent],
      providers: [
        { provide: ActivatedRoute, useValue: activatedRouteStub }
      ]
    })
    .overrideProvider(ProjectService, { useValue: projectServiceStub })
    .compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
