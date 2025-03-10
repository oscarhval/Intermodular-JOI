import { Injectable, Injector, runInInjectionContext } from '@angular/core';
import {
  AngularFirestore,
  AngularFirestoreCollection,
  DocumentReference
} from '@angular/fire/compat/firestore';
import { Project } from '../models/project.model';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private projectsCollection: AngularFirestoreCollection<Project>;

  constructor(private firestore: AngularFirestore, private injector: Injector) {

    this.projectsCollection = this.firestore.collection<Project>('projects');
    
  }

  getAll(): Observable<Project[]> {
    return this.projectsCollection.snapshotChanges().pipe(
      map(actions =>
        actions.map(a => {
          const data = a.payload.doc.data() as Project;
          const id = a.payload.doc.id;
          return { id, ...data };
        })
      )
    );
  }

  getById(id: string): Observable<Project> {
    return runInInjectionContext(this.injector, () =>
      this.firestore.doc<Project>(`projects/${id}`).snapshotChanges().pipe(
        map(action => {
          const data = (action.payload as any).data();
          console.log('getById: datos obtenidos para id', id, data);
          if (data) {
            return { id, ...data } as Project;
          }
          throw new Error('Proyecto no encontrado');
        })
      )
    );
  }


  create(project: Project): Promise<DocumentReference<Project>> {
    return this.projectsCollection.add(project);
  }


  update(id: string, project: Partial<Project>): Promise<void> {
    return runInInjectionContext(this.injector, () =>
      this.projectsCollection.doc(id).update(project)
    );
  }


  delete(id: string): Promise<void> {
    return runInInjectionContext(this.injector, () =>
      this.projectsCollection.doc(id).delete()
    );
  }
}