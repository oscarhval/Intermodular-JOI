import { Routes } from '@angular/router';
import { ListComponent } from './components/list/list.component';
import { FormComponent } from './components/form/form.component';
import { DetailComponent } from './components/detail/detail.component';


export const routes: Routes = [
  { path: '', redirectTo: '/projects', pathMatch: 'full' },
  { path: 'projects', component: ListComponent },
  { path: 'projects/new', component: FormComponent },
  { path: 'projects/edit/:id', component: FormComponent },
  { path: 'projects/:id', component: DetailComponent }
];

